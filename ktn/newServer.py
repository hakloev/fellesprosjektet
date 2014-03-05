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

class Server(object):

    def __init__(self, HOST='localhost', PORT=9999):
        self.debug = True
        self.connections = []
        self.write = []
        self.recvBuff = 1024
        self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.serverSocket.bind((HOST, PORT))
        self.serverSocket.listen(10)
        print 'Server.__init__: CHAT SERVER STARTED ON PORT ' + str(PORT)

    def serveForever(self):
        self.connections.append(self.serverSocket)
        print 'Server.serveForever: SERVING FOREVER' 
        serving = True

        while serving:

            try:
                readyToRead, readyToWrite, errorSockets = select.select(self.connections, self.write, [])
            except socket.error, e:
                print 'Server.serveForever: SOCKET ERROR'
                continue
            except select.error, e:
                print 'Server.serveForever: SELECT ERROR'
                continue

            for sock in readyToRead:
                try:
                    if sock == self.serverSocket:
                        sockfd, addr = self.serverSocket.accept()
                        self.connections.append(sockfd)
                        print 'Client (%s, %s) connected' % addr
                        self.broadcastMessage(sockfd, '--> server (%s@%s) has joined the chat\n' % addr)
                    else:
                        data = sock.recv(self.recvBuff).strip()
                        print 'Server.serveForever: RECIEVED DATA SAID ' + data
                        if data:
                            if self.debug: print 'Server.serveForever: CONNECTED SOCKETS ' + str(len(self.connections))
                            self.broadcastMessage(sock, 'server | ' + data)
                            if sock not in self.write:
                                self.write.append(sock) 
                        else:
                            print 'Server.serveForever: CONNECTION LOST'
                            if sock in self.write:
                                self.write.remove(sock)
                            self.connections.remove(sock)
                            sock.close()
                except:
                    print 'WHY'
                    print 'Client (%s, %s) disconnected' % addr
                    self.broadcastMessage(sock, '<-- server (%s, %s) has quit the chat\n')
                    sock.close()
                    self.connections.remove(sock)
                    continue        
                    

        self.serverSocket.close()

    def broadcastMessage(self, sock, message):
        for socket in self.write:
            if socket != self.serverSocket and socket != sock:
                try:
                    print 'Server.broadcastMessage: SENDING MESSAGE to ' + str(socket)
                    socket.sendall(message)
                except: 
                    socket.close()
                    self.connections.remove(socket)

    
# Main method
if __name__ == "__main__":
    chatServer = Server().serveForever()
