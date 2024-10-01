package de.schmiereck.noiseComp.generator;

public abstract class GenratorUtils {

    private GenratorUtils() {
    }

    public static float calcPeriodFadeValue(final float generatorStartTimePos, final float generatorEndTimePos,
                                             final float soundFrameRate, float frameTime, float periodLengthInFrames) {
        // Relativer Zeitpunkt im Generator.
        final float relToStartTime = frameTime - generatorStartTimePos;
        final float relToEndTime = generatorEndTimePos - frameTime;

        final float relToStartInFrames = relToStartTime * soundFrameRate;
        final float relToEndInFrames = relToEndTime * soundFrameRate;

        final float fadeValue;
        if ((relToStartInFrames >= 0.0F) && (relToStartInFrames < periodLengthInFrames)) {
            fadeValue = (relToStartInFrames / periodLengthInFrames);
        } else {
            if ((relToEndInFrames >= 0.0F) && (relToEndInFrames < periodLengthInFrames)) {
                fadeValue = (relToEndInFrames / periodLengthInFrames);
            } else {
                fadeValue = 1.0F;
            }
        }
        return fadeValue;
    }
}
