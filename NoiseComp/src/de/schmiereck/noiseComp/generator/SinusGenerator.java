package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

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
	public static final int	INPUT_TYPE_IIFREQ	= 4;
	public static final int	INPUT_TYPE_PULSE_WIDTH	= 5; // pulse width (0.0 to 1.0 (0.5 is Default))
	
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
		// Frequency of generated Signal oscillation.
		float signalFrequency = Float.NaN;
		// Integrated Input of Frequency of generated Signal oscillation.
		float signalIIFreq = Float.NaN;
		// Amplitude of generated Signal.
		float signalAmplitude = Float.NaN;
		// Shif of generated Signal oscillation.
		float signalShift = Float.NaN;
		// Pulse-Width of generated Signal oscillation (0.0 to 1.0).
		// Width of Signal per half oscillation.
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
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
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
			pulseWidth = 0.5F;
		}
		
		//------------------------------------------------------------------------------------------
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
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", SinusGenerator.class, "Sinus", "Generates a sinus signal with a specified frequency and amplidude.");
		
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
//			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_INPUT, "signalInput", -1, -1, null, "Integrated Input of the frequenz signal (alternativ to signalFrequency).");
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_IIFREQ, "signalIIFreq", -1, -1, null, "Integrated Input of the frequenz signal (alternativ to signalFrequency).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
