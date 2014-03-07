#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
KTN-project 2013 / 2014
Very simple server implementation 
'''

# Import modules we need for server
import re
import json
import socket
import select
import Queue
import time

class Server(object):

    def __init__(self, HOST='localhost', PORT=9999, debug=True):
        self.debug = debug
        self.readableClients = []
        self.writeableClients = []
        self.recvBuff = 1024
        self.msgQ = {}
        self.usernames = {}
        self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.serverSocket.setblocking(0)
        self.serverSocket.bind((HOST, PORT))
        self.serverSocket.listen(10)
        if self.debug: print 'Server.__init__: CHAT SERVER STARTED ON (%s, %s)' % (HOST, PORT)


    def serveForever(self):
        self.readableClients.append(self.serverSocket)
        if self.debug: print 'Server.serveForever: SERVING FOREVER' 

        while True:
            if self.debug: print '\nServer.serveForever: NEW ITERATION'
            try:
                readyToRead, readyToWrite, errorSockets = select.select(self.readableClients, self.writeableClients, self.readableClients)
            except socket.error, e:
                print 'Server.serveForever: SOCKET ERROR'
                continue
            except select.error, e:
                print 'Server.serveForever: SELECT ERROR'
                continue

            for sock in readyToRead:
                if sock == self.serverSocket:
                    sockfd, addr = self.serverSocket.accept()
                    sockfd.setblocking(0)
                    self.readableClients.append(sockfd)
                    print 'Server.serveForever: CLIENT (%s, %s) CONNECTED' % addr
                    self.msgQ[sockfd] = Queue.Queue()
                    # sockfd.sendall(json.dumps({'response': 'message', 'message': 'Welcome to the chatserver!'}))
                    # broadcast when logged in self.broadcastMessage(sockfd, 'CLIENT (%s, %s) CONNECTED' % addr)
                else:
                    data = sock.recv(self.recvBuff).strip()
                    if self.debug: print 'Server.serveForever: RECIEVED DATA FROM (%s, %s)' % addr
                    if data:
                        if self.debug: print 'Server.serveForever: CONNECTED SOCKETS ' + str(len(self.readableClients))
                        print 'Server.serveForever: RECEIVED %s FROM %s' % (data, sock.getpeername())
                        if sock not in self.writeableClients:
                            self.writeableClients.append(sock)
                        self.handleJSON(data, sock) # This is where messages are added to the message queue
                    else:
                        if self.debug: print 'Server.serveForever: CONNECTION LOST WITH (%s, %s) AFTER READING NO DATA' % addr
                        print 'Server.serveForever: CLIENT (%s, %s) DISCONNECTED' % addr
                        self.broadcastMessage(sock, json.dumps({'response': 'message', 'message': '%s  %s has quit the chat (connection lost or quit client)' % (time.strftime("%H:%M:%S"), self.usernames[sock])})) # must send json
                        if sock in self.writeableClients:
                            self.writeableClients.remove(sock)
                        self.readableClients.remove(sock)
                        if sock in self.usernames.iterkeys():
                            if self.debug: print 'Server.serveForever: REMOVED %s FROM USERNAMES' % self.usernames[sock]
                            del self.usernames[sock]
                        sock.close()

            for sock in readyToWrite:
                try:
                    nextMsg = self.msgQ[sock].get_nowait()
                except Queue.Empty:
                    print 'Server.serveForever: MESSAGE QUEUE FOR (%s, %s) IS EMPTY' % addr
                    self.writeableClients.remove(sock)
                else:
                    self.broadcastMessage(sock, nextMsg) # must send json
                    #print 'Server.serveForever: SENDING %s TO %s' % (nextMsg, str(sock.getpeername()))
                    #sock.send(nextMsg)
            
            for sock in errorSockets:
                print 'Server.serveForever: HANDLING EXCEPTION FOR (%s, %s)' % sock.getpeername()
                self.readableClients.remove(sock)
                if sock in self.writeableClients:
                    self.writeableClients.remove(sock)
                sock.close()
        self.serverSocket.close()

    def handleJSON(self, data, sock):
        data = json.loads(data)
        if data['request'] == 'login':
            if not data['username'] in self.usernames.itervalues():
                if re.search("^[a-zA-ZæøåÆØÅ0-9_]{0,15}$", data['username']) is not None:
                    if self.debug: print 'Server.handleJSON: USERNAME %s ADDED' % data['username']
                    self.usernames[sock] = data['username']
                    #sock.sendall(json.dumps({'response': 'login', 'username': data['username']}))
                    #self.broadcastMessage(sock, json.dumps({'response': 'message', 'message': '%s LOGGED IN' % data['username']}))
                    self.msgQ[sock].put(json.dumps({'response': 'message', 'message': '%s  %s joined the chat' % (time.strftime("%H:%M:%S"), data['username'])}))
                else:
                    if self.debug: print 'Server.handleJSON: INVALID USERNAME %s' % data['username']
                    self.msgQ[sock].put(json.dumps({'response': 'login', 'username': data['username'], 'error': 'Invalid username!'}))
                    #sock.sendall(json.dumps({'response': 'login', 'username': data['username'], 'error': 'Invalid username!'}))
            else:
                if self.debug: print 'Server.handleJSON: USERNAME %s ALREADY TAKEN' % data['username'] 
                #sock.sendall(json.dumps({'response': 'login', 'username': data['username'], 'error': 'Name already taken!'}))
                self.msgQ[sock].put(json.dumps({'response': 'login', 'username': data['username'], 'error': 'Name already taken!'}))
        elif data['request'] == 'logout':
            # handle logout
            pass
        elif data['request'] == 'message':
            if self.debug: print 'Server.handleJSON: SENDING MESSAGE FROM %s' % str(sock.getpeername())
            #self.broadcastMessage(sock, json.dumps({'response': 'message', 'message': self.usernames[sock] + ' said ' + data['message']}))
            try:
                self.msgQ[sock].put(json.dumps({'response': 'message', 'message': '%s  %s said | %s' % (time.strftime("%H:%M:%S"), self.usernames[sock], data['message'])}))
            except KeyError:
                if self.debug: print 'Server.handleJSON: KeyError, PROBABLY INVALID USERNAME'
        else:
            if self.debug: print 'Server.handleJSON: UNEXPECTED JSON'
                    
    def broadcastMessage(self, sock, message):
        if self.debug: print 'Server.broadcastMessage: BROADCAST CALLED'
        for socket in self.readableClients:
            if socket != self.serverSocket and socket != sock:
                print 'Server.broadcastMessage: SENDING %s TO %s' % (message, str(socket.getpeername()))
                socket.sendall(message)
    
# Main method
if __name__ == "__main__":
    ip = raw_input('''Choose IP: [''] for localhost, [out] for router-IP: ''')
    if str(ip) == '':
        chatServer = Server().serveForever()
    else:
        chatServer = Server(socket.gethostbyname(socket.gethostname())).serveForever()
