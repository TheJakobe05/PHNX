
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;

import org.fife.rsta.ui.GoToDialog;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

public class Function_Edit implements SearchListener {
	
	GUI gui;
	
	// Keep the dialogs as fields so they are created only once
	private FindDialog findDialog;
	private ReplaceDialog replaceDialog;
	
	public Function_Edit(GUI gui) {
		
		this.gui = gui;
		initSearchDialogs();
		
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
	
	// ------------------------------------------------------------------
	// RSTAUI – Find / Replace / Go To
	// ------------------------------------------------------------------
	private void initSearchDialogs() {

		findDialog = new FindDialog(gui.window, this);
		replaceDialog = new ReplaceDialog(gui.window, this);

		// Share the same SearchContext so options stay in sync
		SearchContext context = findDialog.getSearchContext();
		replaceDialog.setSearchContext(context);
		
		ImageIcon appIcon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
		findDialog.setIconImage(appIcon.getImage());
		replaceDialog.setIconImage(appIcon.getImage());
		
		findDialog.setResizable(false);
		replaceDialog.setResizable(false);
	}
	
	/** Call this from your "Find" JMenuItem */
	public void find() {
		if (replaceDialog.isVisible()) {
			replaceDialog.setVisible(false);
		}
		findDialog.setVisible(true);
	}
	
	/** Call this from your "Replace" JMenuItem */
	public void replace() {
		if (findDialog.isVisible()) {
			findDialog.setVisible(false);
		}
		replaceDialog.setVisible(true);
	}
	
	/** Call this from your "Go To Line" JMenuItem */
	public void goTo() {
		if (findDialog.isVisible()) {
			findDialog.setVisible(false);
		}
		if (replaceDialog.isVisible()) {
			replaceDialog.setVisible(false);
		}
		
		GoToDialog dialog = new GoToDialog(gui.window);
		dialog.setMaxLineNumberAllowed(gui.textArea.getLineCount());
		dialog.setResizable(false);
		
		dialog.setVisible(true);

		int line = dialog.getLineNumber();
		if (line > 0) {
			try {
				gui.textArea.setCaretPosition(
						gui.textArea.getLineStartOffset(line - 1));
			} catch (BadLocationException ble) {
				UIManager.getLookAndFeel().provideErrorFeedback(gui.textArea);
			}
		}
	}

	// ------------------------------------------------------------------
		// SearchListener implementation (required by RSTAUI)
		// ------------------------------------------------------------------
		@Override
		public void searchEvent(SearchEvent e) {
			SearchEvent.Type type = e.getType();
			SearchContext context = e.getSearchContext();
			SearchResult result;

			switch (type) {
				case MARK_ALL:
					result = SearchEngine.markAll(gui.textArea, context);
					break;

				case FIND:
					result = SearchEngine.find(gui.textArea, context);
					if (!result.wasFound() || result.isWrapped()) {
						UIManager.getLookAndFeel().provideErrorFeedback(gui.textArea);
					}
					break;

				case REPLACE:
					result = SearchEngine.replace(gui.textArea, context);
					if (!result.wasFound() || result.isWrapped()) {
						UIManager.getLookAndFeel().provideErrorFeedback(gui.textArea);
					}
					break;

				case REPLACE_ALL:
					result = SearchEngine.replaceAll(gui.textArea, context);
					JOptionPane.showMessageDialog(gui.window,
							result.getCount() + " occurrences replaced.");
					break;

				default:
					return;
			}
		}

		@Override
		public String getSelectedText() {
			return gui.textArea.getSelectedText();
		}
	}
