package edu.augustana.sound;

import edu.augustana.data.ScriptedBot;

import javax.sound.sampled.LineUnavailableException;

public class BotRunnable implements Runnable {
    private ScriptedBot bot;
    private volatile boolean keepPlaying = true;  // Shared flag to control playback

    public BotRunnable(ScriptedBot bot) {
        this.bot = bot;
    }

    public void stopPlaying() {
        keepPlaying = false;  // Stop the loop
    }

    @Override
    public void run() {
        while (keepPlaying) {  // Keep playing until stop signal is given
            try {
                SoundProducer.ProduceSound(
                        bot.getMessage(),
                        String.valueOf(bot.getCharSpeed()),
                        String.valueOf(bot.getEffectiveSpeed()),
                        bot.getVolume(),
                        bot.getTone()
                );
                Thread.sleep(1000);
            } catch (InterruptedException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}

