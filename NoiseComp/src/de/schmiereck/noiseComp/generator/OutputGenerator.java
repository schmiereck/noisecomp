package de.schmiereck.noiseComp.generator;

/**
 * TODO docu
 *
 * @author smk
 * @version 22.01.2004
 */
public class OutputGenerator
extends Generator
{
	public static final int	INPUT_TYPE_SIGNAL	= 1;

	/**
	 * Constructor.
	 * 
	 */
	public OutputGenerator(String name, float frameRate)
	{
		super(name, frameRate);
	}

	/**
	 * @param string
	 * @param soundGenerator
	 */
	public void setSignalInput(Generator inputSoundGenerator)
	{
		this.addInputGenerator(inputSoundGenerator, INPUT_TYPE_SIGNAL);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		Generator inputSoundGenerator = this.getInputByType(INPUT_TYPE_SIGNAL);
		
		if (inputSoundGenerator != null)
		{	
			SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
			
			soundSample.setValues(inputSoundSample);
		}
	}
}
