package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;

/**
 * <p>
 * 	Manages a connection between the OutputGenerator of
 * 	the current selected Generators and the 
 * 	{@link de.schmiereck.noiseComp.soundData.SoundData}-Output.
 * </p>
 * <p>
 * 	Buffers the output.<br/>
 * 	The buffer is filled by a polling thread.<br/>
 * 	The affected parts of the buffer are cleard if changes in the input
 * 	are reported. 
 * </p>
 * 
 * TODO Change management einbauen, wenn sich "oben" was ändert, smk
 * 
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceLogic
{
	private OutputGenerator outputGenerator = null;

	private SoundSample[]	soundSamples = null;
	
	public SoundSourceLogic(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;
	}

	/**
	 * @return the attribute {@link #outputGenerator}.
	 */
	public OutputGenerator getOutputGenerator()
	{
		return this.outputGenerator;
	}

	/**
	 * @param frame
	 * @return
	 */
	public SoundSample generateFrameSample(long frame)
	{
		if (this.soundSamples == null)
		{
			float len = this.outputGenerator.getEndTimePos() - this.outputGenerator.getStartTimePos();
			
			int frames = (int)(len * this.outputGenerator.getFrameRate());

			this.soundSamples = new SoundSample[frames];
			
			for (int pos = 0; pos < frames; pos++)
			{
				this.soundSamples[pos] = null;
			}
		}
		
		SoundSample soundSample;
		
		if (frame < this.soundSamples.length)
		{
			soundSample = this.soundSamples[(int)frame];

			if (soundSample == null)
			{
				soundSample = this.outputGenerator.generateFrameSample(frame, null);
			
				this.soundSamples[(int)frame] = soundSample;
			}
		}
		else
		{
			soundSample = null;
		}
		
		return soundSample;
	}
}
