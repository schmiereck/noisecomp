/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Modul-Generator Remove Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>12.10.2010:	created, smk</p>
 */
public interface ModulGeneratorRemoveListenerInterface
{

	/**
	 * Notify the Listener 
	 * that the given generator is removed.
	 * 
	 * @param removedGenerator
	 * 			is the generator.
	 */
	void notifyModulGeneratorRemoved(Generator removedGenerator);
}
