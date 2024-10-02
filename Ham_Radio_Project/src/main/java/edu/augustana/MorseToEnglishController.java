package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MorseToEnglishController {

    @FXML
    private TextArea morseInput;

    @FXML
    private TextArea englishOutput;

    private MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    private void convertToEnglish(){
        String morseText = morseInput.getText();
        String englishText = converter.MorseToEnglish(morseText);
        englishOutput.setText(englishText);
    }
    @FXML
    private void switchToEnglishToMorse() throws IOException {
        App.setRoot("EnglishToMorse");
    }
}