package edu.augustana.sound;

import edu.augustana.data.ScriptedBot;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class CWBotPlayer {

    private ScriptedBot scriptedBot;
    private SourceDataLine botLine;

    public CWBotPlayer(ScriptedBot scriptedBot) {
        this.scriptedBot = scriptedBot;
    }

    public void playBot() throws LineUnavailableException {
        botLine = SoundProducer.openLine();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < scriptedBot.getRepeatAmount(); i++) {
                SoundProducer.setSpeeds(String.valueOf(scriptedBot.getCharSpeed()),
                        String.valueOf(scriptedBot.getEffectiveSpeed()));
                SoundProducer.ProduceSound(botLine, scriptedBot.getMessage(),
                        scriptedBot.getVolume(),
                        scriptedBot.getTone());
            }
        });
        thread.start();
    }
}

