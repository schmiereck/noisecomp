package de.schmiereck.noiseComp.generator;

/**
 * Buffers all output samples of a generator (over the length of the generator). 
 *
 * @author smk
 * @version <p>14.04.2004: created, smk</p>
 */
public class GeneratorFullBuffer
	extends GeneratorBuffer
{
	private SoundSample[] bufferedSoundSamples		= null;

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBuffer#readBuffer(long)
	 */
	public synchronized SoundSample readBuffer(long framePosition)
	{
		SoundSample soundSample;
		
		if (this.bufferedSoundSamples != null)
		{	
			soundSample = this.bufferedSoundSamples[(int)(framePosition - this.getStartPosition())];
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
		if (this.bufferedSoundSamples == null)
		{	
			this.bufferedSoundSamples = new SoundSample[(int)(this.getEndPosition() - this.getStartPosition())];
		}
		this.bufferedSoundSamples[(int)(framePosition - this.getStartPosition())] = soundSample;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBuffer#setEndPosition(long)
	 */
	public synchronized void setEndPosition(long endPosition)
	{
		this.bufferedSoundSamples = null;
		super.setEndPosition(endPosition);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBuffer#setStartPosition(long)
	 */
	public synchronized void setStartPosition(long startPosition)
	{
		this.bufferedSoundSamples = null;
		super.setStartPosition(startPosition);
	}
}
