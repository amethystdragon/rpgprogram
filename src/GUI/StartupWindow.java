package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartupWindow extends JPanel{
	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = 7368586068474491421L;
	/**
	 * Stores the ActionListener for this window 
	 */
	ActionListener listener;
	/**
	 * Stores the JMenuBar for this window
	 */
	JMenuBar menuBar;
	
	/**
	 * Default Constructor
	 */
	@SuppressWarnings("unused")
	private StartupWindow(){
		//Should not be used
	}
	
	/**
	 * Main Constructor
	 * @param al - ActionListener
	 */
	public StartupWindow(ActionListener al){
		this.listener = al;
		createUI();
		this.setVisible(true);
	}
	
	/**
	 * Creates the JPanel UI for this window
	 */
	private void createUI(){
		//Sets the panel background
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout(10, 10));
		this.setPreferredSize(new Dimension(800, 600));
		this.setVisible(true);
		
		//Sets up the button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		buttonPanel.setBackground(new Color(75, 75, 75));
		//Sets up the builder button
		JButton builder = new JButton();
		builder.addActionListener(listener);
		builder.setActionCommand("builder");
		builder.setPreferredSize(new Dimension(150, 150));
		buttonPanel.add(builder);
		//Sets up the game master button
		JButton gameMaster = new JButton();
		gameMaster.addActionListener(listener);
		gameMaster.setActionCommand("gamemaster");
		gameMaster.setPreferredSize(new Dimension(150, 150));
		buttonPanel.add(gameMaster);
		//Sets up the player button
		JButton player = new JButton();
		player.addActionListener(listener);
		player.setActionCommand("player");
		player.setPreferredSize(new Dimension(150, 150));
		buttonPanel.add(player);
		//Adds the Button panel to window's panel
		this.add(buttonPanel, BorderLayout.CENTER);
		
		//Creates and sets up the label panel
		JPanel labelPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		labelPanel.setBackground(new Color(50, 50, 50));
		//Sets the fonts for the labels
		Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
		//Sets up the builder label
		JLabel builderLabel = new JLabel("Builder");
		builderLabel.setForeground(Color.WHITE);
		builderLabel.setFont(labelFont);
		builderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(builderLabel);
		//Sets up the game master label		
		JLabel gameMasterLabel = new JLabel("Game Master");
		gameMasterLabel.setForeground(Color.WHITE);
		gameMasterLabel.setFont(labelFont);
		gameMasterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(gameMasterLabel);
		//Sets up the player label		
		JLabel playerLabel = new JLabel("Player");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(labelFont);
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(playerLabel);
		//Adds the label panel to window's panel
		this.add(labelPanel, BorderLayout.PAGE_END);
	}
	
	/**
	 * Creates the JMenuBar for this window
	 */
	private void createMenu(){
		// Menu Bar
		this.menuBar = new JMenuBar();

		//Menu
		JMenu fileMenu = new JMenu("File");
		
		//Load Previous configuration
		JMenuItem load = new JMenuItem("Load...");
		load.setActionCommand("load");
		load.addActionListener(listener);
		fileMenu.add(load);
	
		//Load new configuration
		JMenu newClient = new JMenu("New");
		newClient.setActionCommand("new");
		newClient.addActionListener(listener);
		//Builder
		JMenuItem builder = new JMenuItem("Builder");
		builder.setActionCommand("builder");
		builder.addActionListener(listener);
		newClient.add(builder);
		//GM
		JMenuItem gameMaster = new JMenuItem("Game Master");
		gameMaster.setActionCommand("gamemaster");
		gameMaster.addActionListener(listener);
		newClient.add(gameMaster);
		//Player
		JMenuItem player = new JMenuItem("Player");
		player.setActionCommand("player");
		player.addActionListener(listener);
		newClient.add(player);
		//Adds it to the menu
		fileMenu.add(newClient);
		
		//Exit
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(listener);
		fileMenu.add(exit);
		
		//Adds the menu menu to the Menu bar
		this.menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		
		//Help
		JMenuItem help = new JMenuItem("Help");
		help.setActionCommand("help");
		help.addActionListener(listener);
		helpMenu.add(help);
		
		//About
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand("about");
		about.addActionListener(listener);
		helpMenu.add(about);
		
		//Adds the help menu to the Menu bar
		this.menuBar.add(helpMenu);
	}
	
	/**
	 * Determines if the JMenuBar has been created yet. 
	 * If not calls createMenu().
	 * 
	 * @return menuBar - JMenuBar for this window conficuration
	 */
	public JMenuBar getMenuBar(){
		if(this.menuBar == null) createMenu();
		return this.menuBar;
	}
}
