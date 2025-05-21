package controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Advertisement;
import repository.AdvertisementRepository;
import services.SceneManager;
import utils.Navigator;

import java.io.File;
import java.util.ArrayList;

import javafx.util.Duration;

public class HomeController {

    @FXML
    private HBox adContainer;
    @FXML
    private ScrollPane adScrollPane;

    private final AdvertisementRepository adsRepository = new AdvertisementRepository();
    private final SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    public void initialize() {
        loadAdvertisements();
    }

    @FXML
    private void handleSignIn() {
        sceneManager.switchScene(Navigator.SIGN_IN, "Sign In");
    }

    @FXML
    private void handleSignUp() {
        sceneManager.switchScene(Navigator.SIGN_UP, "Sign Up");
    }

    @FXML
    private void handleInfo() {
        sceneManager.switchScene(Navigator.INFO, "Info");
    }

    private void loadAdvertisements() {
        ArrayList<Advertisement> advertisements;
        try {
            advertisements = adsRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            Text errorText = new Text("Error loading advertisements.");
            errorText.setFont(Font.font("Segoe UI", 12));
            adContainer.getChildren().add(errorText);
            return;
        }

        if (advertisements == null || advertisements.isEmpty()) {
            Text noAdsText = new Text("No advertisements available.");
            noAdsText.setFont(Font.font("Segoe UI", 12));
            adContainer.getChildren().add(noAdsText);
            return;
        }

        for (Advertisement ad : advertisements) {
            HBox adBox = new HBox(15);
            adBox.getStyleClass().add("ad-box");

            ImageView adImageView = new ImageView();
            adImageView.setFitWidth(200);
            adImageView.setFitHeight(120);
            adImageView.setPreserveRatio(true);

            try {
                File file = new File(System.getProperty("user.dir") + "/src/main/java/adsImages/" + ad.getAdImageUrl());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    adImageView.setImage(image);
                } else {
                    throw new Exception("Ad image not found: " + file.getAbsolutePath());
                }
            } catch (Exception e) {
                File file = new File(System.getProperty("user.dir") + "/src/main/java/adsImages/placeholder.png");
                Image image = new Image(file.toURI().toString());
                adImageView.setImage(image);
                System.err.println("Failed to load ad image: " + ad.getAdImageUrl() + " - " + e.getMessage());
            }


            VBox textBox = new VBox(5);
            Text sponsorText = new Text(ad.getSponsorName());
            sponsorText.setFont(Font.font("Segoe UI Semibold", 16));
            sponsorText.setWrappingWidth(180);

            Text titleText = new Text(ad.getAdTitle());
            titleText.setFont(Font.font("Segoe UI", 14));
            titleText.setWrappingWidth(180);

            textBox.getChildren().addAll(sponsorText, titleText);
            adBox.getChildren().addAll(adImageView, textBox);
            adContainer.getChildren().add(adBox);
        }

        if (advertisements.size() > 2) {
            startAdCycleAnimation(advertisements.size());
        }
    }

    private void startAdCycleAnimation(int adCount) {
        double maxHvalue = 1.0;
        double cycleDurationSeconds = adCount * 3.0 + 1.0;

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(adScrollPane.hvalueProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(adCount * 3.0), new KeyValue(adScrollPane.hvalueProperty(), maxHvalue, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(cycleDurationSeconds), new KeyValue(adScrollPane.hvalueProperty(), maxHvalue, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(cycleDurationSeconds + 0.5), new KeyValue(adScrollPane.hvalueProperty(), 0, Interpolator.LINEAR))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
