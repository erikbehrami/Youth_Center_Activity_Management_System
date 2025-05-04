package controllers;

import javafx.fxml.FXML;
import services.LanguageManager;
import services.SceneManager;
import utils.Navigator;
import utils.ModeChange;
import java.util.Locale;

abstract class BaseController {
    SceneManager sceneManager = SceneManager.getInstance();
    LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    protected void handleSQLanguageClick() {
        loadLanguage(new Locale("sq"));
    }

    @FXML
    protected void handleENLanguageClick() {
        loadLanguage(Locale.ENGLISH);
    }

    @FXML
    protected void loadLanguage(Locale locale) {
        languageManager.setLocale(locale);
        sceneManager.reload();
    }

    @FXML
    protected void handleGoBack() {
        sceneManager.switchScene(sceneManager.getLastPath(), null);
    }

    @FXML
    protected void changeMode(){
        if(ModeChange.getMode().equals(Navigator.DARK_MODE)){
            ModeChange.setMode("LIGHT_MODE");
        }else{
            ModeChange.setMode(Navigator.DARK_MODE);
        }
        sceneManager.reload();
    }
}
