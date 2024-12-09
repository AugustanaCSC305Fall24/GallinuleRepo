package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainMenu {
    @FXML
    private Button learnButton;

    @FXML
    private Button interactButton;

    @FXML
    private Button testButton;

    @FXML
    private void initialize() {
        learnButton.setOnAction(event -> openLevelPage());
        interactButton.setOnAction(event -> openInteractPage());
        testButton.setOnAction(event -> openTestPage());
        new Thread(() ->App.connectToServer("34.57.163.173")).start();
    }

    @FXML
    private void openLevelPage(){
        App.switchToLevelPage();
    }

    @FXML
    private void openInteractPage() {
        App.switchToMainPage();
    }

    @FXML
    private void openTestPage() {
        App.switchToTestPage();
    }

}
