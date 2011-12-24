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

import chat.Colleague;
import chat.Mediator;
import chat.Message;


/**
 * Represents the chat server mediator.
 * @author Nick Iannone
 * @version 1.1 5/5/11
 */
public class ChatServer implements Mediator {
	
	/**
	 * The list of colleagues attached to the mediator.
	 */
	private volatile List<Colleague> colleagues;

	/**
	 * The version string.
	 * @since 1.1
	 */
	private static final String VERSION = "1.1";
	
	/**
	 * The default name index. Used in generating default names.
	 */
	public static int defaultNameIndex = 0;
	
	/**
	 * Gets the next available default name.
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
	private static final String LOCAL_NAME = "GHOST";
	
	/**
	 * The server-side socket.
	 */
	private ServerSocket serverSocket;
	
	/**
	 * The outgoing message queue.
	 */
	private volatile Queue<Message> outgoingQueue;
	
	/**
	 * Constructor. Starts the chat server, and starts a listener thread for connections.
	 * @throws IOException if the socket cannot be opened.
	 */
	public ChatServer() throws IOException {
		// Set up the colleague tracking.
		colleagues = new LinkedList<Colleague>();
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
		System.out.println("Listening on address " + addr.getHostAddress() + ", port " + PORT + "...");
	}

	/**
	 * Echoes the version number.
	 * @since 1.1
	 */
	private void echoVersion() {
		System.out.println("=============================================");
		System.out.println("Welcome to Mediator Chat v" + VERSION);
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
						Colleague c = new ColleagueProxy(ChatServer.this, s);
						System.out.println("Client connected from " + s.getInetAddress().getHostAddress() + ":" + s.getPort() + ", on local port " + s.getLocalPort() + ".");
						addColleague(c);
					} catch (IOException ex) {
						// If we can't connect to the remote client, just forget it and move on.
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
					} catch (InterruptedException e) {}
					// Make a list to store the removed colleagues.
					List<Colleague> removedColleagues = new LinkedList<Colleague>();
					synchronized (colleagues) {
						// Go thru the colleagues list, looking for closed connections.
						Iterator<Colleague> it = colleagues.iterator();
						while (it.hasNext()) {
							// Get the next colleague.
							Colleague c = it.next();
							if (c instanceof ColleagueProxy) {
								// Convert to a server colleague.
								ColleagueProxy col = (ColleagueProxy)c;
								// If we find a closed connection, remove the colleague and quickly restart the search.
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
							System.out.println("Found " + size + " colleagues to remove.");
						}
						// Go thru and notify everyone of the removed colleague(s).
						for (Colleague col : removedColleagues) {
							removeColleague(col);
						}
					}
				}
			}
		});
		removal.start();
	}
	
	/**
	 * Starts the message dispatch thread.
	 * This thread runs through and dispatches outgoing messages to all clients.
	 */
	private void startDispatchThread() {
		Thread dispatch = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Starting dispatch thread...");
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {}
					synchronized (outgoingQueue) {
						while (!outgoingQueue.isEmpty()) {
							Message msg = outgoingQueue.poll();
							dispatchMessage(msg);
						}
					}
				}
			}
		});
		dispatch.start();
	}
	
	/**
	 * Adds a colleague to the list.
	 * @param col The colleague that has joined the server.
	 */
	@Override
	public boolean addColleague(Colleague col) {
		boolean added = false;
		if (col != null) {
			synchronized (colleagues) {
				if (!colleagues.contains(col)) {
					added = colleagues.add(col);
				}
			}
			if (added) {
				String name = LOCAL_NAME;
				if (col instanceof ColleagueProxy) {
					ColleagueProxy cp = (ColleagueProxy)col;
					name = cp.getName();
				}
				Message msg = new Message("INFO", "User " + name + " has joined the server.");
				enqueueMessage(msg);
			}
		}
		return added;
	}
	
	/**
	 * Removes a colleague from the list of mediators.
	 * @param col The colleague that has left the server.
	 */
	@Override
	public boolean removeColleague(Colleague col) {
		boolean removed = false;
		if (col != null) {
			synchronized (colleagues) {
				removed = colleagues.remove(col);
			}
			if (removed) {
				String name = LOCAL_NAME;
				if (col instanceof ColleagueProxy) {
					ColleagueProxy cp = (ColleagueProxy)col;
					name = cp.getName();
				}
				Message msg = new Message("INFO", "User " + name + " has left the server.");
				enqueueMessage(msg);
			}
		}
		return removed;
	}
	
	/**
	 * Checks for / switches, and handles those if applicable. Otherwise, sends the message to all clients.
	 * @see chat.Mediator#sendMessage(chat.Colleague, java.lang.String)
	 */
	@Override
	public void sendMessage(Colleague sender, String msg) {
		if (msg.startsWith("/")) {
			// Check for / switches.
			handleSwitch(sender, msg);
		} else {
			// Generate the name, and send the message raw to all clients.
			String name = LOCAL_NAME;
			// If we can get a name from the client, use that.
			if (sender instanceof ColleagueProxy) {
				name = ((ColleagueProxy)sender).getName();
			}
			// Generate and send the message.
			Message rawmsg = new Message(name, msg);
			enqueueMessage(rawmsg);
		}
	}
	
	/**
	 * Enqueues outgoing messages for future dispatching.
	 * @param msg The message to be dispatched.
	 */
	private void enqueueMessage(Message msg) {
		synchronized (outgoingQueue) {
			outgoingQueue.add(msg);
		}
	}

	/**
	 * Handle a switch in the client's message.
	 * @param sender The colleague object who sent the message.
	 * @param msg The message that was sent.
	 */
	private void handleSwitch(Colleague sender, String msg) {
		// Just yell at the user for now.
		Message m = null;
		if(msg.substring(1,3).equals("me")){
			m = new Message(((ColleagueProxy)sender).getName(),msg.substring(3, msg.length()));
			m.toggleMe(true);
		}else{
			m = new Message("INFO", "Invalid command: [[" + msg + "]].");
		}
		enqueueMessage(m);
	}
	
	/**
	 * Dispatches the message to the selected client.
	 * Used By whisper
	 * 
	 * @param target The target colleague.
	 * @param msg The message to send.
	 */
	@SuppressWarnings("unused")
	private void dispatchSingleMessage(Colleague target, Message msg) {
		target.receiveMessage(msg);
	}

	/**
	 * Dispatches the message to all connected clients.
	 * @param msg The message that was sent.
	 */
	private void dispatchMessage(Message msg) {
		// Send it to all colleagues.
		synchronized (colleagues) {
			for (Colleague col : colleagues) {
				col.receiveMessage(msg);
			}
		}
		System.out.println(msg.sender + ": " + msg.message);
	}

	/**
	 * Handles a client name change.
	 * @param oldName The last reported name of the client.
	 * @param newName The new name of the client.
	 */
	public void onNameChanged(String oldName, String newName) {
		Message msg = new Message("INFO", "User " + oldName + " has changed their name to " + newName + ".");
		enqueueMessage(msg);
	}
}
