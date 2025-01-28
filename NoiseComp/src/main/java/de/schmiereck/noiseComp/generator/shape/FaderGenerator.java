package de.schmiereck.noiseComp.generator.shape;


import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import static de.schmiereck.noiseComp.service.StartupService.SHAPE_GENERATOR_FOLDER_PATH;

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
	public FaderGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
	{
		super(name, frameRate, generatorTypeInfoData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample soundSample,
	                                 ModuleGenerator parentModuleGenerator,
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments)
	{
		//==========================================================================================
		float startFadeValue = 
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_START_VALUE), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);
		
		float endFadeValue = 
			this.calcSingleInputMonoValueByInputType(framePosition,
			                        frameTime,
			                        this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_END_VALUE), 
			                        parentModuleGenerator,
			                        generatorBuffer,
			                        moduleArguments);
		
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
	
		/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		//==========================================================================================
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SHAPE_GENERATOR_FOLDER_PATH, FaderGenerator.class, "Fader", "Fades linear from a start to a end value.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_START_VALUE, "startFadeValue", 1, 1, "Start value between -1 and 1 of the fading over generator length (other value ranges can used).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_END_VALUE, "endFadeValue", 1, 1, "End value between -1 and 1 of the fading over generator length (other value ranges can used).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		//==========================================================================================
		return generatorTypeInfoData;
	}

//	/* (non-Javadoc)
//	 * @see de.schmiereck.noiseComp.generator.Generator#getGeneratorSampleDrawScale()
//	 */
//	public float getGeneratorSampleDrawScale(ModuleGenerator parentModuleGenerator, 
//	                                         GeneratorBufferInterface generatorBuffer,
//	                                         ModuleArguments modulerguments)
//	{
//		//==========================================================================================
//		// Works only, if the values are constant inputs:
//		
//        float frameTime = 0.0F;
//        
//		float startFadeValue = this.calcStartFadeValue(0, 
//		                                               frameTime,
//		                                               parentModuleGenerator, 
//		                                               generatorBuffer,
//		                                               modulerguments);
//		
//		float endFadeValue = this.calcEndFadeValue(0, 
//	                                               frameTime,
//		                                           parentModuleGenerator, 
//		                                           generatorBuffer,
//		                                           modulerguments);
//		
//		float max = Math.max(Math.abs(startFadeValue), Math.abs(endFadeValue));
//		
//		float ret;
//		
//		if (max > 1.0F)
//		{
//			ret = 1.0F / max;
//		}
//		else
//		{	
//			ret = 1.0F;
//		}
//		
//		//==========================================================================================
//		return ret;
//	}
}
