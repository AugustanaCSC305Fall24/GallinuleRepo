package edu.augustana;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MorseCodeConverter {
    Map<Character, String> EnglishToMorse;
    Map<String, Character> MorseToEnglish;

    public MorseCodeConverter() {
        EnglishToMorse = new HashMap<>();
        MorseToEnglish = new HashMap<>();

        //Letters
        EnglishToMorse.put('A', ".-");
        EnglishToMorse.put('B', "-...");
        EnglishToMorse.put('C', "-.-.");
        EnglishToMorse.put('D', "-..");
        EnglishToMorse.put('E', ".");
        EnglishToMorse.put('F', "..-.");
        EnglishToMorse.put('G', "--.");
        EnglishToMorse.put('H', "....");
        EnglishToMorse.put('I', "..");
        EnglishToMorse.put('J', ".---");
        EnglishToMorse.put('K', "-.-");
        EnglishToMorse.put('L', ".-..");
        EnglishToMorse.put('M', "--");
        EnglishToMorse.put('N', "-.");
        EnglishToMorse.put('O', "---");
        EnglishToMorse.put('P', ".--.");
        EnglishToMorse.put('Q', "--.-");
        EnglishToMorse.put('R', ".-.");
        EnglishToMorse.put('S', "...");
        EnglishToMorse.put('T', "-");
        EnglishToMorse.put('U', "..-");
        EnglishToMorse.put('V', "...-");
        EnglishToMorse.put('W', ".--");
        EnglishToMorse.put('X', "-..-");
        EnglishToMorse.put('Y', "-.--");
        EnglishToMorse.put('Z', "--..");


        // Numbers 

        EnglishToMorse.put('0', "-----");
        EnglishToMorse.put('1', ".----");
        EnglishToMorse.put('2', "..---");
        EnglishToMorse.put('3', "...--");
        EnglishToMorse.put('4', "....-");
        EnglishToMorse.put('5', ".....");
        EnglishToMorse.put('6', "-....");
        EnglishToMorse.put('7', "--...");
        EnglishToMorse.put('8', "---..");
        EnglishToMorse.put('9', "----.");


        // Punctuation 
        EnglishToMorse.put(' ', " ");
        EnglishToMorse.put('.', ".-.-.-");
        EnglishToMorse.put(',', "--..--");
        EnglishToMorse.put('?', "..--..");
        EnglishToMorse.put(':', "---...");
        EnglishToMorse.put('/', "-..-.");
        EnglishToMorse.put('-', "-....-");
        EnglishToMorse.put('=', "-...-");
        EnglishToMorse.put('\'', ".----.");
        EnglishToMorse.put('_', "..--.-");
        EnglishToMorse.put('!', "-.-.--");
        EnglishToMorse.put('&', ".-...");
        EnglishToMorse.put(';', "-.-.-.");
        EnglishToMorse.put('$', "...-..-");


        for (Map.Entry<Character, String> entry : EnglishToMorse.entrySet()) {
            MorseToEnglish.put(entry.getValue(), entry.getKey());
        }
    }

    public String EnglishToMorse(String input) {
        char[] inputArray = input.toUpperCase().toCharArray();
        System.out.println(Arrays.toString(inputArray));
        String sentence = "";
        for (char letter : inputArray) {
            if (!EnglishToMorse.containsKey(letter)) {
                continue;
            }
            sentence += EnglishToMorse.get(letter) + " ";
        }
        return sentence;
    }

    public String MorseToEnglish(String input) {
        System.out.println(input);
        String[] inputArray = input.toUpperCase().replaceAll("  ", " ").split(" ");
        System.out.println(Arrays.toString(inputArray));
        String sentence = "";
        for (String letter : inputArray) {
            if (!MorseToEnglish.containsKey(letter)) {
                sentence += " ";
                continue;
            }
            sentence += MorseToEnglish.get(letter);
        }
        return sentence;
    }
}
