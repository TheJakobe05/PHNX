import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class Function_Preferences {
	
	GUI gui;
	String font = "SansSerif";
	
	int defaultFontSize = 16;
	int fontSize = defaultFontSize;
	int defaultTheme = 2;
	int selectedTheme = defaultTheme;
	
	public Function_Preferences(GUI gui) {
		
		this.gui = gui;
		
	}

	public void setThemeAlabaster() {
		gui.textArea.setBackground(Color.decode("#F8F6F1"));
		gui.textArea.setForeground(Color.decode("#2C2C2A"));
		gui.textArea.setCaretColor(Color.decode("#C17B5C"));
		gui.textArea.setSelectionColor(Color.decode("#C17B5C"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#A8B0A5"));
		gui.lineNumbers.setLineNumberColor(Color.decode("#7A6B8A"));
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#C17B5C"));
		
		gui.statusPanel.setBackground(Color.decode("#A8B0A5"));
		gui.colRowLabel.setForeground(Color.decode("#7A6B8A"));
		gui.docTypeLabel.setForeground(Color.decode("#7A6B8A"));
		
		Color alabasterTrack = Color.decode("#A8B0A5");
		Color alabasterThumb = Color.decode("#C17B5C");
		updateScrollbars(gui.scrollPane, alabasterTrack, alabasterThumb);
		updateScrollbars(gui.fileTree.scroll, alabasterTrack, alabasterThumb);

		gui.fileTree.title.setBackground(Color.decode("#A8B0A5"));
		gui.fileTree.title.setForeground(Color.decode("#7A6B8A"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(Color.decode("#7A6B8A"));
        gui.fileTree.renderer.setTextSelectionColor(Color.decode("#C17B5C"));
        
        gui.fileTree.tree.setForeground(Color.decode("#7A6B8A"));
        gui.fileTree.tree.setBackground(Color.decode("#A8B0A5"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(Color.decode("#7A6B8A"));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(gui.textArea.getBackground());
			gui.minimap.setViewportColors(
		            new Color(193, 123, 92, 80),
		            new Color(193, 123, 92)
		        );
			gui.minimap.repaint();
		}

		gui.iTheme1.setSelected(true);
		selectedTheme = 1;

		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
		
	}
	public void setThemeCinderandAsh() {
		gui.textArea.setBackground(Color.decode("#0F0F0F"));
		gui.textArea.setForeground(Color.decode("#E8E4E0"));
		gui.textArea.setCaretColor(Color.decode("#C97B63"));
		gui.textArea.setSelectionColor(Color.decode("#C97B63"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#1A1A1A"));
		gui.lineNumbers.setLineNumberColor(Color.decode("#4A4A4A"));
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#C97B63"));
		
		gui.statusPanel.setBackground(Color.decode("#1A1A1A"));
		gui.colRowLabel.setForeground(Color.decode("#E8E4E0"));
		gui.docTypeLabel.setForeground(Color.decode("#E8E4E0"));
		
		Color cinderTrack = Color.decode("#1A1A1A");
		Color cinderThumb = Color.decode("#C97B63");
		updateScrollbars(gui.scrollPane, cinderTrack, cinderThumb);
		updateScrollbars(gui.fileTree.scroll, cinderTrack, cinderThumb);

		gui.fileTree.title.setBackground(Color.decode("#1A1A1A"));
		gui.fileTree.title.setForeground(Color.decode("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(Color.decode("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(Color.decode("#C97B63"));
        
        gui.fileTree.tree.setForeground(Color.decode("#E8E4E0"));
        gui.fileTree.tree.setBackground(Color.decode("#1A1A1A"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(Color.decode("#E8E4E0"));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(gui.textArea.getBackground());
			gui.minimap.setViewportColors(
		            new Color(201, 123, 99, 80),
		            new Color(201, 123, 99)
		        );
			gui.minimap.repaint();
		}

		gui.iTheme2.setSelected(true);
		selectedTheme = 2;

		gui.iTheme1.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
	}
	
	public void setThemeVaporwave77() {
		gui.textArea.setBackground(Color.decode("#0D0221"));
		gui.textArea.setForeground(Color.decode("#F0E6FF"));
		gui.textArea.setCaretColor(Color.decode("#B967FF"));
		gui.textArea.setSelectionColor(Color.decode("#01CDFE"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#1E0B3A"));
		gui.lineNumbers.setLineNumberColor(Color.decode("#01CDFE"));
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#B967FF"));
		
		gui.statusPanel.setBackground(Color.decode("#1E0B3A"));
		gui.colRowLabel.setForeground(Color.decode("#01CDFE"));
		gui.docTypeLabel.setForeground(Color.decode("#01CDFE"));
		
		Color vaporTrack = Color.decode("#1E0B3A");
		Color vaporThumb = Color.decode("#B967FF");
		updateScrollbars(gui.scrollPane, vaporTrack, vaporThumb);
		updateScrollbars(gui.fileTree.scroll, vaporTrack, vaporThumb);

		gui.fileTree.title.setBackground(Color.decode("#1E0B3A"));
		gui.fileTree.title.setForeground(Color.decode("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(Color.decode("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(Color.decode("#01CDFE"));
        
        gui.fileTree.tree.setForeground(Color.decode("#E8E4E0"));
        gui.fileTree.tree.setBackground(Color.decode("#1E0B3A"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(Color.decode("#E8E4E0"));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(gui.textArea.getBackground());
			gui.minimap.setViewportColors(
		            new Color(185, 103, 255, 80),
		            new Color(185, 103, 255)
		        );
			gui.minimap.repaint();
		}

		gui.iTheme3.setSelected(true);
		selectedTheme = 3;

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
	}
	
	public void setThemeMatchaLatte() {
		gui.textArea.setBackground(Color.decode("#E0DBD0"));
		gui.textArea.setForeground(Color.decode("#2A3428"));
		gui.textArea.setCaretColor(Color.decode("#6B8F4E"));
		gui.textArea.setSelectionColor(Color.decode("#B8924A"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#6B8F4E"));
		gui.lineNumbers.setLineNumberColor(Color.decode("#3d4735"));
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#E5E0D4"));
		
		gui.statusPanel.setBackground(Color.decode("#6B8F4E"));
		gui.colRowLabel.setForeground(Color.decode("#E5E0D4"));
		gui.docTypeLabel.setForeground(Color.decode("#E5E0D4"));
		
		Color matchaTrack = Color.decode("#3d4735");
		Color matchaThumb = Color.decode("#6B8F4E");
		updateScrollbars(gui.scrollPane, matchaTrack, matchaThumb);
		updateScrollbars(gui.fileTree.scroll, matchaTrack, matchaThumb);

		gui.fileTree.title.setBackground(Color.decode("#6B8F4E"));
		gui.fileTree.title.setForeground(Color.decode("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(Color.decode("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(Color.decode("#3d4735"));
        
        gui.fileTree.tree.setForeground(Color.decode("#E8E4E0"));
        gui.fileTree.tree.setBackground(Color.decode("#6B8F4E"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(Color.decode("#E8E4E0"));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(gui.textArea.getBackground());
			gui.minimap.setViewportColors(
		            new Color(107, 143, 78, 80),
		            new Color(107, 143, 78)
		        );
			gui.minimap.repaint();
		}

		gui.iTheme4.setSelected(true);
		selectedTheme = 4;

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme5.setSelected(false);
	}
	
	public void setThemeCremeBrulee() {

		gui.textArea.setBackground(Color.decode("#F8F1E3"));
		gui.textArea.setForeground(Color.decode("#3A2F22"));
		gui.textArea.setCaretColor(Color.decode("#C48A3A"));
		gui.textArea.setSelectionColor(Color.decode("#EBD9B0"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#F5E9C8"));
		gui.lineNumbers.setLineNumberColor(Color.decode("#A89B82"));
		gui.lineNumbers.setCurrentLineNumberColor(Color.decode("#C48A3A"));
		
		gui.statusPanel.setBackground(Color.decode("#F5E9C8"));
		gui.colRowLabel.setForeground(Color.decode("#A89B82"));
		gui.docTypeLabel.setForeground(Color.decode("#A89B82"));
		
		Color matchaTrack = Color.decode("#3d4735");
		Color matchaThumb = Color.decode("#C48A3A");
		updateScrollbars(gui.scrollPane, matchaTrack, matchaThumb);
		updateScrollbars(gui.fileTree.scroll, matchaTrack, matchaThumb);

		gui.fileTree.title.setBackground(Color.decode("#F5E9C8"));
		gui.fileTree.title.setForeground(Color.decode("#7A6B8A"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(Color.decode("#7A6B8A"));
        gui.fileTree.renderer.setTextSelectionColor(Color.decode("#C48A3A"));
        
        gui.fileTree.tree.setForeground(Color.decode("#7A6B8A"));
        gui.fileTree.tree.setBackground(Color.decode("#F5E9C8"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(Color.decode("#7A6B8A"));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(gui.textArea.getBackground());
			gui.minimap.setViewportColors(
		            new Color(196, 138, 58, 80),
		            new Color(196, 138, 58)
		        );
			gui.minimap.repaint();
		}
		
		gui.iTheme5.setSelected(true);
		selectedTheme = 5;

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
	}
	
	public void fontChoose() {
		SwingUtilities.invokeLater(() -> {
            // Retrieve all available system font families
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();

            // Create a combo box selector
            JComboBox<String> fontPicker = new JComboBox<>(fontNames);
            
            // Display inside a standard dialog popup
            int result = JOptionPane.showConfirmDialog(
                null, 
                fontPicker, 
                "Select a Font", 
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedFontName = (String) fontPicker.getSelectedItem();
                Font selectedFont = new Font(selectedFontName, fontSize, fontSize);
                font = selectedFontName;
                gui.textArea.setFont(selectedFont);
            }
        });
	}
	
	public void fontLarger() {
		fontSize = fontSize + 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setLineNumberFont(myFont);
	}
	
	public void fontSmaller() {
		fontSize = fontSize - 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setLineNumberFont(myFont);
	}
	
	public void fontReset() {
		fontSize = defaultFontSize;
		font = "SansSerif";
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setLineNumberFont(myFont);
	}
	
	private void updateScrollbars(JScrollPane scrollPane, Color trackColor, Color thumbColor) {
	    // 1. Update Vertical Scrollbar
	    JScrollBar vertical = scrollPane.getVerticalScrollBar();
	    vertical.setPreferredSize(new java.awt.Dimension(8, Integer.MAX_VALUE));
	    if (vertical.getUI() instanceof CustomScrollBar) {
	        ((CustomScrollBar) vertical.getUI()).setColors(trackColor, thumbColor);
	    } else {
	        vertical.setUI(new CustomScrollBar(trackColor, thumbColor));
	    }

	    // 2. Update Horizontal Scrollbar
	    JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
	    horizontal.setPreferredSize(new java.awt.Dimension(Integer.MAX_VALUE, 8));
	    if (horizontal.getUI() instanceof CustomScrollBar) {
	        ((CustomScrollBar) horizontal.getUI()).setColors(trackColor, thumbColor);
	    } else {
	        horizontal.setUI(new CustomScrollBar(trackColor, thumbColor));
	    }
	    
	    // Force a visual updates
	    scrollPane.repaint();
	    scrollPane.revalidate();
	}

}
