package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu {
    @FXML
    private Button learnButton;

    @FXML
    private Button interactButton;

    @FXML
    private void initialize() {
        learnButton.setOnAction(event -> openLevelPage());
        interactButton.setOnAction(event -> openInteractPage());
    }

    @FXML
    private void openLevelPage() {
        System.out.println("Level page opened.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/augustana/LevelPage.fxml"));
            AnchorPane learnPage = loader.load();
            Stage stage = (Stage) learnButton.getScene().getWindow();
            stage.setScene(new Scene(learnPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openInteractPage() {
        System.out.println("Interact page opened.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/augustana/MainPage.fxml"));
            // Change AnchorPane to BorderPane if that's the root element of MainPage.fxml
            BorderPane mainPage = loader.load();
            Stage stage = (Stage) interactButton.getScene().getWindow();
            stage.setScene(new Scene(mainPage));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
