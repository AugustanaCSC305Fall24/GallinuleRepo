package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.data.CwBotRecord;
import edu.augustana.data.ScriptedBot;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPageController extends BasePage {

    @FXML
    private VBox morseMessagesVBox;
    @FXML
    private VBox englishMessagesVBox;
    @FXML
    private ScrollPane morseMessagesScrollPane;
    @FXML
    private ScrollPane englishMessagesScrollPane;
    @FXML
    private Label morseInput = new Label();
    @FXML
    private Slider frequencySlider;
    @FXML
    private Slider rangeSlider;
    @FXML
    public ComboBox<String> frequencySelection;
    @FXML
    public ComboBox<String> effectiveSpeedSelection;
    @FXML
    public ComboBox<String> characterSpeedSelection;
    @FXML
    private ListView<CwBotRecord> CwBotsListView;
    @FXML
    public Slider volumeSlider;
    @FXML
    private Button addBotButton;

    private final ArrayList<String> frequency1Morse = new ArrayList<>();
    private final ArrayList<String> frequency2Morse = new ArrayList<>();
    private final ArrayList<String> frequency3Morse = new ArrayList<>();
    private final ArrayList<String> frequency4Morse = new ArrayList<>();
    private final ArrayList<String> frequency5Morse = new ArrayList<>();

    private final ArrayList<String> frequency1English = new ArrayList<>();
    private final ArrayList<String> frequency2English = new ArrayList<>();
    private final ArrayList<String> frequency3English = new ArrayList<>();
    private final ArrayList<String> frequency4English = new ArrayList<>();
    private final ArrayList<String> frequency5English = new ArrayList<>();

    private final MorseCodeConverter converter = new MorseCodeConverter();
    private Boolean isTranslationHidden = true;
    public static String[] FREQUENCIES = {"200", "300", "400", "500", "600", "700", "800", "900"};
    public static String[] CHARACTER_SPEED = {"200", "300", "400", "500", "600"};
    public static String[] EFFECTIVE_SPEED = {"200", "300", "400", "500", "600", "700", "800", "900"};
    private int volume = 50;
    public static List<ScriptedBot> bots = new ArrayList<>();

    @Override
    public void initialize() {
        super.initialize();
        //frequencySelection.getItems().addAll(List.of(FREQUENCIES));
        //frequencySelection.setValue(FREQUENCIES[0]);
        effectiveSpeedSelection.getItems().addAll(List.of(EFFECTIVE_SPEED));
        effectiveSpeedSelection.setValue(EFFECTIVE_SPEED[0]);
        characterSpeedSelection.getItems().addAll(List.of(CHARACTER_SPEED));
        characterSpeedSelection.setValue(CHARACTER_SPEED[0]);

    }

    //Code from exam 1 (Chatter Box)
    private void addMessageToChatLogUI(String message, VBox vbox, ScrollPane scrollpane) {
        Label label = new Label(message);
        label.setWrapText(true);
        vbox.getChildren().add(label);
    }

    @FXML
    private void writeToFrequency() {
        int rangeValue = (int) rangeSlider.getValue();
        morseMessagesVBox.getChildren().clear();
        String morseText = morseInput.getText();
        String englishText = converter.MorseToEnglish(morseText);
        int sliderValue = (int) frequencySlider.getValue();

        writeMessages(sliderValue, morseText, englishText);
        for (int i = 1; i < rangeValue; i++ ) {
            writeMessages(sliderValue + i, morseText, englishText);
            writeMessages(sliderValue - i, morseText, englishText);
        }

        displayMorseMessagesFromFrequency(sliderValue);
        morseInput.setText("");
    }

    private void writeMessages(int sliderValue, String morseText, String englishText) {
        if (sliderValue >= frequencySlider.getMin() && sliderValue <= frequencySlider.getMax()) {
            addMessageToFrequency(sliderValue, "User:  " + morseText, "User:  " + englishText);
            //addMessageToFrequency(sliderValue, "Bot:  " + converter.EnglishToMorse(ChatBot.getResponse(englishText)), "Bot:  " + ChatBot.getResponse(englishText));
        }
    }

    @FXML
    private void displayFrequency() {
        morseMessagesVBox.getChildren().clear();
        englishMessagesVBox.getChildren().clear();
        int sliderValue = (int) frequencySlider.getValue();

        displayMorseMessagesFromFrequency(sliderValue);
        if (!isTranslationHidden){
            displayEnglishMessagesFromFrequency(sliderValue);
        }
    }

    @FXML
    private void showTranslation(){
        englishMessagesVBox.getChildren().clear();
        int sliderValue = (int) frequencySlider.getValue();
        displayEnglishMessagesFromFrequency(sliderValue);
        isTranslationHidden = false;
    }

    @FXML
    private void hideTranslation(){
        englishMessagesVBox.getChildren().clear();
        isTranslationHidden = true;
    }

    @FXML
    private void playSound(){
        int sliderValue = (int) frequencySlider.getValue();

        ArrayList<String> morseTextList = getFrequencyEnglishList(sliderValue);
        for (String morseText: morseTextList){
            try {
                morseText += " ";
                SoundProducer.ProduceSound(morseText.split(":  ")[1], characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume);
            } catch (LineUnavailableException e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void getVolume(){
        volume = (int) volumeSlider.getValue();
    }

    @FXML
    private void ditButton(){
        morseInput.setText(morseInput.getText() + ".");

        try {
            SoundProducer.ProduceSound("e ", characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume);
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void dahButton(){
        morseInput.setText(morseInput.getText() + "-");

        try {
            SoundProducer.ProduceSound("t ", characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume);
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void charSpaceButton(){
        morseInput.setText(morseInput.getText() + " ");
    }

    @FXML
    private void wordSpaceButton(){
        morseInput.setText(morseInput.getText() + "   ");

    }


    private void addMessageToFrequency(int sliderValue, String morseText, String englishText) {
        switch (sliderValue) {
            case 1:
                frequency1Morse.add(morseText);
                frequency1English.add(englishText);
                break;
            case 2:
                frequency2Morse.add(morseText);
                frequency2English.add(englishText);
                break;
            case 3:
                frequency3Morse.add(morseText);
                frequency3English.add(englishText);
                break;
            case 4:
                frequency4Morse.add(morseText);
                frequency4English.add(englishText);
                break;
            case 5:
                frequency5Morse.add(morseText);
                frequency5English.add(englishText);
                break;
            default:
                break;
        }
    }

    private void displayMorseMessagesFromFrequency(int sliderValue) {
        ArrayList<String> frequencyMorse = getFrequencyMorseList(sliderValue);

        if (frequencyMorse != null) {
            for (String message : frequencyMorse) {
                addMessageToChatLogUI(message, morseMessagesVBox, morseMessagesScrollPane);
            }
        }
    }

    private void displayEnglishMessagesFromFrequency(int sliderValue) {
        ArrayList<String> frequencyEnglish = getFrequencyEnglishList(sliderValue);

        if (frequencyEnglish != null) {
            for (String message : frequencyEnglish) {
                addMessageToChatLogUI(message, englishMessagesVBox, englishMessagesScrollPane);
            }
        }
    }

    private ArrayList<String> getFrequencyMorseList(int sliderValue) {
        switch (sliderValue) {
            case 1:
                return frequency1Morse;
            case 2:
                return frequency2Morse;
            case 3:
                return frequency3Morse;
            case 4:
                return frequency4Morse;
            case 5:
                return frequency5Morse;
            default:
                return null;
        }
    }

    private ArrayList<String> getFrequencyEnglishList(int sliderValue) {
        switch (sliderValue) {
            case 1:
                return frequency1English;
            case 2:
                return frequency2English;
            case 3:
                return frequency3English;
            case 4:
                return frequency4English;
            case 5:
                return frequency5English;
            default:
                return null;
        }
    }

    @FXML
    private void openCwBotAddPage() throws IOException  {
        App.switchToAddNewBotView();
    }
}
