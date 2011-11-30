package commands;

import chat.Server.ChatServer;

public class AddUser implements Command{

	private String username = null;
	private ChatServer server = null;
	
	public AddUser(String username){
		this.username = username;
	}

	@Override
	public void execute() {
		if(server == null) return;
		//TODO Finish this
	}

	public void execute(ChatServer server){
		this.server = server;
		execute();
	}
	
}
