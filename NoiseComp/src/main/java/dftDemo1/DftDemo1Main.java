package dftDemo1;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class DftDemo1 {
    public static void main(String[] args) {
        try {
            // Load the WAV file
            File wavFile = new File(DftDemo1.class.getResource("/16.wav").getFile());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
            AudioFormat format = audioInputStream.getFormat();
            byte[] audioBytes = Files.readAllBytes(wavFile.toPath());

            // Convert byte array to double array
            int n = audioBytes.length / 2;
            int paddedLength = 1;
            while (paddedLength < n) {
                paddedLength *= 2;
            }
            double[] audioData = new double[paddedLength];
            for (int i = 0; i < n; i++) {
                audioData[i] = ((audioBytes[2 * i + 1] << 8) | (audioBytes[2 * i] & 0xff)) / 32768.0;
            }

            // Perform DFT using Apache Commons Math
            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] result = transformer.transform(audioData, TransformType.FORWARD);

            // Output the results
            for (Complex c : result) {
                System.out.println(c);
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}