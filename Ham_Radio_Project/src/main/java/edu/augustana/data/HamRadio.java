package edu.augustana.data;

import edu.augustana.sound.SoundProducer;
import edu.augustana.ui.App;

import java.util.Random;

public class HamRadio {
    public static HamRadio theRadio = new HamRadio();

    private String userName;
    private double frequency;
    private double frequencyRange;
    private int characterSpeed;
    private int effectiveSpeed;
    private int volume;
    private int sideToneSoundFrequency;

    private MessageReceivedListener messageReceivedListener = null;

    private HamRadio() {
        userName = "user" + new Random().nextInt(10000);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public double getFrequency() {
        return frequency;
    }
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    public void setRange(double range){
        this.frequencyRange = range;
    }
    public double getSideToneSoundFrequency() {
        return sideToneSoundFrequency;
    }
    public void setSideToneSoundFrequency(int sideToneSoundFrequency) {
        this.sideToneSoundFrequency = sideToneSoundFrequency;
    }
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public double getEffectiveSpeed() {
        return effectiveSpeed;
    }
    public void setEffectiveSpeed(int effectiveSpeed) {
        this.effectiveSpeed = effectiveSpeed;
    }
    public double getCharacterSpeed() {
        return characterSpeed;
    }
    public void setCharacterSpeed(int characterSpeed) {
        this.characterSpeed = characterSpeed;
    }
    public double getFrequencyRange() {
        return frequencyRange;
    }
    public void setFrequencyRange(double frequencyRange) {
        this.frequencyRange = frequencyRange;
    }
    public void setMessageReceivedListener(MessageReceivedListener messageReceivedListener) {
        this.messageReceivedListener = messageReceivedListener;
    }

    public void sendMessageFromHumanUser(CWMessage message) {
        App.sendMessageToServer(message);
//        if (messageReceivedListener != null) {
//            messageReceivedListener.onNewMessage(message);
//        }
    }

    public void setSoundVariables(int effectiveSpeed, int volume, int sideToneSoundFrequency) {
        this.sideToneSoundFrequency = sideToneSoundFrequency;
        this.effectiveSpeed = effectiveSpeed;
        this.volume = volume;
    }

    public void receiveMessage(CWMessage msg) {
        if (messageReceivedListener != null) {
            messageReceivedListener.onNewMessage(msg);
            // do we want to put in the sound stuff here, and
            // the matching of sender frequency with the radio's current frequency
            frequency = App.mainPageController.getCurrentFrequencyIntVal();
            frequencyRange = App.mainPageController.getCurrentRange();
            volume = App.mainPageController.getCurrentVolume();
            if (msg.getFrequency() <= frequency + frequencyRange &&  msg.getFrequency() >= frequency - frequencyRange) {
                int tone;
                if (msg.getFrequency() > frequency) {
                    tone = (int) (msg.getFrequency() - frequency) * 20 + 600;
                } else if (msg.getFrequency() < frequency) {
                    tone = (int) (frequency - msg.getFrequency()) * 20 - 600;
                } else {
                    tone = 600;
                }

                Thread thread = new Thread(() -> {
                    SoundProducer.produceSound(msg.getMorseMessageText(), effectiveSpeed, volume, tone);
                });
                thread.start();
            }
        }
    }

}
