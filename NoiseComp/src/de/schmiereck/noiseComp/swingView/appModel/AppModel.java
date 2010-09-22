/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.io.File;


/**
 * <p>
 * 	App Model.
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

}
