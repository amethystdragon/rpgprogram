/**********************************************************
 * FILE:	Command.java
 * AUTHOR:	Karl Schmidbauer (schmidbauerk@msoe.edu)
 * DATE:	14-Apr-2011 10:06:32 PM
 * PROVIDES: Provides an interface for all commands
 *********************************************************/
package commands;
/**
 * Interface providing common functionality for all commands
 * 
 * @author schmidbauerk
 * @version 1.0
 * @created 05-May-2011 1:49:44 PM
 */
public interface Command {
	/**
	 * Executes the command
	 */
	public void execute();
}