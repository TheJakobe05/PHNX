import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
	JPanel editorRight;
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
	JMenuItem iNew, iNewWindow, iCloseWindow, iOpen, iOpenFolder, iCloseFolder, iSave, iSaveAs, iExit;
//	Edit Menu
	JMenuItem iUndo, iRedo, iCut, iCopy, iPaste, iDelete, iSelectAll, iFind, iReplace, iGoTo;
//	View Menu
	JCheckBoxMenuItem iMinimap, iStatusBar, iWordWrap;
//	Preferences Menu
	JMenu menuTheme, menuThemeCustom, menuThemeInstalled;
	JCheckBoxMenuItem iTheme1, iTheme2, iTheme3, iTheme4, iTheme5, iThemeCustom;
	JMenuItem iThemeConfig;
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
	ImageViewer imgViewer = new ImageViewer(this);
	Config cfg = new Config(this);

	Load_Document_Type docType = new Load_Document_Type(this);

//	EASTER EGGS
	
	EasterEggs eggs;
	DiscoMode disco = new DiscoMode(this);
	GravityMode gravity = new GravityMode(this);
	IdleDVDBounce idleDVD;
	MortalCombatFatality mkf;

	KeyHandler kHandler = new KeyHandler(this);

	UndoManager um = new UndoManager();

	String appName = "PHNX";
	String appVersion = "0.11.0 (alpha)";
	String defaultAppTitle = appName;
	int WIDTH = 1200;
	int HEIGHT = 800;

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
			for (String path : args) {
				if (path == null || path.isBlank())
					continue;

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
		
		eggs = new EasterEggs(this);
		
		tim.createTextArea();
		
		kHandler.setupDiscoTrigger();
		kHandler.setupGravityTrigger();
		
		mkf = new MortalCombatFatality(this);
		
		pref.applyDefaultFont();
		pref.setupCtrlMouseWheelFontSize();

		fileTree = new FileTree(this);

		editorRight = new JPanel(new BorderLayout());
		editorRight.add(scrollPane, BorderLayout.CENTER); // or your tabbed editor
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

		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, _ -> {
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
		
		cfg.loadHomeNotesFile();

		setupFolderDrop();

//		pick default editors theme
		int pickTheme = pref.selectedTheme;
		switch (pickTheme) {
		case 1:
			pref.setThemeAlabaster();
			break;
		case 2:
			pref.setThemeCinderandAsh();
			break;
		case 3:
			pref.setThemeVaporwave77();
			break;
		case 4:
			pref.setThemeMatchaLatte();
			break;
		case 5:
			pref.setThemeCremeBrulee();
			break;
		}

		window.setVisible(true);
		window.requestFocusInWindow();
		idleDVD = new IdleDVDBounce(this);
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
				if (idleDVD != null) {
		            idleDVD.shutdown();
		        }
				if (fileSaved == false) {

					int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
							"Are you sure you want to exit with unsaved changes?", "Exit Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						if (idleDVD != null) {
						    idleDVD.shutdown();
						}

						// 1. Close ONLY the current window
						window.dispose();

						// 2. Check if any other windows are still open/visible
						boolean otherWindowsOpen = false;
						for (java.awt.Window w : java.awt.Window.getWindows()) {
						    if (w != window && w.isDisplayable() && w.isVisible()) {
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
					if (idleDVD != null) {
					    idleDVD.shutdown();
					}

					// 1. Close ONLY the current window
					window.dispose();

					// 2. Check if any other windows are still open/visible
					boolean otherWindowsOpen = false;
					for (java.awt.Window w : java.awt.Window.getWindows()) {
					    if (w != window && w.isDisplayable() && w.isVisible()) {
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
		// Handle images
	    if (isImageFile(f)) {
	    	imgViewer.openImageInNewFrame(f);
	        return;
	    }
		
		if (fileSaved == false) {

			int result = JOptionPane.showConfirmDialog(ConfirmExitFrame,
					"Are you sure you want to open a this file with unsaved changes?", "Overide Unsaved Changes Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				if (f == null || !f.isFile() || !f.canRead()) {
					System.err.println("Cannot open: " + f);
					return;
				}
				// Block video files
			    if (isUnsupportedMedia(f)) {
			        int choice = JOptionPane.showOptionDialog(
			            window,
			            "The file is not displayed in the text editor because it is either binary or\n uses an unsupported text encoding..\n\n" + f.getName(),
			            "Unsupported File",
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.WARNING_MESSAGE,
			            null,
			            new String[]{"Open Anyway", "Cancel"},
			            "Cancel"
			        );

			        if (choice != JOptionPane.YES_OPTION) {
			            return; // user cancelled
			        }
			        // If they chose "Open Anyway", fall through and load it as text
			    }
				file.openDropped(f); // whatever your Function_File method is
			}
		} else {
			if (f == null || !f.isFile() || !f.canRead()) {
				System.err.println("Cannot open: " + f);
				return;
			}
			// Block video files
		    if (isUnsupportedMedia(f)) {
		        int choice = JOptionPane.showOptionDialog(
		            window,
		            "This file type is not supported in the editor.\n\n" + f.getName(),
		            "Unsupported File",
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.WARNING_MESSAGE,
		            null,
		            new String[]{"Open Anyway", "Cancel"},
		            "Cancel"
		        );

		        if (choice != JOptionPane.YES_OPTION) {
		            return; // user cancelled
		        }
		        // If they chose "Open Anyway", fall through and load it as text
		    }
			file.openDropped(f); // whatever your Function_File method is
		}

	}

	private void setupFolderDrop() {
		// Shared logic
		DropTargetAdapter adapter = new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde) {
				try {
					if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						dtde.rejectDrop();
						return;
					}

					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					Transferable t = dtde.getTransferable();

					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

					boolean handled = false;
					for (File f : files) {
						if (f == null)
							continue;

						if (f.isDirectory()) {
							fileTree.setRootFolder(f);
							handled = true;
						} else if (f.isFile()) {
							openFileFromDrop(f); // your existing method
							handled = true;
						}
					}

					dtde.dropComplete(handled);
				} catch (Exception ex) {
					ex.printStackTrace();
					dtde.dropComplete(false);
				}
			}
		};

		// Attach to the three places that should accept drops
		new DropTarget(fileTree, DnDConstants.ACTION_COPY, adapter, true);
		new DropTarget(textArea, DnDConstants.ACTION_COPY, adapter, true);
		new DropTarget(scrollPane, DnDConstants.ACTION_COPY, adapter, true);

		// Important for RSyntaxTextArea
		textArea.setDragEnabled(false);
	}
	
	public boolean isImageFile(File f) {
	    if (f == null) return false;
	    String name = f.getName().toLowerCase();
	    return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")
	        || name.endsWith(".gif") || name.endsWith(".bmp") || name.endsWith(".webp")
	        || name.endsWith(".ico");
	}
	
//	checks for if opening file request is these types so it can display error binary open anyway popup
	
	public boolean isUnsupportedMedia(File f) {
	    if (f == null) return false;
	    String name = f.getName().toLowerCase();
	    return name.endsWith(".mp4") || name.endsWith(".mov") || name.endsWith(".avi")
	        || name.endsWith(".mkv") || name.endsWith(".webm") || name.endsWith(".pdf")
	        || name.endsWith(".pptx") || name.endsWith(".xlsx") || name.endsWith(".docx")
	        || name.endsWith(".jar") || name.endsWith(".exe") || name.endsWith(".wav")
	        || name.endsWith(".aiff") || name.endsWith(".dsd") || name.endsWith(".flac")
	        || name.endsWith(".alac") || name.endsWith(".wma") || name.endsWith(".mp3")
	        || name.endsWith(".aac") || name.endsWith(".ogg") || name.endsWith(".wma")
	        || name.endsWith(".opus");
	}

}