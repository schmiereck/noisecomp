package de.schmiereck.noiseComp.generator;
/*
 * Created on 09.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
ASR:

Attack
Zeit die ein Signal ben�tigt, um von Wert Null auf die max. Amplitude (Lautst�rke) zu gelangen; Einschwingphase einer Voice
Anschlag: Ansteigen auf hohen Wert.

Sustain
Voice aushalten
Halten des Tones auf Lautst�rke.

Release
Loslassen der Taste, Nachklingen einer Voice
Absenken und Ausklingen zu Null.


     _________
    /         \
 __/           \___
--+--+-------+--+-----
  A  S       R
 * </p>
 * 
 * @author smk
 * @version <p>09.04.2005:	created, smk</p>
 */
public class ASRPulseGenerator
extends Generator
{
	public static final int	INPUT_TYPE_FREQ			= 1;
	public static final int	INPUT_TYPE_AMPL			= 2;
	public static final int	INPUT_TYPE_ATTACK_TIME	= 3;
	public static final int	INPUT_TYPE_SUSTAIN_TIME	= 4;
	public static final int	INPUT_TYPE_RELEASE_TIME	= 5;
	
	/**
	 * Constructor.
	 * 
	 * @param frameRate	Frames per Second
	 */
	public ASRPulseGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, 
	                                 SoundSample soundSample, 
	                                 ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer)
	{
		//----------------------------------------------------------------------
		float signalFrequency;
//		try
//		{
			signalFrequency = this.calcInputMonoValue(framePosition, 
			                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ),
			                                          parentModulGenerator,
			                                          generatorBuffer);
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
			signalAmplitude = this.calcInputMonoValue(framePosition, 
			                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL), 
			                                          parentModulGenerator,
			                                          generatorBuffer);
//		}
//		catch (NoInputSignalException ex)
//		{
//			signalAmplitude = 0.0F;
//		}
		
		//----------------------------------------------------------------------
		float attackTime;
//		try
//		{
			attackTime = this.calcInputMonoValue(framePosition, 
			                                     this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_ATTACK_TIME), 
			                                     parentModulGenerator,
			                                     generatorBuffer);
//		}
//		catch (NoInputSignalException ex)
//		{
//			attackTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		float sustainTime;
//		try
//		{
			sustainTime = this.calcInputMonoValue(framePosition, 
			                                      this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SUSTAIN_TIME), 
			                                      parentModulGenerator,
			                                      generatorBuffer);
//		}
//		catch (NoInputSignalException ex)
//		{
//			sustainTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		float releaseTime;
//		try
//		{
			releaseTime = this.calcInputMonoValue(framePosition, 
			                                      this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_RELEASE_TIME), 
			                                      parentModulGenerator,
			                                      generatorBuffer);
		
//		}
//		catch (NoInputSignalException ex)
//		{
//			releaseTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());
		
		
		// Länge einer Sinus-Periode in Frames.
		int periodLengthInFrames = Math.round(this.getSoundFrameRate() / signalFrequency);
		
		// Die Position im Puls in Prozent als Wert zwischen 0.0 und 1.0.
		float	periodPosition = (float) (framePosition) / (float)periodLengthInFrames;

		float value;
		
		float ppos = periodPosition % 1.0F;
		
		// After Attack + Sustain + Release ?
		if (ppos > (attackTime + sustainTime + releaseTime))
		{
			// Between pulses:
			
			value = 0;
		}
		else
		{
			// After: Attack + Sustain ?
			if (ppos > (attackTime + sustainTime))
			{
				// Release:
				
				//   x              100%
				// ------------- = ------
				//  releaseTime     1.0
				
				float f = (ppos - (attackTime + sustainTime)) / releaseTime;
				
				value = signalAmplitude - (signalAmplitude * f);
			}
			else
			{
				// After Attack?
				if (ppos > (attackTime))
				{
					// Sustain:
					
					value = signalAmplitude;
				}
				else
				{
					// Attack:

					float f = ppos / attackTime;

					value = signalAmplitude * f;
				}
			}
		}
		
		soundSample.setStereoValues(value, value);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(ASRPulseGenerator.class, "ASRPulse", "Generates a pulse signal with a specified frequency and amplidude and puls shape.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the pulse in oscillations per second.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the pulse between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_ATTACK_TIME, "attackTime", 1, 1, Float.valueOf(0.1F), "The attack time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SUSTAIN_TIME, "sustainTime", 1, 1, Float.valueOf(0.3F), "The sustain time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_RELEASE_TIME, "releaseTime", 1, 1, Float.valueOf(0.2F), "The release time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
}
