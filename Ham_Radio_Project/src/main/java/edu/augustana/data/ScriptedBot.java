package edu.augustana.data;


public class ScriptedBot {
    private String botName;
    private String message;
    private int charSpeed;
    private int effectiveSpeed;
    private int frequency;
    private int volume;


    public ScriptedBot(String botName, String message, int charSpeed, int effectiveSpeed, int frequency, int volume){
        this.botName = botName;
        this.message = message;
        this.charSpeed = charSpeed;
        this.effectiveSpeed = effectiveSpeed;
        this.frequency = frequency;
        this.volume = volume;
    }

    public String getBotID() {return botName;}
    public String getMessage() {return message;}
    public int getCharSpeed() {return charSpeed;}
    public int getEffectiveSpeed() {return effectiveSpeed;}
    public int getVolume() {return volume;}
    public int getFrequency() {return frequency;}

    @Override
    public String toString() {
        return "Bot{" +
                "bot name='" + botName + '\'' +
                ", message='" + message + '\'' +
                ", character speed='" + charSpeed + '\'' +
                ", effective speed='" + effectiveSpeed + '\'' +
                ", frequency='" + frequency + '\'' +
                ", volume=" + volume +
                '}';
    }
}
