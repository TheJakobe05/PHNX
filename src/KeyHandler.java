import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

public class KeyHandler implements KeyListener {
	
	GUI gui;
	
	public KeyHandler(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		FILE MENU SHORTCUTS
//		if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_N) {
//			gui.file.newFile();
//		}
//		if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_O) {
//			gui.file.open();
//		}
//		if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_S) {
//			gui.file.save();
//		}
//		if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_S) {
//			gui.file.saveAs();
//		}
//		if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_N) {
//			gui.file.newWindow();
//		}
//		if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_W) {
//			gui.file.closeWindow();
//		}
////		EDIT MENU SHORTCUTS
//		if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z) {
//			gui.edit.undo();
//		}
//		if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Y) {
//			gui.edit.redo();
//		}
//		SECRET EASTER EGGS
		if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_1) {
			new Calculator();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	private String typedBuffer = "";

	public void setupDiscoTrigger() {
	    gui.textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	        @Override
	        public void insertUpdate(javax.swing.event.DocumentEvent e) {
	            checkForDisco();
	        }
	        @Override public void removeUpdate(javax.swing.event.DocumentEvent e) {}
	        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) {}

	        private void checkForDisco() {
	            try {
	                int len = gui.textArea.getDocument().getLength();
	                int start = Math.max(0, len - 10);
	                String recent = gui.textArea.getDocument().getText(start, len - start).toLowerCase();

	                if (recent.endsWith("disco")) {
	                	gui.disco.start(5000);   // 5 seconds of disco
	                    // Optional: remove the word so it doesn't re-trigger
	                    // textArea.getDocument().remove(len - 5, 5);
	                }
	                if (recent.endsWith("fallout") || recent.endsWith("vault76")) {
	                    gui.pref.setThemeFallout();
	                }
	                if (recent.endsWith("iddqd")) {
	                    gui.pref.setThemeDoom();
	                }
//	                sound easter eggs
	                if (recent.endsWith("horn")) {
	                	SoundPlayer.play("/horn.wav");
	                }
	                if (recent.endsWith("boom")) {
	                	SoundPlayer.play("/boom.wav");
	                }
	                if (recent.endsWith("sus")) {
	                	SoundPlayer.play("/sus.wav");
	                }
	            } catch (Exception ignored) {}
	        }
	    });
	}
	
	public void setupGravityTrigger() {
	    gui.textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	        private String last = "";

	        @Override
	        public void insertUpdate(javax.swing.event.DocumentEvent e) {
	            SwingUtilities.invokeLater(this::check);
	        }
	        @Override public void removeUpdate(javax.swing.event.DocumentEvent e) {}
	        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) {}

	        private void check() {
	        	if (gui.gravity.isRunning()) return;   // ← add this line
	            try {
	                String text = gui.textArea.getText().toLowerCase();
	                if (text.endsWith("gravity") && !last.endsWith("gravity")) {
	                    gui.gravity.start();
	                }
	                last = text;
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
	}

}
