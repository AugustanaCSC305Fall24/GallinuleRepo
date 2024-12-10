package edu.augustana.sound;

import edu.augustana.MorseCodeConverter;
import edu.augustana.data.HamRadio;
import edu.augustana.data.ScriptedBot;
import edu.augustana.ui.App;
import edu.augustana.ui.MainPageController;

import javax.sound.sampled.LineUnavailableException;

public class CWBotPlayer {

    private ScriptedBot scriptedBot;
    private MainPageController mainPageController;


    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }

    public CWBotPlayer(ScriptedBot scriptedBot) {
        this.scriptedBot = scriptedBot;
    }

    public void playBot() {
        MorseCodeConverter converter = new MorseCodeConverter();
        Thread thread = new Thread(() -> {
            for (int j = 0; j < scriptedBot.getRepeatAmount(); j++) {
                boolean played = false;
                for (int i = 0; i < App.mainPageController.getCurrentRange(); i++) {
                        if (App.mainPageController.getCurrentFrequencyIntVal() + i == getFrequencyIntVal(scriptedBot.getFrequency())) {
                            SoundProducer.produceSound(converter.EnglishToMorse(scriptedBot.getMessage()),
                                    scriptedBot.getEffectiveSpeed(),
                                    (int) App.mainPageController.volumeSlider.getValue(),
                                    600 + i * 20
                            );
                            played = true;
                        } else if (App.mainPageController.getCurrentFrequencyIntVal() - i == getFrequencyIntVal(scriptedBot.getFrequency())){
                            SoundProducer.produceSound(converter.EnglishToMorse(scriptedBot.getMessage()),
                                    scriptedBot.getEffectiveSpeed(),
                                    (int) App.mainPageController.volumeSlider.getValue(),
                                    600 - i * 20
                            );
                            played = true;
                        }
                    }
                    if (!played){
                        SoundProducer.produceSound(converter.EnglishToMorse(scriptedBot.getMessage()),
                                scriptedBot.getEffectiveSpeed(),
                                0,
                                600
                        );
                    }
                }
            });
        thread.start();
    }

    public int getFrequencyIntVal(double frequencyValue) {
        double transformedValue = (frequencyValue - 7) * 1000;
        return (int) Math.round(transformedValue);
    }
}

