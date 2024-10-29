package edu.augustana;

import java.util.Random;

public class ChatBot {
    public static String getResponse(String input) {
        return respondToChat(input);
    }

    private static String respondToChat(String input) {
        if (input.toLowerCase().contains("hi")) {
            return "Hi!";
        }
        if (input.toLowerCase().contains("how are you")) {
            return "I'm doing well. How are you?";
        }
        if (input.toLowerCase().contains("bye")) {
            return "Bye!";
        }
        else {
            return "Sorry, I don't understand";
        }
    }

    public static String sendRandomMessage() {
        if (shouldChatSendRandomMessage()) {
            return "Hi, is anyone there?";
        }
        return "";
    }

    public static boolean shouldChatSendRandomMessage() {
        Random random = new Random();
        int num = random.nextInt(10);
        System.out.println(num);
        return num == 5;
    }
}
