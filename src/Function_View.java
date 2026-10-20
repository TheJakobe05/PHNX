
public class Function_View {
	
	GUI gui;
	boolean isWrapped = false;
	
	public Function_View(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void minimap() {
		gui.minimap.setVisible(!gui.iMinimap.isSelected());
		gui.cfg.saveConfig();
	}
	
	public void statusbar() {
		gui.statusPanel.setVisible(!gui.iStatusBar.isSelected());
		gui.cfg.saveConfig();
	}
	
	public void wordWrap() {
	    boolean wrap = gui.iWordWrap.isSelected();
	    gui.textArea.setLineWrap(wrap);
	    gui.textArea.setWrapStyleWord(wrap);
		gui.cfg.saveConfig();
	}
}
