/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appModel.EditModuleChangedListener;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditController;
import de.schmiereck.noiseComp.swingView.modulInputTypeEdit.ModulInputTypeEditController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectEntryModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeTabelModel;
import de.schmiereck.noiseComp.swingView.modulInputs.ModulInputTypesController;
import de.schmiereck.noiseComp.swingView.modulsTree.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditController;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelModel;
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
	private final ModulInputTypesController modulInputTypesController;
	
	/**
	 * App Model
	 */
	private final AppModel appModel;
	
	/**
	 * App View
	 */
	private final AppView appView;
	
	private SoundSchedulerLogic soundSchedulerLogic = null;
	
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
		this.modulInputTypesController = new ModulInputTypesController(this);
		
		final ModulInputTypeEditController modulInputTypeEditController = modulInputTypesController.getModulInputTypeEditController();
		final ModulInputTypeSelectController modulInputTypeSelectController = modulInputTypesController.getModulInputTypeSelectController();
		
		//------------------------------------------------------------------------------------------
		this.timelinesScrollPanelController = new TimelinesScrollPanelController();
		
		this.appView.setTimelineComponent(this.timelinesScrollPanelController.getTimelinesScrollPanelView());
		
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelController = 
			new TimelinesDrawPanelController(modulesTreeController.getModulesTreeModel());
		
		this.timelinesScrollPanelController.setTimelinesScrollPanelController(this.timelinesDrawPanelController);
		
		//------------------------------------------------------------------------------------------
		final InputSelectController inputSelectController = 
			new InputSelectController(this,
			                          this.timelinesDrawPanelController.getTimelinesDrawPanelModel());
		
		this.appView.setInputSelectView(inputSelectController.getInputSelectView());
		
		//------------------------------------------------------------------------------------------
		final InputEditController inputEditController = 
			new InputEditController(inputSelectController.getInputSelectModel());
		
		this.appView.setInputEditView(inputEditController.getInputEditView());
		
		//------------------------------------------------------------------------------------------
		this.timelineEditController = 
			new TimelineEditController(this,
			                           this.timelinesDrawPanelController.getTimelinesDrawPanelModel());
		
		this.appView.setTimelineEditView(this.timelineEditController.getTimelineEditView());
		
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
		// Modules-Tree Edited Model changed -> updated Modul-Edit Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel)
				{
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					modulEditController.doEditModuleChanged(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modules-Tree Edited Model changed -> updated Modul-Input-Type Select Model:
		
		this.modulesTreeController.getModulesTreeModel().addEditModuleChangedListener
		(
		 	new EditModuleChangedListener()
		 	{
				@Override
				public void notifyEditModulChanged(ModulesTreeModel modulesTreeModel)
				{
					ModulInputTypeSelectController modulInputTypeSelectController = modulInputTypesController.getModulInputTypeSelectController();
					
					ModulGeneratorTypeData modulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					modulInputTypeSelectController.doEditModuleChanged(modulGeneratorTypeData);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.modulEditController.getModulEditView().getEditInputTypesButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					modulInputTypesController.getModulInputTypesView().setVisible(true);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Edit-Modul Model changed: Update Modules-Tree-View:
		
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
		// Modul-Input-Type Edit: Create-New Button: Update Modul-Input-Type Select Model:
		
		modulInputTypeEditController.getModulInputTypeEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					ModulInputTypeSelectModel modulInputTypeSelectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					doCreateNew(modulInputTypeSelectModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}

				/**
				 * @param inputSelectModel
				 * 			is the Modul-Input-Type Select Model. 
				 */
				private void doCreateNew(ModulInputTypeSelectModel modulInputTypeSelectModel)
				{
					ModulInputTypeTabelModel tabelModel = modulInputTypeSelectModel.getModulInputTypeTabelModel();
					
					ModulInputTypeSelectEntryModel selectEntryModel = new ModulInputTypeSelectEntryModel(null);
					
					int rowNo = tabelModel.addInputData(selectEntryModel);
					
					modulInputTypeSelectModel.setSelectedRowNo(rowNo);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit Update-Button: Update Modul-Input-Type Data and Modul-Input-Type Edit-Model:
		
		modulInputTypeEditController.getModulInputTypeEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					ModulInputTypeSelectModel selectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
					InputTypeData inputTypeData = modulInputTypeSelectController.getSelectedModulInputType();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					modulInputTypeEditController.doUpdate(selectModel, 
					                                      inputTypeData,
					                                      editedModulGeneratorTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Modul-Input-Type Edit Model Value changed: Update Input-Select:
		
		modulInputTypeEditController.getModulInputTypeEditModel().getInputTypeIDChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					ModulInputTypeSelectModel selectModel = modulInputTypeSelectController.getInputTypeSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
						
					modulInputTypeSelectController.doInputTypeUpdated(selectModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Remove-Timeline-Button: Update Modul-Data and Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					TimelineGeneratorModel selectedTimelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Modul-Data:
					
					Generator selectedTimelineGenerator = timelinesDrawPanelController.getSelectedTimelineGenerator();
					
					editedModulGeneratorTypeData.removeGenerator(selectedTimelineGenerator);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select-Model:
					
					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(null);
					
					timelinesDrawPanelModel.removeTimelineGeneratorModel(selectedTimelineGeneratorModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);	
		//------------------------------------------------------------------------------------------
		// Timeline-Edit Create-New-Timeline-Button: Timeline-Select-Model:
		
		this.timelineEditController.getTimelineEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					TimelinesDrawPanelModel timelinesDrawPanelModel = timelinesDrawPanelController.getTimelinesDrawPanelModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select-Model:
					
					TimelineGeneratorModel timelineGeneratorModel = 
						new TimelineGeneratorModel("(new)",
						                           0.0F,
						                           1.0F);
					
					timelinesDrawPanelModel.addTimelineGeneratorModel(timelineGeneratorModel);
					
					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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
					
					InputSelectEntryModel inputSelectEntryModel = inputSelectModel.getSelectedRow();
					
					boolean editInput;
					InputData inputData;
					
					if (inputSelectEntryModel != null)
					{
						inputData = inputSelectEntryModel.getInputData();
						editInput = true;
					}
					else
					{
						inputData = null;
						editInput = false;
					}
					
					ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
					
					ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
					
					Generator selectedTimelineGenerator = timelinesDrawPanelController.getSelectedTimelineGenerator();
					
					inputEditController.updateEditedInput(editedModulGeneratorTypeData,
					                                      selectedTimelineGenerator,
					                                      inputData,
					                                      editInput);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Update-Button: Update Input-Data and Input-Edit-Model:
		
		inputEditController.getInputEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					Generator selectedGenerator = timelinesDrawPanelController.getSelectedTimelineGenerator();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					inputEditController.doUpdate(inputSelectModel, selectedGenerator);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Remove-Input-Button: Update Input-Select-Model and Generator-Input-Data:
		
		inputEditController.getInputEditView().getRemoveButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					if (selectedRowNo != null)
					{
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
					
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Generator-Input-Data:
						
						InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);
						
						InputData inputData = inputSelectEntryModel.getInputData();
						
						Generator ownerGenerator = inputData.getOwnerGenerator();
						
						ownerGenerator.removeInput(inputData);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Input-Select-Model:
						
						inputsTabelModel.removeInput(selectedRowNo);

						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
			 	}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Create-New-Input-Button: Update Input-Select-Model // and Generator-Input-Data:
		
		inputEditController.getInputEditView().getCreateNewButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					doCreateNewInput(inputSelectModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 	}

				/**
				 * @param inputSelectModel
				 * 			is the Input-Select Model. 
				 */
				private void doCreateNewInput(InputSelectModel inputSelectModel)
				{
					InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
					
					InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(null);
					
					int rowNo = inputsTabelModel.addInputData(inputSelectEntryModel);
					
					inputSelectModel.setSelectedRowNo(rowNo);
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit-Model Value changed: Update Input-Select:
		
		inputEditController.getInputEditModel().getValueChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
						
					inputSelectController.doInputUpdated();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Timeline-Select: See below.
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		// Input-Edit-Model Value changed: Update Timeline-Select:
		
		inputEditController.getInputEditModel().getValueChangedNotifier().addModelPropertyChangedListener
	    (
	    	timelinesDrawPanelController.getTimelineGeneratorModelChangedListener()
	    );
	    //------------------------------------------------------------------------------------------
		// http://download.oracle.com/javase/tutorial/uiswing/misc/action.html
		this.appView.getPlayButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					playSound();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getPauseButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					pauseSound();
				}
		 	}
		);
	    //------------------------------------------------------------------------------------------
		this.appView.getStopButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					stopSound();
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * @param modulGeneratorTypeData
	 * 			is the edited modul.
	 */
	public void selectEditModule(ModulGeneratorTypeData modulGeneratorTypeData)
	{
		//==========================================================================================
		TimelinesDrawPanelModel timelinesDrawPanelModel = this.timelinesDrawPanelController.getTimelinesDrawPanelModel();
		
		timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(null);

		this.timelinesDrawPanelController.clearTimelineGenerators();
		
		//------------------------------------------------------------------------------------------
		this.modulesTreeController.getModulesTreeModel().setEditedModulGeneratorTypeData(modulGeneratorTypeData);
		
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

	public void playSound()
	{
		if (this.soundSchedulerLogic == null)
		{
			SoundData soundData = SwingMain.getSoundData();
			
			this.soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);
			
			this.soundSchedulerLogic.startThread();

			this.soundSchedulerLogic.startPlayback();
		}
		else
		{	
			this.soundSchedulerLogic.resumePlayback();
		}
	}

	public void stopSound()
	{
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.stopPlayback();
			
			this.soundSchedulerLogic.stopThread();
			
			this.soundSchedulerLogic = null;
		}
	}

	public void pauseSound()
	{
		if (this.soundSchedulerLogic != null)
		{
			this.soundSchedulerLogic.pausePlayback();
		}
	}

	/**
	 * @return
	 * 			the generator types.
	 */
	public List<GeneratorTypeData> retrieveGeneratorTypesForSelect()
	{
		SoundService soundService = SoundService.getInstance();
		
		List<GeneratorTypeData> generatorTypes = soundService.retrieveGeneratorTypes();

		return generatorTypes;
	}
	
	/**
	 * @return
	 * 			the edited Modul-Generator-Type Data.
	 */
	public ModulGeneratorTypeData getEditedModulGeneratorTypeData()
	{
		ModulesTreeModel modulesTreeModel = this.modulesTreeController.getModulesTreeModel();
		
		ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
		
		return editedModulGeneratorTypeData;
	}
}
