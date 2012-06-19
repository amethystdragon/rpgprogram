package chat.commands;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import chat.Message;
import chat.Server.ChatServer;


/**
 * @author ggzaery@gmail.com
 *
 */
public class DiceRoll implements Command{

	/**
	 * Stores the command passed in
	 */
	private String command = "";
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
	public DiceRoll(ChatServer server, Message msg) {
		this.command = msg.getMessage().substring(6);
		this.server = server;
	}

	/**
	 * Parses the command string passed in and calculates the result of the sum of the dice rolled
	 * 
	 * @param text - the dice command passed in
	 * @return - the the command passed in and the result as a string
	 */
	@Override
	public void execute() {
		//Stores the string result
		String result = command + " = ";
		//Stores the numerical answer
		int answer = 0;
		//Scan through the message and find all rolls
		Scanner scan = new Scanner(command);
		
		//While there is more strings
		ArrayList<String> rolls = new ArrayList<String>();
		while (scan.hasNext()){
			//Store the roll
			String temp = scan.next(); 
			if(!temp.equals("+"))
				rolls.add(temp);
		}

		for(String roll : rolls){
			roll = roll.replace('D', ' ');
			roll = roll.replace('d', ' ');
			Scanner rollscan = new Scanner(roll);
			
			int quantity = rollscan.nextInt();
			int sides = rollscan.nextInt();
			
			if (quantity < 1 || sides < 2){
				result = "0 + ";
				break;
			}
			
			Random rng = new Random();
			int sum = 0;
			for (int i = 0; i < quantity; i++) {
				// Totals the results
				sum += rng.nextInt(sides) + 1;
			}
			// Returns calculated
			result += sum + " + ";
			answer+=sum;
		}
		result = result.substring(0, result.length()-2) + " = "+answer;
		if(server!=null)
			this.server.sendMessage(new Message("Server", result));
		else
			System.out.println(result);
	}
}
