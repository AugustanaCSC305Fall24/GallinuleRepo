package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class FrequencySelection {

    @FXML
    private TextArea morseInput;

    @FXML
    private TextArea morseOutput;

    @FXML
    private Slider writeFrequencySlider;

    @FXML
    private Slider displayFrequencySlider;

    private String frequency1 = "";
    private String frequency2 = "";
    private String frequency3 = "";
    private String frequency4 = "";
    private String frequency5 = "";


    @FXML
    private void writeToFrequency(){
        String morseText = morseInput.getText();
        if (writeFrequencySlider.getValue() == 1){
            frequency1 = frequency1 + morseText + "\n";
        } else if (writeFrequencySlider.getValue() == 2){
            frequency2 = frequency2+morseText + "\n";
        } else if (writeFrequencySlider.getValue() == 3){
            frequency3 = frequency3+morseText + "\n";
        } else if (writeFrequencySlider.getValue() == 4){
            frequency4 = frequency4+morseText + "\n";
        } else if (writeFrequencySlider.getValue() == 5){
            frequency5 = frequency5+morseText + "\n";
        }

    }

    @FXML
    private void displayFrequency() {
        int sliderValue = (int) displayFrequencySlider.getValue();
        String[] frequencies = {frequency1, frequency2, frequency3, frequency4, frequency5};

        if (sliderValue >= 1 && sliderValue <= 5) {
            morseOutput.setText(frequencies[sliderValue - 1]);
        }
    }

    @FXML
    private void switchToMorseToEnglish() throws IOException {
        App.setRoot("MorseToEnglish");
    }
}
