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
	
	public Function_Preferences(GUI gui) {
		
		this.gui = gui;
		
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
	}
	
	public void fontSmaller() {
		fontSize = fontSize - 2;
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
	}
	
	public void fontReset() {
		fontSize = defaultFontSize;
		font = "SansSerif";
		Font myFont = new Font(font, Font.PLAIN, fontSize);
		gui.textArea.setFont(myFont);
	}

}
