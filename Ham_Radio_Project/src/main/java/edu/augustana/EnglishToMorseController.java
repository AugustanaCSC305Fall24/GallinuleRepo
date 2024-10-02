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
        String morseText = converter.EnglishToMorse(englishText);
        morseOutput.setText(morseText);
    }

    @FXML
    private void switchToMorseToEnglish() throws IOException {
        App.setRoot("MorseToEnglish");
    }

}