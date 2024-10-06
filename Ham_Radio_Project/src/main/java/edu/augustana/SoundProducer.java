package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundProducer {

    private static final int DOT_DURATION = 200;
    private static final int DASH_DURATION = DOT_DURATION * 3;
    private static final int DOT_GAP = DOT_DURATION;
    private static final int CHARACTER_GAP = DOT_DURATION * 3;
    private static final int WORD_GAP = DOT_DURATION * 7;

    public static void ProduceSound(String message) throws LineUnavailableException {
        final AudioFormat af =
                new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(af);
        line.open(af, Note.SAMPLE_RATE);
        line.start();

        playMorseCode(line, message);

        line.drain();
        line.close();
    }

    private static void playMorseCode(SourceDataLine line, String message) {
        MorseCodeConverter converter = new MorseCodeConverter();
        for (char letter : message.toUpperCase().toCharArray()) {
            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
            if (morseLetter.equals(" ")) {
                pause(line, WORD_GAP);
            } else {
                for (char click: morseLetter.toCharArray()) {
                    if (click == '.') {
                        playNote(line, DOT_DURATION);
                    } else if(click == '-') {
                        playNote(line, DASH_DURATION);
                    }
                    pause(line, DOT_GAP);
                }
                pause(line, CHARACTER_GAP);
            }
        }
    }

    private static void playNote(SourceDataLine line, int duration) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        line.write(Note.A4.data(), 0, length);
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
    private final byte[] sin = new byte[SAMPLE_RATE * 2];

    Note() {
       double frequency = 440.0;
       for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / frequency;
           double angle = 2.0 * Math.PI * i / period;
           sin[i] = (byte) (Math.sin(angle) * 127f);
       }
    }

    public byte[] data() {
        return sin;
    }
}
