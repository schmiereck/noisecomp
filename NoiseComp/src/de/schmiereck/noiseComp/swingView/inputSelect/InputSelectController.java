/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.InputTypesData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesAddListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesRemoveListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesUpdateListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Input-Select Controller.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Select Model.
	 */
	private final InputSelectModel inputSelectModel;
	
	/**
	 * Input-Select View.
	 */
	private final InputSelectView inputSelectView;
	
	private AppModelChangedObserver appModelChangedObserver;
	
	private final SelectedTimelineModel selectedTimelineModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the App Controller.
	 * @param timelinesDrawPanelModel 
	 * 			is the Timeline Draw-Panel Model.
	 */
	public InputSelectController(final AppController appController,
	                             final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                             final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.inputSelectModel = new InputSelectModel();
		this.inputSelectView = new InputSelectView(this.inputSelectModel);
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
		this.selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
		{
			TableColumnModel columnModel = this.inputSelectView.getColumnModel();
	
			TableColumn nameCol = columnModel.getColumn(0); 
			nameCol.setPreferredWidth(120); 
			
			TableColumn valueCol = columnModel.getColumn(1); 
			valueCol.setPreferredWidth(100); 
		}		
		//------------------------------------------------------------------------------------------
		// Selected Timeline changed -> update Input-Select-Model:
		
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Generator generator;
					
					TimelineSelectEntryModel timelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					if (timelineSelectEntryModel != null)
					{
						generator = 
							appController.retrieveGeneratorOfEditedModul(timelineSelectEntryModel.getName());
					}
					else
					{
						generator = null;
					}

					inputSelectModel.clearInputs();
					
					if (generator != null)
					{
						GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
						
						Vector<InputData> inputs = generator.getInputs();
						
						InputTypesData inputTypesData = generatorTypeData.getInputTypesData();
						
						int entryPos = 0;
						
						Iterator<InputTypeData> inputTypesIterator = inputTypesData.getInputTypesIterator();
						
						while (inputTypesIterator.hasNext())
						{
							InputTypeData inputTypeData = (InputTypeData)inputTypesIterator.next();
							
							for (InputData inputData : inputs)
							{
								InputTypeData inputTypeData2 = inputData.getInputTypeData();
								
								if (inputTypeData == inputTypeData2)
								{
									InputSelectEntryModel inputSelectEntryModel = 
										new InputSelectEntryModel(inputData);
									
									inputSelectModel.addInputData(entryPos,
									                              inputSelectEntryModel);
									
									entryPos++;
								}
							}
							
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		// Input-Select-Model Selected-Row changed -> update Input-Select-View:
		
		this.inputSelectModel.getSelectedRowNoChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
//					ListSelectionModel selectionModel = inputSelectView.getSelectionModel();
//					
//					if (selectedRowNo != null)
//					{
//						selectionModel.setSelectionInterval(selectedRowNo, selectedRowNo);
//					}
//					else
//					{
//						selectionModel.clearSelection();
//					}
					InputEntryModel selectedInputEntry;
					
					if (selectedRowNo != null)
					{
						InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
						
						selectedInputEntry = inputEntriesModel.searchInputEntry(selectedRowNo);
					}
					else
					{
						selectedInputEntry = null;
					}
					
					selectedTimelineModel.setSelectedInputEntry(selectedInputEntry);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		{
			selectedTimelineModel.getSelectedInputEntryChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
				{
					@Override
					public void notifyModelPropertyChanged()
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						ListSelectionModel selectionModel = inputSelectView.getSelectionModel();
						
						InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();
						
						if (selectedInputEntry != null)
						{
							InputData inputData = selectedInputEntry.getInputData();
							
							InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
							
							for (int rowNo = 0; rowNo < inputsTabelModel.getRowCount(); rowNo++)
							{
								InputSelectEntryModel entryModel = inputsTabelModel.getRow(rowNo);
								
								InputData rowInputData = entryModel.getInputData();
								
								if (inputData == rowInputData)
								{
//									inputSelectModel.setSelectedRowNo(rowNo);
									selectionModel.setSelectionInterval(rowNo, rowNo);
									break;
								}
							}
						}
						else
						{
							selectionModel.clearSelection();
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesAddNotifier().addInputEntriesAddListeners
			(
			 	new InputEntriesAddListenerInterface()
				{
					@Override
					public void notifyAddInputEntry(int entryPos,
					                                InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						InputData inputData = inputEntryModel.getInputData();
						
						InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(inputData);
						
						int rowNo = inputsTabelModel.addInputData(entryPos,
						                                          inputSelectEntryModel);
						
						inputSelectModel.setSelectedRowNo(entryPos);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesRemoveNotifier().addInputEntriesRemoveListeners
			(
			 	new InputEntriesRemoveListenerInterface()
				{
					@Override
					public void notifyRemoveInputEntry(int inputNo,
					                                   InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();

						TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						
						Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(inputNo);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//						// Update Generator-Input-Data:
//						
//						InputData inputData = inputSelectEntryModel.getInputData();
//						
//						if (inputData != null)
//						{
//							Generator ownerGenerator = inputData.getOwnerGenerator();
//							
//							ownerGenerator.removeInput(inputData);
//						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Update Input-Select-Model:
						
						inputSelectModel.removeInputSelectEntry(selectedTimeline,
						                                        inputSelectEntryModel,
						                                        inputNo);

						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						appModelChangedObserver.notifyAppModelChanged();

						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		// Update Input-Select:
		{
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesUpdateNotifier().addInputEntriesUpdateListeners
			(
			 	new InputEntriesUpdateListenerInterface()
				{
					@Override
					public void notifyUpdateInputEntry(InputEntryModel inputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
						
						if (selectedRowNo != null)
						{
							InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
							
							InputSelectEntryModel selectedRow = inputSelectModel.getSelectedRow();
							
							selectedRow.updateInputData();
							
							inputsTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputSelectModel}.
	 */
	public InputSelectModel getInputSelectModel()
	{
		return this.inputSelectModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputSelectView}.
	 */
	public InputSelectView getInputSelectView()
	{
		return this.inputSelectView;
	}

	/**
	 * @param inputEntryModel
	 * 			is the input entry.
	 * @param inputData
	 * 			is the inputData.
	 */
	public void doInputUpdated(final InputEntryModel inputEntryModel,
	                           final InputData inputData)
	{
		//==========================================================================================
		InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		
		inputEntriesModel.updateInputEntry(inputEntryModel, 
		                                   inputData);
		
		//==========================================================================================
	}
	
	/**
	 * Do Remove Selected Entry.
	 * 
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 */
	public void doRemoveSelectedEntry(final Timeline selectedTimeline)
	{
		//==========================================================================================
		Integer selectedRowNo = this.inputSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.removeInputEntry(selectedRowNo);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.appModelChangedObserver.notifyAppModelChanged();
		}
		//==========================================================================================
	}

	/**
	 * @param inputTypeData
	 * 			is the inputType.
	 */
	public void doCreateNewInput(final InputTypeData inputTypeData)
	{
		//==========================================================================================
		InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		
		InputEntryModel inputEntryModel = new InputEntryModel(null);
		
		inputEntriesModel.addInputEntry(inputTypeData,
		                                inputEntryModel);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}

}
