'''
KTN-project 2013 / 2014
Very simple server implementation that should serve as a basis
for implementing the chat server
'''

# Import modules we need for server
import re
import json
import socket
import select
import Queue

class Server(object):

    def __init__(self, HOST='localhost', PORT=9999):
        self.debug = True
        self.readableClients = []
        self.writeableClients = []
        self.recvBuff = 1024
        self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.serverSocket.setblocking(0)
        self.serverSocket.bind((HOST, PORT))
        self.serverSocket.listen(10)
        self.msgQ = {}
        if self.debug: print 'Server.__init__: CHAT SERVER STARTED ON (%s, %s)' % (HOST, PORT)


    def serveForever(self):
        self.readableClients.append(self.serverSocket)
        if self.debug: print 'Server.serveForever: SERVING FOREVER' 

        while True:
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
                else:
                    data = sock.recv(self.recvBuff).strip()
                    if self.debug: print 'Server.serveForever: RECIEVED DATA FROM (%s, %s)' % addr
                    if data:
                        if self.debug: print 'Server.serveForever: CONNECTED SOCKETS ' + str(len(self.readableClients))
                        print 'Server.serveForever: RECEIVED %s FROM %s' % (data, sock.getpeername())
                        self.msgQ[sock].put(data)
                        if sock not in self.writeableClients:
                            self.writeableClients.append(sock)
                    else:
                        if self.debug: print 'Server.serveForever: CONNECTION LOST WITH (%s, %s) AFTER READING NO DATA' % addr
                        print 'Server.serveForever: CLIENT (%s, %s) DISCONNECTED' % addr
                        if sock in self.writeableClients:
                            self.writeableClients.remove(sock)
                        self.readableClients.remove(sock)
                        sock.close()

            for sock in readyToWrite:
                try:
                    nextMsg = self.msgQ[sock].get_nowait()
                except Queue.Empty:
                    print 'Server.serveForever: MESSAGE QUEUE FOR (%s, %s) IS EMPTY' % addr
                    self.writeableClients.remove(sock)
                else:
                    print 'Server.serveForever: SENDING %s TO %s' % (nextMsg, str(sock.getpeername()))
                    sock.send(nextMsg)
            
            for sock in errorSockets:
                print 'Server.serveForever: HANDLING EXCEPTION FOR (%s, %s)' % sock.getpeername()
                self.readableClients.remove(sock)
                if sock in self.writeableClients:
                    self.writeableClients.remove(sock)
                sock.close()

        self.serverSocket.close()

    #def broadcastMessage(self, sock, message):
    #    readyToRead, readyToWrite, errorSockets = select.select([], self.writeableClients, [], 0)
    #    for socket in readyToWrite:
    #        if socket != self.serverSocket and socket != sock:
    #            try:
    #                socket.sendall(message)
    #            except : 
    #                self.broadcastMessage(socket, '<-- server (%s, %s) has quit the chat' % addr)
    #                self.readableClients.remove(socket)
    #                self.writeableClients.remove(socket)
    #                socket.close()
    
# Main method
if __name__ == "__main__":
    chatServer = Server().serveForever()
