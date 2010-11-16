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
		//------------------------------------------------------------------------------------------
		this.modulEditModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						float zoomX = modulEditModel.getZoomX();
						
						modulGeneratorTypeData.setViewZoomX(zoomX);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.modulEditModel.getTicksPerChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						TicksPer ticksPer = modulEditModel.getTicksPer();
						
						modulGeneratorTypeData.setViewTicksPer(ticksPer);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.modulEditModel.getTicksCountChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					if (modulGeneratorTypeData != null)
					{
						Float ticksCount = modulEditModel.getTicksCount();
						
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

	public void doTimelinesZoomIn()
	{
		//==========================================================================================
		float zoomX = this.modulEditModel.getZoomX();
		
		zoomX *= 1.5F;
		
		this.modulEditModel.setZoomX(zoomX);

		//==========================================================================================
	}

	public void doTimelinesZoomOut()
	{
		//==========================================================================================
		float zoomX = this.modulEditModel.getZoomX();
		
		zoomX /= 1.5F;
		
		this.modulEditModel.setZoomX(zoomX);

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
		this.modulEditModel.setModulName(generatorTypeName);
		this.modulEditModel.setModulIsMain(modulIsMain);
		
		this.modulEditModel.setZoomX(viewZoomX);
		this.modulEditModel.setTicksPer(viewTicksPer);
		this.modulEditModel.setTicksCount(viewTicksCount);
		
		//==========================================================================================
	}
}
