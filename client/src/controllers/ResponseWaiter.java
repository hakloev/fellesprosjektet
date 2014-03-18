package controllers;

public class ResponseWaiter extends Thread {
	
	private boolean ready = false;
	
	
	/**
	 * Waits for response
	 * stores an Integer(0) in response on timeout
	 * 
	 * @param socketListener
	 * @param response
	 */
	public ResponseWaiter(SocketListener socketListener, Object[] response) {
		socketListener.register(this);
		int counter = 0;
		
		while(! ready) {
			if (counter > 200) { // Timeout
				socketListener.unregister(this);
				response[0] = new Integer(1);
				return;
			}
			counter++;
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response[0] = socketListener.getResponse();
		socketListener.unregister(this);
		System.out.println(counter);
		
		// TODO cast exception  on timeout 
		// throw new Time
		
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
