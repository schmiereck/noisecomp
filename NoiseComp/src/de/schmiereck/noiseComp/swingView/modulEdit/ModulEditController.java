/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import de.schmiereck.noiseComp.swingView.appController.AppController;


/**
 * <p>
 * 	Modul-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModulEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Edit View.
	 */
	private final ModulEditView	modulEditView;

	/**
	 * Modul-Edit Model.
	 */
	private final ModulEditModel	modulEditModel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the App Controller.
	 */
	public ModulEditController(final AppController appController)
	{
		//==========================================================================================
		this.modulEditModel = new ModulEditModel();
		this.modulEditView = new ModulEditView(this.modulEditModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulEditView}.
	 */
	public ModulEditView getModulEditView()
	{
		return this.modulEditView;
	}
}
