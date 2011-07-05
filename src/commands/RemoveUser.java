package commands;

import chat.Server.ChatServer;

public class RemoveUser implements Command {

	private String username;
	private ChatServer server;
	
	
	public RemoveUser(String username){
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
