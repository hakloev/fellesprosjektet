'''
KTN-project 2013 / 2014
Very simple server implementation that should serve as a basis
for implementing the chat server
'''

# Import modules we need for server
import re
import json
import socket
import threading
import select

def broadcastMessage(sock, message):
    for socket in connections:
        if socket != serverSocket and socket != sock:
            try:
                socket.send(message)
            except: 
                socket.close()
                connections.remove(socket)

def sendResponse(res, type, origin):
        print 'sendResponse'
        res = json.dumps(res)
        if type == 'all': 
            broadcastMessage(origin, res)    
        elif type == 'one':
            pass
        elif type == 'allxone':
            #har ikke mulighet til aa finne lokal sender enda
            pass
        pass 

def handleJSON(data, sock):
        data = json.loads(data)
        if data['request'] == 'login':
            if not data['username'] in usernames.itervalues():
                if re.search("^[a-zA-Z0-9_]{0,15}$", data['username']) is not None:
                    usernames[data['username']] = sock
                    print data['username'] + ' added to usernames'
                    res = {'response': 'login', 'username': data['username']}
                    sock.send(json.dumps(res))
                    #sendResponse(res,'all', sock)
                else:
                    res = {'response': 'login', 'error': 'Invalid username!', 'username': data['username']}
                    sendResponse(res,'one', sock)
            else:  
                res = {'response': 'login', 'error': 'Name already taken!', 'username': data['username']}
                sendResponse(res,'one', sock)
        elif data['request'] == 'logout':
            if not data['username'] in usernames:
                res = {'response': 'login', 'error': 'Not logged in!', 'username': data['username']}
                sendResponse(res,'one', sock)
            else: 
                del usernames[data['username']]
                res = {'response': 'logout', 'username': data['username']}
                sendResponse(res,'all', sock)
            print 'handleJSON: logout'
        elif data['request'] == 'message':
            print 'message requested'
            res = {'response': 'message', 'message': data['message']}
            sendResponse(res,'all', sock)
        else:
            pass

# Main method
if __name__ == "__main__":
    debug = False

    connections = []
    usernames = {}

    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    ip = raw_input("Choose IP [localhost/foreign]: ")
    if str(ip).upper() == 'FOREIGN':
        HOST = socket.gethostbyname(socket.gethostname())
    else:
        HOST = 'localhost'    
    print "Using IP: " + HOST
    PORT = 9999

    serverSocket.bind((HOST, PORT))
    serverSocket.listen(10)

    connections.append(serverSocket)

    print 'main: CHAT SERVER STARTED ON PORT ' + str(PORT)

    while True:

        readyToRead, readyToWrite, errorSockets = select.select(connections, [], [])

        for sock in readyToRead:
            if sock == serverSocket:
                sockfd, addr = serverSocket.accept()
                connections.append(sockfd)
                print 'Client (%s, %s) connected' % addr
                broadcastMessage(sockfd, '--> username (%s@%s) has joined the chat\n' % addr)
            else:
                try:
                    data = sock.recv(1024).strip()
                    print data
                    if data:
                        if debug: print 'main: ' + str(connections)
                        handleJSON(data, sock) 
                        #broadcastMessage(sock, 'username | ' + data)
                        
                except:
                    print 'Client (%s, %s) disconnected' % addr
                    broadcastMessage(sock, '<-- username (%s, %s) has quit the chat\n')
                    sock.close()
                    connections.remove(sock)
                    break

    serverSocket.close()





