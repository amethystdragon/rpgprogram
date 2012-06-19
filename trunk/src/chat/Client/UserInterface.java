package chat.Client;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
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
 * The user interface used for the chat program GUI.
 * 
 * @author ggzaery@gmail.com
 * @version 2.0
 * @date 12/24/11
 */
public class UserInterface extends JFrame {

	public static void main(String[] args){
		new UserInterface();
	}
	
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -5499387413427892073L;

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
	 * Default constructor for the user interface which initializes necessary
	 * variables and calls initializeUI()
	 */
	public UserInterface() {
		in = null;
		out = null;
		initializeUI();
	}

	/**
	 * Sets up the UI to display a generic chat window
	 */
	private void initializeUI() {
		// sets the default window size
		setSize(600, 800);
		setLayout(new BorderLayout());
		super.addWindowListener(windowListener);

		// create the newMessage text field
		JPanel temp = new JPanel(new BorderLayout());
		newMessage = new JTextField();
		newMessage.addKeyListener(keyListener);
		temp.add(newMessage, BorderLayout.CENTER);

		// create the send button
		send = new JButton("Send");
		send.addActionListener(actionListener);
		temp.add(send, BorderLayout.EAST);

		// add the panel to the frame
		add(temp, BorderLayout.SOUTH);

		// create the message log
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setAutoscrolls(true);

		// make it scrollable
		JScrollPane scroll = new JScrollPane(messages);

		// add the message log to the frame
		add(scroll, BorderLayout.CENTER);

		// make the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu optionsMenu = new JMenu("Connection");

		menuBar.add(optionsMenu);

		connect = new JMenuItem("Connect");
		connect.addActionListener(actionListener);
		optionsMenu.add(connect);

		// make it visible
		setVisible(true);

	}

	/**
	 * Attempts to connect to a server and, if successful, will print all
	 * incoming messages to the message log.
	 */
	private void connectToServer() {
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
					messages.append(message.toString());

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
								connectToServer();
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

	/**
	 * Window Listener which handles the close operation
	 */
	private WindowListener windowListener = new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
			// unused

		}

		@Override
		public void windowClosing(WindowEvent e) {

			// if connected to the server
			if (connected) {
				try {
					messages.append("Ending session\n");

					// send a disconnect message
					out.writeObject(new Message("DISCONNECT", "END"));
				} catch (IOException e1) {
					// ignored, we're closing the application anyway
				}
			}

			System.exit(1);

		}

		@Override
		public void windowClosed(WindowEvent e) {
			// unused
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// unused
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// unused
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// unused
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// unused
		}
	};
}
