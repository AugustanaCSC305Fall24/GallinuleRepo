package edu.augustana;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MorseCodeConverterTest {

    private MorseCodeConverter converter;

    @Test
    void testEnglishToMorse_SimpleWord() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "SOS";
        String expected = "... --- ...";
        assertEquals(expected, converter.EnglishToMorse(input));
    }

    @Test
    void testEnglishToMorse_WithNumbers() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "HELLO 123";
        String expected = ".... . .-.. .-.. ---   .---- ..--- ...--";
        assertEquals(expected, converter.EnglishToMorse(input));
    }

    @Test
    void testEnglishToMorse_WithPunctuation() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "HELLO, WORLD!";
        String expected = ".... . .-.. .-.. --- --..--   .-- --- .-. .-.. -.. -.-.--";
        assertEquals(expected, converter.EnglishToMorse(input));
    }

    @Test
    void testEnglishToMorse_EmptyString() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "";
        String expected = "";
        assertEquals(expected, converter.EnglishToMorse(input));
    }

    @Test
    void testMorseToEnglish_SimpleWord() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "... --- ...";
        String expected = "SOS";
        assertEquals(expected, converter.MorseToEnglish(input));
    }

    @Test
    void testMorseToEnglish_WithNumbers() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = ".... . .-.. .-.. ---   .---- ..--- ...--";
        String expected = "HELLO 123";
        assertEquals(expected, converter.MorseToEnglish(input));
    }

    @Test
    void testMorseToEnglish_WithPunctuation() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = ".... . .-.. .-.. --- --..--   .-- --- .-. .-.. -.. -.-.--";
        String expected = "HELLO, WORLD!";
        assertEquals(expected, converter.MorseToEnglish(input));
    }

    @Test
    void testBothConversions() {
        MorseCodeConverter converter = new MorseCodeConverter();
        String input = "The quick brown fox jumps over the moon.";
        String morse = converter.EnglishToMorse(input);
        String english = converter.MorseToEnglish(morse);
        assertEquals(input.toUpperCase(), english);
    }

    @Test
    void testPunctuationToMorse() {
        MorseCodeConverter converter = new MorseCodeConverter();
        assertEquals(".-.-.-", converter.EnglishToMorse("."));
        assertEquals("--..--", converter.EnglishToMorse(","));
        assertEquals("..--..", converter.EnglishToMorse("?"));
        assertEquals("---...", converter.EnglishToMorse(":"));
        assertEquals("-..-.", converter.EnglishToMorse("/"));
        assertEquals("-....-", converter.EnglishToMorse("-"));
        assertEquals("-...-", converter.EnglishToMorse("="));
        assertEquals(".----.", converter.EnglishToMorse("'"));
        assertEquals("..--.-", converter.EnglishToMorse("_"));
        assertEquals("-.-.--", converter.EnglishToMorse("!"));
        assertEquals(".-...", converter.EnglishToMorse("&"));
        assertEquals("-.-.-.", converter.EnglishToMorse(";"));
        assertEquals("...-..-", converter.EnglishToMorse("$"));
    }

    @Test
    void testPunctuationFromMorse() {
        MorseCodeConverter converter = new MorseCodeConverter();
        assertEquals(".", converter.MorseToEnglish(".-.-.-"));
        assertEquals(",", converter.MorseToEnglish("--..--"));
        assertEquals("?", converter.MorseToEnglish("..--.."));
        assertEquals(":", converter.MorseToEnglish("---..."));
        assertEquals("/", converter.MorseToEnglish("-..-."));
        assertEquals("-", converter.MorseToEnglish("-....-"));
        assertEquals("=", converter.MorseToEnglish("-...-"));
        assertEquals("'", converter.MorseToEnglish(".----."));
        assertEquals("_", converter.MorseToEnglish("..--.-"));
        assertEquals("!", converter.MorseToEnglish("-.-.--"));
        assertEquals("&", converter.MorseToEnglish(".-..."));
        assertEquals(";", converter.MorseToEnglish("-.-.-."));
        assertEquals("$", converter.MorseToEnglish("...-..-"));
    }
}
