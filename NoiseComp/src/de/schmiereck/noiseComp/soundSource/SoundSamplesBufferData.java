package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.Generator;
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
	private SoundSample[]	bufferSoundSamples	= null;
	private float 			frameRate			= 0F;
	
	public void createBuffer(float timeLen, float frameRate)
	{
		synchronized (this)
		{
			long frames = (long)(timeLen * frameRate);
	
			this.bufferSoundSamples = new SoundSample[(int)frames];
			this.frameRate = frameRate;
			
			for (long pos = 0; pos < frames; pos++)
			{
				this.bufferSoundSamples[(int)pos] = null;
			}
			
			this.emptyBufferStart = 0;
			this.emptyBufferEnd = frames;
		}
	}

	public void clearBuffer(float startTimePos, float endTimePos)
	{
		synchronized (this)
		{
System.out.println("clearBuffer: " + startTimePos + ", " + endTimePos);
			long startFrame = (int)(startTimePos * this.frameRate);
			long endFrame = (int)(endTimePos * this.frameRate);
	
			for (long pos = startFrame; pos < endFrame; pos++)
			{
				this.bufferSoundSamples[(int)pos] = null;
			}
			
			this.emptyBufferStart 	= Math.min(this.emptyBufferStart, startFrame);
			this.emptyBufferEnd 	= Math.max(this.emptyBufferEnd, endFrame);
		}
	}

	public SoundSample get(long frame, Generator outputGenerator)
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
	 * Generates the next part of samples and store them in the buffer.
	 * 
	 * @param partTime
	 * 			says in milliseconds how long is the time part the buffer is filled. 
	 * @param outputGenerator
	 */
	public void calcWaitingSamplesPart(float partTime, Generator outputGenerator)
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
