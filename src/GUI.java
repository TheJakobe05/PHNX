import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener {
	
	protected static final Component ConfirmExitFrame = null;

	JFrame window;
	
//	Text Area
	JTextArea textArea;
	JScrollPane scrollPane;
//	Menu Bar
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuFormat, menuColor;
//	File Menu
	JMenuItem iNew, iNewWindow, iCloseWindow, iOpen, iSave, iSaveAs, iExit;
//	Edit Menu
	JMenuItem iUndo, iRedo, iCut, iCopy, iPaste, iDelete, iSelectAll;

	Function_File file = new Function_File(this);
	Function_Edit edit = new Function_Edit(this);
	
	UndoManager um = new UndoManager();

	String appName = "PHNX";
	String appVersion = "v0.4.0 (alpha)";
	String defaultAppTitle = appName + " " + appVersion;
	int WIDTH = 800;
	int HEIGHT = 600;
	
	boolean fileSaved = true;

	public static void main(String[] args) {

		new GUI();

	}
	
	public GUI() {
		
		try {
            // Set the look and feel to match the native operating system (Windows)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

		createWindow();
		createTextArea();
		createMenuBar();
		
		createFileMenu();
		createEditMenu();
		
		window.setVisible(true);
	}
	
	public void createWindow() {
		
		window = new JFrame(defaultAppTitle + " - Untitled");
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(fileSaved == false) {

                    int result = JOptionPane.showConfirmDialog(
                        ConfirmExitFrame,
                        "Are you sure you want to exit with unsaved changes?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        window.dispose();
                    }
            	} else {
            		window.dispose();
            	}
            }
        });
		
	}	
	
	public void createTextArea() {
		
		textArea = new JTextArea();
		textArea.setTabSize(5);
		
		textArea.getDocument().addUndoableEditListener(
			new UndoableEditListener() {
				public void undoableEditHappened(UndoableEditEvent e) {
					um.addEdit(e.getEdit());
				}
			}
		);
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        // Triggered when text is added/pasted
		    	updateSaveState();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        // Triggered when text is deleted/backspaced
		    	updateSaveState();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        // Triggered rare attribute changes, can usually be ignored for plain text
		    }

		    private void updateSaveState() {
		        if(fileSaved == true) {
		        	fileSaved = false;
		        	window.setTitle(window.getTitle() + "*");
		        } else {
		        	return;
		        }
		    }
		});
		
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		window.add(scrollPane);
		
	}
	
	public void createMenuBar() {
		
		menuBar = new JMenuBar();
		
		window.setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
//		
//		menuFormat = new JMenu("Format");
//		menuBar.add(menuFormat);
//		
//		menuColor = new JMenu("Color");
//		menuBar.add(menuColor);
		
	}
	
	public void createFileMenu() {
		iNew = new JMenuItem("New File");
		iNew.addActionListener(this);
		iNew.setActionCommand("New");
		menuFile.add(iNew);
		
		iOpen = new JMenuItem("Open");
		iOpen.addActionListener(this);
		iOpen.setActionCommand("Open");
		menuFile.add(iOpen);
		
		iSave = new JMenuItem("Save");
		iSave.addActionListener(this);
		iSave.setActionCommand("Save");
		menuFile.add(iSave);
		
		iSaveAs = new JMenuItem("Save As");
		iSaveAs.addActionListener(this);
		iSaveAs.setActionCommand("SaveAs");
		menuFile.add(iSaveAs);
		
		menuFile.addSeparator();

		iNewWindow = new JMenuItem("New Window");
		iNewWindow.addActionListener(this);
		iNewWindow.setActionCommand("NewWindow");
		menuFile.add(iNewWindow);

		iCloseWindow = new JMenuItem("Close Window");
		iCloseWindow.addActionListener(this);
		iCloseWindow.setActionCommand("CloseWindow");
		menuFile.add(iCloseWindow);

		menuFile.addSeparator();
		
		iExit = new JMenuItem("Exit");
		iExit.addActionListener(this);
		iExit.setActionCommand("Exit");
		menuFile.add(iExit);
	}
	
	public void createEditMenu() {
		iUndo = new JMenuItem("Undo");
		iUndo.addActionListener(this);
		iUndo.setActionCommand("Undo");
		menuEdit.add(iUndo);
		
		iRedo = new JMenuItem("Redo");
		iRedo.addActionListener(this);
		iRedo.setActionCommand("Redo");
		menuEdit.add(iRedo);
		
		menuEdit.addSeparator();
		
		iCut = new JMenuItem("Cut");
		iCut.addActionListener(this);
		iCut.setActionCommand("Cut");
		menuEdit.add(iCut);
		
		iCopy = new JMenuItem("Copy");
		iCopy.addActionListener(this);
		iCopy.setActionCommand("Copy");
		menuEdit.add(iCopy);
		
		iPaste = new JMenuItem("Paste");
		iPaste.addActionListener(this);
		iPaste.setActionCommand("Paste");
		menuEdit.add(iPaste);

		iDelete = new JMenuItem("Delete");
		iDelete.addActionListener(this);
		iDelete.setActionCommand("Delete");
		menuEdit.add(iDelete);
		
		menuEdit.addSeparator();
		
		iSelectAll = new JMenuItem("Select All");
		iSelectAll.addActionListener(this);
		iSelectAll.setActionCommand("SelectAll");
		menuEdit.add(iSelectAll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		switch(command) {
		case "New": file.newFile(); break;
		case "NewWindow": file.newWindow(); break;
		case "CloseWindow": file.closeWindow(); break;
		case "Open": file.open(); break;
		case "Save": file.save(); break;
		case "SaveAs": file.saveAs(); break;
		case "Exit": file.exit(); break;
		case "Undo": edit.undo(); break;
		case "Redo": edit.redo(); break;
		case "Cut": edit.cut(); break;
		case "Copy": edit.copy(); break;
		case "Paste": edit.paste(); break;
		case "Delete": edit.delete(); break;
		case "SelectAll": edit.selectAll(); break;
		}
	}
	
	
	
}
