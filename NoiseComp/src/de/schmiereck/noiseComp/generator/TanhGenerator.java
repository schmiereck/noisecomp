/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;


/**
 * <p>
 * 	Tanh Generator.
 * </p>
 * 
 * @author smk
 * @version <p>01.10.2010:	created, smk</p>
 */
public class TanhGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the generator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeData
	 * 			is the Generator-Type Data.
	 */
	public TanhGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
	{
		//==========================================================================================
		SoundSample signalSample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		if (signalInputData != null)
		{
			this.calcInputValue(framePosition, 
                                frameTime,
			                    signalInputData, 
			                    signalSample, 
			                    parentModulGenerator,
			                    generatorBuffer,
			                    modulArguments);
		}
		
		//------------------------------------------------------------------------------------------
		float left;
		float right;

		float leftValue = signalSample.getLeftValue();
		
		if (Float.isNaN(leftValue) == false)
		{
			left = (float)Math.tanh(leftValue * 2.0D);
		}
		else
		{
			left = Float.NaN;
		}
		
		float rightValue = signalSample.getRightValue();
		
		if (Float.isNaN(rightValue) == false)
		{
			right = (float)Math.tanh(rightValue * 2.0D);
		}
		else
		{
			right = Float.NaN;
		}
		
		soundSample.setStereoValues(left, 
		                            right);
		
		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", TanhGenerator.class, "tanh", "Math.tanh Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal between -1 and 1 send to output speakers.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}


}
