package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface;
import de.schmiereck.noiseComp.generator.SoundSample;

/**
 * <p>
 * 	Manages a connection between the (Output) {@link Generator} of
 * 	the current selected Generators and the 
 * 	{@link de.schmiereck.noiseComp.soundData.SoundData}-Output.
 * </p>
 * <p>
 * 	Buffers the output.<br/>
 * 	The buffer is filled by a polling thread.<br/>
 * 	The affected parts of the buffer are cleard if changes in the input
 * 	are reported. 
 * </p>
 * <p>
 * Manages the actual Output-Generator and filles a buffer with his
 * pre calculated samples (by polling in a thread).
 * </p>
 * 
 * TODO Change management einbauen, wenn sich "oben" was ändert, smk
 * 
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceLogic
implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Is the generator genarates the output samples.
	 */
	private Generator outputGenerator = null;

	/**
	 * Is a buffer to store the output samples generated by {@link #outputGenerator}. 
	 */
	private SoundSamplesBufferData	soundSamplesBufferData;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 *
	 */
	public SoundSourceLogic() // OutputGenerator outputGenerator
	{
		//this.outputGenerator = outputGenerator;

		this.soundSamplesBufferData = new SoundSamplesBufferData();
	}

	/**
	 * @return 
	 * 			the attribute {@link #outputGenerator}.
	 */
	public Generator getOutputGenerator()
	{
		return this.outputGenerator;
	}

	/**
	 * @param frame
	 * 			ist the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample generateFrameSample(long frame)
	{
		SoundSample soundSample;
		
		//synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				soundSample = this.soundSamplesBufferData.generateSoundSample(frame, this.outputGenerator);
			}
			else
			{
				soundSample = null;
			}
		}
		
		return soundSample;
	}
	
	/**
	 * @param frame
	 * 			ist the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample getFrameSample(long frame)
	{
		SoundSample soundSample;
		
		//synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				soundSample = this.soundSamplesBufferData.getSoundSample(frame);
			}
			else
			{
				soundSample = null;
			}
		}
		
		return soundSample;
	}

	/**
	 * Generates the next part of samples and store them in the buffer object {@link #soundSamplesBufferData}.
	 * 
	 * @param actualWaitPerFramesMillis
	 * 			???
	 */
	public void pollCalcFillBuffer(long actualWaitPerFramesMillis)
	{
		synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				this.soundSamplesBufferData.calcWaitingSamplesPart(actualWaitPerFramesMillis / 1000.0F, 
																   this.outputGenerator);
			}
		}
	}
	/**
	 * @param outputGenerator 
	 * 			to set {@link #outputGenerator}.
	 */
	public void setOutputGenerator(Generator outputGenerator)
	{
		synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				this.outputGenerator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
			}
			
			this.outputGenerator = outputGenerator;
			
			float timeLen = this.outputGenerator.getEndTimePos() - this.outputGenerator.getStartTimePos();
			
			this.soundSamplesBufferData.createBuffer(timeLen, this.outputGenerator.getSoundFrameRate());

			if (this.outputGenerator != null)
			{
				this.outputGenerator.getGeneratorChangeObserver().registerGeneratorChangeListener(this);
			}
		}
	}
	
	public long getEmptyBufferStart()
	{
		long ret;
		
		if (this.soundSamplesBufferData != null)
		{
			ret = this.soundSamplesBufferData.getEmptyBufferStart();
		}
		else
		{
			ret = 0L;
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	public void notifyGeneratorChanged(Generator generator, float startTimePos, float endTimePos)
	{
		long bufferSamplesCount = this.soundSamplesBufferData.getBufferSamplesCount();

		float timeLen = this.outputGenerator.getEndTimePos(); // - this.outputGenerator.getStartTimePos();

		float frameRate = this.soundSamplesBufferData.getFrameRate();

		long newCamplesCount = (long)(timeLen * frameRate);
		
		System.out.println("SoundSourceLogic.notifyChanged: frameRate: generator(" + generator.getSoundFrameRate() + "), buffer(" + frameRate + ")");
		System.out.println("SoundSourceLogic.notifyChanged: oldBuffer(" + bufferSamplesCount + "), new(" + newCamplesCount + "), " + startTimePos + ", " + endTimePos + ")");
		
		if (newCamplesCount > bufferSamplesCount)
		{
			this.soundSamplesBufferData.createBuffer(timeLen, frameRate);
		}
		
		this.soundSamplesBufferData.clearBuffer(startTimePos, endTimePos);
	}
}
