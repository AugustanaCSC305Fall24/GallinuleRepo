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

    @FXML
    private Button level6Button;



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

    @FXML
    private void openLevel2Page() throws IOException{
        App.switchToLevel2();
    }

    @FXML
    private void openLevel3page() throws IOException{
        App.switchToLevel3();
    }
    @FXML
    private void openLevel4page() throws IOException{
        App.switchToLevel4();
    }

    @FXML
    private void openLevel5page() throws IOException{
        App.switchToLevel5();
    }

    @FXML
    private void openLevel6page() throws IOException{
        App.switchToLevel6();
    }

    @FXML
    private void openLevel7page() throws IOException{
        App.switchToLevel7();
    }

    @FXML
    private void openLevel8page() throws IOException{
        App.switchToLevel8();
    }

}
