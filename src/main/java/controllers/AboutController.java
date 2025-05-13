package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import services.LanguageManager;

import java.util.Locale;

public class AboutController {

    @FXML
    private Label labelTitle;
@FXML
private Label labelDescription;
    @FXML
    public void initialize() {
        if (languageManager.getLocale() == Locale.ENGLISH) {
            System.out.println("wsedtrfguhij");
            labelDescription.setText("ENGLISH");
        } else {
            labelDescription.setText("ALBANESE");
        }
    }
    LanguageManager languageManager = LanguageManager.getInstance();




}
