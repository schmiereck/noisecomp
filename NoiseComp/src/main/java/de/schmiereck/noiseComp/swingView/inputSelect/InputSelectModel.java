/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.timeline.Timeline;


/**
 * <p>
 * 	Input-Select Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectModel {
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final InputsTabelModel inputsTabelModel = new InputsTabelModel();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final RemoveInputSelectEntryNotifier removeInputSelectEntryNotifier = new RemoveInputSelectEntryNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final UpdateInputSelectEntryNotifier updateInputSelectEntryNotifier = new UpdateInputSelectEntryNotifier();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Selected Row.
	 */
	private Integer selectedRowNo = null;
	
	/**
	 * {@link #selectedRowNo} changed notifier.
	 */
	private final ModelPropertyChangedNotifier selectedRowNoChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public InputSelectModel() {
		//==========================================================================================
//		this.inputsTabelModel.addTableModelListener
//		(
//		 	new TableModelListener()
//		 	{
//				@Override
//				public void tableChanged(TableModelEvent e)
//				{
//				}
//		 	}
//		);
		//------------------------------------------------------------------------------------------

		//==========================================================================================
	}

	/**
	 * Clear Inputs.
	 */
	public void clearInputs() {
		this.inputsTabelModel.clearInputs();
	}

	/**
	 * @param entryPos
	 * 			is the entry pos.
	 * @param inputSelectEntryModel
	 * 			is the Input-Select-Entry Model.
	 */
	public void addInputData(int entryPos,
	                         InputSelectEntryModel inputSelectEntryModel) {
		this.inputsTabelModel.addInputData(entryPos,
		                                   inputSelectEntryModel);
	}

	/**
	 * @return 
	 * 			returns the {@link #inputsTabelModel}.
	 */
	public InputsTabelModel getInputsTabelModel() {
		return this.inputsTabelModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedRowNo}.
	 */
	public Integer getSelectedRowNo() {
		return this.selectedRowNo;
	}

	/**
	 * @param selectedRowNo 
	 * 			to set {@link #selectedRowNo}.
	 */
	public void setSelectedRowNo(Integer selectedRowNo) {
		//==========================================================================================
		if (!CompareUtils.compareWithNull(this.selectedRowNo, selectedRowNo)) {
			this.selectedRowNo = selectedRowNo;
			
			// Notify listeners.
			this.selectedRowNoChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedRowNoChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getSelectedRowNoChangedNotifier() {
		return this.selectedRowNoChangedNotifier;
	}

	/**
	 * @return
	 * 			the selected Input Select-Entry Model.
	 */
	public InputSelectEntryModel getSelectedRow() {
		//==========================================================================================
		InputSelectEntryModel selectEntryModel;
		
		Integer selectedRowNo = this.getSelectedRowNo();
		
		if (selectedRowNo != null) {
			InputsTabelModel tabelModel = this.getInputsTabelModel();
			
			selectEntryModel = tabelModel.getRow(selectedRowNo);
		} else {
			selectEntryModel = null;
		}
		//==========================================================================================
		return selectEntryModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #removeInputSelectEntryNotifier}.
	 */
	public RemoveInputSelectEntryNotifier getRemoveInputSelectEntryNotifier() {
		return this.removeInputSelectEntryNotifier;
	}

	/**
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 * @param inputSelectEntryModel
	 * 			is the InputSelectEntryModel.
	 * @param rowNo
	 * 			is the row number.
	 */
	public void removeInputSelectEntry(Timeline selectedTimeline,
	                                   InputSelectEntryModel inputSelectEntryModel,
	                                   int rowNo) {
		//==========================================================================================
		this.inputsTabelModel.removeInput(rowNo);
		
		this.removeInputSelectEntryNotifier.notifyRemoveInputSelectEntryListeners(selectedTimeline,
		                                                                          this, 
		                                                                          inputSelectEntryModel);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #updateInputSelectEntryNotifier}.
	 */
	public UpdateInputSelectEntryNotifier getUpdateInputSelectEntryNotifier() {
		return this.updateInputSelectEntryNotifier;
	}

}
