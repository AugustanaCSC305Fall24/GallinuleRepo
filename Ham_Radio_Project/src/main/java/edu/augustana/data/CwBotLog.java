package edu.augustana.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CwBotLog {

    private List<ScriptedBot> botRecords;

    public CwBotLog() {
        botRecords = new ArrayList<>();
    }

    public void addCwBotRecord(ScriptedBot cwBot) {
        botRecords.add(cwBot);
    }

    public List<ScriptedBot> getCwBotRecords() {
        return botRecords;
    }

    public void saveToFile(File logFile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedLog = gson.toJson(this);
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
            writer.println(serializedLog);
        }
        System.out.println("CW bot records saved to " + logFile.getName());
    }

    public static CwBotLog loadFromFile(File logFile) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(logFile)) {
            return gson.fromJson(reader, CwBotLog.class);
        }
    }
}

