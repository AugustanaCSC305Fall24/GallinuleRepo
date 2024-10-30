package edu.augustana.ui;

import javafx.fxml.FXML;

import java.awt.*;

public class InteractPageController {
    @FXML
    private Label interactLabel;

    @FXML
    public void initialize() {
        interactLabel.setText("Welcome to the Interact Page!");
    }
}
