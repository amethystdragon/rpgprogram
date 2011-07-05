package chat.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.Server.ChatClientConnection;

public class ChatClient extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6763899869344364214L;
	public static final int PORT = 98327;
	public String site;
	private boolean connected;
	private JTextArea area;
	private JTextField field;
	private JButton send;
	
	public static void main(String[] arg){
		JFrame window = new JFrame();
		window.setName("Chat Test");
		window.add(new ChatClient());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(326, 500);
		window.setVisible(true);
	}

	public ChatClient(){
		this.area = new JTextArea();
		this.field = new JTextField();
		this.send = new JButton("Send");
		createUI();
		repaint();
	}
	
	public void setConnected(boolean bool) {
		this.connected = bool;
	}

	public void error(String string) {
		this.area.setForeground(Color.RED);
		this.area.setText(this.area.getText()+string+'\n');
		this.area.setForeground(Color.WHITE);
	}

	private void createUI(){
		//Sets the panel background
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(326, 350));
		this.setVisible(true);
		
		//Sets the viewable text area
		this.area.setEditable(false);
		this.area.setBackground(Color.BLACK);
		this.area.setForeground(Color.WHITE);
		this.area.setAutoscrolls(true);
		this.area.setLineWrap(true);
		//Wraps in a scroll pane and adds it
		this.add(new JScrollPane(this.area), BorderLayout.CENTER);
		
		//Creates a panel to add the button and the text field to
		JPanel bottom = new JPanel();
		bottom.setSize(300, 150);
		bottom.setLayout(new FlowLayout());
		
		//Sets the writing text field
		this.field.addKeyListener(new KeyboardListener());
		this.field.setPreferredSize(new Dimension(200, 50));
		bottom.add(this.field);
		
		//Sets up the send button
		this.send.addActionListener(new ButtonHandler());
		this.send.setActionCommand("send");
		this.send.setPreferredSize(new Dimension(100, 50));
		bottom.add(this.send);
		//Adds the send button and the text field to the the panel
		this.add(bottom, BorderLayout.PAGE_END);
	}
	
	private class KeyboardListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar()=='\n'){
				//Checks for a blank line
				if(field.getText().equals("")) return; 
				//Adds the field text to the area text
				area.setText(area.getText() + '\n' + field.getText());
				//clears the field text
				field.setText("");
			}
		}
	}
	private class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand().equals("send")){
				//Checks for a blank line
				if(field.getText().equals("")) return; 
				//Adds the field text to the area text
				area.setText(area.getText() + '\n' + field.getText());
				//clears the field text
				field.setText("");
			}
		}
		
	}
}