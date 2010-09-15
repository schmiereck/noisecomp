/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditController;
import de.schmiereck.noiseComp.swingView.modulInputs.ModulInputsController;
import de.schmiereck.noiseComp.swingView.modulsTree.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditController;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesScrollPanelController;


/**
 * <p>
 * 	App Controller.
 * </p>
 * 
 * @author smk
 * @version <p>04.09.2010:	created, smk</p>
 */
public class AppController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modules Tree Controller.
	 */
	private final ModulesTreeController modulesTreeController;
	
	/**
	 * Modul-Edit Controller.
	 */
	private final ModulEditController modulEditController;
	
	/**
	 * Timelines Scroll-Panel Controller.
	 */
	private final TimelinesScrollPanelController timelinesScrollPanelController;
	
	/**
	 * Timelines Draw-Panel Controller.
	 */
	private final TimelinesDrawPanelController timelinesDrawPanelController;
	
	/**
	 * Timeline-Edit Controller.
	 */
	private final TimelineEditController timelineEditController;
	
	/**
	 * Modul-Inputs Controller.
	 */
	private final ModulInputsController modulInputsController;
	
	/**
	 * App Model
	 */
	private final AppModel appModel;
	
	/**
	 * App View
	 */
	private final AppView appView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public AppController()
	{
		//==========================================================================================
		this.appModel = new AppModel();
		
		this.appView = new AppView(this.appModel);
		
		this.appView.setTitle("NoiseComp V2.0");
		this.appView.setSize(800, 600);
		this.appView.setLocationRelativeTo(null);
		this.appView.setVisible(true);
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController = new ModulesTreeController(this);
		
		this.appView.setModulesTreeView(this.modulesTreeController.getModulesTreeView());
		
		//------------------------------------------------------------------------------------------
		this.modulEditController = new ModulEditController(this,
		                                                   this.modulesTreeController.getModulesTreeModel());
		
		this.appView.setModulEditView(this.modulEditController.getModulEditView());
		
		//------------------------------------------------------------------------------------------
		this.timelinesScrollPanelController = new TimelinesScrollPanelController();
		
		this.appView.setTimelineComponent(this.timelinesScrollPanelController.getTimelinesScrollPanelView());
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = new TimelinesDrawPanelController();
		
		this.timelinesScrollPanelController.setTimelinesScrollPanelController(this.timelinesDrawPanelController);
		
		//------------------------------------------------------------------------------------------
		this.timelineEditController = 
			new TimelineEditController(this,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel());
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
		//------------------------------------------------------------------------------------------
		this.modulInputsController = new ModulInputsController(this);
		
//		this.appView.add(this.modulInputsController.getModulInputsView());
		
//		//------------------------------------------------------------------------------------------
//		this.appModel.addEditModuleChangedListener(this.appView);
//		
		
		//------------------------------------------------------------------------------------------
		final InputSelectController inputSelectController = 
			new InputSelectController(this,
			                          this.timelinesDrawPanelController.getTimelinesDrawPanelModel());
		
		this.appView.setInputSelectView(inputSelectController.getInputSelectView());
		
		//------------------------------------------------------------------------------------------
		final InputEditController inputEditController = 
			new InputEditController();
		
		this.appView.setInputEditView(inputEditController.getInputEditView());
		
		//==========================================================================================
		this.modulesTreeController.getModulesTreeView().addDoEditModuleListener
		(
		 	new DoEditModuleListener()
		 	{
				@Override
				public void notifyEditModul(ModulGeneratorTypeData modulGeneratorTypeData)
				{
					selectEditModule(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.modulEditController.getModulEditView().getEditInputsButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulInputsController.getModulInputsView().setVisible(true);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Edit-Modul Model changed: Update Tree-View:
		
		this.modulEditController.getModulEditModel().getModulEditModelChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulGeneratorTypeData editedModulGeneratorTypeData = 
						modulesTreeController.getModulesTreeModel().getEditedModulGeneratorTypeData();
					
					modulesTreeController.updateEditedModulTreeEntry(editedModulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Selected Input changed: Update Input-Edit:
		
		inputSelectController.getInputSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					InputData inputData;
					
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					if (selectedRowNo != null)
					{
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
					
						inputData = inputsTabelModel.getRow(selectedRowNo);
					}
					else
					{
						inputData = null;
					}
					
					inputEditController.updateEditedInput(inputData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Update-Button: Update Timelines-Model, Input-Select-Model and Input:
		
//		inputEditController.getInputEditView().getUpdateButton().addActionListener
//		(
//		 	new ActionListener()
//		 	{
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
//					
//					if (timelineGeneratorModel != null)
//					{
//						Generator generator = 
//							appController.retrieveGeneratorOfEditedModul(timelineGeneratorModel.getName());
//
//						String generatorName = timelineEditView.getGeneratorNameTextField().getText();
//						Float generatorStartTimePos = Float.parseFloat(timelineEditView.getGeneratorStartTimePosTextField().getText());
//						Float generatorEndTimePos = Float.parseFloat(timelineEditView.getGeneratorEndTimePosTextField().getText());
//						
//						timelineGeneratorModel.setName(generatorName);
//						timelineGeneratorModel.setStartTimePos(generatorStartTimePos);
//						timelineGeneratorModel.setEndTimePos(generatorEndTimePos);
//						
//						// Update Generator.
//						generator.setName(generatorName);
//						generator.setStartTimePos(generatorStartTimePos);
//						generator.setEndTimePos(generatorEndTimePos);
//					}
//				}
//		 	}
//		);
		//==========================================================================================
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void selectEditModule(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		this.modulesTreeController.getModulesTreeModel().setEditedModulGeneratorTypeData(modulGeneratorTypeData);
		
		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		Iterator<Generator> generatorsIterator = modulGeneratorTypeData.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = generatorsIterator.next();
			
			TimelineGeneratorModel timelineGeneratorModel = 
				new TimelineGeneratorModel(generator.getName(),
				                           generator.getStartTimePos(),
				                           generator.getEndTimePos());
			
			this.timelinesDrawPanelController.addTimelineGeneratorModel(timelineGeneratorModel);
		}
		//==========================================================================================
	}

	/**
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @return
	 * 			the genaror with given name.
	 */
	public Generator retrieveGeneratorOfEditedModul(String generatorName)
	{
		//==========================================================================================
		ModulGeneratorTypeData modulGeneratorTypeData = 
			modulesTreeController.getModulesTreeModel().getEditedModulGeneratorTypeData();
		
		Generator generator = modulGeneratorTypeData.searchGenerator(generatorName);
		
		//==========================================================================================
		return generator;
	}

	/**
	 * @return 
	 * 			returns the {@link #appView}.
	 */
	public AppView getAppView()
	{
		return this.appView;
	}
}
