package de.schmiereck.noiseComp.service;

import de.schmiereck.noiseComp.soundData.PlaybackPosChangedListenerInterface;
import de.schmiereck.noiseComp.soundData.SoundDataLogic;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.swingView.SwingMain;

public class PlaySoundService {
    //**********************************************************************************************
    // Fields:

    private final SoundDataLogic soundDataLogic;

    private SoundSchedulerLogic soundSchedulerLogic = null;

    //**********************************************************************************************
    // Functions:

    public PlaySoundService(final SoundDataLogic soundDataLogic) {
        this.soundDataLogic = soundDataLogic;
    }

    /**
     * @return
     * 			returns the {@link #soundSchedulerLogic}.
     */
    public synchronized SoundSchedulerLogic getSoundSchedulerLogic()
    {
        return this.soundSchedulerLogic;
    }

    /**
     * @param soundSchedulerLogic
     * 			to set {@link #soundSchedulerLogic}.
     */
    public synchronized void setSoundSchedulerLogic(SoundSchedulerLogic soundSchedulerLogic)
    {
        this.soundSchedulerLogic = soundSchedulerLogic;
    }

    /**
     * @return
     * 			<code>true</code> if sound played (resumed or playing).
     */
    public synchronized boolean getPlaySound()
    {
        //==========================================================================================
        boolean playSound;

        if (this.soundSchedulerLogic != null)
        {
            playSound = true;
        }
        else
        {
            playSound = false;
        }

        //==========================================================================================
        return playSound;
    }

    /**
     * @param startTime
     * 			is the start time in seconds.
     * @param playbackPosChangedListener
     * 			is the playbackPosChangedListener.
     */
    public synchronized void startPlayback(double startTime,
                                           PlaybackPosChangedListenerInterface playbackPosChangedListener)
    {
        //==========================================================================================
        //SoundDataLogic soundDataLogic = SwingMain.getSoundData();

        this.soundSchedulerLogic = new SoundSchedulerLogic(25, this.soundDataLogic);

        this.soundSchedulerLogic.submitPlaybackPos((float)startTime);

        this.soundSchedulerLogic.addPlaybackPosChangedListener(playbackPosChangedListener);

        this.soundSchedulerLogic.startThread();

        this.soundSchedulerLogic.startPlayback();

        //==========================================================================================
    }

    /**
     *
     */
    public synchronized void resumePlayback()
    {
        //==========================================================================================
        this.soundSchedulerLogic.resumePlayback();
        //==========================================================================================
    }

    /**
     *
     */
    public synchronized void stopPlayback()
    {
        //==========================================================================================
        if (this.soundSchedulerLogic != null) {
            System.out.println("soundSchedulerLogic.stopPlayback: BEGIN");
            this.soundSchedulerLogic.stopPlayback();
            System.out.println("soundSchedulerLogic.stopPlayback: END");

            System.out.println("soundSchedulerLogic.stopThread: BEGIN");
            this.soundSchedulerLogic.stopThread();
            System.out.println("soundSchedulerLogic.stopThread: END");

            this.soundSchedulerLogic = null;

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

    /**
     *
     */
    public synchronized void pauseSound()
    {
        //==========================================================================================
        if (this.soundSchedulerLogic != null)
        {
            this.soundSchedulerLogic.pausePlayback();
        }
        //==========================================================================================
    }

    /**
     * @param playbackPos
     * 			is the playbackPos in seconds.
     */
    public synchronized void submitPlaybackPos(double playbackPos)
    {
        //==========================================================================================
        if (this.soundSchedulerLogic != null)
        {
            this.soundSchedulerLogic.submitPlaybackPos((float)playbackPos);
        }
        //==========================================================================================
    }
}
