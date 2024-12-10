package edu.augustana.ui;
import edu.augustana.data.GeminiAIBot;
import edu.augustana.data.HamRadio;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TestPage {

    private StringBuilder chat;
    @FXML
    private TextArea chatLog;

    @FXML
    private TextField textField;

    private Thread checkerThread;

    @FXML
    void initialize() {
        GeminiAIBot bot = new GeminiAIBot("HAM", 600, HamRadio.theRadio);
        chat = new StringBuilder();
        textField.setOnAction(event -> {
            String message = textField.getText();
            if (!message.isBlank()) {
                chat.append("User: " + message + "\n");
                textField.clear();
                chatLog.appendText("User: " + message + "\n");
                System.out.println(chat.toString());
                bot.requestMessage(chat);
            }
        });

        checkerThread = new Thread(() -> {
            while (true) {
                if (bot.messageReceived) {
                    chat.append(bot.getName() + ": " + bot.geminiResponse);
                    Platform.runLater(() -> chatLog.appendText(bot.getName() + ": " + bot.geminiResponse));
                    bot.messageReceived = false;
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
