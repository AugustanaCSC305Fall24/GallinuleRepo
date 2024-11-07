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
            String soundSpace;
            if (scriptedBot.getEffectiveSpeed() == 100){
                soundSpace = "         ";
            } else if (scriptedBot.getEffectiveSpeed() == 200){
                soundSpace = "    ";
            } else if (scriptedBot.getEffectiveSpeed() == 300){
                soundSpace = " ";
            } else {
                soundSpace = "";
            }
            try {
                for (int i = 0; i < scriptedBot.getRepeatAmount(); i++) {
                    SoundProducer.ProduceSound(scriptedBot.getMessage() + soundSpace,
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

