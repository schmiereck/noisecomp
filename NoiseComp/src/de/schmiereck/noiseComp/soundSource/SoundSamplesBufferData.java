package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSamplesBufferData
{
	private long			emptyBufferStart	= 0;
	private long			emptyBufferEnd		= 0;
	private SoundSample[]	bufferSoundSamples = null;
	
	public void createBuffer(float timeLen, float frameRate)
	{
		int frames = (int)(timeLen * frameRate);

		this.bufferSoundSamples = new SoundSample[(int)frames];
		
		for (int pos = 0; pos < frames; pos++)
		{
			this.bufferSoundSamples[pos] = null;
		}
		
		this.emptyBufferStart = 0;
		this.emptyBufferEnd = frames;
	}

	public SoundSample get(long frame, OutputGenerator outputGenerator)
	{
		SoundSample soundSample;
		
		if (frame < this.bufferSoundSamples.length)
		{
			soundSample = this.bufferSoundSamples[(int)frame];

			if (soundSample == null)
			{
				soundSample = outputGenerator.generateFrameSample(frame, null);
			
				this.bufferSoundSamples[(int)frame] = soundSample;
			}
		}
		else
		{
			soundSample = null;
		}
		
		return soundSample;
	}

	/**
	 * @param partTime
	 * @param outputGenerator
	 */
	public void calcWaitingSamplesPart(float partTime, OutputGenerator outputGenerator)
	{
		long framePos;
		long frames = (long)(partTime * outputGenerator.getFrameRate());
		
		for (framePos = this.emptyBufferStart; framePos < this.emptyBufferEnd; framePos++)
		{
			if (frames <= 0)
			{
				break;
			}
			frames--;

			SoundSample soundSample = outputGenerator.generateFrameSample(framePos, null);
		
			this.bufferSoundSamples[(int)framePos] = soundSample;
		}
		
		this.emptyBufferStart = framePos;
		//System.err.println("POLL framePos:" + framePos);
		
		if (this.emptyBufferStart >= this.emptyBufferEnd)
		{
			this.emptyBufferStart = 0;
			this.emptyBufferEnd = 0;
		}
	}
}
