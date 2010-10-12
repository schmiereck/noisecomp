package de.schmiereck.noiseComp.generator;

/**
 * The output of this generator is send to the audio hardware if a instance 
 * of this generator is used as &quote;root&quote;.<br/>
 * If it is used in a generator module, it works as the output signal of 
 * the module instance.
 *
 * @author smk
 * @version 22.01.2004
 */
public class OutputGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;

	//**********************************************************************************************
	// Functions:

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
		this.addInputGenerator(inputSoundGenerator, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL), 
							   null, null, null);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample soundSample, 
	                                 ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer)
	{
		//==========================================================================================
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		this.calcInputSignal(framePosition, 
		                     frameTime,
		                     signalInputData, 
		                     soundSample, 
		                     parentModulGenerator,
		                     generatorBuffer);
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
		//==========================================================================================
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

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorBuffer()
	 */
	//public GeneratorBuffer createGeneratorBuffer()
	//{
	//	return new GeneratorFullBuffer();
	//}
}
