package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * Manages a List of Generators and use them
 * to generate his output signal.
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModulGenerator
extends Generator 
//implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public ModulGenerator(String name, Float frameRate, 
						  GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
		
		// Fügt sich als Listener zu dem seinem Typ hinzu, um über Änderungen informiert zu werden.
		//((ModulGeneratorTypeData)generatorTypeData).getGeneratorChangeObserver().registerGeneratorChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize()
		throws Throwable
	{
		// Nimmt sich als Listener von seinem Typ weg, um nicht mehr über Änderungen informiert zu werden.
		//((ModulGeneratorTypeData)this.getGeneratorTypeData()).getGeneratorChangeObserver().removeGeneratorChangeListener(this);

		super.finalize();
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample sample, 
	                                 ModulGenerator parentModulGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModulArguments modulArguments)
	{
		if (this.checkIsInTime(frameTime) == true)
		{	
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)this.getGeneratorTypeData();
			
			OutputGenerator outputGenerator = modulGeneratorTypeData.getOutputGenerator();
			
			//ModulGenerator modulGenerator = (ModulGenerator)generatorBuffer;
			
			Timeline modulTimeline = generatorBuffer.getSubGeneratorTimeline(outputGenerator);
			
			ModulArguments thisModulArguments = new ModulArguments(generatorBuffer,
			                                                       modulArguments);
			
			long outputStartPos = (long)(this.getStartTimePos() * this.getSoundFrameRate());

			//outputGenerator.calculateSoundSample(framePosition, frameTime, sample);
			SoundSample outputSample = 
				outputGenerator.generateFrameSample(framePosition - outputStartPos, 
				                                    this,
				                                    modulTimeline,
				                                    thisModulArguments);
			
			sample.setValues(outputSample);
		}
		else
		{
			sample.setNaN();
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData(String folderPath)
	{
		ModulGeneratorTypeData generatorTypeData = new ModulGeneratorTypeData(folderPath,
		                                                                      ModulGenerator.class, 
																			  "Modul", 
																			  "Manages a Group of other generators.");
		
		return generatorTypeData;
	}

	/**
	 * @see #createGeneratorTypeData() with cast to {@link ModulGeneratorTypeData}
	 * 
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 */
	public static ModulGeneratorTypeData createModulGeneratorTypeData(String folderPath)
	{
		return (ModulGeneratorTypeData)ModulGenerator.createGeneratorTypeData(folderPath);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	//public void notifyGeneratorChanged(Generator generator, float startTimePos, float endTimePos)
	//{
	//	this.getGeneratorChangeObserver().changedEvent(this, 
	//												   this.getStartTimePos() + startTimePos, 
	//												   this.getStartTimePos() + endTimePos);
	//}
}
