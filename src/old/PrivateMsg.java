/*- ChatPane.java -------------------------------------------------+
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/* -------------------- JavaDoc Information ----------------------*/
/**
 * This handles private messages
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
public class PrivateMsg {
	ChatClient chatClient;
	private Hashtable<String, MsgWindow> privates;
	
	PrivateMsg(ChatClient lc) {
		chatClient = lc;
		privates = new Hashtable<String, MsgWindow>();
	}
	
	
	public void recievePrivate(String username, String message) {
		MsgWindow msg = newPrivate(username);
		if (msg != null) {
			msg.cp.sendText(username, message);
		}
	}
	
	public MsgWindow newPrivate(String username) {
		if (!chatClient.users.contains(username)) {
			chatClient.error(username  + " does not exist");
			return null;
		}
		MsgWindow msg = null;
		if (privates.containsKey(username)) {
			msg = (MsgWindow) privates.get(username);
		} else {
			msg = new MsgWindow(username);
			privates.put(username, msg);
		}
		return msg;
	}
	
	public void serverMessage(String username, String message) {
		if (privates.containsKey(username)) {
			MsgWindow msg = (MsgWindow) privates.get(username);
			msg.cp.sendText("server", message);
		}
	}
	
	private class MsgWindow extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2771587594265774988L;
		ChatPane cp;
		JTextField messageText;
		String username;
		MsgWindow(String un) {
			super("Private Message: " + un);
			username = un;
			setSize(new Dimension(350, 200));
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					MsgWindow cur = (MsgWindow) e.getWindow();
					Enumeration<String> enumer = privates.keys();
					while (enumer.hasMoreElements()) {
						String user = (String) enumer.nextElement();
						MsgWindow tmpFra = (MsgWindow) privates.get(user);
						if (cur.equals(tmpFra)) {
							privates.remove(user);
							break;
						}
					}
				}
			});
			cp = new ChatPane(chatClient);
			messageText = new JTextField();
			
			messageText.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent ke) {
					if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
						String txt = new String(messageText.getText());
						if (!txt.equals("")) {
							if (!chatClient.users.contains(username)) {
								cp.error(username  + " does not exist");
							} else {
								chatClient.sendPrivate(username, txt);
								cp.sendText(chatClient.username, txt);
							}
							messageText.setText("");
						}
					}
				}
				public void keyPressed(KeyEvent ke) {
					//
				}
				public void keyReleased(KeyEvent ke) {
					//
				}
			});
			getContentPane().add(new JScrollPane(cp, 
								JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
			getContentPane().add(messageText, BorderLayout.SOUTH);
			setVisible(true);
			cp.sendText("server", "Private message session started with " + un);
		}
	}
}
