
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar implements ActionListener {
	
	GUI gui;
	
	public MenuBar(GUI gui) {
		
		this.gui = gui;
		
	}
	
public void createMenuBar() {
		
		gui.menuBar = new JMenuBar();
		
		gui.window.setJMenuBar(gui.menuBar);

		gui.menuFile = new JMenu("File");
		gui.menuBar.add(gui.menuFile);
		
		gui.menuEdit = new JMenu("Edit");
		gui.menuBar.add(gui.menuEdit);

		gui.menuView = new JMenu("View");
		gui.menuBar.add(gui.menuView);

		gui.menuPreferences = new JMenu("Preferences");
		gui.menuBar.add(gui.menuPreferences);
		
		gui.menuHelp = new JMenu("Help");
		gui.menuBar.add(gui.menuHelp);
		
	}
	
	public void createFileMenu() {
		ImageIcon newFileIcon = new ImageIcon(getClass().getClassLoader().getResource("newfile.png"));
		ImageIcon openFileIcon = new ImageIcon(getClass().getClassLoader().getResource("openfile.png"));
		ImageIcon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("save.png"));
		ImageIcon saveAsIcon = new ImageIcon(getClass().getClassLoader().getResource("saveas.png"));
		ImageIcon newWindowIcon = new ImageIcon(getClass().getClassLoader().getResource("newwindow.png"));
		ImageIcon closeWindowIcon = new ImageIcon(getClass().getClassLoader().getResource("closewindow.png"));
		ImageIcon exitIcon = new ImageIcon(getClass().getClassLoader().getResource("exit.png"));
		
		gui.iNew = new JMenuItem("New File");
		gui.iNew.setIcon(newFileIcon);
		gui.iNew.setMargin(new Insets(2, -20, 2, 2));
		gui.iNew.addActionListener(this);
		gui.iNew.setActionCommand("New");
		gui.iNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		gui.menuFile.add(gui.iNew);
		
		gui.iOpen = new JMenuItem("Open...");
		gui.iOpen.setIcon(openFileIcon);
		gui.iOpen.setMargin(new Insets(2, -20, 2, 2));
		gui.iOpen.addActionListener(this);
		gui.iOpen.setActionCommand("Open");
		gui.iOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		gui.menuFile.add(gui.iOpen);
		
		gui.iOpenFolder = new JMenuItem("Open Folder...");
		gui.iOpenFolder.setIcon(openFileIcon);
		gui.iOpenFolder.setMargin(new Insets(2, -20, 2, 2));
		gui.iOpenFolder.addActionListener(this);
		gui.iOpenFolder.setActionCommand("OpenFolder");
		gui.menuFile.add(gui.iOpenFolder);
		
		gui.iSave = new JMenuItem("Save");
		gui.iSave.setIcon(saveIcon);
		gui.iSave.setMargin(new Insets(2, -20, 2, 2));
		gui.iSave.addActionListener(this);
		gui.iSave.setActionCommand("Save");
		gui.iSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		gui.menuFile.add(gui.iSave);
		
		gui.iSaveAs = new JMenuItem("Save As");
		gui.iSaveAs.setIcon(saveAsIcon);
		gui.iSaveAs.setMargin(new Insets(2, -20, 2, 2));
		gui.iSaveAs.addActionListener(this);
		gui.iSaveAs.setActionCommand("SaveAs");
		gui.iSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		gui.menuFile.add(gui.iSaveAs);
		
		gui.menuFile.addSeparator();

		gui.iNewWindow = new JMenuItem("New Window");
		gui.iNewWindow.setIcon(newWindowIcon);
		gui.iNewWindow.setMargin(new Insets(2, -20, 2, 2));
		gui.iNewWindow.addActionListener(this);
		gui.iNewWindow.setActionCommand("NewWindow");
		gui.iNewWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		gui.menuFile.add(gui.iNewWindow);

		gui.iCloseWindow = new JMenuItem("Close Window");
		gui.iCloseWindow.setIcon(closeWindowIcon);
		gui.iCloseWindow.setMargin(new Insets(2, -20, 2, 2));
		gui.iCloseWindow.addActionListener(this);
		gui.iCloseWindow.setActionCommand("CloseWindow");
		gui.iCloseWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		gui.menuFile.add(gui.iCloseWindow);

		gui.menuFile.addSeparator();
		
		gui.iExit = new JMenuItem("Exit");
		gui.iExit.setIcon(exitIcon);
		gui.iExit.setMargin(new Insets(2, -20, 2, 2));
		gui.iExit.addActionListener(this);
		gui.iExit.setActionCommand("Exit");
		gui.menuFile.add(gui.iExit);
	}
	
	public void createEditMenu() {
		ImageIcon undoIcon = new ImageIcon(getClass().getClassLoader().getResource("undo.png"));
		ImageIcon redoIcon = new ImageIcon(getClass().getClassLoader().getResource("redo.png"));
		ImageIcon cutIcon = new ImageIcon(getClass().getClassLoader().getResource("cut.png"));
		ImageIcon copyIcon = new ImageIcon(getClass().getClassLoader().getResource("copy.png"));
		ImageIcon pasteIcon = new ImageIcon(getClass().getClassLoader().getResource("paste.png"));
		ImageIcon deleteIcon = new ImageIcon(getClass().getClassLoader().getResource("delete.png"));
		ImageIcon selectAllIcon = new ImageIcon(getClass().getClassLoader().getResource("selectall.png"));
		ImageIcon findIcon = new ImageIcon(getClass().getClassLoader().getResource("find.png"));
		ImageIcon replaceIcon = new ImageIcon(getClass().getClassLoader().getResource("replace.png"));
		ImageIcon gotoIcon = new ImageIcon(getClass().getClassLoader().getResource("goto.png"));
		
		gui.iUndo = new JMenuItem("Undo");
		gui.iUndo.setIcon(undoIcon);
		gui.iUndo.setMargin(new Insets(2, -20, 2, 2));
		gui.iUndo.addActionListener(this);
		gui.iUndo.setActionCommand("Undo");
		gui.iUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iUndo);
		
		gui.iRedo = new JMenuItem("Redo");
		gui.iRedo.setIcon(redoIcon);
		gui.iRedo.setMargin(new Insets(2, -20, 2, 2));
		gui.iRedo.addActionListener(this);
		gui.iRedo.setActionCommand("Redo");
		gui.iRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iRedo);
		
		gui.menuEdit.addSeparator();
		
		gui.iCut = new JMenuItem("Cut");
		gui.iCut.setIcon(cutIcon);
		gui.iCut.setMargin(new Insets(2, -20, 2, 2));
		gui.iCut.addActionListener(this);
		gui.iCut.setActionCommand("Cut");
		gui.iCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iCut);
		
		gui.iCopy = new JMenuItem("Copy");
		gui.iCopy.setIcon(copyIcon);
		gui.iCopy.setMargin(new Insets(2, -20, 2, 2));
		gui.iCopy.addActionListener(this);
		gui.iCopy.setActionCommand("Copy");
		gui.iCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iCopy);
		
		gui.iPaste = new JMenuItem("Paste");
		gui.iPaste.setIcon(pasteIcon);
		gui.iPaste.setMargin(new Insets(2, -20, 2, 2));
		gui.iPaste.addActionListener(this);
		gui.iPaste.setActionCommand("Paste");
		gui.iPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iPaste);

		gui.iDelete = new JMenuItem("Delete");
		gui.iDelete.setIcon(deleteIcon);
		gui.iDelete.setMargin(new Insets(2, -20, 2, 2));
		gui.iDelete.addActionListener(this);
		gui.iDelete.setActionCommand("Delete");
		gui.menuEdit.add(gui.iDelete);
		
		
		gui.iSelectAll = new JMenuItem("Select All");
		gui.iSelectAll.setIcon(selectAllIcon);
		gui.iSelectAll.setMargin(new Insets(2, -20, 2, 2));
		gui.iSelectAll.addActionListener(this);
		gui.iSelectAll.setActionCommand("SelectAll");
		gui.iSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iSelectAll);
		
		gui.menuEdit.addSeparator();

		gui.iFind = new JMenuItem("Find...");
		gui.iFind.setIcon(findIcon);
		gui.iFind.setMargin(new Insets(2, -20, 2, 2));
		gui.iFind.addActionListener(this);
		gui.iFind.setActionCommand("Find");
		gui.iFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iFind);

		gui.iReplace = new JMenuItem("Replace...");
		gui.iReplace.setIcon(replaceIcon);
		gui.iReplace.setMargin(new Insets(2, -20, 2, 2));
		gui.iReplace.addActionListener(this);
		gui.iReplace.setActionCommand("Replace");
		gui.iReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iReplace);

		gui.iGoTo = new JMenuItem("Goto...");
		gui.iGoTo.setIcon(gotoIcon);
		gui.iGoTo.setMargin(new Insets(2, -20, 2, 2));
		gui.iGoTo.addActionListener(this);
		gui.iGoTo.setActionCommand("Goto");
		gui.iGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		gui.menuEdit.add(gui.iGoTo);
		
//		menuEdit.add(new JMenuItem(new ShowFindDialogAction()));
//		menuEdit.add(new JMenuItem(new ShowReplaceDialogAction()));
//		menuEdit.add(new JMenuItem(new GoToLineAction()));
	}
	
	public void createViewMenu() {
		
		gui.iMinimap = new JCheckBoxMenuItem("Hide Minimap");
		gui.iMinimap.addActionListener(this);
		gui.iMinimap.setActionCommand("Minimap");
		gui.menuView.add(gui.iMinimap);
		
		gui.iStatusBar = new JCheckBoxMenuItem("Hide Status Bar");
		gui.iStatusBar.addActionListener(this);
		gui.iStatusBar.setActionCommand("StatusBar");
		gui.menuView.add(gui.iStatusBar);
		
		gui.menuView.addSeparator();
		
		gui.iWordWrap = new JCheckBoxMenuItem("Word Wrap");
		gui.iWordWrap.addActionListener(this);
		gui.iWordWrap.setActionCommand("WordWrap");
		gui.menuView.add(gui.iWordWrap);
		
	}
	
	public void createPreferencesMenu() {
		
		gui.menuTheme = new JMenu("Select Theme");
		gui.menuTheme.setMargin(new Insets(2, -20, 2, 2));
		gui.menuPreferences.add(gui.menuTheme);

		gui.iTheme1 = new JCheckBoxMenuItem("Alabaster");
		gui.iTheme1.addActionListener(this);
		gui.iTheme1.setActionCommand("Theme1");
		gui.menuTheme.add(gui.iTheme1);
		
		gui.iTheme2 = new JCheckBoxMenuItem("Cinder & Ash");
		gui.iTheme2.addActionListener(this);
		gui.iTheme2.setActionCommand("Theme2");
		gui.menuTheme.add(gui.iTheme2);
		
		gui.iTheme3 = new JCheckBoxMenuItem("Vaporwave 77");
		gui.iTheme3.addActionListener(this);
		gui.iTheme3.setActionCommand("Theme3");
		gui.menuTheme.add(gui.iTheme3);
		
		gui.iTheme4 = new JCheckBoxMenuItem("Matcha Latte");
		gui.iTheme4.addActionListener(this);
		gui.iTheme4.setActionCommand("Theme4");
		gui.menuTheme.add(gui.iTheme4);
		
		gui.iTheme5 = new JCheckBoxMenuItem("Crème Brûlée");
		gui.iTheme5.addActionListener(this);
		gui.iTheme5.setActionCommand("Theme5");
		gui.menuTheme.add(gui.iTheme5);
		
		gui.menuThemeCustom = new JMenu("Customize...");
		gui.menuTheme.add(gui.menuThemeCustom);
		
		gui.iThemeCustom = new JCheckBoxMenuItem("Custom Theme");
		gui.iThemeCustom.addActionListener(this);
		gui.iThemeCustom.setActionCommand("CustomTheme");
		gui.menuThemeCustom.add(gui.iThemeCustom);
		
		gui.iThemeConfig = new JMenuItem("Config...");
		gui.iThemeConfig.addActionListener(this);
		gui.iThemeConfig.setActionCommand("CustomConfig");
		gui.menuThemeCustom.add(gui.iThemeConfig);

		gui.menuPreferences.addSeparator();
		
		gui.menuFont = new JMenu("Font");
		gui.menuFont.setMargin(new Insets(2, -20, 2, 2));
		gui.menuPreferences.add(gui.menuFont);

		gui.iFontChoose = new JMenuItem("Choose...");
		gui.iFontChoose.setMargin(new Insets(2, -20, 2, 2));
		gui.iFontChoose.addActionListener(this);
		gui.iFontChoose.setActionCommand("FontChoose");
		gui.menuFont.add(gui.iFontChoose);

		gui.iFontLarger = new JMenuItem("Larger");
		gui.iFontLarger.setMargin(new Insets(2, -20, 2, 2));
		gui.iFontLarger.addActionListener(this);
		gui.iFontLarger.setActionCommand("FontLarger");
		gui.iFontLarger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK));
		gui.menuFont.add(gui.iFontLarger);
		
		gui.iFontSmaller = new JMenuItem("Smaller");
		gui.iFontSmaller.setMargin(new Insets(2, -20, 2, 2));
		gui.iFontSmaller.addActionListener(this);
		gui.iFontSmaller.setActionCommand("FontSmaller");
		gui.iFontSmaller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
		gui.menuFont.add(gui.iFontSmaller);
		
		gui.menuFont.addSeparator();
		
		gui.iFontReset = new JMenuItem("Reset Size");
		gui.iFontReset.setMargin(new Insets(2, -20, 2, 2));
		gui.iFontReset.addActionListener(this);
		gui.iFontReset.setActionCommand("FontReset");
		gui.menuFont.add(gui.iFontReset);
		
	}
	
	public void createHelpMenu() {

		ImageIcon twitterIcon = new ImageIcon(getClass().getClassLoader().getResource("twitter.png"));
		ImageIcon githubIcon = new ImageIcon(getClass().getClassLoader().getResource("github.png"));
		ImageIcon changelogIcon = new ImageIcon(getClass().getClassLoader().getResource("changelog.png"));
		ImageIcon aboutIcon = new ImageIcon(getClass().getClassLoader().getResource("about.png"));
		ImageIcon reportabugIcon = new ImageIcon(getClass().getClassLoader().getResource("reportabug.png"));
		ImageIcon sponsorIcon = new ImageIcon(getClass().getClassLoader().getResource("sponsor.png"));
		
		gui.iTwitter = new JMenuItem("Twitter");
		gui.iTwitter.setIcon(twitterIcon);
		gui.iTwitter.setMargin(new Insets(2, -20, 2, 2));
		gui.iTwitter.addActionListener(this);
		gui.iTwitter.setActionCommand("Twitter");
		gui.menuHelp.add(gui.iTwitter);
		
		gui.iGithub = new JMenuItem("Github");
		gui.iGithub.setIcon(githubIcon);
		gui.iGithub.setMargin(new Insets(2, -20, 2, 2));
		gui.iGithub.addActionListener(this);
		gui.iGithub.setActionCommand("Github");
		gui.menuHelp.add(gui.iGithub);
		
		gui.iChangeLog = new JMenuItem("Changelog...");
		gui.iChangeLog.setIcon(changelogIcon);
		gui.iChangeLog.setMargin(new Insets(2, -20, 2, 2));
		gui.iChangeLog.addActionListener(this);
		gui.iChangeLog.setActionCommand("ChangeLog");
		gui.menuHelp.add(gui.iChangeLog);
		
		gui.menuHelp.addSeparator();
		
		gui.iAbout = new JMenuItem("About " + gui.appName);
		gui.iAbout.setIcon(aboutIcon);
		gui.iAbout.setMargin(new Insets(2, -20, 2, 2));
		gui.iAbout.addActionListener(this);
		gui.iAbout.setActionCommand("About");
		gui.menuHelp.add(gui.iAbout);
		
		gui.iReportaBug = new JMenuItem("Report a Bug");
		gui.iReportaBug.setIcon(reportabugIcon);
		gui.iReportaBug.setMargin(new Insets(2, -20, 2, 2));
		gui.iReportaBug.addActionListener(this);
		gui.iReportaBug.setActionCommand("ReportBug");
		gui.menuHelp.add(gui.iReportaBug);
		
		gui.iSponsor = new JMenuItem("Sponsor");
		gui.iSponsor.setIcon(sponsorIcon);
		gui.iSponsor.setMargin(new Insets(2, -20, 2, 2));
		gui.iSponsor.addActionListener(this);
		gui.iSponsor.setActionCommand("Sponsor");
		gui.menuHelp.add(gui.iSponsor);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		String url;
		
		switch(command) {
		case "New": gui.file.newFile(); break;
		case "NewWindow": gui.file.newWindow(); break;
		case "CloseWindow": gui.file.closeWindow(); break;
		case "Open": gui.file.open(); break;
		case "OpenFolder": gui.file.openFolder(); break;
		case "Save": gui.file.save(); break;
		case "SaveAs": gui.file.saveAs(); break;
		case "Exit": gui.file.exit(); break;
		case "Undo": gui.edit.undo(); break;
		case "Redo": gui.edit.redo(); break;
		case "Cut": gui.edit.cut(); break;
		case "Copy": gui.edit.copy(); break;
		case "Paste": gui.edit.paste(); break;
		case "Delete": gui.edit.delete(); break;
		case "SelectAll": gui.edit.selectAll(); break;
		case "Find": gui.edit.find(); break;
		case "Replace": gui.edit.replace(); break;
		case "Goto": gui.edit.goTo(); break;
		case "Minimap": gui.view.minimap(); break;
		case "StatusBar": gui.view.statusbar(); break;
		case "WordWrap": gui.view.wordWrap(); break;
		case "Theme1": gui.pref.setThemeAlabaster(); break;
		case "Theme2": gui.pref.setThemeCinderandAsh(); break;
		case "Theme3": gui.pref.setThemeVaporwave77(); break;
		case "Theme4": gui.pref.setThemeMatchaLatte(); break;
		case "Theme5": gui.pref.setThemeCremeBrulee(); break;
		case "CustomTheme": gui.pref.setCustomTheme(); break;
		case "CustomConfig": gui.pref.openConfig(); break;
		case "FontChoose": gui.pref.fontChoose(); break;
		case "FontLarger": gui.pref.fontLarger(); break;
		case "FontSmaller": gui.pref.fontSmaller(); break;
		case "FontReset": gui.pref.fontReset(); break;
		case "Twitter": url = "https://x.com/thejakobe05"; openLink(url); break;
		case "Github": url = "https://github.com/TheJakobe05"; openLink(url); break;
		case "ChangeLog": url = "https://github.com/TheJakobe05/PHNX/releases"; openLink(url); break;
		case "About": gui.help.assembleMasthead(); break;
		case "ReportBug": url = "https://mailto:jacoblynnortiz.jlo@gmail.com"; openLink(url); break;
		case "Sponsor": url = "https://www.patreon.com/cw/TheJakobe05"; openLink(url); break;
		}
	}
	
	public void openLink(String url) {
		try {
            // Check if the Desktop API is supported on the current platform
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                // Get the Desktop instance and open the URI
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop browsing is not supported on this platform.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}