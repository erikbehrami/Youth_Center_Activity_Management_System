package utils;

import javafx.scene.Scene;

public final class ModeChange {
    private static String mode = "LIGHT_MODE";

    private ModeChange() {}

    public static String getMode() {
        return ModeChange.mode;
    }

    public static void setMode(String mode) {
        ModeChange.mode = mode;
    }

    public static void changeMode(Scene scene) {
        String darkModeStylesheet = ModeChange.class.getResource(Navigator.DARK_MODE).toExternalForm();

        if (mode.equals(Navigator.DARK_MODE)) {
            scene.getStylesheets().add(darkModeStylesheet);
        } else {
            scene.getStylesheets().remove(darkModeStylesheet);
        }
    }
}
