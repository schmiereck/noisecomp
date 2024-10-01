/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.shape;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

/**
 * <p>
 * 	Exponent Generator.
 * </p>
 * 
 * @author smk
 * @version <p>02.12.2010:	created, smk</p>
 */
public class ExponentGenerator
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
	public ExponentGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModuleGenerator parentModuleGenerator,
									 GeneratorBufferInterface generatorBuffer,
									 ModuleArguments moduleArguments)
	{
		//==========================================================================================
//		SoundSample signalSample = new SoundSample();
//		
//		//------------------------------------------------------------------------------------------
//		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
//		
//		if (signalInputData != null)
//		{
//			this.calcInputValue(framePosition, 
//                                frameTime,
//			                    signalInputData, 
//			                    signalSample, 
//			                    parentModuleGenerator,
//			                    generatorBuffer,
//			                    modulerguments);
//		}
		
		//------------------------------------------------------------------------------------------
//		float left;
//		float leftValue = signalSample.getLeftValue();
//		
//		if (Float.isNaN(leftValue) == false)
//		{
//			left = (float)Math.tanh(leftValue * 2.0D);
//		}
//		else
//		{
//			left = Float.NaN;
//		}
//		
//		float right;
//		float rightValue = signalSample.getRightValue();
//		
//		if (Float.isNaN(rightValue) == false)
//		{
//			right = (float)Math.tanh(rightValue * 2.0D);
//		}
//		else
//		{
//			right = Float.NaN;
//		}
		
		float value;
		
		float startTimePos = this.getStartTimePos();
		float endTimePos = this.getEndTimePos();
		
		float length = (endTimePos - startTimePos);
		
		float pos = (frameTime - startTimePos) * length;
		
//		value = (float)Math.exp(pos);
		value = (float)(2.0 * Math.pow(0.1, pos));
		
		soundSample.setStereoValues(value, 
		                            value);
		
		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", ExponentGenerator.class, "exponent", "Exponent Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal between -1 and 1 send to output speakers.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
