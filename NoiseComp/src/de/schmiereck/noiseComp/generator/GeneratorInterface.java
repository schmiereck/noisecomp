package de.schmiereck.noiseComp.generator;


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
	 * Liefert null, wenn der Generator für den Zeitpunkt keinen Wert 
	 * generieren kann (Frame-Position nicht zwischen Start und Ende).
	 * 
	 * @param framePosition
	 * 			ist the position of the sample frame.
	 * @return
	 * 			the sound sample.
	 */
	SoundSample generateFrameSample(long framePosition, ModulGenerator parentModulGenerator);

	/**
	 * @return
	 * 			the name of the generator.
	 */
	String getName();

}
