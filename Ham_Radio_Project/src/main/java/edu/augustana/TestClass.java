package edu.augustana;

import javax.sound.sampled.LineUnavailableException;
import java.util.Map;
import static edu.augustana.AIHandler.openAI;

public class TestClass {
    public static void main(String[] args) throws LineUnavailableException {
//        MorseCodeConverter converter = new MorseCodeConverter();
//        System.out.println(converter.EnglishToMorse.entrySet());
//        System.out.println();
//        System.out.println(converter.EnglishToMorse("Hello World!"));
//        System.out.println();
//        System.out.println(converter.EnglishToMorse.entrySet());
//        System.out.println();
//        System.out.println(converter.MorseToEnglish(".... . .-.. .-.. ---   .-- --- .-. .-.. -.. -.-.--"));
//        System.out.println();
//        System.out.println(converter.EnglishToMorse("Hello! how are you"));
//        System.out.println();
//        System.out.println(converter.MorseToEnglish(".... . .-.. .-.. --- -.-.--   .... --- .--   .- .-. .   -.-- --- ..- "));

        //SoundProducer.ProduceSound("Hello World");
        //SoundProducer.ProduceSound("Hello! how are you");

        //to test the AIHandler class

        //System.out.println(openAI("hello, how are you?"));
        boolean isTrue;
        do {
            isTrue = ChatBot.shouldChatSendRandomMessage();
            System.out.println(isTrue);
        } while (!isTrue);

       // System.out.println(openAI("hello, how are you?"));


    }
}
