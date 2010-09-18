/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;

/**
 * <p>
 * 	Inputs Tabel Model.
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

	private String columnNames[] = new String[]{"Name", "Value"};
	
	private List<InputSelectEntryModel> inputs = new Vector<InputSelectEntryModel>();
	
	//**********************************************************************************************
	// Functions:

	@Override
	public int getColumnCount() 
	{ 
		return 2; 
	}
	
	public void clearInputs() 
	{ 
		this.inputs.clear();
		
		this.fireTableDataChanged();
	}
	
	public void removeInput(int rowNo) 
	{ 
		this.inputs.remove(rowNo);
		
		this.fireTableRowsDeleted(rowNo, rowNo);
	}

	/**
	 * @param inputSelectEntryModel
	 * 			is the Input-Select-Entry Model.
	 * @return
	 * 			the row number.
	 */
	public int addInputData(InputSelectEntryModel inputSelectEntryModel)
	{
		this.inputs.add(inputSelectEntryModel);
		
		int rowNo = this.inputs.size() - 1;
		
		this.fireTableRowsInserted(rowNo, rowNo);
		
		return rowNo;
	}
	
	@Override
	public int getRowCount() 
	{ 
		return this.inputs.size();
	}
	
	public InputSelectEntryModel getRow(int rowNo) 
	{ 
		return this.inputs.get(rowNo);
	}
	
	@Override
	public String getColumnName(int column)
	{
		return this.columnNames[column];
	}
	
	@Override
	public Object getValueAt(int row, int col) 
	{
		String value;
		
		InputSelectEntryModel inputSelectEntryModel = this.inputs.get(row);
		
		switch (col)
		{
			case 0:		// Type name.
			{
				value = inputSelectEntryModel.getInputTypeName();
				break;
			}
			case 1:		// Label.
			{
				value = inputSelectEntryModel.getInputLabel();
//				value = this.makeInputLabel(inputData);
				break;
			}
			default:
			{
				throw new RuntimeException("Unknown column \"" + col + "\".");
			}
		}
		
		return value; //new Integer(row * col); 
	}
}
