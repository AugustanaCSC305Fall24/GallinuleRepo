package edu.augustana;

import java.io.IOException;

import edu.augustana.sound.SoundProducer;
import edu.augustana.ui.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.sound.sampled.LineUnavailableException;

public class MorseToEnglishController {

    @FXML
    private TextArea morseInput;

    @FXML
    private TextArea englishOutput;

    private MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    private void convertToEnglish(){
        String morseText = morseInput.getText();
        if (morseText.isEmpty()) {
            englishOutput.setText("Input cannot be empty!");
            return;
        }
        try {
            String englishText = converter.MorseToEnglish(morseText);
            englishOutput.setText(englishText);
        } catch (Exception e) {
            englishOutput.setText("Error converting to English: " + e.getMessage());
        }

    }
    @FXML
    private void playSound(){
        String morseText = englishOutput.getText();
        try {
            SoundProducer.ProduceSound(morseText, "200", "600", 5, 440); // ERROR: extract from main
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

   /* @FXML
    private void switchToEnglishToMorse() throws IOException {
        App.setRoot("EnglishToMorse");
    }*/

    /*@FXML
    private void switchToFrequency() throws IOException{
        App.setRoot("FrequencySelection");
    }*/
}