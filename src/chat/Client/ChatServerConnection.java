package chat.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import chat.ConnectionInterface;
import chat.MessageQueue;
import chat.commands.AddUser;
import chat.commands.RemoveUser;

/**
 * Implements the generic connection interface for basic functionality
 * Implements Runnable so it can operate on its own thread natively
 * 
 * @author ggzaery@gmail.com
 *
 */
public class ChatServerConnection implements ConnectionInterface, Runnable{
	/**
	 * Chat Client
	 */
	private ChatClient client;
	/**
	 * Uses a SSL socket
	 */
	private SSLSocket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private MessageQueue queue;
	private boolean connected;
	
	
	public ChatServerConnection(ChatClient client){
		connected = false;
		in = null;
		out = null;
		this.client = client;
		// initialize the SSl socket
		try{
			SSLSocketFactory sslFact = (SSLSocketFactory)SSLSocketFactory.getDefault();
			socket = (SSLSocket)sslFact.createSocket(client.site, client.PORT);
			// Checking the supported Cipher suites.
			String [] enabledCipher = socket.getSupportedCipherSuites ();
			// Enabled the Cipher suites.
			socket.setEnabledCipherSuites (enabledCipher);
	
			// make in/out stream connection
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			connected = true;
			client.setConnected(true);
			new Thread(this).start();
		} catch (IOException e) {
			client.error("Server not available");
			client.setConnected(false);
			connected = false;
		} 
	}
	
	
	
	@Override
	public void addUser(String username) {
		try {
			this.out.writeObject(new AddUser(username));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeUser(String username) {
		try {
			this.out.writeObject(new RemoveUser(username));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setDM(String username) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void chat(String username, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roll(String diceRoll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}



}
