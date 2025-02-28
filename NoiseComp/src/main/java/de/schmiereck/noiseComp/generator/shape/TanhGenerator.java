/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.shape;


import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import static de.schmiereck.noiseComp.service.StartupService.SHAPE_GENERATOR_FOLDER_PATH;

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
	 * @param generatorTypeInfoData
	 * 			is the Generator-Type Data.
	 */
	public TanhGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
	{
		super(name, frameRate, generatorTypeInfoData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModuleGenerator parentModuleGenerator,
									 GeneratorBufferInterface generatorBuffer,
									 ModuleArguments moduleArguments)
	{
		//==========================================================================================
		SoundSample signalSample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		InputData signalInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
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

	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SHAPE_GENERATOR_FOLDER_PATH, TanhGenerator.class, "tanh", "Math.tanh Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal between -1 and 1 send to output speakers.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}


}
