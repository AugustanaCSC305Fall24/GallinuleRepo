package edu.augustana.data;

public class CWMessage {
    private final String sender;
    private final String morseMessageText;
    private final double frequency;
    private boolean isFromRemoteClient;

    public CWMessage(String sender, String morseMessageText, double frequency) {
        this.sender = sender;
        this.morseMessageText = morseMessageText;
        this.frequency = frequency;
        this.isFromRemoteClient = false;
    }
    public String getSender() {
        return sender;
    }

    public boolean isFromRemoteClient() {
        return isFromRemoteClient;
    }
    public void setFromRemoteClient(boolean fromRemoteClient) {
        this.isFromRemoteClient = fromRemoteClient;
    }


    public String getMorseMessageText() {
        return morseMessageText;
    }

    public double getFrequency() {
        return frequency;
    }
}
