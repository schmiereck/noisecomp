package de.schmiereck.noiseComp.soundScheduler;

/**
 * <p>
 * Pipeline-Scheduler der angegebenen Sound
 * bei jedem Aufruf abspielt ({@link SoundOutLogic#notifyRunSchedulOut()}).<br/>
 * Sorgt dafür, das der Sound-Buffer durch den zweiten Thread 
 * immer gefüllt ist ({@link SoundCalcLogic#notifyRunSchedulCalc()}).
 * </p>
 * <p>
 * 	Starts two Threads to manage a pipeline.<br/>
 * 	This two threads are named &quot;calc&quot; and &quot;out&quot;.<br/>
 * </p>
 * <p>
 * 	The &quot;out&quot; outThread gives the calculated stream to output
 * 	and manages the time to the next time slice.
 * </p>
 * <p>
 * 	The &quot;calc&quot; outThread is called twice as often as the &quot;out&quot;
 * 	outThread, to calculate the data in the buffers.
 * </p>
 *
 * @author smk
 * @version 26.01.2004
 */
public class SoundSchedulerLogic 
{
	//**********************************************************************************************
	// Fields:
	private final SoundOutSchedulerLogic soundOutSchedulerLogic;
	private final SoundCalcSchedulerLogic soundCalcSchedulerLogic;

	private final SoundSchedulerData soundSchedulerData;

	/**
	 * Sound data to play.
	 */
	private final SoundDataLogic soundDataLogic;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param soundDataLogic
	 * 			is the Sound data to play.
	 */
	public SoundSchedulerLogic(final SoundSchedulerData soundSchedulerData, final SoundDataLogic soundDataLogic) {
		//==========================================================================================
		this.soundOutSchedulerLogic = new SoundOutSchedulerLogic(soundSchedulerData, soundDataLogic);
		this.soundCalcSchedulerLogic = new SoundCalcSchedulerLogic(soundSchedulerData, soundDataLogic);

		this.soundSchedulerData = soundSchedulerData;
		this.soundDataLogic = soundDataLogic;

		//==========================================================================================
	}
	public void startThread() {
		this.soundCalcSchedulerLogic.startCalcThread(this::run);

		this.soundOutSchedulerLogic.startOutThread();
	}

	private void run() {
		this.soundCalcSchedulerLogic.runCalc();

		this.soundOutSchedulerLogic.stopOutThread();
	}

	public void stopThread() {
		this.soundOutSchedulerLogic.stopOutThread();

		this.soundCalcSchedulerLogic.stopCalcThread();
	}

	public void startPlayback() {
		this.soundDataLogic.startPlayback();
	}
	
	public void pausePlayback() {
		//==========================================================================================
		this.soundDataLogic.pausePlayback();
		this.soundSchedulerData.setPlaybackPaused(true);
		//==========================================================================================
	}
	
	public void resumePlayback() {
		//==========================================================================================
		this.soundSchedulerData.setPlaybackPaused(false);
		this.soundDataLogic.resumePlayback();
		//==========================================================================================
	}
	
	public void stopPlayback() {
		this.soundDataLogic.stopPlayback();
	}

	/**
	 * @param playbackPosChangedListener 
	 * 			to add to the {@link SoundOutLogic#addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface)}.
	 */
	public void addPlaybackPosChangedListener(final PlaybackPosChangedListenerInterface playbackPosChangedListener) {
		this.soundOutSchedulerLogic.addPlaybackPosChangedListener(playbackPosChangedListener);
	}

	/**
	 * @return
	 * 			<code>true</code> if sound played (resumed or playing).
	 */
	public synchronized boolean retrievePlaySound() {
		return this.soundSchedulerData.getPlaySound();
	}

	/**
	 * @return
	 * 			<code>true</code> if sound played (resumed or playing).
	 */
	public synchronized void submitPlaySound(final boolean playSound) {
		this.soundSchedulerData.setPlaySound(playSound);
	}
}
