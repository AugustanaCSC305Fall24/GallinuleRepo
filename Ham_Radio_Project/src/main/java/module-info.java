module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
    exports edu.augustana.ui;
    opens edu.augustana.ui to javafx.fxml;
}
