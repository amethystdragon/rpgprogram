package rpgprogram;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(false);
		defineLayout(layout);
	}

	private void defineLayout(IPageLayout layout) {
		IFolderLayout folder = layout.createFolder("center", IPageLayout.TOP, (float)0.10, null);
		
		folder.addView("RPGProgram.MapView");
		folder.addView("RPGProgram.ChatView");
		
		
		
	}

}
