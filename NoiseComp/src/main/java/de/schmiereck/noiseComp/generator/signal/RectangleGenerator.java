package de.schmiereck.noiseComp.generator.signal;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.util.Iterator;
import java.util.Objects;

import static de.schmiereck.noiseComp.generator.GeneratorUtils.calcPeriodFadeValue;
import static de.schmiereck.noiseComp.service.StartupService.SIGNAL_GENERATOR_FOLDER_PATH;

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
extends Generator {
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_FREQ			= 1;
	public static final int	INPUT_TYPE_AMPL			= 2;
	public static final int	INPUT_TYPE_SHIFT		= 3;
	public static final int	INPUT_TYPE_PULSE_WIDTH	= 4; // pulse width (0.0 to 1.0, 0,5 is Square (Default))
	public static final int	INPUT_TYPE_IIFREQ		= 5;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public RectangleGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData) {
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
		// Frequency of generated Signal oscillation.
		float signalFrequency = Float.NaN;
		// Integrated Input of Frequency of generated Signal oscillation.
		float signalIIFreq = Float.NaN;
		// Amplitude of generated Signal.
		float signalAmplitude = Float.NaN;
		// Shift of generated Signal oscillation.
		float signalShift = Float.NaN;
		// Pulse-Width of generated Signal oscillation (0.0 to 1.0, 0,5 is Square).
		// Width of Signal per half oscillation.
		float pulseWidth = Float.NaN;

		final OscillatorSoundSample oscillatorSoundSample = (OscillatorSoundSample) soundSample;

		//==========================================================================================
		final Object inputsSyncObject = this.getInputsSyncObject();

		if (Objects.nonNull(inputsSyncObject)) {
			synchronized (inputsSyncObject) {
				signalFrequency = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FREQ),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				signalIIFreq = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_IIFREQ),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				signalAmplitude = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_AMPL),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				signalShift = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SHIFT),
						parentModuleGenerator, generatorBuffer, moduleArguments);

				pulseWidth = this.calcInputMonoValueSumByInputType(framePosition, frameTime,
						this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_PULSE_WIDTH),
						parentModuleGenerator, generatorBuffer, moduleArguments);
			}
		}
		//------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------
		// Phase in the range [0, 1) is the Period-Position.
		// Phase of the Signal in the current period.
		final float actualPhase;
		// Length of a Sinus-Periode in Frames.
		final float periodLengthInFrames;
		// Phase of the Signal in the current period.
		//float periodPosition;

		if (Float.isNaN(signalFrequency) == false) {
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
			actualPhase = (previousPhase + (signalFrequency * deltaT)) % 1.0F;

			// output = Math.sin(TWO_PI * actualPhase);

			// Length of a Period in Frames.
			periodLengthInFrames = (sampleRate / signalFrequency);
			//periodPosition = (framePosition / periodLengthInFrames);
		} else {
			actualPhase = 0.0F;

			periodLengthInFrames = 1.0F;
			//periodPosition = 0.0F;
		}
		
		if (Float.isNaN(signalIIFreq) == false) {
			//periodPosition += signalIIFreq;
		}

		// signalAmplitude is not defined?
		if (Float.isNaN(signalAmplitude) == true) {
			// Use default value.
			signalAmplitude = 1.0F;
		}

		// signalShift is not defined?
		if (Float.isNaN(signalShift) == true) {
			// Use default value.
			signalShift = 0.0F;
		}

		// pulseWidth is not defined?
		if (Float.isNaN(pulseWidth) == true) {
			// Use default value.
			pulseWidth = 0.5F;
		}

		//------------------------------------------------------------------------------------------
		final float fadeValue = calcPeriodFadeValue(this.getStartTimePos(), this.getEndTimePos(),
				this.getSoundFrameRate(), frameTime, periodLengthInFrames);

		float value;
		
		//if (((periodPosition + signalShift) % 1.0F) > pulseWidth) { //0.5F)
		if (((actualPhase + signalShift) % 1.0F) > pulseWidth) { //0.5F)
			value = signalAmplitude * fadeValue;
		} else {
			value = -signalAmplitude * fadeValue;
		}
		
		soundSample.setStereoValues(value, value);
		oscillatorSoundSample.setPhase(actualPhase);

		//==========================================================================================
	}
	

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample_old(long framePosition, float frameTime, SoundSample soundSample, ModuleGenerator parentModuleGenerator,
									 GeneratorBufferInterface generatorBuffer,
									 ModuleArguments moduleArguments) {
		//==========================================================================================
		// Frequency of generated Signal oscillation.
		float signalFrequency = Float.NaN;
		// Integrated Input of Frequency of generated Signal oscillation.
		float signalIIFreq = Float.NaN;
		// Amplitude of generated Signal.
		float signalAmplitude = Float.NaN;
		// Shift of generated Signal oscillation.
		float signalShift = Float.NaN;
		// Pulse-Width of generated Signal oscillation (0.0 to 1.0, 0,5 is Square).
		// Width of Signal per half oscillation.
		float pulseWidth = Float.NaN;

		//==========================================================================================
		Object inputsSyncObject = this.getInputsSyncObject();

		if (inputsSyncObject != null) {
			synchronized (inputsSyncObject) {
				Iterator<InputData> inputsIterator = this.getInputsIterator();

				if (inputsIterator != null) {
					while (inputsIterator.hasNext()) {
						InputData inputData = inputsIterator.next();

						switch (inputData.getInputTypeData().getInputType()) {
							case INPUT_TYPE_FREQ: {
								final float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData,
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false) {
									if (Float.isNaN(signalFrequency) == false) {
										signalFrequency += value;
									} else {
										signalFrequency = value;
									}
								}
								break;
							}
							case INPUT_TYPE_IIFREQ: {
								final float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData,
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false) {
									if (Float.isNaN(signalIIFreq) == false) {
										signalIIFreq += value;
									} else {
										signalIIFreq = value;
									}
								}
								break;
							}
							case INPUT_TYPE_AMPL: {
								final float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData,
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false) {
									if (Float.isNaN(signalAmplitude) == false) {
										signalAmplitude += value;
									} else {
										signalAmplitude = value;
									}
								}
								break;
							}
							case INPUT_TYPE_SHIFT: {
								final float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData,
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false) {
									if (Float.isNaN(signalShift) == false) {
										signalShift += value;
									} else {
										signalShift = value;
									}
								}
								break;
							}
							case INPUT_TYPE_PULSE_WIDTH: {
								final float value =
									this.calcInputMonoValueByInputData(framePosition,
									                        frameTime,
									                        inputData,
									                        parentModuleGenerator,
									                        generatorBuffer,
									                        moduleArguments);

								if (Float.isNaN(value) == false) {
									if (Float.isNaN(pulseWidth) == false) {
										pulseWidth += value;
									} else {
										pulseWidth = value;
									}
								}
								break;
							}
							default: {
								throw new RuntimeException("Unknown input type \"" + inputData.getInputTypeData() + "\".");
							}
						}
					}
				}
			}
		}
		//------------------------------------------------------------------------------------------
		final float periodLengthInFrames;
		// Pos in Periode.
		float periodPosition;

		if (Float.isNaN(signalFrequency) == false) {
			// Length of a Period in Frames.
			periodLengthInFrames = (float)/*Math.floor*/(this.getSoundFrameRate() / signalFrequency);
			periodPosition = (float)(framePosition / periodLengthInFrames);
		} else {
			periodLengthInFrames = 1.0F;
			periodPosition = 0.0F;
		}

		if (Float.isNaN(signalIIFreq) == false) {
			periodPosition += signalIIFreq;
		}

		// signalAmplitude is not defined?
		if (Float.isNaN(signalAmplitude) == true) {
			// Use default value.
			signalAmplitude = 1.0F;
		}

		// signalShift is not defined?
		if (Float.isNaN(signalShift) == true) {
			// Use default value.
			signalShift = 0.0F;
		}

		// pulseWidth is not defined?
		if (Float.isNaN(pulseWidth) == true) {
			// Use default value.
			pulseWidth = 0.5F;
		}

		//------------------------------------------------------------------------------------------
		final float fadeValue = calcPeriodFadeValue(this.getStartTimePos(), this.getEndTimePos(),
				this.getSoundFrameRate(), frameTime, periodLengthInFrames);

		float value;

		if (((periodPosition + signalShift) % 1.0F) > pulseWidth) { //0.5F)
			value = signalAmplitude * fadeValue;
		} else {
			value = -signalAmplitude * fadeValue;
		}

		soundSample.setStereoValues(value, value);

		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData() {
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SIGNAL_GENERATOR_FOLDER_PATH, RectangleGenerator.class, "Rectangle", "Generates a rectangle signal with a specified frequency and amplidude.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FREQ, "signalFrequency", 1, 1, Float.valueOf(1.0F), "Frequency of the signal in oscillations per second.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_AMPL, "signalAmplitude", 1, 1, Float.valueOf(1.0F), "Amplidude of the signal between 0 and 1.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SHIFT, "signalShift", 0, 1, Float.valueOf(0.0F), "The offset of the rectangle between -1 and 1 (0 is no shift, 0.5 is shifting a half oscillation).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_PULSE_WIDTH, "pulseWidth", 0, 1, Float.valueOf(0.5F), "The pulse width of the rectangle between 0 and 1 (0.5 (Default) is rectangle).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_IIFREQ, "signalIIFreq", -1, -1, null, "Integrated Input of the frequenz signal (alternativ to signalFrequency).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeInfoData;
	}
}
