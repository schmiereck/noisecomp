package de.schmiereck.noiseComp.generator.signal;

import de.schmiereck.noiseComp.generator.SoundSample;

public class OscillatorSoundSample extends SoundSample {
    private float frequency;
    private float amplitude;
    /**
     * Phase in the range [0, 1)
     */
    private float phase;

    public OscillatorSoundSample() {
    }

    public float getFrequency() {
        return this.frequency;
    }

    public void setFrequency(final float frequency) {
        this.frequency = frequency;
    }

    public float getAmplitude() {
        return this.amplitude;
    }

    public void setAmplitude(final float amplitude) {
        this.amplitude = amplitude;
    }

    public float getPhase() {
        return this.phase;
    }

    public void setPhase(final float phase) {
        this.phase = phase;
    }
}
