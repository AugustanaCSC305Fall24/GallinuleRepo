package edu.augustana.ui;

import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Example program to test the {@link GenAi} functionality.
 *
 * From: https://github.com/michael-ameri/gemini-api
 */
public class GeminiAPITest {

    // NOTE: It is NOT safe to share your API key with the public, or someone could
    // use it do a bunch of AI calls using your billing account
    // However, for now you're using private repos on GitHub, and you should have
    // used the "Billing Account For Education" for the project you created
    // at Google's AI Studio, so if your key got leaked to someone unethical,
    // they should only be able to use up your $50.00 coupon,
    // and not rack up a huge credit card bill for you.
    // Still, you would want to delete/disable the API key in your Google AI Studio Project
    // before your team makes the repo public at some point

    // replace the following with a real API key from https://aistudio.google.com
    private static final String API_KEY = "AIzaSyBb_4MxkSM7giWSJELleLPTLHZMCdR8XuE";

    public static String getGeminiApiKey() {
        if (API_KEY == null || API_KEY.equals("")) {
            throw new IllegalStateException("API_KEY is null/empty. Please set it to a valid API key from https://aistudio.google.com");
        }
        return API_KEY;
    }

    private GeminiAPITest() {
        throw new AssertionError("Not instantiable");
    }

    /**
     * Entry point. takes the Gemini API key as argument. See <a href="https://aistudio.google.com/app/apikey">aistuio.google.com</a> to generate a new API key.
     *
     * @param args should receive the API key as argument
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        JsonParser parser = new GsonJsonParser();

        try (var genAi = new GenAi(getGeminiApiKey(), parser)) {
            // each method represents an example usage
            listModels(genAi);
            getModel(genAi);
            countTokens(genAi);
            generateContent(genAi);
//            generateContentStream(genAi);
//            multiChatTurn(genAi);
//            textAndImage(genAi);
//            embedContents(genAi);
        }


    }

    private static void embedContents(GenAi genAi) {
        System.out.println("----- embed contents");
        var model = GenerativeModel.builder()
                .modelName(ModelVariant.TEXT_EMBEDDING_004)
                .addContent(Content.textContent(
                        Content.Role.USER,
                        "Write a 50 word story about a magic backpack."
                ))
                .addContent(Content.textContent(
                        Content.Role.MODEL,
                        "bla bla bla bla"
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

        List<GenAi.ContentEmbedding> embeddings = genAi.embedContents(model, null, null, null).join();
        System.out.println("Embedding count: " + embeddings.size());
        System.out.println("Values per embedding: " + embeddings.stream().map(GenAi.ContentEmbedding::values).map(List::size).toList());

    }

    private static void countTokens(GenAi genAi) {
        System.out.println("----- count tokens");
        var model = createStoryModel();
        Long result = genAi.countTokens(model)
                .join();
        System.out.println("Tokens: " + result);
    }

    private static void multiChatTurn(GenAi genAi) {
        System.out.println("----- multi turn chat");
        GenerativeModel chatModel = GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(new Content.TextContent(
                        Content.Role.USER.roleName(),
                        "Write the first line of a story about a magic backpack."
                ))
                .addContent(new Content.TextContent(
                        Content.Role.MODEL.roleName(),
                        "In the bustling city of Meadow brook, lived a young girl named Sophie. She was a bright and curious soul with an imaginative mind."
                ))
                .addContent(new Content.TextContent(
                        Content.Role.USER.roleName(),
                        "Can you set it in a quiet village in 1600s France? Max 30 words"
                ))
                .build();
        genAi.generateContentStream(chatModel)
                .forEach(System.out::println);
    }

    private static void generateContentStream(GenAi genAi) {
        System.out.println("----- Generate content (streaming) -- with usage meta data");
        var model = createStoryModel();
        genAi.generateContentStream(model)
                .forEach(x -> {
                    System.out.println(x);
                    // note that the usage metadata is updated as it arrives
                    System.out.println(genAi.usageMetadata(x.id()));
                    System.out.println(genAi.safetyRatings(x.id()));
                });
    }

    private static void generateContent(GenAi genAi) throws InterruptedException, ExecutionException, TimeoutException {
        var model = createStoryModel();
        System.out.println("----- Generate content (blocking)");
        genAi.generateContent(model)
                .thenAccept(gcr -> {
                    System.out.println(gcr);
                    System.out.println("----- Generate content (blocking) usage meta data & safety ratings");
                    System.out.println(genAi.usageMetadata(gcr.id()));
                    System.out.println(genAi.safetyRatings(gcr.id()).stream().map(GenAi.SafetyRating::toTypedSafetyRating).toList());
                })
                .get(20, TimeUnit.SECONDS);
    }

    private static GenerativeModel createStoryModel() {
        return GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(Content.textContent(
                        Content.Role.USER,
                        "Write a 50 word story about a magic backpack."
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

    private static void getModel(GenAi genAi) {
        System.out.println("----- Get Model");
        System.out.println(
                genAi.getModel(ModelVariant.GEMINI_1_5_FLASH)
        );
    }

    private static void listModels(GenAi genAi) {
        System.out.println("----- List models");
        genAi.listModels()
                .forEach(System.out::println);
    }

    private static void textAndImage(GenAi genAi) throws IOException {
        System.out.println("----- text and image");
        var model = GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_PRO)
                .addContent(
                        Content.textAndMediaContentBuilder()
                                .role(Content.Role.USER)
                                .text("What is in this image?")
                                .addMedia(new Content.MediaData(
                                        "image/png",
                                        loadSconesImage()
                                ))
                                .build()
                ).build();
        genAi.generateContent(model)
                .thenAccept(System.out::println)
                .join();
    }

    private static String loadSconesImage() throws IOException {
        try (InputStream is = GeminiAPITest.class.getClassLoader().getResourceAsStream("scones.png")) {
            if (is == null) {
                throw new IllegalStateException("Image not found! ");
            }
            return Base64.getEncoder().encodeToString(is.readAllBytes());
        }
    }
}