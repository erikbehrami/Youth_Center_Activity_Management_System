package services;

import javafx.scene.Scene;
import utils.Navigator;

public final class ModeManager {
    private static String mode = "LIGHT_MODE";

    private ModeManager() {
    }

    public static String getMode() {
        return ModeManager.mode;
    }

    public static void setMode(String mode) {
        ModeManager.mode = mode;
    }

    public static void changeMode(Scene scene) {
        String darkModeStylesheet = ModeManager.class.getResource(Navigator.DARK_MODE).toExternalForm();

        if (mode.equals(Navigator.DARK_MODE)) {
            scene.getStylesheets().add(darkModeStylesheet);
        } else {
            scene.getStylesheets().remove(darkModeStylesheet);
        }
    }
}
