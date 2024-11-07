package edu.augustana.ui;

import edu.augustana.data.CwBotLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static CwBotLog currentCwBotLog = new CwBotLog();
    private static File currentCwBotLogFile = null;


    @Override
    public void start(Stage stage) throws IOException {

        try {
            scene = new Scene(loadFXML("/edu/augustana.ui/MainMenu"));
            stage.setScene(scene);
            stage.setTitle("Ham Radio Simulator");
            stage.show();
        } catch (IOException e) {
            showError("Error loading main menu: " + e.getMessage());
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void showError(String message) {
        System.err.println(message);
    }

    public static CwBotLog getCurrentCwBotLog() {return currentCwBotLog;}

    public static File getCurrentCwBotLogFile() {
        return currentCwBotLogFile;
    }

    public static void loadCurrentMovieLogFromFile(File cwBotLogFile) throws IOException {
        currentCwBotLog = CwBotLog.loadFromFile(cwBotLogFile);
        currentCwBotLogFile = cwBotLogFile;
    }

    public static void saveCurrentCwBotLogToFile(File chosenFile) throws IOException {
        currentCwBotLog.saveToFile(chosenFile);
        currentCwBotLogFile = chosenFile;
    }

    static void switchToAddNewBotView() {
        try {
            System.out.println("hello");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/AddBot.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void switchToMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/MainPage.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void switchToLevelPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/LevelPage.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchToLevel1() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/Level1.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backToMainMenu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/MainMenu.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backToLevelPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/LevelPage.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
