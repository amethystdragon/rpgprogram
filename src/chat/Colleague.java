/**
 * 
 */
package chat;

/**
 * Represents the Colleague interface.
 * @author Nick Iannone
 * @version 1.1 5/5/11
 */
public interface Colleague {

	/**
	 * Receives a message from the mediator.
	 * @param msg The message received.
	 */
	public void receiveMessage(Message msg);
	
	/**
	 * Sends a message to the mediator.
	 * @param msg The message to send.
	 */
	public void sendMessage(String msg);
	
}
