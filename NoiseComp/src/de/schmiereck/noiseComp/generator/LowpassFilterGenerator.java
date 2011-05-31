/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

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
	// Fields:

	private double xv[] = new double[3];
	private double yv[] = new double[3];
    
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param frameRate	
	 * 			are the Frames per Second.
	 */
	public LowpassFilterGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		float soundFrameRate = this.getSoundFrameRate();
		
		double ax[] = new double[3];
		double by[] = new double[3];

		this.calcLPCoefficientsButterworth2Pole((int)soundFrameRate, cutoff, ax, by);

		float value = signalSample.getMonoValue();
		
		if (Float.isNaN(value) == false)
		{
			value = (float)this.filter(ax, by, value);
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

	void calcLPCoefficientsButterworth2Pole(final int samplerate, 
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

	void filter(final double samples[], int count)
	{
		//==========================================================================================
		double ax[] = new double[3];
		double by[] = new double[3];

		this.calcLPCoefficientsButterworth2Pole(44100, 5000, ax, by);

		for (int i = 0; i < count; i++)
		{
			double sample = this.filter(ax, by, samples[i]);
			
			samples[i] = sample;
		}
		//==========================================================================================
	}
	
	private double filter(double ax[], double by[],
	                      final double sample)
	{
		//==========================================================================================
		xv[2] = xv[1]; 
		xv[1] = xv[0];
		xv[0] = sample;
		
		yv[2] = yv[1]; 
		yv[1] = yv[0];
		yv[0] = (ax[0] * xv[0] + 
				 ax[1] * xv[1] + 
				 ax[2] * xv[2] - 
    		     by[1] * yv[0] - 
    		     by[2] * yv[1]);
		
		//==========================================================================================
		return yv[0];
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData("/", LowpassFilterGenerator.class, "Lowpass", "Lowpass filter (Cutoff).");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_CUTOFF, "cutoff", 1, 1, Float.valueOf(5000.0F), "Cutoff value.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		//==========================================================================================
		return generatorTypeData;
	}
}
