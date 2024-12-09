package edu.augustana.data;

import javafx.scene.paint.Color;
import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;
import edu.augustana.ui.GeminiAPITest;

import java.util.ArrayList;
import java.util.List;

public class GeminiAIBot {
    private String systemPromptText;
    private JsonParser parser;
    private GenAi genAi;

    private final String name;
    private final HamRadio radio;
    private final double frequency;
    private List<String> myChatHistory = new ArrayList<>();
    public boolean messageReceived;
    public String geminiResponse;

    public GeminiAIBot(String name, double frequency, HamRadio radio) {
        this.name = name;
        this.frequency = frequency;
        this.radio = radio;
        this.systemPromptText = "You are a HAM Radio operator talking to another person on the same frequency as you.";
        this.parser = new GsonJsonParser();
        this.genAi = new GenAi(GeminiAPITest.getGeminiApiKey(), parser);
    }
    public String getName() {
        return name;
    }
    public HamRadio getRadio() {
        return radio;
    }
    public String toString() {
        return getName() + " [AI]";
    }

    public void requestMessage(StringBuilder msgLog) {
        StringBuilder transcript = msgLog;
//        for (CWMessage message : getRadio().getChatMessageList()) {
//            transcript.append(message.getSender()).append(": ").append(message.getMorseMessageText()).append("\n");
//        }
        String fullPrompt = systemPromptText + "\n" +
                "Your name is: " + getName() + "\n" +
                "Read the following past history/transcript, and reply by stating exactly what you would say next in this conversation.  (If the transcript is mostly empty, then just try to make conversation.) \n" +
                transcript.toString();

        var model = createBotModel(fullPrompt);

        genAi.generateContent(model)
                .thenAccept(gcr -> {
                    String geminiResponse = gcr.text();
                    System.out.println("Debug: GeminiBot received response: " + geminiResponse);
                    messageReceived = true;
                    this.geminiResponse = geminiResponse;
//                    getRadio().addMessage(new CWMessage(getName(), geminiResponse, 3));

                });
        // Note: don't include the .get() because we DON'T want to wait/block until the task completes,
        // or else it will make the UI hang / less responsive
        //.get(20, TimeUnit.SECONDS);



    }

    private GenerativeModel createBotModel(String fullPrompt) {
        return GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(Content.textContent(
                        Content.Role.USER,
                        fullPrompt
                ))
                .addSafetySetting(SafetySetting.of(
                        SafetySetting.HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
                        SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH
                ))
                .generationConfig(new GenerationConfig(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ))
                .build();
    }
}
