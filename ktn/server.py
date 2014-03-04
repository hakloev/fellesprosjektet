'''
KTN-project 2013 / 2014
Very simple server implementation that should serve as a basis
for implementing the chat server
'''

import SocketServer
import json
import re
import socket
import threading
import select

'''
This will make all Request handlers being called in its own thread.
Very important, otherwise only one client will be served at a time
'''
class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    daemon_threads = True
    allow_reuse_address = True
    def __init__(self, server_address, RequestHandlerClass):
        SocketServer.TCPServer.__init__(self, server_address, RequestHandlerClass)

'''
The RequestHandler class for our server.

It is instantiated once per connection to the server, and must
override the handle() method to implement communication to the
client.
'''
class CLientHandler(SocketServer.BaseRequestHandler):

    debug = None
    socketHandler = None

    def __init__(self, request, client_address, server):
        SocketServer.BaseRequestHandler.__init__(self, request, client_address, server)

    def handle(self):
        print 'Handle called from: ' + str(self)
        # Get a reference to the socket object
        self.connection = self.request
        # Get the remote ip adress of the socket
        self.ip = self.client_address[0]
        # Get the remote port number of the socket
        self.port = self.client_address[1]
        # Print information about client
        print 'Client connected @' + self.ip + ':' + str(self.port)

        ready_to_read, ready_to_write, in_error = select.select([], [], [], None)
    
        print 'ready to read' + str(ready_to_read)
        
        while True:
            print 'ClientHandler: Active threads: ' + str(threading.active_count())
            print 'ClientHandler: Current Thread: ' + str(threading.current_thread())

            if len(ready_to_read) == 1 and ready_to_read[0] == self.request:
                # Wait for data from the client
                data = self.request.recv(1024).strip()
                print data
                # Check if the data exists
                # (recv could have returned due to a disconnect)
                if data:
                    print self.ip + ':' + str(self.port) + ' requested ' + data 
                    self.socketHandler.handleJSON(data, self)
                else:
                    print 'Client disconnected @' + self.ip + ':' + str(self.port)
                    break
    
    def sendJSON(self, data):
        print 'Send all data from ' + self.ip + ':' + str(self.port)
        self.request.sendall(data)


'''
Class for holding information about sockets, usernames and jsonhandling
'''
class SocketHandler:

    usernames = None
    clients = None
    debug = None

    '''
    TANKEN ER:
    Alle kaller sockethandler for persing av json, og har samme referanse til sockethandler
    de kaller sockethandler med en instans av seg selv, altsaa vil den holde oversikt og sende til alle clienthandler objekter 
    da vil alle clienthandlers muligens ha en send funksjon, usikker paa denne
    '''

    def __init__(self):
        self.usernames = [] 
        self.clients = {}
        debug = True
        pass

    def handleJSON(self, data, socket):
        data = json.loads(data)
        if data['request'] == 'login':
            if not data['username'] in self.clients.itervalues():
                if re.search("^[a-zA-Z0-9_]{0,15}$", data['username']) is not None:
                    self.usernames.append(data['username'])
                    print data['username'] + ' added to self.clients'
                    res = {'response': 'login', 'username': data['username']}
                    self.clients[socket]  = data['username']
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
            print 'message requested'
            res = {'response': 'message', 'message': data['message']}
            self.sendResponse(res,'all', '')
        else:
            pass

    def sendResponse(self, res, type, origin):
        if self.debug: print 'sendResponse'
        res = json.dumps(res)
        if type == 'all': 
            print self.clients
            for socket in self.clients.iterkeys():
                print socket
                socket.sendJSON(res)
        elif type == 'one':
            pass
        elif type == 'allxone':
            #har ikke mulighet til aa finne lokal sender enda
            pass
        pass 

# Main method
if __name__ == "__main__":
    ip = raw_input("Choose IP [localhost/foreign]: ")
    if str(ip).upper() == 'FOREIGN':
        HOST = socket.gethostbyname(socket.gethostname())
    else:
        HOST = 'localhost'    
    print "Using IP: " + HOST
    PORT = 9999

    # Give all CLientHandlers the same instance of SocketHandler
    CLientHandler.socketHandler = SocketHandler()

    CONNECTION = []

    # Create the server, binding to localhost on port 9999
    server = ThreadedTCPServer((HOST, PORT), CLientHandler)

    CONNECTION.append(server)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()
