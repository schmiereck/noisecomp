/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesAddListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesChangePositionsListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesRemoveListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesUpdateListenerInterface;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.InputEntryTargetModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.InputPosEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

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

	private final SoundSourceLogic soundSourceLogic;

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
								 final SoundSourceLogic soundSourceLogic,
	                             final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                             final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.soundSourceLogic = soundSourceLogic;
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
							appController.retrieveGeneratorOfEditedModule(timelineSelectEntryModel.getName());
					}
					else
					{
						generator = null;
					}

					inputSelectModel.clearInputs();
					
					if (generator != null)
					{
						GeneratorTypeInfoData generatorTypeInfoData = generator.getGeneratorTypeData();
						
						Vector<InputData> inputs = generator.getInputs();
						
						if (inputs != null)
						{
							InputTypesData inputTypesData = generatorTypeInfoData.getInputTypesData();
							
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
						
						//int rowNo = 
							inputsTabelModel.addInputData(entryPos,
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
//						InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();

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
							final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
							
							InputSelectEntryModel selectedRow = inputSelectModel.getSelectedRow();
							
							selectedRow.updateInputData();
							
							inputsTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		// ChangePositions Input-Select:
		{
			InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesChangePositionsNotifier().addInputEntriesChangePositionsListeners
			(
			 	new InputEntriesChangePositionsListenerInterface()
				{
					@Override
					public void notifyChangePositions(final InputEntryModel selectedInputEntryModel, 
					                                  final InputEntryModel targetInputEntryModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						InputSelectEntryModel selEntryModel = inputSelectModel.getSelectedRow();
						InputData selInputData = selEntryModel.getInputData();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputData selectedInputData = selectedInputEntryModel.getInputData();
						final InputData targetInputData = targetInputEntryModel.getInputData();
						
						final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						inputsTabelModel.changePositions(selectedInputData,
						                                 targetInputData);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						if (selInputData == selectedInputData)
						{
							Integer selectedRowNo;
							
							selectedRowNo = inputsTabelModel.searchInputSelectEntryPos(selectedInputData);
							
							inputSelectModel.setSelectedRowNo(selectedRowNo);
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						inputSelectView.repaint();
						
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
	 * Do Remove Selected Input-Entry.
	 * 
	 */
	public void doRemoveSelectedInputEntry()
	{
		//==========================================================================================
		final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
		
		//==========================================================================================
		Integer selectedRowNo = this.inputSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final InputEntryModel removedInputEntry = 
				inputEntriesModel.removeInputEntry(selectedRowNo);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final InputEntryGroupModel removeInputEntryGroupModel = removedInputEntry.getInputEntryGroupModel();
			
			final List<InputPosEntriesModel> groupInputPosEntries = inputPosEntriesModel.getInputPosEntries();
			
			//------------------------------------------------------------------------------------------
			outerloop:
			{
				for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
				{
					//--------------------------------------------------------------------------------------
					final InputEntryGroupModel inputEntryGroupModel = groupInputPosEntriesModel.getInputEntryGroupModel();
					
					if (inputEntryGroupModel == removeInputEntryGroupModel)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Search and remove Pos-Entry of group.
						
						final List<InputPosEntriesModel> group2InputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
						
						for (InputPosEntriesModel inputPos2EntriesModel : group2InputPosEntries)
						{
							final InputEntryModel input2EntryModel = inputPos2EntriesModel.getInputEntryModel();
							
							if (input2EntryModel == removedInputEntry)
							{
								groupInputPosEntriesModel.removeGroupInputPosEntry(inputPos2EntriesModel);
								
								break outerloop;
							}
						}
					}
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.appModelChangedObserver.notifyAppModelChanged();
		}
		//==========================================================================================
	}

	/**
	 * @param newInputTypeData
	 * 			is the inputType.
	 */
	public void doCreateNewInput(final SoundSourceData soundSourceData, final InputTypeData newInputTypeData)
	{
		//==========================================================================================
		final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
		
		//==========================================================================================
		InputEntryModel retInputEntryModel = null;
		
		InputEntryGroupModel newInputEntryGroupModel = inputEntriesModel.searchInputEntryGroup(newInputTypeData);
		
//		InputEntryModel inputEntryModel = new InputEntryModel(inputEntryGroupModel,
//		                                                      null);
		
		//------------------------------------------------------------------------------------------
		outerloop:
		{
			final List<InputPosEntriesModel> groupInputPosEntries = inputPosEntriesModel.getInputPosEntries();
			
			for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
			{
				final InputEntryGroupModel inputEntryGroupModel = groupInputPosEntriesModel.getInputEntryGroupModel();
				
				if (inputEntryGroupModel == newInputEntryGroupModel)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Search and update Add-Pos-Entry of group.
					
					final List<InputPosEntriesModel> group2InputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
					
					for (InputPosEntriesModel inputPos2EntriesModel : group2InputPosEntries)
					{
						final InputEntryModel input2EntryModel = inputPos2EntriesModel.getInputEntryModel();
						final InputData input2Data = input2EntryModel.getInputData();
						
	//					if (input2EntryModel == newInputEntryModel)
						if (input2Data == null)
						{
	//						inputPos2EntriesModel.setInputEntryModel(newInputEntryModel);
							
							this.updateAddPosEntry(soundSourceData, newInputTypeData,
							                       input2EntryModel);
							
							retInputEntryModel = input2EntryModel;
							
							break outerloop;
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
			}
		}
		//------------------------------------------------------------------------------------------
		inputEntriesModel.addInputEntry(newInputTypeData,
		                                retInputEntryModel);
		
		//------------------------------------------------------------------------------------------
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}

	private void updateAddPosEntry(final SoundSourceData soundSourceData,
								   final InputTypeData newInputTypeData,
								   final InputEntryModel input2EntryModel)
	{
		//==========================================================================================
		//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
		
		//==========================================================================================
		InputEntryTargetModel inputEntryTargetModel = this.selectedTimelineModel.getInputEntryTargetModel();
		
		// Target defined (Not Update-Button)?
		if (inputEntryTargetModel != null)
		{
			TimelineSelectEntryModel inputTargetTimelineSelectEntryModel = inputEntryTargetModel.getTargetTimelineSelectEntryModel();
			Timeline inputTargetTimeline = inputTargetTimelineSelectEntryModel.getTimeline();
			
			Timeline newTimeline = selectedTimeline;
			Timeline inputTimeline = inputTargetTimeline;
			
			InputData inputData = 
				timelineManagerLogic.addGeneratorInput(soundSourceData, newTimeline,
				                                       inputTimeline, 
				                                       newInputTypeData, 
				                                       null, 
				                                       null, 
				                                       null);
		
			input2EntryModel.setInputData(inputData);
		}
	}

}
