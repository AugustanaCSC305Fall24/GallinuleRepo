package edu.augustana.ui;

import edu.augustana.data.GeminiAIBot;
import edu.augustana.data.HamRadio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BotCreation {

    @FXML
    private Button backBtn;

    @FXML
    private TextField botNameTextField;

    @FXML
    private Label botPromptTextField;

    @FXML
    private Button createBotButton;

    @FXML
    void createBot(ActionEvent event) {
        GeminiAIBot newBot = new GeminiAIBot(botNameTextField.getText(), botPromptTextField.getText(), HamRadio.theRadio);
        HamRadio.theRadio.botsList.add(newBot);
        App.switchToTestPage();
    }

    @FXML
    private void goBack() {
        App.backToMainMenu();
    }

}
