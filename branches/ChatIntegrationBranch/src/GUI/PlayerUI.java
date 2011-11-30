package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mapGenerator.Displaytest;
import chat.Client.ChatClient;

public class PlayerUI extends JPanel{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2457263062148886999L;
	
	private JFrame container;
	
	public PlayerUI(JFrame container) {
		this.container = container;
		createUI();
	}
	
	
	private void createUI(){
		//TODO FIX!
		JPanel chat = new ChatClient();
		chat.setPreferredSize(new Dimension(300, 500));
		chat.setVisible(true);
		this.add(chat, BorderLayout.LINE_START);
		
		JPanel grid = new Displaytest(this.container);
		grid.setPreferredSize(new Dimension(500, 500));
		this.add(grid, BorderLayout.CENTER);
		
	}
	
//	public void serverPrompt(){
//		ChatClientConnection server = null;
//		try {
//			String serverAddress = JOptionPane.showInputDialog(null,
//					"What is the server's IPv4 address?", "127.0.0.1");
//			Scanner scan = new Scanner(serverAddress);
//			scan.useDelimiter("[.,;\\s]+");
//			byte[] addr = new byte[4];
//			addr[0] = (byte)scan.nextInt();
//			addr[1] = (byte)scan.nextInt();
//			addr[2] = (byte)scan.nextInt();
//			addr[3] = (byte)scan.nextInt();
//			int port = Integer.parseInt(JOptionPane.showInputDialog(null,
//					"What is the server's port?", "4000"));
//			
//			server = new ChatServerProxy(InetAddress.getByAddress(addr), port);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		server.writeToChat("");
//	}
}
