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
        System.out.println(converter.MorseToEnglish(".... . .-.. .-.. ---   .-- --- .-. .-.. -.."));
        System.out.println();
        System.out.println(converter.EnglishToMorse("Hello! how are you"));
        System.out.println();
        System.out.println(converter.MorseToEnglish(".... . .-.. .-.. --- / .... --- .-- / .- .-. . / -.-- --- ..-"));
    }
}
