import mysql.connector

print 'conn'
cnx = mysql.connector.connect(user='hakloev', password='u2_gothen360', host='loevdal.net', database='vinstraff')

print 'close'
cnx.close()
