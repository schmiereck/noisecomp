/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulesTree;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;


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
	 * Do Edit Module performed.
	 * 
	 * @param moduleGeneratorTypeData
	 * 			is the edited module
	 */
	public void notifyEditModule(ModuleGeneratorTypeData moduleGeneratorTypeData);
}
