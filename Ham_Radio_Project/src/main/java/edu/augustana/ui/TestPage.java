package edu.augustana.ui;
import edu.augustana.MorseCodeConverter;
import edu.augustana.data.GeminiAIBot;
import edu.augustana.data.HamRadio;
import edu.augustana.sound.SoundProducer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TestPage {

    private StringBuilder chat;
    @FXML
    private TextArea chatLog;

    @FXML
    private TextField textField;

    private Thread checkerThread;

    private MorseCodeConverter converter = new MorseCodeConverter();

    @FXML
    void initialize() {
        GeminiAIBot bot = new GeminiAIBot("HAM", 600, HamRadio.theRadio);
        chat = new StringBuilder();
        textField.setOnAction(event -> {
            String message = textField.getText();
            if (!message.isBlank()) {
                chat.append("User: " + message + "\n");
                textField.clear();
                chatLog.appendText("User: " + converter.EnglishToMorse(message) + "\n");
                System.out.println(chat.toString());
                bot.requestMessage(chat);
            }
        });

        checkerThread = new Thread(() -> {
            while (true) {
                if (bot.messageReceived) {
                    chat.append(bot.getName() + ": " + bot.geminiResponse);
                    Platform.runLater(() -> chatLog.appendText(bot.getName() + ": " + converter.EnglishToMorse(bot.geminiResponse)  + "\n"));
                    bot.messageReceived = false;
                    Thread thread = new Thread(() -> {
                        SoundProducer.produceSound(converter.EnglishToMorse(bot.geminiResponse), 18, 50, 600);
                    });
                    thread.start();
                }

                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkerThread.setDaemon(true); // Ensure the thread stops when the app closes
        checkerThread.start();
    }

    @FXML
    private void goBack() {
        App.backToMainMenu();
    }

}
