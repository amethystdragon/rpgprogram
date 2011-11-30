package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407379354004740936L;

	private StartupWindow startup;
	private JPanel currentWindow;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow(){
		super("RPG Hosting Program");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);
		//Initializes the startup window
		this.startup = new StartupWindow(new ActionHandler());
		this.setJMenuBar(this.startup.getMenuBar());
		//Sets the current window
		this.currentWindow = startup;
		this.add(this.currentWindow, BorderLayout.CENTER);
		
		repaint();
		this.setVisible(true);
	}
	
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("player")){
				System.out.println("Player");
				remove(currentWindow);
				currentWindow = new PlayerUI(MainWindow.this);
				add(currentWindow);
				pack();
				repaint();
			} else if(e.getActionCommand().equals("gamemaster")){
				currentWindow = new GameMasterUI();
			} else if(e.getActionCommand().equals("builder")){
				currentWindow = new BuilderUI();
			} else if(e.getActionCommand().equals("about")){
				String message = 
					"+-----------------------------------------------------------------+\n"+
					"| RPG Hosting Program                                             |\n"+
					"|       Copyright (C) 2011 Karl Schmidbauer schmidbauerk@msoe.edu,|\n"+
					"|                          Jacob Williams williamsj@msoe.edu      |\n"+
					"|                                                                 |\n"+
					"+-----------------------------------------------------------------+\n"+  
					"\n\nChat Based on LlamaChat by Joseph Monti\n"+
					"+-----------------------------------------------------------------+\n"+
					"|           Copyright (C) 2002-2003 Joseph Monti, LlamaChat       |\n"+
					"|                              countjoe@users.sourceforge.net     |\n"+
					"|                                                                 |\n"+
					"| This program is free software; you can redistribute it and/or   |\n"+
					"| modify it under the terms of the GNU General Public License     |\n"+
					"| as published by the Free Software Foundation; either version 2  |\n"+
					"| of the License, or (at your option) any later version.          |\n"+
					"|                                                                 |\n"+
					"| This program is distributed in the hope that it will be useful, |\n"+
					"| but WITHOUT ANY WARRANTY; without even the implied warranty of  |\n"+
					"| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   |\n"+
					"| GNU General Public License for more details.                    |\n"+
					"|                                                                 |\n"+
					"| A copy of the GNU General Public License may be found in the    |\n"+
					"| installation directory named 'GNUGPL.txt'                       |\n"+
					"+-----------------------------------------------------------------+\n";
					JFrame about = new JFrame("About");
					JTextArea text = new JTextArea();
					text.setText(message);
					text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
					text.setEditable(false);
					about.add(text);
					about.setSize(500, 500);
					about.setVisible(true);
				}
		}
	}
}
