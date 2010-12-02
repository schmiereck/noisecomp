/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel;
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
		{
			TableColumnModel columnModel = this.inputSelectView.getColumnModel();
	
			TableColumn nameCol = columnModel.getColumn(0); 
			nameCol.setPreferredWidth(120); 
			
			TableColumn valueCol = columnModel.getColumn(1); 
			valueCol.setPreferredWidth(100); 
		}		
		this.appModelChangedObserver = appModelChangedObserver;
		
		//------------------------------------------------------------------------------------------
		// Selected Timeline changed -> update Input-Select-Model:
		
		timelinesDrawPanelModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					Iterator<InputData> inputsIterator;
					
					TimelineSelectEntryModel timelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
					
					if (timelineSelectEntryModel != null)
					{
						Generator generator = 
							appController.retrieveGeneratorOfEditedModul(timelineSelectEntryModel.getName());
						
						if (generator != null)
						{
							inputsIterator = generator.getInputsIterator();
						}
						else
						{
							inputsIterator = null;
						}
					}
					else
					{
						inputsIterator = null;
					}

					inputSelectModel.clearInputs();
					
					if (inputsIterator != null)
					{
						while (inputsIterator.hasNext())
						{
							InputData inputData = (InputData)inputsIterator.next();
							
							InputSelectEntryModel inputSelectEntryModel = 
								new InputSelectEntryModel(inputData);
							
							inputSelectModel.addInputData(inputSelectEntryModel);
						}
					}
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
					Integer selectedRowNo = inputSelectModel.getSelectedRowNo();
					
					ListSelectionModel selectionModel = inputSelectView.getSelectionModel();
					
					if (selectedRowNo != null)
					{
						selectionModel.setSelectionInterval(selectedRowNo, selectedRowNo);
					}
					else
					{
						selectionModel.clearSelection();
					}
				}
		 	}
		);
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
	 */
	public void doInputUpdated()
	{
		Integer selectedRowNo = this.inputSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			InputsTabelModel inputsTabelModel = this.inputSelectModel.getInputsTabelModel();
			
			InputSelectEntryModel selectedRow = this.inputSelectModel.getSelectedRow();
			
			selectedRow.updateInputData();
			
			inputsTabelModel.fireTableRowsUpdated(selectedRowNo, selectedRowNo);
		}
	}
	
	/**
	 * Do Remove Selected Entry.
	 * 
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 */
	public void doRemoveSelectedEntry(Timeline selectedTimeline)
	{
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Integer selectedRowNo = this.inputSelectModel.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			InputsTabelModel inputsTabelModel = this.inputSelectModel.getInputsTabelModel();
		
			InputSelectEntryModel inputSelectEntryModel = inputsTabelModel.getRow(selectedRowNo);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//			// Update Generator-Input-Data:
//			
//			InputData inputData = inputSelectEntryModel.getInputData();
//			
//			if (inputData != null)
//			{
//				Generator ownerGenerator = inputData.getOwnerGenerator();
//				
//				ownerGenerator.removeInput(inputData);
//			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Update Input-Select-Model:
			
			this.inputSelectModel.removeInputSelectEntry(selectedTimeline,
			                                             inputSelectEntryModel,
			                                             selectedRowNo);

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.appModelChangedObserver.notifyAppModelChanged();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
	}

	/**
	 */
	public void doCreateNewInput()
	{
		InputsTabelModel inputsTabelModel = this.inputSelectModel.getInputsTabelModel();
		
		InputSelectEntryModel inputSelectEntryModel = new InputSelectEntryModel(null);
		
		int rowNo = inputsTabelModel.addInputData(inputSelectEntryModel);
		
		this.inputSelectModel.setSelectedRowNo(rowNo);
	}

}
