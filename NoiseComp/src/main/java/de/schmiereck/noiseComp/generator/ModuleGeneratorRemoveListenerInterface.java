/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;

/**
 * <p>
 * 	Module-Generator Remove Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>12.10.2010:	created, smk</p>
 */
public interface ModuleGeneratorRemoveListenerInterface
{

	/**
	 * Notify the Listener
	 * that the given generator is removed.
	 *
	 * @param soundSourceData
	 * @param removedGenerator is the generator.
	 */
	void notifyModuleGeneratorRemoved(SoundSourceData soundSourceData, Generator removedGenerator);
}
