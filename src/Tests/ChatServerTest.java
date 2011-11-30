/**
 * 
 */
package Tests;

import old.ChatServer;

import org.junit.After;
import org.junit.Before;

/**
 * @author amethyr
 *
 */
public class ChatServerTest {
	/**
	 * 
	 */
	ChatServer server;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		server = new ChatServer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		server = null;
	}


//
//	/**
//	 * Test method for {@link chat.Server.ChatServer#writeToLog(java.lang.String)}.
//	 */
//	@Test
//	public void testAddToChat() {
//		//Test basic message
//		server.addToChat("Hello, World");
//		assertTrue(server.getChatLog().equals("Hello, World\n"));
//		
//		//Tests roll command
//		server = new ChatServer();
//		server.addToChat("/roll 2D6");
//		System.out.print(server.getChatLog());
//		assertTrue(server.getChatLog().substring(0, 6).equals("2D6 = "));
//
//		//Tests unknown command
//		server = new ChatServer();
//		server.addToChat("/ashk");
//		assertTrue(server.getChatLog().equals(""));
//	}
}
