package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;


public class Level3Controller extends LevelClassController {

    @FXML
    private Button nextLevelButton;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of('F', 'L', 'J', 'Y', 'Q', 'Z', 'X', 'P', 'V'));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        backButton.setOnAction(event -> goBack());
        nextLevelButton.setOnAction(event -> goToLevel4());
    }

    @FXML
    private void playMorseF() {
        playMorseCodeForLetter('F');
    }

    @FXML
    private void playMorseL() {
        playMorseCodeForLetter('L');
    }

    @FXML
    private void playMorseJ() {
        playMorseCodeForLetter('J');
    }

    @FXML
    private void playMorseY() {
        playMorseCodeForLetter('Y');
    }

    @FXML
    private void playMorseQ() {
        playMorseCodeForLetter('Q');
    }

    @FXML
    private void playMorseZ() {
        playMorseCodeForLetter('Z');
    }

    @FXML
    private void playMorseX() {
        playMorseCodeForLetter('X');
    }

    @FXML
    private void playMorseP() {
        playMorseCodeForLetter('P');
    }

    @FXML
    private void playMorseV() {
        playMorseCodeForLetter('V');
    }

    @FXML
    private void goToLevel4() {
        App.switchToLevel4();
    }

}
