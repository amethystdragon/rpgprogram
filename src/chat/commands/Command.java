package chat.commands;


public interface Command {
	/**
	 * This method is what calls the appropriate functions on
	 * the receiving end of the socket
	 * @param sc The reference to the receiving connection
	 */
	public void execute();
}
