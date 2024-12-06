package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.data.CWMessage;
import edu.augustana.data.CwBotRecord;
import edu.augustana.data.HamRadio;
import edu.augustana.data.ScriptedBot;
import edu.augustana.sound.CWBotPlayer;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static edu.augustana.ui.App.scene;
import static edu.augustana.ui.App.switchToMainPage;


public class MainPageController extends BasePage {

    @FXML private ListView<ScriptedBot> botListView;
    @FXML private VBox morseMessagesVBox, englishMessagesVBox;
    @FXML private ScrollPane morseMessagesScrollPane, englishMessagesScrollPane;
    @FXML private Label morseInput = new Label();
    @FXML private Slider frequencySlider, rangeSlider, volumeSlider;
    @FXML public ComboBox<String> frequencySelection, effectiveSpeedSelection, characterSpeedSelection;
    @FXML private ListView<CwBotRecord> CwBotsListView;
    @FXML private Button helperBtn;

    private final MorseCodeConverter converter = new MorseCodeConverter();
    private Boolean isTranslationHidden = true;
    public static final String[] FREQUENCIES = {"200", "300", "400", "500", "600", "700", "800", "900"};
    public static final String[] CHARACTER_SPEED = {"100", "200", "300", "400", "500", "600"};
    public static final String[] EFFECTIVE_SPEED = {"100", "200", "300", "400", "500", "600", "700", "800", "900"};
    private int volume = 50;
    public static List<ScriptedBot> bots = new ArrayList<>();
    private StringBuilder inputSequence = new StringBuilder();
    private Timeline timeline;

    private Map<Integer, ArrayList<String>> EnglishFrequencies = new HashMap<>();
    private Map<Integer, ArrayList<String>> MorseFrequencies = new HashMap<>();

    private SourceDataLine inputLine;
    private SourceDataLine staticNoiceLine;

    @Override
    public void initialize() throws LineUnavailableException {
        initializeFrequencyMaps();
        configureUIComponents();
        initializeSoundLine();
        setupKeyEventHandler();
        startNoInputTimeline();
    }

    private void initializeFrequencyMaps() {
        Thread thread = new Thread(() -> {
            for (int index = 0; index < 68; index++) {
                EnglishFrequencies.put(index, new ArrayList<>());
                MorseFrequencies.put(index, new ArrayList<>());
            }
        });
        thread.start();
    }

    private void configureUIComponents() {
        helperBtn.setOnAction(event -> showHelperPopUp());
        frequencySelection.getItems().addAll(List.of(FREQUENCIES));
        frequencySelection.setValue(FREQUENCIES[0]);
        effectiveSpeedSelection.getItems().addAll(List.of(EFFECTIVE_SPEED));
        effectiveSpeedSelection.setValue(EFFECTIVE_SPEED[0]);
        characterSpeedSelection.getItems().addAll(List.of(CHARACTER_SPEED));
        characterSpeedSelection.setValue(CHARACTER_SPEED[0]);
        if (bots != null) {
            botListView.getItems().addAll(bots);
        }
    }

    private void initializeSoundLine() throws LineUnavailableException {
        inputLine = SoundProducer.openLine();

        Thread thread2 = new Thread(() -> {
            staticNoiceLine = SoundProducer.openLine();
            SoundProducer.playStaticNoise(staticNoiceLine, 1);
        });
        thread2.start();
    }

    private void setupKeyEventHandler() {
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    private void startNoInputTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleNoInput()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        HamRadio.theRadio.setMessageReceivedListener(msg -> System.out.println("we'd like to play this message as SOUND: " + msg));
        frequencySlider.valueProperty().addListener((obs, oldVal, newVal) -> HamRadio.theRadio.setFrequency(newVal.doubleValue()));
    }

    private void showHelperPopUp() {
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
        staticNoiceLine.close();
        App.backToMainMenu();

    }

    private void handleKeyPress(KeyCode code) {
        resetTimeline();
        if (code == KeyCode.N) {
            inputSequence.append(".");
            SoundProducer.produceSound("e", Integer.parseInt(characterSpeedSelection.getValue()), Integer.parseInt(effectiveSpeedSelection.getValue()), volume, Integer.parseInt(frequencySelection.getValue()));
        } else if (code == KeyCode.M) {
            inputSequence.append("-");
            SoundProducer.produceSound("t", Integer.parseInt(characterSpeedSelection.getValue()), Integer.parseInt(effectiveSpeedSelection.getValue()), volume, Integer.parseInt(frequencySelection.getValue()));
        }
    }

    private void resetTimeline() {
        timeline.stop();
        timeline.playFromStart();
    }

    private void handleNoInput() {
        if (!inputSequence.toString().isEmpty()) {
            inputSequence.append(" ");
            morseInput.setText(inputSequence.toString());
        }
    }

    private int getFrequencyIntVal(Double frequencyValue){
        double transformedValue = (frequencyValue - 7) * 1000;
        return (int) Math.round(transformedValue);
    }
    @FXML
    private void writeToFrequency() {
        int rangeValue = (int) Math.round(rangeSlider.getValue() * 1000);

        morseMessagesVBox.getChildren().clear();
        String morseText = morseInput.getText();
        String englishText = converter.MorseToEnglish(morseText);
        int intTransformedValue = getFrequencyIntVal(frequencySlider.getValue());

        CWMessage message = new CWMessage(HamRadio.theRadio.getUserName(), morseText, intTransformedValue);
        HamRadio.theRadio.sendMessage(message);


        writeMessages(intTransformedValue, morseText, englishText);
        for (int i = 1; i < rangeValue; i++ ) {
            writeMessages(intTransformedValue + i, morseText, englishText);
            writeMessages(intTransformedValue - i, morseText, englishText);
        }

        displayMorseMessagesFromFrequency(intTransformedValue);
        morseInput.setText("");
        inputSequence = new StringBuilder();
    }

    private void writeMessages(int sliderValue, String morseText, String englishText) {
        if (sliderValue >= getFrequencyIntVal(frequencySlider.getMin()) && sliderValue <= getFrequencyIntVal(frequencySlider.getMax())) {
            addMessageToFrequency(sliderValue, "User:  " + morseText, "User:  " + englishText + " ");

            //addMessageToFrequency(sliderValue, "Bot:  " + converter.EnglishToMorse(ChatBot.getResponse(englishText)), "Bot:  " + ChatBot.getResponse(englishText));
        }
    }

    @FXML
    private void displayFrequency() {
        morseMessagesVBox.getChildren().clear();
        englishMessagesVBox.getChildren().clear();
        int intTransformedValue = getFrequencyIntVal(frequencySlider.getValue());

        displayMorseMessagesFromFrequency(intTransformedValue);
        if (!isTranslationHidden){
            displayEnglishMessagesFromFrequency(intTransformedValue);
        }
    }

    @FXML
    private void showTranslation() {
        englishMessagesVBox.getChildren().clear();
        int sliderValue = (int) frequencySlider.getValue();
        displayEnglishMessagesFromFrequency(sliderValue);
        isTranslationHidden = false;
    }

    @FXML
    private void hideTranslation() {
        englishMessagesVBox.getChildren().clear();
        isTranslationHidden = true;
    }

    @FXML
    private void playSound() {
        int sliderValue = (int) frequencySlider.getValue();
        ArrayList<String> morseTextList = getFrequencyEnglishList(sliderValue);
        Thread thread = new Thread(() -> {
            for (String morseText : morseTextList) {
                SoundProducer.setSpeeds(Integer.parseInt(characterSpeedSelection.getValue()), Integer.parseInt(effectiveSpeedSelection.getValue()));
                SoundProducer.produceSound(inputLine, morseText.split(":  ")[1], volume, Integer.parseInt(frequencySelection.getValue()));
            }
        });
        thread.start();
    }

    @FXML
    private void getVolume() {
        volume = (int) volumeSlider.getValue();
    }

    private void addMessageToFrequency(int sliderValue, String morseText, String englishText) {
        MorseFrequencies.get(sliderValue).add(morseText);
        EnglishFrequencies.get(sliderValue).add(englishText);

    }

    //Code from exam 1 (Chatter Box)
    private void addMessageToChatLogUI(String message, VBox vbox, ScrollPane scrollpane) {
        Label label = new Label(message);
        label.setWrapText(true);
        vbox.getChildren().add(label);
        inputSequence = new StringBuilder("");
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
        staticNoiceLine.close();
        App.switchToAddNewBotView();

    }

    @FXML
    private void playCurrentBot() throws LineUnavailableException {
        ScriptedBot botToPlay = botListView.getSelectionModel().getSelectedItem();
        if (botToPlay != null) {
            CWBotPlayer botPlayer = new CWBotPlayer(botToPlay);
            botPlayer.playBot();
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a bot to play first!").show();
        }
    }

    @FXML
    private void actionDeleteBot() {
        ScriptedBot botToDelete = botListView.getSelectionModel().getSelectedItem();
        if (botToDelete != null) {
            bots.remove(botToDelete);
            botListView.getItems().clear();
            botListView.getItems().addAll(bots);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a bot to delete first!").show();
        }
    }
}


