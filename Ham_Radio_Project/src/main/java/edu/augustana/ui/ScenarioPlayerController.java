package edu.augustana.ui;
import edu.augustana.MorseCodeConverter;
import edu.augustana.data.HamRadio;
import edu.augustana.data.ScenarioBot;
import edu.augustana.sound.SoundProducer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static edu.augustana.ui.App.scene;

public class ScenarioPlayerController {

    private StringBuilder chat;
    @FXML
    private TextArea chatLog;

    @FXML
    private TextField textField;

    @FXML
    private ListView<ScenarioBot> botListView;

    private ArrayList<ScenarioBot> botList = new ArrayList<>();
    private StringBuilder inputSequence = new StringBuilder();
    private Timeline timeline;
    private MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    void initialize() {
        createBot();
        setupKeyEventHandler();
        startNoInputTimeline();
        // create a wait point for user to click on a bot
        AtomicReference<ScenarioBot> bot = new AtomicReference<>();
        bot.set(botListView.getItems().get(0));
        botListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bot.set(botListView.getSelectionModel().getSelectedItem());
                chatLog.appendText(bot.get().getBotName() + ": " + bot.get().getFirstMessage() + "\n");
                chat.append(bot.get().getBotName() + ": " + bot.get().getFirstMessage() + "\n");
                Thread thread = new Thread(() -> {
                    SoundProducer.produceSound(bot.get().getFirstMessage(), 18, 50, 600);
                });
                thread.start();
            }
        });
        chat = new StringBuilder();
        AtomicReference<String> nextMessage = new AtomicReference<>(bot.get().nextBotMessage());
        chat.append(bot.get().getBotName() + ": " + nextMessage + "\n");
        chatLog.appendText(bot.get().getBotName() + ": " + nextMessage + "\n");
        textField.setOnAction(event -> {
            String message = textField.getText();
            if (!message.isBlank()) {
                chat.append("User: " + message + "\n");
                textField.clear();
                inputSequence = new StringBuilder();
                chatLog.appendText("User: " + message + "\n");
                System.out.println(chat.toString());
                if (bot.get().isExpectedMessage(message)) {
                    nextMessage.set(bot.get().nextBotMessage());
                    chatLog.appendText(bot.get().getBotName() + ": " + nextMessage + "\n");
                    chat.append(bot.get().getBotName() + ": " + nextMessage + "\n");
                    Thread thread = new Thread(() -> {
                        SoundProducer.produceSound(String.valueOf(nextMessage), 18, 50, 600);
                    });
                    thread.start();
                } else {
                    chatLog.appendText(bot.get().getBotName() + ": " + converter.EnglishToMorse("Sorry, I didn't understand") + "\n");
                    chat.append(bot.get().getBotName() + ": " + converter.EnglishToMorse("Sorry, I didn't understand") + "\n");
                    Thread thread = new Thread(() -> {
                        SoundProducer.produceSound(converter.EnglishToMorse("Sorry, I didn't understand"), 18, 50, 600);
                    });
                    thread.start();
                }
            }
        });
    }

    void createBot() {
        String filePath = "bots.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line + "\n");
            }
            for (String singleBot: jsonContent.toString().split("\n")) {
                botList.add(new ScenarioBot(new StringBuilder(singleBot)));
            }
            botListView.getItems().addAll(botList);

        }
            catch(IOException e) {
                e.printStackTrace();
            }
    }

    private void setupKeyEventHandler() {
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    private void startNoInputTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> handleNoInput()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //HamRadio.theRadio.setMessageReceivedListener(msg -> System.out.println("we'd like to play this message as SOUND: " + msg));
    }

    private void handleKeyPress(KeyCode code) {
        resetTimeline();
        if (code == KeyCode.N) {
            inputSequence.append(".");
            SoundProducer.playSendingDit((int) 50);

        } else if (code == KeyCode.M) {
            inputSequence.append("-");
            SoundProducer.playSendingDah((int) 50);

        }
    }

    private void resetTimeline() {
        timeline.stop();
        timeline.playFromStart();
    }

    private void handleNoInput() {
        if (!inputSequence.toString().isEmpty()) {
            inputSequence.append(" ");
            textField.setText(inputSequence.toString());
        }
    }

    @FXML
    private void goBack() {
        App.switchToScenarioPage();
    }

}
