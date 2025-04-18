/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator.shape;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import java.util.Iterator;

import static de.schmiereck.noiseComp.service.StartupService.SHAPE_GENERATOR_FOLDER_PATH;

/**
 * <p>
 * 	Integrator Generator.
 * </p>
 * 
 * @author smk
 * @version <p>20.10.2010:	created, smk</p>
 */
public class IntegratorGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_SIGNAL	= 1;
	
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the genrator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeInfoData
	 * 			is the Generator-Type Data.
	 */
	public IntegratorGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData)
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
//		SoundSample signal1Sample = new SoundSample();
		SoundSample signal2Sample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		long frame1Position = framePosition - 1L;
		float frame1Time = frame1Position / this.getSoundFrameRate();
		
		long frame2Position = framePosition;
		float frame2Time = frame2Position / this.getSoundFrameRate();
		
		//------------------------------------------------------------------------------------------
//		float signal1Left = 0.0F;
//		float signal1Right = 0.0F;
	
		float signal2Left = 0.0F;
		float signal2Right = 0.0F;
	
		//------------------------------------------------------------------------------------------
		Object inputsSyncObject = this.getInputsSyncObject();
		
		if (inputsSyncObject != null)
		{	
			synchronized (inputsSyncObject)
			{
				Iterator<InputData> inputsIterator = this.getInputsIterator();
			
				if (inputsIterator != null)
				{
					while (inputsIterator.hasNext())
					{
						InputData inputData = inputsIterator.next();
						
						switch (inputData.getInputTypeData().getInputType())
						{
							case INPUT_TYPE_SIGNAL:
							{
								// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
								{
									this.calcInputValue(frame2Position, 
									                    frame2Time,
									                    inputData, 
									                    signal2Sample, 
									                    parentModuleGenerator,
									                    generatorBuffer,
									                    moduleArguments);
	
									float leftValue = signal2Sample.getLeftValue();
									
									if (Float.isNaN(leftValue) == false)
									{
										signal2Left += (leftValue );
									}
									
									float rightValue = signal2Sample.getRightValue();
									
									if (Float.isNaN(rightValue) == false)
									{
										signal2Right += (rightValue);
									}
								}
//								// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//								this.calcInputValue(frame1Position, 
//								                    frame1Time,
//								                    inputData, 
//								                    signal1Sample, 
//								                    parentModuleGenerator,
//								                    generatorBuffer);
//								{
//									float leftValue = signal1Sample.getLeftValue();
//									
//									if (Float.isNaN(leftValue) == false)
//									{
//										signal1Left += (leftValue);
//									}
//									
//									float rightValue = signal1Sample.getRightValue();
//									
//									if (Float.isNaN(rightValue) == false)
//									{
//										signal1Right += (rightValue);
//									}
//								}
								// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
								break;
							}
							default:
							{
								throw new RuntimeException("Unknown input type \"" + inputData.getInputTypeData() + "\".");
							}
						}
					}
				}
			}
		}
		
		//------------------------------------------------------------------------------------------
		float dTime = 1.0F / this.getSoundFrameRate(); //44100.0F * 1.0F;//4.0F;//
		
		float signalLeft = 0.0F;
		float signalRight = 0.0F;
		
		SoundSample intgrSoundSample;
		
//		if (generatorBuffer != null)
		{
			//Von einer Funktion holen die den Buffer abfragt.
			intgrSoundSample = 
//				this.generateFrameSample(frame1Position,
//				                         parentModuleGenerator, 
//				                         generatorBuffer);
				generatorBuffer.calcFrameSample(frame1Position, 
				                                frame1Time, 
 												parentModuleGenerator,
 					                            moduleArguments);
		}
//		else
//		{
//			// TODO #82: Every generator needs a Timeline to calculates the Integrator effectively (and without StackOverflowError), also generator in SubModules.
//			intgrSoundSample = new SoundSample();
//			
//			this.calculateSoundSample(frame1Position,
//			                          frame1Time,
//			                          intgrSoundSample,
//			                          parentModuleGenerator, 
//			                          null);
//		}
		
		if (intgrSoundSample != null)
		{
			float intgrLeftValue = intgrSoundSample.getLeftValue();
			
			if (Float.isNaN(intgrLeftValue) == false)
			{
				signalLeft = intgrLeftValue + (signal2Left * dTime);//(intgrLeftValue + (signal2Left - signal1Left));//(leftValue - intgrLeftValue);//intgrLeftValue ;// / 4.410F * 1.0F;
			}
			
			float intgrRightValue = intgrSoundSample.getRightValue();
			
			if (Float.isNaN(intgrRightValue) == false)
			{
				signalRight = intgrRightValue + (signal2Right * dTime);//(intgrRightValue + (signal2Right - signal1Right));//(rightValue - intgrRightValue);//intgrRightValue;// / 4.410F * 1.0F;
			}
		}
		//------------------------------------------------------------------------------------------
		soundSample.setStereoValues(signalLeft, signalRight);
		
		//==========================================================================================
	}

	public static GeneratorTypeInfoData createGeneratorTypeData()
	{
		GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(SHAPE_GENERATOR_FOLDER_PATH, IntegratorGenerator.class, "Integrator", "Integrates multiple signal input lines.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1, "The input signal.");
			generatorTypeInfoData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeInfoData;
	}


}
