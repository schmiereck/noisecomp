/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.createFolder;

import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Create-Folder Model.
 * </p>
 * 
 * @author smk
 * @version <p>31.01.2011:	created, smk</p>
 */
public class CreateFolderModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Input-Type Name.
	 */
	private String folderName = null;

	/**
	 * {@link #folderName} changed listeners.
	 */
	private final ModelPropertyChangedNotifier folderNameChangedNotifier = new ModelPropertyChangedNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #folderName}.
	 */
	public String getFolderName()
	{
		return this.folderName;
	}

	/**
	 * @param folderName 
	 * 			to set {@link #folderName}.
	 */
	public void setFolderName(String folderName)
	{
		if (CompareUtils.compareWithNull(this.folderName, folderName) == false)
		{
			this.folderName = folderName;
			
			// Notify listeners.
			this.folderNameChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDataChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getFolderNameChangedNotifier()
	{
		return this.folderNameChangedNotifier;
	}
}
