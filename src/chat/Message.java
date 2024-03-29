/**
 * 
 */
package chat;

import java.io.Serializable;

/**
 * Represents a Message object which can be transmitted between 
 * clients and the server.
 * @author ggzaery@gmail.com
 * @version 1.0 5/3/11
 */
public class Message implements Serializable {
	/**
	 * The name of the sender.
	 */
	private String sender;
	
	/**
	 * The contents of the message.
	 */
	private String message;
	
	/**
	 * 
	 */
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
	
	public void toggleMe(boolean b) {
		me = b;
	}

	public boolean getMeState() {
		return me;
	}
	
	public String toString(){
		if(me) return "* "+sender+" "+message;
		return sender+": "+message;
	}

	public String getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}
}
