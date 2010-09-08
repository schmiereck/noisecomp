package de.schmiereck.noiseComp.generator;

/**
 * Generator Single Buffer.
 *
 * @author smk
 * @version <p>14.04.2004: created, smk</p>
 */
public class GeneratorSingleBuffer
extends GeneratorBuffer
{
	/**
	 * Wenn der Wert eines Samples berechnet wurde,
	 * wird er in {@link #bufferedSoundSample} abgelegt und der
	 * Frame zu dem er geh�rt in dieser Varaiablen abgelegt.<br/>
	 * Die Berechnung wird nur angestossen, wenn die nachgefragte Frame-Position
	 * nicht der hier abgelegten entspricht.
	 */
	private long bufferedFramePosition	= -1;
	
	/**
	 * @see #bufferedFramePosition
	 */
	private SoundSample bufferedSoundSample		= null;
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBuffer#readBuffer(long)
	 */
	public synchronized SoundSample readBuffer(long framePosition)
	{
		SoundSample soundSample;
		
		if (this.bufferedFramePosition == framePosition)
		{
			soundSample = this.bufferedSoundSample;
		}
		else
		{
			soundSample = null;
		}
		
		return soundSample;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBuffer#writeBuffer(long, de.schmiereck.noiseComp.generator.SoundSample)
	 */
	public synchronized void writeBuffer(long framePosition, SoundSample soundSample)
	{
		this.bufferedFramePosition = framePosition;

		this.bufferedSoundSample = soundSample;
	}

}
