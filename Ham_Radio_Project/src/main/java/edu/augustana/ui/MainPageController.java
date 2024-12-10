package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.data.CWMessage;
import edu.augustana.data.CwBotRecord;
import edu.augustana.data.HamRadio;
import edu.augustana.data.ScriptedBot;
import edu.augustana.sound.CWBotPlayer;
import edu.augustana.sound.SoundProducer;
import javafx.application.Platform;
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


public class MainPageController extends BasePage {

    @FXML public ListView<ScriptedBot> botListView;
    @FXML private VBox morseMessagesVBox, englishMessagesVBox;
    @FXML private ScrollPane morseMessagesScrollPane, englishMessagesScrollPane;
    @FXML private Label morseInput = new Label();
    @FXML public Slider volumeSlider;
    @FXML private Slider frequencySlider, rangeSlider;
    @FXML public ComboBox<String> effectiveSpeedSelection;
    @FXML private ListView<CwBotRecord> CwBotsListView;
    @FXML private Button helperBtn;

    private final MorseCodeConverter converter = new MorseCodeConverter();
    private Boolean isTranslationHidden = true;
    public static final String[] FREQUENCIES = {"200", "300", "400", "500", "600", "700", "800", "900"};
    public static final String[] EFFECTIVE_SPEED = {"5", "10", "15", "18", "20"};
    private int volume = 50;
    public static List<ScriptedBot> bots = new ArrayList<>();
    private StringBuilder inputSequence = new StringBuilder();
    private Timeline timeline;

    private static Map<Integer, ArrayList<String>> EnglishFrequencies = new HashMap<>();
    public static Map<Integer, ArrayList<String>> MorseFrequencies = new HashMap<>();

    @Override
    public void initialize() throws LineUnavailableException {
        HamRadio.theRadio.setMessageReceivedListener(msg -> handleNewMessage(msg));
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

        effectiveSpeedSelection.getItems().addAll(List.of(EFFECTIVE_SPEED));
        effectiveSpeedSelection.setValue(EFFECTIVE_SPEED[0]);
    }

    private void initializeSoundLine()  {
        SoundProducer.openInputLine();
        SoundProducer.openPlaysoundLine();
    }

    public void addBotsToView(){
        botListView.getItems().addAll(bots);
    }

    private void setupKeyEventHandler() {
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    private void startNoInputTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleNoInput()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //HamRadio.theRadio.setMessageReceivedListener(msg -> System.out.println("we'd like to play this message as SOUND: " + msg));
        frequencySlider.valueProperty().addListener((obs, oldVal, newVal) -> HamRadio.theRadio.setFrequency(newVal.doubleValue()));
        rangeSlider.valueProperty().addListener((obs, oldVal, newVal) -> HamRadio.theRadio.setRange(newVal.doubleValue()));
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
        SoundProducer.closeStaticLine();
        App.backToMainMenu();
    }

    private void handleKeyPress(KeyCode code) {
        resetTimeline();
        if (code == KeyCode.N) {
            inputSequence.append(".");
            SoundProducer.playSendingDit((int) volumeSlider.getValue());

        } else if (code == KeyCode.M) {
            inputSequence.append("-");
            SoundProducer.playSendingDah((int) volumeSlider.getValue());

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

    public int getCurrentFrequencyIntVal() {
        double transformedValue = (frequencySlider.getValue() - 7) * 1000;
        return (int) Math.round(transformedValue);
    }

    public int getFrequencyIntVal(double frequencyValue) {
        double transformedValue = (frequencyValue - 7) * 1000;
        return (int) Math.round(transformedValue);
    }

    public int getCurrentRange() {
        //System.out.println("Debug: range slider" + rangeSlider.getMax());
        double transformedValue = (rangeSlider.getValue() * 1000);
        return (int) Math.round(transformedValue);
    }

    @FXML
    private void writeToFrequency() {
        int rangeValue = getCurrentRange();
        System.out.println(rangeValue);
        morseMessagesVBox.getChildren().clear();
        String morseText = morseInput.getText();
        String englishText = converter.MorseToEnglish(morseText);
        int intTransformedValue = getCurrentFrequencyIntVal();

        CWMessage message = new CWMessage(HamRadio.theRadio.getUserName(), morseText, intTransformedValue);
        HamRadio.theRadio.sendMessageFromHumanUser(message);



        writeMessages(intTransformedValue, morseText, englishText);
//        for (int i = 1; i < rangeValue; i++ ) {
//            writeMessages(intTransformedValue + i, morseText, englishText);
//            writeMessages(intTransformedValue - i, morseText, englishText);
//        }

        displayMorseMessagesFromFrequency(intTransformedValue);
        morseInput.setText("");
        inputSequence = new StringBuilder();
    }

    private void writeMessages(int sliderValue, String morseText, String englishText) {
        if (sliderValue >= getFrequencyIntVal(frequencySlider.getMin()) && sliderValue <= getFrequencyIntVal(frequencySlider.getMax())) {
            addMessageToFrequency(sliderValue, HamRadio.theRadio.getUserName() + ":  " + morseText, HamRadio.theRadio.getUserName() + ":  " + englishText + " ");

            //addMessageToFrequency(sliderValue, "Bot:  " + converter.EnglishToMorse(ChatBot.getResponse(englishText)), "Bot:  " + ChatBot.getResponse(englishText));
        }
    }

    private void handleNewMessage(CWMessage msg) {
        HamRadio.theRadio.setSoundVariables(Integer.parseInt(effectiveSpeedSelection.getValue()), (int) volumeSlider.getValue(), 600);
        if (!msg.isFromRemoteClient()) {
            App.sendMessageToServer(msg);
        }
        Platform.runLater(()->handleRemoteMessage(msg));
    }

    private void handleRemoteMessage(CWMessage msg) {
        addMessageToFrequency((int) msg.getFrequency(),  msg.getSender() + ":  " + msg.getMorseMessageText(), msg.getSender() + ":  " + converter.MorseToEnglish(msg.getMorseMessageText()) + " ");
        addMessageToChatLogUI(msg.getSender() + msg.getMorseMessageText(), morseMessagesVBox, morseMessagesScrollPane);
    }
//    @FXML
//    private void sendAction() {
//        String msgText = morseInput.getText();
//        if (!msgText.isBlank()) {
//            CWMessage newMessageFromUser = new CWMessage("New sender", msgText, frequencySlider.getValue());
//            HamRadio.theRadio.sendMessage(newMessageFromUser);
//            morseInput.setText("");
//        }
//    }

    @FXML
    private void displayFrequency() {
        morseMessagesVBox.getChildren().clear();
        englishMessagesVBox.getChildren().clear();
        int intTransformedValue = getCurrentFrequencyIntVal();


        displayMorseMessagesFromFrequency(intTransformedValue);
        if (!isTranslationHidden){
            displayEnglishMessagesFromFrequency(intTransformedValue);
        }
        int rangeValue = getCurrentRange();
        for (int i = 1; i <= rangeValue; i++) {
            displayMorseMessagesFromFrequency(intTransformedValue + i);
            displayMorseMessagesFromFrequency(intTransformedValue - i);
            if (!isTranslationHidden){
                displayEnglishMessagesFromFrequency(intTransformedValue + i);
                displayEnglishMessagesFromFrequency(intTransformedValue - i);
            }
        }
    }

    @FXML
    private void showTranslation() {
        englishMessagesVBox.getChildren().clear();
        int intTransformedValue = getCurrentFrequencyIntVal();
        displayEnglishMessagesFromFrequency(intTransformedValue);
        isTranslationHidden = false;
    }

    @FXML
    private void hideTranslation() {
        englishMessagesVBox.getChildren().clear();
        isTranslationHidden = true;
    }

    @FXML
    private void playSound() {
        int transformedValue = getCurrentFrequencyIntVal();

        int rangeValue = getCurrentRange();
        Thread thread = new Thread(() -> {
            ArrayList<String> morseTextList = getFrequencyMorseList(transformedValue);
            for (String morseText : morseTextList) {
                SoundProducer.playSoundLine(morseText.split(":  ")[1], (int) volumeSlider.getValue(), Integer.parseInt(effectiveSpeedSelection.getValue()), 600);
            }
            for (int i = 1; i <= rangeValue; i++) {
                morseTextList = getFrequencyMorseList(transformedValue + i);
                ArrayList<String> morseTextList2 = getFrequencyMorseList(transformedValue - i);
                if (morseTextList != null) {
                    for (String morseText : morseTextList) {
                        SoundProducer.playSoundLine(morseText.split(":  ")[1], (int) volumeSlider.getValue(), Integer.parseInt(effectiveSpeedSelection.getValue()), 600 + 20 * i);
                    }
                }

                if (morseTextList2 != null) {
                    for (String morseText : morseTextList2) {
                        SoundProducer.playSoundLine(morseText.split(":  ")[1], (int) volumeSlider.getValue(), Integer.parseInt(effectiveSpeedSelection.getValue()), 600 - 20 * i);
                    }
                }


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
    private void openCwBotAddPage() {

        SoundProducer.closeStaticLine();
        App.switchToAddNewBotView();

    }

    @FXML
    private void playCurrentBot() {
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


