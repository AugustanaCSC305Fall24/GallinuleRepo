package edu.augustana.ui;

import edu.augustana.FrequencySelection;
import edu.augustana.MorseCodeConverter;
import edu.augustana.data.CwBotRecord;
import edu.augustana.data.ScriptedBot;
import edu.augustana.sound.CWBotPlayer;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import static edu.augustana.ui.App.scene;


public class MainPageController extends BasePage {

    @FXML
    private Button backButton;
    @FXML
    private ListView<ScriptedBot> botListView;
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
    private Button helperBtn;

    private final MorseCodeConverter converter = new MorseCodeConverter();
    private Boolean isTranslationHidden = true;
    public static String[] FREQUENCIES = {"200", "300", "400", "500", "600", "700", "800", "900"};
    public static String[] CHARACTER_SPEED = {"100", "300", "400", "500", "600"};
    public static String[] EFFECTIVE_SPEED = {"100", "300", "400", "500", "600", "700", "800", "900"};
    private int volume = 50;
    public static List<ScriptedBot> bots = new ArrayList<>();

    private StringBuilder inputSequence = new StringBuilder();
    private Timeline timeline;

    Map<Integer, ArrayList<String>> EnglishFrequencies;
    Map<Integer, ArrayList<String>> MorseFrequencies;
    @Override
    public void initialize() {
        Thread thread = new Thread(() -> {
            EnglishFrequencies = new HashMap<>();
            MorseFrequencies = new HashMap<>();
            for (int index = 0; index < 68; index++) {
                EnglishFrequencies.put(index, new ArrayList<>());
                MorseFrequencies.put(index, new ArrayList<>());
            }

        });
        thread.start();
        super.initialize();
        helperBtn.setOnAction(event -> helperPopUp());
        frequencySelection.getItems().addAll(List.of(FREQUENCIES));
        frequencySelection.setValue(FREQUENCIES[0]);
        effectiveSpeedSelection.getItems().addAll(List.of(EFFECTIVE_SPEED));
        effectiveSpeedSelection.setValue(EFFECTIVE_SPEED[0]);
        characterSpeedSelection.getItems().addAll(List.of(CHARACTER_SPEED));
        characterSpeedSelection.setValue(CHARACTER_SPEED[0]);
        if (bots != null) {
            botListView.getItems().addAll(bots);
        }
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleNoInput()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

        backButton.setOnAction(event -> goBack());

    }
    private void helperPopUp() {
        ImageView imageView = new ImageView(new Image("MorseCodeImageHelper.png"));
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Morse Code Tree");
        // chatgpt summary
        alert.setContentText("A Morse Code tree visually represents Morse Code, helping decode sequences of dots (.) and dashes (-). Starting at the root, each left branch corresponds to a dot, while each right branch corresponds to a dash. By following branches based on a sequence of signals, you reach the letter or number represented by that sequence.\n" +
                "\n" +
                "For example, to decode .-, start at the root, move left for the dot, and then right for the dash. Each character has a unique path down the tree, making it easy to decode messages by following the branches in the order of the signals.");
        alert.setGraphic(imageView);

        alert.show();
    }
    @FXML
    private void goBack() {
        App.backToMainMenu();
    }
    
    private void handleKeyPress(KeyCode code) {
        timeline.stop();
        timeline.playFromStart();

        if (code == KeyCode.N) {
            inputSequence.append(".");
        } else if (code == KeyCode.M) {
            inputSequence.append("-");
        }

        morseInput.setText(inputSequence.toString());
    }

    private void handleNoInput() {
        inputSequence.append(" ");
        morseInput.setText(inputSequence.toString());
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
        inputSequence = new StringBuilder();
    }

    private void writeMessages(int sliderValue, String morseText, String englishText) {
        if (sliderValue >= frequencySlider.getMin() && sliderValue <= frequencySlider.getMax()) {
            addMessageToFrequency(sliderValue, "User:  " + morseText, "User:  " + englishText + " ");
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
                SoundProducer.ProduceSound(morseText.split(":  ")[1], characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume, Integer.parseInt(frequencySelection.getValue()));
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
            SoundProducer.ProduceSound("e ", characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume, Integer.parseInt(frequencySelection.getValue()));
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void dahButton(){
        morseInput.setText(morseInput.getText() + "-");

        try {
            SoundProducer.ProduceSound("t ", characterSpeedSelection.getValue(), effectiveSpeedSelection.getValue(), volume, Integer.parseInt(frequencySelection.getValue()));
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
        MorseFrequencies.get(sliderValue).add(morseText);
        EnglishFrequencies.get(sliderValue).add(englishText);
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
        return MorseFrequencies.get(sliderValue);
    }

    private ArrayList<String> getFrequencyEnglishList(int sliderValue) {
        return EnglishFrequencies.get(sliderValue);
    }

    @FXML
    private void openCwBotAddPage() throws IOException  {
        App.switchToAddNewBotView();
    }

    @FXML
    private void playCurrentBot() {
        ScriptedBot botToPlay = botListView.getSelectionModel().getSelectedItem();
        if (botToPlay!= null) {
            CWBotPlayer botPlayer = new CWBotPlayer(botToPlay);
            botPlayer.playBot();
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a bot to play first!").show();
        }
    }

    @FXML
    private void actionDeleteBot() {
        ScriptedBot botToDelete = botListView.getSelectionModel().getSelectedItem();
        if (botToDelete!= null) {
            bots.remove(botToDelete);
            botListView.getItems().clear();
            botListView.getItems().addAll(bots);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a bot to delete first!").show();
        }
    }
}


