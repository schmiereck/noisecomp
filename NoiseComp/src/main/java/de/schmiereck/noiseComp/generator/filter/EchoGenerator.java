/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import static de.schmiereck.noiseComp.service.StartupService.FILTER_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Echo Generator.
 * </p>
 * <p>
 * 	Manages a count of echos, the delay (Verzögerung) and there decay (Dämpfung).
 * </p>
 * 
 * @author smk
 * @version <p>21.11.2010:	created, smk</p>
 */
public class EchoGenerator
extends Generator {
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;
	public static final int	INPUT_TYPE_ECHOS	= 2;
	public static final int	INPUT_TYPE_DELAY	= 3;
	public static final int	INPUT_TYPE_DECAY	= 4;
	
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
	 * @param generatorTypeInfoData
	 * 			is the Generator-Type Data.
	 */
	public EchoGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData) {
		super(name, frameRate, generatorTypeInfoData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime,
									 SoundSample soundSample, ModuleGenerator parentModuleGenerator,
                                     GeneratorBufferInterface generatorBuffer,
                                     ModuleArguments moduleArguments) {
		//==========================================================================================
		int echos;
		float delay;
		float decay;
		
		//------------------------------------------------------------------------------------------
		{
			InputData echosInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_ECHOS));
			
			if (echosInputData != null) {
				float echosValue = 
					this.calcInputMonoValueByInputData(framePosition,
					                        frameTime,
					                        echosInputData, 
					                        parentModuleGenerator,
					                        generatorBuffer,
				                            moduleArguments);
					
				if (Float.isNaN(echosValue) == false) {
					echos = (int)Math.round(echosValue);
				} else {
					echos = 0;
				}
			} else {
				echos = 0;
			}
		}
		//------------------------------------------------------------------------------------------
		{
			InputData delayInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_DELAY));
			
			if (delayInputData != null) {
				float delayValue = 
					this.calcInputMonoValueByInputData(framePosition,
					                        frameTime,
					                        delayInputData, 
					                        parentModuleGenerator,
					                        generatorBuffer,
				                            moduleArguments);
					
				if (Float.isNaN(delayValue) == false) {
					delay = delayValue;
				} else {
					delay = 0.0F;
				}
			} else {
				delay = 0.0F;
			}
		}
		//------------------------------------------------------------------------------------------
		{
			InputData decayInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_DECAY));
			
			if (decayInputData != null) {
				float decayValue = 
					this.calcInputMonoValueByInputData(framePosition,
					                        frameTime,
					                        decayInputData, 
					                        parentModuleGenerator,
					                        generatorBuffer,
				                            moduleArguments);
					
				if (Float.isNaN(decayValue) == false) {
					decay = decayValue;
				} else {
					decay = 0.0F;
				}
			} else {
				decay = 0.0F;
			}
		}
		//------------------------------------------------------------------------------------------
		float left = 0.0F;
		float right = 0.0F;
		
		InputData signalInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
		
		float soundFrameRate = this.getSoundFrameRate();
		
		if (signalInputData != null) {
			for (int echoPos = 0; echoPos <= echos; echoPos++) {
				SoundSample signalSample = new SoundSample();
				
				float delayTime = (echoPos * delay);
				long delayFrames = (long)(delayTime * soundFrameRate);
				
				long delayFramePosition = framePosition - delayFrames;
				float delayFrameTime = frameTime - delayTime;
				
				this.calcInputValue(delayFramePosition, 
				                    delayFrameTime,
				                    signalInputData, 
				                    signalSample, 
				                    parentModuleGenerator,
				                    generatorBuffer,
				                    moduleArguments);
				
				float decayValue;
				
				if (echoPos == 0) {
					decayValue = 1.0F;
				} else {
					decayValue = (decay / echoPos);
				}
				
				float leftValue = signalSample.getLeftValue();
				
				if (Float.isNaN(leftValue) == false) {
					left += (leftValue * decayValue);
				}
			
				float rightValue = signalSample.getRightValue();
				
				if (Float.isNaN(rightValue) == false) {
					right += (rightValue * decayValue);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		soundSample.setStereoValues(left, 
		                            right);
		
		//==========================================================================================
	}

	public static GeneratorTypeInfoData createGeneratorTypeData() {
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(FILTER_GENERATOR_FOLDER_PATH, EchoGenerator.class, "echo", "Echo Generator.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, "The input signal between -1 and 1.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_ECHOS, "echos", 1, 1, "Count of echos.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_DELAY, "delay", 1, 1, "Delay (Verzögerung).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_DECAY, "decay", 1, 1, "Decay (Dämpfung).");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}
}
