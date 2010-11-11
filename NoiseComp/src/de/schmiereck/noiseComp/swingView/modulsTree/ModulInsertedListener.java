/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * <p>
 * 	Modul Inserted Listener.
 * </p>
 * 
 * @author smk
 * @version <p>11.11.2010:	created, smk</p>
 */
public interface ModulInsertedListener
{

	/**
	 * @param modulGeneratorTypeData
	 * 			is the new Modul-Generator-Type Data.
	 */
	void notifyModulInserted(ModulGeneratorTypeData modulGeneratorTypeData);
}
