package edu.augustana.ui;
import edu.augustana.data.ScenarioBot;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ScenarioPlayerController {

    private StringBuilder chat;
    @FXML
    private TextArea chatLog;

    @FXML
    private TextField textField;

    private Thread checkerThread;

    private ScenarioBot bot = null;
    @FXML
    void initialize() {
        // create the bot
        createBot();
        chat = new StringBuilder();
        AtomicReference<String> nextMessage = new AtomicReference<>(this.bot.nextBotMessage());
        chat.append(this.bot.getBotName() + ": " + nextMessage + "\n");
        chatLog.appendText(this.bot.getBotName() + ": " + nextMessage + "\n");
        textField.setOnAction(event -> {
            String message = textField.getText();
            if (!message.isBlank()) {
                chat.append("User: " + message + "\n");
                textField.clear();
                chatLog.appendText("User: " + message + "\n");
                System.out.println(chat.toString());
                if (this.bot.isExpectedMessage(message)) {
                    nextMessage.set(this.bot.nextBotMessage());
                    chatLog.appendText(this.bot.getBotName() + ": " + nextMessage + "\n");
                    chat.append(this.bot.getBotName() + ": " + nextMessage + "\n");
                } else {
                    chatLog.appendText(this.bot.getBotName() + ": " + "Sorry, I didn't understand" + "\n");
                    chat.append(this.bot.getBotName() + ": " + "Sorry, I didn't understand" + "\n");
                }
            }
        });
    }

    void createBot() {
        String filePath = "bots.json";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            this.bot = new ScenarioBot(jsonContent);
        }
            catch(IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void goBack() {
        App.switchToScenarioPage();
    }

}
