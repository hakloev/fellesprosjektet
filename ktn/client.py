'''
KTN-project 2013 / 2014
'''
import sys
import socket
import json
import threading
import MessageWorker

class Client(object):

    debug = None
    loggedIn = None

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = True 
        self.loggedIn = False

    def start(self, host, port):
        # Initate the connection
        self.connection.connect((host, port))
        
        print 'Connection esblished with server'
        print 'Exit with [q], logout with [logout]'
        
        # Login here before threading?
        self.login()

        self.messageWorker = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        self.messageWorker.start()

        while True:
            print
            msg = raw_input()
            if msg.upper() == 'Q':
                self.force_disconnect()
                break
            elif msg.upper() == 'LOGOUT':
                self.logout()
                break
            msg = self.createJSONMessage(msg)
            self.send(msg)
            #received_data = self.connection.recv(1024).strip()
            #print 'Received from server: ' + received_data
        #self.connection.close()

    def message_received(self, message, connection):
        if self.debug: print 'Client: message_received'
        self.handleJSON(message)

    def connection_closed(self, connection):
        print 'Will terminate'
        sys.exit(0)

    def send(self, data):
        try:
            self.connection.sendall(data)
        except socket.error:
            print 'Connection to server broken'
            self.connection_closed(self.connection)

    def force_disconnect(self):
        self.connection.close()

    def login(self):
        while not self.loggedIn:
            uname = raw_input('Choose your username: ')
            req = {'request': 'login', 'username': str(uname)}
            req = json.dumps(req)
            self.send(req)
            
            data = self.connection.recv(1024).strip()
            data = json.loads(data)
            if data: 
                if data['response'] == 'login':
                    if 'error' in data:
                        if self.debug: print 'login: invalid login'
                        print data['error']
                    else:
                        self.loggedIn = True
                        if self.debug: print 'login: loggedIn'
                        #print data['messages']

    def logout(self):
        pass

    def handleJSON(self, data):
        data = json.loads(data)
        if data['response'] == 'login':
            if 'error' in data:
                if self.debug: print 'handleJSON: invalid login'
                print data['error']
            else:
                self.loggedIn = True
                if self.debug: print 'handleJSON: loggedIn'
                print data['messages']
        elif data['response'] == 'logout':
            if self.debug: print 'handleJSON: logout'
        elif data['response'] == 'message':
            print data['message']
        else:
            pass

    def createJSONMessage(self, msg):
        msg = {'request': 'message', 'message': msg}
        msg = json.dumps(msg)
        return msg
        
if __name__ == "__main__":
    client = Client()
    ip = raw_input("Velg IP: [''] for localhost: ")
    if str(ip) == '':
        client.start('localhost', 9999)
    else: 
        client.start(ip, 9999)
