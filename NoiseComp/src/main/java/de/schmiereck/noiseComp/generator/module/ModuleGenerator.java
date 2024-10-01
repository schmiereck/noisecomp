package de.schmiereck.noiseComp.generator.module;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.signal.OutputGenerator;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * Manages a List of Generators and use them
 * to generate his output signal.
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModuleGenerator
extends Generator
//implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public ModuleGenerator(String name, Float frameRate, 
						  GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
		
		// Fügt sich als Listener zu dem seinem Typ hinzu, um über Änderungen informiert zu werden.
		//((ModuleGeneratorTypeData)generatorTypeData).getGeneratorChangeObserver().registerGeneratorChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize()
		throws Throwable
	{
		// Nimmt sich als Listener von seinem Typ weg, um nicht mehr über Änderungen informiert zu werden.
		//((ModuleGeneratorTypeData)this.getGeneratorTypeData()).getGeneratorChangeObserver().removeGeneratorChangeListener(this);

		super.finalize();
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample sample,
	                                 ModuleGenerator parentModuleGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments)
	{
		if (this.checkIsInTime(frameTime) == true)
		{	
			ModuleGeneratorTypeData moduleGeneratorTypeData = (ModuleGeneratorTypeData)this.getGeneratorTypeData();
			
			OutputGenerator outputGenerator = moduleGeneratorTypeData.getOutputGenerator();
			
			//ModuleGenerator moduleenerator = (ModuleGenerator)generatorBuffer;
			
			Timeline moduleimeline = generatorBuffer.getSubGeneratorTimeline(outputGenerator);
			
			ModuleArguments thisModuleArguments = new ModuleArguments(generatorBuffer,
			                                                       moduleArguments);
			
			long outputStartPos = (long)(this.getStartTimePos() * this.getSoundFrameRate());

			//outputGenerator.calculateSoundSample(framePosition, frameTime, sample);
			SoundSample outputSample = 
				outputGenerator.generateFrameSample(framePosition - outputStartPos, 
				                                    this,
				                                    moduleimeline,
				                                    thisModuleArguments);
			
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
		ModuleGeneratorTypeData generatorTypeData = new ModuleGeneratorTypeData(folderPath,
		                                                                      ModuleGenerator.class, 
																			  "Module", 
																			  "Manages a Group of other generators.");
		
		return generatorTypeData;
	}

	/**
	 * @see #createGeneratorTypeData() with cast to {@link ModuleGeneratorTypeData}
	 * 
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 */
	public static ModuleGeneratorTypeData createModuleGeneratorTypeData(String folderPath)
	{
		return (ModuleGeneratorTypeData)ModuleGenerator.createGeneratorTypeData(folderPath);
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
