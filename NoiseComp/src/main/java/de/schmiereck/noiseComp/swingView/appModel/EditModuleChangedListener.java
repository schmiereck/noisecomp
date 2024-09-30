/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import javax.swing.tree.TreePath;

import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeModel;



/**
 * <p>
 * 	{@link AppModel#getEditedModuleGeneratorTypeData()} Changed Listener.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public interface EditModuleChangedListener
{

	/**
	 * Edit Module performed.
	 * 
	 * @param modulesTreeModel
	 * 			is the Modules Tree Model.
	 * @param selectionTreePath
	 * 			is the selection Tree-Path.
	 */
	public void notifyEditModuleChanged(ModulesTreeModel modulesTreeModel,
	                                    TreePath selectionTreePath);
}
