/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.swingView.InputUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.SwingMain;
import de.schmiereck.noiseComp.swingView.appModel.AppModel;
import de.schmiereck.noiseComp.swingView.appView.AppView;
import de.schmiereck.noiseComp.swingView.inputEdit.GeneratorSelectItem;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditController;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditModel;
import de.schmiereck.noiseComp.swingView.inputEdit.InputEditView;
import de.schmiereck.noiseComp.swingView.inputEdit.InputTypeSelectItem;
import de.schmiereck.noiseComp.swingView.inputEdit.ModulInputTypeSelectItem;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectController;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectEntryModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;
import de.schmiereck.noiseComp.swingView.inputSelect.InputsTabelModel;
import de.schmiereck.noiseComp.swingView.modulEdit.ModulEditController;
import de.schmiereck.noiseComp.swingView.modulInputs.ModulInputTypesController;
import de.schmiereck.noiseComp.swingView.modulsTree.DoEditModuleListener;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
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
		
		//------------------------------------------------------------------------------------------
		this.modulInputTypesController = new ModulInputTypesController(this);
		
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
		// Input-Edit Update-Button: Update Input-Edit-Model and Input-Data:
		
		inputEditController.getInputEditView().getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					InputEditModel inputEditModel = inputEditController.getInputEditModel();
					InputEditView inputEditView = inputEditController.getInputEditView();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputTypeSelectItem inputTypeSelectItem = (InputTypeSelectItem)inputEditView.getInputTypeComboBox().getSelectedItem();
					GeneratorSelectItem inputGeneratorSelectItem = (GeneratorSelectItem)inputEditView.getInputGeneratorComboBox().getSelectedItem();
					String valueStr = inputEditView.getValueTextField().getText();
					ModulInputTypeSelectItem modulInputTypeSelectItem = (ModulInputTypeSelectItem)inputEditView.getModulInputTypeComboBox().getSelectedItem();
						
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputTypeData inputTypeData = inputTypeSelectItem.getInputTypeData();
					Generator inputGenerator = inputGeneratorSelectItem.getGenerator();
					MultiValue multiValue = InputUtils.makeMultiValue(valueStr);
					InputTypeData modulInputTypeData = modulInputTypeSelectItem.getInputTypeData();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Input-Data:
					
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					// Input selected?
					if (selectedRowNo != null)
					{
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);
						
						InputData inputData = inputSelectEntryModel.getInputData();
						
						// Existing Input selected?
						if (inputData != null)
						{
							// Update selected Input:
							
							inputData.setInputGenerator(inputGenerator);
							inputData.setInputValue(multiValue.floatValue, multiValue.stringValue);
							inputData.setInputModulInputTypeData(modulInputTypeData);
						}
						else
						{
							// Insert new Input:
							
							Generator selectedGenerator = timelinesDrawPanelController.getSelectedTimelineGenerator();
							
							inputData = 
								selectedGenerator.addInputGenerator(inputGenerator, 
								                                    inputTypeData, 
								                                    multiValue.floatValue, multiValue.stringValue,
								                                    modulInputTypeData);
							
							inputSelectEntryModel.setInputData(inputData);
						}
					}
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Edit-Model:
					
					inputEditModel.setInputTypeData(inputTypeSelectItem.getInputTypeData());
					inputEditModel.setInputGenerator(inputGeneratorSelectItem.getGenerator());
					inputEditModel.setValue(valueStr);
					inputEditModel.setModulInputTypeData(modulInputTypeSelectItem.getInputTypeData());
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Edit Remove-Input-Button: Update Input-Select-Model and Generator-Input-Data:
		
		inputEditController.getInputEditView().getRemoveInputButton().addActionListener
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
		
		inputEditController.getInputEditView().getCreateNewInputButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
					
					InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(null);
					
					int rowNo = inputsTabelModel.addInputData(inputSelectEntryModel);
					
					inputSelectModel.setSelectedRowNo(rowNo);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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
					InputSelectModel inputSelectModel = inputSelectController.getInputSelectModel();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Update Input-Select:
						
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					if (selectedRowNo != null)
					{
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						InputSelectEntryModel selectedRow = inputSelectModel.getSelectedRow();
						
						selectedRow.updateInputData();
						
						inputsTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
					}
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
}
