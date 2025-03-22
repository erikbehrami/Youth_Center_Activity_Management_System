package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfApp extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profDashboard.fxml"));

            stage.setTitle("Professor");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
