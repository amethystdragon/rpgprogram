/*- SD_Whisper.java -----------------------------------------------+
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

package chat.commands;

import java.io.Serializable;
import java.util.Scanner;

import chat.Message;
import chat.Server.ChatServer;


/* -------------------- JavaDoc Information ----------------------*/
/**
 * Replace information on this file with your file's information.
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
public class Tell implements Command, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 706760033052528601L;
	private String sender;
	private String message;
	private String listener;
	private ChatServer server;

	public Tell(ChatServer chat, Message msg) {
		this.server = chat;
		
		sender = msg.getSender();
		
		Scanner scan = new Scanner(msg.getMessage().substring(6));
		listener = scan.next();
		message = msg.getMessage().substring(6+listener.length());
		
	}

	public void execute() {
		this.server.tell(this.listener, new Message(this.sender, this.message));
	}
}
