package edu.augustana.data;

import com.google.gson.JsonObject;
import edu.augustana.MorseCodeConverter;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class ScenarioBot {
    private String botName;
    private ArrayList<String> botMessages = new ArrayList<>();
    private ArrayList<String> expectedMessages = new ArrayList<>();
    private int userindex;
    private int botIndex;
    private MorseCodeConverter converter = new MorseCodeConverter();

    public ScenarioBot(StringBuilder botDataString) {
        this.userindex = 0;
        this.botIndex = 0;
        System.out.println(botDataString);
        String[] data = botDataString.toString().split("\"");
        ArrayList<String> textData = new ArrayList<>();
        int count = 1;
        for (String s : data) {
            if (s.matches(".*[a-zA-Z].*")) {
                if (count % 2 == 0) {
                    textData.add(s);
                }
                count++;
            }
        }

        this.botName = textData.get(0);
        this.botMessages.add(converter.EnglishToMorse(textData.get(1)));
        this.expectedMessages.add(converter.EnglishToMorse(textData.get(2)));
        this.botMessages.add(converter.EnglishToMorse(textData.get(3)));
        this.expectedMessages.add(converter.EnglishToMorse(textData.get(4)));
        this.botMessages.add(converter.EnglishToMorse(textData.get(5)));
    }

    public String getBotName() {
        return botName;
    }

    public String nextBotMessage() {
        return botMessages.get(botIndex++);
    }

    public void gotExpectedMessage() {
        userindex++;
    }

    public boolean isExpectedMessage(String message) {
        System.out.println(expectedMessages.get(userindex) + " dash " + message);
        if (message.toLowerCase().contains(expectedMessages.get(userindex))) {
            gotExpectedMessage();
            return true;
        }
        return false;
    }

    public String getFirstMessage() {
        return botMessages.getFirst();
    }

    @Override
    public String toString() {
        return botName + " Bot";
    }
}
