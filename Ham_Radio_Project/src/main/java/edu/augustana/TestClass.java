package edu.augustana;

import java.util.Map;

public class TestClass {
    public static void main(String[] args) {
        MorseCodeConverter converter = new MorseCodeConverter();
        System.out.println(converter.EnglishToMorse.entrySet());
        System.out.println();
        System.out.println(converter.EnglishToMorse("Hello World!"));
        System.out.println();
        System.out.println(converter.EnglishToMorse.entrySet());
        System.out.println();
        System.out.println(converter.MorseToEnglish(".... . .-.. .-.. ---     .-- --- .-. .-.. -.."));
    }
}
