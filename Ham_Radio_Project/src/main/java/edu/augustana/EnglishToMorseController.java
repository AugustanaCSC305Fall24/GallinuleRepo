package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class EnglishToMorseController {

    @FXML
    private TextArea englishInput;

    @FXML
    private TextArea morseOutput;

    private MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    private void convertToMorse() {
        String englishText = englishInput.getText();

        // Check if input is empty
        if (englishText.isEmpty()) {
            morseOutput.setText("Input cannot be empty!");
            return;
        }
        try {
            // Convert English text to Morse code
            String morseText = converter.EnglishToMorse(englishText);
            morseOutput.setText(morseText);
        } catch (Exception e) {
            morseOutput.setText("Error converting to Morse code: " + e.getMessage());
        }

    }

    @FXML
    private void switchToMorseToEnglish() throws IOException {
        App.setRoot("MorseToEnglish");
    }

    @FXML
    private void switchToFrequency() throws IOException{
        App.setRoot("FrequencySelection");
    }

}