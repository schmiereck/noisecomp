/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;


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
	 */
	public ModulEditController(final AppController appController,
	                           final ModulesTreeModel modulesTreeModel)
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
						String generatorTypeName = modulEditView.getModulNameTextField().getText();

						modulEditModel.setModulName(generatorTypeName);
						
						// Update Modul.
						modulGeneratorTypeData.setGeneratorTypeName(generatorTypeName);
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
		
		if (modulGeneratorTypeData != null)
		{
			generatorTypeName = modulGeneratorTypeData.getGeneratorTypeName();
		}
		else
		{
			generatorTypeName = null;
		}

		modulEditModel.setModulName(generatorTypeName);
	}
}
