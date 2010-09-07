/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appView;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;


/**
 * <p>
 * 	Do Edit-Module Listener.
 * </p>
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public interface DoEditModuleListener
{

	/**
	 * Do Edit Modul performed.
	 * 
	 * @param modulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void notifyEditModul(ModulGeneratorTypeData modulGeneratorTypeData);
}
