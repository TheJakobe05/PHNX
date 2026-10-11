
public class Function_Edit {
	
	GUI gui;
	
	public Function_Edit(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void undo() {
		gui.um.undo();
	}
	
	public void redo() {
		gui.um.redo();
	}

	public void cut() {
		gui.textArea.cut();
	}
	
	public void copy() {
		gui.textArea.copy();
	}
	
	public void paste() {
		gui.textArea.paste();
	}
	
	public void delete() {
		gui.textArea.replaceSelection("");
	}
	
	public void selectAll() {
		gui.textArea.selectAll();
	}

}
