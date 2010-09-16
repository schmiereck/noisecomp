/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputs;

import de.schmiereck.noiseComp.swingView.appController.AppController;

/**
 * <p>
 * 	Modul-Input-Types Controller.
 * </p>
 * 
 * @author smk
 * @version <p>12.09.2010:	created, smk</p>
 */
public class ModulInputTypesController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Inputs Model.
	 */
	private final ModulInputTypesModel modulInputTypesModel;

	/**
	 * Modul-Input-Types View.
	 */
	private final ModulInputTypesView modulInputTypesView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 */
	public ModulInputTypesController(AppController appController)
	{
		//==========================================================================================
		this.modulInputTypesModel = new ModulInputTypesModel();
		
		this.modulInputTypesView = new ModulInputTypesView(appController.getAppView());
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypesView}.
	 */
	public ModulInputTypesView getModulInputTypesView()
	{
		return this.modulInputTypesView;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypesModel}.
	 */
	public ModulInputTypesModel getModulInputTypesModel()
	{
		return this.modulInputTypesModel;
	}
}
