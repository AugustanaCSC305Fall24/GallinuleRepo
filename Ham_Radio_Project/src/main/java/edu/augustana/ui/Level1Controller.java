package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class Level1Controller extends BasePage {

    private MorseCodeConverter morseConverter = new MorseCodeConverter(); // Initialize MorseCodeConverter
    private List<Character> currentLetterSet;
    private int currentLetterIndex;
    private char currentLetter;
    private String currentLetterMorse;
    String currentSentence = "";

    @FXML
    private Button backButton;
    @FXML
    private Label morseCodeLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Button resultLabel;
    @FXML
    private Label levelProgress;
    @FXML
    private Button revealButton;
    @FXML
    private Label sentenceLabel;
    private boolean isRevealed = false;

    // List of stages (letters will be shown in these sets)
    private List<List<Character>> stages = new ArrayList<>();

    @FXML
    public void initialize() {

        backButton.setOnAction(event -> goBack());
        // Initialize stages (group of letters for each level)
        stages.add(List.of('E', 'I', 'S', 'H'));   // Stage 1: Learn E, I, S, H
        stages.add(List.of('T', 'M', 'O'));         // Stage 2: Learn T, M, O
        stages.add(List.of('A', 'N', 'G', 'W', 'R', 'U', 'K', 'D', 'C', 'F', 'B')); // Stage 3: 10 more letters
        stages.add(List.of('L', 'J', 'Y', 'Q', 'Z', 'X', 'P', 'V')); // Stage 4: Last 7 letters

        // Start with the first stage
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;
        //generateNewLetter(); // Generate first letter for the user to type
    }

    @FXML
    private void goBack() {
        App.backToLevelPage();
    }

    private void generateNewLetter() {
        // Get the current letter from the set
        currentLetter = currentLetterSet.get(currentLetterIndex);
        currentLetterMorse = morseConverter.EnglishToMorse(String.valueOf(currentLetter)); // Get Morse code for the letter

        // Display the Morse code for the user to type
        //morseCodeLabel.setText(currentLetterMorse);
        levelProgress.setText("Level Progress:  1 / 5" + (currentLetterIndex + 1) + "/" + currentLetterSet.size());

        // Play sound of the Morse code using SoundProducer
        SoundProducer.produceSound(currentLetterMorse, 20, 60, 100, 600); // Play Morse code sound
    }

    @FXML
    private void revealMessage() {
        if (isRevealed) {
            morseCodeLabel.setText("");  // Clear Morse code display
            revealButton.setText("Reveal");
        } else {
            morseCodeLabel.setText(currentLetterMorse);  // Show correct message
            revealButton.setText("Hide");
        }
        isRevealed = !isRevealed;  // Toggle reveal state
    }


    // Add methods to play Morse code when a button is pressed
    @FXML
    private void playMorseCodeForLetter(char letter) {
        String letterMorse = String.valueOf(letter) + " ";
        SoundProducer.produceSound(letterMorse, 90, 700, 100, 700); // Play the Morse code sound for the letter

    }

    @FXML
    private void playMorseE() {
        playMorseCodeForLetter('E');
    }

    @FXML
    private void playMorseI() {
        playMorseCodeForLetter('I');
    }

    @FXML
    private void playMorseS() {
        playMorseCodeForLetter('S');
    }

    @FXML
    private void playMorseH() {
        playMorseCodeForLetter('H');
    }
    @FXML
    private void playSampleSentence() {
        currentSentence = "EISH EISH";
        for (char letter : currentSentence.toCharArray()) {
            if (letter != ' ') {
                playMorseCodeForLetter(letter);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void checkAnswer() {
        String userAnswer = answerField.getText().toUpperCase().replaceAll(" ", "");  // Clean input, removing spaces

        // Debugging logs to check the values
        System.out.println("Current Sentence (before comparison): " + currentSentence);  // Should print "EISH EISH"
        System.out.println("User's Input (after processing): " + userAnswer);  // Should print the cleaned user input

        if (userAnswer.equals("EISHEISH")) {
            resultLabel.setText("Correct!");
            answerField.clear();
        } else {
            resultLabel.setText("Listen and try again!");
            answerField.clear();
        }
    }
    @FXML
    private void showAnswer() {
        // Display the full sentence for the current level
        sentenceLabel.setText("Answer: EISH EISH");
        sentenceLabel.setStyle("-fx-background-color:white;");
        sentenceLabel.setAlignment(Pos.CENTER);
    }

}
