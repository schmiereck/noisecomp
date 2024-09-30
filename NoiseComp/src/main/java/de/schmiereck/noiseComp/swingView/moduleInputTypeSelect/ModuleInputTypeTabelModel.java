/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.moduleInputTypeSelect;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

/**
 * <p>
 * 	ModuleInput-Type Tabel-Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeTabelModel
extends AbstractTableModel
{
	//**********************************************************************************************
	// Fields:

	private String columnNames[] = new String[]{"Name", "Value"};
	
	private List<ModuleInputTypeSelectEntryModel> inputs = new Vector<ModuleInputTypeSelectEntryModel>();
	
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
	 * @param moduleInputTypeSelectEntryModel
	 * 			is the ModuleInput-Type Select-Entry Model
	 * @return
	 * 			the row number.
	 */
	public int addInputData(ModuleInputTypeSelectEntryModel moduleInputTypeSelectEntryModel)
	{
		this.inputs.add(moduleInputTypeSelectEntryModel);
		
		int rowNo = this.inputs.size() - 1;
		
		this.fireTableRowsInserted(rowNo, rowNo);
		
		return rowNo;
	}
	
	@Override
	public int getRowCount() 
	{ 
		return this.inputs.size();
	}
	
	public ModuleInputTypeSelectEntryModel getRow(int rowNo) 
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
		String valueStr;
		
		ModuleInputTypeSelectEntryModel moduleInputTypeSelectEntryModel = this.inputs.get(row);
		
		switch (col)
		{
			case 0:		// Type name.
			{
				valueStr = OutputUtils.makeStringText(moduleInputTypeSelectEntryModel.getInputTypeName());
				break;
			}
			case 1:		// Label.
			{
//				value = this.makeInputLabel(inputData);
//				value = moduleInputTypeSelectEntryModel.getInputTypeLabel();
				Float value;
				InputTypeData inputTypeData = moduleInputTypeSelectEntryModel.getInputTypeData();
				if (inputTypeData != null)
				{
					value = inputTypeData.getDefaultValue();
				}
				else
				{
					value = null;
				}
				valueStr = OutputUtils.makeFloatEditText(value);
				break;
			}
			default:
			{
				throw new RuntimeException("Unknown column \"" + col + "\".");
			}
		}
		
		return valueStr; //new Integer(row * col); 
	}
}
