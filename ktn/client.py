#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
KTN-project 2013 / 2014
'''
import sys
import socket
import json
import threading
import MessageWorker
import time

class Client(object):
    
    # Init method for class
    def __init__(self, debug=False):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = debug
        self.username = None

    def start(self, HOST='localhost', PORT=9999):
        # Initate the connection
        self.connection.connect((HOST, PORT))
        
        print 'CONNECTION ESTABLISHED WITH SERVER'
        print 'EXIT [/q], LOGIN WITH [/login *username*], LOGOUT WITH [/logout]'
        
        # Create a messageworker thread to listen for incoming messages from server
        self.messageWorker = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        self.messageWorker.start()

        # Loops forever, handles user input
        while True:
            data = raw_input()
            data = data.upper()
            if data == '/Q':
                self.force_disconnect()
                break
            elif data.startswith('/LOGIN'):
                self.login(data[data.find('/LOGIN') + 7:])
            elif data == '/LOGOUT':
                if self.username == None:
                    print '%s  CLIENT | You are not logged in' % time.strftime("%H:%M:%S")
                else:
                    self.logout()
            else:
                self.send(self.createJSON(data, 'message'))

    # Called from messageWorker when message received from server
    def message_received(self, message, connection):
        if self.debug: print 'Client.message_received: RECEIVED MESSAGE'
        if self.debug: print 'Client.message_received: ' + message
        self.handleJSON(message)
        
    # Called when connection to server is broken    
    def connection_closed(self, connection):
        print 'Will terminate'
        sys.exit(0)
    
    # Method that sends data to the server
    def send(self, data):
        try:
            self.connection.sendall(data)
        except socket.error:
            print 'Connection to server broken'
            self.connection_closed(self.connection)

    # If the user wants to quit, we force a disconnect
    def force_disconnect(self):
        self.connection.close()

    # If the user wants to log in
    def login(self, data):
        if self.debug: print 'Client.login: REQUESTING LOGIN WITH USERNAME %s' % data
        self.send(self.createJSON(data, 'login'))

    # Same as the other, but for logout
    def logout(self):
        if self.debug: print 'Client.logout: REQUESTING LOGOUT FROM USERNAME %s' % self.username 
        self.send(self.createJSON('', 'logout'))

    # Create JSON-request to send to the server 
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
            
    # Handle JSON-response from server
    def handleJSON(self, data):
        data = json.loads(data)
        if data['response'] == 'login':
            if 'error' in data:
                if self.debug: print 'Client.handleJSON: INVALID LOGIN'
                print data['error']
            else:
                if self.debug: print 'Client.handleJSON: LOGGED IN'
                print '%s  CLIENT | Logged in with: %s' % (time.strftime("%H:%M:%S"), data['username'])
                self.username = data['username']
        elif data['response'] == 'logout':
            if self.debug: print 'Client.handleJSON: LOGOUT'
            print '%s  CLIENT | You are logged out' % time.strftime("%H:%M:%S")
            self.username = None
        elif data['response'] == 'message':
            if self.debug: print 'Client.handleJSON: MESSAGE'
            print data['message'] 
        else:
            if self.debug: print 'Client.handleJSON: UNEXPECTED JSON'
            pass

# Main method
if __name__ == "__main__":
    client = Client()
    ip = raw_input('''Choose IP: [''] for localhost, [out] for router-IP: ''')
    if str(ip) == '': # User wants to start on localhost
        client.start()
    elif str(ip).upper() == 'OUT': # User wants to use the IP given by local router
        client.start(socket.gethostbyname(socket.gethostname()))
    else: # User wants to connect to a given IP
        client.start(ip)
