/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeSelect;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * <p>
 * 	Modul-Input-Type Tabel-Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeTabelModel
extends AbstractTableModel
{
	//**********************************************************************************************
	// Fields:

	private String columnNames[] = new String[]{"Name", "Value"};
	
	private List<ModulInputTypeSelectEntryModel> inputs = new Vector<ModulInputTypeSelectEntryModel>();
	
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
	 * @param modulInputTypeSelectEntryModel
	 * 			is the Modul-Input-Type Select-Entry Model
	 * @return
	 * 			the row number.
	 */
	public int addInputData(ModulInputTypeSelectEntryModel modulInputTypeSelectEntryModel)
	{
		this.inputs.add(modulInputTypeSelectEntryModel);
		
		int rowNo = this.inputs.size() - 1;
		
		this.fireTableRowsInserted(rowNo, rowNo);
		
		return rowNo;
	}
	
	@Override
	public int getRowCount() 
	{ 
		return this.inputs.size();
	}
	
	public ModulInputTypeSelectEntryModel getRow(int rowNo) 
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
		
		ModulInputTypeSelectEntryModel modulInputTypeSelectEntryModel = this.inputs.get(row);
		
		switch (col)
		{
			case 0:		// Type name.
			{
				value = modulInputTypeSelectEntryModel.getInputTypeName();
				break;
			}
			case 1:		// Label.
			{
				value = modulInputTypeSelectEntryModel.getInputTypeLabel();
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
