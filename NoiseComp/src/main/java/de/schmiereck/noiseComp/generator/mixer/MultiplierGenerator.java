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
 * 	Multiplier Generator.
 * </p>
 * 
 * @author smk
 * @version <p>26.09.2010:	created, smk</p>
 */
public class MultiplierGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;
	public static final int	INPUT_TYPE_MULTIPLIER	= 2;
	
	//**********************************************************************************************
	// Functions:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the genrator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeData
	 * 			is the Generator-Type Data.
	 */
	public MultiplierGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		float multiplierLeft = Float.NaN;
		float multiplierRight = Float.NaN;

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
							case INPUT_TYPE_MULTIPLIER:
							{
//								float value = this.calcInputMonoValue(framePosition, 
//								                                      frameTime,
//								                                      inputData, 
//								                                      parentModuleGenerator,
//								                                      generatorBuffer,
//							        	                              modulerguments);
//								
//								if (Float.isNaN(multiplier) == true)
//								{
//									multiplier = value;
//								}
//								else
//								{
//									multiplier *= value;
//								}
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
									if (Float.isNaN(multiplierLeft) == true)
									{
										multiplierLeft = leftValue;
									}
									else
									{
										multiplierLeft *= leftValue;
									}
								}
								
								float rightValue = signalSample.getRightValue();
								
								if (Float.isNaN(rightValue) == false)
								{
									if (Float.isNaN(multiplierRight) == true)
									{
										multiplierRight = rightValue;
									}
									else
									{
										multiplierRight *= rightValue;
									}
								}
								break;
							}
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
		
		soundSample.setStereoValues(signalLeft * multiplierLeft, 
		                            signalRight * multiplierRight);

		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(MIXER_GENERATOR_FOLDER_PATH, MultiplierGenerator.class, "Multiplier", "Multiply multiple signal input lines and multiply them with a multiplier.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MULTIPLIER, "multiplier", -1, -1, "The volume of the output singal between 0 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1, "The input signal between -1 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
