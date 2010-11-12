/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputs;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulInputTypeEdit.ModulInputTypeEditController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectController;

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
	
	private final ModulInputTypeSelectController modulInputTypeSelectController;
	private final ModulInputTypeEditController modulInputTypeEditController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 */
	public ModulInputTypesController(final AppController appController,
	                                 final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.modulInputTypesModel = new ModulInputTypesModel();
		this.modulInputTypesView = new ModulInputTypesView(appController.getAppView());
		
		//------------------------------------------------------------------------------------------
		this.modulInputTypeSelectController = new ModulInputTypeSelectController(appModelChangedObserver);
		
		this.modulInputTypesView.setModulInputTypeSelectView(this.modulInputTypeSelectController.getInputTypeSelectView());
		
		//------------------------------------------------------------------------------------------
		this.modulInputTypeEditController = new ModulInputTypeEditController();
		
		this.modulInputTypesView.setModulInputTypeEditView(this.modulInputTypeEditController.getModulInputTypeEditView());
		
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

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeSelectController}.
	 */
	public ModulInputTypeSelectController getModulInputTypeSelectController()
	{
		return this.modulInputTypeSelectController;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeEditController}.
	 */
	public ModulInputTypeEditController getModulInputTypeEditController()
	{
		return this.modulInputTypeEditController;
	}
}
