package gui.views;

import mapGenerator.copy.Displaytest;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MapView extends ViewPart {

	public MapView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite compos = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(compos);
		frame.add(new Displaytest());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
