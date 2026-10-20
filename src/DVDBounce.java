import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DVDBounce extends JWindow {
    private final List<Image> icons = new ArrayList<>();
    private int currentIcon = 0;
    private int x = 200, y = 200;
    private int dx = 3, dy = 2;
    private final int size = 200;
    private Timer timer;

    public DVDBounce(JFrame owner) {
        setSize(owner.getWidth(), owner.getHeight());
        if (owner.isShowing()) {
            setLocation(owner.getLocationOnScreen());
        } else {
            setLocation(owner.getLocation());
        }
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));

        // Load your colored DVD images
        // Change these names to match your files
        String[] imageNames = {
            "dvdBlue.png",
            "dvdGreen.png",
            "dvdMagenta.png",
            "dvdOrange.png",
            "dvdPurple.png",
            "dvdRed.png",
            "dvdWhite.png",
            "dvdYellow.png"
        };

        for (String name : imageNames) {
            ImageIcon ii = new ImageIcon(getClass().getClassLoader().getResource(name));
            if (ii.getIconWidth() > 0) {
                icons.add(ii.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
            }
        }

        // Fallback if none loaded
        if (icons.isEmpty()) {
            icons.add(null);
        }

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Image img = icons.get(currentIcon);
                if (img != null) {
                    g2.drawImage(img, x, y, size, size, this);
                } else {
                    g2.setColor(new Color(0, 170, 255));
                    g2.fillRoundRect(x, y, size, size, 16, 16);
                }
                g2.dispose();
            }
        };
        canvas.setOpaque(false);
        setContentPane(canvas);
    }

    public void start() {
        if (timer != null && timer.isRunning()) return;

        setVisible(true);
        timer = new Timer(16, e -> {
            try {
                boolean bounced = false;
                x += dx;
                y += dy;

                if (x <= 0 || x + size >= getWidth()) {
                    dx = -dx;
                    bounced = true;
                }
                if (y <= 0 || y + size >= getHeight()) {
                    dy = -dy;
                    bounced = true;
                }

                x = Math.max(0, Math.min(x, getWidth() - size));
                y = Math.max(0, Math.min(y, getHeight() - size));

                if (bounced && icons.size() > 1) {
                    currentIcon = (currentIcon + 1) % icons.size();
                }
                repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        setVisible(false);
        dispose();
        removeAll();
    }
}