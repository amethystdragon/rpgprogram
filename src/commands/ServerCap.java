/*- SD_ServerCap.java ---------------------------------------------+
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

import java.io.Serializable;

import old.ConnectionInterface;

/* -------------------- JavaDoc Information ----------------------*/
/**
 * Replace information on this file with your file's information.
 * @author Joseph Monti <a href="mailto:countjoe@users.sourceforge.net">countjoe@users.sourceforge.net</a>
 * @version 0.8
 */
public class ServerCap implements Command, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1346600761408163576L;
	public static final char T_CREATE = 0x00;
	private char type;
	private Object obj;
	
	public ServerCap(char t, Object o) {
		type = t;
		obj = o;
	}

	public void execute(ConnectionInterface sc) {
		sc.serverCap(type, obj);
	}
}
