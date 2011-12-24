/*- LlamaChat.java ------------------------------------------------+
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chat.commands.AddUser;
import chat.commands.Channel;
import chat.commands.Chat;
import chat.commands.Kick;
import chat.commands.Log;
import chat.commands.Private;
import chat.commands.RemoveUser;
import chat.commands.Rename;
import chat.commands.SetGM;
import chat.commands.Whisper;


/* -------------------- JavaDoc Information ----------------------*/
/**
 * For use in a webpage:<br><pre>
&lt;OBJECT 
   classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
    width="615" height="360" 
    codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,mn"&gt;
  &lt;PARAM name="code" value="LlamaChat.class"&gt;
  &lt;PARAM name="archive" value="LlamaChat.jar"&gt;
  &lt;PARAM name="type" value="application/x-java-applet;version=1.4"&gt;
  &lt;PARAM name="scriptable" value="true"&gt;
  &lt;param name="username" value="[replace with username]"&gt;
  &lt;param name="port" value="[replace with port]"&gt;
&lt;COMMENT&gt;
&lt;EMBED type="application/x-java-applet;version=1.4" 
   width="615" height="331" 
   code="LlamaChat.class" archive="LlamaChat.jar"
   pluginspage="http://java.sun.com/j2se/1.4.1/download.html"
 username="[replace with username]"
 port="[replace with port]"&gt;
 	&lt;NOEMBED&gt;
		No Java 1.4 plugin
	&lt;/NOEMBED&gt;&lt;/EMBED&gt;
&lt;/COMMENT&gt;
&lt;/OBJECT&gt;
 </pre>
 This is the LlamaChat client applet;  The above html needs values for
 username, port, site, and location; Username is the name of the connecting
 user, port is the port on which the server is running, site is the
 IP address or hostname of the server, location is the location of the
 client applet on the web
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
/*
For use in a webpage:
<OBJECT 
   classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
    width="615" height="360" 
    codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,mn">
  <PARAM name="code" value="client/LlamaChat.class">
  <PARAM name="archive" value="LlamaChat.jar">
  <PARAM name="type" value="application/x-java-applet;version=1.4">
  <PARAM name="scriptable" value="true">
  <param name="username" value="[replace with username]">
  <param name="port" value="[replace with port]">
<COMMENT>
<EMBED type="application/x-java-applet;version=1.4" 
   width="615" height="331" 
   code="client/LlamaChat.class" archive="LlamaChat.jar"
   pluginspage="http://java.sun.com/j2se/1.4.1/download.html"
 username="[replace with username]"
 port="[replace with port]">
 	<NOEMBED>
		No Java 1.4 plugin
	</NOEMBED></EMBED>
</COMMENT>
</OBJECT>
*/
public class ChatClient extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4547705344307863288L;
	public String username;
	private ServerConnection server;
	public int PORT;
	public ArrayList<String> users;
	private CommandHistory history;
	public ArrayList<String> afks;
	public ArrayList<String> admins;
	public boolean admin;
	public InetAddress address;
	private final String linkURL = "http://joe.tgpr.org/LlamaChat";
	public Hashtable<String, Boolean> channels;
	private static final String VERSION = "v0.8";
	public PrivateMsg privates;
	public boolean showUserStatus;
	public boolean chanAdmin;
	
	private ChatPane mainChat;
	private JList userList;
	private JTextField messageText;
	private JScrollPane textScroller;
	private JPopupMenu popup;
	protected JComboBox cboChannels;
	private JButton butChannel;
	protected JButton butCreate;
	
	private ImageIcon conNo;
	private ImageIcon conYes;
	private ImageIcon secNo;
	private ImageIcon secYes;
	private JLabel conIcon;
	private JLabel secIcon;
	private JLabel bottomText;
	
	
	private MyAction myAction;
	private MyKeyListener myKeyListener;
	private MyMouseListener myMouseListener;
	protected MyHyperlinkListener myHyperlinkListener;
	private Color myColors[] = new Color[3];
	
	
	public static void main(String[] arg){
		JFrame frame = new JFrame("Test");
		frame.add(new ChatClient());
		frame.setSize(500, 500);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public ChatClient(){
		if (username == null) {
			username = JOptionPane.showInputDialog(this, "Please enter a username", 								"Login",
									JOptionPane.QUESTION_MESSAGE);
		}
		PORT = 42412;
		
		try {
			byte[] temp = {127, 0, 0, 1};
			address = InetAddress.getByAddress(temp);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		createUI();
	}
	
	
	/**
	 * Initializes the graphical components
	 */
	private void createUI() {
		setSize(615,362);
		
		setBackground(new Color(224,224,224));
		
		myAction = new MyAction();
		myKeyListener = new MyKeyListener();
		myMouseListener = new MyMouseListener();
		myHyperlinkListener = new MyHyperlinkListener();

		setLayout(null);
		
		cboChannels = new JComboBox();
		cboChannels.setBounds(5, 5, 150, 24);
		
		butChannel = new JButton("Join");
		butChannel.setToolTipText("Join channel");
		butChannel.addActionListener(myAction);
		butChannel.setBounds(160, 5, 60, 24);
		
		butCreate = new JButton("Create");
		butCreate.setToolTipText("Create new channel");
		butCreate.addActionListener(myAction);
		butCreate.setBounds(230, 5, 80, 24);
		butCreate.setEnabled(false);
		
		mainChat = new ChatPane(this);
		textScroller = new JScrollPane(mainChat, 
								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textScroller.setBounds(5,34,500,270);
				
		userList = new JList();
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setCellRenderer(new MyCellRenderer());
		userList.setBackground(new Color(249, 249, 250));
		JScrollPane userScroller = new JScrollPane(userList);
		userScroller.setBounds(510,34,100,297);
		
		messageText = new JTextField();
		messageText.setBounds(5,309,500,22);
		messageText.setColumns(10);
		messageText.setBackground(new Color(249, 249, 250));
		
		popup = new JPopupMenu("test");
		popup.add("whisper").addActionListener(myAction);
		popup.add("private message").addActionListener(myAction);
		popup.add("ignore").addActionListener(myAction);
		popup.add("clear ignore list").addActionListener(myAction);
		
		conNo = new ImageIcon("images/connect_no.gif");
		conYes = new ImageIcon("images/connect_established.gif");
		secNo = new ImageIcon("images/decrypted.gif");
		secYes = new ImageIcon("images/encrypted.gif");

		conIcon = new JLabel(conNo);
		conIcon.setBorder(new EtchedBorder());
		secIcon = new JLabel(secNo);
		secIcon.setBorder(new EtchedBorder());
		

		conIcon.setBounds(563,334, 22, 22);
		secIcon.setBounds(588,334, 22, 22);
		
		bottomText = new JLabel("<html><body><font color=#445577><b>" +
						"LlamaChat " + VERSION + "</b></font> &nbsp;&copy; " +
						"<a href=\"" + linkURL+ "\">Joseph Monti</a> 2002-2003" +
						"</body></html>");
		bottomText.setBounds(5, 336, 500, 20);
		
		add(cboChannels);
		add(butChannel);
		add(butCreate);
		add(textScroller);
		add(userScroller);
		add(messageText);
		add(conIcon);
		add(secIcon);
		add(bottomText);
		
		userList.addMouseListener(myMouseListener);
		messageText.addKeyListener(myKeyListener);
		bottomText.addMouseListener(myMouseListener);
		
		users = new ArrayList<String>();
		afks = new ArrayList<String>(5);
		admins = new ArrayList<String>(5);
		history = new CommandHistory(10);
		admin = false;
		channels = new Hashtable<String, Boolean>();
		privates = new PrivateMsg(this);
		showUserStatus = false;
		
		myColors[0] = new Color(200, 0, 0);
		myColors[1] = new Color(0, 150, 0);
		myColors[2] = new Color(0, 0, 200);
		
		String opening = "<font color=#333333>" +
						 "==================================<br>" +
						 "Welcome to LlamaChat " + VERSION + "<br>" +
						 "If you need assistance, type \\help<br>" +
						 "Enjoy your stay!<br>" +
						 "==================================<br></font>";
		HTMLDocument doc = (HTMLDocument) mainChat.getDocument();
		HTMLEditorKit kit = (HTMLEditorKit) mainChat.getEditorKit();
		try {
			kit.insertHTML(doc,doc.getLength(),opening,0,0,HTML.Tag.FONT);
		} catch (Throwable t) { t.printStackTrace(System.out); }

		// validate the name
		if (!username.matches("[\\w_-]+?")) {
			error("username contains invalid characters, changing to " +
						"'invalid' for now. " +
						"Type \\rename to chose a new name");
			username = "invalid";
		}
		if (username.length() > 10) {
			username = username.substring(0,10);
			error("username too long, changed to " + username);
		}

		connect();
	}
	
	public void start() { }
	
	public void stop() {
		if (server != null)
			server._writeObject(new RemoveUser(null)); // to bypass the queue
	}
	
	/**
	 * Subclass to act as the action listener for graphical components
	 */
	protected final class MyAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String cmd = ae.getActionCommand().intern();
			if (ae.getSource() == butChannel) {
				String pass = null;
				String channel = (String) cboChannels.getSelectedItem();
				if (channels.containsKey(channel) && ((Boolean) channels.get(channel)).booleanValue()) {
					String message = "Password for " +  channel + 
									"? (blank for no password)";
					pass = JOptionPane.showInputDialog(
									(Component) ae.getSource(),
									message,
									"Join Channel",
									JOptionPane.QUESTION_MESSAGE);
				}
				server.writeObject(new Channel(false, channel, 
							("".equals(pass) ? null : pass)));
				showUserStatus = false;
			} else if (ae.getSource() == butCreate) {
				String channel = JOptionPane.showInputDialog(
									(Component) ae.getSource(),
									"Enter the name of the channel",
									"Create Channel",
									JOptionPane.INFORMATION_MESSAGE);
				if (channel == null) return;
				String message = "Password for " +  channel + 
									"? (blank for no password)";
				String pass = JOptionPane.showInputDialog(
									(Component) ae.getSource(),
									message,
									"Join Channel",
									JOptionPane.QUESTION_MESSAGE);
				server.writeObject(new Channel(true, channel, 
									("".equals(pass) ? null : pass)));
				showUserStatus = false;
			} else if (cmd == "whisper") {
				String user = (String)userList.getSelectedValue();
				if (user != null && !messageText.getText().equals("") && 
													!user.equals(username)) {
					messageText.setText("\\whisper " + user + " " + 
													messageText.getText());
					sendMessage();
				} else {
					error("invalid user or no message, type a message below," +
							" select a user, and then whisper");
				}
			} else if (cmd == "private message") {
				String user = (String)userList.getSelectedValue();
				if (user != null && !user.equals(username)) {
					privates.newPrivate(user);
				} else {
					error("invalid user");
				}
			} else if (cmd == "kick user") {
				String user = (String)userList.getSelectedValue();
				if (user != null) {
					if (user.equals(username)) {
						error("cannot kick yourself");
					} else {
						server.writeObject(new Kick(user));
					}
				} else {
					error("no user selected");
				}
			}
		}
	}
	
	/**
	 * Subclass to act as the key listener for the graphical componets
	 */
	protected final class MyKeyListener implements KeyListener {
		public void keyTyped(KeyEvent ke) {
			if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
				sendMessage();
			}
		}
		public void keyPressed(KeyEvent ke) {
			//
		}
		public void keyReleased(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_UP) {
				String up = history.getUp();
				if (up != null)
					messageText.setText(up);
			} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
				String down = history.getDown();
				if (down != null)
					messageText.setText(down);
			}
		}
	}
	
	/**
	 * Subclass to act as the mouse listener for the graphical components
	 */
    protected final class MyMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
        }
    
    	public void mouseEntered(MouseEvent e) {
    		if (e.getSource() == bottomText) {
    			bottomText.setCursor(
						Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    		}
    	}
    	public void mouseExited(MouseEvent e) {
    		if (e.getSource() == bottomText) {
    			bottomText.setCursor(Cursor.getDefaultCursor());
    		}
    	}
    
    	public void mouseClicked(MouseEvent e) {
        	if (e.getSource() == bottomText) {
//				try {
//					getAccessibleContext().showDocument(
//										new URL(linkURL), "_blank");
//        		} catch (java.net.MalformedURLException ex) { }
        	} else {
            	maybeShowPopup(e);
			}
		}    		

		/**
		 * Checks to see if the event was to show the popup menu
		 */
        private void maybeShowPopup(MouseEvent e) {
            if ((e.getModifiers() & MouseEvent.BUTTON3_MASK)!=0) {
                popup.show(userList, e.getX(), e.getY());
            }
        }
    }
	
	protected final class MyHyperlinkListener implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//				getAccessibleContext().showDocument(e.getURL(), "_blank");
			}
		}
	}
	
	/**
	 * a Subclass to handle cell items in the user list
	 * lets us modify add an icon to the username
	 */
	protected final class MyCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1555775149326441666L;
		final ImageIcon normal = new ImageIcon("images/face.gif");
		final ImageIcon ignored = new ImageIcon("images/face_ignore.gif");
		final ImageIcon afk = new ImageIcon("images/afk.gif");
		final ImageIcon admin = new ImageIcon("images/admin.gif");

		/* This is the only method defined by ListCellRenderer.  We just
		* reconfigure the Jlabel each time we're called.
		*/
		public Component getListCellRendererComponent(
					JList list,
					Object value,   // value to display
					int index,      // cell index
					boolean iss,    // is the cell selected
					boolean chf)    // the list and the cell have the focus
		{
			/* The DefaultListCellRenderer class will take care of
			* the JLabels text property, it's foreground and background
			* colors, and so on.
			*/
			super.getListCellRendererComponent(list, value, index, iss, chf);
			if (afks.contains(value.toString())) {
				setIcon(afk);
			} else if (admins.contains(value.toString())) {
				setIcon(admin);
			} else {
				setIcon(normal);
			}
			if (username.equals(getText())) {
				setForeground(myColors[1]);
			} else {
				setForeground(myColors[2]);
			}

			return this;
		}
	}
	
	/**
	 * Method called when a user message has been sent.
	 * checks to see if it is a command or regular message
	 */
	private void sendMessage() {
		String txt = new String(messageText.getText());
		if (!txt.equals("")) {
			if (txt.charAt(0) == '\\' || txt.charAt(0) == '/') {
				parseCommand(txt.substring(1));
				messageText.setText("");
				history.add(txt);
			} else if (server != null && server.connected && username != null) {
				sendText(username, txt, false);
				messageText.setText("");
				sendChat(txt);
				history.add(txt);
			} else {
				error("Not connected, type \\reconnect to try and reconnect");
			}
		}
	}
	
	/**
	 * Method to parse a command and perform the specified action
	 * @param cmd	the command that the user typed
	 * @return a status indicator to tell if the command was valid
	 */
	public boolean parseCommand(String cmd) {
		cmd = cmd.intern();
		if (cmd == "help" || cmd == "?") {
			String commands = "Listing  available commands:\n" + 
					"<pre>\\help or \\? \t\t- display this message<br>" +
					"\\admin &lt;passphrase&gt; \t- become an admin<br>" + 
					"\\create &lt;channel&gt; [password]\t- create a new channel with optional password<br>" +
					"\\join &lt;channel&gt;  [password]\t- join channel with optional password<br>" +
					"\\disconnect \t\t- disconnect from server<br>" +
					"\\whisper &lt;user&gt; &lt;message&gt; \t- whisper to a user<br>" + 
					"\\private &lt;user&gt; \t- start a private message session<br>" +
					"\\ignore &lt;user&gt; \t\t- ignores a user<br>" +
					"\\clearignore \t\t- clear list of ignores<br>" +
					"\\reconnect \t\t- attempt to reconnect to server<br>" +
					"\\rename &lt;new name&gt; \t\t- change your username<br>";
			if (admin) {
				commands += "\\kick &lt;user&gt; \t\t- kick user from room<br>" + 
							"\\logstart \t\t- start logging sessoin<br>" + 
							"\\logstop \t\t- stop logging session<br>";
			}
			commands += "up or down \t\t- cycle your chat history</pre>";
			sendText("server", commands, false);
			return true;
		} else if (cmd == "disconnect") {
			if (server != null) {
				stop();
			}
			return true;
		} else if (cmd == "reconnect") {
			if (username == null) {
				error("username still invalid. use \\rename &lt;new name&gt; to chose a new name and reconnect");
			} else {
				if (server != null) {
					stop();
				}
				server = new ServerConnection(this);
			}
			return true;
		} else if (cmd.startsWith("whisper")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\whisper &lt;user&gt; &lt;message&gt;");
				return false;
			}
			cmd = cmd.substring(start+1);
			start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\whisper &lt;user&gt; &lt;message&gt;");
				return false;
			}
			String un = cmd.substring(0, start);
			if (username.equals(un) || !users.contains(un)) {
				error("invalid user");
				return false;
			}
			String message = cmd.substring(start+1);
			if (message.length() < 1) {
				error("usage: \\whisper &lt;user&gt; &lt;message&gt;");
			}
			sendWhisper(un, message);
			sendText(username, message, true);
			return true;
		} else if (cmd.startsWith("private")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\private &lt;user&gt;");
				return false;
			}
			String un = cmd.substring(start+1);
			if (username.equals(un)) {
				error("cannot private message yourself");
				return false;
			}
			privates.newPrivate(un);
			return true;
		} else if (cmd.startsWith("rename")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\rename &lt;newname&gt;");
				return false;
			}
			String newName = cmd.substring(start+1);
			if ((newName.length() < 1) || (newName.length()) > 10 || 
											(newName.equals("server")) ||
											(!newName.matches("[\\w_-]+?"))) {
				error("invalid name");
				return false;
			}
			if (!server.connected) {
				server = new ServerConnection(this);
			}
			if (username == null) {
				username = newName;
				server.writeObject(new AddUser(newName));
			} else {
				rename(username, newName);
				username = newName;
				server.writeObject(new Rename(null, username));
			}
			return true;
		} else if (cmd.startsWith("join")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\join &lt;channel&gt; [password]");
				return false;
			}
			String name = cmd.substring(start+1);
			String pass = null;
			start = name.indexOf(' ');
			if (start > 0) {
				pass = name.substring(start+1);
				name = name.substring(0, start);
			}
			server.writeObject(new Channel(false, name, pass));
			return true;
		} else if (cmd.startsWith("create")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\create &lt;channel&gt; [password]");
				return false;
			}
			String name = cmd.substring(start+1);
			String pass = null;
			start = name.indexOf(' ');
			if (start > 0) {
				pass = name.substring(start+1);
				name = name.substring(0, start);
			}
			server.writeObject(new Channel(true, name, pass));
			return true;
/*		} else if (cmd.startsWith("proxy")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\proxy &lt;host&gt; &lt;port&gt;");
				return false;
			}
			String phost = cmd.substring(start+1);
			start = phost.indexOf(' ');
			if (start < 0) {
				error("usage: \\proxy &lt;host&gt; &lt;port&gt;");
				return false;
			}
			String pport = phost.substring(start+1);
			phost = phost.substring(0, start);
			Properties systemSettings = System.getProperties();
			systemSettings.put("proxySet", "true");
			systemSettings.put("proxyHost", phost);
			systemSettings.put("proxyPort", pport);
			System.setProperties(systemSettings);
			serverMessage("Using " + phost + ":" + pport + " as proxy server<br>you can type \\reconnect to reconnect with this proxy");
			return true; */
		}else if (cmd.startsWith("admin")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\admin &lt;password&gt;");
				return false;
			}
			String pass = cmd.substring(start+1);
			server.writeObject(new SetGM(pass));
			return true;
		} else if (admin && cmd.startsWith("kick")) {
			int start = cmd.indexOf(' ');
			if (start < 0) {
				error("usage: \\kick &lt;user&gt;");
				return false;
			}
			String un = cmd.substring(start+1);
			if (un.equals(username)) {
				error("cannot kick yourself");
				return false;
			}
			server.writeObject(new Kick(un));
			return true;
		} else if (admin && cmd == "logstart") {
			server.writeObject(new Log(true));
			return true;	
		} else if (admin && cmd == "logstop") {
			server.writeObject(new Log());
			return true;
		}
		error("unrecognized command, type \\help for help");
		return false;
	}
	
	
	/**
	 * Sends a text to the chat window. Parses the message to pick
	 * out emoticons.
	 * @param un	the name of the user sending the message
	 * @param message	the message to be sent
	 * @param whisper	indicates the message was a wisper and makes the 
	 *					message italic
	 */
	public void sendText(String un, String message, boolean whisper) {
		mainChat.sendText(un, message, whisper);
	}
	
	/**
	 * Create a new connection to a server
	 */
	private void connect() {
		server = new ServerConnection(this);
	}
	
	/**
	 * sends a new message to the server
	 * @param message	the message to be sent
	 */
	private void sendChat(String message) {
		if (server != null) {
			server.writeObject(new Chat(null, message));
		}
	}
	
	/**
	 * sends a whisper to the server
	 * @param message	the message to be sent
	 */
	private void sendWhisper(String to, String message) {
		if (server != null) {
			server.writeObject(new Whisper(to, message));
		}
	}
	
	/**
	 * sorts the user list and rebuilds the user list from 
	 * the sorted user vector,
	 */ 
	public void updateList() {
		Object[] tmp = users.toArray();
		Arrays.sort(tmp);
		userList.setListData(tmp);
	}
	
	/**
	 * adds a new user to the interal list of users
	 * @param un	the name of the user to be added
	 */
	public void userAdd(String un) {
		if (!users.contains(un)) {
			users.add(un);
			updateList();
			if (!un.equals(username) && showUserStatus)
				serverMessage(un + " has joined " + server.channel);
		}
	}
	
	/**
	 * removes a user from the user list
	 * @param un	the name of the user to be removed
	 */
	public void userDel(String un) {
		users.remove(users.indexOf(un));
		if (afks.contains(un)) {
			afks.remove(afks.indexOf(un));
		}
		if (admins.contains(un)) {
			admins.remove(admins.indexOf(un));
		}
		updateList();
		serverMessage(un + " has left " + server.channel);
		privates.serverMessage(un, un + " has left");
	}
	
	/**
	 * changes the name of a user, updating list of admins,
	 * afks, ignoes, and master user list
	 * @param on	old username
	 * @param nn	new username
	 */
	public void rename(String on, String nn) {
		if (admins.contains(on)) {
			admins.remove(admins.indexOf(on));
			admins.add(nn);
		}
		if (afks.contains(on)) {
			afks.remove(afks.indexOf(on));
			afks.add(nn);
		}
		users.remove(on);
		users.add(nn);
		updateList();
		serverMessage(on + " renamed to " + nn);
	}
	
	/**
	 * Recieving method for a chat message
	 * @param un	the name of the user sending the chat
	 * @param message	the message that was sent
	 */
	public void recieveChat(String un, String message) {
		sendText(un, message, false);
	}
	
	/**
	 * Reciving method for a whisper
	 * @param un	the name of the user sending the whisper
	 * @param message	the message that was sent
	 */
	public void recieveWhisper(String un, String message) {
		sendText(un, message, true);
	}
	
	/**
	 * Recieving method for private
	 * @param un	the name of the user sending the message
	 * @param message	the message that was sent
	 */
	public void recievePrivate(String un, String message) {
		privates.recievePrivate(un, message);
	}
	
	/**
	 * signifies an error and reports it to the user
	 * @param s	the error message
	 */
	public void error(String s) {
		mainChat.error(s);
	}
	
	/**
	 * A shortcut to be used when a message from the server (or any automated
	 * message) must ben sent to the client
	 * @param s	the message
	 */
	public void serverMessage(String s) {
		sendText("server", s, false);
	}
	
	/**
	 * cleans up a connection by removing all user from all maintained lists
	 */
	public void close() {
		error("Connection to server was lost");
		admin = false;
		users.clear();
		afks.clear();
		admins.clear();
		channels.clear();
		cboChannels.removeAllItems();
		updateList();
	}
	
	/**
	 * used to manage the connection icons to signify connection and
	 * secure status
	 * @param b	true if connected
	 */
	public void setConnected(boolean b) {
		if (b) {
			conIcon.setIcon(conYes);
			conIcon.setToolTipText("Connected");
			secIcon.setIcon(secYes);
			secIcon.setToolTipText("Secure Connection");
			butChannel.setEnabled(true);
		} else {
			conIcon.setIcon(conNo);
			conIcon.setToolTipText("Not Connected");
			secIcon.setIcon(secNo);
			secIcon.setToolTipText("Connection not Secured");
			butChannel.setEnabled(false);
			butCreate.setEnabled(false);
		}
	}
	
	/**
	 * sets this user to be an admin
	 */
	public void setAdmin() {
		admin = true;
		popup.add("kick user").addActionListener(myAction);
		if (chanAdmin) {
			butCreate.setEnabled(true);
		}
	}
	
	/**
	 * creates a new channel, if the channel exists it is removed
	 * (this method doubles as a removeChannel)
	 * @param name	the name of the channel
	 */
	public void newChannel(String name, boolean pass) {
		if (channels.containsKey(name)) {
			channels.remove(name);
			cboChannels.removeItem(name);
		} else {
			channels.put(name, new Boolean(pass));
			cboChannels.addItem(name);
		}
	}
	
	public void sendPrivate(String name, String message) { 
		server.writeObject(new Private(name, message));
	}


	public InetAddress getAddress() {
		return address;
	}
}

