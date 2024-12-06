package edu.augustana.data;

public class CWMessage {
    private final String sender;
    private final String morseMessageText;
    private final double frequency;

    public CWMessage(String sender, String morseMessageText, double frequency) {
        this.sender = sender;
        this.morseMessageText = morseMessageText;
        this.frequency = frequency;
    }
    public String getSender() {
        return sender;
    }

    public String getMorseMessageText() {
        return morseMessageText;
    }

    public double getFrequency() {
        return frequency;
    }
}
