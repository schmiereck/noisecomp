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

	/**
	 * @param framePosition
	 * 			is the frame position.
	 * @param inputData
	 * 			is the input data.
	 * @param parentModulGenerator
	 * 			is the parent modul generator.
	 * @return
	 * 			the bufInputSoundSample.<br/>
	 * 			<code>null</code> if there is no input generator available (constant value, etc.).
	 */
	SoundSample calcInputFrameSample(long framePosition, InputData inputData, ModulGenerator parentModulGenerator);
	
}
