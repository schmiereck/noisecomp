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
 * 	Mult Generator.
 * </p>
 * 
 * @author smk
 * @version <p>22.12.2010:	created, smk</p>
 */
public class MultGenerator
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
	public MultGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
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
		float signalLeft = Float.NaN;
		float signalRight = Float.NaN;

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
									if (Float.isNaN(signalLeft) == true)
									{
										signalLeft = leftValue;
									}
									else
									{
										signalLeft *= leftValue;
									}
								}
								
								float rightValue = signalSample.getRightValue();
								
								if (Float.isNaN(rightValue) == false)
								{
									if (Float.isNaN(signalRight) == true)
									{
										signalRight = rightValue;
									}
									else
									{
										signalRight *= rightValue;
									}
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
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(MIXER_GENERATOR_FOLDER_PATH, MultGenerator.class, "mult", "Multiplicate signals Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}

}
