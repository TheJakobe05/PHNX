import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
public class Minimap extends JPanel {
    private final RSyntaxTextArea textArea;
    private final RTextScrollPane scrollPane;
    private final JScrollBar verticalBar;
    private static final int MINIMAP_WIDTH = 120;
    private static Color VIEWPORT_COLOR = new Color(201, 123, 99, 80);
    private static Color VIEWPORT_BORDER = new Color(201, 123, 99);
    private static final Color BG = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(120, 120, 120);
    public Minimap(RSyntaxTextArea textArea, RTextScrollPane scrollPane) {
        this.textArea = textArea;
        this.scrollPane = scrollPane;
        this.verticalBar = scrollPane.getVerticalScrollBar();
        setPreferredSize(new Dimension(MINIMAP_WIDTH, 100));
        setBackground(BG);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        // Redraw when document or scroll changes
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { repaint(); }
            public void removeUpdate(DocumentEvent e) { repaint(); }
            public void changedUpdate(DocumentEvent e) { repaint(); }
        });
        verticalBar.addAdjustmentListener(e -> repaint());
        textArea.addCaretListener(e -> repaint());
        textArea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
        // Click / drag to scroll
        MouseAdapter mouse = new MouseAdapter() {
            private void scrollTo(MouseEvent e) {
                int lineCount = Math.max(1, textArea.getLineCount());
                float ratio = (float) e.getY() / Math.max(1, getHeight());
                int targetLine = (int) (ratio * lineCount);
                targetLine = Math.max(0, Math.min(targetLine, lineCount - 1));
                try {
                    int offset = textArea.getLineStartOffset(targetLine);
                    Rectangle r = textArea.modelToView2D(offset).getBounds();
                    JViewport vp = scrollPane.getViewport();
                    int viewHeight = vp.getExtentSize().height;
                    int y = Math.max(0, r.y - viewHeight / 3);
                    vp.setViewPosition(new Point(0, y));
                } catch (BadLocationException ex) {
                    // ignore
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                scrollTo(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                scrollTo(e);
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int lineCount = Math.max(1, textArea.getLineCount());
        // Tiny font so many lines fit
        float fontSize = Math.max(1.5f, Math.min(3.5f, (float) h / lineCount));
        Font miniFont = textArea.getFont().deriveFont(fontSize);
        g2.setFont(miniFont);
        FontMetrics fm = g2.getFontMetrics();
        int charW = Math.max(1, fm.charWidth('m'));
        int maxChars = Math.max(1, (w - 4) / charW);
        float lineHeight = (float) h / lineCount;
        g2.setColor(TEXT_COLOR);
        // For huge files, skip some lines so it stays fast
        int step = 1;
        if (lineCount > h * 2) {
            step = Math.max(1, lineCount / h);
        }
        for (int i = 0; i < lineCount; i += step) {
            int y = (int) (i * lineHeight) + fm.getAscent();
            if (y > h) break;
            try {
                int start = textArea.getLineStartOffset(i);
                int end = textArea.getLineEndOffset(i);
                int len = Math.min(maxChars, end - start);
                if (len <= 0) continue;
                String line = textArea.getText(start, len);
                // Drop trailing newline
                if (line.endsWith("\n") || line.endsWith("\r")) {
                    line = line.substring(0, line.length() - 1);
                }
                if (!line.isEmpty()) {
                    g2.drawString(line, 2, y);
                }
            } catch (BadLocationException ignored) {
            }
        }
        // Viewport rectangle (unchanged)
        JViewport vp = scrollPane.getViewport();
        if (vp != null && textArea.getHeight() > 0) {
            double scale = (double) h / textArea.getHeight();
            int viewY = (int) (vp.getViewPosition().y * scale);
            int viewH = Math.max(4, (int) (vp.getExtentSize().height * scale));
            g2.setColor(VIEWPORT_COLOR);
            g2.fillRect(0, viewY, w, viewH);
            g2.setColor(VIEWPORT_BORDER);
            g2.drawRect(0, viewY, w - 1, viewH);
        }
        g2.dispose();
    }
    private void drawLinePreview(Graphics2D g2, int line, int y, int maxWidth) {
        try {
            int start = textArea.getLineStartOffset(line);
            int end = textArea.getLineEndOffset(line);
            String text = textArea.getText(start, Math.min(40, end - start)).trim();
            if (!text.isEmpty()) {
                // Simple bar proportional to line length
                int barWidth = Math.min(maxWidth - 4, 4 + text.length());
                g2.fillRect(2, y, barWidth, Math.max(1, (int) ((float) getHeight() / textArea.getLineCount())));
            }
        } catch (BadLocationException ignored) {
        }
    }
    
    public void setViewportColors(Color fill, Color border) {
        this.VIEWPORT_COLOR = fill;
        this.VIEWPORT_BORDER = border;
        repaint();
    }
}