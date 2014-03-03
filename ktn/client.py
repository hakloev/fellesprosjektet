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

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.debug = True  

    def start(self, host, port):
        # Initate the connection
        self.connection.connect((host, port))
        
        print 'Connection esblished with server'
        print 'Exit with [q]'
        # Login here before threading?
       
        self.messageWorker = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        self.messageWorker.start()


        while True:
            msg = raw_input()
            if msg.upper() == 'Q':
                self.force_disconnect()
                break
            self.send(msg)
            #received_data = self.connection.recv(1024).strip()
            #print 'Received from server: ' + received_data
        #self.connection.close()

    def message_received(self, message, connection):
        if self.debug: print 'Client: message_received'
        print message

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
        pass


if __name__ == "__main__":
    client = Client()
    ip = raw_input("Velg IP: [''] for localhost: ")
    if str(ip) == '':
        client.start('localhost', 9999)
    else: 
        client.start(ip, 9999)
