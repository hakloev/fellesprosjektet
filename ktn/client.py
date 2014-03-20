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
    
    # Init method for the Client
    def __init__(self, debug=False):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = debug
        self.username = None
        self.printWelcomeMessage()

    def start(self, HOST='localhost', PORT=9999):
        # Initate the connection
        self.connection.connect((HOST, PORT))
        
        self.printConnectionSuccess()

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
                    print '|  %s  CLIENT | You are not logged in                             |' % time.strftime("%H:%M:%S")
                else:
                    self.logout()
            else:
                self.send(self.createJSON(data, 'message'))
    
    def printWelcomeMessage(self):
        print '*==================================================================================*'
        print '|                                                                                  |'
        print '|                             WELCOME TO THE CHATCLIENT                            |'
        print '|                                      GROUP 7                                     |'
        print '|                                                                                  |'
        print '*==================================================================================*'
        print '|                                                                                  |'
        #string = '|                                                                                  |'
        #print str(len(string)) + ' lang'
    
    def printConnectionSuccess(self):
        print '|                                                                                  |'
        print '|                      CONNECTION ESTABLISHED WITH SERVER                          |'
        print '|                                                                                  |'
        print '|         EXIT [/q], LOGIN WITH [/login *username*], LOGOUT WITH [/logout]         |'
        print '|                                                                                  |'
        print '*==================================================================================*'
        print '|                                                                                  |'

    # Called from messageWorker when message received from server
    def message_received(self, message, connection):
        if self.debug: print 'Client.message_received: RECEIVED MESSAGE'
        if self.debug: print 'Client.message_received: ' + message
        self.handleJSON(message)
        
    # Called when connection to server is broken    
    def connection_closed(self, connection):
        print '|                                                                                  |'
        print '|                           CONNECTION TO SERVER LOST                              |'
        print '|                                WILL TERMINATE                                    |'
        print '|                                                                                  |'
        print '|                                   GOOD BYE!                                      |'
        print '*==================================================================================*'
        raw_input('')
        sys.exit(0)
    
    # Method that sends data to the server
    def send(self, data):
        try:
            self.connection.sendall(data)
        except socket.error:
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

    # Create JSON-request to send to the server, simpler than the one in server.py due to easier requests 
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
                print data['message']
                self.username = data['username']
        elif data['response'] == 'logout':
            if self.debug: print 'Client.handleJSON: LOGOUT'
            print data['message']
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
    print '|             CHOOSE IP: [ ] for localhost, [out] for router-IP                    |'
    ip = raw_input()
    if str(ip) == '': # User wants to start on localhost
        client.start()
    elif str(ip).upper() == 'OUT': # User wants to use the IP given by local router
        client.start(socket.gethostbyname(socket.gethostname()))
    else: # User wants to connect to a given IP
        client.start(ip)
