/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Random;

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
	// Fields:

//	private final long generatorSeed = new Random().nextLong();
	
	private final Random rnd = new Random(0);
	
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
	public PinkNoiseGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator)
	{
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
								float value;
								try
								{
									value = this.calcInputMonoValue(framePosition, inputData, parentModulGenerator);
									
									mean += value;
								}
								catch (NoInputSignalException ex)
								{
								}
								break;
							}
							case INPUT_TYPE_VARIANCE:
							{
								float value;
								try
								{
									value = this.calcInputMonoValue(framePosition, inputData, parentModulGenerator);
									
									variance += value;
								}
								catch (NoInputSignalException ex)
								{
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
//		this.rnd.setSeed(this.generatorSeed + framePosition);
		
		float left  = (float)this.makePinkNoise(framePosition);
		float right = (float)this.makePinkNoise(framePosition);
		
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

	private final double pinkBuf[] = new double[pinkCoefs.length];
	
	private double makePinkNoise(final long framePosition)
	{
		//==========================================================================================
		double ret;
		
		//------------------------------------------------------------------------------------------
		final double white = this.signedRnd(framePosition);
		
		for (int i = 0; i < pinkBuf.length; i++)
	    {
			double[] pinkCoef = pinkCoefs[i];
			pinkBuf[i] = pinkCoef[0] * pinkBuf[i] + pinkCoef[1] * white;
	    }   

		//------------------------------------------------------------------------------------------
		// Sum:
		ret = 0.0D;
		
		for (int i = 0; i < pinkBuf.length; i++)
	    {
			ret += pinkBuf[i];
	    }

		ret = ret / pinkBuf.length;
		
		//==========================================================================================
	    return ret;
	}
	
	/**
	 * @return
	 * 			a random variable in the range [0, 1]. 
	 */
	private double uniformRnd(long framePosition)
	{
//		return this.rnd.nextGaussian();
//		return (float)Math.random();
		return this.rnd.nextDouble();
	}
	
	/**
	 * @return
	 * 			a random variable in the range [-1, 1]. 
	 */
	private double signedRnd(long framePosition)
	{
//		return this.rnd.nextGaussian();
//		return (float)Math.random();
		return (this.rnd.nextDouble() - 0.5D) * 2.0D;
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(PinkNoiseGenerator.class, "Pink Noise", "Generate White Gaussian Noise with variance and mean.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VARIANCE, "variance", -1, -1, "The variance.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_MEAN, "mean", -1, -1, "The mean.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}

}
