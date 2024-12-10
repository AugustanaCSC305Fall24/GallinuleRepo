package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;


public class Level4Controller extends LevelClassController {


    @FXML
    private Button nextLevelButton;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        backButton.setOnAction(event -> goBack());
        nextLevelButton.setOnAction(event -> goToLevel5());
    }

    @FXML
    private void playMorse1() {
        playMorseCodeForLetter('1');
    }

    @FXML
    private void playMorse2() {
        playMorseCodeForLetter('2');
    }

    @FXML
    private void playMorse3() {
        playMorseCodeForLetter('3');
    }

    @FXML
    private void playMorse4() {
        playMorseCodeForLetter('4');
    }

    @FXML
    private void playMorse5() {
        playMorseCodeForLetter('5');
    }

    @FXML
    private void playMorse6() {
        playMorseCodeForLetter('6');
    }

    @FXML
    private void playMorse7() {
        playMorseCodeForLetter('7');
    }

    @FXML
    private void playMorse8() {
        playMorseCodeForLetter('8');
    }

    @FXML
    private void playMorse9() {
        playMorseCodeForLetter('9');
    }

    @FXML
    private void playMorse0() {
        playMorseCodeForLetter('0');
    }
    @FXML
    private void goToLevel5() {
        App.switchToLevel5();
    }

}
