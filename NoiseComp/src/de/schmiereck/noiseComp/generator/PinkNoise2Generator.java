/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Random;

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
	// Fields:

	private final long generatorSeed = new Random().nextLong();
	
	private final Random rnd = new Random(0);
	
	/**
	 * Pink noise source.
	 */
	private PinkNoise pinkNoise = null;
	
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
	public PinkNoise2Generator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModuleGenerator parentModuleGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments)
	{
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
									this.calcInputMonoValue(framePosition, 
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
									this.calcInputMonoValue(framePosition, 
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
									this.calcInputMonoValue(framePosition, 
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
									this.calcInputMonoValue(framePosition, 
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
		this.rnd.setSeed(this.generatorSeed + framePosition);
		
		if (this.pinkNoise == null)
		{
			this.pinkNoise = new PinkNoise(alpha, poles, this.rnd);
		}
		else
		{
			// Alpha changed?
			if (this.pinkNoise.getAlpha() != alpha)
			{
				this.pinkNoise.setAlpha(alpha);
			}
			
			// Poles changed?
			if (this.pinkNoise.getPoles() != poles)
			{
				this.pinkNoise.setPoles(poles);
			}
		}
		
		float left  = (float)this.pinkNoise.nextValue();
		float right = (float)this.pinkNoise.nextValue();
		
		//------------------------------------------------------------------------------------------
		float signalLeft = (float)(mean + Math.sqrt(variance) * left);
		float signalRight = (float)(mean + Math.sqrt(variance) * right);
		
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}
	
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", PinkNoise2Generator.class, "Pink Noise 2", "Generate White Gaussian Noise with variance, mean, alpha and poles.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VARIANCE, "variance", -1, -1, "The variance (default 0).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MEAN, "mean", -1, -1, "The mean (default 0).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_ALPHA, "alpha", -1, -1, "The alpha (default 0, alpha 1 is pink, alpha 2 is brown, 0 to 2).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_POLES, "poles", -1, -1, "The poles (default 2).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}

}
