package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

/**
 * TODO docu
 *
 * @author smk
 * @version 22.01.2004
 */
public class MixerGenerator
extends Generator
{
	public static final int	INPUT_TYPE_VOLUME	= 1;
	public static final int	INPUT_TYPE_SIGNAL	= 2;
	
	/**
	 * Liste aus {@link Generator}-Objekten.
	 */
	//private Vector volumeInputs = null;
	
	/**
	 * Liste aus {@link Generator}-Objekten.
	 */
	//private Vector signalInputs = null;
	
	/**
	 * Constructor.
	 * 
	 * @param defaultValue
	 * @param holdLastValue
	 * @param frameRate
	 */
	public MixerGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	public void addVolumeInput(Generator volumeInput)
	{/*
		if (this.volumeInputs == null)
		{	
			this.volumeInputs = new Vector();
		}
		this.volumeInputs.add(volumeInput);
		*/
		this.addInputGenerator(volumeInput, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_VOLUME));
	}

	public void addSignalInput(Generator signalInput)
	{/*
		if (this.signalInputs == null)
		{	
			this.signalInputs = new Vector();
		}
		this.signalInputs.add(signalInput);
		*/
		this.addInputGenerator(signalInput, this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		float volume = 0.0F;

		float signalLeft = 0.0F;
		float signalRight = 0.0F;

		SoundSample signal = new SoundSample();
		
		Iterator inputsIterator = this.getInputsIterator();
		
		if (inputsIterator != null)
		{
			while (inputsIterator.hasNext())
			{
				InputData inputData = (InputData)inputsIterator.next();
				
				switch (inputData.getInputTypeData().getInputType())
				{
					case INPUT_TYPE_VOLUME:
						{
							float value = this.calcInputMonoValue(framePosition, inputData);
							/*
							GeneratorInterface volumeInputGenerator = inputData.getInputGenerator();
							
							// Found Input-Generator ?
							if (volumeInputGenerator != null)
							{	
								SoundSample volumeInputSample = volumeInputGenerator.generateFrameSample(framePosition);
								
								if (volumeInputSample != null)
								{
									value = volumeInputSample.getMonoValue();
								}
								else
								{
									// Found no input signal:
									
									value = 0.0F;
								}
							}
							else
							{	
								// Found no Input-Generator:
								
								Float inputValue = inputData.getInputValue();
								
								// Found constant input value ?
								if (inputValue != null)
								{
									value = inputValue.floatValue();
								}
								else
								{	
									// Found no input value:
									// Use Default Value of Input type:
									
									value = this.getInputDefaultValueByInputType(inputData.getInputType());
								}
							}
							*/
							volume += value;
							break;
						}
					case INPUT_TYPE_SIGNAL:
						{
							this.calcInputValue(framePosition, inputData, signal);
							/*
							float valueRight;
							float valueLeft;

							GeneratorInterface signalInputGenerator = inputData.getInputGenerator();
							
							// Found Input-Generator ?
							if (signalInputGenerator != null)
							{	
								SoundSample signalInputSample = signalInputGenerator.generateFrameSample(framePosition);
							
								if (signalInputSample != null)
								{
									valueLeft = signalInputSample.getLeftValue();
									valueRight = signalInputSample.getRightValue();
								}
								else
								{
									// Found no input signal:
									
									valueLeft = 0.0F;
									valueRight = 0.0F;
								}
							}
							else
							{	
								// Found no Input-Generator:
								
								Float inputValue = inputData.getInputValue();
								
								// Found constant input value ?
								if (inputValue != null)
								{
									valueLeft = inputValue.floatValue();
									valueRight = inputValue.floatValue();
								}
								else
								{	
									// Found no input value:
									// Use Default Value of Input type:
									
									valueLeft = this.getInputDefaultValueByInputType(inputData.getInputType());
									valueRight = valueLeft;
								}
							}
							*/
							signalLeft += signal.getLeftValue();
							signalRight += signal.getRightValue();
							break;
						}
					default:
						{
							throw new RuntimeException("unknown input type " + inputData.getInputTypeData());
						}
				}
			}
		}
		
		soundSample.setStereoValues(signalLeft * volume, signalRight * volume);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(MixerGenerator.class, "Mixer", "Mixes multiple signal input lines and scale them with a volume.");
		
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
