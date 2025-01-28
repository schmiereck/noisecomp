package de.schmiereck.noiseComp.generator.signal;
/*
 * Created on 09.04.2005, Copyright (c) schmiereck, 2005
 */

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.util.Objects;

import static de.schmiereck.noiseComp.generator.GeneratorUtils.calcPeriodFadeValue;
import static de.schmiereck.noiseComp.service.StartupService.SIGNAL_GENERATOR_FOLDER_PATH;

/**
 * <p>
ASR:

Attack
Zeit die ein Signal benötigt, um von Wert Null auf die max. Amplitude (Lautstärke) zu gelangen; Einschwingphase einer Voice
Anschlag: Ansteigen auf hohen Wert.

Sustain
Voice aushalten
Halten des Tones auf Lautstärke.

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
extends Generator {
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
	public ASRPulseGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData) {
		super(name, frameRate, generatorTypeInfoData);
	}

	/**
	 * Funktion zur Generierung des SoundSample im Generator,
	 * so das zusätzliche Informationen (z.B. die Phase) in den SoundSample geschrieben werden können.
	 */
	public SoundSample createSoundSample() {
		return new OscillatorSoundSample();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(final long framePosition, final float frameTime,
									 final SoundSample soundSample,
									 final ModuleGenerator parentModuleGenerator,
									 final GeneratorBufferInterface generatorBuffer,
									 final ModuleArguments moduleArguments) {
		//==========================================================================================
		final OscillatorSoundSample oscillatorSoundSample = (OscillatorSoundSample) soundSample;

		//==========================================================================================
		float signalFrequency = Float.NaN;
		float signalAmplitude = Float.NaN;
		float attackTime = Float.NaN;
		float sustainTime = Float.NaN;
		float releaseTime = Float.NaN;

		final Object inputsSyncObject = this.getInputsSyncObject();

		if (Objects.nonNull(inputsSyncObject)) {
			synchronized (inputsSyncObject) {
				signalFrequency = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				signalAmplitude = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				attackTime = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_ATTACK_TIME),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				sustainTime = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SUSTAIN_TIME),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				releaseTime = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_RELEASE_TIME),
						parentModuleGenerator, generatorBuffer, moduleArguments);
			}
		}
		//---------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());

		// https://chatgpt.com/c/67972cde-b54c-8013-889a-e739689095d8

		final float sampleRate = this.getSoundFrameRate();

		final long previousFramePosition = framePosition - 1L;
		final float previousFrameTime = previousFramePosition / this.getSoundFrameRate();

		final OscillatorSoundSample previousOscillatorSoundSample = (OscillatorSoundSample)
				generatorBuffer.calcFrameSample(previousFramePosition, previousFrameTime, parentModuleGenerator, moduleArguments);

		// Delta-Time between two Samples.
		final float deltaT = 1.0F / sampleRate;

		// Calculate actual Phase (integrated) based on the previous Phase (read from previous Sample).
		final float previousPhase = Objects.nonNull(previousOscillatorSoundSample) ? previousOscillatorSoundSample.getPhase() : 0.0F;
		final float actualPhase = (previousPhase + (signalFrequency * deltaT)) % 1.0F;

		// output = Math.sin(TWO_PI * actualPhase);

		// Length of a Period in Frames.
		final float periodLengthInFrames = (sampleRate / signalFrequency);
		//periodPosition = (framePosition / periodLengthInFrames);

		// Länge einer Sinus-Periode in Frames.
		//float periodLengthInFrames = (float)Math.floor(this.getSoundFrameRate() / signalFrequency);
		
		// Die Position im Puls in Prozent als Wert zwischen 0.0 und 1.0.
		//float periodPosition = (float) (framePosition) / (float)periodLengthInFrames;

		//---------------------------------------------------------------------
		float value;
		
		//float ppos = periodPosition % 1.0F;
		float ppos = actualPhase % 1.0F;

		// After Attack + Sustain + Release ?
		if (ppos > (attackTime + sustainTime + releaseTime)) {
			// Between pulses:
			value = 0;
		} else {
			// After: Attack + Sustain ?
			if (ppos > (attackTime + sustainTime)) {
				// Release:
				
				//   x              100%
				// ------------- = ------
				//  releaseTime     1.0
				
				float f = (ppos - (attackTime + sustainTime)) / releaseTime;
				
				value = signalAmplitude - (signalAmplitude * f);
			} else {
				// After Attack?
				if (ppos > (attackTime)) {
					// Sustain:
					value = signalAmplitude;
				} else {
					// Attack:
					float f = ppos / attackTime;
					value = signalAmplitude * f;
				}
			}
		}
		final float fadeValue = calcPeriodFadeValue(this.getStartTimePos(), this.getEndTimePos(),
				this.getSoundFrameRate(), frameTime, periodLengthInFrames);

		soundSample.setStereoValues(value * fadeValue, value * fadeValue);
		oscillatorSoundSample.setPhase(actualPhase);
	}
	

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample_old(final long framePosition, final float frameTime,
									 final SoundSample soundSample,
									 final ModuleGenerator parentModuleGenerator,
									 final GeneratorBufferInterface generatorBuffer,
									 final ModuleArguments moduleArguments) {
		//----------------------------------------------------------------------
		float signalFrequency;
//		try
//		{
			signalFrequency = this.calcSingleInputMonoValueByInputType(framePosition,
			                                          frameTime,
			                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ),
			                                          parentModuleGenerator,
			                                          generatorBuffer,
			                                          moduleArguments);
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
			signalAmplitude = this.calcSingleInputMonoValueByInputType(framePosition,
			                                          frameTime,
			                                          this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL),
			                                          parentModuleGenerator,
			                                          generatorBuffer,
			        	                              moduleArguments);
//		}
//		catch (NoInputSignalException ex)
//		{
//			signalAmplitude = 0.0F;
//		}

		//----------------------------------------------------------------------
		float attackTime;
//		try
//		{
			attackTime = this.calcSingleInputMonoValueByInputType(framePosition,
			                                     frameTime,
			                                     this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_ATTACK_TIME),
			                                     parentModuleGenerator,
			                                     generatorBuffer,
		        	                             moduleArguments);
//		}
//		catch (NoInputSignalException ex)
//		{
//			attackTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		float sustainTime;
//		try
//		{
			sustainTime = this.calcSingleInputMonoValueByInputType(framePosition,
		                                          frameTime,
			                                      this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SUSTAIN_TIME),
			                                      parentModuleGenerator,
			                                      generatorBuffer,
		        	                              moduleArguments);
//		}
//		catch (NoInputSignalException ex)
//		{
//			sustainTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		float releaseTime;
//		try
//		{
			releaseTime = this.calcSingleInputMonoValueByInputType(framePosition,
		                                          frameTime,
			                                      this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_RELEASE_TIME),
			                                      parentModuleGenerator,
			                                      generatorBuffer,
		        	                              moduleArguments);

//		}
//		catch (NoInputSignalException ex)
//		{
//			releaseTime = 0.0F;
//		}
		//----------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		//float timePos = frameTime - (this.getStartTimePos());


		// Länge einer Sinus-Periode in Frames.
		float periodLengthInFrames = (float)Math.floor(this.getSoundFrameRate() / signalFrequency);

		// Die Position im Puls in Prozent als Wert zwischen 0.0 und 1.0.
		float periodPosition = (float) (framePosition) / (float)periodLengthInFrames;

		float value;

		float ppos = periodPosition % 1.0F;

		// After Attack + Sustain + Release ?
		if (ppos > (attackTime + sustainTime + releaseTime)) {
			// Between pulses:
			value = 0;
		} else {
			// After: Attack + Sustain ?
			if (ppos > (attackTime + sustainTime)) {
				// Release:

				//   x              100%
				// ------------- = ------
				//  releaseTime     1.0

				float f = (ppos - (attackTime + sustainTime)) / releaseTime;

				value = signalAmplitude - (signalAmplitude * f);
			} else {
				// After Attack?
				if (ppos > (attackTime)) {
					// Sustain:
					value = signalAmplitude;
				} else {
					// Attack:
					float f = ppos / attackTime;
					value = signalAmplitude * f;
				}
			}
		}
		final float fadeValue = calcPeriodFadeValue(this.getStartTimePos(), this.getEndTimePos(),
				this.getSoundFrameRate(), frameTime, periodLengthInFrames);

		soundSample.setStereoValues(value * fadeValue, value * fadeValue);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SIGNAL_GENERATOR_FOLDER_PATH, ASRPulseGenerator.class, "ASRPulse", "Generates a pulse signal with a specified frequency and amplidude and puls shape.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the pulse in oscillations per second.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the pulse between 0 and 1.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_ATTACK_TIME, "attackTime", 1, 1, Float.valueOf(0.1F), "The attack time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SUSTAIN_TIME, "sustainTime", 1, 1, Float.valueOf(0.3F), "The sustain time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_RELEASE_TIME, "releaseTime", 1, 1, Float.valueOf(0.2F), "The release time of the pulse in percent between 0.0 and 1.0.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}
}
