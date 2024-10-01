package de.schmiereck.noiseComp.generator;


import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

/**
 * Interface das jeder Generator implementiert.
 *
 * @author smk
 * @version 22.01.2004
 */
public interface GeneratorInterface
{
	/**
	 * Wird aufgerufen, um den Ausgangswert eines Generators für die angegebene 
	 * Frame-Position zu ermitteln.
	 * 
	 * Liefert <code>null</code>, wenn der Generator für den Zeitpunkt keinen Wert 
	 * generieren kann (Frame-Position nicht zwischen Start und Ende).
	 * 
	 * @param framePosition
	 * 			ist the position of the sample frame.
	 * @param parentModuleGenerator
	 * 			is the TODO parentModuleGenerator.
	 * @param generatorBuffer
	 * 			is the generator buffer.<br/>
	 * 			<code>null</code> if there is no buffer available.
	 * @param moduleArguments
	 * 			are the Arguments of calling Module
	 * @return
	 * 			the sound sample.
	 */
	SoundSample generateFrameSample(long framePosition, 
	                                ModuleGenerator parentModuleGenerator,
	                                GeneratorBufferInterface generatorBuffer,
	                                ModuleArguments moduleArguments);

	/**
	 * @return
	 * 			the name of the generator.
	 */
	String getName();

}
