
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

public class EasterEggs {
    private final GUI gui;
    private final int[] konami = {
        KeyEvent.VK_UP, KeyEvent.VK_UP,
        KeyEvent.VK_DOWN, KeyEvent.VK_DOWN,
        KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
        KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
        KeyEvent.VK_B, KeyEvent.VK_A
    };
    private int progress = 0;

    public EasterEggs(GUI gui) {
        this.gui = gui;
        setupKonamiListener();
    }

    private void setupKonamiListener() {
        // Listen globally so it works even when the text area has focus
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(e -> {
                if (e.getID() != KeyEvent.KEY_PRESSED) return false;
                if (!SwingUtilities.isDescendingFrom(e.getComponent(), gui.window)) return false;

                if (e.getKeyCode() == konami[progress]) {
                    progress++;
                    if (progress == konami.length) {
                        triggerKonami();
                        progress = 0;
                    }
                } else {
                    progress = (e.getKeyCode() == konami[0]) ? 1 : 0;
                }
                return false; // don't consume the event
            });
    }

    private void triggerKonami() {
    	new MatrixRain(gui.window).play(10000);
    }
}