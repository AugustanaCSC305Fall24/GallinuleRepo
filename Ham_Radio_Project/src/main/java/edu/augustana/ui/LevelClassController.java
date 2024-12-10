package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class LevelClassController extends BasePage {
//
//    protected MorseCodeConverter morseConverter = new MorseCodeConverter();
//    protected List<Character> currentLetterSet;
//    protected int currentLetterIndex;
//    protected String currentLetterMorse;
//    protected String currentGeneratedPhrase = "";
//    protected Random random = new Random();
//
//    @FXML
//    protected Button backButton;
//    @FXML
//    protected Label morseCodeLabel;
//    @FXML
//    protected TextField answerField;
//    @FXML
//    protected Button resultLabel;
//    @FXML
//    protected Button revealButton;
//    @FXML
//    protected Label sentenceLabel;
//
//    private boolean isRevealed = false;
//
//    protected List<List<Character>> stages = new ArrayList<>();
//    protected List<List<String>> CWstages = new ArrayList<>();
//
//    @FXML
//    public void initialize() {
//
//        //backButton.setOnAction(event -> goBack());
//    }
//
//    @FXML
//    protected void goBack() {
//        App.backToLevelPage();
//    }
//
//    protected void generateNewPhrase() {
//        int phraseLength = random.nextInt(4) + 2;
//        StringBuilder phraseBuilder = new StringBuilder();
//
//        for (int i = 0; i < phraseLength; i++) {
//            char randomLetter = currentLetterSet.get(random.nextInt(currentLetterSet.size()));
//            phraseBuilder.append(randomLetter);
//        }
//
//        currentGeneratedPhrase = phraseBuilder.toString();
//        System.out.println("Generated Phrase: " + currentGeneratedPhrase);
//    }
//
//    @FXML
//    protected void playMorseCodeForLetter(char letter) {
//        String letterMorse = morseConverter.EnglishToMorse(String.valueOf(letter));
//        SoundProducer.produceSound(letterMorse, 90, 500, 100, 600);
//    }
//
//    @FXML
//    protected void revealMessage() {
//        if (isRevealed) {
//            morseCodeLabel.setText("");
//            revealButton.setText("Reveal");
//        } else {
//            morseCodeLabel.setText(currentLetterMorse);
//            revealButton.setText("Hide");
//        }
//        isRevealed = !isRevealed;
//    }
//
//    @FXML
//    protected void playSampleSentence() {
//        generateNewPhrase(); // Generate a new random phrase
//        for (char letter : currentGeneratedPhrase.toCharArray()) {
//            if (letter != ' ') {
//                playMorseCodeForLetter(letter);
//            } else {
//                try {
//                    Thread.sleep(500); // Add a small delay for spaces
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @FXML
//    protected void checkAnswer() {
//        String userAnswer = answerField.getText().toUpperCase().replaceAll(" ", ""); // Clean input, removing spaces
//
//        if (userAnswer.equals(currentGeneratedPhrase)) {
//            resultLabel.setText("Correct!");
//        } else {
//            resultLabel.setText("Incorrect. Try again.");
//        }
//        answerField.clear();
//    }
//
//    @FXML
//    protected void showAnswer() {
//        sentenceLabel.setText(" Answer: " + currentGeneratedPhrase);
//        sentenceLabel.setStyle("-fx-background-color:white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px");
//        sentenceLabel.setAlignment(Pos.CENTER);
//    }
//}



public class LevelClassController extends BasePage {

    protected MorseCodeConverter morseConverter = new MorseCodeConverter();
    protected List<Object> currentLetterSet; // Can now hold both Characters and Strings
    protected int currentLetterIndex;
    protected String currentGeneratedPhrase = "";
    protected Random random = new Random();

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
    public void initialize() {
        // Initialization logic
    }

    @FXML
    protected void goBack() {
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

    @FXML
    protected void playMorseCodeForLetter(Object item) {
        String morseCode;
        if (item instanceof Character) {
            morseCode = morseConverter.EnglishToMorse(item.toString());
        } else if (item instanceof String) {
            morseCode = morseConverter.EnglishToMorse((String) item);
        } else {
            throw new IllegalArgumentException("Unsupported item type: " + item.getClass());
        }

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
        for (String part : currentGeneratedPhrase.split(" ")) {
            playMorseCodeForLetter(part); // Play Morse code for each part
        }
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
