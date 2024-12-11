package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

public class LevelPageController extends BasePage {

    @FXML
    private Button level1Button;

    @FXML
    private Button backButton;

    @FXML
    private Button level2Button;

    @FXML
    private Button level3Button;

    @FXML
    private Button level4Button;

    @FXML
    private Button level5Button;

    private Object Level1;


    @FXML
    private void startLearning() {
        // When the level1Button is clicked, switch to the Level1 page
        App.switchToLevel1();
    }
    @FXML
    private void goBack() {
        App.backToMainMenu();
    }

    @FXML
    public void initialize() {
        level1Button.setOnAction(event -> startLearning());
        backButton.setOnAction(event -> goBack());
    }

    @FXML
    private void openLevel1Page() throws IOException {
        App.switchToLevel1();
    }

}
