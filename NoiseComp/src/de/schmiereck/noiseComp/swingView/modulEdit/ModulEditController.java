/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;


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
	public ModulEditController(final AppController appController,
	                           final AppModel appModel)
	{
		//==========================================================================================
		this.modulEditModel = new ModulEditModel();
		this.modulEditView = new ModulEditView(this.modulEditModel);
		
		//------------------------------------------------------------------------------------------
		appModel.addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModulChanged(AppModel appModel)
				{
					String generatorTypeName;

					ModulGeneratorTypeData modulGeneratorTypeData = appModel.getEditedModulGeneratorTypeData();
					
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
}
