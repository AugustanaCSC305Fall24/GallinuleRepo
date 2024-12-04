package edu.augustana.data;


public class ScriptedBot {
    private String botName;
    private String message;
    private int charSpeed;
    private int effectiveSpeed;
    private int frequency;
    private int repeats;


    public ScriptedBot(String botName, String message, int charSpeed, int effectiveSpeed, int frequency, int repeats){
        this.botName = botName;
        this.message = message;
        this.charSpeed = charSpeed;
        this.effectiveSpeed = effectiveSpeed;
        this.frequency = frequency;
        this.repeats = repeats;
    }

    public String getBotID() {return botName;}
    public String getMessage() {return message;}
    public int getCharSpeed() {return charSpeed;}
    public int getEffectiveSpeed() {return effectiveSpeed;}
    public int getFrequency() {return frequency;}
    public int getRepeatAmount() {return repeats;}

    @Override
    public String toString() {
        return "Bot " + botName + " is on frequency: " + frequency + "Mhz";

    }
}
