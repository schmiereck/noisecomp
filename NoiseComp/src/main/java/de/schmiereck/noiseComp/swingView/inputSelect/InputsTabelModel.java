/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import de.schmiereck.noiseComp.generator.InputData;

/**
 * <p>
 * 	Inputs Tabel-Model.
 * </p>
 * 
 * @author smk
 * @version <p>14.09.2010:	created, smk</p>
 */
public class InputsTabelModel
extends AbstractTableModel
{
	//**********************************************************************************************
	// Fields:

	private String columnNames[] = new String[]{ "Name", "Value" };
	
	private List<InputSelectEntryModel> inputs = new Vector<>();
	
	//**********************************************************************************************
	// Functions:

	@Override
	public int getColumnCount() 
	{ 
		return 2; 
	}
	
	public void clearInputs() {
		//==========================================================================================
		this.inputs.clear();
		
		this.fireTableDataChanged();
		//==========================================================================================
	}
	
	public void removeInput(final int rowNo) {
		//==========================================================================================
		this.inputs.remove(rowNo);
		
		this.fireTableRowsDeleted(rowNo, rowNo);
		//==========================================================================================
	}

	/**
	 * @param entryPos
	 * 			is the entry pos.
	 * @param inputSelectEntryModel
	 * 			is the Input-Select-Entry Model.
	 * @return
	 * 			the row number.
	 */
	public int addInputData(final int entryPos,
							final InputSelectEntryModel inputSelectEntryModel) {
		//==========================================================================================
		this.inputs.add(entryPos, inputSelectEntryModel);
		
//		int rowNo = this.inputs.size() - 1;
		
		this.fireTableRowsInserted(entryPos, entryPos);
		
		//==========================================================================================
		return entryPos;
	}
	
	@Override
	public int getRowCount()  {
		return this.inputs.size();
	}
	
	public InputSelectEntryModel getRow(final int rowNo) {
		return this.inputs.get(rowNo);
	}
	
	@Override
	public String getColumnName(final int column) {
		return this.columnNames[column];
	}
	
	@Override
	public Object getValueAt(final int row, final int col)  {
		//==========================================================================================
		final String value;

		final InputSelectEntryModel inputSelectEntryModel = this.inputs.get(row);
		
		switch (col) {
			case 0:	 {
				value = inputSelectEntryModel.getInputTypeName();
				break;
			}
			case 1:	 {
				value = inputSelectEntryModel.getInputLabel();
//				value = this.makeInputLabel(inputData);
				break;
			}
			default: {
				throw new RuntimeException("Unknown column \"" + col + "\".");
			}
		}
		//==========================================================================================
		return value; //new Integer(row * col); 
	}

	/**
	 * @param searchedInputData
	 * 			is the searched Input-Data.
	 * @return
	 * 			the searched InputSelectEntry.<br/>
	 * 			<code>null</code> if no entry found.
	 */
	public InputSelectEntryModel searchInputSelectEntry(final InputData searchedInputData) {
		//==========================================================================================
		InputSelectEntryModel retInputSelectEntryModel = null;
		
		for (final InputSelectEntryModel inputSelectEntryModel : this.inputs) {
			final InputData inputData = inputSelectEntryModel.getInputData();
			
			if (searchedInputData == inputData) {
				retInputSelectEntryModel = inputSelectEntryModel;
				break;
			}
		}
		//==========================================================================================
		return retInputSelectEntryModel;
	}

	/**
	 * @param selectedInputData
	 * 			is the selected InputData.
	 * @param targetInputData
	 * 			is the target InputData.
	 */
	public void changePositions(final InputData selectedInputData,
								final InputData targetInputData) {
		//==========================================================================================
		final InputSelectEntryModel selectedInputSelectEntryModel = this.searchInputSelectEntry(selectedInputData);
		final InputSelectEntryModel targetInputSelectEntryModel = this.searchInputSelectEntry(targetInputData);

		final int selectedInputPos = this.inputs.indexOf(selectedInputSelectEntryModel);
		final int targetInputPos = this.inputs.indexOf(targetInputSelectEntryModel);
		
		this.inputs.set(selectedInputPos, targetInputSelectEntryModel);
		this.inputs.set(targetInputPos, selectedInputSelectEntryModel);

		this.fireTableRowsUpdated(selectedInputPos, selectedInputPos);
		this.fireTableRowsUpdated(targetInputPos, targetInputPos);
		
		//==========================================================================================
	}

	public Integer searchInputSelectEntryPos(final InputData searchedInputData) {
		//==========================================================================================
		Integer retInputSelectEntryPos = null;

		for (int entryPos = 0; entryPos < this.inputs.size(); entryPos++) {
			final InputSelectEntryModel inputSelectEntryModel = this.inputs.get(entryPos);

			final InputData inputData = inputSelectEntryModel.getInputData();
			
			if (searchedInputData == inputData) {
				retInputSelectEntryPos = entryPos;
				break;
			}
		}
		//==========================================================================================
		return retInputSelectEntryPos;
	}
}
