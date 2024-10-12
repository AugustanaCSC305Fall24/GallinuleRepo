package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import javax.sound.sampled.LineUnavailableException;


public class CWTransmitterController {

    @FXML
    private Button ditButton;

    @FXML
    private Button dahButton;

    @FXML
    private Button spaceButton;

    @FXML
    private TextArea morseCodeDisplay;

    @FXML
    private  TextArea englishTranslationDisplay;

    @FXML
    private Slider volumeSlider;

    private  StringBuilder morseCodeMessage = new StringBuilder();
    private  MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    private void initialize() {
        volumeSlider.setValue(50); // Initial volume set to 50
    }

    @FXML
    private void handleDitButton(MouseEvent event) {
        try {
            playMorseSymbol('.');
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDahButton (MouseEvent event) {
        try {
            playMorseSymbol('-');
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSpaceButton (MouseEvent event) {
        morseCodeMessage.append(" ");
        updateDisplays();
    }

    private void playMorseSymbol(char symbol) throws LineUnavailableException {
        // Playing the sound based on the symbol
        if (symbol == '.') {
            SoundProducer.playDit((int) volumeSlider.getValue());
        } else if (symbol == '-') {
            SoundProducer.playDah((int) volumeSlider.getValue());
        }

        // Appending the symbol to the Morse code message
        morseCodeMessage.append(symbol);
        updateDisplays();
    }

    private  void updateDisplays() {
        // Update Morse code display
        morseCodeDisplay.setText(morseCodeMessage.toString());

        // Converting Morse to English and update the display
        String englishTranslation = converter.MorseToEnglish(morseCodeMessage.toString());
        englishTranslationDisplay.setText(englishTranslation);
    }
}
