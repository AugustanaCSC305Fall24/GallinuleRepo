module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires com.opencsv;
    requires javafx.media;
    requires tyrus.standalone.client;

    requires java.net.http;
    requires swiss.ameri.gemini.api;
    requires swiss.ameri.gemini.gson;
    requires java.logging;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
    exports edu.augustana.ui;
    opens edu.augustana.ui to javafx.fxml;
    opens edu.augustana.data to com.google.gson;
    exports edu.augustana.sound;
    opens edu.augustana.sound to javafx.fxml;
}
