package Tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chat.Server.ChatServer;

import commands.Command;
import commands.DiceRoll;

public class DiceRollTest {
	
	Command diceRoll;
	ChatServer server;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		diceRoll = null;
		server = null;
	}

	@Test
	public void testExecute() {
		//Checks to see if the prefix is written correctly
		server = new ChatServer();
		diceRoll = new DiceRoll(server, "2D6");
		diceRoll.execute();
//		assertTrue(server.getChatLog().substring(0, 6).equals("2D6 = "));
		
		server = new ChatServer();
		diceRoll = new DiceRoll(server, "2d6");
		diceRoll.execute();
//		assertTrue(server.getChatLog().substring(0, 6).equals("2d6 = "));
		
		server = new ChatServer();
		diceRoll = new DiceRoll(server, "3d8");
		diceRoll.execute();
//		assertTrue(server.getChatLog().substring(0, 6).equals("3d8 = "));;
		
		//1D2 through 100D100 
		for(int k = 2; k<=100; k++){ //Sides
			for(int j = 1; j<=100; j++){ //Quantity
				System.out.println(j+"d"+k);
				for(int i = 0; i < 10000; i++){
					server = new ChatServer();
					diceRoll = new DiceRoll(server, j+"d"+k);
					diceRoll.execute();
//					String log = server.getChatLog();
					
					//Determines where the = is
					int mid = 0;
					char temp = '\n';
					for(int l = 0; !(temp == '='); l++){
						mid = l;
//						temp = log.charAt(l);
					}
					//Parses out the result
//					int result = Integer.parseInt(log.substring(mid+2, log.length()-1));
//					System.out.println(result);
					//Checks the result agenst the bounds
//					assertTrue(result > 0 && result <= k*j);
				}			
			}
		}
	}
	
	  public static junit.framework.Test suite() {
	        return new junit.framework.JUnit4TestAdapter(DiceRollTest.class);
	    }
}
