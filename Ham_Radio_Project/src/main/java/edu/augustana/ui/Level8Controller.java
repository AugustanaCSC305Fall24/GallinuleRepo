//package edu.augustana.ui;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//public class Level8Controller extends LevelClassController {
//
//    @FXML
//    private Button playMorseButton, checkAnswerButton, revealAnswerButton, sendButton;
//
//    @FXML
//    private Label sentenceLabel, morseInputLabel;
//
//    @FXML
//    private TextField answerField;
//
//    private String currentPhraseOrAbbreviation;
//    private String morseCodeForCurrentPhraseOrAbbreviation;
//
//    private final List<String> abbreviations = List.of(
//            "YL", "XYL", "OM", "LID", "73", "88", "SOS", "QTH", "QRZ", "QSO", "QRU", "QSB", "QRQ", "QRS", "QSY", "QRM", "QST"
//    );
//
//    private final List<String> predefinedPhrases = List.of(
//            "YL QTH QRZ",
//            "OM QSO QSB",
//            "SOS QTH QRM",
//            "QRZ QSY QRS",
//            "XYL 73 88",
//            "QSO QTH QRU",
//            "OM QRQ QST"
//    );
//
//    private final Random random = new Random();
//
//    @Override
//    public void initialize() {
//        super.initialize();
//        stages.add(Collections.singletonList(abbreviations));
//    }
//
//    @FXML
//    private void playRandomPhraseOrAbbreviation() {
//        if (random.nextBoolean()) {
//            // Select a random abbreviation
//            currentPhraseOrAbbreviation = abbreviations.get(random.nextInt(abbreviations.size()));
//        } else {
//            // Select a random predefined phrase
//            currentPhraseOrAbbreviation = predefinedPhrases.get(random.nextInt(predefinedPhrases.size()));
//        }
//
//        morseCodeForCurrentPhraseOrAbbreviation = morseConverter.EnglishToMorse(currentPhraseOrAbbreviation);
//        playMorseCodeForPhraseOrAbbreviation();
//        displayInstruction();
//    }
//
//    private void playMorseCodeForPhraseOrAbbreviation() {
//        for (String part : currentPhraseOrAbbreviation.split(" ")) {
//            playMorseCodeForLetter(part);
//        }
//    }
//
//    private void displayInstruction() {
//        sentenceLabel.setText("Listen to the abbreviation/phrase and input its Morse code!");
//        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
//    }
//
//    @FXML
//    private void sendInput() {
//        String userInput = getInputSequence();
//        answerField.setText(userInput); // Show the Morse input in the text field
//        resetInputSequence(); // Reset the Morse input sequence
//    }
//
//    @FXML
//    protected void checkAnswer() {
//        String userAnswer = answerField.getText().trim();
//        if (userAnswer.equals(morseCodeForCurrentPhraseOrAbbreviation)) {
//            sentenceLabel.setText("Correct!");
//            sentenceLabel.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
//        } else {
//            sentenceLabel.setText("Incorrect. Try again.");
//            sentenceLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
//        }
//    }
//
//    @FXML
//    protected void revealAnswer() {
//        sentenceLabel.setText("Correct Morse code: " + morseCodeForCurrentPhraseOrAbbreviation + " (" + currentPhraseOrAbbreviation + ")");
//        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
//    }
//}

package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Level8Controller extends LevelClassController {

    @FXML
    private Button playMorseButton, checkAnswerButton, revealAnswerButton, sendButton;

    @FXML
    private Label sentenceLabel, morseInputLabel;

    @FXML
    private TextField answerField;

    private String currentPhraseOrAbbreviation;
    private String morseCodeForCurrentPhraseOrAbbreviation;

    private final List<String> abbreviations = List.of(
            "YL", "XYL", "OM", "LID", "73", "88", "SOS", "QTH", "QRZ", "QSO", "QRU", "QSB", "QRQ", "QRS", "QSY", "QRM", "QST"
    );

    private final List<String> predefinedPhrases = List.of(
            "YL QTH QRZ",
            "OM QSO QSB",
            "SOS QTH QRM",
            "QRZ QSY QRS",
            "XYL 73 88",
            "QSO QTH QRU",
            "OM QRQ QST"
    );

    private final Random random = new Random();

    @Override
    public void initialize() {
        super.initialize();
        stages.add(Collections.singletonList(abbreviations));
    }

    @FXML
    private void playRandomPhraseOrAbbreviation() {
        if (random.nextBoolean()) {
            // Select a random abbreviation
            currentPhraseOrAbbreviation = abbreviations.get(random.nextInt(abbreviations.size()));
        } else {
            // Select a random predefined phrase
            currentPhraseOrAbbreviation = predefinedPhrases.get(random.nextInt(predefinedPhrases.size()));
        }

        morseCodeForCurrentPhraseOrAbbreviation = morseConverter.EnglishToMorse(currentPhraseOrAbbreviation);
        playMorseCodeForPhraseOrAbbreviation();
        displayInstruction();
    }

    private void playMorseCodeForPhraseOrAbbreviation() {
        playMorseCodeForLetter(currentPhraseOrAbbreviation);
    }

    private void displayInstruction() {
        sentenceLabel.setText("Listen to the abbreviation/phrase and input its Morse code!");
        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
    }

    @FXML
    private void sendInput() {
        String userInput = getInputSequence();
        answerField.setText(userInput); // Show the Morse input in the text field
        resetInputSequence(); // Reset the Morse input sequence
    }

    @FXML
    protected void checkAnswer() {
        // Clean user input and expected Morse code by removing spaces
        String userAnswer = answerField.getText().trim().replaceAll(" ", "");
        String expectedAnswer = morseCodeForCurrentPhraseOrAbbreviation.replaceAll(" ", "");

        if (userAnswer.equals(expectedAnswer)) {
            sentenceLabel.setText("Correct!");
            sentenceLabel.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        } else {
            sentenceLabel.setText("Incorrect. Try again.");
            sentenceLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        }
    }

    @FXML
    protected void revealAnswer() {
        sentenceLabel.setText("Correct Morse code: " + morseCodeForCurrentPhraseOrAbbreviation + " (" + currentPhraseOrAbbreviation + ")");
        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
    }
}
