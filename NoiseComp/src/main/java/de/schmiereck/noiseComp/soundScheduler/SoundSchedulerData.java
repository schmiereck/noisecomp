package de.schmiereck.noiseComp.soundScheduler;

public class SoundSchedulerData {

    /**
     * Gewünschte Framerate:<br/>
     * Anzahl der Frames pro Sekunde die dargestellt werden sollen.
     */
    private int targetFramesPerSecond;

    /**
     * Milliseconds actually calculated for the out out-Thread to wait between
     * the calls of {@link SoundOutLogic#notifyRunSchedulOut()}.<br/>
     * Is also used from the calc-Thread!
     */
    private long actualWaitPerFramesMillis	= 0;

    private boolean playbackPaused = false;
    private boolean playSound = false;

    /**
     * Constructor.
     *
     * @param targetFramesPerSecond
     * 			are the Count of frames per second.
     */
    public SoundSchedulerData(final int targetFramesPerSecond) {
        this.targetFramesPerSecond = targetFramesPerSecond;
    }

    public long getActualWaitPerFramesMillis() {
        return this.actualWaitPerFramesMillis;
    }

    public void setActualWaitPerFramesMillis(final long actualWaitPerFramesMillis) {
        this.actualWaitPerFramesMillis = actualWaitPerFramesMillis;
    }

    public boolean getPlaybackPaused() {
        return this.playbackPaused;
    }

    public void setPlaybackPaused(final boolean playbackPaused) {
        this.playbackPaused = playbackPaused;
    }

    public int getTargetFramesPerSecond() {
        return this.targetFramesPerSecond;
    }

    public boolean getPlaySound() {
        return this.playSound;
    }

    public void setPlaySound(final boolean playSound) {
        this.playSound = playSound;
    }
}
