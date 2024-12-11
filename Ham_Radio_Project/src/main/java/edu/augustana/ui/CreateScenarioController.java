package edu.augustana.ui;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class CreateScenarioController {

    @FXML
    private TextField botName;

    @FXML
    private TextField botFirstMessage;

    @FXML
    private TextField botSecondMessage;

    @FXML
    private TextField botThirdMessage;

    @FXML
    private TextField expectedFirstResponse;

    @FXML
    private TextField expectedSecondResponse;

    @FXML
    void createNewBot(ActionEvent event) throws IOException {
        JsonObject scenario = new JsonObject();
        scenario.addProperty("Name", botName.getText());
        scenario.addProperty("botFirstMessage", botFirstMessage.getText());
        scenario.addProperty("expectedFirstResponse", expectedFirstResponse.getText());
        scenario.addProperty("botSecondMessage", botSecondMessage.getText());
        scenario.addProperty("expectedSecondResponse", expectedSecondResponse.getText());
        scenario.addProperty("botThirdMessage", botThirdMessage.getText());

        FileWriter file = new FileWriter("bots.json");
        file.write(scenario.toString());
        System.out.println(scenario.toString());
        file.close();
        App.switchToScenarioPlayer();
    }

    @FXML
    private void goBack() {
        App.backToMainMenu();
    }

}
