import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class IdleDVDBounce {
    private final GUI gui;
    private DVDBounce currentBounce;
    private Timer idleTimer;
    private boolean bounceActive = false;

    public IdleDVDBounce(GUI gui) {
        this.gui = gui;
        setup();
    }

    private void setup() {
        idleTimer = new Timer(300000, e -> {
            stopBounce();
            currentBounce = new DVDBounce(gui.window);
            currentBounce.start();
            bounceActive = true;
        });
        idleTimer.setRepeats(false);

        MouseMotionAdapter reset = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseActivity();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseActivity();
            }
        };

        gui.window.addMouseMotionListener(reset);
        gui.textArea.addMouseMotionListener(reset);
        gui.scrollPane.addMouseMotionListener(reset);
        if (gui.fileTree != null) {
            gui.fileTree.addMouseMotionListener(reset);
        }

        idleTimer.start();
    }

    private void handleMouseActivity() {
        if (bounceActive) {
            // Only stop if the bounce is currently showing
            stopBounce();
        }
        if (idleTimer != null) {
            idleTimer.restart();
        }
    }

    public void shutdown() {
        if (idleTimer != null) {
            idleTimer.stop();
            idleTimer = null;
        }
        stopBounce();
    }

    private void stopBounce() {
        bounceActive = false;
        if (currentBounce != null) {
            currentBounce.stop();
            currentBounce = null;
        }
    }
}