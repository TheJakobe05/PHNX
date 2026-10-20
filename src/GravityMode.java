import javax.swing.Timer;

public class GravityMode {
    private final GUI gui;
    private String originalText;
    private Timer fallTimer;
    private Timer stopTimer;
    private int step;
    private boolean running = false;   // ← add this

    public GravityMode(GUI gui) {
        this.gui = gui;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        if (running) return;
        running = true;

        originalText = gui.textArea.getText();
        String[] lines = originalText.split("\n", -1);
        step = 0;

        fallTimer = new Timer(70, e -> {
            step++;
            StringBuilder sb = new StringBuilder();
            int blanks = Math.min(step, 40);
            for (int i = 0; i < blanks; i++) sb.append("\n");
            for (String line : lines) sb.append(line).append("\n");

            gui.textArea.setText(sb.toString());
            gui.textArea.setCaretPosition(gui.textArea.getDocument().getLength());
        });
        fallTimer.start();

        stopTimer = new Timer(5000, e -> {
            fallTimer.stop();
            gui.textArea.setText(originalText);
            gui.textArea.setCaretPosition(0);
            running = false;               // ← allow it to be triggered again later
            ((Timer) e.getSource()).stop();
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }
    
}