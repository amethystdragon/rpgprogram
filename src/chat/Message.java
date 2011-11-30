/**
 * 
 */
package chat;

import java.io.Serializable;

/**
 * Represents a Message object which can be transmitted between 
 * clients and the server.
 * @author Nick Iannone
 * @version 1.0 5/3/11
 */
public class Message implements Serializable {
	private boolean me = false;
	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = -5565700383348704800L;

	/**
	 * Constructor.
	 * @param sender The name of the sender.
	 * @param message The message to send.
	 */
	public Message(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}
	
	/**
	 * The name of the sender.
	 */
	public String sender;

	/**
	 * The contents of the message.
	 */
	public String message;

	public void toggleMe(boolean b) {
		me = b;
	}

	public boolean getMeState() {
		return me;
	}
}
