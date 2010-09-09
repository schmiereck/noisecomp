/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import javax.swing.JPanel;

/**
 * <p>
 * 	Modul-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModulEditView
extends JPanel
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Edit Model.
	 */
	private ModulEditModel	modulEditModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulEditModel
	 */
	public ModulEditView(ModulEditModel modulEditModel)
	{
		//==========================================================================================
		this.modulEditModel = modulEditModel;
		
		//==========================================================================================
	}
}
