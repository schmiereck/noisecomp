package de.schmiereck.noiseComp.generator;

/**
 * Cut the singal to the given value.
 *
 * @author smk
 * @version <p>13.04.2004: created, smk</p>
 */
public class CutGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_MAX_AMPL		= 1;
	public static final int	INPUT_TYPE_MIN_AMPL		= 2;
	public static final int	INPUT_TYPE_SIGNAL		= 3;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param frameRate	
	 * 			are the Frames per Second.
	 */
	public CutGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, 
	                                 SoundSample signalSample, 
	                                 ModuleGenerator parentModuleGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments)
	{
		//==========================================================================================
		float maxValue = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_MAX_AMPL), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);
		//------------------------------------------------------------------------------------------
		float minValue = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_MIN_AMPL), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		if (signalInputData != null)
		{
			this.calcInputValue(framePosition, 
	                            frameTime,
			                    signalInputData, 
			                    signalSample, 
			                    parentModuleGenerator, 
			                    generatorBuffer,
	                            moduleArguments);
		}
		//------------------------------------------------------------------------------------------
		float leftValue = signalSample.getLeftValue();
		
		if (Float.isNaN(leftValue) == false)
		{
			if (leftValue > maxValue)
			{
				leftValue = maxValue;
			}
			if (leftValue < minValue)
			{
				leftValue = minValue;
			}
		}
		else
		{
			leftValue = Float.NaN;
		}
		
		float rightValue = signalSample.getRightValue();
		
		if (Float.isNaN(rightValue) == false)
		{
			if (rightValue > maxValue)
			{
				rightValue = maxValue;
			}
			if (rightValue < minValue)
			{
				rightValue = minValue;
			}
		}
		else
		{
			rightValue = Float.NaN;
		}
		
		signalSample.setStereoValues(leftValue, rightValue);
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", CutGenerator.class, "Cut", "Cut the input singnal to the given minimum and maximum values.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MAX_AMPL, "signalMaximum", 1, 1, Float.valueOf(1.0F), "Mamximum of the signal value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MIN_AMPL, "signalMinimum", 1, 1, Float.valueOf(-1.0F), "Minimum of the signal value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}