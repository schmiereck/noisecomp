/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;


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
	private final ModulEditModel modulEditModel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the App Controller.
	 * @param modulesTreeModel
	 * 			is the Modules Tree Model.
	 * @param appModelChangedObserver 
	 * 			is the AppModelChangedObserver.
	 */
	public ModulEditController(final AppController appController,
	                           final ModulesTreeModel modulesTreeModel, 
	                           final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.modulEditModel = new ModulEditModel();
		this.modulEditView = new ModulEditView(this.modulEditModel);
		
		//------------------------------------------------------------------------------------------
		// Update-Button: Update Modul:
		
		this.modulEditView.getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						String generatorTypeName = InputUtils.makeStringValue(modulEditView.getModulNameTextField().getText());
						Boolean modulIsMain = InputUtils.makeBooleanValue(modulEditView.getModulIsMainCheckBox().isSelected());
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Edit-Model:
						
						modulEditModel.setModulName(generatorTypeName);
						modulEditModel.setModulIsMain(modulIsMain);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Modul:
						
						modulGeneratorTypeData.setGeneratorTypeName(generatorTypeName);
						modulGeneratorTypeData.setIsMainModulGeneratorType(modulIsMain);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
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

	/**
	 * @return 
	 * 			returns the {@link #modulEditModel}.
	 */
	public ModulEditModel getModulEditModel()
	{
		return this.modulEditModel;
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the Modul-Generator-Type Data.
	 */
	public void doEditModuleChanged(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		String generatorTypeName;
		Boolean modulIsMain;
		
		if (modulGeneratorTypeData != null)
		{
			generatorTypeName = modulGeneratorTypeData.getGeneratorTypeName();
			modulIsMain = modulGeneratorTypeData.getIsMainModulGeneratorType();
		}
		else
		{
			generatorTypeName = null;
			modulIsMain = null;
		}

		this.modulEditModel.setModulName(generatorTypeName);
		this.modulEditModel.setModulIsMain(modulIsMain);
	}
}
