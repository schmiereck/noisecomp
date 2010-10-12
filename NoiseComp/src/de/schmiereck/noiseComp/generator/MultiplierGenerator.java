/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

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
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator, GeneratorBufferInterface generatorBuffer)
	{
		float multiplier = 1.0F;

		float signalLeft = 0.0F;
		float signalRight = 0.0F;

		SoundSample signal = new SoundSample();
		
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
//								try
//								{
									float value = this.calcInputMonoValue(framePosition, 
									                                      frameTime,
									                                      inputData, 
									                                      parentModulGenerator,
									                                      generatorBuffer);
									
									multiplier *= value;
//								}
//								catch (NoInputSignalException ex)
//								{
//								}
								break;
							}
							case INPUT_TYPE_SIGNAL:
							{
								this.calcInputValue(framePosition, 
								                    frameTime,
								                    inputData, 
								                    signal, 
								                    parentModulGenerator,
								                    generatorBuffer);

								signalLeft += signal.getLeftValue();
								signalRight += signal.getRightValue();
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
		
		soundSample.setStereoValues(signalLeft * multiplier, signalRight * multiplier);
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(MultiplierGenerator.class, "Multiplier", "Multiply multiple signal input lines and multiply them with a multiplier.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MULTIPLIER, "multiplier", -1, -1, "The volume of the output singal between 0 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1, "The input signal between -1 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
