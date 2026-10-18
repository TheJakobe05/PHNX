import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.undo.UndoManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class GUI {
	
	protected static final Component ConfirmExitFrame = null;

	JFrame window;
	
//	Text Area
	RSyntaxTextArea textArea = new RSyntaxTextArea();
	RTextScrollPane scrollPane = new RTextScrollPane(textArea);

	Minimap minimap = new Minimap(textArea, scrollPane);
	Gutter lineNumbers;
	SpellingParser spellingParser;
	
//	file tree sidebar
	
	FileTree fileTree;
	JSplitPane splitPane;
	
//	Menu Bar
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuView, menuPreferences, menuHelp;
//	File Menu
	JMenuItem iNew, iNewWindow, iCloseWindow, iOpen, iOpenFolder, iSave, iSaveAs, iExit;
//	Edit Menu
	JMenuItem iUndo, iRedo, iCut, iCopy, iPaste, iDelete, iSelectAll, iFind, iReplace, iGoTo;
//	View Menu
	JCheckBoxMenuItem iMinimap, iStatusBar, iWordWrap;
//	Preferences Menu
	JMenu menuTheme;
	JCheckBoxMenuItem iTheme1, iTheme2, iTheme3, iTheme4, iTheme5;
	JMenu menuFont;
	JMenuItem iFontChoose, iFontSmaller, iFontLarger, iFontReset;
//	Help Menu
	JMenuItem iTwitter, iGithub, iChangeLog, iAbout, iReportaBug, iSponsor;
	
//	Status Bar
	JPanel statusPanel;
	JLabel colRowLabel, docTypeLabel;

	Function_File file = new Function_File(this);
	Function_Edit edit = new Function_Edit(this);
	Function_View view = new Function_View(this);
	Function_Preferences pref = new Function_Preferences(this);
	Function_Help help = new Function_Help(this);

	TextArea tim = new TextArea(this);
	MenuBar menu = new MenuBar(this);

	Load_Document_Type docType = new Load_Document_Type(this);
	
//	EASTER EGGS
	
	KeyHandler kHandler = new KeyHandler(this);
	
	UndoManager um = new UndoManager();

	String appName = "PHNX";
	String appVersion = "0.9.0 (alpha)";
	String defaultAppTitle = appName;
	int WIDTH = 800;
	int HEIGHT = 650;
	
	boolean fileSaved = true;

	public static void main(String[] args) throws IOException {
		
		try {
            // Set the look and feel to match the native operating system (Windows)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		new GUI();
		
		// Files passed by the OS ("Open with", double-click if associated, etc.)
	    if (args != null && args.length > 0) {
	        boolean first = true;
	        for (String path : args) {
	            if (path == null || path.isBlank()) continue;

	            File f = new File(path);
	            // Handle quoted paths / relative paths
	            if (!f.isAbsolute()) {
	                f = f.getAbsoluteFile();
	            }
	            if (!f.isFile()) {
	                System.err.println("Not a file or not found: " + f);
	                continue;
	            } else {
	            	GUI extra = new GUI();
	            	extra.openFileFromDrop(f);
	            }
	        }
	    }
	}
	
	public GUI() {

		createWindow();
		tim.createTextArea();
		
		fileTree = new FileTree(this);
		
		JPanel editorRight = new JPanel(new BorderLayout());
		editorRight.add(scrollPane, BorderLayout.CENTER);   // or your tabbed editor
		editorRight.add(minimap, BorderLayout.EAST);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileTree, editorRight);
		splitPane.setDividerLocation(220);
		splitPane.setResizeWeight(0);
		splitPane.setBorder(null);
		splitPane.setDividerSize(3);
		
		splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(new Color(80, 80, 90));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		fileTree.setVisible(false);
		
		window.add(splitPane, BorderLayout.CENTER);
		
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
		    int loc = splitPane.getDividerLocation();
		    if (loc < 120) {
		    	splitPane.setDividerLocation(120);
		    } else if (loc > 250) {
		    	splitPane.setDividerLocation(250);
		    }
		});
		
		menu.createMenuBar();
		createStatusBar();
		
		menu.createFileMenu();
		menu.createEditMenu();
		menu.createViewMenu();
		menu.createPreferencesMenu();
		menu.createHelpMenu();
		
//		pick default editors theme
		int pickTheme = pref.selectedTheme;
		switch(pickTheme) {
		case 1: pref.setThemeAlabaster(); break;
		case 2: pref.setThemeCinderandAsh(); break;
		case 3: pref.setThemeVaporwave77(); break;
		case 4: pref.setThemeMatchaLatte(); break;
		case 5: pref.setThemeCremeBrulee(); break;
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
	
	/** Called when a file is dropped onto this editor (or onto a new window). */
	public void openFileFromDrop(File f) {
	    if (f == null || !f.isFile()) {
	        return;
	    }
	    file.openDropped(f);
	}
	
}
