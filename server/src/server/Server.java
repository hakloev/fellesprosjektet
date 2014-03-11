package server;

import controllers.SocketListener;

/**
 * Created by hakloev on 10/03/14.
 */
public class Server {

	public static void main(String[] args) {
		SocketListener socketListener = new SocketListener();
		socketListener.start();
	}

}
