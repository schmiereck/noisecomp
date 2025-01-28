/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Generator-Buffer Interface.
 * </p>
 * 
 * @author smk
 * @version <p>09.10.2010:	created, smk</p>
 */
public interface GeneratorBufferInterface
{

//	/**
//	 * @param framePosition
//	 * 			is the frame position.
//	 * @param inputData
//	 * 			is the input data.
//	 * @param parentModuleGenerator
//	 * 			is the parent module generator.
//	 * @return
//	 * 			the bufInputSoundSample.<br/>
//	 * 			<code>null</code> if there is no input generator available (constant value, etc.).
//	 */
//	SoundSample calcFrameSample(long framePosition, InputData inputData, ModuleGenerator parentModuleGenerator);
	
	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param parentModuleGenerator
	 * 			is the parent module generator.
	 * @return
	 * 			the bufSoundSample.<br/>
	 * 			<code>null</code> if time is out of generator time.
	 */
	SoundSample calcFrameSample(long framePosition, 
	                            float frameTime,
	                            ModuleGenerator parentModuleGenerator,
	                            ModuleArguments moduleArguments);

	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param parentModuleGenerator
	 * 			is the parent module generator.
	 * @return
	 * 			the bufSoundSample.<br/>
	 * 			<code>null</code> if time is out of generator time.
	 */
	SoundSample retrieveFrameSample(long framePosition,
								   float frameTime,
								   ModuleGenerator parentModuleGenerator,
								   ModuleArguments moduleArguments);

	/**
	 * @param inputData
	 * 			is the input data.
	 * @return
	 * 			the input GeneratorBuffer.<br/>
	 * 			<code>null</code> if there is no input-timeline found.
	 */
	GeneratorBufferInterface getInputGeneratorBuffer(InputData inputData);
	
	/**
	 * @param generator
	 * 			is the Sub-Generator.
	 * @return 
	 * 			returns Timeline of the {@link #subGeneratorTimelines} for given Sub-Generator.
	 */
	Timeline getSubGeneratorTimeline(Generator generator);

	/**
	 * @return
	 * 			the generator.
	 */
	Generator getGenerator();
	
	/**
	 * @see Generator#createGeneratorData()
	 * 
	 * @return
	 * 			the generator data.
	 */
	Object getGeneratorData();
	
}
