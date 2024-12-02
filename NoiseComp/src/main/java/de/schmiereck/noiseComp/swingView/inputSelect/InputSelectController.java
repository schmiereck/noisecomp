/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
public class InputSelectController {
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
	                             final AppModelChangedObserver appModelChangedObserver) {
		//==========================================================================================
		this.soundSourceLogic = soundSourceLogic;
		this.inputSelectModel = new InputSelectModel();
		this.inputSelectView = new InputSelectView(this.inputSelectModel);
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
		this.selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
		{
			final TableColumnModel columnModel = this.inputSelectView.getColumnModel();

			final TableColumn nameCol = columnModel.getColumn(0);
			nameCol.setPreferredWidth(120);

			final TableColumn valueCol = columnModel.getColumn(1);
			valueCol.setPreferredWidth(100); 
		}		
		//------------------------------------------------------------------------------------------
		// Selected Timeline changed -> update Input-Select-Model:
		
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final Generator generator;

					final TimelineSelectEntryModel timelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
					
					if (timelineSelectEntryModel != null) {
						generator = appController.retrieveGeneratorOfEditedModule(timelineSelectEntryModel.getName());
					} else {
						generator = null;
					}

					inputSelectModel.clearInputs();
					
					if (generator != null) {
						final GeneratorTypeInfoData generatorTypeInfoData = generator.getGeneratorTypeData();

						final Vector<InputData> inputs = generator.getInputs();
						
						if (inputs != null) {
							final InputTypesData inputTypesData = generatorTypeInfoData.getInputTypesData();
							
							int entryPos = 0;

							final Iterator<InputTypeData> inputTypesIterator = inputTypesData.getInputTypesIterator();
							
							while (inputTypesIterator.hasNext()) {
								final InputTypeData inputTypeData = (InputTypeData)inputTypesIterator.next();
								
								for (final InputData inputData : inputs) {
									InputTypeData inputTypeData2 = inputData.getInputTypeData();
									
									if (inputTypeData == inputTypeData2) {
										final InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(inputData);
										
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
		
		this.inputSelectModel.getSelectedRowNoChangedNotifier().addModelPropertyChangedListener (
		 	new ModelPropertyChangedListener() {
				@Override
				public void notifyModelPropertyChanged() {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					final Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
//					ListSelectionModel selectionModel = inputSelectView.getSelectionModel();
//					
//					if (selectedRowNo != null) {
//						selectionModel.setSelectionInterval(selectedRowNo, selectedRowNo);
//					} else {
//						selectionModel.clearSelection();
//					}
					final InputEntryModel selectedInputEntry;
					
					if (selectedRowNo != null) {
						final InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
						
						selectedInputEntry = inputEntriesModel.searchInputEntry(selectedRowNo);
					} else {
						selectedInputEntry = null;
					}
					// TODO YYY2 selectedTimelineModel.setSelectedInputEntry(selectedInputEntry);
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		{
			final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesAddNotifier().addInputEntriesAddListeners (
			 	new InputEntriesAddListenerInterface() {
					@Override
					public void notifyAddInputEntry(final int entryPos,
													final InputEntryModel inputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						final InputData inputData = inputEntryModel.getInputData();
						final InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(inputData);
						
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
			final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesRemoveNotifier().addInputEntriesRemoveListeners (
			 	new InputEntriesRemoveListenerInterface() {
					@Override
					public void notifyRemoveInputEntry(final int inputNo,
													   final InputEntryModel inputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//						InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();

						final TimelineSelectEntryModel selectedTimelineSelectEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
						final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						final InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(inputNo);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//						// Update Generator-Input-Data:
//						
//						InputData inputData = inputSelectEntryModel.getInputData();
//						
//						if (inputData != null) {
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
			final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesUpdateNotifier().addInputEntriesUpdateListeners(
			 	new InputEntriesUpdateListenerInterface() {
					@Override
					public void notifyUpdateInputEntry(final InputEntryModel inputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
						
						if (selectedRowNo != null) {
							final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();

							final InputSelectEntryModel selectedRow = inputSelectModel.getSelectedRow();

							if (Objects.nonNull(selectedRow)) {
								selectedRow.updateInputData();
								inputsTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
							}
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
			);
		}
		//------------------------------------------------------------------------------------------
		// ChangePositions Input-Select:
		{
			final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
			
			inputEntriesModel.getInputEntriesChangePositionsNotifier().addInputEntriesChangePositionsListeners(
			 	new InputEntriesChangePositionsListenerInterface() {
					@Override
					public void notifyChangePositions(final InputEntryModel selectedInputEntryModel, 
					                                  final InputEntryModel targetInputEntryModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputSelectEntryModel selEntryModel = inputSelectModel.getSelectedRow();
						final InputData selInputData = selEntryModel.getInputData();
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final InputData selectedInputData = selectedInputEntryModel.getInputData();
						final InputData targetInputData = targetInputEntryModel.getInputData();
						
						final InputsTabelModel inputsTabelModel = inputSelectModel.getInputsTabelModel();
						
						inputsTabelModel.changePositions(selectedInputData,
						                                 targetInputData);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						if (selInputData == selectedInputData) {
							final Integer selectedRowNo = inputsTabelModel.searchInputSelectEntryPos(selectedInputData);
							
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

	public void changeSelectedInputData(final InputData inputData) {
		if (Objects.nonNull(inputData)) {
			final InputsTabelModel inputsTabelModel = this.inputSelectModel.getInputsTabelModel();

			for (int rowNo = 0; rowNo < inputsTabelModel.getRowCount(); rowNo++) {
				final InputSelectEntryModel entryModel = inputsTabelModel.getRow(rowNo);
				final InputData rowInputData = entryModel.getInputData();

				if (inputData == rowInputData) {
					//inputSelectModel.setSelectedRowNo(rowNo);
					this.changeSelectedTableRow(rowNo);
					break;
				}
			}
		} else {
			this.clearSelectedTableRow();
		}
	}

	private void changeSelectedTableRow(final int rowNo) {
		final ListSelectionModel selectionModel = this.inputSelectView.getSelectionModel();

		selectionModel.setSelectionInterval(rowNo, rowNo);
	}

	private void clearSelectedTableRow() {
		final ListSelectionModel selectionModel = this.inputSelectView.getSelectionModel();

		selectionModel.clearSelection();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputSelectModel}.
	 */
	public InputSelectModel getInputSelectModel() {
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
	                           final InputData inputData) {
		//==========================================================================================
		final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		
		inputEntriesModel.updateInputEntry(inputEntryModel, 
		                                   inputData);
		
		//==========================================================================================
	}
	
	/**
	 * Do Remove Selected Input-Entry.
	 * 
	 */
	public void doRemoveSelectedInputEntry() {
		//==========================================================================================
		final InputEntriesModel inputEntriesModel = this.selectedTimelineModel.getInputEntriesModel();
		final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
		
		//==========================================================================================
		final Integer selectedRowNo = this.inputSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final InputEntryModel removedInputEntry = inputEntriesModel.removeInputEntry(selectedRowNo);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final InputEntryGroupModel removeInputEntryGroupModel = removedInputEntry.getInputEntryGroupModel();
			
			final List<InputPosEntriesModel> groupInputPosEntries = inputPosEntriesModel.getInputPosEntries();
			
			//------------------------------------------------------------------------------------------
			outerloop:
			{
				for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries) {
					//--------------------------------------------------------------------------------------
					final InputEntryGroupModel inputEntryGroupModel = groupInputPosEntriesModel.getInputEntryGroupModel();
					
					if (inputEntryGroupModel == removeInputEntryGroupModel) {
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Search and remove Pos-Entry of group.
						
						final List<InputPosEntriesModel> group2InputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
						
						for (final InputPosEntriesModel inputPos2EntriesModel : group2InputPosEntries) {
							final InputEntryModel input2EntryModel = inputPos2EntriesModel.getInputEntryModel();
							
							if (input2EntryModel == removedInputEntry) {
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
	public void doCreateNewInput(final SoundSourceData soundSourceData, final InputTypeData newInputTypeData) {
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
			
			for (final InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries) {
				final InputEntryGroupModel inputEntryGroupModel = groupInputPosEntriesModel.getInputEntryGroupModel();
				
				if (inputEntryGroupModel == newInputEntryGroupModel) {
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Search and update Add-Pos-Entry of group.
					
					final List<InputPosEntriesModel> group2InputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
					
					for (final InputPosEntriesModel inputPos2EntriesModel : group2InputPosEntries) {
						final InputEntryModel input2EntryModel = inputPos2EntriesModel.getInputEntryModel();
						final InputData input2Data = input2EntryModel.getInputData();
						
	//					if (input2EntryModel == newInputEntryModel)
						if (input2Data == null) {
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
		final int rowNum =
				inputEntriesModel.addInputEntry(newInputTypeData, retInputEntryModel);

		this.changeSelectedTableRow(rowNum);

		//------------------------------------------------------------------------------------------
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}

	private void updateAddPosEntry(final SoundSourceData soundSourceData,
								   final InputTypeData newInputTypeData,
								   final InputEntryModel input2EntryModel) {
		//==========================================================================================
		//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();

		final TimelineManagerLogic timelineManagerLogic = this.soundSourceLogic.getTimelineManagerLogic();
		
		//==========================================================================================
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = this.selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		final Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
		
		//==========================================================================================
		final InputEntryTargetModel inputEntryTargetModel = this.selectedTimelineModel.getInputEntryTargetModel();
		
		// Target defined (Not Update-Button)?
		if (Objects.nonNull(inputEntryTargetModel)) {
			final TimelineSelectEntryModel inputTargetTimelineSelectEntryModel = inputEntryTargetModel.getTargetTimelineSelectEntryModel();
			final Timeline inputTargetTimeline = inputTargetTimelineSelectEntryModel.getTimeline();

			final Timeline newTimeline = selectedTimeline;
			final Timeline inputTimeline = inputTargetTimeline;

			final InputData inputData =
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
