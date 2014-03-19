package controllers;

import javax.swing.JOptionPane;

public class ResponseWaiter extends Thread {
	
	private boolean ready = false;
	
	
	/**
	 * Waits for response
	 * 
	 * @param socketListener
	 * @param response
	 * @throws LogoutException 
	 */
	public ResponseWaiter(SocketListener socketListener, Object[] response) throws LogoutException {
		socketListener.register(this);
		int counter = 0;
		
		while(! ready) {
			if (counter > 400) { // Timeout 10s
				socketListener.unregister(this);
				JOptionPane.showMessageDialog(null, "Tidsavbrudd!", "Feil", JOptionPane.ERROR_MESSAGE);
				throw new LogoutException("Timed out waiting for response");
			}
			counter++;
			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response[0] = socketListener.getResponse();
		socketListener.unregister(this);
	}
	
	/**
	 * Method for other thread to notify this
	 * 
	 * @param ready
	 */
	public synchronized void setReady(boolean ready) {
		this.ready = ready;
	}
	
	
	
}
