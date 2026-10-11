import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Function_Preferences {
	
	GUI gui;
	String font = "SansSerif";
	
	int defaultFontSize = 16;
	int fontSize = defaultFontSize;
	int defaultTheme = 2;
	
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
		gui.lineNumbers.setForeground(Color.decode("#7A6B8A"));
		
		gui.statusPanel.setBackground(Color.decode("#A8B0A5"));
		gui.colRowLabel.setForeground(Color.decode("#7A6B8A"));
		gui.docTypeLabel.setForeground(Color.decode("#7A6B8A"));
		
		gui.iTheme1.setSelected(true);
		
		gui.iTheme2.setSelected(false);
		
	}
	public void setThemeCinderandAsh() {
		gui.textArea.setBackground(Color.decode("#0F0F0F"));
		gui.textArea.setForeground(Color.decode("#E8E4E0"));
		gui.textArea.setCaretColor(Color.decode("#C97B63"));
		gui.textArea.setSelectionColor(Color.decode("#C97B63"));
		gui.textArea.setSelectedTextColor(Color.decode("#ffffff"));

		gui.lineNumbers.setBackground(Color.decode("#1A1A1A"));
		gui.lineNumbers.setForeground(Color.decode("#4A4A4A"));
		
		gui.statusPanel.setBackground(Color.decode("#1A1A1A"));
		gui.colRowLabel.setForeground(Color.decode("#E8E4E0"));
		gui.docTypeLabel.setForeground(Color.decode("#E8E4E0"));
		
		gui.iTheme2.setSelected(true);
		
		gui.iTheme1.setSelected(false);
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
		gui.lineNumbers.setFont(myFont);
	}
	
	public void fontSmaller() {
		fontSize = fontSize - 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setFont(myFont);
	}
	
	public void fontReset() {
		fontSize = defaultFontSize;
		font = "SansSerif";
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
		gui.lineNumbers.setFont(myFont);
	}

}
