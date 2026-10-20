import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MatrixRain extends JWindow {
    private final Random rand = new Random();
    private final char[] glyphs = (
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "abcdefghijklmnopqrstuvwxyz" +
        "0123456789" +
        "@#$%&*+-/=<>[]{}|~"
    ).toCharArray();

    private final int fontSize = 14;
    private final int columns;
    private final int[] y;
    private final int[] speed;
    private final int[] trailLength;
    private final Timer timer;

    public MatrixRain(JFrame owner) {
        setSize(owner.getWidth(), owner.getHeight());
        setLocation(owner.getLocationOnScreen());
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));

        columns = Math.max(1, getWidth() / fontSize);
        y = new int[columns];
        speed = new int[columns];
        trailLength = new int[columns];

        for (int i = 0; i < columns; i++) {
            y[i] = rand.nextInt(getHeight() / fontSize);
            speed[i] = 1 + rand.nextInt(2);
            trailLength[i] = 8 + rand.nextInt(18);
        }

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // deep black with a slight green tint fade
                g2.setColor(new Color(0, 8, 0, 40));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setFont(new Font("Monospaced", Font.BOLD, fontSize));

                for (int i = 0; i < columns; i++) {
                    int colX = i * fontSize;

                    // draw the trail (fading green)
                    for (int t = trailLength[i]; t > 0; t--) {
                        float brightness = 1f - (t / (float) trailLength[i]);
                        int green = (int) (40 + brightness * 215);
                        int alpha = (int) (brightness * 220);
                        g2.setColor(new Color(0, green, 0, alpha));

                        char c = glyphs[rand.nextInt(glyphs.length)];
                        g2.drawString(String.valueOf(c), colX, (y[i] - t) * fontSize);
                    }

                    // bright white-green head
                    g2.setColor(new Color(200, 255, 200));
                    char head = glyphs[rand.nextInt(glyphs.length)];
                    g2.drawString(String.valueOf(head), colX, y[i] * fontSize);

                    // advance
                    y[i] += speed[i];
                    if (y[i] * fontSize > getHeight() + trailLength[i] * fontSize) {
                        if (rand.nextFloat() > 0.96f) {
                            y[i] = 0;
                            speed[i] = 1 + rand.nextInt(2);
                            trailLength[i] = 8 + rand.nextInt(18);
                        }
                    }
                }
                g2.dispose();
            }
        };
        canvas.setOpaque(false);
        setContentPane(canvas);

        timer = new Timer(33, (ActionEvent e) -> canvas.repaint());
    }

    public void play(int durationMs) {
        setVisible(true);
        timer.start();

        Timer stopper = new Timer(durationMs, e -> {
            timer.stop();
            setVisible(false);
            dispose();
        });
        stopper.setRepeats(false);
        stopper.start();
    }
}