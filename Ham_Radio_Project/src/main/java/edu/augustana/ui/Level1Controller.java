package edu.augustana.ui;

import edu.augustana.MorseCodeConverter;
import edu.augustana.sound.SoundProducer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.List;

public class Level1Controller extends BasePage {

    private final MorseCodeConverter morseConverter = new MorseCodeConverter(); // Initialize MorseCodeConverter
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
    private final List<List<Character>> stages = new ArrayList<>();
    private SourceDataLine levelLine;

    @FXML
    public void initialize() {

        backButton.setOnAction(event -> goBack());
        setupStages();
        currentLetterSet = stages.get(0);
        currentLetterIndex = 0;
       initializeSoundLine();
    }

    private void setupStages(){
        stages.add(List.of('E', 'I', 'S', 'H'));
        stages.add(List.of('T', 'M', 'O'));
        stages.add(List.of('A', 'N', 'G', 'W', 'R', 'U', 'K', 'D', 'C', 'F', 'B'));
        stages.add(List.of('L', 'J', 'Y', 'Q', 'Z', 'X', 'P', 'V'));
    }

    private void initializeSoundLine() {
        levelLine = SoundProducer.openLine();
    }

    private void generateNewLetter() {
        currentLetter = currentLetterSet.get(currentLetterIndex);
        currentLetterMorse = morseConverter.EnglishToMorse(String.valueOf(currentLetter));
        levelProgress.setText("Level Progress: " + (currentLetterIndex + 1) + "/" + currentLetterSet.size());
        playMorseCodeSound(currentLetterMorse);
    }

    private void playMorseCodeSound(String morseCode) {
        SoundProducer.produceSound(levelLine, morseCode, 100, 440);
    }

    @FXML
    private void revealMessage() {
        isRevealed = !isRevealed;
        morseCodeLabel.setText(isRevealed ? currentLetterMorse : "");
        revealButton.setText(isRevealed ? "Hide" : "Reveal");
    }

    @FXML
    private void playMorseCodeForLetter(char letter) {
        String letterMorse = morseConverter.EnglishToMorse(String.valueOf(letter));
        playMorseCodeSound(letterMorse);
    }

    @FXML
    private void playSampleSentence() {
        currentSentence = "EISH EISH";
        for (char letter : currentSentence.toCharArray()) {
            if (letter != ' ') {
                playMorseCodeForLetter(letter);
            } else {
                sleepBeforeNextCharacter();
            }
        }
    }

    private void sleepBeforeNextCharacter() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkAnswer() {
        String userAnswer = cleanInput(answerField.getText());
        String expectedAnswer = "EISHEISH";
        resultLabel.setText(userAnswer.equals(expectedAnswer) ? "Correct!" : "Listen and try again!");
        answerField.clear();
    }

    private String cleanInput(String input) {
        return input.toUpperCase().replaceAll(" ", "");
    }
    @FXML
    private void showAnswer() {
        sentenceLabel.setText("Answer: EISH EISH");
        sentenceLabel.setStyle("-fx-background-color:white;");
        sentenceLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void goBack() {
        App.backToLevelPage();
    }

}
