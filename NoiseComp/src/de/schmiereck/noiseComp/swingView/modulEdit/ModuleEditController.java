/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulesTree.ModulesTreeModel;


/**
 * <p>
 * 	ModuleEdit Controller.
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
	 * ModuleEdit View.
	 */
	private final ModuleEditView	moduleEditView;

	/**
	 * ModuleEdit Model.
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
		this.moduleEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					if (moduleGeneratorTypeData != null)
					{
						float zoomX = moduleEditModel.getZoomX();
						
						moduleGeneratorTypeData.setViewZoomX(zoomX);
						
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
					ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					if (moduleGeneratorTypeData != null)
					{
						TicksPer ticksPer = moduleEditModel.getTicksPer();
						
						moduleGeneratorTypeData.setViewTicksPer(ticksPer);
						
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
					ModuleGeneratorTypeData moduleGeneratorTypeData = modulesTreeModel.getEditedModuleGeneratorTypeData();
					
					if (moduleGeneratorTypeData != null)
					{
						Float ticksCount = moduleEditModel.getTicksCount();
						
						moduleGeneratorTypeData.setViewTicksCount(ticksCount);
						
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
	 * @param moduleGeneratorTypeData
	 * 			is the ModuleGenerator-Type Data.
	 */
	public void doEditModuleChanged(ModuleGeneratorTypeData moduleGeneratorTypeData)
	{
		//==========================================================================================
		String generatorTypeName;
		Boolean modulesMain;
		Float viewZoomX;
		TicksPer viewTicksPer;
		Float viewTicksCount;
		
		if (moduleGeneratorTypeData != null)
		{
			generatorTypeName = moduleGeneratorTypeData.getGeneratorTypeName();
			modulesMain = moduleGeneratorTypeData.getIsMainModuleGeneratorType();
			
			viewZoomX = moduleGeneratorTypeData.getViewZoomX();
			viewTicksPer = moduleGeneratorTypeData.getViewTicksPer();
			viewTicksCount = moduleGeneratorTypeData.getViewTicksCount();

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
			modulesMain = null;
			viewZoomX = new Float(1.0F);
			viewTicksPer = TicksPer.Seconds;
			viewTicksCount = new Float(1.0F);
		}

		//------------------------------------------------------------------------------------------
		this.moduleEditModel.setModuleName(generatorTypeName);
		this.moduleEditModel.setModuleIsMain(modulesMain);
		
		this.moduleEditModel.setZoomX(viewZoomX);
		this.moduleEditModel.setTicksPer(viewTicksPer);
		this.moduleEditModel.setTicksCount(viewTicksCount);
		
		//==========================================================================================
	}
}
