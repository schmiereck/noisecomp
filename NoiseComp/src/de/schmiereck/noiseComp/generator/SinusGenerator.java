package de.schmiereck.noiseComp.generator;

/**
 * Erzeugt einzelne Samples anhand der angegebenen Frame-Position
 * auf der Timeline.
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
	 */
	//private float signalFrequency = 1.0F;
	
	/**
	 * Constructor.
	 * 
	 * Frames per Second
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
		float signalFrequency = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ), parentModulGenerator);

		// Amplitude des gerade generierten Sinus-Siganls.
		float signalAmplitude = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), parentModulGenerator);
		
		// Versatz des Sinus-Siganls um eine Schwingung.
		float signalShift = this.calcInputMonoValue(framePosition, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT), parentModulGenerator);
		
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// Länge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getFrameRate() / signalFrequency);
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
