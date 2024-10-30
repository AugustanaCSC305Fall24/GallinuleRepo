package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

public class LevelPageController extends BasePage {

    @FXML
    private Button level1Button;

    @FXML
    private Button level2Button;

    @FXML
    private Button level3Button;

    @FXML
    private Button level4Button;

    @FXML
    private Button level5Button;
    private Object Level1;


    @FXML
    private void startLearning() {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource(Level1.fxml));
            //AnchorPane levelPage = loader.load();
            Stage stage = (Stage) level1Button.getScene().getWindow();
            //stage.setScene(new Scene(levelPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        level1Button.setOnAction(event -> openLevel(1));
        level2Button.setOnAction(event -> openLevel(2));
        level3Button.setOnAction(event -> openLevel(3));
        level4Button.setOnAction(event -> openLevel(4));
        level5Button.setOnAction(event -> openLevel(5));
    }

    private void openLevel(int level) {
        try {
            FXMLLoader loader;
            switch (level) {
                case 1:
                    loader = new FXMLLoader(getClass().getResource("Level1.fxml"));
                    break;
                case 2:
                    loader = new FXMLLoader(getClass().getResource("Level2.fxml"));
                    break;
                case 3:
                    loader = new FXMLLoader(getClass().getResource("Level3.fxml"));
                    break;
                case 4:
                    loader = new FXMLLoader(getClass().getResource("Level4.fxml"));
                    break;
                case 5:
                    loader = new FXMLLoader(getClass().getResource("Level5.fxml"));
                    break;
                default:
                    return; // Handle any invalid level
            }
            AnchorPane levelPage = loader.load();
            Stage stage = (Stage) level1Button.getScene().getWindow(); // Use any button to get the current stage
            stage.setScene(new Scene(levelPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
