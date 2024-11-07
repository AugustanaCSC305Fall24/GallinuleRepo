package edu.augustana.sound;

import edu.augustana.data.ScriptedBot;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotPlayer {

    private ExecutorService executorService;
    private ArrayList<BotRunnable> botRunnables;

    public BotPlayer(int poolSize) {
        executorService = Executors.newFixedThreadPool(poolSize);
        botRunnables = new ArrayList<>();
    }

    public void startBots(ArrayList<ScriptedBot> bots) {
        for (ScriptedBot bot : bots) {
            BotRunnable botRunnable = new BotRunnable(bot);
            botRunnables.add(botRunnable);
            executorService.submit(botRunnable);  // Start the bot in a new thread
        }
    }

    public void stopBots() {
        for (BotRunnable botRunnable : botRunnables) {
            botRunnable.stopPlaying();  // Stop all the bot threads
        }
    }

    public void shutdown() {
        executorService.shutdown();  // Shut down the executor service
    }
}
