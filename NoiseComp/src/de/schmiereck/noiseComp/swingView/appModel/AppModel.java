/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.io.File;


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
	 * Die Datei der letzten Datei-Operation.
	 */
	private File fileActionFile = null;

	//----------------------------------------------------------------------------------------------
	/**
	 * <code>true</code> if model is changed and should be saved.
	 */
	private boolean isModelChanged = false;

	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #fileActionFile}.
	 */
	public File getFileActionFile()
	{
		return this.fileActionFile;
	}

	/**
	 * @param fileActionFile 
	 * 			to set {@link #fileActionFile}.
	 */
	public void setFileActionFile(File fileActionFile)
	{
		this.fileActionFile = fileActionFile;
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

}
