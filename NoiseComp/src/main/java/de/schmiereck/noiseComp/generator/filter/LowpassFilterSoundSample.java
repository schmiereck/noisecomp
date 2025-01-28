package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.SoundSample;

public class LowpassFilterSoundSample extends SoundSample {
    private LowpassFilterGeneratorData data = null;

    public LowpassFilterSoundSample() {
    }

    public LowpassFilterGeneratorData getData() {
        return this.data;
    }

    public void setData(final LowpassFilterGeneratorData data) {
        this.data = data;
    }


}
