/*
 * Course: SE2811-002
 * Term: Spring 2011
 * Assignment: Project
 * Author: Jacob Henkel
 * Date: May 4, 2011
 */
package mediator.cli;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import mediator.Message;

/**
 * @author Jacob
 * @version 1.0
 */
public class CLIChatApp {

	private static InetAddress ip;
	private static final int port = 4000;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//Prompt the user for the address of the server
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter an address to connect to:");
		String ipString = scan.nextLine();
		//Parse that to an InetAddress
		try {
			ip = InetAddress.getByName(ipString);
		} catch (UnknownHostException e) {
			System.out.println("Could not parse address " + ipString + " into a valid host");
			System.exit(1);
		}
		//Ask for a name
		System.out.print("Enter the name you would like:");
		String name = scan.nextLine();
		CLIChat chat = new CLIChat(name,ip,port);
		chat.draw();
		while(chat.getConnected()){
			String message = scan.nextLine();
			if(message.toLowerCase().equals("/quit")){
				//we are exiting
				chat.sendMessage(new Message("DISCONNECT","Bye"));
				System.out.println("Disconnecting");
				chat.disconnect();
			}else if (message.toLowerCase().equals("/redraw")){
				chat.draw();
			}else{
				Message msg = new Message(name,message);
				chat.sendMessage(msg);
				chat.draw();
			}
		}
		//No longer connected
		scan.close();
		System.exit(0);

	}

}
