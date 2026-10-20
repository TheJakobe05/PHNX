import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MortalCombatFatality {
    private final GUI gui;
    private final int[] code = {
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_A, KeyEvent.VK_C,
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_B
    };
    private int progress = 0;

    public MortalCombatFatality(GUI gui) {
        this.gui = gui;
        setup();
    }

    private void setup() {
        gui.textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == code[progress]) {
                    progress++;
                    if (progress == code.length) {
                        trigger();
                        progress = 0;
                    }
                } else {
                    progress = (e.getKeyCode() == code[0]) ? 1 : 0;
                }
            }
        });
    }

    private void trigger() {
        try {
            int caret = gui.textArea.getCaretPosition();
            int line = gui.textArea.getLineOfOffset(caret);
            int start = gui.textArea.getLineStartOffset(line);
            int end = gui.textArea.getLineEndOffset(line);

            String original = gui.textArea.getText(start, end - start);
            int len = original.length();
            if (len == 0) return;

            Timer timer = new Timer(30, null);
            final int[] step = {0};
            final int mid = len / 2;

            timer.addActionListener(ev -> {
                step[0]++;
                int erase = step[0];

                StringBuilder sb = new StringBuilder(original);
                // Erase from the middle outward
                int left = Math.max(0, mid - erase);
                int right = Math.min(len, mid + erase);
                for (int i = left; i < right; i++) {
                    sb.setCharAt(i, ' ');
                }

                try {
                    gui.textArea.replaceRange(sb.toString(), start, end);
                } catch (Exception ignored) {}

                if (erase >= mid + 2) {
                    timer.stop();
                    // Show FATALITY centered
                    String fatality = "FATALITY!";
                    int pad = Math.max(0, (len - fatality.length()) / 2);
                    StringBuilder finalLine = new StringBuilder();
                    for (int i = 0; i < pad; i++) finalLine.append(' ');
                    finalLine.append(fatality);
                    while (finalLine.length() < len) finalLine.append(' ');
                    
                    SoundPlayer.play("/fatality.wav");

                    gui.textArea.replaceRange(finalLine.toString(), start, end);
                }
            });
            timer.start();

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}