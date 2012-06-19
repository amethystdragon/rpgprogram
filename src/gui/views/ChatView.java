package gui.views;

import gui.components.ChatPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


public class ChatView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		Composite chatComposite = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(chatComposite);
		frame.add(new ChatPanel());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
