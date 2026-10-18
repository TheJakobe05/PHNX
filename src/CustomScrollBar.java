import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBar extends BasicScrollBarUI {
	private final int thicknessOffset = 2;
    private Color trackColorCustom;
    private Color thumbColorCustom;

    public CustomScrollBar(Color trackColor, Color thumbColor) {
        this.trackColorCustom = trackColor;
        this.thumbColorCustom = thumbColor;
    }

    // Setter methods to change colors dynamically later
    public void setColors(Color trackColor, Color thumbColor) {
        this.trackColorCustom = trackColor;
        this.thumbColorCustom = thumbColor;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColorCustom);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !c.isEnabled()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = thumbBounds.x;
        int y = thumbBounds.y;
        int width = thumbBounds.width;
        int height = thumbBounds.height;

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            x += thicknessOffset;
            width -= (thicknessOffset * 2);
        } else {
            y += thicknessOffset;
            height -= (thicknessOffset * 2);
        }

        g2.setColor(thumbColorCustom);
        g2.fillRoundRect(x, y, width, height, 8, 8);
        g2.dispose();
    }

    @Override protected JButton createDecreaseButton(int o) { return createZeroButton(); }
    @Override protected JButton createIncreaseButton(int o) { return createZeroButton(); }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}
