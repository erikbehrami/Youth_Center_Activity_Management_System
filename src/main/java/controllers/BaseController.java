package controllers;

import javafx.fxml.FXML;
import services.LanguageManager;
import services.SceneManager;

import java.util.Locale;

abstract class BaseController {
    SceneManager sceneManager = SceneManager.getInstance();
    LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    protected void handleSQLanguageClick() throws Exception{
        loadLanguage(new Locale("sq"));
    }

    @FXML
    protected void handleENLanguageClick() throws Exception{
        loadLanguage(Locale.ENGLISH);
    }

    @FXML
    protected void loadLanguage(Locale locale) throws Exception{
        languageManager.setLocale(locale);
        sceneManager.reload();
    }

    protected void handleGoBack() {
        sceneManager.switchScene(sceneManager.getLastPath(), null);
    }
}
