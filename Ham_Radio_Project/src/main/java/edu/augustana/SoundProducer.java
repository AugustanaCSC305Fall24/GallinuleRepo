package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;



public class SoundProducer {

    private static final int DOT_DURATION = 200;  // Duration for a dot (in milliseconds)
    private static final int DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
    private static final int DOT_GAP = DOT_DURATION; // Gap between dots and dashes
    private static final int CHARACTER_GAP = DOT_DURATION * 3; // Gap between characters
    private static final int WORD_GAP = DOT_DURATION * 7; // Gap between words

    public static void playDit(int volume) throws LineUnavailableException {
        playSound(DOT_DURATION, volume); // Play a 'dit' sound
    }

    public static void playDah(int volume) throws LineUnavailableException {
        playSound(DASH_DURATION, volume); // Play a 'dah' sound
    }

    private static void playSound(int duration, int volume) throws LineUnavailableException {
        // Create a sine wave sound
        float sampleRate = 44100f; // Sample rate
        byte[] buffer = new byte[(int) (sampleRate * duration / 1000)];
        for (int i = 0; i < buffer.length; i++) {
            // Generate a sine wave
            buffer[i] = (byte) (Math.sin(2 * Math.PI * i / (sampleRate / 440)) * volume / 100);
        }

        // Play sound
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();
        line.write(buffer, 0, buffer.length);
        line.drain();
        line.stop();
        line.close();
    }


        public static void ProduceSound(String message) throws LineUnavailableException {
        final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat)) {
            line.open(audioFormat, Note.SAMPLE_RATE);
            line.start();
            playMorseCode(line, message);
        }
    }

    private static void playMorseCode(SourceDataLine line, String message) {
        MorseCodeConverter converter = new MorseCodeConverter();
        //message = message + " ";
        for (char letter : message.toUpperCase().toCharArray()) {
            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
            if (morseLetter.equals(" ")) {
                pause(line, WORD_GAP); // Pause for word gap
            } else {
                for (char click: morseLetter.toCharArray()) {
                    if (click == '.') {
                        playNote(line, DOT_DURATION); // Play dot
                    } else if(click == '-') {
                        playNote(line, DASH_DURATION); // Play dash
                    }
                    pause(line, DOT_GAP);  // Pause between parts of the letter
                }
                pause(line, CHARACTER_GAP); // Pause between letters
            }
        }
    }

    private static void playNote(SourceDataLine line, int duration) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        line.write(Note.A4.data(), 0, length);  // Playing the note for the specified duration
    }

    private static void pause(SourceDataLine line, int duration) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        byte[] silence = new byte[length];
        line.write(silence, 0, length);
    }
}

enum Note {

    A4;
    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    private final byte[] sin = new byte[SAMPLE_RATE * 2]; // Array to hold the sine wave data

    Note() {
       double frequency = 440.0;  // Frequency for the note A
       for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / frequency; // Calculate the period
           double angle = 2.0 * Math.PI * i / period; // Calculate the angle
           sin[i] = (byte) (Math.sin(angle) * 127f);  // Generate the sine wave
       }
    }

    public byte[] data() {

        return sin; // Return the sine wave data
    }
}
