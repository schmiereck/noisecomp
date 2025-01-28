/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.signal;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.util.Iterator;
import java.util.Random;

import static de.schmiereck.noiseComp.service.StartupService.SIGNAL_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Pink-Noise2 Generator.
 * </p>
 * 
 * @author smk
 * @version <p>30.09.2010:	created, smk</p>
 */
public class PinkNoise2Generator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_MEAN		= 1;
	public static final int	INPUT_TYPE_VARIANCE	= 2;
	public static final int	INPUT_TYPE_ALPHA	= 3;
	public static final int	INPUT_TYPE_POLES	= 4;
	
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
	public PinkNoise2Generator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
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
		final PinkNoise2GeneratorData data = (PinkNoise2GeneratorData)generatorBuffer.getGeneratorData();
		
		//==========================================================================================
		float mean = 0.0F;
		float variance = 0.0F;
		double alpha = 0.0D;
		int poles = 2;
		
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
							case INPUT_TYPE_MEAN:
							{
								float value = 
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData, 
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false)
								{
									mean += value;
								}
								break;
							}
							case INPUT_TYPE_VARIANCE:
							{
								float value =  
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData, 
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);
									
								if (Float.isNaN(value) == false)
								{
									variance += value;
								}
								break;
							}
							case INPUT_TYPE_ALPHA:
							{
								float value = 
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData, 
									                        parentModuleGenerator,
									                        generatorBuffer,
								                            moduleArguments);
									
								if (Float.isNaN(value) == false)
								{
									alpha += value;
								}
								break;
							}
							case INPUT_TYPE_POLES:
							{
								float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData, 
									                        parentModuleGenerator,
									                        generatorBuffer,
								                            moduleArguments);
									
								if (Float.isNaN(value) == false)
								{
									poles += (int)Math.round(value);
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
		data.rnd.setSeed(data.generatorSeed + framePosition);
		
		if (data.pinkNoise == null)
		{
			data.pinkNoise = new PinkNoise(alpha, poles, data.rnd);
		}
		else
		{
			// Alpha changed?
			if (data.pinkNoise.getAlpha() != alpha)
			{
				data.pinkNoise.setAlpha(alpha);
			}
			
			// Poles changed?
			if (data.pinkNoise.getPoles() != poles)
			{
				data.pinkNoise.setPoles(poles);
			}
		}
		
		float left  = (float)data.pinkNoise.nextValue();
		float right = (float)data.pinkNoise.nextValue();
		
		//------------------------------------------------------------------------------------------
		float signalLeft = (float)(mean + Math.sqrt(variance) * left);
		float signalRight = (float)(mean + Math.sqrt(variance) * right);
		
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorData()
	 */
	@Override
	public Object createGeneratorData()
	{
		return new PinkNoise2GeneratorData(new Random().nextLong(), new Random(0), null);
	}
	
	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SIGNAL_GENERATOR_FOLDER_PATH, PinkNoise2Generator.class, "Pink Noise 2", "Generate White Gaussian Noise with variance, mean, alpha and poles.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VARIANCE, "variance", -1, -1, "The variance (default 0).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MEAN, "mean", -1, -1, "The mean (default 0).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_ALPHA, "alpha", -1, -1, "The alpha (default 0, alpha 1 is pink, alpha 2 is brown, 0 to 2).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_POLES, "poles", -1, -1, "The poles (default 2).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}

}
