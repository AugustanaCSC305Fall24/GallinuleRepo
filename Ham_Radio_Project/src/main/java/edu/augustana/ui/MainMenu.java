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
    private Button testButton;

    @FXML
    private Button scenarioButton;

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
        SoundProducer.openStaticLine();
        Thread thread2 = new Thread(SoundProducer::playStaticNoise);
        thread2.start();
        App.switchToMainPage();
    }

    @FXML
    private void openTestPage() {
        App.switchToBotPage();
    }

    @FXML
    private void openScenarioPage() {
        App.switchToScenarioPage();
    }

}
