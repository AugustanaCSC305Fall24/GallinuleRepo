package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class LevelClassController extends BasePage {

    protected MorseCodeConverter morseConverter = new MorseCodeConverter();
    protected List<Object> currentLetterSet; // Can now hold both Characters and Strings
    protected int currentLetterIndex;
    protected String currentGeneratedPhrase = "";
    protected Random random = new Random();
    protected MorseCodeConverter converter = new MorseCodeConverter();

    protected StringBuilder inputSequence = new StringBuilder();
    @FXML
    protected Button backButton;
    @FXML
    protected Label morseCodeLabel;
    @FXML
    protected TextField answerField;
    @FXML
    protected Button resultLabel;
    @FXML
    protected Button revealButton;
    @FXML
    protected Label sentenceLabel;

    private boolean isRevealed = false;

    protected List<List<Object>> stages = new ArrayList<>(); // Updated to hold mixed types

    @FXML
    protected Label morseInput;

    @Override
    public void initialize() {
        // Initialization logic
        SoundProducer.openInputLine();
        setupKeyHandler();
    }
    private void setupKeyHandler() {
        App.scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    protected void handleKeyPress(KeyCode code) {
        if (code == KeyCode.N) {
            inputSequence.append(".");
            SoundProducer.playSendingDit(50); // Play the sound for dot
        } else if (code == KeyCode.M) {
            inputSequence.append("-");
            SoundProducer.playSendingDah(50); // Play the sound for dash
        }
    }
    protected void resetInputSequence() {
        inputSequence.setLength(0);
    }
    protected String getInputSequence() {
        return inputSequence.toString().trim();
    }


    @FXML
    protected void goBack() {
        SoundProducer.closeInputLine();
        App.backToLevelPage();
    }

    protected void generateNewPhrase() {
        int phraseLength = random.nextInt(4) + 2; // Random length (between 2 and 5 items)
        StringBuilder phraseBuilder = new StringBuilder();

        for (int i = 0; i < phraseLength; i++) {
            Object randomItem = currentLetterSet.get(random.nextInt(currentLetterSet.size()));
            phraseBuilder.append(randomItem.toString()); // Append the item as a string
            if (i < phraseLength - 1) {
                phraseBuilder.append(" "); // Add space between items
            }
        }

        currentGeneratedPhrase = phraseBuilder.toString();
        System.out.println("Generated Phrase: " + currentGeneratedPhrase);
    }

//    @FXML
//    protected void playMorseCodeForLetter(Object item) {
//        String morseCode;
//        if (item instanceof Character) {
//            morseCode = morseConverter.EnglishToMorse(item.toString());
//        } else if (item instanceof String) {
//            morseCode = morseConverter.EnglishToMorse((String) item);
//        } else {
//            throw new IllegalArgumentException("Unsupported item type: " + item.getClass());
//        }
//
//        SoundProducer.produceSound(morseCode, 5, 100, 600);
//        System.out.println("Playing Morse code for: " + item + " as " + morseCode);
//    }

    @FXML
    protected void playMorseCodeForLetter(Object item) {
        Thread thread = new Thread(() -> {
            try {
                String morseCode = morseConverter.EnglishToMorse(item.toString());
                if (morseCode == null || morseCode.isEmpty()) {
                    throw new IllegalArgumentException("No Morse code found for item: " + item);
                }

                    SoundProducer.produceSound(morseCode, 18, 100, 600); // Play the sound

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error playing Morse code for: " + item);
            }
        });
        thread.start();
        //SoundProducer.produceSound(morseCode, 90, 500, 100, 600);
    }



    @FXML
    protected void revealMessage() {
        if (isRevealed) {
            morseCodeLabel.setText("");
            revealButton.setText("Reveal");
        } else {
            morseCodeLabel.setText(currentGeneratedPhrase);
            revealButton.setText("Hide");
        }
        isRevealed = !isRevealed;
    }

    @FXML
    protected void playSampleSentence() {
        generateNewPhrase(); // Generate a new random phrase

        playMorseCodeForLetter(currentGeneratedPhrase); // Play Morse code for each part

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
}
