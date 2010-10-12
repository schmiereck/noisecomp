/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

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
//	 * @param parentModulGenerator
//	 * 			is the parent modul generator.
//	 * @return
//	 * 			the bufInputSoundSample.<br/>
//	 * 			<code>null</code> if there is no input generator available (constant value, etc.).
//	 */
//	SoundSample calcFrameSample(long framePosition, InputData inputData, ModulGenerator parentModulGenerator);
	
	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param frameTime
	 * 			is the absolute time of the frame in milli seconds.
	 * @param parentModulGenerator
	 * 			is the parent modul generator.
	 * @return
	 * 			the bufSoundSample.
	 */
	SoundSample calcFrameSample(long framePosition, 
	                            float frameTime,
	                            ModulGenerator parentModulGenerator);

	/**
	 * @param inputData
	 * 			is the input data.
	 * @return
	 * 			the input GeneratorBuffer.<br/>
	 * 			<code>null</code> if there is no input-timeline found.
	 */
	GeneratorBufferInterface getInputGeneratorBuffer(InputData inputData);
}
