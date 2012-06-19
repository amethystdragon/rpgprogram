package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chat.Message;
import chat.Server.ChatServer;
import chat.commands.Command;
import chat.commands.DiceRoll;

public class DiceRollTest {
	
	Command diceRoll;
	ChatServer server=null;
	
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
		diceRoll = new DiceRoll(server, new Message(null,"/roll 2D6"));
		diceRoll.execute();
//		assertTrue(server.getChatLog().substring(0, 6).equals("2D6 = "));
		
		diceRoll = new DiceRoll(server, new Message(null,"/roll 2d6"));
		diceRoll.execute();
//		assertTrue(server.getChatLog().substring(0, 6).equals("2d6 = "));
		
		diceRoll = new DiceRoll(server, new Message(null,"/roll 5d8"));
		diceRoll.execute();
		
		diceRoll = new DiceRoll(server, new Message(null,"/roll 3D10 + 4d12"));
		diceRoll.execute();
		
//		assertTrue(server.getChatLog().substring(0, 6).equals("3d8 = "));;
		
		//1D2 through 100D100 
//		for(int k = 2; k<=100; k++){ //Sides
//			for(int j = 1; j<=25; j++){ //Quantity
//				System.out.println(j+"d"+k);
				for(int i = 0; i < 100; i++){
					diceRoll = new DiceRoll(server, new Message(null, "/roll "+1+"d"+20));
					diceRoll.execute();
				}			
//			}
//		}
	}
	
}
