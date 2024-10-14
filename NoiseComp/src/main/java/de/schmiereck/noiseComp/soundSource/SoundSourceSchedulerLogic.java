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
	final SoundSourceData soundSourceData;
	final SoundSourceLogic soundSourceLogic;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundSourceSchedulerLogic(final SoundSourceData soundSourceData,
									 final SoundSourceLogic soundSourceLogic,
									 int targetFramesPerSecond)
	{
		super(targetFramesPerSecond);
		synchronized (this)
		{
			this.soundSourceData = soundSourceData;
			this.soundSourceLogic = soundSourceLogic;
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#notifyRunSchedul(long)
	 */
	public void notifyRunSchedul(long actualWaitPerFramesMillis) {
		synchronized (this) {
			if (this.soundSourceLogic != null) {
				long emptyBufferSize = this.soundSourceLogic.getEmptyBufferSize();
				
				if (emptyBufferSize > 0) {
					System.out.println("CALC: %d (%d)".formatted(this.soundSourceLogic.getEmptyBufferStart(), actualWaitPerFramesMillis));

					this.soundSourceLogic.pollCalcFillBuffer(soundSourceData, actualWaitPerFramesMillis);
				}
			}
		}
	}
}
