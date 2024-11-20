package edu.augustana;

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

    private String[] frequencies = new String[5];

    public FrequencySelection() {
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = "";
        }
    }

    @FXML
    public void writeToFrequency(){
        String morseText = morseInput.getText();
        int frequencyIndex = (int) writeFrequencySlider.getValue() -1;
        if (frequencyIndex >= 0 && frequencyIndex < frequencies.length) {
            frequencies[frequencyIndex] += morseText + "\n";
        }

    }

    @FXML
    public void displayFrequency() {
        int sliderValue = (int) displayFrequencySlider.getValue();

        if (sliderValue >= 1 && sliderValue <= 5) {
            morseOutput.setText(frequencies[sliderValue - 1]);
        }
    }

    /*@FXML
    private void switchToMorseToEnglish() throws IOException {
        App.setRoot("MorseToEnglish");
    }*/
}
