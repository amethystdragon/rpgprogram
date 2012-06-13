/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.Message;

/**
 * @author amethyr
 * 
 */
public class ChatPanel extends JPanel {

	/**
	 * Auto generated serial version id.
	 */
	private static final long serialVersionUID = 6720964013853272405L;

	/**
	 * Text field in which new messages will be written
	 */
	private JTextField newMessage;
	
	/**
	 * Message log
	 */
	private JTextArea messages;
	
	/**
	 * Button used for sending messages
	 */
	private JButton send;
	
	/**
	 * Menu item used to connect to a server
	 */
	private JMenuItem connect;
	
	/**
	 * Indicates if the GUI is currently connected to a server
	 */
	private boolean connected = false;
	
	/**
	 * The server socket
	 */
	private Socket server;
	
	/**
	 * The server port
	 */
	private int port;
	
	/**
	 * The server IP
	 */
	private InetAddress ip;
	
	/**
	 * Input stream from the server
	 */
	private ObjectInputStream in;
	
	/**
	 * Output stream to the server
	 */
	private ObjectOutputStream out;
	
	/**
	 * User name to send to the server
	 */
	private String name;
	
	/**
	 * Default constructor. 
	 * Provides setup for the chat panel part of the GUI.
	 */
	public ChatPanel() {
		
		this.setLayout(new BorderLayout());

		// create the newMessage text field
		JPanel lower = new JPanel();
		lower.setLayout(new BorderLayout());
		newMessage = new JTextField();
		newMessage.addKeyListener(keyListener);
		lower.add(newMessage, BorderLayout.CENTER);

		// create the send button
		send = new JButton("Send");
		send.addActionListener(actionListener);
		lower.add(send, BorderLayout.EAST);

		add(lower, BorderLayout.SOUTH);
		
		// create the message log
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setAutoscrolls(true);

		// make it scrollable
		JScrollPane scroll = new JScrollPane(messages);

		// add the message log to the frame
		add(scroll, BorderLayout.CENTER);
		
	}

	/**
	 * Adds necessary components to the window's JMenuBar.
	 * 
	 * @param current - reference to the current JMenuBar
	 */
	public void appendToMenuBar(JMenuBar current){
		//Create the Menu component
		JMenu optionsMenu = new JMenu("Connection");
		//Creates the JMenu Item
		connect = new JMenuItem("Connect");
		connect.addActionListener(actionListener);
		optionsMenu.add(connect);
		
		//Appends the added component to the manu bar
		current.add(optionsMenu);
	}
	
	
	
	/**
	 * Attempts to connect to a server and, if successful, will print all
	 * incoming messages to the message log.
	 */
	public void connect() {
		try {
			// indicate to user attempting to connect at the specified IP and
			// port
			messages.append("Attempting to connect to " + ip + ";" + port
					+ "\n");

			// try to connect to server
			server = new Socket(ip, port);

			// indicate to user that the connection was successfully made
			messages.append("Connected to server\n");
		} catch (UnknownHostException e) {
			// indicate that the server could not be connected to
			messages.append("Could not connect to " + ip + ";" + port + "\n");
			connected = false;
		} catch (IOException e) {
			// indicate that the connection was bad
			messages.append("Bad connection\n");
			connected = false;
		}

		if (connected) {
			try {
				// indicate its trying to set up its communication channels
				messages.append("Initializing connection streams\n");

				// get the input and output streams from the connection
				out = new ObjectOutputStream(server.getOutputStream());
				in = new ObjectInputStream(server.getInputStream());

				// indicate the communication channels were successfully
				// initialized
				messages.append("Connection streams initialized\n");
			} catch (IOException e) {
				// indicate that the communication channels could not be made
				messages.append("Could not retrieve input and output streams from socket\n");
				connected = false;
			}
		}

		// while still connected
		while (connected) {
			try {
				// read the object from the stream
				Object input = in.readObject();

				// if it is a message
				if (input instanceof Message) {
					// print the message to the text box
					Message message = (Message) input;
					if (message.getMeState()) {
						messages.append(message.sender + message.message + "\n");
					} else {
						messages.append(message.sender + ": " + message.message
								+ "\n");
					}

					// scroll the message log to the bottom
					messages.selectAll();
					int x = messages.getSelectionEnd();
					messages.select(x, x);

				}

			} catch (IOException e) {
				// indicate that the server closed its socket
				messages.append("The server closed the connection\n");
				connected = false;
			} catch (ClassNotFoundException e) {
				messages.append("Warning: An unknown file was sent through the socket");
			}
		}
	}

	/**
	 * Method to disconnect the chat.
	 */
	public void disconnect(){
		messages.append("Ending session\n");
		
		// send a disconnect message
		try {
			this.out.writeObject(new Message("DISCONNECT", "END"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Determines if there is a connection to the chat server
	 * @return - boolean connected
	 */
	public boolean isConnected(){
		return this.connected;
	}

	/**
	 * Sends a message if connected to a server
	 */
	private void sendMessage() {
		// if connected to server
		if (connected) {
			// try to write the current text
			try {
				out.writeObject(new Message(name, newMessage.getText()));

				// clear current text if message was sent correctly
				newMessage.setText("");
			} catch (IOException e) {
				messages.append("Could not send new message\n");
			}

		} else {
			messages.append("Not connected, message could not be sent\n");
		}
	}

	/**
	 * Action Listener which handles all button presses
	 */
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(connect)) {
				if (!connected) {

					connected = true;

					try {
						// get user name
						name = JOptionPane.showInputDialog("Enter user name");
						if (name == null) {
							throw new NullPointerException();
						}
						// get server information
						ip = InetAddress.getByName(JOptionPane
								.showInputDialog("Enter server IP address"));
						port = Integer.parseInt(JOptionPane
								.showInputDialog("Enter server port"));
					} catch (HeadlessException ex) {
						ex.printStackTrace();
					} catch (UnknownHostException ex) {
						messages.append("Invalid host\n");
						connected = false;
					} catch (NumberFormatException ex) {
						messages.append("The port must be an integer value\n");
						connected = false;
					} catch (NullPointerException ex) {
						// name was null because cancel was pressed
						connected = false;
					}

					// connected = true;
					if (connected) {
						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								connect();
							}

						});
						thread.start();
					}
				}
			} else if (e.getSource().equals(send)) {
				sendMessage();
			}
		}
	};

	/**
	 * Key listener which handles all key presses
	 */
	private KeyListener keyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				sendMessage();
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	};
}
