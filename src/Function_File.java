import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Function_File {
	
	GUI gui;
	
	String fileName;
	String fileAddress;
	
	public Function_File(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void newFile() {
		
		gui.textArea.setText("");
		gui.window.setTitle(gui.defaultAppTitle + " - Untitled");
		
		fileName = null;
		fileAddress = null;
		
	}
	
	public void open() {

		FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
		fd.setVisible(true);
		
		if(fd.getFile()!=null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			
			gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
		}
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
			
			gui.textArea.setText("");
			
			String line = null;
			
			while((line = br.readLine())!= null) {
				gui.textArea.append(line + "\n");
			}
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void save() {
		if(fileName == null) {
			saveAs();
		} else {
			try {
				FileWriter fw = new FileWriter(fileAddress + fileName);
				fw.write(gui.textArea.getText());
				gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
				fw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveAs() {
		
		FileDialog fd = new FileDialog(gui.window, "Save", FileDialog.SAVE);
		fd.setVisible(true);
		
		if(fd.getFile()!=null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();

			gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
		}
		
		try {
			
			FileWriter fw = new FileWriter(fileAddress + fileName);
			fw.write(gui.textArea.getText());
			fw.close();
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void exit() {
		System.exit(0);
	}
}
