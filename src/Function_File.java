import java.awt.Component;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Function_File {
	
	private static final Component ConfirmExitFrame = null;

	GUI gui;
	
	String fileName;
	String fileAddress;
	
	public Function_File(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void newFile() {
		
    	if(gui.fileSaved == false) {

            int result = JOptionPane.showConfirmDialog(
                ConfirmExitFrame,
                "Are you sure you want to create a new file with unsaved changes?",
                "Overide Unsaved Changes Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
        		gui.textArea.setText("");
        		gui.window.setTitle(gui.defaultAppTitle + " - Untitled");
        		
        		fileName = null;
        		fileAddress = null;

        		gui.docTypeLabel.setText("Plain Text");
        		gui.fileSaved = true;
            }
    	} else {
    		gui.textArea.setText("");
    		gui.window.setTitle(gui.defaultAppTitle + " - Untitled");
    		
    		fileName = null;
    		fileAddress = null;
    		
    		gui.docTypeLabel.setText("Plain Text");
    		
    		gui.fileSaved = true;
    	}
		
	}
	
	public void newWindow() {
		new GUI();
	}
	
	public void closeWindow() {
    	if(gui.fileSaved == false) {

            int result = JOptionPane.showConfirmDialog(
                ConfirmExitFrame,
                "Are you sure you want to exit with unsaved changes?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
            	gui.window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            	gui.window.dispose();
            }
    	} else {
    		gui.window.dispose();
    	}
	}
	
	public void open() {

		FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
		fd.setVisible(true);
		
		if(fd.getFile()!=null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			
			gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
			
			gui.docType.loadDocumentType(fileName);
		} else {
			return;
		}
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
			
			gui.textArea.setText("");
			
			String line = null;
			
			while((line = br.readLine())!= null) {
				gui.textArea.append(line + "\n");
			}
			br.close();
			
			gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
			gui.docType.loadDocumentType(fileName);
			
			gui.fileSaved = true;
			
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
				
				gui.docType.loadDocumentType(fileName);
				
				gui.fileSaved = true;
				
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
			
			gui.docType.loadDocumentType(fileName);
		}
		
		try {
			
			FileWriter fw = new FileWriter(fileAddress + fileName);
			fw.write(gui.textArea.getText());
			fw.close();
			
			gui.docType.loadDocumentType(fileName);
	
			gui.fileSaved = true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void exit() {
    	if(gui.fileSaved == false) {

            int result = JOptionPane.showConfirmDialog(
                ConfirmExitFrame,
                "Are you sure you want to exit with unsaved changes?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
            	gui.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	System.exit(0);
            }
    	} else {
        	System.exit(0);
    	}
	}
}
