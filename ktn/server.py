'''
KTN-project 2013 / 2014
Very simple server implementation that should serve as a basis
for implementing the chat server
'''

import SocketServer
import json
import re
import socket

'''
The RequestHandler class for our server.

It is instantiated once per connection to the server, and must
override the handle() method to implement communication to the
client.
'''

clients = {}
usernames = []

class CLientHandler(SocketServer.BaseRequestHandler):
    
    debug = True

    def handle(self):
        # Get a reference to the socket object
        self.connection = self.request
        # Get the remote ip adress of the socket
        self.ip = self.client_address[0]
        # Get the remote port number of the socket
        self.port = self.client_address[1]
        # Print information about client
        print 'Client connected @' + self.ip + ':' + str(self.port)

        # Adding client do dictionary
        clients[self.port] = self
        if self.debug: 
            print 'Connected clients:', 
            for ID in clients: 
                print str(clients[ID].port),  
            print 

        while True:
            # Wait for data from the client
            data = self.connection.recv(1024).strip()
            # Check if the data exists
            # (recv could have returned due to a disconnect)
            if data:
                self.handleJSON(data)
                print self. ip + ':' + str(self.port) + ' requested ' + data 
                # Return the string in uppercase
                #for ID in clients:
                #    client = clients[ID]
                #    client.connection.sendall(data.upper())
            else:
                print 'Client disconnected @' + self.ip + ':' + str(self.port)
                del clients[self.port]
                break

    def handleJSON(self, data):
        data = json.loads(data)
        if data['request'] == 'login':
            if not data['username'] in usernames:
                if re.search("^[a-zA-Z0-9_]{0,15}$", data['username']) is not None:
                    usernames.append(data['username'])
                    res = {'response': 'login', 'username': data['username']}
                    self.sendResponse(res,'all','')
                else:
                    res = {'response': 'login', 'error': 'Invalid username!', 'username': data['username']}
                    self.sendResponse(res,'one','')
            else:  
                res = {'response': 'login', 'error': 'Name already taken!', 'username': data['username']}
                self.sendResponse(res,'one','')
        elif data['request'] == 'logout':
            if not data['username'] in usernames:
                res = {'response': 'login', 'error': 'Not logged in!', 'username': data['username']}
                self.sendResponse(res,'one','')
            else: 
                usernames.remove(data['username'])
                res = {'response': 'logout', 'username': data['username']}
                self.sendResponse(res,'all','')
            print 'handleJSON: logout'
        elif data['request'] == 'message':
            res = {'response': 'message', 'message': data['message']}
            self.sendResponse(res,'all', '')

        else:
            pass

    def sendResponse(self, res, type, origin):
        print 'sendResponse()'
        res = json.dumps(res)
        if type == 'all': 
            for ID in clients:
                client = clients[ID]
                client.connection.sendall(res)
        elif type == 'one':
            client.connection.sendall(res)
            pass
        elif type == 'allxone':
            #har ikke mulighet til aa finne lokal sender enda
            pass



        pass 


'''
This will make all Request handlers being called in its own thread.
Very important, otherwise only one client will be served at a time
'''

class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    pass    

if __name__ == "__main__":
    ip = raw_input("Choose IP: [localhost/foreign] ")
    if str(ip).upper() == 'LOCALHOST':
        HOST = 'localhost'
    else:
        HOST = socket.gethostbyname(socket.gethostname())
    print "Using IP: " + HOST
    PORT = 9999

    # Create the server, binding to localhost on port 9999
    server = ThreadedTCPServer((HOST, PORT), CLientHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()
