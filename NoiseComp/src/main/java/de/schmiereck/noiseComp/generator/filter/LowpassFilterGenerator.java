/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

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
	public LowpassFilterGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
	{
		super(name, frameRate, generatorTypeInfoData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample signalSample, ModuleGenerator parentModuleGenerator,
									 GeneratorBufferInterface generatorBuffer,
									 ModuleArguments moduleArguments)
	{
		//==========================================================================================
		final LowpassFilterGeneratorData data = (LowpassFilterGeneratorData)generatorBuffer.getGeneratorData();
		
		//==========================================================================================
		InputData signalInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		if (signalInputData != null)
		{
			this.calcInputValue(framePosition, 
	                            frameTime,
			                    signalInputData, 
			                    signalSample, 
			                    parentModuleGenerator, 
			                    generatorBuffer,
	                            moduleArguments);
		}
		//------------------------------------------------------------------------------------------
		float cutoff = 
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_CUTOFF), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		float soundFrameRate = this.getSoundFrameRate();
		
		double ax[] = new double[3];
		double by[] = new double[3];

		this.calcLPCoefficientsButterworth2Pole(data, (int)soundFrameRate, cutoff, ax, by);

		float value = signalSample.getMonoValue();
		
		if (Float.isNaN(value) == false)
		{
			value = (float)this.filter(data, ax, by, value);
		}
		else
		{
			value = Float.NaN;
		}
		
		signalSample.setMonoValue(value);
		
//		float leftValue = signalSample.getLeftValue();
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
//		signalSample.setStereoValues(leftValue, rightValue);
		//==========================================================================================
	}

	void calcLPCoefficientsButterworth2Pole(final LowpassFilterGeneratorData data,
	                                        final int samplerate, 
	                                        final double cutoff, 
	                                        final double ax[], 
	                                        final double by[])
	{
		//==========================================================================================
	    // Find cutoff frequency in [0..PI]
	    double qcRaw  = (2 * Math.PI * cutoff) / samplerate;
	    // Warp cutoff frequency
	    double qcWarp = Math.tan(qcRaw); 

	    double gain = 1 / ( 1 + sqrt2 / qcWarp + 2 / (qcWarp * qcWarp));
	    
	    by[2] = (1 - sqrt2 / qcWarp + 2 / (qcWarp * qcWarp)) * gain;
	    by[1] = (2 - 2 * 2 / (qcWarp * qcWarp)) * gain;
	    by[0] = 1;
	    ax[0] = 1 * gain;
	    ax[1] = 2 * gain;
	    ax[2] = 1 * gain;
	    
		//==========================================================================================
	}

	void filter(final LowpassFilterGeneratorData data,
                final double samples[], int count)
	{
		//==========================================================================================
		double ax[] = new double[3];
		double by[] = new double[3];

		this.calcLPCoefficientsButterworth2Pole(data, 44100, 5000, ax, by);

		for (int i = 0; i < count; i++)
		{
			double sample = this.filter(data, ax, by, samples[i]);
			
			samples[i] = sample;
		}
		//==========================================================================================
	}
	
	private double filter(final LowpassFilterGeneratorData data,
	                      double ax[], double by[],
	                      final double sample)
	{
		//==========================================================================================
		data.xv[2] = data.xv[1]; 
		data.xv[1] = data.xv[0];
		data.xv[0] = sample;
		
		data.yv[2] = data.yv[1]; 
		data.yv[1] = data.yv[0];
		data.yv[0] = (ax[0] * data.xv[0] + 
				 ax[1] * data.xv[1] + 
				 ax[2] * data.xv[2] - 
    		     by[1] * data.yv[0] - 
    		     by[2] * data.yv[1]);
		
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
	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
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
