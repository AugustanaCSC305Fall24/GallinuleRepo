package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.util.List;


public class Level6Controller extends LevelClassController {

    @FXML
    private Button  nextLevelButton;

    private List<String> predefinedPhrases;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of("YL", "XYL", "OM", "LID", "73", "88", "SOS", "EEEEEEEEE", "QTH", "QRZ", "QSO", "QRU", "QSB", "QRQ",
                "QRS", "QSY", "QRM", "QST"));
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;

        predefinedPhrases = List.of(
                "YL QTH QRZ",
                "OM QSO QSB",
                "SOS QTH QRM",
                "QRZ QSY QRS",
                "XYL 73 88",
                "QSO QTH QRU",
                "OM QRQ QST"
        );

        backButton.setOnAction(event -> goBack());
    }


    @FXML
    private void playMorsePhrase() {
        generatePredefinedPhrase();
        for (String part : currentGeneratedPhrase.split(" ")) {
            playMorseCodeForLetter(part);
        }
    }
    private void generatePredefinedPhrase() {
        currentGeneratedPhrase = predefinedPhrases.get(random.nextInt(predefinedPhrases.size()));
        System.out.println("Selected Phrase: " + currentGeneratedPhrase); // Debugging log
    }

    @FXML
    protected void checkAnswer() {
        String userAnswer = answerField.getText().toUpperCase().replaceAll(" ", ""); // Clean input
        String expectedAnswer = currentGeneratedPhrase.replaceAll(" ", "");

        if (userAnswer.equals(expectedAnswer)) {
            resultLabel.setText("Correct!");
        } else {
            resultLabel.setText("Incorrect. Try again.");
        }
        answerField.clear();
    }

    @FXML
    protected void showAnswer() {
        sentenceLabel.setText(" Answer: " + currentGeneratedPhrase);
        sentenceLabel.setStyle("-fx-background-color:white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
        sentenceLabel.setAlignment(Pos.CENTER);
    }
    @FXML
    private void playMorseYL() {
        playMorseCodeForLetter("YL");
    }

    @FXML
    private void playMorseXYL() {
        playMorseCodeForLetter("XYL");
    }

    @FXML
    private void playMorseOM() {
        playMorseCodeForLetter("OM");
    }

    @FXML
    private void playMorseLID() {
        playMorseCodeForLetter("LID");
    }

    @FXML
    private void playMorse73() {
        playMorseCodeForLetter("73");
    }

    @FXML
    private void playMorse88() {
        playMorseCodeForLetter("88");
    }

    @FXML
    private void playMorseSOS() {
        playMorseCodeForLetter("SOS");
    }
    @FXML
    private void playMorseEEEEEEEE() {
        playMorseCodeForLetter("EEEEEEEE");
    }

    @FXML
    private void playMorseQTH() {
        playMorseCodeForLetter("QTH");
    }

    @FXML
    private void playMorseQRZ() {
        playMorseCodeForLetter("QRZ");
    }

    @FXML
    private void playMorseQSO() {
        playMorseCodeForLetter("QSO");
    }

    @FXML
    private void playMorseQRU() {
        playMorseCodeForLetter("QRU");
    }

    @FXML
    private void playMorseQSB() {
        playMorseCodeForLetter("QSB");
    }
    @FXML
    private void playMorseQRQ() {
        playMorseCodeForLetter("QRQ");
    }

    @FXML
    private void playMorseQRS() {
        playMorseCodeForLetter("QRS");
    }

    @FXML
    private void playMorseQSY() {
        playMorseCodeForLetter("QSY");
    }
    @FXML
    private void playMorseQRM() {
        playMorseCodeForLetter("QRM");
    }

    @FXML
    private void playMorseQST() {
        playMorseCodeForLetter("QST");
    }

    @FXML
    private void goToLevel7() {
        App.switchToLevel7();
    }

}
