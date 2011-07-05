
package chat;

/**
 * Provides a list of commands that can be used on the chat
 * 
 *
 */
public interface ConnectionInterface {
	/**
	 * Command to add a user to the chat
	 * @param username - user to add to the chat
	 */
	public void addUser(String username);
	/**
	 * Adds the DM as the chats admin
	 * @param username - DM's username
	 */
	public void setDM(String username);
	/**
	 * Removes a user from the chat
	 * @param username
	 */
	public void removeUser(String username);
	/**
	 * Allows the players and the DM to communicate
	 * @param username - of player or DM
	 * @param message
	 */
	public void chat(String username, String message);
	/**
	 * Command to display an error message
	 * @param s - error message
	 */
	public void error(String s);
	
	/**
	 * Rolls the number and type of dice in #d# form i.e. 2d6
	 * @param diceRoll
	 */
	public void roll(String diceRoll);
	/**
	 * Closes the chat window
	 */
	public void close();
}
