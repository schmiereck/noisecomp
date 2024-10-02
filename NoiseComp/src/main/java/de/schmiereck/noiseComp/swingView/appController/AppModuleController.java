/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditController;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditModel;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditView;
import de.schmiereck.noiseComp.swingView.moduleInputs.ModuleInputTypesController;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;

/**
 * <p>
 * 	App-Module Controller.
 * </p>
 * 
 * @author smk
 * @version <p>17.05.2011:	created, smk</p>
 */
public class AppModuleController
{
	//**********************************************************************************************
	// Fields:

	public final AppModelChangedObserver appModelChangedObserver;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppModuleController(final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
	}
	
	/**
	 * Do Update Edit Module.
	 */
	public void doUpdateEditModule(final ModuleEditController moduleEditController,
	                               final ModulesTreeController modulesTreeController,
								   final SoundService soundService)
	{
		//==========================================================================================
		final ModuleEditModel moduleEditModel = moduleEditController.getModuleEditModel();
		final ModuleEditView moduleEditView = moduleEditController.getModuleEditView();

		final ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
		
		if (moduleGeneratorTypeData != null) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			//SoundService soundService = SoundService.getInstance();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final String generatorTypeName = InputUtils.makeStringValue(moduleEditView.getModuleameTextField().getText());
			final Boolean modulesMain = InputUtils.makeBooleanValue(moduleEditView.getModulesMainCheckBox().isSelected());
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Module
			
			//ModuleGeneratorTypeData lastMainModuleGeneratorTypeData =
			soundService.updateModuleGeneratorTypeData(moduleGeneratorTypeData,
			                                           generatorTypeName,
			                                           modulesMain);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Edit-Model:
			
			moduleEditModel.setModuleName(generatorTypeName);
			moduleEditModel.setModuleIsMain(modulesMain);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			appModelChangedObserver.notifyAppModelChanged();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * Do Edit Input-Types.
	 */
	public void doEditInputTypes(final ModuleInputTypesController moduleInputTypesController)
	{
		//==========================================================================================
		moduleInputTypesController.getModuleInputTypesView().setVisible(true);
		
		//==========================================================================================
	}
}
