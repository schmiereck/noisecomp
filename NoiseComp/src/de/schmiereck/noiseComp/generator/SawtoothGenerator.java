/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

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
 * 
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

	public static final int	INPUT_TYPE_FREQ		= 1;
	public static final int	INPUT_TYPE_AMPL		= 2;
	public static final int	INPUT_TYPE_SHIFT	= 3;
	public static final int	INPUT_TYPE_IIFREQ	= 4;
	
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
		//----------------------------------------------------------------------
		float signalFrequency;

		signalFrequency = this.calcInputMonoValue(framePosition, 
		                                          frameTime,
		                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ), 
		                                          parentModulGenerator,
		                                          generatorBuffer,
		                                          modulArguments);

		//----------------------------------------------------------------------
		float signalAmplitude;

		// Amplitude des gerade generierten Sinus-Siganls.
		signalAmplitude = this.calcInputMonoValue(framePosition, 
		                                          frameTime,
		                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), 
		                                          parentModulGenerator,
		                                          generatorBuffer,
		                                          modulArguments);
		
		//----------------------------------------------------------------------
		float signalShift;

		// Versatz des Sinus-Siganls um eine Schwingung.
		signalShift = this.calcInputMonoValue(framePosition, 
	                                          frameTime,
		                                      this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT), 
		                                      parentModulGenerator,
		                                      generatorBuffer,
		                                      modulArguments);
		
		//------------------------------------------------------------------------------------------
		// Integrated Input of the Frequenz Signal.
		float signalIIFreq;
		{
			InputTypeData inputTypeData = this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_IIFREQ);
			
			if (inputTypeData != null)
			{
				signalIIFreq = 
					this.calcInputMonoValue(framePosition, 
		                                    frameTime,
		                                    inputTypeData, 
					                        parentModulGenerator,
					                        generatorBuffer,
					                        modulArguments);
			}
			else
			{
				signalIIFreq = Float.NaN;
			}
		}
		//----------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		
		// Länge einer Sinus-Periode in Frames.
		float periodPosition;
		
		if (Float.isNaN(signalFrequency) == false)
		{
			// Länge einer Sinus-Periode in Frames.
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
		
		float posInPeriod = ((periodPosition + signalShift) % 1.0F);
		
		float value;
		
		value = 1.0F - posInPeriod * 2.0F;
		
		soundSample.setStereoValues(value * signalAmplitude, 
		                            value * signalAmplitude);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
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
		
		return generatorTypeData;
	}
}
