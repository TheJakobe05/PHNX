import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8096874771327847013L;
	
	private static final int TILE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private int dirX = 1, dirY = 0;
    private boolean running = true;
    private final Timer timer;
    private final Random rand = new Random();

    @SuppressWarnings("unused")
	public SnakeGame() {
        setTitle("PHNX - Snake");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel board = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // background
                g.setColor(new Color(20, 20, 25));
                g.fillRect(0, 0, getWidth(), getHeight());

                // food
                g.setColor(new Color(220, 80, 80));
                g.fillRect(food.x * TILE, food.y * TILE, TILE, TILE);

                // snake
                g.setColor(new Color(80, 200, 120));
                for (Point p : snake) {
                    g.fillRect(p.x * TILE, p.y * TILE, TILE - 1, TILE - 1);
                }
            }
        };
        board.setPreferredSize(new Dimension(WIDTH * TILE, HEIGHT * TILE));
        board.setFocusable(true);
        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    if (dirY == 0) { dirX = 0; dirY = -1; } break;
                    case KeyEvent.VK_DOWN:  if (dirY == 0) { dirX = 0; dirY = 1; } break;
                    case KeyEvent.VK_LEFT:  if (dirX == 0) { dirX = -1; dirY = 0; } break;
                    case KeyEvent.VK_RIGHT: if (dirX == 0) { dirX = 1; dirY = 0; } break;
                }
            }
        });

        add(board);
        pack();
        setLocationRelativeTo(null);

        // init snake
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        spawnFood();

        timer = new Timer(120, e -> tick());
    }

    public void start() {
        setVisible(true);
        timer.start();
        getContentPane().getComponent(0).requestFocusInWindow();
    }

    private void tick() {
        if (!running) return;

        Point head = snake.getFirst();
        Point next = new Point(head.x + dirX, head.y + dirY);

        // wall collision
        if (next.x < 0 || next.x >= WIDTH || next.y < 0 || next.y >= HEIGHT || snake.contains(next)) {
            running = false;
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over! Length: " + snake.size());
            dispose();
            return;
        }

        snake.addFirst(next);
        if (next.equals(food)) {
            spawnFood();
        } else {
            snake.removeLast();
        }
        repaint();
    }

    private void spawnFood() {
        do {
            food = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        } while (snake.contains(food));
    }
}