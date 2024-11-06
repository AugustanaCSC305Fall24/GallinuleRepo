package edu.augustana.ui;

import edu.augustana.data.CwBotLog;
import edu.augustana.data.CwBotRecord;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class scenarios {

    @FXML private ListView<CwBotRecord> CwBotsListView;

    /*@FXML
    private void menuActionOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CW Bot Log File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CW bot Logs (*.CwBotLog)", "*.CwBotLog");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = CwBotsListView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                App.loadCurrentMovieLogFromFile(chosenFile);
                CwBotsListView.getItems().clear();
                CwBotLog loadedLog = App.getCurrentCwBotLog();
                CwBotsListView.getItems().addAll(loadedLog.getCwBotRecords());
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading movie log file: " + chosenFile).show();
            }
        }
    }*/

    @FXML
    private void menuActionSave(ActionEvent event) {
        if (App.getCurrentCwBotLogFile() == null) {
            menuActionSaveAs(event);
        } else {
            saveCurrentMovieLogToFile(App.getCurrentCwBotLogFile());
        }
    }

    @FXML
    private void menuActionSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Movie Log File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Movie Logs (*.movielog)", "*.movielog");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = CwBotsListView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveCurrentMovieLogToFile(chosenFile);
    }

    private void saveCurrentMovieLogToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentCwBotLogToFile(chosenFile);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error saving movie log file: " + chosenFile).show();
            }
        }
    }


    @FXML
    void menuActionExitApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void menuActionHelpAbout(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Movie Tracker App, v1.0\nBy Dr. Stonedahl as a demo for his CSC 305 class").show();
    }

    /*@FXML
    private void actionOpenCwBotAddPage()  {
        App.switchToAddNewBotView();
    }*/
}
