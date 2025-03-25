package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterCource extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registerCourse.fxml"));

            stage.setTitle("Register Course");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

