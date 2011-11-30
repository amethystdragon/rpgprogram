/*- ClientConnection.java -----------------------------------------+
 |                                                                 |
 |  Copyright (C) 2002-2003 Joseph Monti, LlamaChat                |
 |                     countjoe@users.sourceforge.net              |
 |                     http://www.42llamas.com/LlamaChat/          |
 |                                                                 |
 | This program is free software; you can redistribute it and/or   |
 | modify it under the terms of the GNU General Public License     |
 | as published by the Free Software Foundation; either version 2  |
 | of the License, or (at your option) any later version           |
 |                                                                 |
 | This program is distributed in the hope that it will be useful, |
 | but WITHOUT ANY WARRANTY; without even the implied warranty of  |
 | MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   |
 | GNU General Public License for more details.                    |
 |                                                                 |
 | A copy of the GNU General Public License may be found in the    |
 | installation directory named "GNUGPL.txt"                       |
 |                                                                 |
 +-----------------------------------------------------------------+
 */

package old;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLSocket;


import commands.SetGM;
import commands.AddUser;
import commands.Channel;
import commands.Chat;
import commands.Command;
import commands.Error;
import commands.Kick;
import commands.Log;
import commands.Private;
import commands.RemoveUser;
import commands.Rename;
import commands.Whisper;

/* -------------------- JavaDoc Information ----------------------*/
/**
 * A threaded connection class to maintain the conneciton to the client
 * a new ClientConnection must be create for each new client
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
public class ClientConnection implements Runnable, ConnectionInterface {
	private ChatServer server;
	private SSLSocket socket;
	public String name;
	public String ip;
	public String channel;

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean finalized;
	private boolean admin;
	
	private MessageQueue queue;

	ClientConnection(ChatServer serv, SSLSocket sock) {
		try {
			name = null;
			finalized = false;
			admin = false;
			channel = ChatServer.channels.defaultChannel;
			server = serv;
			socket = sock;
			ip = sock.getInetAddress().getHostName();
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			queue = new MessageQueue(this);
			new Thread(this).start();
		} catch (IOException e) {
			server.log(this, "failed connection");
			ChatServer.connectingUsers.remove(this);
		}
	}

	/**
	 * Method to read an object from the client
	 * @return returns the SocketData object recieved
	 */
	private Command readObject() {
		try {
			return (Command) in.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Method to write an object to the server via MessageQueue
	 * @param sd 	A SocketData object to be sent
	 */
	public void writeObject(Object obj) {
		queue.enqueue(obj);
	}

	/**
	 * Method to actually write an object to the server
	 * @param sd 	A SocketData object to be sent
	 */
	public void _writeObject(Object obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			close();
		}
	}

	/**
	 * closes the connection to the client
	 */
	public void close() {
		server.kill(this);
		try {
			socket.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

	/**
	 * utility method to retrieve the admin status
         * @return true if admin
	 */
	public boolean isAdmin() {
		return admin;
	}

// -- Interface methods -- \\
	/**
	 * receives server configurations
	 * @param type	the type of data being sent
	 * @param obj	the object being sent
	 */
	public void serverCap(char type, Object obj) {
		// does nothing ... eventually will be able to configure from client
	}

	/**
	 * used to finalize the conneciton to the client.
	 * when a user connects he must send a SD_UserAdd to tell
	 * the server the requested name
	 * @param username	the name of the client
	 */
	public void addUser(String username) {
		if (username == null) {
			writeObject(new Error("no username recieved"));
			return;
		}
		if (name == null && !finalized) {
			name = username;
			finalized = server.finalizeUser(username, this);
			if (!finalized) {
				name = null;
				writeObject(new Kick(null));
			}
		} else {
			writeObject(new Error("already recieved name: " + name));
		}
	}

	/**
	 * used by a client to attain administrative status
	 * @param password	the password provided by the client
	 */
	public void setGM(String password) {
		if (!ChatServer.allowAdmin) {
			writeObject(new Error("admins are not allow on this server"));
		} else if (password.equals(ChatServer.adminPass)) {
			admin = true;
			server.broadcast(new SetGM(name), null, channel);
		} else {
			writeObject(new Error("incorrect administrative password"));
		}
	}

	/**
	 * Tells the server that the client wishes to disconnect
     * @param username the name of the user
	 */
	public void removeUser(String username) {
		close();
	}

	/**
	 * used to rename the client
	 * <i>ADD USERNAME VALIDITY CHECK</a>
	 * @param on	the old name of the client
	 * @param nn	the new (requested) name of the client
	 */
	public void rename(String on, String nn) {
		if (ChatServer.connectedUsers.containsKey(nn)) {
			writeObject(new Error("username already exists"));
			//writeObject(new SD_Rename(nn, name));
		} else {
			ChatServer.connectedUsers.remove(name);
			ChatServer.connectedUsers.put(nn, this);
			server.broadcast(new Rename(name, nn), nn, channel);
			ChatServer.updateUserExport();
			name = nn;
		}
	}

	/**
	 * used to kick a user; eventually IP banning will be an added
	 * option (called ban), but will use SD_Kick
     * @param username the name of the user to kicko
	 */
	public void kick(String username) {
		if (admin) {
			ClientConnection cc =
				(ClientConnection) ChatServer.connectedUsers.get(username);
			if (cc == null) {
				writeObject(new Error("user " + username +
												" does not exist"));
			} else {
				server.sendTo(new Error("You have been kicked by " + name),
												username);
				cc.close();
			}
		} else {
			writeObject(new Error("Couldn't verify administrative status"));
		}
	}

	/**
	 * recieves a channel change
	 * @param nc	true for a new channel, if the channel exists an error is
	 				sent back to the user, otherwise the channel is created
					and a recursive call is made to switch the user to
					the new channel; if false it checks the validity of the
					request and move the user
	 * @param n		the name of the channel
     * @param p     password for the channel, if one
	 */
	public void channel(boolean nc, String n, String p) {
		if (nc) {
			String reason;
			if ((reason = server.newChannel(n, p, this)) == null) {
				server.log(this, "channel " + n + " created");
				channel(false, n, p);
			} else {
				writeObject(new Error(reason));
			}
		} else {
			if (ChatServer.channels.channelExists(n)) {
				if (channel.equals(n)) {
					writeObject(new Error("Already a member of " + n));
					writeObject(new Channel(false,null,null));
				} else if (!ChatServer.channels.userAdd(n, p)) {
					writeObject(new Error("invalid passphrase or none " +
								"provided, use \\join " + n + " &lt;password&gt;"));
					writeObject(new Channel(false,null,null));
				} else {
					server.broadcast(new RemoveUser(name), name, channel);
					if (ChatServer.channels.userDel(channel)) {
						server.broadcast(new Channel(true, channel, null), null);
						server.chatLog(this, false);
						server.log(this, "channel " + channel + " removed");
					}
					channel = n;
					server.broadcast(new AddUser(name), name, channel);
					if (admin) {
						server.broadcast(new SetGM(name), name, channel);
					}

					writeObject(new Channel(false, channel, null));
					server.sendUserList(this);
					ChatServer.updateUserExport();
				}
			} else {
				writeObject(new Error(n  + " does not exist"));
				writeObject(new Channel(false,null,null));
			}
		}
	}


	/**
	 * recives a chat message from the user
	 * @param username	the name of the user, not used in this case
	 					(it should be null)
	 * @param message	the messsage sent by the user
	 */
	public void chat(String username, String message) {
		if (finalized) {
			server.chatLog(this, message);
			server.broadcast(new Chat(name, message), name, channel);
		} else {
			writeObject(new Error("connection not confirmed"));
		}
	}

	/**
	 * recives a private message from the user
	 * @param username	the name of the user to whom the message is sent
	 * @param message	the messsage sent by the user
	 */
	public void private_msg(String username, String message) {
		if (finalized) {
			server.sendTo(new Private(name, message), username);
		} else {
			writeObject(new Error("connection not confirmed"));
		}
	}

	/**
	 * whispers to a user
	 * @param username	the name of the user that the whisper is to be sent
	 * @param message	the message that is to be whispered
	 */
	public void whisper(String username, String message) {
		if (finalized) {
			server.sendTo(new Whisper(name, message), username);
		} else {
			writeObject(new Error("connection not confirmed"));
		}
	}

	/**
	 * used to control the chat logging status
	 * @param start		true to start logging, false to stop
	 */
	public void chatLog(boolean start) {
		if (admin) {
			if (server.chatLog(this, start)) {
				writeObject(new Log(start));
			} else {
				writeObject(new Error("unable to modify log for " + channel));
			}
	   } else {
		   writeObject(new Error("Could not verify administrative status"));
	   }
	}

	/**
	 * recieves an error from the client
     * @param err the error to report
	 */
	public void error(String err) {
		server.log(this, err);
	}

// -- Interface methods -- \\

	/**
	 * Method to implement runnable, listens
	 * for incoming objects
	 */
	public void run() {
		Command sd;
		while ((sd = readObject()) != null) {
			sd.execute(this);
		}
		close();
	}
}
