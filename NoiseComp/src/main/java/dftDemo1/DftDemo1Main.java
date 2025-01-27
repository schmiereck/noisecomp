package dftDemo1;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * https://stackoverflow.com/questions/53937227/sound-is-distorted-after-multiplying-frequency-spectrum-by-constant
 *
 * https://www.youtube.com/watch?v=xV4aQvPLYEY&t=479s
 */
class DftDemo1Main {
    public static void main(String[] args) {
        try {
            //----------------------------------------------------------------------------------------------------------
            // Load the WAV file
            //File wavFile = new File(DftDemo1Main.class.getResource("/16.wav").getFile());
            //File wavFile = new File(DftDemo1Main.class.getResource("/soundcamp.org/tag/tuned-kick-drums/00s-studio-hip-hop-kickdrum-a-sharp-key-39-qKp.wav").getFile());
            File wavFile = new File(DftDemo1Main.class.getResource("/de/schmiereck/noiseComp/drums-kick01-mono.wav").getFile());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
            AudioFormat format = audioInputStream.getFormat();

            System.out.printf("File %s\n", wavFile.getName());
            System.out.printf("format %s\n", format);

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

            //----------------------------------------------------------------------------------------------------------
            // Convert byte array to double array
            //int n = audioBytes.length / 2;
            //int paddedLength = 1;
            //while (paddedLength < n) {
            //    paddedLength *= 2;
            //}
            // find nearest power of 2 to zero-pad array in order to use fft
            int power = 0;
            while (Math.pow(2, power) < samples.length / 2) power++;
            int paddedLength = (int) Math.pow(2, power);

            //double[] audioData = new double[paddedLength];
            //for (int i = 0; i < n; i++) {
            //    // A 16 bit sample is stored in two bytes, little endian.
            //    // Convert 16 bit sample (−32,768 to +32,767) to double (-1.0 to 1.0).
            //    audioData[i] = ((audioBytes[2 * i + 1] << 8) | (audioBytes[2 * i] & 0xff)) / 32768.0D;
            //}
            double[] audioData = new double[paddedLength];
            for (int i = 0; i < paddedLength; i++) {
                audioData[i] = samples[i];
            }
                    // Perform DFT using Apache Commons Math
            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] result = transformer.transform(audioData, TransformType.FORWARD);

            //----------------------------------------------------------------------------------------------------------
            // Anzahl der gewünschten Frequenzbänder
            //int numFreqBands = 1024 * 4;
            int numFreqBands = result.length;
            int bandSize = result.length / numFreqBands;

            double[] resultFreqBandSumArr = new double[numFreqBands];
            Complex[] resultFreqBandSumComplexArr = new Complex[numFreqBands];

            for (int i = 0; i < numFreqBands; i++) {
                double resultFreqBandSum = 0.0D;
                Complex resultFreqBandSumComplex = new Complex(0.0, 0.0);
                for (int j = 0; j < bandSize; j++) {
                    Complex complex = result[i * bandSize + j];
                    double abs = complex.abs();
                    resultFreqBandSum += abs;
                    resultFreqBandSumComplex = resultFreqBandSumComplex.add(complex);
                }
                double bandAverage = resultFreqBandSum / bandSize;
                resultFreqBandSumArr[i] = bandAverage;
                resultFreqBandSumComplexArr[(numFreqBands - 1) - i] = resultFreqBandSumComplex.divide(bandSize);
                //System.out.printf("Band %d: %f\n", i, bandAverage);
            }

            Complex[] result2FreqBandSumComplexArr = transformer.transform(resultFreqBandSumComplexArr, TransformType.INVERSE);

            //----------------------------------------------------------------------------------------------------------
            // Output the results
            //for (int i = 0; i < result.length; i++) {
            //    Complex c = result[i];
            //    System.out.printf("%4d: %s\n", i, c);
            //}

            //displayResult(audioData, resultFreqBandSumArr, resultFreqBandSumComplexArr);
            displayResult(audioData, resultFreqBandSumArr, resultFreqBandSumComplexArr, result2FreqBandSumComplexArr);

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayResult(double[] audioData, double[] resultFreqBandSumArr,
                                      Complex[] resultFreqBandSumComplexArr, Complex[] result2FreqBandSumComplexArr) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("DFT Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int midY = height / 2;
                int mid2Y = midY / 2;

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, width, height);

                double max = 0;
                for (int i = 0; i < resultFreqBandSumArr.length; i += 1) {
                    double abs = resultFreqBandSumArr[i];
                    max = Math.max(max, abs);
                }

                g2d.setColor(Color.GREEN);
                for (int i = 0; i < resultFreqBandSumArr.length; i += 1) {
                    double abs = resultFreqBandSumArr[i];
                    int x = i * width / resultFreqBandSumArr.length;
                    int y = midY - (int) (abs / max * midY);
                    g2d.drawLine(x, midY, x, y);
                }
                // Input + Result zero line:
                {
                    g2d.setColor(Color.GRAY);
                    int x = (width);
                    int y = (height - mid2Y);
                    g2d.drawLine(0, y, x, y);
                }
                // Input:
                {
                    g2d.setColor(Color.RED);
                    int lastX = 0;
                    int lastY = height - mid2Y;
                    for (int i = 0; i < audioData.length; i += 1) {
                        double sampleValue = audioData[i];
                        int x = i * width / audioData.length;
                        int y = (height - mid2Y) - (int) (sampleValue * mid2Y);
                        g2d.drawLine(lastX, lastY, x, y);
                        lastX = x;
                        lastY = y;
                    }
                }
                // Result:
                {
                    g2d.setColor(Color.BLUE);
                    double lastSampleValue = 0.0D;
                    int lastX = 0;
                    int lastY = height - mid2Y;
                    for (int i = 0; i < resultFreqBandSumComplexArr.length; i += 1) {
                        double t = i;
                        //https://stackoverflow.com/questions/40775602/how-to-use-complex-coefficient-in-apache-fft
                        int d = 1;
                        //double k = Math.PI / (resultFreqBandSumComplexArr.length / d);
                        double k = (Math.PI * 2.0D) / (resultFreqBandSumComplexArr.length - 1);
                        double sampleValue = resultFreqBandSumComplexArr[0].getReal();
                        for (int m = 1; m < resultFreqBandSumComplexArr.length / 1; m++) {
                            double phase = t * k * m;
                            sampleValue +=
                                    d * resultFreqBandSumComplexArr[m].getReal() * Math.cos(phase) +
                                    d * resultFreqBandSumComplexArr[m].getImaginary() * Math.sin(phase);
                        }
                        int x = (i * width) / (resultFreqBandSumComplexArr.length);
                        int y = (height - mid2Y) - (int) ((sampleValue / (resultFreqBandSumComplexArr.length)) * mid2Y);
                        //int y = (height - mid2Y) - (int) ((sampleValue / audioData.length) * mid2Y);
                        g2d.drawLine(lastX, lastY, x, y);
                        lastSampleValue = sampleValue;
                        lastY = y;
                        lastX = x;
                    }
                }
                // Result 2:
                {
                    final int ql = result2FreqBandSumComplexArr.length / 4;
                    final int ql2 = ql * 2;
                    final int ql3 = ql * 3;
                    g2d.setColor(Color.ORANGE);
                    int lastX = 0;
                    int lastY = height - mid2Y;
                    for (int i = 0; i < result2FreqBandSumComplexArr.length / 1; i += 1) {
                        double real = result2FreqBandSumComplexArr[i].getReal();
                        double abs = result2FreqBandSumComplexArr[i].abs();
                        double sampleValue;
                        // negative frequencies?
                        if (i < ql3) {
                            if (real < 0.0D) {
                                sampleValue = abs;
                            } else {
                                sampleValue = -abs;
                            }
                        } else {
                            if (real < 0.0D) {
                                sampleValue = -abs;
                            } else {
                                sampleValue = abs;
                            }
                        }

                        int x = width - (i * width) / (result2FreqBandSumComplexArr.length);
                        int y = (height - mid2Y) - (int) ((sampleValue) * mid2Y);
                        g2d.drawLine(lastX, lastY, x, y);
                        lastY = y;
                        lastX = x;
                    }
                }
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    });
}
}