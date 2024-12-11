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

        try (FileWriter file = new FileWriter("bots.txt", true)) {
            file.write(scenario.toString() + "\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.switchToScenarioPlayer();
    }

    @FXML
    private void goBack() {
        App.backToMainMenu();
    }

    @FXML
    private void goToBotPage() {
        App.switchToScenarioPlayer();
    }

}
