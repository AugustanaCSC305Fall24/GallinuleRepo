package edu.augustana.ui;

import edu.augustana.data.ScriptedBot;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AddBotController {

    @FXML private TextField botName;
    @FXML private TextField message;
    @FXML private Slider frequency;
    @FXML private Slider repeats;
    @FXML public ComboBox<String> effectiveSpeedSelection;

    private MainPageController mainPageController;
    public static final String[] EFFECTIVE_SPEED = {"5", "10", "15", "18", "20"};


    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }


    @FXML
    public void initialize() {
        effectiveSpeedSelection.getItems().addAll(List.of(EFFECTIVE_SPEED));
        effectiveSpeedSelection.setValue(EFFECTIVE_SPEED[0]);
    }

    @FXML
    private void finishAddingBotAction() {
        String botNameValue = botName.getText();
        String messageValue = message.getText();
        int effectiveSpeedValue = Integer.parseInt(effectiveSpeedSelection.getValue());
        double frequencyValue =  frequency.getValue();
        int repeatsValue = (int) repeats.getValue();

        ScriptedBot newBot = new ScriptedBot(botNameValue, messageValue, effectiveSpeedValue, frequencyValue, repeatsValue);
        MainPageController.bots.add(newBot);

        mainPageController.botListView.getItems().clear();
        mainPageController.addBotsToView();

        SoundProducer.openStaticLine();
        Thread thread2 = new Thread(SoundProducer::playStaticNoise);
        thread2.start();
        App.switchToMainPage();
    }

    @FXML
    private void cancelAction(){
        SoundProducer.openStaticLine();
        Thread thread2 = new Thread(SoundProducer::playStaticNoise);
        thread2.start();
        App.switchToMainPage();
    }
}
