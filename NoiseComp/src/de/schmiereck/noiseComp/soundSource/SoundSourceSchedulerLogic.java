package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.screenTools.scheduler.SchedulerLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceSchedulerLogic
	extends SchedulerLogic
{
	SoundSourceLogic soundSourceLogic	= null;
	
	/**
	 * Constructor.
	 * 
	 * @param targetFramesPerSecond
	 */
	public SoundSourceSchedulerLogic(int targetFramesPerSecond)
	{
		super(targetFramesPerSecond);
	}

	/**
	 * @param soundSourceLogic is the new value for attribute {@link #soundSourceLogic} to set.
	 */
	public void setSoundSourceLogic(SoundSourceLogic soundSourceLogic)
	{
		synchronized (this)
		{
			this.soundSourceLogic = soundSourceLogic;
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#notifyRunSchedul(long)
	 */
	public void notifyRunSchedul(long actualWaitPerFramesMillis)
	{
		synchronized (this)
		{
			if (this.soundSourceLogic != null)
			{
				this.soundSourceLogic.pollCalcFillBuffer(actualWaitPerFramesMillis);
			}
		}
	}
}
