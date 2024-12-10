package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;


public class Level5Controller extends LevelClassController {

    @FXML
    private Button nextLevelButton;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of("ES", "SN", "UR", "GL", "GUD", "CPY", "SND", "PSE", "AGN", "TNX", "MSG", "WX", "HVY", "DEGS",
                "FN", "CUL", "GB"));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        backButton.setOnAction(event -> goBack());
    }
    @FXML
    private void playMorseES() {
        playMorseCodeForLetter("ES");
    }

    @FXML
    private void playMorseSN() {
        playMorseCodeForLetter("SN");
    }

    @FXML
    private void playMorseUR() {
        playMorseCodeForLetter("UR");
    }

    @FXML
    private void playMorseGL() {
        playMorseCodeForLetter("GL");
    }

    @FXML
    private void playMorseGUD() {
        playMorseCodeForLetter("GUD");
    }

    @FXML
    private void playMorseCPY() {
        playMorseCodeForLetter("CPY");
    }

    @FXML
    private void playMorseSND() {
        playMorseCodeForLetter("SND");
    }
    @FXML
    private void playMorsePSE() {
        playMorseCodeForLetter("PSE");
    }

    @FXML
    private void playMorseAGN() {
        playMorseCodeForLetter("AGN");
    }

    @FXML
    private void playMorseTNX() {
        playMorseCodeForLetter("TNX");
    }

    @FXML
    private void playMorseMSG() {
        playMorseCodeForLetter("MSG");
    }

    @FXML
    private void playMorseWX() {
        playMorseCodeForLetter("WX");
    }

    @FXML
    private void playMorseHVY() {
        playMorseCodeForLetter("HVY");
    }
    @FXML
    private void playMorseDEGS() {
        playMorseCodeForLetter("DEGS");
    }

    @FXML
    private void playMorseFN() {
        playMorseCodeForLetter("FN");
    }

    @FXML
    private void playMorseCUL() {
        playMorseCodeForLetter("CUL");
    }

    @FXML
    private void playMorseGB() {
        playMorseCodeForLetter("GB");
    }

    @FXML
    private void playMorseRST() {
        playMorseCodeForLetter("RST");
    }
    @FXML
    private void goToLevel6() {
        App.switchToLevel6();
    }
}
