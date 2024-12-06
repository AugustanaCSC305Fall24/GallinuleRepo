package edu.augustana.sound;

import edu.augustana.MorseCodeConverter;

import javax.sound.sampled.*;


public class SoundProducer {

    private static int DOT_DURATION = 20;  // Duration for a dot (in milliseconds)
    private static int DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
    private static int DOT_GAP = DOT_DURATION; // Gap between dots and dashes
    private static int CHARACTER_GAP = 60; // Gap between characters
    private static int WORD_GAP = (int) (CHARACTER_GAP * 2.333); // Gap between words
    public static int frequencyVal;
    private static final MorseCodeConverter converter = new MorseCodeConverter();

    private static SourceDataLine INPUT_CW_LINE = openLine();
    private static SourceDataLine STATIC_NOISE_LINE = openLine();

//    public static void playDit(int volume) throws LineUnavailableException {
//        playSound(DOT_DURATION, volume); // Play a 'dit' sound
//    }
//
//    public static void playDah(int volume) throws LineUnavailableException {
//        playSound(DASH_DURATION, volume); // Play a 'dah' sound
//    }

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
        FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        float currentVolume = volumeControl.getValue();
        float newVolume = volume;
        volumeControl.setValue(newVolume);
        line.open(format);
        line.start();
        line.write(buffer, 0, buffer.length);
        line.drain();
        line.stop();
        line.close();
    }

    public static SourceDataLine openLine()  {
        try {
            final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
//            line.open(audioFormat, Note.SAMPLE_RATE);
            line.open(audioFormat, 1024);
            line.start();

            return line;
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void playStaticNoise(SourceDataLine line, int volume) {
        int length = Note.SAMPLE_RATE * 10000;
        byte[] noise = new byte[length];
        for (int i = 0; i < noise.length; i++) {
            noise[i] = (byte) ((Math.random() * 2 - 1) * 127 * volume / 100);
        }
        line.write(noise, 0, length);
    }

    public static void produceSound(SourceDataLine line, String message, int volume, int frequency) {
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
        pause(line, 1000);
    }

    public static void produceSound(String message, int characterSpeed, int effectiveSpeed, int volume, int frequency) {
        frequencyVal = frequency;
        setSpeeds(characterSpeed, effectiveSpeed);
        final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat)) {
            line.open(audioFormat, Note.SAMPLE_RATE);
            line.start();
            if (converter.getEnglishToMorseMap().containsKey(message.charAt(0))) {
                playEnglishMessage(line, message, volume);
            } else if (converter.getMorseToEnglishMap().containsKey(Character.toString(message.charAt(0)))) {
                playMorseMessage(line, message, volume);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void setSpeeds(int characterSpeed, int effectiveSpeed) {
        DOT_DURATION = characterSpeed; // Duration for a dot (in milliseconds)
        CHARACTER_GAP = effectiveSpeed; // Gap between characters
        DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
        DOT_GAP = DOT_DURATION; // Gap between dots and dashes
        WORD_GAP = (int) (CHARACTER_GAP * 2.333); // Gap between words
    }


    private static void playMorseMessage(SourceDataLine line, String message, int volume) {
        System.out.println(message);
        for (char letter : message.toCharArray()) {
            if (letter == ' ') {
                pause(line, CHARACTER_GAP); // Pause for character gap
            } else {
                if (letter == '.') {
                    playNote(line, DOT_DURATION, volume); // Play dot
                } else if(letter == '-') {
                    playNote(line, DASH_DURATION, volume); // Play dash
                }
            }
            pause(line, DOT_GAP); // Pause between letters
        }
    }

    private static void playEnglishMessage(SourceDataLine line, String message, int volume) {
        //message = message + " ";
        System.out.println(message);
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
        pause(line, 1000);
    }

    public static void playSendingDit() {
        playNote(INPUT_CW_LINE, DOT_DURATION, 100);
        pause(INPUT_CW_LINE, DOT_DURATION);
    }

    public static void playSendingDah() {
        playNote(INPUT_CW_LINE, DASH_DURATION, 100);
        pause(INPUT_CW_LINE, DOT_DURATION);
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