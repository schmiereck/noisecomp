/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.mixer;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.util.Iterator;

import static de.schmiereck.noiseComp.service.StartupService.MIXER_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Add Generator.
 * </p>
 * 
 * @author smk
 * @version <p>03.12.2010:	created, smk</p>
 */
public class AddGenerator
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
	public AddGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
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
		float signalLeft = 0.0F;
		float signalRight = 0.0F;

		SoundSample signalSample = new SoundSample();
		
		Object inputsSyncObject = this.getInputsSyncObject();
		
		if (inputsSyncObject != null)
		{	
			synchronized (inputsSyncObject)
			{
				Iterator<InputData> inputsIterator = this.getInputsIterator();
			
				if (inputsIterator != null)
				{
					while (inputsIterator.hasNext())
					{
						InputData inputData = inputsIterator.next();
						
						switch (inputData.getInputTypeData().getInputType())
						{
							case INPUT_TYPE_SIGNAL:
							{
								this.calcInputValue(framePosition, 
								                    frameTime,
								                    inputData, 
								                    signalSample, 
								                    parentModuleGenerator,
								                    generatorBuffer,
								                    moduleArguments);

								float leftValue = signalSample.getLeftValue();
								
								if (Float.isNaN(leftValue) == false)
								{
									signalLeft += leftValue;
								}
								
								float rightValue = signalSample.getRightValue();
								
								if (Float.isNaN(rightValue) == false)
								{
									signalRight += rightValue;
								}
								break;
							}
							default:
							{
								throw new RuntimeException("Unknown input type \"" + inputData.getInputTypeData() + "\".");
							}
						}
					}
				}
			}
		}
		
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}

	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(MIXER_GENERATOR_FOLDER_PATH, AddGenerator.class, "add", "Add signals Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}

}
