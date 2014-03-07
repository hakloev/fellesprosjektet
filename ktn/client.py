'''
KTN-project 2013 / 2014
'''
import sys
import socket
import json
import threading
import MessageWorker

class Client(object):

    def __init__(self, debug=True):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = debug
        self.loggedIn = False

    def start(self, host, port):
        # Initate the connection
        self.connection.connect((host, port))
        
        print 'CONNECTION ESTABLISHED WITH SERVER'
        print 'EXIT [\q], LOGOUT WITH [\close]'
        
        self.messageWorker = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        self.messageWorker.start()
        
       # while not self.loggedIn:
        self.login() 

        while True:
            #self.serve()
            data = sys.stdin.readline().strip()
            if data.upper() == '\Q':
                self.force_disconnect()
                break
            elif data.upper() == '\CLOSE':
                self.logout()
                break
            self.send(self.createJSON(data, 'message'))

    def message_received(self, message, connection):
        if self.debug: print 'Client.message_received: RECEIVED MESSAGE'
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
        data = raw_input('Enter username: ') 
        if data.upper() == '\Q':
            self.force_disconnect()
        elif data.upper() == '\CLOSE':
            self.logout()
        self.send(self.createJSON(data, 'login'))
        

    def logout(self):
        pass

    def createJSON(self, data, reqType):
        if reqType == 'login':
            data = {'request': reqType, 'username': data}
        elif reqType == 'logout':
            data = {'request': reqType}
        elif reqType == 'message':
            data = {'request': reqType, reqType: data}
        else:
            if self.debug: print 'Client.createJSON: UNEXPECTED REQUEST'
        return json.dumps(data)
            
    def handleJSON(self, data):
        data = json.loads(data)
        if data['response'] == 'login':
            if 'error' in data:
                if self.debug: print 'Client.handleJSON: INVALID LOGIN'
                print data['error']
            else:
                if self.debug: print 'Client.handleJSON: LOGGED IN'
                print 'LOGGED IN WITH: ' + data['username']
                self.loggedIn = True
        elif data['response'] == 'logout':
            if self.debug: print 'Client.handleJSON: LOGOUT'
            print data['logout']
        elif data['response'] == 'message':
            print data['message'] 
        else:
            if self.debug: print 'Client.handleJSON: UNEXPECTED JSON'
            pass

if __name__ == "__main__":
    client = Client()
    ip = raw_input('Choose IP: [''] for localhost: ')
    if str(ip) == '':
        client.start('localhost', 9999)
    else: 
        client.start(ip, 9999)
