package de.schmiereck.noiseComp.soundScheduler;

public class SoundOutSchedulerLogic {
    Thread outThread = null;
    final SoundOutLogic soundOutLogic;

    public SoundOutSchedulerLogic(final SoundSchedulerData soundSchedulerData, final SoundDataLogic soundDataLogic) {
        this.soundOutLogic = new SoundOutLogic(soundSchedulerData, soundDataLogic);
    }

    void startOutThread() {
        if (this.outThread != null) {
            throw new RuntimeException("out thread already startet");
        }
        this.outThread = new Thread(this.soundOutLogic::runOut);

        this.outThread.start();
    }

    public void stopOutThread() {
        this.soundOutLogic.stopRunning();
        this.outThread = null;
    }
    /**
     * @param playbackPosChangedListener
     * 			to add to the {@link SoundOutLogic#addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface)}.
     */
    public void addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface playbackPosChangedListener) {
        this.soundOutLogic.addPlaybackPosChangedListener(playbackPosChangedListener);
    }
}