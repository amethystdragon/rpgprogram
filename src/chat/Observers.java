/**
 * 
 */
package chat;


/**
 * Represents the Colleague interface.
 * @author ggzaery@gmail.com
 * @version 1.1 5/5/11
 */
public abstract class Observers implements Comparable<Observers>{

	public String username = null;
	/**
	 * Receives a message from the mediator.
	 * @param msg The message received.
	 */
	public abstract void receiveMessage(Message msg);
	
	/**
	 * Sends a message to the mediator.
	 * @param msg The message to send.
	 */
	public abstract void sendMessage(Message msg);
	
	@Override
	public int compareTo(Observers o) {
		return username.equalsIgnoreCase(o.username)?0:-1;
	}
}
