/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulsTree;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;

/**
 * <p>
 * 	Modules Tree Model.
 * </p>
 * 
 * @author smk
 * @version <p>14.09.2010:	created, smk</p>
 */
public class ModulesTreeModel
{
	//**********************************************************************************************
	// Fields:

	private final DefaultTreeModel treeModel;
	
	/**
	 * Edited Modul-Generator-Type Data.
	 */
	private ModulGeneratorTypeData editedModulGeneratorTypeData;
	
	private List<EditModuleChangedListener> editModuleChangedListeners = new Vector<EditModuleChangedListener>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param treeModel
	 * 			is the tree Model.
	 */
	public ModulesTreeModel(DefaultTreeModel treeModel)
	{
		this.treeModel = treeModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #editedModulGeneratorTypeData}.
	 */
	public ModulGeneratorTypeData getEditedModulGeneratorTypeData()
	{
		return this.editedModulGeneratorTypeData;
	}

	/**
	 * @param editedModulGeneratorTypeData 
	 * 			to set {@link #editedModulGeneratorTypeData}.
	 */
	public void setEditedModulGeneratorTypeData(ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		this.editedModulGeneratorTypeData = editedModulGeneratorTypeData;
		
		for (EditModuleChangedListener editModuleChangedListener : this.editModuleChangedListeners)
		{
			editModuleChangedListener.notifyEditModulChanged(this);
		}
	}

	/**
	 * @param editModuleChangedListener 
	 * 			to add to {@link #editModuleChangedListeners}.
	 */
	public void addEditModuleChangedListener(EditModuleChangedListener editModuleChangedListener)
	{
		this.editModuleChangedListeners.add(editModuleChangedListener);
	}

	/**
	 * @return 
	 * 			returns the {@link #treeModel}.
	 */
	public DefaultTreeModel getTreeModel()
	{
		return this.treeModel;
	}

}
