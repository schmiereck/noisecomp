package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Generates a rectangle-signal based on the values of the input types.
 * </p>
 * 	<code>
 * 		 ___
 * 		|   |
 * 		|   |
 * 			|   |
 * 			|   |
 * 			`---´
 * 	</code>
 * </p>
 * 
 * @author smk
 * @version <p>13.04.2004: created, smk</p>
 */
public class RectangleGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_FREQ			= 1;
	public static final int	INPUT_TYPE_AMPL			= 2;
	public static final int	INPUT_TYPE_SHIFT		= 3;
	public static final int	INPUT_TYPE_PULSE_WIDTH	= 4; // pulse width (0.0 to 1.0, 0,5 is Square)
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public RectangleGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		// pulse width (0.0 to 1.0, 0,5 is Square)
		float pulseWidth;

		// Versatz des Sinus-Siganls um eine Schwingung.
		pulseWidth = this.calcInputMonoValue(framePosition, 
		                                     frameTime,
		                                     this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_PULSE_WIDTH), 
		                                     parentModulGenerator,
		                                     generatorBuffer,
		                                     modulArguments);
		
		// pulseWidth is not defined?
		if (Float.isNaN(pulseWidth) == true)
		{
			// Use default value.
			pulseWidth = 0.5F;
		}
		
		//----------------------------------------------------------------------
		// Relative timepos in Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// Length of a Period in Frames.
		float periodLengthInFrames = (float)Math.floor(this.getSoundFrameRate() / signalFrequency);
		float periodPosition = (float)(framePosition) / (float)periodLengthInFrames;
		//float value = ((float)Math.sin(periodPosition * (2.0F * Math.PI) + (signalShift * Math.PI))) * signalAmplitude;

		float value;
		
		if (((periodPosition + signalShift) % 1.0F) > pulseWidth) //0.5F)
		{
			value = signalAmplitude;
		}
		else
		{
			value = -signalAmplitude;
		}
		
		soundSample.setStereoValues(value, value);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", RectangleGenerator.class, "Rectangle", "Generates a rectangle signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the signal in oscillations per second.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", 0, 1, Float.valueOf(0.0F), "The offset of the rectangle between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_PULSE_WIDTH, "pulseWidth", 0, 1, Float.valueOf(0.5F), "The pulse width of the rectangle between 0 and 1 (0.5 (Default) is rectangle).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
