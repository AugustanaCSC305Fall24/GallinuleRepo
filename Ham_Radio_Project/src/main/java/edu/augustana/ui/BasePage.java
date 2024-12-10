package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.LineUnavailableException;
import java.util.List;
public class BasePage {

    @FXML
    protected ImageView helperImage;



    public void initialize() throws LineUnavailableException {
        System.out.println("BasePage initialize() called"); // Debugging line
        if (helperImage != null) {
            Image image = new Image(getClass().getResourceAsStream("MorseCodeImageHelper.png"));
            helperImage.setImage(image);
        } else {
            System.out.println("helperImage is null!");
        }
    }

    @FXML
    protected void showHelperImage() {
        helperImage.setVisible(!helperImage.isVisible());
    }
}
