import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener {
	
	JFrame window;
	
//	Text Area
	JTextArea textArea;
	JScrollPane scrollPane;
//	Menu Bar
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuFormat, menuColor;
//	File Menu
	JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;
//	Edit Menu
	JMenuItem iUndo, iRedo, iCut, iCopy, iPaste, iDelete, iSelectAll;

	Function_File file = new Function_File(this);
	Function_Edit edit = new Function_Edit(this);
	
	UndoManager um = new UndoManager();

	String appName = "PHNX";
	String appVersion = "v0.3.0 (alpha)";
	String defaultAppTitle = appName + " " + appVersion;
	int WIDTH = 800;
	int HEIGHT = 600;

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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		iNew = new JMenuItem("New");
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
