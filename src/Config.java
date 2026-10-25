import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

public class Config {

	GUI gui;

	public Config(GUI gui) {

		this.gui = gui;

	}

	public void loadHomeNotesFile() {
		String home = System.getProperty("user.home");
		File notesFile = new File(home, "PHNX/config.txt");

		try {
			if (!notesFile.exists()) {
				gui.pref.selectedTheme = gui.pref.defaultTheme;
				String starter = """
						selectedTheme = 2
						fontSize = 16
						fontFamily = JetBrains Mono
						wordWrap = 0
						miniMap = 1
						statusBar = 1
						""";
				java.nio.file.Files.writeString(notesFile.toPath(), starter);
				System.out.println("Created config file: " + notesFile.getAbsolutePath());
			}

			// Load lines into a map
			java.util.Map<String, String> config = new java.util.HashMap<>();
			java.util.List<String> lines = java.nio.file.Files.readAllLines(notesFile.toPath());

			for (String line : lines) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#"))
					continue;

				String[] parts = line.split("=", 2);
				if (parts.length == 2) {
					String key = parts[0].trim();
					String value = parts[1].trim().replace("\"", ""); // remove quotes if present
					config.put(key, value);
				}
			}

			// Apply the settings
			applyConfig(config);

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(gui.window,
					"Could not load or create config file:\n" + notesFile.getAbsolutePath(), "Config Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void applyConfig(java.util.Map<String, String> config) {
		// Theme
		if (config.containsKey("selectedTheme")) {
			try {
				// Use Long.parseLong to safely handle large UUIDs (e.g., 3409000988)
				int theme = Integer.parseInt(config.get("selectedTheme"));
				gui.pref.selectedTheme = theme;
				
				if (theme == 1) {
					gui.pref.setThemeAlabaster();
				} else if (theme == 2) {
					gui.pref.setThemeCinderandAsh();
				} else if (theme == 3) {
					gui.pref.setThemeVaporwave77();
				} else if (theme == 4) {
					gui.pref.setThemeMatchaLatte();
				} else if (theme == 5) {
					gui.pref.setThemeCremeBrulee();
				} else if (theme == 67) {
					gui.pref.setThemeFallout();
				} else if (theme == 666) {
					gui.pref.setThemeDoom();
				} else if (theme > 1000) {
					System.out.println("Loading installed theme UUID: " + theme);
					boolean found = false;
					
					String userHome = System.getProperty("user.home");
					Path targetDir = Paths.get(userHome, "PHNX", "installed-themes");
					
					if (Files.exists(targetDir) && Files.isDirectory(targetDir)) {
						try (Stream<Path> stream = Files.list(targetDir)) {
							for (Path subPath : (Iterable<Path>) stream::iterator) {
								if (Files.isDirectory(subPath)) {
									String themeName = subPath.getFileName().toString();
									Path installedThemeConfigFilePath = subPath.resolve("config.txt");
									
									if (Files.exists(installedThemeConfigFilePath)) {
										Map<String, String> installedThemeConfig = new HashMap<>();
										List<String> themeLines = Files.readAllLines(installedThemeConfigFilePath);
										
										for (String line : themeLines) {
											line = line.trim();
											if (line.isEmpty() || line.startsWith("#")) {
												continue;
											}

											String[] parts = line.split("=", 2);
											if (parts.length == 2) {
												String key = parts[0].trim();
												String value = parts[1].trim().replace("\"", "");
												installedThemeConfig.put(key, value);
											}
										}
										
										String uuidStr = installedThemeConfig.get("CTthemeUUID");
										if (uuidStr != null) {
											try {
												long folderUuid = Long.parseLong(uuidStr);
												if (folderUuid == theme) {
													gui.pref.applyInstalledTheme(gui, installedThemeConfig, themeName);
													System.out.println("Installed theme [" + themeName + "] applied successfully.");
													
													if (gui.menuThemeInstalled != null) {
												        for (Component comp : gui.menuThemeInstalled.getMenuComponents()) {
												            if (comp instanceof JCheckBoxMenuItem) {
												                JCheckBoxMenuItem item = (JCheckBoxMenuItem) comp;
												                
												                // First uncheck everything in the menu
												                item.setSelected(false);
												                
												                // If the text matches this theme's folder name, select it
												                if (item.getText().equals(themeName)) {
												                    item.setSelected(true);
												                }
												            }
												        }
												    }
													
													found = true;
													break;
												}
											} catch (NumberFormatException ignored) {
											}
										}
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					if (!found) {
						System.out.println("Installed theme with UUID " + theme + " not found. Falling back to default theme.");
						gui.pref.setThemeCinderandAsh();
					}
				} else {
					gui.pref.setThemeCinderandAsh();
				}
			} catch (NumberFormatException ignored) {
				gui.pref.setThemeCinderandAsh();
			}
		}
		if (config.containsKey("CTtextAreaBackground")) {
			gui.pref.CTtextAreaBackground = config.get("CTtextAreaBackground");
		}
		if (config.containsKey("CTtextAreaforeground")) {
			gui.pref.CTtextAreaforeground = config.get("CTtextAreaforeground");
		}
		if (config.containsKey("CTtextAreaCaretColor")) {
			gui.pref.CTtextAreaCaretColor = config.get("CTtextAreaCaretColor");
		}
		if (config.containsKey("CTtextAreaSelectionColor")) {
			gui.pref.CTtextAreaSelectionColor = config.get("CTtextAreaSelectionColor");
		}
		if (config.containsKey("CTtextAreaSelectedTextColor")) {
			gui.pref.CTtextAreaSelectedTextColor = config.get("CTtextAreaSelectedTextColor");
		}
		if (config.containsKey("CTlineNumbersBackground")) {
			gui.pref.CTlineNumbersBackground = config.get("CTlineNumbersBackground");
		}
		if (config.containsKey("CTlineNumbersForeground")) {
			gui.pref.CTlineNumbersForeground = config.get("CTlineNumbersForeground");
		}
		if (config.containsKey("CTcurrentLineNumberForeground")) {
			gui.pref.CTcurrentLineNumberForeground = config.get("CTcurrentLineNumberForeground");
		}
		if (config.containsKey("CTstatusPanelBackground")) {
			gui.pref.CTstatusPanelBackground = config.get("CTstatusPanelBackground");
		}
		if (config.containsKey("CTcolRowLabel")) {
			gui.pref.CTcolRowLabel = config.get("CTcolRowLabel");
		}
		if (config.containsKey("CTdocTypeLabel")) {
			gui.pref.CTdocTypeLabel = config.get("CTdocTypeLabel");
		}
		if (config.containsKey("CTscrollbarTrack")) {
			gui.pref.CTscrollbarTrack = config.get("CTscrollbarTrack");
		}
		if (config.containsKey("CTscrollbarThumb")) {
			gui.pref.CTscrollbarThumb = config.get("CTscrollbarThumb");
		}
		if (config.containsKey("CTfileTreeNonSelectionColor")) {
			gui.pref.CTfileTreeNonSelectionColor = config.get("CTfileTreeNonSelectionColor");
		}
		if (config.containsKey("CTfileTreeSelectionColor")) {
			gui.pref.CTfileTreeSelectionColor = config.get("CTfileTreeSelectionColor");
		}
		if (config.containsKey("CTfileTreeTextNonSelectionColor")) {
			gui.pref.CTfileTreeTextNonSelectionColor = config.get("CTfileTreeTextNonSelectionColor");
		}
		if (config.containsKey("CTfileTreeTextSelectionColor")) {
			gui.pref.CTfileTreeTextSelectionColor = config.get("CTfileTreeTextSelectionColor");
		}
		if (config.containsKey("CTfileTreeTitleBackground")) {
			gui.pref.CTfileTreeTitleBackground = config.get("CTfileTreeTitleBackground");
		}
		if (config.containsKey("CTfileTreeTitleForeground")) {
			gui.pref.CTfileTreeTitleForeground = config.get("CTfileTreeTitleForeground");
		}
		if (config.containsKey("CTfileTreeDividerBackground")) {
			gui.pref.CTfileTreeDividerBackground = config.get("CTfileTreeDividerBackground");
		}
		if (config.containsKey("CTfileTreeBackground")) {
			gui.pref.CTfileTreeBackground = config.get("CTfileTreeBackground");
		}
		if (config.containsKey("CTfileTreeForeground")) {
			gui.pref.CTfileTreeForeground = config.get("CTfileTreeForeground");
		}
		if (config.containsKey("CTminiMapBackground")) {
			gui.pref.CTminiMapBackground = config.get("CTminiMapBackground");
		}
		if (config.containsKey("CTminiMapViewport")) {
			gui.pref.CTminiMapViewport = config.get("CTminiMapViewport");
		}
		if (config.containsKey("CTminiMapViewportBorder")) {
			gui.pref.CTminiMapViewportBorder = config.get("CTminiMapViewportBorder");
		}
		if (config.containsKey("CTtextAreaWOE")) {
			int CTtextAreaWOE = Integer.parseInt(config.get("CTtextAreaWOE"));
			gui.pref.CTtextAreaWOE = CTtextAreaWOE;
		}
		if (config.containsKey("CTtextAreaWallpaper")) {
			gui.pref.CTtextAreaWallpaper = config.get("CTtextAreaWallpaper");
		}
		if (config.containsKey("CTtextAreaEmblem")) {
			gui.pref.CTtextAreaEmblem = config.get("CTtextAreaEmblem");
		}

		if (config.containsKey("customTheme")) {
			try {
				int theme = Integer.parseInt(config.get("customTheme"));
				gui.pref.customTheme = theme;
				if (theme == 1) {
					gui.pref.setCustomTheme();
				}
			} catch (NumberFormatException ignored) {
			}
		}

		// Font size
		// Font size
		if (config.containsKey("fontSize")) {
			try {
				int size = Integer.parseInt(config.get("fontSize"));
				// Cap at 72
				if (size > 72) {
					size = 72;
				}
				if (size < 8) {
					size = 8;
				}
				gui.pref.fontSize = size;
			} catch (NumberFormatException ignored) {
			}
		}

		// Font family
		if (config.containsKey("fontFamily")) {
			gui.pref.font = config.get("fontFamily");
		}

		// Apply font
		gui.pref.applyDefaultFont(); // or set it directly if you prefer

		// Word wrap
		if (config.containsKey("wordWrap") && gui.iWordWrap != null) {
			boolean wrap = "1".equals(config.get("wordWrap"));
			gui.textArea.setLineWrap(wrap);
			gui.textArea.setWrapStyleWord(wrap);
			gui.iWordWrap.setSelected(wrap);
		}

		// Minimap
		if (config.containsKey("miniMap") && gui.iMinimap != null && gui.minimap != null) {
			boolean show = "1".equals(config.get("miniMap"));
			gui.minimap.setVisible(show);
			// Checkbox says "Hide Minimap", so select it when we want to HIDE
			gui.iMinimap.setSelected(!show);
		}

		// Open Folder
		if (config.containsKey("openFolder")) {
			boolean show = "1".equals(config.get("openFolder"));
			if (show == true) {
				gui.fileTree.setVisible(show);
				gui.pref.openFolder = 1;

				// Open Folder
				if (config.containsKey("openFolderPath")) {
					File openFolderPath = new File(config.get("openFolderPath"));
					gui.pref.openFolderPath = config.get("openFolderPath");
					gui.fileTree.setRootFolder(openFolderPath);
				}
			} else {
				gui.file.closeFolder();
			}
		}

		// Status bar
		if (config.containsKey("statusBar") && gui.iStatusBar != null && gui.statusPanel != null) {
			boolean show = "1".equals(config.get("statusBar"));
			gui.statusPanel.setVisible(show);
			// Checkbox says "Hide Status Bar", so select it when we want to HIDE
			gui.iStatusBar.setSelected(!show);
		}
	}

	public void saveConfig() {
		String home = System.getProperty("user.home");
		File notesFile = new File(home, "PHNX/config.txt");

		// Build the config string from current state
		StringBuilder sb = new StringBuilder();
		sb.append("selectedTheme = ").append(gui.pref.selectedTheme).append("\n");
		sb.append("fontSize = ").append(gui.pref.fontSize).append("\n");
		sb.append("fontFamily = ").append(gui.pref.font).append("\n");
		sb.append("wordWrap = ").append(gui.textArea.getLineWrap() ? "1" : "0").append("\n");
		sb.append("miniMap = ").append(gui.minimap != null && gui.minimap.isVisible() ? "1" : "0").append("\n");
		sb.append("statusBar = ").append(gui.statusPanel != null && gui.statusPanel.isVisible() ? "1" : "0")
				.append("\n");
		sb.append("openFolder = ").append(gui.pref.openFolder).append("\n");
		sb.append("openFolderPath = ").append(gui.pref.openFolderPath).append("\n");
		sb.append("customTheme = ").append(gui.pref.customTheme).append("\n");
		sb.append("CTtextAreaBackground = ").append(gui.pref.CTtextAreaBackground).append("\n");
		sb.append("CTtextAreaforeground = ").append(gui.pref.CTtextAreaforeground).append("\n");
		sb.append("CTtextAreaCaretColor = ").append(gui.pref.CTtextAreaCaretColor).append("\n");
		sb.append("CTtextAreaSelectionColor = ").append(gui.pref.CTtextAreaSelectionColor).append("\n");
		sb.append("CTtextAreaSelectedTextColor = ").append(gui.pref.CTtextAreaSelectedTextColor).append("\n");
		sb.append("CTlineNumbersBackground = ").append(gui.pref.CTlineNumbersBackground).append("\n");
		sb.append("CTlineNumbersForeground = ").append(gui.pref.CTlineNumbersForeground).append("\n");
		sb.append("CTcurrentLineNumberForeground = ").append(gui.pref.CTcurrentLineNumberForeground).append("\n");
		sb.append("CTstatusPanelBackground = ").append(gui.pref.CTstatusPanelBackground).append("\n");
		sb.append("CTcolRowLabel = ").append(gui.pref.CTcolRowLabel).append("\n");
		sb.append("CTdocTypeLabel = ").append(gui.pref.CTdocTypeLabel).append("\n");
		sb.append("CTscrollbarTrack = ").append(gui.pref.CTscrollbarTrack).append("\n");
		sb.append("CTscrollbarThumb = ").append(gui.pref.CTscrollbarThumb).append("\n");
		sb.append("CTfileTreeNonSelectionColor = ").append(gui.pref.CTfileTreeNonSelectionColor).append("\n");
		sb.append("CTfileTreeSelectionColor = ").append(gui.pref.CTfileTreeSelectionColor).append("\n");
		sb.append("CTfileTreeTextNonSelectionColor = ").append(gui.pref.CTfileTreeTextNonSelectionColor).append("\n");
		sb.append("CTfileTreeTextSelectionColor = ").append(gui.pref.CTfileTreeTextSelectionColor).append("\n");
		sb.append("CTfileTreeTitleBackground = ").append(gui.pref.CTfileTreeTitleBackground).append("\n");
		sb.append("CTfileTreeTitleForeground = ").append(gui.pref.CTfileTreeTitleForeground).append("\n");
		sb.append("CTfileTreeForeground = ").append(gui.pref.CTfileTreeForeground).append("\n");
		sb.append("CTfileTreeBackground = ").append(gui.pref.CTfileTreeBackground).append("\n");
		sb.append("CTfileTreeDividerBackground = ").append(gui.pref.CTfileTreeDividerBackground).append("\n");
		sb.append("CTminiMapBackground = ").append(gui.pref.CTminiMapBackground).append("\n");
		sb.append("CTminiMapViewport = ").append(gui.pref.CTminiMapViewport).append("\n");
		sb.append("CTminiMapViewportBorder = ").append(gui.pref.CTminiMapViewportBorder).append("\n");
		sb.append("CTtextAreaWallpaper = ").append(gui.pref.CTtextAreaWallpaper).append("\n");
		sb.append("CTtextAreaEmblem = ").append(gui.pref.CTtextAreaEmblem).append("\n");
		sb.append("CTtextAreaWOE = ").append(gui.pref.CTtextAreaWOE).append("\n");
		try {
			java.nio.file.Files.writeString(notesFile.toPath(), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
