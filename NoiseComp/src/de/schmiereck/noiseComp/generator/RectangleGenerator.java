package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Generates a rectangle-signal based on the values of the input types.
 * </p>
 * 
 * @author smk
 * @version <p>13.04.2004: created, smk</p>
 */
public class RectangleGenerator
extends Generator
{
	public static final int	INPUT_TYPE_FREQ		= 1;
	public static final int	INPUT_TYPE_AMPL		= 2;
	public static final int	INPUT_TYPE_SHIFT	= 3;
	
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
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator)
	{
		//----------------------------------------------------------------------
		float signalFrequency;
//		try
//		{
			signalFrequency = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ), parentModulGenerator);
//		}
//		catch (NoInputSignalException ex)
//		{
//			signalFrequency = 0.0F;
//		}

		//----------------------------------------------------------------------
		float signalAmplitude;
//		try
//		{
			// Amplitude des gerade generierten Sinus-Siganls.
			signalAmplitude = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), parentModulGenerator);
//		}
//		catch (NoInputSignalException ex)
//		{
//			signalAmplitude = 0.0F;
//		}
		
		//----------------------------------------------------------------------
		float signalShift;
//		try
//		{
			// Versatz des Sinus-Siganls um eine Schwingung.
			signalShift = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT), parentModulGenerator);
//		}
//		catch (NoInputSignalException ex)
//		{
//			signalShift = 0.0F;
//		}
		
		//----------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// L�nge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getSoundFrameRate() / signalFrequency);
		float	periodPosition = (float) (framePosition) / (float)periodLengthInFrames;
		//float value = ((float)Math.sin(periodPosition * (2.0F * Math.PI) + (signalShift * Math.PI))) * signalAmplitude;

		float value;
		
		if (((periodPosition + signalShift) % 1.0F) > 0.5F)
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
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(RectangleGenerator.class, "Rectangle", "Generates a rectangle signal with a specified frequency and amplidude.");
		
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
		
		return generatorTypeData;
	}
}
