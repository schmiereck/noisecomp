package de.schmiereck.noiseComp.service;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.noiseComp.soundScheduler.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundScheduler.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundScheduler.SoundSchedulerData;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;

public class PlaySoundService {
    //**********************************************************************************************
    // Fields:

    private final SoundDataLogic soundDataLogic;

    private final SoundSchedulerLogic soundSchedulerLogic;

    //**********************************************************************************************
    // Functions:

    public PlaySoundService(final SoundDataLogic soundDataLogic) {
        this.soundDataLogic = soundDataLogic;

        final SoundSchedulerData soundSchedulerData = new SoundSchedulerData(25);
        this.soundSchedulerLogic = new SoundSchedulerLogic(soundSchedulerData, this.soundDataLogic);
    }

    /**
     * @return
     * 			<code>true</code> if sound played (resumed or playing).
     */
    public synchronized boolean retrievePlaySound() {
        //==========================================================================================
        return this.soundSchedulerLogic.retrievePlaySound();
    }

    /**
     * @param startTime
     * 			is the start time in seconds.
     * @param playbackPosChangedListener
     * 			is the playbackPosChangedListener.
     */
    public synchronized void startPlayback(final SoundSourceData soundSourceData,
                                           final double startTime,
                                           final PlaybackPosChangedListenerInterface playbackPosChangedListener) {
        //==========================================================================================
        //SoundDataLogic soundDataLogic = SwingMain.getSoundData();

        this.submitPlaybackPos((float)startTime);

        this.soundSchedulerLogic.addPlaybackPosChangedListener(playbackPosChangedListener);

        this.soundSchedulerLogic.startThread(soundSourceData);

        this.soundSchedulerLogic.startPlayback();

        this.soundSchedulerLogic.submitPlaySound(true);

        //==========================================================================================
    }

    public synchronized void resumePlayback() {
        //==========================================================================================
        this.soundSchedulerLogic.resumePlayback();
        //==========================================================================================
    }

    public synchronized void stopPlayback() {
        //==========================================================================================
        if (this.soundSchedulerLogic.retrievePlaySound()) {
            System.out.println("soundSchedulerLogic.stopPlayback: BEGIN");
            this.soundSchedulerLogic.stopPlayback();
            System.out.println("soundSchedulerLogic.stopPlayback: END");

            System.out.println("soundSchedulerLogic.stopThread: BEGIN");
            this.soundSchedulerLogic.stopThread();
            System.out.println("soundSchedulerLogic.stopThread: END");

            this.soundSchedulerLogic.submitPlaySound(false);

            //--------------------------------------------------------------------------------------
            // Set to start marker.

            // TODO Set to start time and notify listeners.

//			TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
//
//			double startTime = startTimeMarkerSelectEntryModel.getTimeMarker();
//
//			this.doPlaybackTimeChanged((float)startTime); //0.0F);
        }
        //==========================================================================================
    }

    public synchronized void pauseSound() {
        //==========================================================================================
        if (this.soundSchedulerLogic.retrievePlaySound()) {
            this.soundSchedulerLogic.pausePlayback();
        }
        //==========================================================================================
    }

    /**
     * @param playbackPos
     * 			is the playbackPos in seconds.
     */
    public synchronized void submitPlaybackPos(final double playbackPos) {
        //==========================================================================================
        final SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

        soundBufferManager.setActualTime((float)playbackPos);

        //==========================================================================================
    }
}
