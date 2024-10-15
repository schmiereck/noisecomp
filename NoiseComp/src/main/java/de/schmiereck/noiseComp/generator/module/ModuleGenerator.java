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
extends Generator {
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public ModuleGenerator(String name, Float frameRate, 
						  GeneratorTypeInfoData generatorTypeInfoData) {
		super(name, frameRate, generatorTypeInfoData);
		
		// Fügt sich als Listener zu dem seinem Typ hinzu, um über Änderungen informiert zu werden.
		//((ModuleGeneratorTypeData)generatorTypeData).getGeneratorChangeObserver().registerGeneratorChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	public void calculateSoundSample(long framePosition, 
	                                 float frameTime, 
	                                 SoundSample sample,
	                                 ModuleGenerator parentModuleGenerator, 
	                                 GeneratorBufferInterface generatorBuffer,
	                                 ModuleArguments moduleArguments) {
		if (this.checkIsInTime(frameTime)) {
			ModuleGeneratorTypeInfoData moduleGeneratorTypeData = (ModuleGeneratorTypeInfoData)this.getGeneratorTypeData();
			
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
		} else {
			sample.setNaN();
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeInfoData createGeneratorTypeData(String folderPath) {
		ModuleGeneratorTypeInfoData generatorTypeData = new ModuleGeneratorTypeInfoData(folderPath,
		                                                                      ModuleGenerator.class, 
																			  "Module", 
																			  "Manages a Group of other generators.");
		
		return generatorTypeData;
	}

	/**
	 * @see #createGeneratorTypeData(String) with cast to {@link ModuleGeneratorTypeInfoData}
	 * 
	 * @param folderPath
	 * 			is the Folder-Path in Format <code>"/folder1/folder2/"</code>.
	 */
	public static ModuleGeneratorTypeInfoData createModuleGeneratorTypeData(String folderPath) {
		return (ModuleGeneratorTypeInfoData)ModuleGenerator.createGeneratorTypeData(folderPath);
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
