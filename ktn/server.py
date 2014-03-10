#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
KTN-project 2013 / 2014
Very simple server implementation 

Because of troubles with implementing multithreaded server
we went for implementing a multiplexed server instead. 
This server accepts sockets and puts incoming messages
in a queue. It then sends this messages to the sockets
with free space in the write buffer
'''

# Import modules we need for the server
import re
import json
import socket
import select
import Queue
import time

class Server(object):

    # Initiate server with gives arguments or default arguments
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

    # The main method in the server, loops as long as program runs
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
            
            # Sockets ready to be read from
            for sock in readyToRead:
                # If it is a new connection, add it to the readable list and create message queue
                if sock == self.serverSocket:
                    sockfd, addr = self.serverSocket.accept() # Accept connection
                    sockfd.setblocking(0) # Set as non-blocking
                    self.readableClients.append(sockfd) # Add it to the readable list
                    print 'Server.serveForever: CLIENT (%s, %s) CONNECTED' % addr
                    self.msgQ[sockfd] = Queue.Queue() # Create message queue
                    # Will not broadcast to other clients that client is connected before a valid username is entered
                # Existing connection 
                else:
                    data = sock.recv(self.recvBuff).strip() # Read data from sockete
                    if self.debug: print 'Server.serveForever: RECIEVED DATA FROM (%s, %s)' % addr
                    # If it recieved data 
                    if data:
                        if self.debug: print 'Server.serveForever: CONNECTED SOCKETS ' + str(len(self.readableClients))
                        print 'Server.serveForever: RECEIVED %s FROM %s' % (data, sock.getpeername())
                        # If the socket isn't in the writeable list, append it
                        if sock not in self.writeableClients:
                            self.writeableClients.append(sock)
                        self.handleJSON(data, sock) # This is where messages are added to the message queue
                    # No data recieved, the connection is lost, so we must remove the socket
                    else:
                        if self.debug: print 'Server.serveForever: CONNECTION LOST WITH (%s, %s) AFTER READING NO DATA' % addr
                        print 'Server.serveForever: CLIENT (%s, %s) DISCONNECTED' % addr
                        # Broadcast that client lost connection
                        self.broadcastMessageToOthers(sock, json.dumps({'response': 'message', 'message': '%s  SERVER | %s has quit the chat (connection lost or quit client)' % (time.strftime("%H:%M:%S"), self.usernames[sock])})) 
                        # If socket in writeable list, remove it
                        if sock in self.writeableClients:
                            self.writeableClients.remove(sock)
                        # Remove the socket from readable list    
                        self.readableClients.remove(sock)
                        # Remove the username, it's now free for other users to use
                        if sock in self.usernames.iterkeys():
                            if self.debug: print 'Server.serveForever: REMOVED %s FROM USERNAMES' % self.usernames[sock]
                            del self.usernames[sock]
                        # Close connection
                        sock.close()
            
            # Sending all messages in queue to everyone except origin sender. Sockets must have free space in write buffer
            for sock in readyToWrite:
                try:
                    nextMsg = self.msgQ[sock].get_nowait() # Get the sockets next message in queue
                except Queue.Empty:
                    print 'Server.serveForever: MESSAGE QUEUE FOR (%s, %s) IS EMPTY' % addr
                    self.writeableClients.remove(sock) # Message queue is empty, so we remove the client from writeable list
                else:
                    self.broadcastMessageToOthers(sock, nextMsg) # Sending JSON-response to all other than origin sender
            
            # Remove sockets with error 
            for sock in errorSockets:
                print 'Server.serveForever: HANDLING EXCEPTION FOR (%s, %s)' % sock.getpeername()
                self.readableClients.remove(sock)
                if sock in self.writeableClients:
                    self.writeableClients.remove(sock)
                sock.close()
        self.serverSocket.close()

    # Method to handle the JSON-requests, mostly self explained! Creates JSON-response and send to correct client(s)
    def handleJSON(self, data, sock):
        data = json.loads(data)
        if data['request'] == 'login':
            if not data['username'] in self.usernames.itervalues():
                if re.search("^[a-zA-ZæøåÆØÅ0-9_]{0,15}$", data['username']) is not None:
                    if self.debug: print 'Server.handleJSON: USERNAME %s ADDED' % data['username']
                    self.usernames[sock] = data['username']
                    self.broadcastMessageToOne(sock, json.dumps({'response': 'login', 'username': data['username']}))
                    self.msgQ[sock].put(json.dumps({'response': 'message', 'message': '%s  SERVER | %s joined the chat' % (time.strftime("%H:%M:%S"), data['username'])}))
                else:
                    if self.debug: print 'Server.handleJSON: INVALID USERNAME %s' % data['username']
                    self.broadcastMessageToOne(sock, json.dumps({'response': 'login', 'username': data['username'], 'error': 'Invalid username!'}))
            else:
                if self.debug: print 'Server.handleJSON: USERNAME %s ALREADY TAKEN' % data['username'] 
                self.broadcastMessageToOne(sock, json.dumps({'response': 'login', 'username': data['username'], 'error': 'Name already taken!'}))
        elif data['request'] == 'logout':
            if sock in self.usernames.keys():
                if self.debug: print 'Server.handleJSON: USERNAME %s REQUESTED LOGOUT' % self.usernames[sock]
                self.broadcastMessageToOne(sock, json.dumps({'response': 'logout', 'username': self.usernames[sock]}))
                self.msgQ[sock].put(json.dumps({'response': 'message', 'message': '%s  SERVER | %s logged out' % (time.strftime("%H:%M:%S"), self.usernames[sock])}))
                del self.usernames[sock]
        elif data['request'] == 'message':
            if self.debug: print 'Server.handleJSON: SENDING MESSAGE FROM %s' % str(sock.getpeername())
            try:
                self.msgQ[sock].put(json.dumps({'response': 'message', 'message': '%s  %s said | %s' % (time.strftime("%H:%M:%S"), self.usernames[sock], data['message'])}))
            except KeyError:
                if self.debug: print 'Server.handleJSON: KeyError, INVALID USERNAME/NOT LOGGED IN'
                self.broadcastMessageToOne(sock, json.dumps({'response': 'login', 'error': '%s  SERVER | Please log in' % time.strftime("%H:%M:%S")}))

        else:
            if self.debug: print 'Server.handleJSON: UNEXPECTED JSON'
    
    # Sending message to all but origin client             
    def broadcastMessageToOthers(self, sock, message):
        if self.debug: print 'Server.broadcastMessageToOthers: BROADCAST CALLED'
        for socket in self.readableClients:
            if socket != self.serverSocket and socket != sock and socket in self.usernames.keys():
                print 'Server.broadcastMessageToOne: SENDING %s TO %s' % (message, str(socket.getpeername()))
                socket.sendall(message)
    
    # Sending message to origin client
    def broadcastMessageToOne(self, sock, message):
        if self.debug: print 'Server.broadcastMessageToOne: BROADCAST CALLED'
        sock.sendall(message)
    
# Main method
if __name__ == "__main__":
    ip = raw_input('''Choose IP: [''] for localhost, [out] for router-IP: ''') # We only need localhost or ip given by router, because remote connections must be handled/forwarded by router (DMZ etc) 
    if str(ip) == '': # User wants to start server on localhost
        chatServer = Server().serveForever()
    else: # User wants to start server on IP given by router (need DMZ or equal to accept from outside local network)
        chatServer = Server(socket.gethostbyname(socket.gethostname())).serveForever()
