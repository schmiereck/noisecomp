package dftDemo1;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * https://stackoverflow.com/questions/53937227/sound-is-distorted-after-multiplying-frequency-spectrum-by-constant
 */
class DftDemo1Main {
    public static void main(String[] args) {
        try {
            // Load the WAV file
            //File wavFile = new File(DftDemo1Main.class.getResource("/16.wav").getFile());
            File wavFile = new File(DftDemo1Main.class.getResource("/soundcamp.org/tag/tuned-kick-drums/00s-studio-hip-hop-kickdrum-a-sharp-key-39-qKp.wav").getFile());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
            AudioFormat format = audioInputStream.getFormat();

            if (format.getChannels() != 1) {
                throw new IllegalArgumentException("Only mono audio is supported");
            }

            //byte[] audioBytes = Files.readAllBytes(wavFile.toPath());

            //byte[] audioBytes = new byte[(int) (audioInputStream.getFrameLength() * format.getFrameSize())];
            //audioInputStream.read(audioBytes);
            byte[] audioBytes = new byte[audioInputStream.available()];
            int resultN = audioInputStream.read(audioBytes);

            //float[] samples = new float[bytes.length * 8 / fmt.getSampleSizeInBits()];
            //int validSamples = SimpleAudioConversion.decode(bytes, samples, result, fmt);
            float[] samples = new float[audioBytes.length * 8 / format.getSampleSizeInBits()];
            int validSamples = SimpleAudioConversion.decode(audioBytes, samples, resultN, format);


            // Convert byte array to double array
            int n = audioBytes.length / 2;
            int paddedLength = 1;
            while (paddedLength < n) {
                paddedLength *= 2;
            }
            // find nearest power of 2 to zero-pad array in order to use fft
            //int power = 0;
            //while (Math.pow(2, power) < samples.length / 2) power++;

            double[] audioData = new double[paddedLength];
            for (int i = 0; i < n; i++) {
                // A 16 bit sample is stored in two bytes, little endian.
                // Convert 16 bit sample (−32,768 to +32,767) to double (-1.0 to 1.0).
                audioData[i] = ((audioBytes[2 * i + 1] << 8) | (audioBytes[2 * i] & 0xff)) / 32768.0D;
            }

            // Perform DFT using Apache Commons Math
            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] result = transformer.transform(audioData, TransformType.FORWARD);

            // Output the results
            for (int i = 0; i < result.length; i++) {
                Complex c = result[i];
                System.out.printf("%4d: %s\n", i, c);
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}