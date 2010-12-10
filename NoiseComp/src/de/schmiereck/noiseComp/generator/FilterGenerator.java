/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Filter Generator.
 * </p>
 * 
 * @author smk
 * @version <p>10.12.2010:	created, smk</p>
 */
public class FilterGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;
	public static final int	INPUT_TYPE_FILTER	= 2;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the generator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeData
	 * 			is the Generator-Type Data.
	 */
	public FilterGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
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
		SoundSample signalSample = new SoundSample();
		SoundSample filterSample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		InputData signalInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		if (signalInputData != null)
		{
			this.calcInputValue(framePosition, 
                                frameTime,
			                    signalInputData, 
			                    signalSample, 
			                    parentModulGenerator,
			                    generatorBuffer,
			                    modulArguments);
		}
		
		//------------------------------------------------------------------------------------------
		InputData filterInputData = this.searchInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_FILTER));
		
		if (filterInputData != null)
		{
			this.calcInputValue(framePosition, 
                                frameTime,
                                filterInputData, 
			                    filterSample, 
			                    parentModulGenerator,
			                    generatorBuffer,
			                    modulArguments);
		}
		
		//------------------------------------------------------------------------------------------
		float left;
		float right;

//		float a = 0.999F;//0.99F;
//		float b = 0.001F;//0.01F;
		
		float al = 1.0F - filterSample.getLeftValue();
		float bl = filterSample.getLeftValue();
		
		float ar = 1.0F - filterSample.getRightValue();
		float br = filterSample.getRightValue();
		
		{
			float soundFrameRate = this.getSoundFrameRate();
			
//			long delayFrames = 1L;
//			float delayTime = frameTime - (delayFrames / soundFrameRate);
			float delayTime = (1.0F / soundFrameRate);
			long delayFrames = (long)(delayTime * soundFrameRate);
			
//			long frame1Position = framePosition - 19L;
//			float frame1Time = frame1Position / this.getSoundFrameRate();
			
//			float soundFramePosition = framePosition - this.getStartTimePos();
//
//			if ((soundFramePosition - delayTime) >= 0.0F)
//			{
			long delayFramePosition = framePosition - delayFrames;
			float delayFrameTime = frameTime - delayTime;
			
			SoundSample sample = 
				generatorBuffer.calcFrameSample(delayFramePosition, 
				                                delayFrameTime, 
				                                parentModulGenerator, 
				                                modulArguments);
			
//				this.calculateSoundSample(framePosition - delayFrames, 
//				                          frameTime - delayTime, 
//				                          sample, 
//				                          parentModulGenerator, 
//				                          generatorBuffer, 
//				                          modulArguments);
			if (sample != null)
			{
				// y[n] = a * y[n-1] + b * x[n]
				
				left  = al * sample.getLeftValue()  + bl * signalSample.getLeftValue();
				right = ar * sample.getRightValue() + br * signalSample.getRightValue();
			}
			else
			{
				left  = bl * signalSample.getLeftValue();
				right = br * signalSample.getRightValue();
			}
		}
		
		soundSample.setStereoValues(left, 
		                            right);
		
		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(FilterGenerator.class, "filter", "Filter Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "Is a signal between -1 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_FILTER, "filter", 1, 1, "Is a filter between 0 and 1.");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}


}
