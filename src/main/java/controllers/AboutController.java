package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import model.Faqs;
import repository.FaqsRepository;

import java.util.List;

public class AboutController {
    @FXML
    private VBox aboutVBox;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelDescription;
    @FXML
    private Label labelMission;

    private final FaqsRepository faqsRepository = new FaqsRepository();

    @FXML
    private void initialize() {

        Accordion accordion = new Accordion();
        accordion.setPrefWidth(520.0);
        accordion.getStyleClass().add("faq-accordion");

        List<Faqs> faqs = faqsRepository.getAll();
        for (Faqs faq : faqs) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(faq.getQuestion());
            titledPane.setAnimated(true);

            Label answerLabel = new Label(faq.getAnswer());
            answerLabel.setWrapText(true);
            answerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-padding: 10;");
            answerLabel.setPrefWidth(500.0);

            VBox content = new VBox(answerLabel);
            content.setStyle("-fx-background-color: white; -fx-padding: 10;");
            titledPane.setContent(content);

            accordion.getPanes().add(titledPane);
        }

        aboutVBox.getChildren().add(aboutVBox.getChildren().indexOf(labelDescription) + 1, accordion);
    }
}