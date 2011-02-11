/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;
import java.util.Random;

/**
 * <p>
 * 	Noise Generator.
 * </p>
 * 
 * see: http://www.dspguru.com/dsp/howtos/how-to-generate-white-gaussian-noise
 * 
 * @author smk
 * @version <p>29.09.2010:	created, smk</p>
 */
public class WhiteNoiseGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_MEAN	= 1;
	public static final int	INPUT_TYPE_VARIANCE	= 2;
	
	//**********************************************************************************************
	// Fields:

	private final long generatorSeed = new Random().nextLong();
	
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
	public WhiteNoiseGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
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
								float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
								                            modulArguments);
									
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
									                        parentModulGenerator,
									                        generatorBuffer,
								                            modulArguments);
									
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
		// see http://www.dspguru.com/dsp/howtos/how-to-generate-white-gaussian-noise
		
		this.rnd.setSeed(this.generatorSeed + framePosition);
		
		double v1;
		double v2;
		double s;
		
		do
		{
			double u1 = this.uniformRnd(framePosition); 	// U1=[0,1] 
			double u2 = this.uniformRnd(framePosition); 	// U2=[0,1] 
			v1 = 2.0F * u1 - 1.0F;        					// V1=[-1,1]
			v2 = 2.0F * u2 - 1.0F;        					// V2=[-1,1]
			
			s = v1 * v1 + v2 * v2;
		}
		while (s >= 1.0F);
				
		double left  = (double)(Math.sqrt(-2.0F * Math.log(s) / s) * v1);
		double right = (double)(Math.sqrt(-2.0F * Math.log(s) / s) * v2);
		
		//------------------------------------------------------------------------------------------
		float signalLeft = (float)(mean + Math.sqrt(variance) * left);
		float signalRight = (float)(mean + Math.sqrt(variance) * right);
		
		soundSample.setStereoValues(signalLeft * 0.5F, signalRight * 0.5F);
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			a random variable in the range [0, 1]. 
	 */
	private double uniformRnd(long framePosition)
	{
		return this.rnd.nextGaussian();
//		return (float)Math.random();
//		return this.rnd.nextFloat();
//		return this.rnd.nextDouble();
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", WhiteNoiseGenerator.class, "White Gaussian Noise", "Generate White Gaussian Noise with variance and mean.");
		
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
