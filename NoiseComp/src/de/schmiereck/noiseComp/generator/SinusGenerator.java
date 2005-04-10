package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Generates a sinus-signal based on the values of the input types.
 * </p>
 *
 * Frequenz des generierten Sinus-Signals.
 *	 Frequenz  Note  Instrument  
 *	 16.5Hz  C2  Taste C im 32' der Orgel 
 *	 33Hz  C1  C-Saite bei fünfseitigen Kontrabässen 
 *	 66Hz  C  C-Saite der Violoncelli 
 *	 131Hz  c  C-Saite der Bratschen 
 *	 262Hz  c'  tiefstes c der Geigen 
 *	 524Hz  c''  hohes c der Tenöre 
 *	 1047Hz  c'''  hohes c der Soprane 
 *	 2093Hz  c4  höchstes c der Geigen 
 *	 4185Hz  c5  höchstes c der Piccolo-Flöten
 * 
 * @author smk
 * @version 21.01.2004
 */
public class SinusGenerator
extends Generator
{
	public static final int	INPUT_TYPE_FREQ		= 1;
	public static final int	INPUT_TYPE_AMPL		= 2;
	public static final int	INPUT_TYPE_SHIFT	= 3;
	
	/**
	 * Constructor.
	 * 
	 * @param frameRate	Frames per Second
	 */
	public SinusGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		try
		{
			signalFrequency = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ), parentModulGenerator);
		}
		catch (NoInputSignalException ex)
		{
			signalFrequency = 0.0F;
		}

		//----------------------------------------------------------------------
		float signalAmplitude;
		try
		{
			// Amplitude des gerade generierten Sinus-Siganls.
			signalAmplitude = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), parentModulGenerator);
		}
		catch (NoInputSignalException ex)
		{
			signalAmplitude = 0.0F;
		}
		
		//----------------------------------------------------------------------
		float signalShift;
		try
		{
			// Versatz des Sinus-Siganls um eine Schwingung.
			signalShift = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT), parentModulGenerator);
		}
		catch (NoInputSignalException ex)
		{
			signalShift = 0.0F;
		}
		
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// Länge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getSoundFrameRate() / signalFrequency);
		float	periodPosition = (float) (framePosition) / (float)periodLengthInFrames;
		float value = ((float)Math.sin(periodPosition * (2.0F * Math.PI) + (signalShift * Math.PI))) * signalAmplitude;
		
		//float value = ((float)Math.sin(frameTime * ((2.0F + signalShift) * Math.PI))) * amplitude;
		
		soundSample.setStereoValues(value, value);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(SinusGenerator.class, "Sinus", "Generates a sinus signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the signal in oscillations per second.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", 0, 1, Float.valueOf(0.0F), "The offset of the sinus between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
