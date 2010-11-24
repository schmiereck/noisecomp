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
 * http://forums.creativecow.net/thread/227/13104
 * needs integral of the signalFrequency over time
 * this needs a working buffer system for generators
 * 
 * @author smk
 * @version 21.01.2004
 */
public class SinusGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_FREQ		= 1;
	public static final int	INPUT_TYPE_AMPL		= 2;
	public static final int	INPUT_TYPE_SHIFT	= 3;
	public static final int	INPUT_TYPE_INPUT	= 4;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

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
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
	{
		//==========================================================================================
//		float dt = (1.0F / this.getSoundFrameRate());
//		
//		//==========================================================================================
		// Sinus Frequenz of the Sinus-Signal.
		float signalFrequency = 
			this.calcInputMonoValue(framePosition, 
                                    frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ), 
			                        parentModulGenerator,
			                        generatorBuffer,
		                            modulArguments);

		//------------------------------------------------------------------------------------------
		// Amplitude of the Sinus-Signal.
		float signalAmplitude = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), 
			                        parentModulGenerator,
			                        generatorBuffer,
		                            modulArguments);
		
		//------------------------------------------------------------------------------------------
		// Shift of the Sinus-Signal.
		float signalShift = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT), 
			                        parentModulGenerator,
			                        generatorBuffer,
			                        modulArguments);
		
		//------------------------------------------------------------------------------------------
		// Integrated Input of the Sinus-Signal.
		float signalInput;
		{
			InputTypeData inputTypeData = this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_INPUT);
			
			if (inputTypeData != null)
			{
				signalInput = 
					this.calcInputMonoValue(framePosition, 
		                                    frameTime,
		                                    inputTypeData, 
					                        parentModulGenerator,
					                        generatorBuffer,
					                        modulArguments);
			}
			else
			{
				signalInput = Float.NaN;
			}
		}
		//------------------------------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		float periodPosition;
		
		if (Float.isNaN(signalInput) == false)
		{
			periodPosition = signalInput;
		}
		else
		{
			// Länge einer Sinus-Periode in Frames.
			float periodLengthInFrames = (float)/*Math.floor*/(this.getSoundFrameRate() / signalFrequency);
			periodPosition = (float)(framePosition / periodLengthInFrames);
		}
		
		float value;
		
		if (Float.isNaN(periodPosition) == false)
		{
			float s = (float)(periodPosition * (2.0F * Math.PI) + (signalShift * Math.PI));
			
			value = (float)(Math.sin(s) * signalAmplitude);

			soundSample.setStereoValues(value, value);
		}
		else
		{
			soundSample.setNaN();
		}
		
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(SinusGenerator.class, "Sinus", "Generates a sinus signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, null, "Frequency of the signal in oscillations per second (alternativ to signalInput).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", -1, 1, Float.valueOf(0.0F), "The offset of the sinus between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_INPUT, "signalInput", -1, -1, null, "Input of the sinus signal (alternativ to signalFrequency).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
