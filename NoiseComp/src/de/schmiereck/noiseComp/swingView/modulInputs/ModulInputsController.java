/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputs;

import de.schmiereck.noiseComp.swingView.appController.AppController;

/**
 * <p>
 * 	Modul-Inputs Controller.
 * </p>
 * 
 * @author smk
 * @version <p>12.09.2010:	created, smk</p>
 */
public class ModulInputsController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Inputs Model.
	 */
	private final ModulInputsModel modulInputsModel;

	/**
	 * Modul-Inputs View.
	 */
	private final ModulInputsView modulInputsView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 */
	public ModulInputsController(AppController appController)
	{
		//==========================================================================================
		this.modulInputsModel = new ModulInputsModel();
		
		this.modulInputsView = new ModulInputsView(appController.getAppView());
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputsView}.
	 */
	public ModulInputsView getModulInputsView()
	{
		return this.modulInputsView;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputsModel}.
	 */
	public ModulInputsModel getModulInputsModel()
	{
		return this.modulInputsModel;
	}
}
