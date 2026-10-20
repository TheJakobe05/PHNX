import javax.swing.Timer;

public class DiscoMode {
    private final GUI gui;
    private Timer timer;
    private int originalTheme;
    private int step;

    public DiscoMode(GUI gui) {
        this.gui = gui;
    }

    public void start(int durationMs) {
        if (timer != null && timer.isRunning()) return;

        originalTheme = gui.pref.selectedTheme;
        step = 0;

        timer = new Timer(180, e -> {
            step++;
            int theme = (step % 5) + 1;
            switch (theme) {
                case 1: gui.pref.setThemeAlabaster(); break;
                case 2: gui.pref.setThemeCinderandAsh(); break;
                case 3: gui.pref.setThemeVaporwave77(); break;
                case 4: gui.pref.setThemeMatchaLatte(); break;
                case 5: gui.pref.setThemeCremeBrulee(); break;
            }
        });
        timer.start();

        Timer stop = new Timer(durationMs, e -> {
            timer.stop();
            // restore original theme
            switch (originalTheme) {
                case 1: gui.pref.setThemeAlabaster(); break;
                case 2: gui.pref.setThemeCinderandAsh(); break;
                case 3: gui.pref.setThemeVaporwave77(); break;
                case 4: gui.pref.setThemeMatchaLatte(); break;
                case 5: gui.pref.setThemeCremeBrulee(); break;
            }
        });
        stop.setRepeats(false);
        stop.start();
    }
}