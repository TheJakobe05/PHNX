import java.awt.Component;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
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

		if (gui.fileSaved == false) {

			int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
					"Are you sure you want to create a new file with unsaved changes?",
					"Overide Unsaved Changes Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

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
		if (gui.fileSaved == false) {

			int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
					"Are you sure you want to exit with unsaved changes?", "Exit Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				gui.window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

				if (gui.idleDVD != null) {
				    gui.idleDVD.shutdown();
				}

				// 1. Close ONLY the current window
				gui.window.dispose();

				// 2. Check if any other windows are still open/visible
				boolean otherWindowsOpen = false;
				for (java.awt.Window w : java.awt.Window.getWindows()) {
				    if (w != gui.window && w.isDisplayable() && w.isVisible()) {
				        otherWindowsOpen = true;
				        break; // Found another active window, no need to keep checking
				    }
				}

				// 3. Fully exit the application ONLY if this was the last window
				if (!otherWindowsOpen) {
				    System.exit(0);
				}
			}
		} else {

			if (gui.idleDVD != null) {
			    gui.idleDVD.shutdown();
			}

			// 1. Close ONLY the current window
			gui.window.dispose();

			// 2. Check if any other windows are still open/visible
			boolean otherWindowsOpen = false;
			for (java.awt.Window w : java.awt.Window.getWindows()) {
			    if (w != gui.window && w.isDisplayable() && w.isVisible()) {
			        otherWindowsOpen = true;
			        break; // Found another active window, no need to keep checking
			    }
			}

			// 3. Fully exit the application ONLY if this was the last window
			if (!otherWindowsOpen) {
			    System.exit(0);
			}
		}
	}

	public void open() {

		if (gui.fileSaved == false) {

			int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
					"Are you sure you want to open a file with unsaved changes?",
					"Overide Unsaved Changes Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
				fd.setVisible(true);

				if (fd.getFile() != null) {
					fileName = fd.getFile();
					fileAddress = fd.getDirectory();
					
					File selected = new File(fileAddress, fileName);
					
					// Images open in their own window
				    if (gui.isImageFile(selected)) {
				        gui.imgViewer.openImageInNewFrame(selected);   // make sure this method is public in GUI
				        return;
				    }
				    
				    if (gui.isUnsupportedMedia(selected)) {
				        int choice = JOptionPane.showOptionDialog(
				            gui.window,
				            "The file is not displayed in the text editor because it is either binary or uses an unsupported text encoding.\n\n" + selected.getName(),
				            "Unsupported File",
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.WARNING_MESSAGE,
				            null,
				            new String[]{"Open Anyway", "Cancel"},
				            "Cancel"
				        );
				        if (choice != JOptionPane.YES_OPTION) {
				            return;
				        }
				    }

					gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);

					gui.docType.loadDocumentType(fileName);
				} else {
					return;
				}

				try {

					BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));

					gui.textArea.setText("");

					String line = null;

					while ((line = br.readLine()) != null) {
						gui.textArea.append(line + "\n");
					}
					br.close();

					gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
					gui.docType.loadDocumentType(fileName);

					gui.fileSaved = true;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
			fd.setVisible(true);

			if (fd.getFile() != null) {
				fileName = fd.getFile();
				fileAddress = fd.getDirectory();
				
				File selected = new File(fileAddress, fileName);
				
				if (gui.isUnsupportedMedia(selected)) {
				    int choice = JOptionPane.showOptionDialog(
				        gui.window,
				        "The file is not displayed in the text editor because it is either binary or\n uses an unsupported text encoding.\n\n" + selected.getName(),
				        "Unsupported File",
				        JOptionPane.YES_NO_OPTION,
				        JOptionPane.WARNING_MESSAGE,
				        null,
				        new String[]{"Open Anyway", "Cancel"},
				        "Cancel"
				    );
				    if (choice != JOptionPane.YES_OPTION) {
				        return;
				    }
				}
				
				// Images open in their own window
			    if (gui.isImageFile(selected)) {
			        gui.imgViewer.openImageInNewFrame(selected);   // make sure this method is public in GUI
			        return;
			    }

				gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);

				gui.docType.loadDocumentType(fileName);
			} else {
				return;
			}

			try {

				BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));

				gui.textArea.setText("");

				String line = null;

				while ((line = br.readLine()) != null) {
					gui.textArea.append(line + "\n");
				}
				br.close();

				gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
				gui.docType.loadDocumentType(fileName);

				gui.fileSaved = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void openFolder() {
		javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
		chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Open Folder");
		if (chooser.showOpenDialog(gui.window) == javax.swing.JFileChooser.APPROVE_OPTION) {
			gui.fileTree.setRootFolder(chooser.getSelectedFile());
			gui.fileTree.setRootFolder(chooser.getSelectedFile());
			gui.fileTree.setRootFolder(chooser.getSelectedFile());

			gui.fileTree.setVisible(true);
		}
	}

	public void openDropped(File fileToOpen) {
		
		if (gui.isUnsupportedMedia(fileToOpen)) {
		    int choice = JOptionPane.showOptionDialog(
		        gui.window,
		        "The file is not displayed in the text editor because it is either binary or\n uses an unsupported text encoding.\n\n" + fileToOpen.getName(),
		        "Unsupported File",
		        JOptionPane.YES_NO_OPTION,
		        JOptionPane.WARNING_MESSAGE,
		        null,
		        new String[]{"Open Anyway", "Cancel"},
		        "Cancel"
		    );
		    if (choice != JOptionPane.YES_OPTION) {
		        return;
		    }
		}
		
		try {

			BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));

			gui.textArea.setText("");

			String line = null;

			while ((line = br.readLine()) != null) {
				gui.textArea.append(line + "\n");
			}
			br.close();

			gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
			gui.docType.loadDocumentType(fileName);

			gui.fileSaved = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// Example: read text and put into the editor
			// Adapt to whatever you already do in open()
			String content = new String(java.nio.file.Files.readAllBytes(fileToOpen.toPath()),
					java.nio.charset.StandardCharsets.UTF_8);
			gui.textArea.setText(content);
			gui.textArea.setCaretPosition(0);

			// Track path / title / saved state (match your existing open() logic)
			// e.g. gui.currentFile = fileToOpen;
			gui.fileSaved = true;
			gui.window.setTitle(gui.appName + " - " + fileToOpen.getName());

			// Syntax by extension
			gui.docType.loadDocumentType(fileToOpen.getName()); // if you have this

			fileName = fileToOpen.getName();
			fileAddress = fileToOpen.getParent() + "/";
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(gui.window, "Failed to open:\n" + fileToOpen.getAbsolutePath(),
					"Open Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void save() {
		if (fileName == null) {
			saveAs();
		} else {
			try {
				FileWriter fw = new FileWriter(fileAddress + fileName);
				fw.write(gui.textArea.getText());
				gui.window.setTitle(gui.defaultAppTitle + " - " + fileName);
				fw.close();

				gui.docType.loadDocumentType(fileName);

				gui.fileSaved = true;
				
				// If this is the config file, reload it
	            if ("PHNX.txt".equals(fileName)) {
	                gui.cfg.loadHomeNotesFile();
	            }

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void saveAs() {

		FileDialog fd = new FileDialog(gui.window, "Save", FileDialog.SAVE);
		fd.setVisible(true);

		if (fd.getFile() != null) {
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
			
			// If this is the config file, reload it
            if ("PHNX.txt".equals(fileName)) {
                gui.cfg.loadHomeNotesFile();
            }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void exit() {
		if (gui.fileSaved == false) {

			int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
					"Are you sure you want to exit with unsaved changes?", "Exit Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				gui.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				if (gui.idleDVD != null) {
					gui.idleDVD.shutdown();
		        }
				gui.window.dispose();
				for (java.awt.Window w : java.awt.Window.getWindows()) {
			        if (w != gui.window && w.isDisplayable()) {
			            w.dispose();
			        }
			    }
				System.exit(0);
			}
		} else {
			if (gui.idleDVD != null) {
				gui.idleDVD.shutdown();
	        }
			gui.window.dispose();
			System.exit(0);
		}
	}
}
