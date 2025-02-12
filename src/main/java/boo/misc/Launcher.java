package boo.misc;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {

    /**
     * Main entry path of application.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

