'''
KTN-project 2013 / 2014
'''
import socket
import json
import MessageWorker

class Client(object):

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.messageThread = MessageWorker.ReceiveMessageWorker(self, self.connection)    
        
        #self.messageThread.start() 

    def start(self, host, port):
        self.connection.connect((host, port))
        self.messageThread.start()

        while True:
            msg = raw_input('\nNew message: ')
            try:
                self.send(msg)
            except socket.error:
                print 'Connection Error'
                self.connection.connect((host, port))
                self.send(msg)
            #received_data = self.connection.recv(1024).strip()
            #print 'Received from server: ' + received_data
        #self.connection.close()

    def message_received(self, message, connection):
        print 'message_received called'
        print message

    def connection_closed(self, connection):
        pass

    def send(self, data):
        self.connection.sendall(data)

    def force_disconnect(self):
        self.connection.close()
        pass


if __name__ == "__main__":
    client = Client()
    client.start('localhost', 9999)
