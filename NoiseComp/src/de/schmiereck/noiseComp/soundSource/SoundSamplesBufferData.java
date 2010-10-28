package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * Buffer of the calculated Samples.
 *
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSamplesBufferData
{
	//**********************************************************************************************
	// Fields:
	
	private int				emptyBufferStart	= 0;
	private int				emptyBufferEnd		= 0;
	private SoundSample[]	bufferSoundSamples	= null;
	private float 			frameRate			= 0F;
	
	//**********************************************************************************************
	// Functions:
	
	public void createBuffer(float timeLen, float frameRate)
	{
		synchronized (this)
		{
			int frames = (int)(timeLen * frameRate);
	
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
			int startFrame = (int)(startTimePos * this.frameRate);
			int endFrame = (int)(endTimePos * this.frameRate);
	
			for (int pos = startFrame; pos < endFrame; pos++)
			{
				this.bufferSoundSamples[pos] = null;
			}
			
			if ((this.emptyBufferStart == 0) && (this.emptyBufferEnd == 0))
			{
				this.emptyBufferStart 	= startFrame;
				this.emptyBufferEnd 	= endFrame;
			}
			else
			{
				this.emptyBufferStart 	= Math.min(this.emptyBufferStart, startFrame);
				this.emptyBufferEnd 	= Math.max(this.emptyBufferEnd, endFrame);
			}
		}
	}

	/**
	 * @param frame
	 * 			is the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample getSoundSample(long frame)
	{
		SoundSample soundSample;
		
		if (frame < this.bufferSoundSamples.length)
		{
			soundSample = this.bufferSoundSamples[(int)frame];
		}
		else
		{
			soundSample = null;
		}
		
		return soundSample;
	}

	/**
	 * @param frame
	 * 			is the sound sample frame.
	 * @param outputTimeline
	 * 			is the output Timeline.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample generateSoundSample(long frame, Timeline outputTimeline)
	{
		SoundSample soundSample;
		
		if (frame < this.bufferSoundSamples.length)
		{
			soundSample = this.bufferSoundSamples[(int)frame];

			if (soundSample == null)
			{
				soundSample = outputTimeline.generateFrameSample(frame, null);
			
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
	 * @param outputTimeline
	 * 			is the output Timeline.
	 */
	public void calcWaitingSamplesPart(float partTime, Timeline outputTimeline)
	{
		int framePos;
		int frames = (int)(partTime * outputTimeline.getSoundFrameRate());

		// TODO null as modul is not the best...?
		ModulGenerator parentModulGenerator = null;
		
		for (framePos = this.emptyBufferStart; framePos < this.emptyBufferEnd; framePos++)
		{
			if (frames <= 0)
			{
				break;
			}
			frames--;
			
			SoundSample soundSample = outputTimeline.generateFrameSample(framePos, parentModulGenerator);
		
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

	/**
	 * @return 
	 * 			returns the {@link #emptyBufferStart}.
	 */
	public long getEmptyBufferStart()
	{
		return this.emptyBufferStart;
	}

	/**
	 * @return 
	 * 			returns the {@link #emptyBufferEnd}.
	 */
	public long getEmptyBufferEnd()
	{
		return this.emptyBufferEnd;
	}
	
	/**
	 * @return
	 * 			the count of sound buffer samples.
	 */
	public long getBufferSamplesCount()
	{
		return this.bufferSoundSamples.length;
	}

	/**
	 * @return 
	 * 			returns the {@link #frameRate}.
	 */
	public float getFrameRate()
	{
		return this.frameRate;
	}
}
