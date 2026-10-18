
public class Function_View {
	
	GUI gui;
	boolean isWrapped = false;
	
	public Function_View(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void minimap() {
		gui.minimap.setVisible(!gui.iMinimap.isSelected());
	}
	
	public void statusbar() {
		gui.statusPanel.setVisible(!gui.iStatusBar.isSelected());
	}
	
	public void wordWrap() {
		if(isWrapped == false) {
			isWrapped = true;
			gui.textArea.setLineWrap(true);
			gui.textArea.setWrapStyleWord(true);
		} else {
			isWrapped = false;
			gui.textArea.setLineWrap(false);
			gui.textArea.setWrapStyleWord(false);
		}
	}
}
