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
 * 	PinkNoiseGenerator
 * </p>
 * 
 * see http://sampo.kapsi.fi/PinkNoise/
 * 
 * @author smk
 * @version <p>30.09.2010:	created, smk</p>
 */
public class PinkNoiseGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_MEAN	= 1;
	public static final int	INPUT_TYPE_VARIANCE	= 2;
	
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
	public PinkNoiseGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
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
		final PinkNoiseGeneratorData data = (PinkNoiseGeneratorData)generatorBuffer.getGeneratorData();
		
		//==========================================================================================
		float mean = 0.0F;
		float variance = 0.0F;
		
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
		
		float left  = (float)this.makePinkNoise(data, framePosition);
		float right = (float)this.makePinkNoise(data, framePosition);
		
		//------------------------------------------------------------------------------------------
		float signalLeft = (float)(mean + Math.sqrt(variance) * left);
		float signalRight = (float)(mean + Math.sqrt(variance) * right);
		
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}
	
	private static final double pinkCoefs[][] = //new float[5][2]
	{
		//	P		V
	    { 0.997D, 0.011743D },
	    { 0.985D, 0.012911D },
	    { 0.950D, 0.019071D },
	    { 0.850D, 0.035946D },
	    { 0.620D, 0.043253D },
	    { 0.250D, 0.101508D }
	};

	private double makePinkNoise(final PinkNoiseGeneratorData data,
		                         final long framePosition)
	{
		//==========================================================================================
		double ret;
		
		//------------------------------------------------------------------------------------------
		final double white = this.signedRnd(data, framePosition);
		
		for (int i = 0; i < data.pinkBuf.length; i++)
	    {
			double[] pinkCoef = pinkCoefs[i];
			data.pinkBuf[i] = pinkCoef[0] * data.pinkBuf[i] + pinkCoef[1] * white;
	    }   

		//------------------------------------------------------------------------------------------
		// Sum:
		ret = 0.0D;
		
		for (int i = 0; i < data.pinkBuf.length; i++)
	    {
			ret += data.pinkBuf[i];
	    }

		ret = ret / data.pinkBuf.length;
		
		//==========================================================================================
	    return ret;
	}
	
	/**
	 * @return
	 * 			a random variable in the range [-1, 1]. 
	 */
	private double signedRnd(final PinkNoiseGeneratorData data,
	                         long framePosition)
	{
		return data.rnd.nextGaussian() - 0.5D;
//		return (float)Math.random();
//		return (this.rnd.nextDouble() - 0.5D) * 2.0D;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorData()
	 */
	@Override
	public Object createGeneratorData()
	{
		return new PinkNoiseGeneratorData(new Random().nextLong(), new Random(0), new double[pinkCoefs.length]);
	}

	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SIGNAL_GENERATOR_FOLDER_PATH, PinkNoiseGenerator.class, "Pink Noise", "Generate White Gaussian Noise with variance and mean.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VARIANCE, "variance", -1, -1, "The variance.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MEAN, "mean", -1, -1, "The mean.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}

}
