import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	int selectedTheme;
	int customTheme = 0;
	
//	variables for all the users custom theme files
	String CTtextAreaBackground          = "#1A1A2E";
	String CTtextAreaforeground          = "#E0E0E0";
	String CTtextAreaCaretColor          = "#00D4FF";
	String CTtextAreaSelectionColor      = "#3D5A80";
	String CTtextAreaSelectedTextColor   = "#FFFFFF";
	String CTlineNumbersBackground       = "#12121F";
	String CTlineNumbersForeground       = "#6B6B8A";
	String CTcurrentLineNumberForeground = "#00D4FF";
	String CTstatusPanelBackground       = "#12121F";
	String CTcolRowLabel                = "#A0A0C0";
	String CTdocTypeLabel                = "#A0A0C0";
	String CTscrollbarTrack              = "#12121F";
	String CTscrollbarThumb              = "#00D4FF";
	String CTfileTreeNonSelectionColor   = "#00000000";
	String CTfileTreeSelectionColor      = "#3D5A80B4";
	String CTfileTreeTextNonSelectionColor = "#C0C0E0";
	String CTfileTreeTextSelectionColor  = "#FFFFFF";
	String CTfileTreeTitleBackground     = "#12121F";
	String CTfileTreeTitleForeground     = "#00D4FF";
	String CTfileTreeDividerBackground   = "#00D4FF";
	String CTfileTreeBackground   = "#00D4FF";
	String CTfileTreeForeground   = "#00D4FF";
	String CTminiMapBackground           = "#1A1A2E";
	String CTminiMapViewport             = "#00D4FF50";
	String CTminiMapViewportBorder       = "#00D4FF";
	
	public Function_Preferences(GUI gui) {
		
		this.gui = gui;
		
	}

	public void setThemeAlabaster() {
		
		gui.textArea.setBackground(parseColor("#F8F6F1"));
		gui.textArea.setForeground(parseColor("#2C2C2A"));
		gui.textArea.setCaretColor(parseColor("#C17B5C"));
		gui.textArea.setSelectionColor(parseColor("#C17B5C"));
		gui.textArea.setSelectedTextColor(parseColor("#ffffff"));

		gui.lineNumbers.setBackground(parseColor("#A8B0A5"));
		gui.lineNumbers.setLineNumberColor(parseColor("#7A6B8A"));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor("#C17B5C"));
		
		gui.statusPanel.setBackground(parseColor("#A8B0A5"));
		gui.colRowLabel.setForeground(parseColor("#7A6B8A"));
		gui.docTypeLabel.setForeground(parseColor("#7A6B8A"));
		
		Color alabasterTrack = parseColor("#A8B0A5");
		Color alabasterThumb = parseColor("#C17B5C");
		updateScrollbars(gui.scrollPane, alabasterTrack, alabasterThumb);
		updateScrollbars(gui.fileTree.scroll, alabasterTrack, alabasterThumb);

		gui.fileTree.title.setBackground(parseColor("#A8B0A5"));
		gui.fileTree.title.setForeground(parseColor("#7A6B8A"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#7A6B8A"));
        gui.fileTree.renderer.setTextSelectionColor(parseColor("#C17B5C"));
        
        gui.fileTree.tree.setForeground(parseColor("#7A6B8A"));
        gui.fileTree.tree.setBackground(parseColor("#A8B0A5"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor("#7A6B8A"));
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
		customTheme = 0;
		gui.cfg.saveConfig();

		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);
		
	}
	
	public void setThemeCinderandAsh() {;
		
		gui.textArea.setBackground(parseColor("#0F0F0F"));
		gui.textArea.setForeground(parseColor("#E8E4E0"));
		gui.textArea.setCaretColor(parseColor("#C97B63"));
		gui.textArea.setSelectionColor(parseColor("#C97B63"));
		gui.textArea.setSelectedTextColor(parseColor("#ffffff"));

		gui.lineNumbers.setBackground(parseColor("#1A1A1A"));
		gui.lineNumbers.setLineNumberColor(parseColor("#4A4A4A"));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor("#C97B63"));
		
		gui.statusPanel.setBackground(parseColor("#1A1A1A"));
		gui.colRowLabel.setForeground(parseColor("#E8E4E0"));
		gui.docTypeLabel.setForeground(parseColor("#E8E4E0"));
		
		Color cinderTrack = parseColor("#1A1A1A");
		Color cinderThumb = parseColor("#C97B63");
		updateScrollbars(gui.scrollPane, cinderTrack, cinderThumb);
		updateScrollbars(gui.fileTree.scroll, cinderTrack, cinderThumb);

		gui.fileTree.title.setBackground(parseColor("#1A1A1A"));
		gui.fileTree.title.setForeground(parseColor("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(parseColor("#C97B63"));
        
        gui.fileTree.tree.setForeground(parseColor("#E8E4E0"));
        gui.fileTree.tree.setBackground(parseColor("#1A1A1A"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor("#E8E4E0"));
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
		customTheme = 0;
		gui.cfg.saveConfig();

		gui.iTheme1.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);
	}
	
	public void setThemeVaporwave77() {
		gui.textArea.setBackground(parseColor("#0D0221"));
		gui.textArea.setForeground(parseColor("#F0E6FF"));
		gui.textArea.setCaretColor(parseColor("#B967FF"));
		gui.textArea.setSelectionColor(parseColor("#01CDFE"));
		gui.textArea.setSelectedTextColor(parseColor("#ffffff"));

		gui.lineNumbers.setBackground(parseColor("#1E0B3A"));
		gui.lineNumbers.setLineNumberColor(parseColor("#01CDFE"));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor("#B967FF"));
		
		gui.statusPanel.setBackground(parseColor("#1E0B3A"));
		gui.colRowLabel.setForeground(parseColor("#01CDFE"));
		gui.docTypeLabel.setForeground(parseColor("#01CDFE"));
		
		Color vaporTrack = parseColor("#1E0B3A");
		Color vaporThumb = parseColor("#B967FF");
		updateScrollbars(gui.scrollPane, vaporTrack, vaporThumb);
		updateScrollbars(gui.fileTree.scroll, vaporTrack, vaporThumb);

		gui.fileTree.title.setBackground(parseColor("#1E0B3A"));
		gui.fileTree.title.setForeground(parseColor("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(parseColor("#01CDFE"));
        
        gui.fileTree.tree.setForeground(parseColor("#E8E4E0"));
        gui.fileTree.tree.setBackground(parseColor("#1E0B3A"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor("#E8E4E0"));
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
		customTheme = 0;
		gui.cfg.saveConfig();

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);
	}
	
	public void setThemeMatchaLatte() {
		gui.textArea.setBackground(parseColor("#E0DBD0"));
		gui.textArea.setForeground(parseColor("#2A3428"));
		gui.textArea.setCaretColor(parseColor("#6B8F4E"));
		gui.textArea.setSelectionColor(parseColor("#B8924A"));
		gui.textArea.setSelectedTextColor(parseColor("#ffffff"));

		gui.lineNumbers.setBackground(parseColor("#6B8F4E"));
		gui.lineNumbers.setLineNumberColor(parseColor("#3d4735"));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor("#E5E0D4"));
		
		gui.statusPanel.setBackground(parseColor("#6B8F4E"));
		gui.colRowLabel.setForeground(parseColor("#E5E0D4"));
		gui.docTypeLabel.setForeground(parseColor("#E5E0D4"));
		
		Color matchaTrack = parseColor("#3d4735");
		Color matchaThumb = parseColor("#6B8F4E");
		updateScrollbars(gui.scrollPane, matchaTrack, matchaThumb);
		updateScrollbars(gui.fileTree.scroll, matchaTrack, matchaThumb);

		gui.fileTree.title.setBackground(parseColor("#6B8F4E"));
		gui.fileTree.title.setForeground(parseColor("#E8E4E0"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#E8E4E0"));
        gui.fileTree.renderer.setTextSelectionColor(parseColor("#3d4735"));
        
        gui.fileTree.tree.setForeground(parseColor("#E8E4E0"));
        gui.fileTree.tree.setBackground(parseColor("#6B8F4E"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor("#E8E4E0"));
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
		customTheme = 0;
		gui.cfg.saveConfig();

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);
	}
	
	public void setThemeCremeBrulee() {

		gui.textArea.setBackground(parseColor("#F8F1E3"));
		gui.textArea.setForeground(parseColor("#3A2F22"));
		gui.textArea.setCaretColor(parseColor("#C48A3A"));
		gui.textArea.setSelectionColor(parseColor("#EBD9B0"));
		gui.textArea.setSelectedTextColor(parseColor("#ffffff"));

		gui.lineNumbers.setBackground(parseColor("#F5E9C8"));
		gui.lineNumbers.setLineNumberColor(parseColor("#A89B82"));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor("#C48A3A"));
		
		gui.statusPanel.setBackground(parseColor("#F5E9C8"));
		gui.colRowLabel.setForeground(parseColor("#A89B82"));
		gui.docTypeLabel.setForeground(parseColor("#A89B82"));
		
		Color matchaTrack = parseColor("#3d4735");
		Color matchaThumb = parseColor("#C48A3A");
		updateScrollbars(gui.scrollPane, matchaTrack, matchaThumb);
		updateScrollbars(gui.fileTree.scroll, matchaTrack, matchaThumb);

		gui.fileTree.title.setBackground(parseColor("#F5E9C8"));
		gui.fileTree.title.setForeground(parseColor("#7A6B8A"));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45, 0));
        gui.fileTree.renderer.setBackgroundSelectionColor(new Color(70, 90, 120, 0));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#7A6B8A"));
        gui.fileTree.renderer.setTextSelectionColor(parseColor("#C48A3A"));
        
        gui.fileTree.tree.setForeground(parseColor("#7A6B8A"));
        gui.fileTree.tree.setBackground(parseColor("#F5E9C8"));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor("#7A6B8A"));
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
		customTheme = 0;
		gui.cfg.saveConfig();

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iThemeCustom.setSelected(false);
	}
	
	public void setCustomTheme() {

		gui.textArea.setBackground(parseColor(CTtextAreaBackground));
		gui.textArea.setForeground(parseColor(CTtextAreaforeground));
		gui.textArea.setCaretColor(parseColor(CTtextAreaCaretColor));
		gui.textArea.setSelectionColor(parseColor(CTtextAreaSelectionColor));
		gui.textArea.setSelectedTextColor(parseColor(CTtextAreaSelectedTextColor));

		gui.lineNumbers.setBackground(parseColor(CTlineNumbersBackground));
		gui.lineNumbers.setLineNumberColor(parseColor(CTlineNumbersForeground));
		gui.lineNumbers.setCurrentLineNumberColor(parseColor(CTcurrentLineNumberForeground));
		
		gui.statusPanel.setBackground(parseColor(CTstatusPanelBackground));
		gui.colRowLabel.setForeground(parseColor(CTcolRowLabel));
		gui.docTypeLabel.setForeground(parseColor(CTdocTypeLabel));
		
		Color matchaTrack = parseColor(CTscrollbarTrack);
		Color matchaThumb = parseColor(CTscrollbarThumb);
		updateScrollbars(gui.scrollPane, matchaTrack, matchaThumb);
		updateScrollbars(gui.fileTree.scroll, matchaTrack, matchaThumb);

		gui.fileTree.title.setBackground(parseColor(CTfileTreeTitleBackground));
		gui.fileTree.title.setForeground(parseColor(CTfileTreeTitleForeground));
		
        gui.fileTree.renderer.setBackgroundNonSelectionColor(parseColor(CTfileTreeNonSelectionColor));
        gui.fileTree.renderer.setBackgroundSelectionColor(parseColor(CTfileTreeSelectionColor));
        gui.fileTree.renderer.setTextNonSelectionColor(parseColor(CTfileTreeTextNonSelectionColor));
        gui.fileTree.renderer.setTextSelectionColor(parseColor(CTfileTreeTextSelectionColor));
        
        gui.fileTree.tree.setForeground(parseColor(CTfileTreeForeground));
        gui.fileTree.tree.setBackground(parseColor(CTfileTreeBackground));
        
        gui.splitPane.setUI(new BasicSplitPaneUI() {
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() {
		        return new BasicSplitPaneDivider(this) {
		            @Override
		            public void paint(Graphics g) {
		                g.setColor(parseColor(CTfileTreeDividerBackground));
		                g.fillRect(0, 0, getWidth(), getHeight());
		            }
		        };
		    }
		});
		
		if (gui.minimap != null) {
			gui.minimap.setBackground(parseColor(CTminiMapBackground));
			gui.minimap.setViewportColors(
					parseColor(CTminiMapViewport),
		            parseColor(CTminiMapViewportBorder)
		        );
			gui.minimap.repaint();
		}

		selectedTheme = 0;
		customTheme = 1;
		gui.cfg.saveConfig();

		gui.iTheme1.setSelected(false);
		gui.iTheme2.setSelected(false);
		gui.iTheme3.setSelected(false);
		gui.iTheme4.setSelected(false);
		gui.iTheme5.setSelected(false);
		
		gui.iThemeCustom.setSelected(true); // Re-apply the current font so it doesn't reset
	    Font myFont = new Font(font, Font.PLAIN, fontSize);
	    gui.textArea.setFont(myFont);
	    if (gui.lineNumbers != null) {
	        gui.lineNumbers.setLineNumberFont(myFont);
	    }
	}
	
	public void setThemeFallout() {
	    // Classic Pip-Boy / Fallout terminal green
	    gui.textArea.setBackground(parseColor("#0A0F0A"));
	    gui.textArea.setForeground(parseColor("#39FF14"));
	    gui.textArea.setCaretColor(parseColor("#39FF14"));
	    gui.textArea.setSelectionColor(parseColor("#FFB000"));
	    gui.textArea.setSelectedTextColor(parseColor("#FFB000"));

	    gui.lineNumbers.setBackground(parseColor("#050805"));
	    gui.lineNumbers.setLineNumberColor(parseColor("#1E7A1E"));
	    gui.lineNumbers.setCurrentLineNumberColor(parseColor("#FFB000"));

	    gui.statusPanel.setBackground(parseColor("#050805"));
	    gui.colRowLabel.setForeground(parseColor("#FFB000"));
	    gui.docTypeLabel.setForeground(parseColor("#FFB000"));

	    Color track = parseColor("#050805");
	    Color thumb = parseColor("#39FF14");
	    updateScrollbars(gui.scrollPane, track, thumb);
	    updateScrollbars(gui.fileTree.scroll, track, thumb);

	    gui.fileTree.title.setBackground(parseColor("#050805"));
	    gui.fileTree.title.setForeground(parseColor("#39FF14"));

	    gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(0, 0, 0, 0));
	    gui.fileTree.renderer.setBackgroundSelectionColor(new Color(30, 80, 30, 180));
	    gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#39FF14"));
	    gui.fileTree.renderer.setTextSelectionColor(parseColor("#FFB000"));

	    gui.fileTree.tree.setForeground(parseColor("#39FF14"));
	    gui.fileTree.tree.setBackground(parseColor("#050805"));

	    gui.splitPane.setUI(new BasicSplitPaneUI() {
	        @Override
	        public BasicSplitPaneDivider createDefaultDivider() {
	            return new BasicSplitPaneDivider(this) {
	                @Override
	                public void paint(Graphics g) {
	                    g.setColor(parseColor("#FFB000"));
	                    g.fillRect(0, 0, getWidth(), getHeight());
	                }
	            };
	        }
	    });

	    if (gui.minimap != null) {
	        gui.minimap.setBackground(gui.textArea.getBackground());
	        gui.minimap.setViewportColors(
	            new Color(255, 176, 0, 60),
	            new Color(255, 176, 0)
	        );
	        gui.minimap.repaint();
	    }

	    // Don't select any of the normal theme checkboxes
	    gui.iTheme1.setSelected(false);
	    gui.iTheme2.setSelected(false);
	    gui.iTheme3.setSelected(false);
	    gui.iTheme4.setSelected(false);
	    gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);

	    selectedTheme = 67;   // hidden theme id
		customTheme = 0;
	    
	    setFalloutBackground();
		gui.cfg.saveConfig();
	}
	
	public void setThemeDoom() {
	    // DOOM – dark red / black with blood-orange accents
	    gui.textArea.setBackground(parseColor("#0C0000"));
	    gui.textArea.setForeground(parseColor("#FF2200"));
	    gui.textArea.setCaretColor(parseColor("#FF5500"));
	    gui.textArea.setSelectionColor(parseColor("#8B0000"));
	    gui.textArea.setSelectedTextColor(parseColor("#FFAA00"));

	    gui.lineNumbers.setBackground(parseColor("#1A0000"));
	    gui.lineNumbers.setLineNumberColor(parseColor("#660000"));
	    gui.lineNumbers.setCurrentLineNumberColor(parseColor("#FF5500"));

	    gui.statusPanel.setBackground(parseColor("#1A0000"));
	    gui.colRowLabel.setForeground(parseColor("#FF5500"));
	    gui.docTypeLabel.setForeground(parseColor("#FF5500"));

	    Color track = parseColor("#1A0000");
	    Color thumb = parseColor("#FF2200");
	    updateScrollbars(gui.scrollPane, track, thumb);
	    updateScrollbars(gui.fileTree.scroll, track, thumb);

	    gui.fileTree.title.setBackground(parseColor("#1A0000"));
	    gui.fileTree.title.setForeground(parseColor("#FF2200"));

	    gui.fileTree.renderer.setBackgroundNonSelectionColor(new Color(0, 0, 0, 0));
	    gui.fileTree.renderer.setBackgroundSelectionColor(new Color(120, 0, 0, 180));
	    gui.fileTree.renderer.setTextNonSelectionColor(parseColor("#FF2200"));
	    gui.fileTree.renderer.setTextSelectionColor(parseColor("#FFAA00"));

	    gui.fileTree.tree.setForeground(parseColor("#FF2200"));
	    gui.fileTree.tree.setBackground(parseColor("#0C0000"));

	    gui.splitPane.setUI(new BasicSplitPaneUI() {
	        @Override
	        public BasicSplitPaneDivider createDefaultDivider() {
	            return new BasicSplitPaneDivider(this) {
	                @Override
	                public void paint(Graphics g) {
	                    g.setColor(parseColor("#FF2200"));
	                    g.fillRect(0, 0, getWidth(), getHeight());
	                }
	            };
	        }
	    });

	    if (gui.minimap != null) {
	        gui.minimap.setBackground(gui.textArea.getBackground());
	        gui.minimap.setViewportColors(
	            new Color(255, 34, 0, 70),
	            new Color(255, 85, 0)
	        );
	        gui.minimap.repaint();
	    }

	    // Clear normal theme checkboxes
	    gui.iTheme1.setSelected(false);
	    gui.iTheme2.setSelected(false);
	    gui.iTheme3.setSelected(false);
	    gui.iTheme4.setSelected(false);
	    gui.iTheme5.setSelected(false);
		gui.iThemeCustom.setSelected(false);

	    selectedTheme = 666;   // hidden DOOM theme id
		customTheme = 0;
	    
	    setDoomWallpaper();
		gui.cfg.saveConfig();
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
                gui.cfg.saveConfig();
            }
        });
	}
	
	public void fontLarger() {
		if(fontSize >= 72) {
			return;
		}
		fontSize = fontSize + 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setLineNumberFont(myFont);
		gui.cfg.saveConfig();
	}
	
	public void fontSmaller() {
		if(fontSize <= 8) {
			return;
		}
		fontSize = fontSize - 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setLineNumberFont(myFont);
		gui.cfg.saveConfig();
	}
	
	public void fontReset() {
		fontSize = defaultFontSize;
	    applyDefaultFont();   // re-check JetBrains Mono
		gui.cfg.saveConfig();
	}
	
	public void setupCtrlMouseWheelFontSize() {
	    gui.scrollPane.addMouseWheelListener(e -> {
	        if (e.isControlDown()) {
	            e.consume();   // only block scrolling when Ctrl is pressed

	            if (e.getWheelRotation() < 0) {
	                fontLarger();
	            } else {
	                fontSmaller();
	            }
	        } else {
	        	return;
	        }
	        // when Ctrl is NOT held, do nothing → normal scrolling still works
	    });
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
	
	public void applyDefaultFont() {
		// Only auto-detect JetBrains Mono if the user hasn't picked something else
	    if (font == null || font.equals("SansSerif") || font.equals("JetBrains Mono")) {
	        String preferred = "JetBrains Mono";
	        String fallback = "SansSerif";
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        boolean found = false;
	        for (String name : ge.getAvailableFontFamilyNames()) {
	            if (name.equalsIgnoreCase(preferred)) {
	                found = true;
	                break;
	            }
	        }
	        font = found ? preferred : fallback;
	    }

	    Font myFont = new Font(font, Font.PLAIN, fontSize);
	    gui.textArea.setFont(myFont);
	    if (gui.lineNumbers != null) {
	        gui.lineNumbers.setLineNumberFont(myFont);
	    }
	}
	
	private void setFalloutBackground() {
	    // Try to load the image – change the name if needed
	    java.net.URL url = getClass().getClassLoader().getResource("fallout76.png");
	    if (url == null) {
	        url = getClass().getClassLoader().getResource("fallout76.png"); // fallback
	    }
	    if (url == null) {
	        System.err.println("Fallout logo not found on classpath");
	        return;
	    }
	    
	    ImageIcon logoIcon = new ImageIcon(url);
	    Image logo = logoIcon.getImage();

	    // Make text area transparent
	    gui.textArea.setOpaque(false);
	    gui.textArea.setBackground(new Color(0, 0, 0, 0));
	    gui.scrollPane.setOpaque(false);
	    gui.scrollPane.getViewport().setOpaque(false);

	    JPanel bgPanel = new JPanel(new BorderLayout()) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();

	            // Solid dark background
	            g2.setColor(parseColor("#0A0F0A"));
	            g2.fillRect(0, 0, getWidth(), getHeight());

	            // Draw logo
	            if (logo != null) {
	                int lw = logo.getWidth(this);
	                int lh = logo.getHeight(this);
	                if (lw > 0 && lh > 0) {
	                    double scale = Math.min(
	                        (getWidth() * 0.55) / lw,
	                        (getHeight() * 0.55) / lh
	                    );
	                    int w = (int) (lw * scale);
	                    int h = (int) (lh * scale);
	                    int x = (getWidth() - w) / 2;
	                    int y = (getHeight() - h) / 2;

	                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.22f));
	                    g2.drawImage(logo, x, y, w, h, this);
	                }
	            }
	            g2.dispose();
	        }
	    };
	    bgPanel.setBackground(parseColor("#0A0F0A"));
	    bgPanel.add(gui.scrollPane, BorderLayout.CENTER);

	    // Swap into the split pane
	    java.awt.Component right = gui.splitPane.getRightComponent();
	    if (right instanceof JPanel editorRight) {
	        editorRight.removeAll();
	        editorRight.add(bgPanel, BorderLayout.CENTER);
	        if (gui.minimap != null) {
	            editorRight.add(gui.minimap, BorderLayout.EAST);
	        }
	        editorRight.revalidate();
	        editorRight.repaint();
	    }
	}
	
	private void setDoomWallpaper() {
	    java.net.URL url = getClass().getClassLoader().getResource("doom.png");
	    if (url == null) {
	        System.err.println("DOOM wallpaper not found");
	        return;
	    }

	    Image wallpaper = new ImageIcon(url).getImage();

	    // Make the text area and scroll pane transparent
	    gui.textArea.setOpaque(false);
	    gui.textArea.setBackground(new Color(0, 0, 0, 0));
	    gui.scrollPane.setOpaque(false);
	    gui.scrollPane.getViewport().setOpaque(false);
	    gui.scrollPane.setBorder(null);

	    JPanel bgPanel = new JPanel(new BorderLayout()) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	            // Draw wallpaper stretched to fill the entire panel
	            if (wallpaper != null) {
	                g2.drawImage(wallpaper, 0, 0, getWidth(), getHeight(), this);
	            } else {
	                g2.setColor(parseColor("#0C0000"));
	                g2.fillRect(0, 0, getWidth(), getHeight());
	            }

	            // Optional dark overlay so text stays readable
	            g2.setColor(new Color(0, 0, 0, 100));
	            g2.fillRect(0, 0, getWidth(), getHeight());

	            g2.dispose();
	        }
	    };

	    bgPanel.add(gui.scrollPane, BorderLayout.CENTER);

	    java.awt.Component right = gui.splitPane.getRightComponent();
	    if (right instanceof JPanel editorRight) {
	        editorRight.removeAll();
	        editorRight.add(bgPanel, BorderLayout.CENTER);
	        if (gui.minimap != null) {
	            editorRight.add(gui.minimap, BorderLayout.EAST);
	        }
	        editorRight.revalidate();
	        editorRight.repaint();
	    }
	}
	
	public void openConfig() {
		String home = System.getProperty("user.home");
	    File notesFile = new File(home, "PHNX.txt");

	    if (!notesFile.exists()) {
	        // Create it first if missing
	        gui.cfg.loadHomeNotesFile();
	    }

	    gui.openFileFromDrop(notesFile);
	}
	
	public static Color parseColor(String hex) {
	    if (hex == null) return Color.BLACK;
	    hex = hex.trim();
	    if (hex.startsWith("#")) hex = hex.substring(1);

	    try {
	        if (hex.length() == 3) {
	            hex = "" + hex.charAt(0) + hex.charAt(0)
	                     + hex.charAt(1) + hex.charAt(1)
	                     + hex.charAt(2) + hex.charAt(2);
	        }
	        if (hex.length() == 6) {
	            return new Color(Integer.parseInt(hex, 16));
	        }
	        if (hex.length() == 8) {
	            // #RRGGBBAA  (alpha last)
	            int r = Integer.parseInt(hex.substring(0, 2), 16);
	            int g = Integer.parseInt(hex.substring(2, 4), 16);
	            int b = Integer.parseInt(hex.substring(4, 6), 16);
	            int a = Integer.parseInt(hex.substring(6, 8), 16);
	            return new Color(r, g, b, a);
	        }
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid color: " + hex);
	    }
	    return Color.BLACK;
	}

}
