
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;

public class TextArea {
	
	GUI gui;
	
	public TextArea(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void createTextArea() {
		
		gui.textArea.setTabSize(5);
		Font myFont = new Font(gui.pref.font, Font.PLAIN, gui.pref.fontSize);
		gui.textArea.setFont(myFont); 
        
        // 3. Configure the syntax highlighting style (e.g., Java)
		gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        
        // 4. Enable modern IDE features like code folding
		gui.textArea.setCodeFoldingEnabled(true);
        
		gui.textArea.setHighlightCurrentLine(false);
        
		gui.textArea.setCloseCurlyBraces(true); 
		gui.textArea.setInsertPairedCharacters(true);
		gui.textArea.setCloseMarkupTags(true);
		
		gui.textArea.addKeyListener(gui.kHandler);
		
		gui.textArea.getDocument().addUndoableEditListener(
			new UndoableEditListener() {
				public void undoableEditHappened(UndoableEditEvent e) {
					gui.um.addEdit(e.getEdit());
				}
			}
		);
		
		gui.textArea.addCaretListener(new CaretListener() {
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

                    gui.colRowLabel.setText("Line " + line + ", Column " + column);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
		
		gui.textArea.getDocument().addDocumentListener(new DocumentListener() {
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
		    	
		    }

		    private void updateSaveState() {
		        if(gui.fileSaved == true) {
		        	gui.fileSaved = false;
		        	gui.window.setTitle(gui.window.getTitle() + "*");
		        } else {
		        	return;
		        }
		    }
		});
		
		// --- Spell checking ---
		// Spell checker
	    try {
	        File dictZip = new File("res/english_dic.zip"); // <-- update path
	        gui.spellingParser = SpellingParser.createEnglishSpellingParser(dictZip, true);
	        gui.textArea.addParser(gui.spellingParser);
	        
	        gui.spellingParser.setSquiggleUnderlineColor(Color.RED);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
		AutoComplete autoComplete = new AutoComplete(gui);// Minimap on the right

		autoComplete.setupAutoComplete();
		
	    LanguageSupportFactory.get().register(gui.textArea);// Make Java autocomplete work (finds java.lang.Object, String, etc.)
		
		// Put scroll pane + minimap in a panel
		JPanel editorPanel = new JPanel(new BorderLayout());
		editorPanel.add(gui.scrollPane, BorderLayout.CENTER);
		editorPanel.add(gui.minimap, BorderLayout.EAST);

		// Add the combined panel to the window (instead of only scrollPane)
		gui.window.add(editorPanel, BorderLayout.CENTER);
		
		gui.lineNumbers = gui.scrollPane.getGutter();
		
		gui.lineNumbers.setBackground(Color.LIGHT_GRAY);
		gui.lineNumbers.setLineNumberFont(gui.textArea.getFont()); // Match fonts
		gui.lineNumbers.setBorderColor(null);
		gui.lineNumbers.setFont(myFont);
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#C17B5C"));
		
		// Drag & drop: each dropped file opens in a NEW window
		DropTargetAdapter dropHandler = new DropTargetAdapter() {
		    @Override
		    public void drop(DropTargetDropEvent dtde) {
		        try {
		            dtde.acceptDrop(DnDConstants.ACTION_COPY);

		            @SuppressWarnings("unchecked")
		            List<File> files = (List<File>) dtde.getTransferable()
		                    .getTransferData(DataFlavor.javaFileListFlavor);

		            for (File dropped : files) {
		                if (dropped != null && dropped.isFile()) {
		                    // New window per file
		                    GUI newWindow = new GUI();
		                    newWindow.openFileFromDrop(dropped);
		                }
		            }

		            dtde.dropComplete(true);
		        } catch (Exception ex) {
		            dtde.dropComplete(false);
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(
		            		gui.window,
		                    "Could not open dropped file(s):\n" + ex.getMessage(),
		                    "Drop Error",
		                    JOptionPane.ERROR_MESSAGE
		            );
		        }
		    }
		};

		// Accept drops on the text area, scroll pane, and whole editor panel
		new DropTarget(gui.textArea, dropHandler);
		new DropTarget(gui.scrollPane, dropHandler);
		new DropTarget(editorPanel, dropHandler);
	}
}
