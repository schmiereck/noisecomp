package de.schmiereck.noiseComp.generator;


/**
 * Fader Generator.
 *
 * @author smk
 * @version 22.01.2004
 */
public class FaderGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_START_VALUE	= 1;
	public static final int	INPUT_TYPE_END_VALUE	= 2;
	
	//private float startFadeValue = 0.0F;
	//private float endFadeValue = 0.0F;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public FaderGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample soundSample, 
	                                 ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
	{
		//==========================================================================================
		float startFadeValue = this.calcStartFadeValue(framePosition, 
		                                               frameTime,
		                                               parentModulGenerator,
		                                               generatorBuffer,
		                                               modulArguments);
		
		float endFadeValue = this.calcEndFadeValue(framePosition, 
	                                               frameTime,
		                                           parentModulGenerator, 
		                                           generatorBuffer,
		                                           modulArguments);
		
		//------------------------------------------------------------------------------------------
		// Relativer Zeitpunkt im Generator.
		float timePos = frameTime - (this.getStartTimePos());
		
		// Länge des Generators.
		float timeLen = this.getEndTimePos() - this.getStartTimePos();
		
		// Different zwischen End- und Startwert des Faders.
		float valueDif = endFadeValue - startFadeValue;
		
		//------------------------------------------------------------------------------------------
		// Wert zu dem angegebenen Zeitpunkt.
		float value = ((valueDif * timePos) / timeLen) + startFadeValue; 
		
		soundSample.setStereoValues(value, value);
		//==========================================================================================
	}
	
	private float calcEndFadeValue(long framePosition, 
                                   float frameTime,
	                               ModulGenerator parentModulGenerator,
	                               GeneratorBufferInterface generatorBuffer,
	                               ModulArguments modulArguments)
	{
		//==========================================================================================
		float endFadeValue;

		endFadeValue = this.calcInputMonoValue(framePosition, 
			                                   frameTime,
		                                       this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_END_VALUE), 
		                                       parentModulGenerator,
		                                       generatorBuffer,
		                                       modulArguments);

		//==========================================================================================
		return endFadeValue;
	}

	private float calcStartFadeValue(long framePosition, 
                                     float frameTime,
	                                 ModulGenerator parentModulGenerator,
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
	{
		//==========================================================================================
		float startFadeValue;

		startFadeValue = this.calcInputMonoValue(framePosition, 
		                                         frameTime,
		                                         this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_START_VALUE), 
		                                         parentModulGenerator,
		                                         generatorBuffer,
		                                         modulArguments);

		//==========================================================================================
		return startFadeValue;
	}

		/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(FaderGenerator.class, "Fader", "Fades linear from a start to a end value.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_START_VALUE, "startFadeValue", 1, 1, "Start value between -1 and 1 of the fading over generator length (other value ranges can used).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_END_VALUE, "endFadeValue", 1, 1, "End value between -1 and 1 of the fading over generator length (other value ranges can used).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		//==========================================================================================
		return generatorTypeData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#getGeneratorSampleDrawScale()
	 */
	public float getGeneratorSampleDrawScale(ModulGenerator parentModulGenerator, 
	                                         GeneratorBufferInterface generatorBuffer,
	                                         ModulArguments modulArguments)
	{
		//==========================================================================================
		// Works only, if the values are constant inputs:
		
        float frameTime = 0.0F;
        
		float startFadeValue = this.calcStartFadeValue(0, 
		                                               frameTime,
		                                               parentModulGenerator, 
		                                               generatorBuffer,
		                                               modulArguments);
		
		float endFadeValue = this.calcEndFadeValue(0, 
	                                               frameTime,
		                                           parentModulGenerator, 
		                                           generatorBuffer,
		                                           modulArguments);
		
		float max = Math.max(Math.abs(startFadeValue), Math.abs(endFadeValue));
		
		float ret;
		
		if (max > 1.0F)
		{
			ret = 1.0F / max;
		}
		else
		{	
			ret = 1.0F;
		}
		
		//==========================================================================================
		return ret;
	}
}
