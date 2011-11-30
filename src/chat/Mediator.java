/**
 * 
 */
package chat;

/**
 * Represents the Mediator interface.
 * @author Nick Iannone
 * @version 1.0 5/3/11
 */
public interface Mediator {

	/**
	 * Sends a message to the connected clients.
	 * @param sender The sender of the message.
	 * @param msg The message to be sent.
	 */
	public void sendMessage(Colleague sender, String msg);
	
	/**
	 * Adds a colleague to the mediator.
	 * @param col The colleague to attach.
	 * @return <b>true</b> if the colleague was added properly; <b>false</b> otherwise.
	 */
	public boolean addColleague(Colleague col);
	
	/**
	 * Removes a colleague from the mediator.
	 * @param col The colleague to detach.
	 * @return <b>true</b> if the colleague was removed properly; <b>false</b> otherwise.
	 */
	public boolean removeColleague(Colleague col);
}
