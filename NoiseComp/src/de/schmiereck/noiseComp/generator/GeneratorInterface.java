package de.schmiereck.noiseComp.generator;


/**
 * TODO docu
 *
 * @author smk
 * @version 22.01.2004
 */
public interface GeneratorInterface
{
	/**
	 * Wird aufgerufen, um den Ausgangswert eines Generators f�r die angegebene 
	 * Frame-Position zu ermitteln.
	 * 
	 * Liefert null, wenn der Generator f�r den Zeitpunkt keinen Wert 
	 * generieren kann (Frame-Position nicht zwischen Start und Ende).
	 * 
	 * @param framePosition
	 * @return
	 */
	SoundSample generateFrameSample(long framePosition, ModulGenerator parentModulGenerator);

	/**
	 * @return
	 */
	String getName();

}
