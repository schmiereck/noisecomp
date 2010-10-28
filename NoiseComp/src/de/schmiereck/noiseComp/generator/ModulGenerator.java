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
	/**
	 * Constructor.
	 * 
	 */
	public ModulGenerator(String name, Float frameRate, 
						  GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
		
		// F�gt sich als Listener zu dem seinem Typ hinzu, um über Änderungen informiert zu werden.
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
	                                 GeneratorBufferInterface generatorBuffer)
	{
		if (this.checkIsInTime(frameTime) == true)
		{	
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)this.getGeneratorTypeData();
			
			OutputGenerator outputGenerator = modulGeneratorTypeData.getOutputGenerator();
			
			//ModulGenerator modulGenerator = (ModulGenerator)generatorBuffer;
			
			Timeline modulTimeline = generatorBuffer.getSubGeneratorTimeline(outputGenerator);
			
			long outputStartPos = (long)(this.getStartTimePos() * this.getSoundFrameRate());
			
			//outputGenerator.calculateSoundSample(framePosition, frameTime, sample);
			SoundSample outputSample = outputGenerator.generateFrameSample(framePosition - outputStartPos, 
			                                                               this,
			                                                               modulTimeline);
			
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
	public static GeneratorTypeData createGeneratorTypeData()
	{
		ModulGeneratorTypeData generatorTypeData = new ModulGeneratorTypeData(ModulGenerator.class, 
																			  "Modul", 
																			  "Manages a Group of other generators.");
		
		return generatorTypeData;
	}

	/**
	 * @see #createGeneratorTypeData() with cast to {@link ModulGeneratorTypeData}
	 */
	public static ModulGeneratorTypeData createModulGeneratorTypeData()
	{
		return (ModulGeneratorTypeData)ModulGenerator.createGeneratorTypeData();
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
