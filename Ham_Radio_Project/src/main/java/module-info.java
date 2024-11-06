module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires com.opencsv;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
    exports edu.augustana.ui;
    opens edu.augustana.ui to javafx.fxml;
    opens edu.augustana.data to com.google.gson;
    exports edu.augustana.sound;
    opens edu.augustana.sound to javafx.fxml;
}
