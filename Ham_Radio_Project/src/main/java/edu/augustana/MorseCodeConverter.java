package edu.augustana;

import java.util.HashMap;
import java.util.Map;

public class MorseCodeConverter {
    Map<String, String> EnglishToMorse;
    Map<String, String> MorseToEnglish;

    public MorseCodeConverter() {
        EnglishToMorse = new HashMap<>();
        MorseToEnglish = new HashMap<>();
        EnglishToMorse.put("A", ".-");
        EnglishToMorse.put("B", "-...");
        EnglishToMorse.put("C", "-.-.");
        EnglishToMorse.put("D", "-..");
        EnglishToMorse.put("E", ".");
        EnglishToMorse.put("F", "..-.");
        EnglishToMorse.put("G", "--.");
        EnglishToMorse.put("H", "....");
        EnglishToMorse.put("I", "..");
        EnglishToMorse.put("J", ".---");
        EnglishToMorse.put("K", "-.-");
        EnglishToMorse.put("L", ".-..");
        EnglishToMorse.put("M", "--");
        EnglishToMorse.put("N", "-.");
        EnglishToMorse.put("O", "---");
        EnglishToMorse.put("P", ".--.");
        EnglishToMorse.put("Q", "--.-");
        EnglishToMorse.put("R", ".-.");
        EnglishToMorse.put("S", "...");
        EnglishToMorse.put("T", "-");
        EnglishToMorse.put("U", "..-");
        EnglishToMorse.put("V", "...-");
        EnglishToMorse.put("W", ".--");
        EnglishToMorse.put("X", "-..-");
        EnglishToMorse.put("Y", "-.--");
        EnglishToMorse.put("Z", "--..");
        EnglishToMorse.put(" ", "   ");
        for (Map.Entry<String, String> entry : EnglishToMorse.entrySet()) {
            MorseToEnglish.put(entry.getValue(), entry.getKey());
        }
    }

    public String EnglishToMorse(String input) {
        input = input.toUpperCase();
        String[] inputArray = input.split("");
        String sentence = "";
        for (String letter : inputArray) {
            if (!EnglishToMorse.containsKey(letter)) {
                continue;
            }
            sentence += EnglishToMorse.get(letter) + " ";
        }
        return sentence;
    }

    public String MorseToEnglish(String input) {
        input = input.toUpperCase();
        String[] inputArray = input.split(" ");
        String sentence = "";
        for (String letter : inputArray) {
            if (!MorseToEnglish.containsKey(letter)) {
                continue;
            }
            sentence += MorseToEnglish.get(letter);
        }
        return sentence;
    }
}
