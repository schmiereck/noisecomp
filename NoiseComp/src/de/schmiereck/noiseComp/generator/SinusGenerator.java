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
	public static final int	INPUT_TYPE_FREQ	= 1;
	public static final int	INPUT_TYPE_AMPL	= 2;
	
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
	public SinusGenerator(String name, Float frameRate)
	{
		super(name, frameRate);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		float signalFrequency = this.calcSignalFrequency(framePosition);

		// Amplitude des gerade generierten Sinus-Siganls.
		float signalAmplitude = this.calcSignalAmplitude(framePosition);
		
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		// Länge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getFrameRate() / signalFrequency);
		float	periodPosition = (float) (framePosition) / (float)periodLengthInFrames;
		float value = ((float)Math.sin(periodPosition * 2.0 * Math.PI)) * signalAmplitude;
		
		//float value = ((float)Math.sin(frameTime * 2.0 * Math.PI)) * amplitude;
		
		soundSample.setStereoValues(value, value);
	}
	/**
	 * @param signalFrequency is the new value for attribute {@link #signalFrequency} to set.
	public void setSignalFrequency(float signalFrequency)
	{
		this.signalFrequency = signalFrequency;
	}
	 */
	/**
	 * @return the attribute {@link #signalFrequency}.
	public float getSignalFrequency()
	{
		return this.signalFrequency;
	}
	 */

	private float calcSignalFrequency(long framePosition)
	{
		/*
		float signalFrequency;
		{
			InputData signalFrequencyInputData = this.searchInputByType(INPUT_TYPE_FREQ);
			
			if (signalFrequencyInputData != null)
			{	
				Float signalFrequencyInputValue = signalFrequencyInputData.getInputValue();
				
				// Constant input value ?
				if (signalFrequencyInputValue != null)
				{
					signalFrequency = signalFrequencyInputValue.floatValue();
				}
				else
				{	
					Generator inputSoundGenerator = signalFrequencyInputData.getInputGenerator();
					
					if (inputSoundGenerator != null)
					{	
						SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
						
						if (inputSoundSample != null)
						{	
							signalFrequency = inputSoundSample.getMonoValue();
						}
						else
						{
							signalFrequency = 1.0F;
						}
					}
					else
					{
						signalFrequency = 1.0F;
					}
				}
			}
			else
			{
				signalFrequency = 1.0F;
			}
		}
		return signalFrequency;
		*/
		return this.calcMonoValue(framePosition, INPUT_TYPE_FREQ, 1.0F);
	}
	
	private float calcSignalAmplitude(long framePosition)
	{
		return this.calcMonoValue(framePosition, INPUT_TYPE_AMPL, 1.0F);
	}

	private float calcMonoValue(long framePosition, int inputType, float defaultValue)
	{
		float value;
		
		InputData inputData = this.searchInputByType(inputType);
		
		if (inputData != null)
		{	
			Float inputValue = inputData.getInputValue();
			
			// Constant input value ?
			if (inputValue != null)
			{
				value = inputValue.floatValue();
			}
			else
			{	
				Generator inputSoundGenerator = inputData.getInputGenerator();
				
				if (inputSoundGenerator != null)
				{	
					SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
					
					if (inputSoundSample != null)
					{	
						value = inputSoundSample.getMonoValue();
					}
					else
					{
						value = defaultValue;
					}
				}
				else
				{
					value = defaultValue;
				}
			}
		}
		else
		{
			value = defaultValue;
		}
		
		return value;
	}
	
	/**
	 * @return
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(SinusGenerator.class, "Sinus");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F));
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F));
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
