package dftDemo1;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.*;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/53937227/sound-is-distorted-after-multiplying-frequency-spectrum-by-constant
 */
class DftDemo1bMain {
    public static void main(String[] args) {
        try {
            // Load the WAV file
            //File wavFile = new File(DftDemo1bMain.class.getResource("/16.wav").getFile());
            File wavFile = new File(DftDemo1bMain.class.getResource("/soundcamp.org/tag/tuned-kick-drums/00s-studio-hip-hop-kickdrum-a-sharp-key-39-qKp.wav").getFile());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
            AudioFormat format = audioInputStream.getFormat();

            if (format.getChannels() != 2) {
                throw new IllegalArgumentException("Only stereo audio is supported");
            }

            byte[] bytes = new byte[audioInputStream.available()];
            int result = audioInputStream.read(bytes);

            // convert bytes to float array
            float[] samples = new float[bytes.length * 8 / format.getSampleSizeInBits()];
            int validSamples = SimpleAudioConversion.decode(bytes, samples, result, format);

            // find nearest power of 2 to zero-pad array in order to use fft
            int power = 0;
            while (Math.pow(2, power) < samples.length / 2)
                power++;

            // divide data into left and right channels
            double[][] left = new double[2][(int) Math.pow(2, power)];
            double[][] right = new double[2][(int) Math.pow(2, power)];

            for (int i = 0; i < samples.length / 2; i++) {
                left[0][i] = samples[2 * i];
                right[0][i] = samples[2 * i + 1];
            }

            //fft
            FastFourierTransformer.transformInPlace(left, DftNormalization.STANDARD, TransformType.FORWARD);
            FastFourierTransformer.transformInPlace(right, DftNormalization.STANDARD, TransformType.FORWARD);

// here I amplify the 0-4kHz frequencies by 12dB
// 0-4kHz is 1/5 of whole spectrum, and since there are negative frequencies in the array
// I iterate over 1/10 and multiply frequencies on both sides of the array
            for (int i = 1; i < left[0].length / 10; i++) {
                double factor = 3.981d; // ratio = 10^(12dB/20)
                //positive frequencies 0-4kHz
                left[0][i] *= factor;
                right[0][i] *= factor;
                left[1][i] *= factor;
                right[1][i] *= factor;

                // negative frequencies 0-4kHz
                left[0][left[0].length - i] *= factor;
                right[0][left[0].length - i] *= factor;
                left[1][left[0].length - i] *= factor;
                right[1][left[0].length - i] *= factor;
            }

            //ifft
            FastFourierTransformer.transformInPlace(left, DftNormalization.STANDARD, TransformType.INVERSE);
            FastFourierTransformer.transformInPlace(right, DftNormalization.STANDARD, TransformType.INVERSE);

            // put left and right channel into array
            float[] samples2 = new float[(left[0].length) * 2];
            for (int i = 0; i < samples2.length / 2; i++) {
                samples2[2 * i] = (float) left[0][i];
                samples2[2 * i + 1] = (float) right[0][i];
            }

            // convert back to byte array which can be played
            byte[] outBytes = new byte[bytes.length];
            int validBytes = SimpleAudioConversion.encode(samples2, outBytes, validSamples, format);

            // Write to audio file
            File outputFile = new File(DftDemo1bMain.class.getResource("output.wav").getFile());

            AudioFormat outAudioFormat = new AudioFormat(format.getEncoding(), format.getSampleRate(),
                    format.getSampleSizeInBits(), format.getChannels(),
                    format.getFrameSize(), format.getFrameRate(), format.isBigEndian());
            //AudioFileFormat outAudioFileFormat = new AudioFileFormat(AudioFileFormat.Type.WAVE, outAudioFormat, validSamples);
            AudioInputStream outAudioInputStream = new AudioInputStream(new ByteArrayInputStream(outBytes), outAudioFormat, validSamples);

            AudioSystem.write(outAudioInputStream, AudioFileFormat.Type.WAVE, outputFile);

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}