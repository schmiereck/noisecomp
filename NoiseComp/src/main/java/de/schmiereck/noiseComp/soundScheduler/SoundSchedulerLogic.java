package de.schmiereck.noiseComp.soundScheduler;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;

/**
 * <p>
 * Pipeline-Scheduler der angegebenen Sound
 * bei jedem Aufruf abspielt ({@link OutLogic#notifyRunSchedulOut()}).<br/>
 * Sorgt dafür, das der Sound-Buffer durch den zweiten Thread 
 * immer gefüllt ist ({@link #notifyRunSchedulCalc(long)}).
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
	Thread calcThread = null;
	final CalcLogic calcLogic;

	Thread outThread = null;
	final OutLogic outLogic;

	final SoundSchedulerData soundSchedulerData;

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
		this.outLogic = new OutLogic(soundSchedulerData, soundDataLogic);
		this.calcLogic = new CalcLogic(soundSchedulerData, this);

		this.soundSchedulerData = soundSchedulerData;
		this.soundDataLogic = soundDataLogic;

		//==========================================================================================
	}
	public void startThread() {
		if (this.calcThread != null) {
			throw new RuntimeException("calc thread already startet");
		}
		if (this.outThread != null) {
			throw new RuntimeException("out thread already startet");
		}

		//final CalcLogic calcLogic = new CalcLogic(this);

		//this.calcThread = new Thread(calcLogic::runCalc);
		this.calcThread = new Thread(this::run);

		this.calcThread.start();

		this.startOutThread();
	}

	private void run() {
		this.calcLogic.runCalc();

		this.outLogic.stopRunning();
		this.outThread = null;
	}

	private void startOutThread() {
		this.outThread = new Thread(this.outLogic::runOut);

		this.outThread.start();
	}

	public void stopThread() {
		if (this.outThread == null) {
			throw new RuntimeException("out thread not startet");
		}

		this.outLogic.stopRunning();
		this.outThread = null;

		if (this.calcThread == null) {
			throw new RuntimeException("calc thread not startet");
		}

		this.calcLogic.stopRunning();
		this.calcThread = null;
	}

	//public abstract void notifyRunSchedulOut();

	public CalcLogic getCalcLogic() {
		return this.calcLogic;
	}

	public OutLogic getOutLogic() {
		return this.outLogic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic#notifyRunSchedulCalc(long)
	 */
	public void notifyRunSchedulCalc(long actualWaitPerFramesMillis) {
		//==========================================================================================
		if (this.soundSchedulerData.getPlaybackPaused() == false)
		{	
			SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();

			soundBufferManager.pollGenerate();
		}
		//==========================================================================================
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
	 * 			to add to the {@link OutLogic#addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface)}.
	 */
	public void addPlaybackPosChangedListener(PlaybackPosChangedListenerInterface playbackPosChangedListener) {
		this.getOutLogic().addPlaybackPosChangedListener(playbackPosChangedListener);
	}

	/**
	 * @param playbackPos
	 * 			is the playbackPos in seconds.
	 */
	public void submitPlaybackPos(float playbackPos) {
		//==========================================================================================
		SoundBufferManager soundBufferManager = this.soundDataLogic.getSoundBufferManager();
		
		//==========================================================================================
		soundBufferManager.setActualTime(playbackPos);
		
		//==========================================================================================
	}
}
