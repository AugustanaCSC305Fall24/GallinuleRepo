package edu.augustana.ui;

import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;


public class Level1Controller extends LevelClassController {


    @FXML
    private Button nextLevelButton;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of('E', 'I', 'S', 'H', 'T', 'M', 'O', 'A'));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        backButton.setOnAction(event -> goBack());
        nextLevelButton.setOnAction(event -> goToLevel2());
    }

    // Add methods to play Morse code when a button is pressed
    @FXML
    private void playMorseCodeForLetter(char letter) {
        String letterMorse = String.valueOf(letter) + " ";
        SoundProducer.produceSound(letterMorse, 700, 100, 700); // Play the Morse code sound for the letter

    }

    @FXML
    private void playMorseE() {
        playMorseCodeForLetter('E');
    }

    @FXML
    private void playMorseT() {
        playMorseCodeForLetter('T');
    }

    @FXML
    private void playMorseM() {
        playMorseCodeForLetter('M');
    }

    @FXML
    private void playMorseO() {
        playMorseCodeForLetter('0');
    }

    @FXML
    private void playMorseA() {
        playMorseCodeForLetter('A');
    }

    @FXML
    private void playMorseI() {
        playMorseCodeForLetter('I');
    }

    @FXML
    private void playMorseS() {
        playMorseCodeForLetter('S');
    }

    @FXML
    private void playMorseH() {
        playMorseCodeForLetter('H');
    }

    @FXML
    private void goToLevel2() {
        App.switchToLevel2();
    }
}
