/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Triangel Generator.
 * </p>
 * <p>
 * 	<pre>
 * 	      .
 * 	     / \
 * 	    /   \
 * 	         \   /
 * 	          \ /
 * 	           ´
 * 	</pre>
 * </p>
 * 
 * @author smk
 * @version <p>10.12.2010:	created, smk</p>
 */
public class TriangelGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_FREQ		= 1;
	public static final int	INPUT_TYPE_AMPL		= 2;
	public static final int	INPUT_TYPE_SHIFT	= 3;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TriangelGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		
		//----------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		
		// Länge einer Sinus-Periode in Frames.
		float periodLengthInFrames = (float)Math.floor(this.getSoundFrameRate() / signalFrequency) * 1.0F;
		float periodPosition = (float)(framePosition / periodLengthInFrames) + signalShift;

		float posInPeriod = (periodPosition % 1.0F);
		
		float value;
		
		if (posInPeriod <= 0.25F)
		{
			value = posInPeriod * 4.0F;
		}
		else
		{
			if (posInPeriod <= 0.5F)
			{
				value = 2.0F - (posInPeriod * 4.0F);
			}
			else
			{
				if (posInPeriod <= 0.75F)
				{
					value = 2.0F - (posInPeriod * 4.0F);
				}
				else
				{
					if (posInPeriod <= 1.0F)
					{
						value = (posInPeriod * 4.0F) - 4.0F;
					}
					else
					{
						value = 0.0F;
					}
				}
			}
		}
		
		soundSample.setStereoValues(value * signalAmplitude, 
		                            value * signalAmplitude);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", TriangelGenerator.class, "Triangel", "Generates a triangel signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the signal in oscillations per second.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", 0, 1, Float.valueOf(0.0F), "The offset of the triangel between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
