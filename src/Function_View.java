
public class Function_View {
	
	GUI gui;
	boolean isWrapped = false;
	
	public Function_View(GUI gui) {
		
		this.gui = gui;
		
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
