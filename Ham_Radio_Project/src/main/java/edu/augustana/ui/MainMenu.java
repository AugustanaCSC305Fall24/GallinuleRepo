package edu.augustana.ui;

import edu.augustana.sound.SoundProducer;
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
        SoundProducer.openStaticLine();
        Thread thread2 = new Thread(SoundProducer::playStaticNoise);
        thread2.start();
        App.switchToMainPage();
    }
}
