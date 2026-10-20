import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Function_Help {
	
	GUI gui;
	
	JLabel aboutPHNXLabel;
	
	public Function_Help(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void assembleMasthead() {
		// Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // 1. Create the main application window
            
            showModalPopup(gui.window);
        });
	}
	
	public void showModalPopup(JFrame window) {
	    JDialog popup = new JDialog(window, "About " + gui.appName, Dialog.ModalityType.APPLICATION_MODAL);
	    popup.setSize(400, 250);
	    ImageIcon appIcon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
	    popup.setIconImage(appIcon.getImage());
	    popup.setLocationRelativeTo(window);
	    popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    popup.setResizable(false);

	    aboutPHNXLabel = new JLabel(
	        "<html><style>body {text-align: center;} h1 {color: white; font-weight: 400; font-family: sans-serif; font-size: 26px} " +
	        "h2 {color: white; font-weight: 400; font-family: sans-serif; font-size: 12px} " +
	        "h3 {color: white; font-weight: 400; font-family: sans-serif; font-size: 10px}</style>" +
	        "<h1>" + gui.appName + "</h1>" +
	        "<h2>Version: " + gui.appVersion + "</h2>" +
	        "<h3>Copyright ©️ 2026 PHNX Utilities</h3></html>",
	        SwingConstants.CENTER
	    );

	    // Make the version clickable
	    aboutPHNXLabel.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent e) {
	            if (e.getClickCount() == 2 && e.getY() > 80 && e.getY() < 130) {
	                popup.dispose();
	                new SnakeGame().start();
	            }
	        }
	    });

	    int selectedTheme = gui.pref.selectedTheme;
	    switch (selectedTheme) {
	        case 1: popup.getContentPane().setBackground(Color.decode("#A8B0A5")); break;
	        case 2: popup.getContentPane().setBackground(Color.decode("#1A1A1A")); break;
	        case 3: popup.getContentPane().setBackground(Color.decode("#1E0B3A")); break;
	        case 4: popup.getContentPane().setBackground(Color.decode("#6B8F4E")); break;
	        case 5: popup.getContentPane().setBackground(Color.decode("#F5E9C8")); break;
	        case 67: popup.getContentPane().setBackground(Color.decode("#FFB000")); break;
	        case 0: popup.getContentPane().setBackground(gui.pref.parseColor(gui.pref.CTtextAreaBackground)); break;
	    }

	    popup.setLayout(new BorderLayout(10, 10));
	    popup.add(aboutPHNXLabel, BorderLayout.CENTER);
	    popup.setVisible(true);
	}

}
