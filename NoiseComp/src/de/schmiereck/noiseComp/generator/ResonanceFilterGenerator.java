/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

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
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL		= 1;
	public static final int	INPUT_CUTOFF			= 2;
	public static final int	INPUT_RESONANCE			= 3;
	public static final int	INPUT_AMP				= 4;

    //**********************************************************************************************
	// Fields:

	/**
	 * Last input value.
	 */
	private double lp_input = 0.0D;
	
	private double lp_cutoff	= 10.0D;
	private double lp_resonance	= 1.0D;
	private double lp_amp		= 0.1D;

	private double lp_low = 0.0D;
	private double lp_band = 0.0D;
	private double lp_high = 0.0D;
	private double lp_d1 = 0.0D;
	private double lp_d2 = 0.0D;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param frameRate	
	 * 			are the Frames per Second.
	 */
	public ResonanceFilterGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample signalSample, ModuleGenerator parentModuleGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments)
	{
		//==========================================================================================
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
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
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_CUTOFF), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		float resonance = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_RESONANCE), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
		float amp = 
			this.calcInputMonoValue(framePosition, 
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_AMP), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);

		//------------------------------------------------------------------------------------------
//		float soundFrameRate = this.getSoundFrameRate();
		
		this.lp_cutoff = cutoff;
		this.lp_resonance = resonance;
		this.lp_amp = amp;
		
		float value = signalSample.getMonoValue();
		
		if (Float.isNaN(value) == false)
		{
			value = (float)this.tb303LPResoFilter(value);
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

	double tb303LPResoFilter(double input) 
	{
		this.lp_input = input;
		
		this.lp_low = this.lp_d2 + 
					  this.lp_cutoff * this.lp_amp * this.lp_d1;
		this.lp_high = this.lp_input - 
					   this.lp_low - 
					   this.lp_resonance * this.lp_d1;
		this.lp_band = this.lp_cutoff * this.lp_amp * this.lp_high + 
					   this.lp_d1;
		
		this.lp_d1 = this.lp_band;
		this.lp_d2 = this.lp_low;
		
		return this.lp_low;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", ResonanceFilterGenerator.class, "Resonance", "Resonance filter.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_CUTOFF, "cutoff", 1, 1, Float.valueOf(10.0F), "Cutoff value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_RESONANCE, "resonance", 1, 1, Float.valueOf(1.0F), "Resonance value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_AMP, "amp", 1, 1, Float.valueOf(0.1F), "Amp value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
