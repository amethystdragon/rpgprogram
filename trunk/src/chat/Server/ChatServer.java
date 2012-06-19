/**
 * 
 */
package chat.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import chat.Mediator;
import chat.Message;
import chat.Observers;
import chat.commands.DiceRoll;

/**
 * Represents the chat server mediator.
 * 
 * @author Nick Iannone
 * @version 1.1 5/5/11
 */
public class ChatServer implements Mediator {

	/**
	 * The list of colleagues attached to the mediator.
	 */
	private volatile List<Observers> observers;

	/**
	 * The version string.
	 */
	private static final String VERSION = "1.1";

	/**
	 * Used to generate default names
	 */
	public static int defaultNameIndex = 0;

	/**
	 * Gets the next available default name.
	 * 
	 * @return The next default name.
	 */
	public static String getDefaultName() {
		String name = "Unnamed-" + defaultNameIndex;
		defaultNameIndex++;
		return name;
	}

	/**
	 * The server port.
	 */
	public static final int PORT = 5000;

	/**
	 * The non-proxy client name.
	 */
	private static final String LOCAL_NAME = "SERVER";

	/**
	 * The server-side socket.
	 */
	private ServerSocket serverSocket;

	/**
	 * The outgoing message queue.
	 */
	private volatile Queue<Message> outgoingQueue;

	/**
	 * Constructor. Starts the chat server, and starts a listener thread for
	 * connections.
	 * 
	 * @throws IOException
	 *             if the socket cannot be opened.
	 */
	public ChatServer() throws IOException {
		// Set up the colleague tracking.
		observers = new LinkedList<Observers>();
		// Notify of initialization.
		echoVersion();
		// Create the outgoing message queue.
		outgoingQueue = new LinkedList<Message>();
		// Create the server socket.
		serverSocket = new ServerSocket(PORT);
		// Notify of port creation.
		echoPortConfig();
		// Start the listen thread.
		startListenThread();
		// Start the dispatch thread.
		startDispatchThread();
		// Start the removal thread.
		startRemovalThread();
	}

	/**
	 * Echoes the IP/port configuration.
	 */
	private void echoPortConfig() {
		InetAddress addr = serverSocket.getInetAddress();
		System.out.println("Listening on address " + addr.getHostAddress()
				+ ", port " + PORT + "...");
	}

	/**
	 * Echoes the version number.
	 * 
	 * @since 1.1
	 */
	private void echoVersion() {
		System.out.println("=============================================");
		System.out.println("Welcome to RPG Chat v" + VERSION);
		System.out.println("=============================================");
	}

	/**
	 * Starts the connection listener thread.
	 */
	private void startListenThread() {
		Thread listen = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Starting listen thread...");
				while (true) {
					try {
						Socket s = serverSocket.accept();
						Observers c = new ColleagueProxy(ChatServer.this, s);
						System.out.println("Client connected from "
								+ s.getInetAddress().getHostAddress() + ":"
								+ s.getPort() + ", on local port "
								+ s.getLocalPort() + ".");
						addColleague(c);
					} catch (IOException ex) {
						// If we can't connect to the remote client, just forget
						// it and move on.
						ex.printStackTrace();
					}
				}
			}
		});
		listen.start();
	}

	/**
	 * Starts the client removal thread.
	 */
	private void startRemovalThread() {
		Thread removal = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Starting client timeout thread...");
				while (true) {
					try {
						// Wait for a while to minimize blocking.
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					// Make a list to store the removed colleagues.
					List<Observers> removedColleagues = new LinkedList<Observers>();
					synchronized (observers) {
						// Go thru the colleagues list, looking for closed
						// connections.
						Iterator<Observers> it = observers.iterator();
						while (it.hasNext()) {
							// Get the next colleague.
							Observers c = it.next();
							if (c instanceof ColleagueProxy) {
								// Convert to a server colleague.
								ColleagueProxy col = (ColleagueProxy) c;
								// If we find a closed connection, remove the
								// colleague and quickly restart the search.
								if (col.isClosed()) {
									it.remove();
									removedColleagues.add(col);
								}
							}
						}
					}
					int size = removedColleagues.size();
					if (size > 0) {
						if (size == 1) {
							System.out.println("Found 1 colleague to remove.");
						} else {
							System.out.println("Found " + size
									+ " colleagues to remove.");
						}
						// Go thru and notify everyone of the removed
						// colleague(s).
						for (Observers col : removedColleagues) {
							removeColleague(col);
						}
					}
				}
			}
		});
		removal.start();
	}

	/**
	 * Starts the message dispatch thread. This thread runs through and
	 * dispatches outgoing messages to all clients.
	 */
	private void startDispatchThread() {
		Thread dispatch = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Starting dispatch thread...");
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {
					}
					while (!outgoingQueue.isEmpty()) {
						Message msg = outgoingQueue.poll();
						if (msg != null)
							dispatchMessage(msg);
					}
				}
			}
		});
		dispatch.start();
	}

	/**
	 * Adds a colleague to the list.
	 * 
	 * @param col
	 *            The colleague that has joined the server.
	 */
	@Override
	public boolean addColleague(Observers col) {
		boolean added = false;
		if (col != null) {
			synchronized (observers) {
				if (!observers.contains(col)) {
					added = observers.add(col);
				}
			}
			if (added) {
				String name = LOCAL_NAME;
				if (col instanceof ColleagueProxy) {
					ColleagueProxy cp = (ColleagueProxy) col;
					name = cp.getName();
				}
				Message msg = new Message("INFO", "User " + name
						+ " has joined the server.");
				enqueueMessage(msg);
			}
		}
		return added;
	}

	/**
	 * Removes a colleague from the list of mediators.
	 * 
	 * @param col
	 *            The colleague that has left the server.
	 */
	@Override
	public boolean removeColleague(Observers col) {
		
		boolean removed = false;
		if (col != null) {
			synchronized (observers) {
				removed = observers.remove(col);
			}
			if (removed) {
				String name = LOCAL_NAME;
				if (col instanceof ColleagueProxy) {
					ColleagueProxy cp = (ColleagueProxy) col;
					name = cp.getName();
				}
				Message msg = new Message("INFO", "User " + name
						+ " has left the server.");
				enqueueMessage(msg);
			}
		}
		return removed;
	}

	/**
	 * Checks for / switches, and handles those if applicable. Otherwise, sends
	 * the message to all clients.
	 * 
	 * @see chat.Mediator#sendMessage(chat.Observers, java.lang.String)
	 */
	public void sendMessage(Message msg) {
		if (msg.getMessage().startsWith("/")) {
			// Check for / switches.
			handleSwitch(msg);
		} else {
			// Generate the name, and send the message raw to all clients.
			String name = null;
			// TODO send it back and let the user know they are unknown
			if (name == null)
				name = "UNKNOWN";

			// Generate and send the message.
			enqueueMessage(msg);
		}
	}

	/**
	 * Enqueues outgoing messages for future dispatching.
	 * 
	 * @param msg
	 *            The message to be dispatched.
	 */
	private void enqueueMessage(Message msg) {
		synchronized (outgoingQueue) {
			outgoingQueue.add(msg);
		}
	}

	/**
	 * Handle a switch in the client's message.
	 * 
	 * @param sender
	 *            The colleague object who sent the message.
	 * @param msg
	 *            The message that was sent.
	 */
	private void handleSwitch(Message msg) {
		// Just yell at the user for now.
		if (msg.getMessage().contains("me")) {
			msg.toggleMe(true);
		} else if (msg.getMessage().contains("roll")) {
			DiceRoll dr = new DiceRoll(this, msg);
			dr.execute();
			return;
		} else {
			msg = new Message("INFO", "Invalid command: [[" + msg + "]].");
		}
		enqueueMessage(msg);
	}

	/**
	 * Dispatches the message to the selected client. Used By whisper
	 * 
	 * @param target
	 *            The target colleague.
	 * @param msg
	 *            The message to send.
	 */
	public void tell(String username, Message msg) {
		Observers obs = new Observers() {
			@Override
			public void sendMessage(Message msg) {
			}
			@Override
			public void receiveMessage(Message msg) {
			}
		};
		obs.username = username;
		//TODO add checking
		observers.get(observers.indexOf(obs)).receiveMessage(msg);
	}

	/**
	 * Dispatches the message to all connected clients.
	 * 
	 * @param msg
	 *            The message that was sent.
	 */
	private void dispatchMessage(Message msg) {
		// Send it to all colleagues.
		synchronized (observers) {
			for (Observers col : observers) {
				col.receiveMessage(msg);
			}
		}
		System.out.println(msg.toString());
	}

	/**
	 * Handles a client name change.
	 * 
	 * @param oldName
	 *            The last reported name of the client.
	 * @param newName
	 *            The new name of the client.
	 */
	public void changeName(String oldName, String newName) {
		Message msg = new Message(LOCAL_NAME, "User "+oldName+" has changed their name to "+newName+".");
		Observers obs = new Observers() {
			@Override
			public void sendMessage(Message msg) {
			}
			@Override
			public void receiveMessage(Message msg) {
			}
		};
		obs.username = oldName;
		//TODO checking
		observers.get(observers.indexOf(obs)).username = newName;
		enqueueMessage(msg);
	}

}
