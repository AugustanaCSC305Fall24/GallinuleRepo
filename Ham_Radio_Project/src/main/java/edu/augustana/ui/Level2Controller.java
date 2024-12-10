package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;


public class Level2Controller extends LevelClassController {


    @FXML
    private Button nextLevelButton;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of('N', 'G', 'W', 'R', 'U', 'K', 'D', 'C'));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        backButton.setOnAction(event -> goBack());
        nextLevelButton.setOnAction(event -> goToLevel3());
    }


    @FXML
    private void playMorseN() {
        playMorseCodeForLetter('N');
    }

    @FXML
    private void playMorseG() {
        playMorseCodeForLetter('G');
    }

    @FXML
    private void playMorseW() {
        playMorseCodeForLetter('W');
    }

    @FXML
    private void playMorseR() {
        playMorseCodeForLetter('R');
    }

    @FXML
    private void playMorseU() {
        playMorseCodeForLetter('U');
    }

    @FXML
    private void playMorseK() {
        playMorseCodeForLetter('K');
    }

    @FXML
    private void playMorseD() {
        playMorseCodeForLetter('D');
    }

    @FXML
    private void playMorseC() {
        playMorseCodeForLetter('C');
    }

    @FXML
    private void goToLevel3() {
        App.switchToLevel3();
    }

}
