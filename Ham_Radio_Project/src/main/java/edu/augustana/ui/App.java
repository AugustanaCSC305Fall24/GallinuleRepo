package edu.augustana.ui;

import com.google.gson.Gson;
import edu.augustana.data.CWMessage;
import edu.augustana.data.CwBotLog;
import edu.augustana.data.HamRadio;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import jakarta.websocket.*;

/**
 * JavaFX App
 */
@ClientEndpoint
public class App extends Application {

    private static App app;
    public static Scene scene;
    private static CwBotLog currentCwBotLog = new CwBotLog();
    private static File currentCwBotLogFile = null;
    private Session webSocketSession = null;
    private static Parent mainPageRoot = null;
    public static MainPageController mainPageController;

    public static App getApp() {
        return app;
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            App.app = this;
            scene = new Scene(loadFXML("/edu/augustana.ui/MainMenu"));
            stage.setScene(scene);
            stage.setTitle("Ham Radio Simulator");
            stage.show();
        } catch (IOException e) {
            showError("Error loading main menu: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        if (isConnectedToServer()) {
            try {
                webSocketSession.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void connectToServer(String serverIPAddress) {
        try {
            if (isConnectedToServer()) {
                app.webSocketSession.close();
            }
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            app.webSocketSession = container.connectToServer(app, new URI("ws://"+serverIPAddress+":8000/ws/"+HamRadio.theRadio.getUserName()));
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Error connecting to server! " + e.getMessage()).show());
        }
    }
    public static boolean isConnectedToServer() {
        return app.webSocketSession != null && app.webSocketSession.isOpen();
    }

    public static void sendMessageToServer(CWMessage msg) {
        if (isConnectedToServer()) {
            String jsonMessage = new Gson().toJson(msg);
            System.out.println("DEBUG: Sending WebSocket message: " + jsonMessage);
            app.webSocketSession.getAsyncRemote().sendText(jsonMessage);
        }
    }

    @OnMessage
    public void onMessage(String jsonMessage) {
        System.out.println("DEBUG: Received WebSocket message: " + jsonMessage);
        CWMessage chatMessage = new Gson().fromJson(jsonMessage, CWMessage.class);
        chatMessage.setFromRemoteClient(true);
        HamRadio.theRadio.receiveMessage(chatMessage);
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
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/AddBot.fxml"));
            Parent root = fxmlLoader.load();
            AddBotController addBotController = fxmlLoader.getController();
            addBotController.setMainPageController(mainPageController);
            scene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    static void initiateMainPage() {
//        try {
//
//            FXMLLoader mainPageLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/MainPage.fxml"));
//            scene.setRoot(mainPageLoader.load());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static void switchToMainPage() {
        try {
            if (mainPageRoot == null) {
                FXMLLoader mainPageLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/MainPage.fxml"));
                mainPageRoot = mainPageLoader.load();

                mainPageController = mainPageLoader.getController();
            }


            scene.setRoot(mainPageRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void switchToTestPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana.ui/TestPage.fxml"));
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
