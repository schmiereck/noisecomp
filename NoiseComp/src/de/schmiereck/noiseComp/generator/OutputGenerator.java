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
	public OutputGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/**
	 * @param string
	 * @param soundGenerator
	 */
	public void setSignalInput(Generator inputSoundGenerator)
	{
		this.addInputGenerator(inputSoundGenerator, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		this.calcInputSignals(framePosition, signalInputData, soundSample);
		/*
		InputData inputData = this.searchInputByType(INPUT_TYPE_SIGNAL);
		
		if (inputData != null)
		{	
			Float inputValue = inputData.getInputValue();
			
			// Constant input value ?
			if (inputValue != null)
			{
				soundSample.setMonoValue(inputValue.floatValue());
			}
			else
			{	
				Generator inputSoundGenerator = inputData.getInputGenerator();
				
				if (inputSoundGenerator != null)
				{	
					SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
					
					soundSample.setSignals(inputSoundSample);
				}
			}
		}
		*/
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(OutputGenerator.class, "Output", "The input signal is audible at the audio hardware.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal between -1 and 1 send to output speakers.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
