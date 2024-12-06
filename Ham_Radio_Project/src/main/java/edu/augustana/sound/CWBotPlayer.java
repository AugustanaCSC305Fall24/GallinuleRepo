package edu.augustana.sound;

import edu.augustana.data.HamRadio;
import edu.augustana.data.ScriptedBot;

import javax.sound.sampled.LineUnavailableException;

public class CWBotPlayer {

    private ScriptedBot scriptedBot;

    public CWBotPlayer(ScriptedBot scriptedBot) {
        this.scriptedBot = scriptedBot;
    }

    public void playBot() {
        Thread thread = new Thread(() -> {
            String soundSpace;
            if (scriptedBot.getEffectiveSpeed() == 100) {
                soundSpace = "         ";
            } else if (scriptedBot.getEffectiveSpeed() == 200) {
                soundSpace = "    ";
            } else if (scriptedBot.getEffectiveSpeed() == 300) {
                soundSpace = " ";
            } else {
                soundSpace = "";
            }
            for (int i = 0; i < scriptedBot.getRepeatAmount(); i++) {
                SoundProducer.produceSound(scriptedBot.getMessage() + soundSpace,
                        scriptedBot.getCharSpeed(),
                        scriptedBot.getEffectiveSpeed(),
                        HamRadio.theRadio.getVolume(),
                        600
                );
            }
        });
        thread.start();
    }
}

