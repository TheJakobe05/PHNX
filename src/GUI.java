import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener {
	
	protected static final Component ConfirmExitFrame = null;

	JFrame window;
	
//	Text Area
	JTextArea textArea, lineNumbers;
	JScrollPane scrollPane;
//	Menu Bar
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuView, menuPreferences;
//	File Menu
	JMenuItem iNew, iNewWindow, iCloseWindow, iOpen, iSave, iSaveAs, iExit;
//	Edit Menu
	JMenuItem iUndo, iRedo, iCut, iCopy, iPaste, iDelete, iSelectAll;
//	View Menu
	JCheckBoxMenuItem iWordWrap;
//	Preferences Menu
	JMenu menuTheme;
	JCheckBoxMenuItem iTheme1, iTheme2;
	JMenu menuFont;
	JMenuItem iFontChoose, iFontSmaller, iFontLarger, iFontReset;
	
//	Status Bar
	JPanel statusPanel;
	JLabel colRowLabel, docTypeLabel;

	Function_File file = new Function_File(this);
	Function_Edit edit = new Function_Edit(this);
	Function_View view = new Function_View(this);
	Function_Preferences pref = new Function_Preferences(this);

	Load_Document_Type docType = new Load_Document_Type(this);
	
	KeyHandler kHandler = new KeyHandler(this);
	
	UndoManager um = new UndoManager();

	String appName = "PHNX";
	String appVersion = "v0.6.0 (alpha)";
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
		createStatusBar();
		
		createFileMenu();
		createEditMenu();
		createViewMenu();
		createPreferencesMenu();
		
//		pick default editors theme
		int pickDefaultTheme = pref.defaultTheme;
		switch(pickDefaultTheme) {
		case 1: pref.setThemeAlabaster(); break;
		case 2: pref.setThemeCinderandAsh(); break;
		}
		
		window.setVisible(true);
	}
	
	public void createWindow() {
		
		window = new JFrame(defaultAppTitle + " - Untitled");
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		ImageIcon appIcon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
		window.setIconImage(appIcon.getImage());
		
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
		Font myFont = new Font(pref.font, Font.PLAIN, pref.fontSize);
		textArea.setFont(myFont);
		
		textArea.addKeyListener(kHandler);
		
		textArea.getDocument().addUndoableEditListener(
			new UndoableEditListener() {
				public void undoableEditHappened(UndoableEditEvent e) {
					um.addEdit(e.getEdit());
				}
			}
		);
		
		lineNumbers = new JTextArea("1");
		lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setEditable(false);
        lineNumbers.setFocusable(false);
		lineNumbers.setFont(textArea.getFont()); // Match fonts
		lineNumbers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lineNumbers.setFont(myFont);
		
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                JTextArea editArea = (JTextArea) e.getSource();
                int caretPos = editArea.getCaretPosition();
                
                try {
                    // Line number is 0-indexed; add 1 for 1-indexed display
                    int line = editArea.getLineOfOffset(caretPos) + 1;
                    
                    // Column number is caret position minus the start offset of the line
                    int startOffset = editArea.getLineStartOffset(line - 1);
                    int column = caretPos - startOffset + 1; // add 1 for 1-indexed column

                    colRowLabel.setText("Line " + line + ", Column " + column);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        // Triggered when text is added/pasted
		    	updateSaveState();
		    	updateLineNumbers();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        // Triggered when text is deleted/backspaced
		    	updateSaveState();
		    	updateLineNumbers();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		    	updateLineNumbers();
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

		scrollPane.setRowHeaderView(lineNumbers);
		
		window.add(scrollPane);
		
	}
	
    private void updateLineNumbers() {
        // Find out how many lines are currently in the main text area
        int lineCount = textArea.getLineCount();
        
        StringBuilder numbersText = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            numbersText.append(i).append("\n");
        }
        
        lineNumbers.setText(numbersText.toString());
    }
	
	public void createMenuBar() {
		
		menuBar = new JMenuBar();
		
		window.setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

		menuView = new JMenu("View");
		menuBar.add(menuView);
		
		menuPreferences = new JMenu("Preferences");
		menuBar.add(menuPreferences);
		
	}
	
	public void createStatusBar() {
		
		statusPanel = new JPanel(new BorderLayout());
		window.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(window.getWidth(), 24));
		statusPanel.setBackground(Color.WHITE);
		
		Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 10);
		statusPanel.setBorder(padding);

		colRowLabel = new JLabel("Line 1, Column 1");
		colRowLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		statusPanel.add(colRowLabel, BorderLayout.WEST);
		
		docTypeLabel = new JLabel("Plain Text");
		docTypeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		statusPanel.add(docTypeLabel, BorderLayout.EAST);
		
	}
	
	public void createFileMenu() {
		ImageIcon newFileIcon = new ImageIcon(getClass().getClassLoader().getResource("newfile.png"));
		ImageIcon openFileIcon = new ImageIcon(getClass().getClassLoader().getResource("openfile.png"));
		ImageIcon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("save.png"));
		ImageIcon saveAsIcon = new ImageIcon(getClass().getClassLoader().getResource("saveas.png"));
		ImageIcon newWindowIcon = new ImageIcon(getClass().getClassLoader().getResource("newwindow.png"));
		ImageIcon closeWindowIcon = new ImageIcon(getClass().getClassLoader().getResource("closewindow.png"));
		ImageIcon exitIcon = new ImageIcon(getClass().getClassLoader().getResource("exit.png"));
		
		iNew = new JMenuItem("New File");
		iNew.setIcon(newFileIcon);
		iNew.setMargin(new Insets(2, -20, 2, 2));
		iNew.addActionListener(this);
		iNew.setActionCommand("New");
		iNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		menuFile.add(iNew);
		
		iOpen = new JMenuItem("Open");
		iOpen.setIcon(openFileIcon);
		iOpen.setMargin(new Insets(2, -20, 2, 2));
		iOpen.addActionListener(this);
		iOpen.setActionCommand("Open");
		iOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuFile.add(iOpen);
		
		iSave = new JMenuItem("Save");
		iSave.setIcon(saveIcon);
		iSave.setMargin(new Insets(2, -20, 2, 2));
		iSave.addActionListener(this);
		iSave.setActionCommand("Save");
		iSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		menuFile.add(iSave);
		
		iSaveAs = new JMenuItem("Save As");
		iSaveAs.setIcon(saveAsIcon);
		iSaveAs.setMargin(new Insets(2, -20, 2, 2));
		iSaveAs.addActionListener(this);
		iSaveAs.setActionCommand("SaveAs");
		iSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		menuFile.add(iSaveAs);
		
		menuFile.addSeparator();

		iNewWindow = new JMenuItem("New Window");
		iNewWindow.setIcon(newWindowIcon);
		iNewWindow.setMargin(new Insets(2, -20, 2, 2));
		iNewWindow.addActionListener(this);
		iNewWindow.setActionCommand("NewWindow");
		iNewWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		menuFile.add(iNewWindow);

		iCloseWindow = new JMenuItem("Close Window");
		iCloseWindow.setIcon(closeWindowIcon);
		iCloseWindow.setMargin(new Insets(2, -20, 2, 2));
		iCloseWindow.addActionListener(this);
		iCloseWindow.setActionCommand("CloseWindow");
		iCloseWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		menuFile.add(iCloseWindow);

		menuFile.addSeparator();
		
		iExit = new JMenuItem("Exit");
		iExit.setIcon(exitIcon);
		iExit.setMargin(new Insets(2, -20, 2, 2));
		iExit.addActionListener(this);
		iExit.setActionCommand("Exit");
		menuFile.add(iExit);
	}
	
	public void createEditMenu() {
		ImageIcon undoIcon = new ImageIcon(getClass().getClassLoader().getResource("undo.png"));
		ImageIcon redoIcon = new ImageIcon(getClass().getClassLoader().getResource("redo.png"));
		ImageIcon cutIcon = new ImageIcon(getClass().getClassLoader().getResource("cut.png"));
		ImageIcon copyIcon = new ImageIcon(getClass().getClassLoader().getResource("copy.png"));
		ImageIcon pasteIcon = new ImageIcon(getClass().getClassLoader().getResource("paste.png"));
		ImageIcon deleteIcon = new ImageIcon(getClass().getClassLoader().getResource("delete.png"));
		ImageIcon selectAllIcon = new ImageIcon(getClass().getClassLoader().getResource("selectall.png"));
		
		iUndo = new JMenuItem("Undo");
		iUndo.setIcon(undoIcon);
		iUndo.setMargin(new Insets(2, -20, 2, 2));
		iUndo.addActionListener(this);
		iUndo.setActionCommand("Undo");
		iUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iUndo);
		
		iRedo = new JMenuItem("Redo");
		iRedo.setIcon(redoIcon);
		iRedo.setMargin(new Insets(2, -20, 2, 2));
		iRedo.addActionListener(this);
		iRedo.setActionCommand("Redo");
		iRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iRedo);
		
		menuEdit.addSeparator();
		
		iCut = new JMenuItem("Cut");
		iCut.setIcon(cutIcon);
		iCut.setMargin(new Insets(2, -20, 2, 2));
		iCut.addActionListener(this);
		iCut.setActionCommand("Cut");
		iCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iCut);
		
		iCopy = new JMenuItem("Copy");
		iCopy.setIcon(copyIcon);
		iCopy.setMargin(new Insets(2, -20, 2, 2));
		iCopy.addActionListener(this);
		iCopy.setActionCommand("Copy");
		iCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iCopy);
		
		iPaste = new JMenuItem("Paste");
		iPaste.setIcon(pasteIcon);
		iPaste.setMargin(new Insets(2, -20, 2, 2));
		iPaste.addActionListener(this);
		iPaste.setActionCommand("Paste");
		iPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iPaste);

		iDelete = new JMenuItem("Delete");
		iDelete.setIcon(deleteIcon);
		iDelete.setMargin(new Insets(2, -20, 2, 2));
		iDelete.addActionListener(this);
		iDelete.setActionCommand("Delete");
		menuEdit.add(iDelete);
		
		menuEdit.addSeparator();
		
		iSelectAll = new JMenuItem("Select All");
		iSelectAll.setIcon(selectAllIcon);
		iSelectAll.setMargin(new Insets(2, -20, 2, 2));
		iSelectAll.addActionListener(this);
		iSelectAll.setActionCommand("SelectAll");
		iSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(iSelectAll);
	}
	
	public void createViewMenu() {
		
		iWordWrap = new JCheckBoxMenuItem("Word Wrap");
		iWordWrap.addActionListener(this);
		iWordWrap.setActionCommand("WordWrap");
		menuView.add(iWordWrap);
		
	}
	
	public void createPreferencesMenu() {
		
		menuTheme = new JMenu("Select Theme");
		menuTheme.setMargin(new Insets(2, -20, 2, 2));
		menuPreferences.add(menuTheme);

		iTheme1 = new JCheckBoxMenuItem("Alabaster");
		iTheme1.addActionListener(this);
		iTheme1.setActionCommand("Theme1");
		menuTheme.add(iTheme1);
		
		iTheme2 = new JCheckBoxMenuItem("Cinder & Ash");
		iTheme2.addActionListener(this);
		iTheme2.setActionCommand("Theme2");
		menuTheme.add(iTheme2);

		menuPreferences.addSeparator();
		
		menuFont = new JMenu("Font");
		menuFont.setMargin(new Insets(2, -20, 2, 2));
		menuPreferences.add(menuFont);

		iFontChoose = new JMenuItem("Choose...");
		iFontChoose.setMargin(new Insets(2, -20, 2, 2));
		iFontChoose.addActionListener(this);
		iFontChoose.setActionCommand("FontChoose");
		menuFont.add(iFontChoose);

		iFontLarger = new JMenuItem("Larger");
		iFontLarger.setMargin(new Insets(2, -20, 2, 2));
		iFontLarger.addActionListener(this);
		iFontLarger.setActionCommand("FontLarger");
		iFontLarger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK));
		menuFont.add(iFontLarger);
		
		iFontSmaller = new JMenuItem("Smaller");
		iFontSmaller.setMargin(new Insets(2, -20, 2, 2));
		iFontSmaller.addActionListener(this);
		iFontSmaller.setActionCommand("FontSmaller");
		iFontSmaller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
		menuFont.add(iFontSmaller);
		
		menuFont.addSeparator();
		
		iFontReset = new JMenuItem("Reset Size");
		iFontReset.setMargin(new Insets(2, -20, 2, 2));
		iFontReset.addActionListener(this);
		iFontReset.setActionCommand("FontReset");
		menuFont.add(iFontReset);
		
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
		case "WordWrap": view.wordWrap(); break;
		case "Theme1": pref.setThemeAlabaster(); break;
		case "Theme2": pref.setThemeCinderandAsh(); break;
		case "FontChoose": pref.fontChoose(); break;
		case "FontLarger": pref.fontLarger(); break;
		case "FontSmaller": pref.fontSmaller(); break;
		case "FontReset": pref.fontReset(); break;
		}
	}
	
	
	
}
