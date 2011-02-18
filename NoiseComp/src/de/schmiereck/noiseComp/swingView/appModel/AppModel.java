/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 * <p>
 * 	App Model.
 * </p>
 * <p>
 * 	see: http://de.wikipedia.org/wiki/Beobachter_%28Entwurfsmuster%29
 * 
 * 	AppModelChangedObserver
 * 		ModulsModelChangedObserver
 * 			- editedModul
 * 			TimelinesModelChangedObserver
 * 				- editedTimeline
 * 			ModulInputTypesModelChangedObserver
 * 				- editedModulInputType
 * 			InputsModelChangedObserver
 * 				- editedInput
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public class AppModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * File of last file load or save operation.
	 */
	private File loadFile = null;

	/**
	 * File of last file load or save operation.
	 */
	private File importFile = null;

	//----------------------------------------------------------------------------------------------
	/**
	 * <code>true</code> if model is changed and should be saved.
	 */
	private boolean isModelChanged = false;

	/**
	 * Edited Folder Tree-Node.
	 */
	private DefaultMutableTreeNode editedModuleTreeNode = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #loadFile}.
	 */
	public File getLoadFile()
	{
		return this.loadFile;
	}

	/**
	 * @param loadFile 
	 * 			to set {@link #loadFile}.
	 */
	public void setLoadFile(File fileActionFile)
	{
		this.loadFile = fileActionFile;
	}

	/**
	 * @return 
	 * 			returns the {@link #importFile}.
	 */
	public File getImportFile()
	{
		return this.importFile;
	}

	/**
	 * @param importFile 
	 * 			to set {@link #importFile}.
	 */
	public void setImportFile(File importFile)
	{
		this.importFile = importFile;
	}

	/**
	 * @return 
	 * 			returns the {@link #isModelChanged}.
	 */
	public boolean getIsModelChanged()
	{
		return this.isModelChanged;
	}

	/**
	 * @param isModelChanged 
	 * 			to set {@link #isModelChanged}.
	 */
	public void setIsModelChanged(boolean isModelChanged)
	{
		this.isModelChanged = isModelChanged;
	}

	/**
	 * @return 
	 * 			returns the {@link #editedModuleTreeNode}.
	 */
	public DefaultMutableTreeNode getEditedModuleTreeNode()
	{
		return this.editedModuleTreeNode;
	}

	/**
	 * @param editedModuleTreeNode 
	 * 			to set {@link #editedModuleTreeNode}.
	 */
	public void setEditedModuleTreeNode(DefaultMutableTreeNode editedModuleTreeNode)
	{
		this.editedModuleTreeNode = editedModuleTreeNode;
	}

}
