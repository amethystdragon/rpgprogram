/*
 * Course: SE2811-002
 * Term: Spring 2011
 * Assignment:
 * Author: Jacob Henkel
 * Date: May 4, 2011
 */
package mediator.cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

import chat.Message;


/**
 * @author Jacob
 * @version 1.0
 */
public class CLIChat {

	private Socket socket;
	private String name;
	private Thread receiveThread;
	private boolean connected;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private LinkedList<Message> messageBuffer;
	private static final int BUFFERSIZE = 40;
	/**
	 * ctor
	 * @param name Name to tell the server
	 * @param ip IP of the server
	 * @param port Port to connect on
	 */
	public CLIChat(String name, InetAddress ip, int port) {
		this.name=name;
		try {
			socket = new Socket(ip,port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			connected = true;
			messageBuffer = new LinkedList<Message>();
			Message temp = new Message("","");
			for(int i =0;i<20;i++){
				messageBuffer.add(temp);
			}
			receiveThread = new Thread(new Runnable(){
				@Override
				public void run() {
					while(connected){
						try {
							Object msg = ois.readObject();
							if(msg instanceof Message){
								receiveMessage((Message)msg);
							}
						} catch (ClassNotFoundException e) {
							//Just drop the message
						} catch (IOException e) {
							//Error reading
							System.out.println("Error connecting, exiting");
							connected = false;
						}

					}
				}

			});
			receiveThread.start();
		} catch (IOException e) {
			System.out.println("Could not Connect");
		}
	}

	/**
	 * Handle receiving a Message
	 */
	public void receiveMessage(Message msg) {
		if(messageBuffer.size()>=BUFFERSIZE){
			messageBuffer.remove(0);
		}
		messageBuffer.add(msg);
		draw();
	}

	/**
	 * Handle sending a message
	 */
	public void sendMessage(Message msg) {
		try {
			oos.writeObject(msg);//Send the Message
		} catch (IOException e) {
			System.out.println("Failed to get output stream");
		}
	}

	/**
	 * Returns if we are connected or not
	 * @return
	 */
	public boolean getConnected(){
		return connected;
	}

	/**
	 * Draws the message buffer
	 */
	public void draw() {
		for(Message m: messageBuffer){
			if(m.sender.equals("")){
				System.out.println("");
			}else if (m.sender.equals(name)){
				System.out.println(String.format("[%s] %s", m.sender, m.message));
			}else{
				System.out.println(String.format("<%s> %s", m.sender, m.message));
			}
		}
		System.out.flush();
	}

	/**
	 * This method disconnects from the server
	 */
	public void disconnect() {
		try {
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			//failed at closing somthign, exiting anyway
		}
		connected=false;
	}
}
