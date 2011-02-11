/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputs;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulInputTypeEdit.ModuleInputTypeEditController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModuleInputTypeSelectController;

/**
 * <p>
 * 	Modul-Input-Types Controller.
 * </p>
 * 
 * @author smk
 * @version <p>12.09.2010:	created, smk</p>
 */
public class ModuleInputTypesController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Inputs Model.
	 */
	private final ModuleInputTypesModel moduleInputTypesModel;

	/**
	 * Modul-Input-Types View.
	 */
	private final ModuleInputTypesView moduleInputTypesView;
	
	private final ModuleInputTypeSelectController moduleInputTypeSelectController;
	private final ModuleInputTypeEditController moduleInputTypeEditController;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 * @param appModelChangedObserver 
	 * 			is the AppModelChangedObserver.
	 */
	public ModuleInputTypesController(final AppController appController,
	                                 final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.moduleInputTypesModel = new ModuleInputTypesModel();
		this.moduleInputTypesView = new ModuleInputTypesView(appController.getAppView());
		
		//------------------------------------------------------------------------------------------
		this.moduleInputTypeSelectController = new ModuleInputTypeSelectController(appModelChangedObserver);
		
		this.moduleInputTypesView.setModuleInputTypeSelectView(this.moduleInputTypeSelectController.getInputTypeSelectView());
		
		//------------------------------------------------------------------------------------------
		this.moduleInputTypeEditController = new ModuleInputTypeEditController();
		
		this.moduleInputTypesView.setModuleInputTypeEditView(this.moduleInputTypeEditController.getModuleInputTypeEditView());
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypesView}.
	 */
	public ModuleInputTypesView getModuleInputTypesView()
	{
		return this.moduleInputTypesView;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypesModel}.
	 */
	public ModuleInputTypesModel getModuleInputTypesModel()
	{
		return this.moduleInputTypesModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeSelectController}.
	 */
	public ModuleInputTypeSelectController getModuleInputTypeSelectController()
	{
		return this.moduleInputTypeSelectController;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeEditController}.
	 */
	public ModuleInputTypeEditController getModuleInputTypeEditController()
	{
		return this.moduleInputTypeEditController;
	}
}
