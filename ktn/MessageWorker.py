'''
KTN-project 2013 / 2014
Python daemon thread class for listening for events on
a socket and notifying a listener of new messages or
if the connection breaks.

A python thread object is started by calling the start()
method on the class. in order to make the thread do any
useful work, you have to override the run() method from
the Thread superclass. NB! DO NOT call the run() method
directly, this will cause the thread to block and suspend the
entire calling process' stack until the run() is finished.
it is the start() method that is responsible for actually
executing the run() method in a new thread.
'''
from threading import Thread


class ReceiveMessageWorker(Thread):

    debug = None

    def __init__(self, listener, connection):
        Thread.__init__(self)
        self.daemon = True
        self.listener = listener
        self.connection = connection
        self.debug = True

    def run(self):
        if self.debug: print 'ReceiveMessageWorker: run'
        while True:
            try:
                msg = self.connection.recv(1024).strip()
                if not msg:
                    break
                self.listener.message_received(msg, self.connection)
            except:
                break
        pass



