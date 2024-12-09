package edu.augustana.data;


public class ScriptedBot {
    private final String botName;
    private final String message;
    private final int effectiveSpeed;
    private final double frequency;
    private final int repeats;


    public ScriptedBot(String botName, String message, int effectiveSpeed, double frequency, int repeats){
        this.botName = botName;
        this.message = message;
        this.effectiveSpeed = effectiveSpeed;
        this.frequency = frequency;
        this.repeats = repeats;
    }

    public String getBotID() {return botName;}
    public String getMessage() {return message;}
    public int getEffectiveSpeed() {return effectiveSpeed;}
    public double getFrequency() {return frequency;}
    public int getRepeatAmount() {return repeats;}

    @Override
    public String toString() {
        return "Bot " + botName + " is on frequency: " + Math.round(frequency * 1000.0) / 1000.0 + "Mhz";

    }
}
