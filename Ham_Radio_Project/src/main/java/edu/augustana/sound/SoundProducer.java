package edu.augustana.sound;

import edu.augustana.MorseCodeConverter;

import javax.sound.sampled.*;


public class SoundProducer {

    private static int DOT_DURATION = 80;  // Duration for a dot (in milliseconds)
    private static int DASH_DURATION = DOT_DURATION * 3; // Duration for a dash
    private static int DOT_GAP = DOT_DURATION / 2; // Gap between dots and dashes
    private static int CHARACTER_GAP = 60; // Gap between characters
    private static int WORD_GAP = (int) (CHARACTER_GAP * 2.333); // Gap between words
    private static final MorseCodeConverter converter = new MorseCodeConverter();

    private static SourceDataLine INPUT_CW_LINE;
    private static SourceDataLine STATIC_NOISE_LINE;


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

    public static void openInputLine()  {
        try {
            final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
            INPUT_CW_LINE = AudioSystem.getSourceDataLine(audioFormat);
            INPUT_CW_LINE.open(audioFormat, 1024);
            INPUT_CW_LINE.start();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }

    }

<<<<<<< HEAD
    public static void openPlaysoundLine() {
        try {
            final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
            PLAY_SOUND_LINE = AudioSystem.getSourceDataLine(audioFormat);
            PLAY_SOUND_LINE.open(audioFormat, Note.SAMPLE_RATE);
            PLAY_SOUND_LINE.start();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }
=======

//    public static void openPlaysoundLine() {
//        try {
//            final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
//            PLAY_SOUND_LINE = AudioSystem.getSourceDataLine(audioFormat);
//            PLAY_SOUND_LINE.open(audioFormat, Note.SAMPLE_RATE);
//            PLAY_SOUND_LINE.start();
//        } catch (LineUnavailableException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
>>>>>>> 553ce8647ec8fcb2f185dd65dc9465dd8b4957ca


    public static void openStaticLine()  {
        try {
            final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
            STATIC_NOISE_LINE = AudioSystem.getSourceDataLine(audioFormat);
            STATIC_NOISE_LINE.open(audioFormat, Note.SAMPLE_RATE);
            STATIC_NOISE_LINE.start();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void closeStaticLine(){
        STATIC_NOISE_LINE.close();
    }

    public static void closeInputLine(){
        INPUT_CW_LINE.close();
    }

    public static void playStaticNoise() {
        int length = Note.SAMPLE_RATE * 10000;
        byte[] noise = new byte[length];
        for (int i = 0; i < noise.length; i++) {
            noise[i] = (byte) ((Math.random() * 2 - 1) * 127 * 10 / 100);
        }
        STATIC_NOISE_LINE.write(noise, 0, length);
    }

//    public static void produceSound(SourceDataLine line, String message, int volume, int tone) {
//        MorseCodeConverter converter = new MorseCodeConverter();
//        for (char letter : message.toUpperCase().toCharArray()) {
//            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
//            if (morseLetter.equals(" ")) {
//                pause(line, WORD_GAP); // Pause for word gap
//            } else {
//                for (char click: morseLetter.toCharArray()) {
//                    if (click == '.') {
//                        playNote(line, DOT_DURATION, volume, tone); // Play dot
//                    } else if(click == '-') {
//                        playNote(line, DASH_DURATION, volume, tone); // Play dash
//                    }
//                    pause(line, DOT_GAP);  // Pause between parts of the letter
//                }
//                pause(line, CHARACTER_GAP); // Pause between letters
//            }
//        }
//        pause(line, 1000);
//    }

    public static void produceSound(String message, int effectiveSpeed, int volume, int tone) {
        setSpeeds(effectiveSpeed);
        final AudioFormat audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat)) {
            line.open(audioFormat, Note.SAMPLE_RATE);
            line.start();
            playMorseMessage(line, message, volume, tone);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void setSpeeds(int effectiveSpeed) {
        if (effectiveSpeed == 5) {
            CHARACTER_GAP = 1200;
        } else if (effectiveSpeed == 10) {
            CHARACTER_GAP = 600;
        } else if (effectiveSpeed == 15) {
            CHARACTER_GAP = 300;
        } else if (effectiveSpeed == 18) {
            CHARACTER_GAP = 150;
        } else {
            CHARACTER_GAP = 100;
        }
        WORD_GAP = (int) (CHARACTER_GAP * 2.333);
    }


    private static void playMorseMessage(SourceDataLine line, String message, int volume, int tone) {

        for (char letter : message.toCharArray()) {
            if (letter == ' ') {
                pause(line, CHARACTER_GAP); // Pause for character gap
            } else {
                if (letter == '.') {
                    playNote(line, DOT_DURATION, volume, tone); // Play dot
                } else if(letter == '-') {
                    playNote(line, DASH_DURATION, volume, tone); // Play dash
                }
            }
            pause(line, DOT_GAP); // Pause between letters
        }
    }

<<<<<<< HEAD
    private static void playEnglishMessage(SourceDataLine line, String message, int volume, int tone) {
        //message = message + " ";
        for (char letter : message.toUpperCase().toCharArray()) {
            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
            if (morseLetter.equals(" ")) {
                pause(line, WORD_GAP); // Pause for word gap
            } else {
                for (char click: morseLetter.toCharArray()) {
                    if (click == '.') {
                        playNote(line, DOT_DURATION, volume, tone); // Play dot
                    } else if(click == '-') {
                        playNote(line, DASH_DURATION, volume, tone); // Play dash
                    }
                    pause(line, DOT_GAP);  // Pause between parts of the letter
                }
                pause(line, CHARACTER_GAP); // Pause between letters
            }
        }
        pause(line, 1000);
    }
=======
//    private static void playEnglishMessage(SourceDataLine line, String message, int volume, int tone) {
//        //message = message + " ";
//        for (char letter : message.toUpperCase().toCharArray()) {
//            String morseLetter = converter.EnglishToMorse(Character.toString(letter));
//            if (morseLetter.equals(" ")) {
//                pause(line, WORD_GAP); // Pause for word gap
//            } else {
//                for (char click: morseLetter.toCharArray()) {
//                    if (click == '.') {
//                        playNote(line, DOT_DURATION, volume, tone); // Play dot
//                    } else if(click == '-') {
//                        playNote(line, DASH_DURATION, volume, tone); // Play dash
//                    }
//                    pause(line, DOT_GAP);  // Pause between parts of the letter
//                }
//                pause(line, CHARACTER_GAP); // Pause between letters
//            }
//        }
//        pause(line, 1000);
//    }

>>>>>>> 553ce8647ec8fcb2f185dd65dc9465dd8b4957ca

//    public static void playSoundLine(String message, int volume, int speed, int tone) {
//        setSpeeds(speed);
//        playMorseMessage(PLAY_SOUND_LINE, message, volume, tone);
//        PLAY_SOUND_LINE.drain();
//    }

    public static void playSendingDit(int volume) {
        playNote(INPUT_CW_LINE, DOT_DURATION , volume, 600);
        pause(INPUT_CW_LINE, DOT_DURATION);
    }

    public static void playSendingDah(int volume) {
        playNote(INPUT_CW_LINE, DASH_DURATION , volume, 600);
        pause(INPUT_CW_LINE, DOT_DURATION);
    }

    private static void playNote(SourceDataLine line, int duration, int volume, int tone) {
        int length = Note.SAMPLE_RATE * duration / 1000;
        line.write(Note.A4.data(volume, tone), 0, length);  // Playing the note for the specified duration
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

    public byte[] data(int volume, int tone) {
        int durationInSeconds = 1; // Adjust as needed for dit/dah length
        int totalSamples = SAMPLE_RATE * durationInSeconds;

        byte[] sin = new byte[totalSamples];

        // Normalize volume to a clear range to prevent saturation
        int maxAmplitude = (int) (127 * (volume / 100.0)); // Scale amplitude based on volume

        for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / tone; // Calculate the period
            double angle = 2.0 * Math.PI * i / period;   // Calculate the angle
            sin[i] = (byte) (Math.sin(angle) * maxAmplitude); // Generate sine wave with volume control
        }

        return sin; // Return the sine wave data
    }
}