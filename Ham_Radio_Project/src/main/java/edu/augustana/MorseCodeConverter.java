package edu.augustana;

import java.util.HashMap;
import java.util.Map;

public class MorseCodeConverter {
    Map<Character, String> englishToMorseMap;

    public Map<Character, String> getEnglishToMorseMap() {
        return englishToMorseMap;
    }

    public Map<String, Character> getMorseToEnglishMap() {
        return morseToEnglishMap;
    }

    Map<String, Character> morseToEnglishMap;

    public MorseCodeConverter() {
        englishToMorseMap = new HashMap<>();
        morseToEnglishMap = new HashMap<>();
        initializeMappins();
    }

        private void initializeMappins () {

            //Letters
            englishToMorseMap.put('A', ".-");
            englishToMorseMap.put('B', "-...");
            englishToMorseMap.put('C', "-.-.");
            englishToMorseMap.put('D', "-..");
            englishToMorseMap.put('E', ".");
            englishToMorseMap.put('F', "..-.");
            englishToMorseMap.put('G', "--.");
            englishToMorseMap.put('H', "....");
            englishToMorseMap.put('I', "..");
            englishToMorseMap.put('J', ".---");
            englishToMorseMap.put('K', "-.-");
            englishToMorseMap.put('L', ".-..");
            englishToMorseMap.put('M', "--");
            englishToMorseMap.put('N', "-.");
            englishToMorseMap.put('O', "---");
            englishToMorseMap.put('P', ".--.");
            englishToMorseMap.put('Q', "--.-");
            englishToMorseMap.put('R', ".-.");
            englishToMorseMap.put('S', "...");
            englishToMorseMap.put('T', "-");
            englishToMorseMap.put('U', "..-");
            englishToMorseMap.put('V', "...-");
            englishToMorseMap.put('W', ".--");
            englishToMorseMap.put('X', "-..-");
            englishToMorseMap.put('Y', "-.--");
            englishToMorseMap.put('Z', "--..");


            // Numbers
            englishToMorseMap.put('0', "-----");
            englishToMorseMap.put('1', ".----");
            englishToMorseMap.put('2', "..---");
            englishToMorseMap.put('3', "...--");
            englishToMorseMap.put('4', "....-");
            englishToMorseMap.put('5', ".....");
            englishToMorseMap.put('6', "-....");
            englishToMorseMap.put('7', "--...");
            englishToMorseMap.put('8', "---..");
            englishToMorseMap.put('9', "----.");


            // Punctuation
            englishToMorseMap.put(' ', " ");
            englishToMorseMap.put('.', ".-.-.-");
            englishToMorseMap.put(',', "--..--");
            englishToMorseMap.put('?', "..--..");
            englishToMorseMap.put(':', "---...");
            englishToMorseMap.put('/', "-..-.");
            englishToMorseMap.put('-', "-....-");
            englishToMorseMap.put('=', "-...-");
            englishToMorseMap.put('\'', ".----.");
            englishToMorseMap.put('_', "..--.-");
            englishToMorseMap.put('!', "-.-.--");
            englishToMorseMap.put('&', ".-...");
            englishToMorseMap.put(';', "-.-.-.");
            englishToMorseMap.put('$', "...-..-");


            for (Map.Entry<Character, String> entry : englishToMorseMap.entrySet()) {
                morseToEnglishMap.put(entry.getValue(), entry.getKey());
            }
        }

    public String EnglishToMorse(String input) {
        char[] inputArray = input.toUpperCase().toCharArray();
        StringBuilder sentence = new StringBuilder(); // Use StringBuilder for efficiency
        for (char letter : inputArray) {
            if (englishToMorseMap.containsKey(letter)) {
                sentence.append(englishToMorseMap.get(letter)).append(" ");
            }
        }
        return sentence.toString().trim(); // Return trimmed string
    }

    public String MorseToEnglish(String input) {
        String[] inputArray = input.trim().toUpperCase().replaceAll("  ", " ").split(" "); // Trim input
        StringBuilder sentence = new StringBuilder(); // Use StringBuilder for efficiency
        for (String letter : inputArray) {
            if (morseToEnglishMap.containsKey(letter)) {
                sentence.append(morseToEnglishMap.get(letter));
            } else {
                sentence.append(" "); // Handle unknown symbols
            }
        }
        return sentence.toString(); // Return final sentence
    }
}
