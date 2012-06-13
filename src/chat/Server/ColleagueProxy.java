/**
 * 
 */
package chat.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import chat.Colleague;
import chat.Mediator;
import chat.Message;


/**
 * Represents a proxy for a remote Colleague.
 * @author Nick Iannone
 * @version 1.1 5/5/11
 */
public class ColleagueProxy implements Colleague {
	
	protected Mediator mediator;
	protected String name;
	
	/**
	 * The disconnection string.
	 * @since 1.1
	 */
	private static final String DISCON_STRING = "DISCONNECT";
	
	/**
	 * The socket to communicate with.
	 */
	private Socket socket;

	/**
	 * The input stream from the client.
	 */
	private ObjectInputStream inputStream;
	
	/**
	 * The output stream to the client.
	 */
	private ObjectOutputStream outputStream;
	
	/**
	 * Whether the connection has been closed or not.
	 */
	private boolean isClosed;
	
	/**
	 * Constructor. Serves as a stand-in proxy for the Colleague, which passes data between the server and the client socket.
	 * @param server The chat server to feed back to.
	 * @param socket The socket to communicate with.
	 * @throws IOException If the socket is not connected, or an error occurs in opening the input or output streams.
	 */
	public ColleagueProxy(ChatServer server, Socket socket) throws IOException {
		name = ChatServer.getDefaultName();
		mediator = server;
		isClosed = false;
		this.socket = socket;
		if (socket.isConnected()) {
			this.inputStream = new ObjectInputStream(socket.getInputStream());
			this.outputStream =  new ObjectOutputStream(socket.getOutputStream());
		} else {
			isClosed = true;
			throw new IOException("Socket not connected!");
		}
		// Start the update thread.
		startUpdateThread();
	}
	
	/**
	 * Starts a thread to capture incoming messages from the remote colleague.
	 */
	private void startUpdateThread() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!socket.isInputShutdown()) {
					try {
						// Get the message from the client.
						Object o = inputStream.readObject();
						if (o instanceof Message) {
							Message msg = (Message)o;
							// Handle disconnect requests.
							if (DISCON_STRING.equals(msg.sender)) {
								isClosed = true;
								break;
							}
							// Handle name changes.
							if (name != null && !name.equals(msg.sender)) {
								// Send a name change notification.
								if (mediator instanceof ChatServer) {
									ChatServer server = (ChatServer)mediator;
									server.onNameChanged(name, msg.sender);
								}
								// Update the name.
								name = msg.sender;
							}
							// Forward the client's message to the server.
							sendMessage(msg.message);
						}
					} catch (SocketException e) {
						isClosed = true;
						break;
					} catch (IOException e) {
						isClosed = true;
						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				// Mark the proxy as closed.
				isClosed = true;
			}
		});
		t.start();
	}
	
	/**
	 * @see chat.Colleague#receiveMessage(chat.Message)
	 */
	@Override
	public void receiveMessage(Message msg) {
		if (!socket.isOutputShutdown()) {
			try {
				outputStream.writeObject(msg);
			} catch (SocketException e) {
				isClosed = true;
			} catch (IOException e) {
				isClosed = true;
			}
		} else {
			isClosed = true;
		}
	}
	
	/**
	 * @see chat.Colleague#sendMessage(String)
	 */
	@Override
	public void sendMessage(String msg) {
		mediator.sendMessage(this, msg);
	}

	/**
	 * Tells whether the colleague connection has been closed or not.
	 * @return <b>true</b> if the connection is closed, <b>false</b> otherwise.
	 */
	public boolean isClosed() {
		return isClosed;
	}
	
	/**
	 * Gets the colleague's name.
	 * @return The colleague's name.
	 */
	public String getName() {
		return name;
	}

}
