/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

/**
 * <p>
 * 	Integrator Generator.
 * </p>
 * 
 * @author smk
 * @version <p>20.10.2010:	created, smk</p>
 */
public class IntegratorGenerator
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
	 * 			is the genrator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeData
	 * 			is the Generator-Type Data.
	 */
	public IntegratorGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator, GeneratorBufferInterface generatorBuffer)
	{
		//==========================================================================================
		SoundSample signalSample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		float signalLeft = 0.0F;
		float signalRight = 0.0F;
	
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
								// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
								this.calcInputValue(framePosition, 
								                    frameTime,
								                    inputData, 
								                    signalSample, 
								                    parentModulGenerator,
								                    generatorBuffer);

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

								// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
								long intgrFramePosition = framePosition - 1L;
								float intgrFrameTime = intgrFramePosition / this.getSoundFrameRate();
								
								SoundSample intgrSoundSample = 
//									this.generateFrameSample(framePosition - 1L,
//									                         parentModulGenerator, 
//									                         generatorBuffer);
									generatorBuffer.calcFrameSample(intgrFramePosition, 
									                                intgrFrameTime, 
									                                parentModulGenerator);
								
								if (intgrSoundSample != null)
								{
									float intgrLeftValue = intgrSoundSample.getLeftValue();
									
									if (Float.isNaN(intgrLeftValue) == false)
									{
										signalLeft += intgrLeftValue / 44100.0F * 4.0F;
									}
									
									float intgrRightValue = intgrSoundSample.getRightValue();
									
									if (Float.isNaN(intgrRightValue) == false)
									{
										signalLeft += intgrRightValue / 44100.0F * 4.0F;
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
		
		//------------------------------------------------------------------------------------------
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(IntegratorGenerator.class, "Integrator", "Integrates multiple signal input lines.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1, "The input signal between -1 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}


}
