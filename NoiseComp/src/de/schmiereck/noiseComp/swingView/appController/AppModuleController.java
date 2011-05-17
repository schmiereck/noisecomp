/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appController;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditController;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditModel;
import de.schmiereck.noiseComp.swingView.modulEdit.ModuleEditView;
import de.schmiereck.noiseComp.swingView.modulInputs.ModuleInputTypesController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
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
	                               final ModulesTreeController modulesTreeController)
	{
		//==========================================================================================
		final ModuleEditModel moduleEditModel = moduleEditController.getModuleEditModel();
		final ModuleEditView moduleEditView = moduleEditController.getModuleEditView();
		
		ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
		
		if (modulGeneratorTypeData != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			SoundService soundService = SoundService.getInstance();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			String generatorTypeName = InputUtils.makeStringValue(moduleEditView.getModulNameTextField().getText());
			Boolean modulIsMain = InputUtils.makeBooleanValue(moduleEditView.getModulIsMainCheckBox().isSelected());
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Modul:
			
			//ModulGeneratorTypeData lastMainModulGeneratorTypeData =
				soundService.updateModulGeneratorTypeData(modulGeneratorTypeData,
				                                          generatorTypeName,
				                                          modulIsMain);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Edit-Model:
			
			moduleEditModel.setModulName(generatorTypeName);
			moduleEditModel.setModulIsMain(modulIsMain);
			
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
