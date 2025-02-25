/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import static de.schmiereck.noiseComp.service.StartupService.FILTER_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Resonance-Filter Generator
 * </p>
 * 
 * see: http://sourceforge.net/projects/venomtwist
 * 
 * @author smk
 * @version <p>28.02.2011:	created, smk</p>
 */
public class ResonanceFilterGenerator
extends Generator {
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL		= 1;
	public static final int	INPUT_CUTOFF			= 2;
	public static final int	INPUT_RESONANCE			= 3;
	public static final int	INPUT_AMP				= 4;

    //**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param frameRate	
	 * 			are the Frames per Second.
	 */
	public ResonanceFilterGenerator(final String name, final Float frameRate, final GeneratorTypeInfoData generatorTypeInfoData) {
		super(name, frameRate, generatorTypeInfoData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(final long framePosition, final float frameTime,
									 final SoundSample signalSample,
									 final ModuleGenerator parentModuleGenerator,
									 final GeneratorBufferInterface generatorBuffer,
									 final ModuleArguments moduleArguments)
	{
		//==========================================================================================
		// TODO Store the data in the last SoundSample to use it to generate this new SoundSample (see SinusSoundSample).
		final ResonanceFilterGeneratorData data = (ResonanceFilterGeneratorData)generatorBuffer.getGeneratorData();
		
		//==========================================================================================
		final InputData signalInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		if (signalInputData != null) {
			this.calcInputValue(framePosition, 
	                            frameTime,
			                    signalInputData, 
			                    signalSample, 
			                    parentModuleGenerator, 
			                    generatorBuffer,
	                            moduleArguments);
		}
		//------------------------------------------------------------------------------------------
		final float cutoff =
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_CUTOFF), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		final float resonance =
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_RESONANCE), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		final float amp =
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_AMP), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
//		float soundFrameRate = this.getSoundFrameRate();
		
		data.lp_cutoff = cutoff;
		data.lp_resonance = resonance;
		data.lp_amp = amp;

		float value = signalSample.getMonoValue();
		
		if (Float.isNaN(value) == false) {
			value = (float)this.tb303LPResoFilter(data, value);
		} else {
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

	double tb303LPResoFilter(final ResonanceFilterGeneratorData data,
	                         double input) 
	{
		data.lp_input = input;
		
		data.lp_low = data.lp_d2 + 
					  data.lp_cutoff * data.lp_amp * data.lp_d1;
		data.lp_high = data.lp_input - 
					   data.lp_low - 
					   data.lp_resonance * data.lp_d1;
		data.lp_band = data.lp_cutoff * data.lp_amp * data.lp_high + 
					   data.lp_d1;
		
		data.lp_d1 = data.lp_band;
		data.lp_d2 = data.lp_low;
		
		return data.lp_low;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorData()
	 */
	@Override
	public Object createGeneratorData()
	{
		return new ResonanceFilterGeneratorData(0.0D, 10.0D, 1.0D, 0.1D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(FILTER_GENERATOR_FOLDER_PATH, ResonanceFilterGenerator.class, "Resonance", "Resonance filter.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_CUTOFF, "cutoff", 1, 1, Float.valueOf(10.0F), "Cutoff value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_RESONANCE, "resonance", 1, 1, Float.valueOf(1.0F), "Resonance value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_AMP, "amp", 1, 1, Float.valueOf(0.1F), "Amp value.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeInfoData;
	}
}
