package chat.Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import commands.Command;

public class ChatServer extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7980659617631701518L;
	/**
	 * String - Username
	 * 
	 */
	HashMap<String, ChatClientConnection> clients;
	/**
	 * Keeps track of the last 15 commands
	 */
	Stack<Command> commands;
	
	private static final int MAX_COMMANDS = 15;
	
	private static final int PORT = 98327;
	
	private JTextArea area;
	
	public void addCommand(Command command){
		if (this.commands.size() >= MAX_COMMANDS)
			this.commands.remove(0);
		this.commands.push(command);
		command.execute();
	}
	
	
	private void createUI(){
		//Sets the panel background
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 200));
		this.setVisible(true);
		
		//Sets the viewable text area
		this.area.setEditable(false);
		this.area.setBackground(Color.BLACK);
		this.area.setForeground(Color.WHITE);
		this.area.setAutoscrolls(true);
		this.area.setLineWrap(true);
		//Wraps in a scroll pane and adds it
		this.add(new JScrollPane(this.area), BorderLayout.CENTER);
	}
	
	private void addText(String text){
		
	}
}
