/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.generator.signal.OscillatorSoundSample;

import java.util.Objects;

import static de.schmiereck.noiseComp.service.StartupService.FILTER_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Lowpass-Filter Generator.
 * </p>
 * 
 * see: http://baumdevblog.blogspot.com/2010/11/butterworth-lowpass-filter-coefficients.html
 * 
 * @author smk
 * @version <p>23.02.2011:	created, smk</p>
 */
public class LowpassFilterGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL		= 1;
	public static final int	INPUT_CUTOFF			= 2;
	
    private final static double sqrt2 = 1.4142135623730950488;

    //**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param frameRate	
	 * 			are the Frames per Second.
	 */
	public LowpassFilterGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData) {
		super(name, frameRate, generatorTypeInfoData);
	}

	/**
	 * Funktion zur Generierung des SoundSample im Generator,
	 * so das zusätzliche Informationen (z.B. die Phase) in den SoundSample geschrieben werden können.
	 */
	public SoundSample createSoundSample() {
		//return new LowpassFilterSoundSample(new LowpassFilterGeneratorData(new double[3], new double[3]));
		return new LowpassFilterSoundSample();
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
		final LowpassFilterSoundSample lowpassFilterSoundSample = (LowpassFilterSoundSample) soundSample;

		final LowpassFilterGeneratorData lowpassFilterData = (LowpassFilterGeneratorData)generatorBuffer.getGeneratorData();

		float cutoff = Float.NaN;

		//==========================================================================================
		final Object inputsSyncObject = this.getInputsSyncObject();

		if (Objects.nonNull(inputsSyncObject)) {
			synchronized (inputsSyncObject) {
				final InputData signalInputData =
						this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));

				if (signalInputData != null) {
					this.calcInputValue(framePosition,
							frameTime,
							signalInputData,
							soundSample,
							parentModuleGenerator,
							generatorBuffer,
							moduleArguments);
				}
				//------------------------------------------------------------------------------------------
				cutoff =
						this.calcInputMonoValueSumByInputType(framePosition,
								frameTime,
								this.getGeneratorTypeData().getInputTypeData(INPUT_CUTOFF),
								parentModuleGenerator,
								generatorBuffer,
								moduleArguments);
			}
		}
		//==========================================================================================
		final float soundFrameRate = this.getSoundFrameRate();
		final float sampleRate = this.getSoundFrameRate();

		final long previousFramePosition = framePosition - 1L;
		final float previousFrameTime = previousFramePosition / sampleRate;

		final LowpassFilterSoundSample previousSoundSample = (LowpassFilterSoundSample)
				//generatorBuffer.calcFrameSample(previousFramePosition, previousFrameTime, parentModuleGenerator, moduleArguments);
				generatorBuffer.retrieveFrameSample(previousFramePosition, previousFrameTime, parentModuleGenerator, moduleArguments);

		final LowpassFilterGeneratorData previousLowpassFilterData;

		if (Objects.nonNull(previousSoundSample)) {
			previousLowpassFilterData = previousSoundSample.getData();
		} else {
			previousLowpassFilterData = null;
		}

		final LowpassFilterGeneratorData actualLowpassFilterData;

		if (Objects.nonNull(previousLowpassFilterData)) {
			actualLowpassFilterData = new LowpassFilterGeneratorData(previousLowpassFilterData);
		} else {
			actualLowpassFilterData = new LowpassFilterGeneratorData();
		}
		//------------------------------------------------------------------------------------------
		//double ax[] = new double[3];
		//double by[] = new double[3];

		if (lowpassFilterData.cutoff != cutoff) {
			lowpassFilterData.cutoff = cutoff;
			//this.calcLPCoefficientsButterworth2Pole(lowpassFilterData, (int)soundFrameRate, lowpassFilterData.cutoff);
			this.calcLPCoefficientsButterworth2Pole(actualLowpassFilterData, (int)soundFrameRate, lowpassFilterData.cutoff);
		}

		//this.calcLPCoefficientsButterworth2Pole(lowpassFilterData, (int)soundFrameRate, cutoff, ax, by);

		float value = soundSample.getMonoValue();
		
		if (Float.isNaN(value) == false) {
			//value = (float)this.filter(lowpassFilterData, value);
			value = (float)this.filter(actualLowpassFilterData, value);
		} else {
			value = Float.NaN;
		}
		
		soundSample.setMonoValue(value);
		lowpassFilterSoundSample.setData(actualLowpassFilterData);

//		float leftValue = soundSample.getLeftValue();
//		
//		if (Float.isNaN(leftValue) == false)
//		{
//			leftValue = (float)this.filter(ax, by, leftValue);
//		}
//		else
//		{
//			leftValue = Float.NaN;
//		}
//		
//		soundSample.setStereoValues(leftValue, rightValue);
		//==========================================================================================
	}

	void calcLPCoefficientsButterworth2Pole(final LowpassFilterGeneratorData data,
	                                        final int samplerate, 
	                                        final double cutoff) {
		//==========================================================================================
	    // Find cutoff frequency in [0..PI]
	    final double qcRaw  = (2.0D * Math.PI * cutoff) / samplerate;
	    // Warp cutoff frequency
		final double qcWarp = Math.tan(qcRaw);

		final double gain = 1.0D / (1.0D + sqrt2 / qcWarp + 2.0D / (qcWarp * qcWarp));
	    
	    data.by[2] = (1.0D - sqrt2 / qcWarp + 2.0D / (qcWarp * qcWarp)) * gain;
		data.by[1] = (2.0D - 2.0D * 2.0D / (qcWarp * qcWarp)) * gain;
		data.by[0] = 1.0D;
		data.ax[0] = 1.0D * gain;
		data.ax[1] = 2.0D * gain;
		data.ax[2] = 1.0D * gain;
	    
		//==========================================================================================
	}

	void filter(final LowpassFilterGeneratorData data,
                final double samples[], int count) {
		//==========================================================================================
		this.calcLPCoefficientsButterworth2Pole(data, 44100, 5000);

		for (int samplePos = 0; samplePos < count; samplePos++) {
			double sample = this.filter(data, samples[samplePos]);
			
			samples[samplePos] = sample;
		}
		//==========================================================================================
	}
	
	private double filter(final LowpassFilterGeneratorData data,
	                      final double sample) {
		//==========================================================================================
		data.xv[2] = data.xv[1]; 
		data.xv[1] = data.xv[0];
		data.xv[0] = sample;
		
		data.yv[2] = data.yv[1]; 
		data.yv[1] = data.yv[0];
		data.yv[0] = (data.ax[0] * data.xv[0] +
				data.ax[1] * data.xv[1] +
				data.ax[2] * data.xv[2] -
				data.by[1] * data.yv[0] -
				data.by[2] * data.yv[1]);
		
		//==========================================================================================
		return data.yv[0];
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorData()
	 */
	@Override
	public Object createGeneratorData()
	{
		return new LowpassFilterGeneratorData(new double[3], new double[3]);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData() {
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(FILTER_GENERATOR_FOLDER_PATH, LowpassFilterGenerator.class, "Lowpass", "Lowpass filter (Cutoff).");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_CUTOFF, "cutoff", 1, 1, Float.valueOf(5000.0F), "Cutoff value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeInfoData;
	}
}
