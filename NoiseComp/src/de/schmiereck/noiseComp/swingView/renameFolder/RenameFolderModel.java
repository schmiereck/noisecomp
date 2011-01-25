/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Rename-Folder Model.
 * </p>
 * 
 * @author smk
 * @version <p>25.01.2011:	created, smk</p>
 */
public class RenameFolderModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Input-Type Name.
	 */
	private String moduleName = null;

	/**
	 * {@link #moduleName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier moduleNameChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #moduleName}.
	 */
	public String getModuleName()
	{
		return this.moduleName;
	}

	/**
	 * @param moduleName 
	 * 			to set {@link #moduleName}.
	 */
	public void setModuleName(String inputTypeName)
	{
		this.moduleName = inputTypeName;
		
		// Notify listeners.
		this.moduleNameChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getModuleNameChangedNotifier()
	{
		return this.moduleNameChangedNotifier;
	}
}
