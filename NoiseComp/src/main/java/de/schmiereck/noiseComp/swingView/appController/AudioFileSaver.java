package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.timeline.Timeline;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AudioFileSaver {

    public static void saveToFileAsFloatPcm(final SoundSourceData soundSourceData, final File file) {
        final Timeline outputTimeline = soundSourceData.getOutputTimeline();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            for (long sampleFramePos = 0; sampleFramePos < outputTimeline.getBufSoundSamplesCount(); sampleFramePos++) {
                final SoundSample bufSoundSample = outputTimeline.getBufSoundSample(sampleFramePos);

                byteArrayOutputStream.write(floatToByteArray(bufSoundSample.getLeftValue()));
                byteArrayOutputStream.write(floatToByteArray(bufSoundSample.getRightValue()));

                //audioInputStream.write(sampleValue);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        //this.soundSourceLogic
        //SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

        final byte[] audioBytes = byteArrayOutputStream.toByteArray();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);

        // AudioInputStream aus ByteArrayInputStream erstellen:
        final float soundFrameRate = outputTimeline.getSoundFrameRate(); // 44100.0f;
        final int sampleSizeInBits = 32;
        final int channels = 2;
        final int frameSize = sampleSizeInBits / 8 * channels; // 4 Bytes pro Kanal * 2 Kanäle
        final float frameRate = soundFrameRate;
        final boolean bigEndian = false;
        final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, soundFrameRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        final AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioBytes.length / audioFormat.getFrameSize());

        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToFileAs16BitPcm(final SoundSourceData soundSourceData, final File file) {
        final Timeline outputTimeline = soundSourceData.getOutputTimeline();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            for (long sampleFramePos = 0; sampleFramePos < outputTimeline.getBufSoundSamplesCount(); sampleFramePos++) {
                final SoundSample bufSoundSample = outputTimeline.getBufSoundSample(sampleFramePos);

                byteArrayOutputStream.write(floatToPcm16ByteArray(bufSoundSample.getLeftValue()));
                byteArrayOutputStream.write(floatToPcm16ByteArray(bufSoundSample.getRightValue()));

                //audioInputStream.write(sampleValue);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        //this.soundSourceLogic
        //SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

        final byte[] audioBytes = byteArrayOutputStream.toByteArray();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);

        // AudioInputStream aus ByteArrayInputStream erstellen:
        final float soundFrameRate = outputTimeline.getSoundFrameRate(); // 44100.0f;
        final int sampleSizeInBits = 16;
        final int channels = 2;
        final int frameSize = sampleSizeInBits / 8 * channels; // 2 Bytes pro Kanal * 2 Kanäle
        final float frameRate = soundFrameRate;
        final boolean bigEndian = false;
        final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, soundFrameRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        final AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioBytes.length / audioFormat.getFrameSize());

        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] floatToByteArray(final float value) {
        final int intBits = Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits),
                (byte) (intBits >> 8),
                (byte) (intBits >> 16),
                (byte) (intBits >> 24)
        };
    }

    private static byte[] floatToPcm16ByteArray(final float value) {
        // Annahme: float-Werte sind im Bereich -1.0f bis 1.0f
        int intVal = Math.round(value * 32767); // Skalieren auf 16-Bit PCM Bereich (-32768 bis 32767)

        // Clipping, um sicherzustellen, dass Werte innerhalb des erlaubten Bereichs liegen
        intVal = Math.max(-32768, Math.min(32767, intVal));

        // Little Endian: Niedrigwertiges Byte zuerst
        return new byte[]{
                (byte) (intVal & 0xFF),          // Low-Byte
                (byte) ((intVal >> 8) & 0xFF)    // High-Byte
        };
    }
}
