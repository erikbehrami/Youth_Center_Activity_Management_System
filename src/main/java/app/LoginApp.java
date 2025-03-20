package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.setScene(scene);
        stage.show();
    }
}

