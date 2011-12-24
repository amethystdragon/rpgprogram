/**
 * 
 */
package chat.Server;

import java.io.IOException;

/**
 * Represents the chat server application.
 * @author Nick Iannone
 * @version 1.1 5/5/11
 */
public class ServerApp {

	/**
	 * Runs the server application.
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		try {
			new ChatServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
