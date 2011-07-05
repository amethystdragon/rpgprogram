package commands;

import java.util.Random;

import chat.ConnectionInterface;
import chat.Server.ChatServer;

/**
 * @author amethyr
 *
 */
public class DiceRoll implements Command{

	/**
	 * Stores the command passed in
	 */
	private String command = "";
	/**
	 * Stores the result once executed
	 */
	private String result = "";
	/**
	 * Stores the server to write back the result to
	 */
	private ChatServer server = null;
	
	/**
	 * Should never be used.
	 */
	@SuppressWarnings("unused")
	private DiceRoll(){	}
	
	/**
	 * Constructor to take in and store the command and the server
	 * 
	 * @param server - where the result is going to be written to
	 * @param command - the command to roll the correct set of dice.
	 */
	public DiceRoll(ChatServer server, String command) {
		this.command = command;
		this.server = server;
	}

	/**
	 * Parses the command string passed in and calculates the result of the sum of the dice rolled
	 * 
	 * @param text - the dice command passed in
	 * @return - the the command passed in and the result as a string
	 */
	public void execute() {
		int mid = 0;
		char temp = '\n';
		for(int i = 0; !(temp == 'd' || temp == 'D'); i++){
			mid = i;
			temp = this.command.charAt(i);
		}
		//Parse the quantity of dice
		int quantity = Integer.parseInt(this.command.substring(0, mid));
		//Parse the number of sides for the dice
		int sides = Integer.parseInt(this.command.substring(mid+1));
		
		if(quantity < 1) this.result = "Cannot roll less then 1 die";
		if(sides < 2) this.result = "Cannot get a die less then 2 sides";
		
//		System.out.println("Sides: " + sides + " Quantity: " + quantity);
		Random rng = new Random();
		int answer = 0;
		for(int i = 0; i < quantity; i++){
			//Totals the results 
			answer += rng.nextInt(sides) + 1; 
		}
		//Returns calculated
		this.result = this.command + " = " + answer;
		//TODO Rework to add to the server
//		this.server.addToChat(this.result);
	}

	@Override
	public void execute(ConnectionInterface sc) {
		// TODO Auto-generated method stub
		
	}
}
