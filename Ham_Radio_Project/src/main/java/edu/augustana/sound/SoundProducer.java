package edu.augustana.sound;

import edu.augustana.MorseCodeConverter;
import javax.sound.sampled.*;


public class SoundProducer {

    private static int DOT_DURATION = 60;  // Duration for a dot (in milliseconds)
    private static int DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
    private static int DOT_GAP = DOT_DURATION; // Gap between dots and dashes
    private static int CHARACTER_GAP = 60; // Gap between characters
    private static int WORD_GAP = (int) (CHARACTER_GAP * 2.333); // Gap between words
    public static int frequencyVal = 600;

    public static SourceDataLine openLine() throws LineUnavailableException {
        final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, Note.SAMPLE_RATE);
            line.start();

        return line;
    }

    public static void setSpeeds(String characterSpeed, String effectiveSpeed) {
        DOT_DURATION = Integer.parseInt(characterSpeed); // Duration for a dot (in milliseconds)
        CHARACTER_GAP = Integer.parseInt(effectiveSpeed); // Gap between characters
        DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
        DOT_GAP = DOT_DURATION; // Gap between dots and dashes
        WORD_GAP = (int) (CHARACTER_GAP * 2.333); // Gap between words
    }

    public static void ProduceSound(SourceDataLine line, String message, int volume, int frequency) {
        MorseCodeConverter converter = new MorseCodeConverter();
        for (char letter : message.toUpperCase().toCharArray()) {
            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
            if (morseLetter.equals(" ")) {
               pause(line, WORD_GAP); // Pause for word gap
            } else {
                for (char click: morseLetter.toCharArray()) {
                    if (click == '.') {
                        playNote(line, DOT_DURATION, volume); // Play dot
                    } else if(click == '-') {
                        playNote(line, DASH_DURATION, volume); // Play dash
                    }
                    pause(line, DOT_GAP);  // Pause between parts of the letter
                }
                pause(line, CHARACTER_GAP); // Pause between letters
            }
        }
    }

    private static void playNote(SourceDataLine line, int duration, int volume) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        line.write(Note.A4.data(volume), 0, length);  // Playing the note for the specified duration
    }

    private static void pause(SourceDataLine line, int duration) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        byte[] silence = new byte[length];
        line.write(silence, 0, length);
    }

    public static void playStaticNoise(SourceDataLine line, int volume) {
            int length = Note.SAMPLE_RATE * 10000;
            byte[] noise = new byte[length];
            for (int i = 0; i < noise.length; i++) {
                noise[i] = (byte) ((Math.random() * 2 - 1) * 127 * volume / 100);
            }
            line.write(noise, 0, length);
    }
}

enum Note {

    A4;
    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    // Array to hold the sine wave data

    public byte[] data(int volume){
        byte[] sin = new byte[SAMPLE_RATE * 2];
        double frequency = SoundProducer.frequencyVal;  // Frequency for the note A
        for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / frequency; // Calculate the period
            double angle = 2.0 * Math.PI * i / period; // Calculate the angle
            sin[i] = (byte) (Math.sin(angle) * 127f * volume / 100);  // Generate the sine wave
        }
        return sin; // Return the sine wave data
    }
}