/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
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
public class ModuleEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Edit View.
	 */
	private final ModuleEditView	moduleEditView;

	/**
	 * Modul-Edit Model.
	 */
	private final ModuleEditModel moduleEditModel;

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
	public ModuleEditController(final AppController appController,
	                           final ModulesTreeModel modulesTreeModel, 
	                           final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.moduleEditModel = new ModuleEditModel();
		this.moduleEditView = new ModuleEditView(this.moduleEditModel);
		
		//------------------------------------------------------------------------------------------
		// Update-Button: Update Modul:
		
		this.moduleEditView.getUpdateButton().addActionListener
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
						String generatorTypeName = InputUtils.makeStringValue(moduleEditView.getModulNameTextField().getText());
						Boolean modulIsMain = InputUtils.makeBooleanValue(moduleEditView.getModulIsMainCheckBox().isSelected());
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Modul:
						
						modulGeneratorTypeData.setGeneratorTypeName(generatorTypeName);
						modulGeneratorTypeData.setIsMainModulGeneratorType(modulIsMain);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Edit-Model:
						
						moduleEditModel.setModulName(generatorTypeName);
						moduleEditModel.setModulIsMain(modulIsMain);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.moduleEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						float zoomX = moduleEditModel.getZoomX();
						
						modulGeneratorTypeData.setViewZoomX(zoomX);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.moduleEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						TicksPer ticksPer = moduleEditModel.getTicksPer();
						
						modulGeneratorTypeData.setViewTicksPer(ticksPer);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.moduleEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						Float ticksCount = moduleEditModel.getTicksCount();
						
						modulGeneratorTypeData.setViewTicksCount(ticksCount);
						
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
	 * 			returns the {@link #moduleEditView}.
	 */
	public ModuleEditView getModuleEditView()
	{
		return this.moduleEditView;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleEditModel}.
	 */
	public ModuleEditModel getModuleEditModel()
	{
		return this.moduleEditModel;
	}

	public void doTimelinesZoomIn()
	{
		//==========================================================================================
		float zoomX = this.moduleEditModel.getZoomX();
		
		zoomX *= 1.5F;
		
		this.moduleEditModel.setZoomX(zoomX);

		//==========================================================================================
	}

	public void doTimelinesZoomOut()
	{
		//==========================================================================================
		float zoomX = this.moduleEditModel.getZoomX();
		
		zoomX /= 1.5F;
		
		this.moduleEditModel.setZoomX(zoomX);

		//==========================================================================================
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the Modul-Generator-Type Data.
	 */
	public void doEditModuleChanged(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		String generatorTypeName;
		Boolean modulIsMain;
		Float viewZoomX;
		TicksPer viewTicksPer;
		Float viewTicksCount;
		
		if (modulGeneratorTypeData != null)
		{
			generatorTypeName = modulGeneratorTypeData.getGeneratorTypeName();
			modulIsMain = modulGeneratorTypeData.getIsMainModulGeneratorType();
			
			viewZoomX = modulGeneratorTypeData.getViewZoomX();
			viewTicksPer = modulGeneratorTypeData.getViewTicksPer();
			viewTicksCount = modulGeneratorTypeData.getViewTicksCount();

			if (viewZoomX == null)
			{
				viewZoomX = new Float(40.0F);
			}
			
			if (viewTicksPer == null)
			{
				viewTicksPer = TicksPer.Seconds;
			}
			
			if (viewTicksCount == null)
			{
				viewTicksCount = new Float(1.0F);
			}
		}
		else
		{
			generatorTypeName = null;
			modulIsMain = null;
			viewZoomX = new Float(1.0F);
			viewTicksPer = TicksPer.Seconds;
			viewTicksCount = new Float(1.0F);
		}

		//------------------------------------------------------------------------------------------
		this.moduleEditModel.setModulName(generatorTypeName);
		this.moduleEditModel.setModulIsMain(modulIsMain);
		
		this.moduleEditModel.setZoomX(viewZoomX);
		this.moduleEditModel.setTicksPer(viewTicksPer);
		this.moduleEditModel.setTicksCount(viewTicksCount);
		
		//==========================================================================================
	}
}
