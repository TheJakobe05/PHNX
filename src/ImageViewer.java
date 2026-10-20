import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ImageViewer {
	
	GUI gui;
	
	public ImageViewer(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void openImageInNewFrame(File imageFile) {
	    if (imageFile == null || !imageFile.isFile()) {
	        JOptionPane.showMessageDialog(gui.window, "Invalid image file.");
	        return;
	    }
	 // Animated GIF handling
	    if (imageFile.getName().toLowerCase().endsWith(".gif")) {
	        openAnimatedGif(imageFile);
	        return;
	    }

	    try {
	        BufferedImage original = readImageScaled(imageFile, 4000); // allow a bit larger for zooming
	        if (original == null) {
	            JOptionPane.showMessageDialog(gui.window, "Could not load image: " + imageFile.getName());
	            return;
	        }

	        // --- Zoom state ---
	        final double[] zoom = {1.0}; // mutable holder
	        final JLabel imageLabel = new JLabel();
	        imageLabel.setHorizontalAlignment(JLabel.CENTER);
	        imageLabel.setVerticalAlignment(JLabel.CENTER);

	        // Initial display
	        updateImageLabel(imageLabel, original, zoom[0]);

	        JScrollPane scroll = new JScrollPane(imageLabel);
	        scroll.setBorder(null);
	        scroll.getViewport().setBackground(new Color(40, 40, 45));

	        // --- Zoom controls ---
	        JButton zoomOutBtn = new JButton("−");
	        JButton zoomInBtn  = new JButton("+");
	        JButton resetBtn   = new JButton("100%");
	        JButton fitBtn     = new JButton("Fit");

	        JLabel zoomLabel = new JLabel("100%", SwingConstants.CENTER);
	        zoomLabel.setPreferredSize(new Dimension(60, 25));

	        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
	        controlPanel.setBackground(new Color(50, 50, 55));
	        controlPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 70, 75)));

	        controlPanel.add(zoomOutBtn);
	        controlPanel.add(zoomLabel);
	        controlPanel.add(zoomInBtn);
	        controlPanel.add(Box.createHorizontalStrut(12));
	        controlPanel.add(resetBtn);
	        controlPanel.add(fitBtn);

	        // --- Frame ---
	        JFrame imageFrame = new JFrame(gui.appName + " - " + imageFile.getName());
	        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        imageFrame.setLayout(new BorderLayout());
	        imageFrame.add(scroll, BorderLayout.CENTER);
	        imageFrame.add(controlPanel, BorderLayout.SOUTH);

	        imageFrame.setLocationRelativeTo(gui.window);
	        imageFrame.setMinimumSize(new Dimension(400, 300));

	        // Initial size
	        int w = Math.min(original.getWidth() + 40, 1200);
	        int h = Math.min(original.getHeight() + 80, 800);
	        imageFrame.setSize(w, h);

	        // App icon
	        URL iconUrl = getClass().getResource("/icon.png");
	        if (iconUrl != null) {
	            imageFrame.setIconImage(new ImageIcon(iconUrl).getImage());
	        }

	        // --- Zoom actions ---
	        Runnable updateZoom = () -> {
	            updateImageLabel(imageLabel, original, zoom[0]);
	            zoomLabel.setText(String.format("%.0f%%", zoom[0] * 100));
	            imageLabel.revalidate();
	            scroll.revalidate();
	        };
	        
	        
	        zoomInBtn.addActionListener(e -> {
	            zoom[0] = Math.min(zoom[0] * 1.25, 8.0);
	            updateZoom.run();
	        });

	        zoomOutBtn.addActionListener(e -> {
	            zoom[0] = Math.max(zoom[0] / 1.25, 0.1);
	            updateZoom.run();
	        });

	        resetBtn.addActionListener(e -> {
	            zoom[0] = 1.0;
	            updateZoom.run();
	        });
	        
	     // Fit helper
	        Runnable fitToWindow = () -> {
	            Dimension viewSize = scroll.getViewport().getSize();
	            if (viewSize.width > 10 && viewSize.height > 10) {
	                double scaleX = (double) viewSize.width / original.getWidth();
	                double scaleY = (double) viewSize.height / original.getHeight();
	                zoom[0] = Math.min(scaleX, scaleY) * 0.98; // small padding
	                updateZoom.run();
	            }
	        };
	        
	        fitToWindow.run();
	        
	        fitBtn.addActionListener(e -> fitToWindow.run());

	        // Mouse wheel zoom (optional but nice)
	        imageLabel.addMouseWheelListener(e -> {
	        	if (!e.isControlDown()) {
	                return; // normal scrolling
	            }

	            if (e.getWheelRotation() < 0) {
	                zoom[0] = Math.min(zoom[0] * 1.15, 8.0); // zoom in
	            } else {
	                zoom[0] = Math.max(zoom[0] / 1.15, 0.1); // zoom out
	            }
	            updateZoom.run();
	            e.consume();
	        });
	        
	     // Auto-fit once the viewport is actually sized
	        scroll.getViewport().addComponentListener(new ComponentAdapter() {
	            private boolean fitted = false;

	            @Override
	            public void componentResized(ComponentEvent e) {
	                if (!fitted && e.getComponent().getWidth() > 20 && e.getComponent().getHeight() > 20) {
	                    fitted = true;
	                    fitToWindow.run();
	                    e.getComponent().removeComponentListener(this);
	                }
	            }
	        });

	        imageFrame.setVisible(true);

	    } catch (IOException | OutOfMemoryError ex) {
	        JOptionPane.showMessageDialog(gui.window, "Failed to open image: " + ex.getMessage());
	    }
	}

	private void updateImageLabel(JLabel label, BufferedImage original, double zoom) {
	    int w = Math.max(1, (int) (original.getWidth() * zoom));
	    int h = Math.max(1, (int) (original.getHeight() * zoom));
	    BufferedImage scaled = scaleImage(original, w, h);
	    label.setIcon(new ImageIcon(scaled));
	}

	/**
	 * Reads an image, applying source subsampling if it is larger than maxDim
	 * on either side. Never loads the full-resolution image into memory.
	 */
	private BufferedImage readImageScaled(File file, int maxDim) throws IOException {
	    try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
	        if (input == null) {
	            return null;
	        }

	        Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
	        if (!readers.hasNext()) {
	            return null; // unsupported format
	        }

	        ImageReader reader = readers.next();
	        try {
	            reader.setInput(input, true, true); // seekForwardOnly, ignoreMetadata

	            int width  = reader.getWidth(0);
	            int height = reader.getHeight(0);

	            // Calculate subsampling factor
	            int subsample = 1;
	            if (width > maxDim || height > maxDim) {
	                subsample = (int) Math.ceil(Math.max(
	                        (double) width / maxDim,
	                        (double) height / maxDim));
	            }

	            ImageReadParam param = reader.getDefaultReadParam();
	            param.setSourceSubsampling(subsample, subsample, 0, 0);

	            BufferedImage image = reader.read(0, param);

	            // Optional: if the subsampled result is still a bit large, do a final high-quality scale
	            if (image.getWidth() > maxDim || image.getHeight() > maxDim) {
	                double scale = Math.min((double) maxDim / image.getWidth(),
	                                         (double) maxDim / image.getHeight());
	                int newW = Math.max(1, (int) (image.getWidth() * scale));
	                int newH = Math.max(1, (int) (image.getHeight() * scale));
	                BufferedImage scaled = scaleImage(image, newW, newH);
	                image.flush();
	                image = scaled;
	            }

	            return image;
	        } finally {
	            reader.dispose();
	        }
	    }
	}

	private BufferedImage scaleImage(BufferedImage src, int w, int h) {
	    BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = scaled.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2.drawImage(src, 0, 0, w, h, null);
	    g2.dispose();
	    return scaled;
	}
	
	private void openAnimatedGif(File gifFile) {
	    ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
	    if (gifIcon.getIconWidth() <= 0) {
	        JOptionPane.showMessageDialog(gui.window, "Could not load GIF: " + gifFile.getName());
	        return;
	    }

	    final double[] zoom = {1.0};
	    final int origW = gifIcon.getIconWidth();
	    final int origH = gifIcon.getIconHeight();

	    JLabel gifLabel = new JLabel(gifIcon);
	    gifLabel.setHorizontalAlignment(JLabel.CENTER);
	    gifLabel.setVerticalAlignment(JLabel.CENTER);

	    // Wrapper that we resize for zooming
	    JPanel wrapper = new JPanel(new BorderLayout());
	    wrapper.setBackground(new Color(40, 40, 45));
	    wrapper.add(gifLabel, BorderLayout.CENTER);

	    JScrollPane scroll = new JScrollPane(wrapper);
	    scroll.setBorder(null);
	    scroll.getViewport().setBackground(new Color(40, 40, 45));

	    // Controls
	    JButton zoomOutBtn = new JButton("−");
	    JButton zoomInBtn  = new JButton("+");
	    JButton resetBtn   = new JButton("100%");
	    JButton fitBtn     = new JButton("Fit");
	    JLabel zoomLabel = new JLabel("100%", SwingConstants.CENTER);
	    zoomLabel.setPreferredSize(new Dimension(60, 25));

	    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
	    controlPanel.setBackground(new Color(50, 50, 55));
	    controlPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 70, 75)));
	    controlPanel.add(zoomOutBtn);
	    controlPanel.add(zoomLabel);
	    controlPanel.add(zoomInBtn);
	    controlPanel.add(Box.createHorizontalStrut(12));
	    controlPanel.add(resetBtn);
	    controlPanel.add(fitBtn);

	    JFrame gifFrame = new JFrame(gui.appName + " - " + gifFile.getName());
	    gifFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    gifFrame.setLayout(new BorderLayout());
	    gifFrame.add(scroll, BorderLayout.CENTER);
	    gifFrame.add(controlPanel, BorderLayout.SOUTH);
	    gifFrame.setLocationRelativeTo(gui.window);
	    gifFrame.setMinimumSize(new Dimension(400, 300));

	    int frameW = Math.min(origW + 40, 1000);
	    int frameH = Math.min(origH + 80, 700);
	    gifFrame.setSize(frameW, frameH);

	    URL iconUrl = getClass().getResource("/icon.png");
	    if (iconUrl != null) {
	        gifFrame.setIconImage(new ImageIcon(iconUrl).getImage());
	    }

	    Runnable updateZoom = () -> {
	        int w = Math.max(1, (int) (origW * zoom[0]));
	        int h = Math.max(1, (int) (origH * zoom[0]));
	        gifLabel.setPreferredSize(new Dimension(w, h));
	        gifLabel.setSize(w, h);
	        wrapper.setPreferredSize(new Dimension(w, h));
	        zoomLabel.setText(String.format("%.0f%%", zoom[0] * 100));
	        wrapper.revalidate();
	        scroll.revalidate();
	        gifLabel.repaint();
	    };

	    zoomInBtn.addActionListener(e -> {
	        zoom[0] = Math.min(zoom[0] * 1.25, 8.0);
	        updateZoom.run();
	    });
	    zoomOutBtn.addActionListener(e -> {
	        zoom[0] = Math.max(zoom[0] / 1.25, 0.1);
	        updateZoom.run();
	    });
	    resetBtn.addActionListener(e -> {
	        zoom[0] = 1.0;
	        updateZoom.run();
	    });

	    Runnable fitToWindow = () -> {
	        Dimension view = scroll.getViewport().getSize();
	        if (view.width > 20 && view.height > 20) {
	            double sx = (double) view.width / origW;
	            double sy = (double) view.height / origH;
	            zoom[0] = Math.min(sx, sy) * 0.98;
	            updateZoom.run();
	        }
	    };
	    fitBtn.addActionListener(e -> fitToWindow.run());

	    gifLabel.addMouseWheelListener(e -> {
	        if (!e.isControlDown()) return;
	        zoom[0] = e.getWheelRotation() < 0
	                ? Math.min(zoom[0] * 1.15, 8.0)
	                : Math.max(zoom[0] / 1.15, 0.1);
	        updateZoom.run();
	        e.consume();
	    });

	    // Initial fit after the frame is shown
	    SwingUtilities.invokeLater(fitToWindow::run);

	    gifFrame.setVisible(true);
	}
}
