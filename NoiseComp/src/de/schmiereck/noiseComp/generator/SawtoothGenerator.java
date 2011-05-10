/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

/**
 * <p>
 * 	Sawtooth Generator.
 * </p>
 * <p>
 * 	<pre>
 * 	    .
 * 	    |\
 * 	    | \
 * 	       \ |
 * 	        \|
 * 	         ´
 * 	</pre>
 * </p>
 * 
 * @author smk
 * @version <p>11.12.2010:	created, smk</p>
 */
public class SawtoothGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_FREQ			= 1;
	public static final int	INPUT_TYPE_AMPL			= 2;
	public static final int	INPUT_TYPE_SHIFT		= 3;
	public static final int	INPUT_TYPE_IIFREQ		= 4;
	public static final int	INPUT_TYPE_PULSE_WIDTH	= 5; // pulse width (0.0 to 1.0 (Default))
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public SawtoothGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		// Frequency of generated Signal oscillation.
		float signalFrequency = Float.NaN;
		// Integrated Input of Frequency of generated Signal oscillation.
		float signalIIFreq = Float.NaN;
		// Amplitude of generated Signal.
		float signalAmplitude = Float.NaN;
		// Shif of generated Signal oscillation.
		float signalShift = Float.NaN;
		// Pulse-Width of generated Signal oscillation (0.0 to 1.0).
		// Width of Signal per oscillation.
		float pulseWidth = Float.NaN;

		//==========================================================================================
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
							case INPUT_TYPE_FREQ:
							{
								final float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
									                        modulArguments);
								
								if (Float.isNaN(value) == false)
								{
									if (Float.isNaN(signalFrequency) == false)
									{
										signalFrequency += value;
									}
									else
									{
										signalFrequency = value;
									}
								}
								break;
							}
							case INPUT_TYPE_IIFREQ:
							{
								final float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
									                        modulArguments);
								
								if (Float.isNaN(value) == false)
								{
									if (Float.isNaN(signalIIFreq) == false)
									{
										signalIIFreq += value;
									}
									else
									{
										signalIIFreq = value;
									}
								}
								break;
							}
							case INPUT_TYPE_AMPL:
							{
								final float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
									                        modulArguments);
								
								if (Float.isNaN(value) == false)
								{
									if (Float.isNaN(signalAmplitude) == false)
									{
										signalAmplitude += value;
									}
									else
									{
										signalAmplitude = value;
									}
								}
								break;
							}
							case INPUT_TYPE_SHIFT:
							{
								final float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
									                        modulArguments);
								
								if (Float.isNaN(value) == false)
								{
									if (Float.isNaN(signalShift) == false)
									{
										signalShift += value;
									}
									else
									{
										signalShift = value;
									}
								}
								break;
							}
							case INPUT_TYPE_PULSE_WIDTH:
							{
								final float value =  
									this.calcInputMonoValue(framePosition, 
									                        frameTime,
									                        inputData, 
									                        parentModulGenerator,
									                        generatorBuffer,
									                        modulArguments);
								
								if (Float.isNaN(value) == false)
								{
									if (Float.isNaN(pulseWidth) == false)
									{
										pulseWidth += value;
									}
									else
									{
										pulseWidth = value;
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
		// Relative timepos in Generator.
		
		// Pos in Periode.
		float periodPosition;
		
		if (Float.isNaN(signalFrequency) == false)
		{
			// Length of a Period in Frames.
			float periodLengthInFrames = (float)/*Math.floor*/(this.getSoundFrameRate() / signalFrequency);
			periodPosition = (float)(framePosition / periodLengthInFrames);
		}
		else
		{
			periodPosition = 0.0F;
		}
		
		if (Float.isNaN(signalIIFreq) == false)
		{
			periodPosition += signalIIFreq;
		}

		// signalAmplitude is not defined?
		if (Float.isNaN(signalAmplitude) == true)
		{
			// Use default value.
			signalAmplitude = 1.0F;
		}

		// signalShift is not defined?
		if (Float.isNaN(signalShift) == true)
		{
			// Use default value.
			signalShift = 0.0F;
		}

		// pulseWidth is not defined?
		if (Float.isNaN(pulseWidth) == true)
		{
			// Use default value.
			pulseWidth = 1.0F;
		}

		//------------------------------------------------------------------------------------------
		float value;
		
		float posInPeriod = ((periodPosition + signalShift) % 1.0F);
		
		if (posInPeriod <= pulseWidth)
		{
			if (pulseWidth > 0.0F)
			{
				float d = posInPeriod / pulseWidth;
				value = 1.0F - d * 2.0F;
			}
			else
			{
				value = 1.0F;
			}
		}
		else
		{
			value = -1.0F;
		}
		
		soundSample.setStereoValues(value * signalAmplitude, 
		                            value * signalAmplitude);
		
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", SawtoothGenerator.class, "Sawtooth", "Generates a sawtooth signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the signal in oscillations per second.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", 0, 1, Float.valueOf(0.0F), "The offset of the sawtooth between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_IIFREQ, "signalIIFreq", -1, -1, null, "Integrated Input of the frequenz signal (alternativ to signalFrequency).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_PULSE_WIDTH, "pulseWidth", 0, 1, Float.valueOf(0.5F), "The pulse width of the rectangle between 0 and 1 (Default).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
