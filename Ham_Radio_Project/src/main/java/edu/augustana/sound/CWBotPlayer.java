package edu.augustana.sound;

import edu.augustana.data.ScriptedBot;
import javax.sound.sampled.LineUnavailableException;

public class CWBotPlayer {

    private ScriptedBot scriptedBot;

    public CWBotPlayer(ScriptedBot scriptedBot) {
        this.scriptedBot = scriptedBot;
    }

    public void playBot() {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < scriptedBot.getRepeatAmount(); i++) {
                    SoundProducer.ProduceSound(scriptedBot.getMessage() + "       ",
                            String.valueOf(scriptedBot.getCharSpeed()),
                            String.valueOf(scriptedBot.getEffectiveSpeed()),
                            scriptedBot.getVolume(),
                            scriptedBot.getTone());
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}

