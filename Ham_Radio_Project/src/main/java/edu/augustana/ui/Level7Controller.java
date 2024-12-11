package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.Random;

public class Level7Controller extends LevelClassController {

    @FXML
    private Button nextLevelButton;
    @FXML
    private Button checkAnswerButton, revealAnswerButton, playMorseButton, sendButton;

    @FXML
    private TextField answerField;

    @FXML
    private Label sentenceLabel;

    private char currentLetter;

    @FXML
    private Label  morseInputLabel;
    private String morseCodeForCurrentLetter;

    @Override
    public void initialize() {
        super.initialize();
        stages.add(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
        currentLetterSet = stages.get(0);
    }

    @FXML
    private void playMorseLetter() {
        currentLetter = generateRandomLetter();
        morseCodeForCurrentLetter = morseConverter.EnglishToMorse(String.valueOf(currentLetter));
        playMorseCodeForLetter(currentLetter);
        displayLetterInstruction(currentLetter);
    }

    private char generateRandomLetter() {
        Random random = new Random();
        return (char) currentLetterSet.get(random.nextInt(currentLetterSet.size()));
    }

    private void displayLetterInstruction(char letter) {
        sentenceLabel.setText("Listen to the letter and input its Morse code!");
        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
    }


    private void addToInputSequence(String input) {
        inputSequence.append(input);
        updateMorseInputLabel();
    }

    private void updateMorseInputLabel() {
        morseInputLabel.setText(inputSequence.toString());
    }

    @FXML
    private void sendInput() {
        String userInput = getInputSequence(); // Get Morse code entered by 'N' and 'M' keys
        answerField.setText(userInput); // Display in the input field
        resetInputSequence();
        //answerField.setText(inputSequence.toString()); // Copy the sequence to the answer field
        //resetInputSequence(); // Clear the sequence
    }

    protected void resetInputSequence() {
        inputSequence.setLength(0); // Clear the sequence
        updateMorseInputLabel(); // Update the label
    }


    @FXML
    protected void revealAnswer() {
        sentenceLabel.setText("The correct Morse code: " + morseCodeForCurrentLetter);
        sentenceLabel.setText("The correct Morse code: " + morseCodeForCurrentLetter + " (" + currentLetter + ")");
        sentenceLabel.setStyle("-fx-background-color: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
    }

    @FXML
    protected void checkAnswer() {
        String userAnswer = answerField.getText().trim();
        if (userAnswer.equals(morseCodeForCurrentLetter)) {
            sentenceLabel.setText("Correct!");
            sentenceLabel.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        } else {
            sentenceLabel.setText("Incorrect. Try again.");
            sentenceLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void goToLevel8() {
        App.switchToLevel8();
    }

}