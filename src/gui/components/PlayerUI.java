package gui.components;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerUI extends JPanel{
	ChatPanel chatter;


	/**
	 * 
	 */
	private static final long serialVersionUID = -2457263062148886999L;

	/**
	 * Parent container for the JFrame
	 */
	private JFrame container;

	public PlayerUI(JFrame container) {
		this.container = container;
		this.container.addWindowListener(new PlayerUIWindowListener());

		createUI();
	}


	private void createUI(){
		// sets the default window size
		container.setSize(600, 800);
		setLayout(new BorderLayout());

		chatter = new ChatPanel();
		//Test code
		add(chatter, BorderLayout.CENTER);
		chatter.appendToMenuBar(container.getJMenuBar());
		//End test code
		
		
		// make it visible
		setVisible(true);

	}

	/**
	 * Window Listener which handles the close operation
	 */
	private class PlayerUIWindowListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// unused

		}

		@Override
		public void windowClosing(WindowEvent e) {

			// if connected to the server
			if (chatter.isConnected()) {
				chatter.disconnect();
			}
			System.exit(1);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// unused
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// unused
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// unused
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// unused
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// unused
		}
	};
}
