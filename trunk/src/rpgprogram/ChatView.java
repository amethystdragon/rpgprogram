package rpgprogram;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import GUI.ChatPanel;

public class ChatView extends ViewPart {

	public ChatView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite compos = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(compos);
		frame.add(new ChatPanel());

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
