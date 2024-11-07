package edu.augustana.data;


public class ScriptedBot {
    private String botName;
    private String message;
    private int charSpeed;
    private int effectiveSpeed;
    private int frequency;
    private int volume;
    private int tone;
    private int repeats;


    public ScriptedBot(String botName, String message, int charSpeed, int effectiveSpeed, int frequency, int volume, int tone, int repeats){
        this.botName = botName;
        this.message = message;
        this.charSpeed = charSpeed;
        this.effectiveSpeed = effectiveSpeed;
        this.frequency = frequency;
        this.volume = volume;
        this.tone = tone;
        this.repeats = repeats;
    }

    public String getBotID() {return botName;}
    public String getMessage() {return message;}
    public int getCharSpeed() {return charSpeed;}
    public int getEffectiveSpeed() {return effectiveSpeed;}
    public int getVolume() {return volume;}
    public int getFrequency() {return frequency;}
    public int getTone() {return tone;}
    public int getRepeatAmount() {return repeats;}

    @Override
    public String toString() {
        return "Bot " + botName + " is on frequency: " + frequency + "Mhz";

    }
}
