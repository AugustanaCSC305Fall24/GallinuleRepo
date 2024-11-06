package edu.augustana.ui;

import edu.augustana.data.CwBotRecord;
import edu.augustana.data.ScriptedBot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddBotController {

    @FXML private TextField botName;
    @FXML private TextField message;
    @FXML private Slider charSpeed;
    @FXML private Slider effectiveSpeed;
    @FXML private Slider frequency;
    @FXML private Slider volume;



    @FXML
    public void initialize() {
    }

    @FXML
    private void finishAddingBotAction() {
        String botNameValue = botName.getText();
        String messageValue = message.getText();
        int charSpeedValue = (int) charSpeed.getValue();
        int effectiveSpeedValue = (int) effectiveSpeed.getValue();
        int frequencyValue = (int) frequency.getValue();
        int volumeValue = (int) volume.getValue();


        ScriptedBot newBot = new ScriptedBot(botNameValue, messageValue, charSpeedValue, effectiveSpeedValue, frequencyValue, volumeValue);
        MainPageController.bots.add(newBot);
        System.out.println(MainPageController.bots);
        App.switchToMainPage();
    }

    @FXML
    private void cancelAction(){
        App.switchToMainPage();
    }
}
