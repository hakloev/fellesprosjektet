'''
KTN-project 2013 / 2014
'''
import sys
import socket
import json
import threading
import MessageWorker
from Colors import getColor

class Client(object):

    debug = None
    loggedIn = None

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = False 
        self.loggedIn = False

    def start(self, host, port):
        # Initate the connection
        self.connection.connect((host, port))
        
        print getColor('O') + 'Connection esblished with server'
        print getColor('O') + 'Exit with ' + getColor('R') + '[q]' + getColor('O') + ', logout with ' + getColor('R') + '[logout]'
        
        # Login here before threading?
        #self.login()

        self.messageWorker = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        self.messageWorker.start()

        while True:
            msg = sys.stdin.readline().strip()
            #msg = raw_input(getColor('G') + 'client | ' + getColor('W'))
            if msg.upper() == 'Q':
                self.force_disconnect()
                break
            elif msg.upper() == 'LOGOUT':
                self.logout()
                break
            #msg = self.createJSONMessage(msg)
            self.send(msg)
            #received_data = self.connection.recv(1024).strip()
            #print 'Received from server: ' + received_data
        #self.connection.close()

    def message_received(self, message, connection):
        if self.debug: print getColor('P') + 'Client: message_received'
        #self.handleJSON(message)
        sys.stdout.write(message + '\n')
        sys.stdout.flush()
        
    def connection_closed(self, connection):
        print getColor('R') +'Will terminate'
        sys.exit(0)

    def send(self, data):
        try:
            self.connection.sendall(data)
        except socket.error:
            print getColor('R') + 'Connection to server broken'
            self.connection_closed(self.connection)

    def force_disconnect(self):
        self.connection.close()

    def login(self):
        while not self.loggedIn:
            uname = raw_input(getColor('G') + 'Choose your username: ' + getColor('W'))
            req = {'request': 'login', 'username': str(uname)}
            req = json.dumps(req)
            self.send(req)
            
            data = self.connection.recv(1024).strip()
            data = json.loads(data)
            if data: 
                if data['response'] == 'login':
                    if 'error' in data:
                        if self.debug: print getColor('P') + 'login: invalid login'
                        print getColor('R') + data['error']
                    else:
                        self.loggedIn = True
                        self.uname = uname
                        print getColor('O') + 'Logged in with username: ' + getColor('W') + uname
                        if self.debug: print getColor('P') + 'login: loggedIn'
                        #print data['messages']

    def logout(self):
        if self.loggedIn:
            req = {'request': 'logout', 'username': self.uname}
            
    def handleJSON(self, data):
        data = json.loads(data)
        if data['response'] == 'login':
            if 'error' in data:
                if self.debug: print getColor('P') + 'handleJSON: invalid login'
                print data['error']
            else:
                self.loggedIn = True
                if self.debug: print getColor('P') + 'handleJSON: loggedIn'
                print getColor('C') + data['messages'] + getColor('W')
        elif data['response'] == 'logout':
            if self.debug: print getColor('P') + 'handleJSON: logout'
        elif data['response'] == 'message':
            print getColor('C') + data['message'] + getColor('W')
        else:
            pass

    def createJSONMessage(self, msg):
        msg = {'request': 'message', 'message': msg}
        msg = json.dumps(msg)
        return msg
        
if __name__ == "__main__":
    client = Client()
    ip = raw_input(getColor('O') + 'Choose IP: ' + getColor('R') + "['']" + getColor('O') + ' for localhost: ' + getColor('W'))
    if str(ip) == '':
        client.start('localhost', 9999)
    else: 
        client.start(ip, 9999)
