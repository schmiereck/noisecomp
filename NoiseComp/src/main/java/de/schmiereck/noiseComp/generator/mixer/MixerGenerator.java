package de.schmiereck.noiseComp.generator.mixer;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;

import java.util.Iterator;

import static de.schmiereck.noiseComp.service.StartupService.MIXER_GENERATOR_FOLDER_PATH;

/**
 * Mixer Generator.
 *
 * @author smk
 * @version 22.01.2004
 */
public class MixerGenerator
extends Generator
{
	//**********************************************************************************************
	// Constants:

	public static final int	INPUT_TYPE_VOLUME	= 1;
	public static final int	INPUT_TYPE_SIGNAL	= 2;
	
	//**********************************************************************************************
	// Fields:

	/**
	 * Liste aus {@link Generator}-Objekten.
	 */
	//private Vector volumeInputs = null;
	
	/**
	 * Liste aus {@link Generator}-Objekten.
	 */
	//private Vector signalInputs = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the genrator name.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorTypeData
	 * 			is the Generator-Type Data.
	 */
	public MixerGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	public void addVolumeInput(final SoundSourceData soundSourceData, Generator volumeInput)
	{/*
		if (this.volumeInputs == null)
		{	
			this.volumeInputs = new Vector();
		}
		this.volumeInputs.add(volumeInput);
		*/
		this.addGeneratorInput(soundSourceData, volumeInput, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_VOLUME),
							   null, null, null);
	}

	public void addSignalInput(final SoundSourceData soundSourceData, Generator signalInput)
	{/*
		if (this.signalInputs == null)
		{	
			this.signalInputs = new Vector();
		}
		this.signalInputs.add(signalInput);
		*/
		this.addGeneratorInput(soundSourceData, signalInput, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL),
							   null, null, null);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample, ModuleGenerator parentModuleGenerator,
                                     GeneratorBufferInterface generatorBuffer,
                                     ModuleArguments moduleArguments)
	{
		//==========================================================================================
		SoundSample signalSample = new SoundSample();
		
		//------------------------------------------------------------------------------------------
		//int valumeCnt = 0;
		float volume = 1.0F;
//		float volumeCnt = 1.0F;
		
		float signalLeft = 0.0F;
		float signalRight = 0.0F;
	
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
							case INPUT_TYPE_VOLUME:
							{
//								try
//								{
									float value = this.calcInputMonoValue(framePosition, 
									                                      frameTime,
									                                      inputData, 
									                                      parentModuleGenerator,
									                                      generatorBuffer,
								        	                              moduleArguments);
									

									// Input da?
									if (Float.isNaN(value) == false)
									{
										volume *= value;
//										volumeCnt++;
									}
//								}
//								catch (NoInputSignalException ex)
//								{
//								}
								break;
							}
							case INPUT_TYPE_SIGNAL:
							{
								this.calcInputValue(framePosition, 
								                    frameTime,
								                    inputData, 
								                    signalSample, 
								                    parentModuleGenerator,
								                    generatorBuffer,
								                    moduleArguments);

								float leftValue = signalSample.getLeftValue();
								
								if (Float.isNaN(leftValue) == false)
								{
									signalLeft += leftValue;
								}
								
								float rightValue = signalSample.getRightValue();
								
								if (Float.isNaN(rightValue) == false)
								{
									signalRight += rightValue;
								}
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
		//if (valumeCnt > 1)
		//{
		//	volume /= valumeCnt;
		//}
		
//		soundSample.setStereoValues(signalLeft * (volume / volumeCnt), 
//		                            signalRight * (volume / volumeCnt));
		soundSample.setStereoValues(signalLeft * (volume), 
		                            signalRight * (volume));
		
		//==========================================================================================
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(MIXER_GENERATOR_FOLDER_PATH, MixerGenerator.class, "Mixer", "Mixes multiple signal input lines and scale them with a volume.");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VOLUME, "volume", -1, -1, "The volume of the output singal between 0 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1, "The input signal between -1 and 1 (average is calculated if more then one volume is connected).");
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
	
}
