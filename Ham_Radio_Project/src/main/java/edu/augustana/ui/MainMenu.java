package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


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
    private void openLevelPage(){
        App.switchToLevelPage();
    }

    @FXML
    private void openInteractPage() {
        App.switchToMainPage();
    }
}
