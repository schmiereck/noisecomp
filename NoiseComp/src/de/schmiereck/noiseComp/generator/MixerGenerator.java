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
		this.addInputGenerator(volumeInput, INPUT_TYPE_VOLUME);
	}

	public void addSignalInput(Generator signalInput)
	{/*
		if (this.signalInputs == null)
		{	
			this.signalInputs = new Vector();
		}
		this.signalInputs.add(signalInput);
		*/
		this.addInputGenerator(signalInput, INPUT_TYPE_SIGNAL);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		float volume = 0.0F;

		float signalLeft = 0.0F;
		float signalRight = 0.0F;
		
		Iterator inputsIterator = this.getInputsIterator();
		
		if (inputsIterator != null)
		{
			while (inputsIterator.hasNext())
			{
				InputData inputData = (InputData)inputsIterator.next();
				
				switch (inputData.getInputType())
				{
					case INPUT_TYPE_VOLUME:
						{
							Float inputValue = inputData.getInputValue();
							
							// Constant input value ?
							if (inputValue != null)
							{
								volume += inputValue.floatValue();
							}
							else
							{	
								GeneratorInterface volumeInput = inputData.getInputGenerator();
								
								SoundSample volumeInputSample = volumeInput.generateFrameSample(framePosition);
								
								if (volumeInputSample != null)
								{
									volume += volumeInputSample.getMonoValue();
								}
							}
							break;
						}
					case INPUT_TYPE_SIGNAL:
						{
							Float inputValue = inputData.getInputValue();
							
							// Constant input value ?
							if (inputValue != null)
							{
								signalLeft += inputValue.floatValue();
								signalRight += inputValue.floatValue();
							}
							else
							{	
								GeneratorInterface signalInput = inputData.getInputGenerator();
								
								SoundSample signalInputSample = signalInput.generateFrameSample(framePosition);
								
								if (signalInputSample != null)
								{
									signalLeft += signalInputSample.getLeftValue();
									signalRight += signalInputSample.getRightValue();
								}
							}
							break;
						}
					default:
						{
							throw new RuntimeException("unknown input type " + inputData.getInputType());
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
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_VOLUME, "volume", -1, -1);
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", -1, -1);
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		
		return generatorTypeData;
	}
	
}
