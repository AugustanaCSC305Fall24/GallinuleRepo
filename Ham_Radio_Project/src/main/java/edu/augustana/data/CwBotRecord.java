package edu.augustana.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CwBotRecord {
    private ScriptedBot cwBot;
    private String dateCreated;
    //public static List<ScriptedBot> bots;

    public CwBotRecord(ScriptedBot cwBot) {
        this.cwBot = cwBot;
        this.dateCreated = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        //bots = new ArrayList<>();
    }

    public ScriptedBot getCwBot() {
        return cwBot;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "CwBotRecord{" +
                "cwBot=" + cwBot +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
