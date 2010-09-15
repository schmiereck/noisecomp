/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;



/**
 * <p>
 * 	{@link AppModel#getEditedModulGeneratorTypeData()} Changed Listener.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public interface EditModuleChangedListener
{

	/**
	 * Edit Modul performed.
	 * 
	 * @param modulesTreeModel
	 * 			is the Modules Tree Model.
	 */
	public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel);
}
