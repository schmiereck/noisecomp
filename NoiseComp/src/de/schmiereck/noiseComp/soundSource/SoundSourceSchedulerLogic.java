package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.screenTools.scheduler.SchedulerLogic;

/**
 * Scheduler der den Puffer der angegebenen Sound-Source 
 * bei jedem Aufruf mit berechneten Werten füllt. 
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
				System.out.println("CALC: " + this.soundSourceLogic.getEmptyBufferStart() + 
				                   " (" + actualWaitPerFramesMillis + ")");

				this.soundSourceLogic.pollCalcFillBuffer(actualWaitPerFramesMillis);
			}
		}
	}
}
