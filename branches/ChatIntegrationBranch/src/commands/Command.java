/*- SocketData.java -----------------------------------------------+
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

package commands;

import old.ConnectionInterface;

/* -------------------- JavaDoc Information ----------------------*/
/**
 * Interface to represent data to be transfered over a socket.
 * Any data that needs to be sent over the socket must implement this 
 * method and Serializable so it can be serialized and sent over the socket
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
public interface Command {
	/**
	 * This method is what calls the aproprate functions on
	 * the recieving end of the socket
	 * @param sc The reference to the recieving connection
	 */
	public void execute(ConnectionInterface sc);
}