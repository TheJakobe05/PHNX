

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;

public class AutoComplete {

	GUI gui;
	
	private AutoCompletion autoCompletion;
	private DefaultCompletionProvider completionProvider;

	public AutoComplete(GUI gui) {

		this.gui = gui;
		
	}
	
	public void setupAutoComplete() {
	    // Create a simple provider that suggests words
	    completionProvider = new DefaultCompletionProvider();

	    // Example completions (you can add as many as you want)
	    // Basic words
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "public"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "private"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "protected"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "class"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "interface"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "void"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "return"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "static"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "final"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "if"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "else"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "for"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "while"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "try"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "catch"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "import"));
	    completionProvider.addCompletion(new BasicCompletion(completionProvider, "package"));

	    // Shorthand completions (type short text → expands to longer text)
	    completionProvider.addCompletion(new ShorthandCompletion(completionProvider,
	            "sysout", "System.out.println();", "System.out.println();"));
	    completionProvider.addCompletion(new ShorthandCompletion(completionProvider,
	            "syserr", "System.err.println();", "System.err.println();"));
	    completionProvider.addCompletion(new ShorthandCompletion(completionProvider,
	            "main", "public static void main(String[] args) {\n\t\n}", "main method"));

	    // Create and install the AutoCompletion
	    autoCompletion = new AutoCompletion(completionProvider);
	    autoCompletion.setShowDescWindow(true);          // shows description window
	    autoCompletion.setAutoCompleteSingleChoices(true);
	    autoCompletion.setAutoActivationEnabled(true);   // popup appears automatically
	    autoCompletion.setAutoActivationDelay(300);      // delay in ms
	    autoCompletion.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK));

	    // Install it on your text area
	    autoCompletion.install(gui.textArea);
	}
}
