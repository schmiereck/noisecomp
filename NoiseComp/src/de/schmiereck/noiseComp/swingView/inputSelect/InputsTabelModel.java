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
import de.schmiereck.noiseComp.generator.InputTypeData;

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
	private String columnNames[] = new String[]{"Name", "Value"};
	
	private List<InputData> inputs = new Vector<InputData>();
	
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
	
	@Override
	public int getRowCount() 
	{ 
		return this.inputs.size();
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
		
		InputData inputData = this.inputs.get(row);
		
		switch (col)
		{
			case 0:		// Type name.
			{
				InputTypeData inputTypeData = inputData.getInputTypeData();
				value = inputTypeData.getInputTypeName();
				break;
			}
			case 1:		// Label.
			{
				value = this.makeInputLabel(inputData);
				break;
			}
			default:
			{
				throw new RuntimeException("Unknown column \"" + col + "\".");
			}
		}
		
		return value; //new Integer(row * col); 
	}

	/**
	 * @param inputData
	 * 			is the input.
	 * @return
	 * 			the label.
	 */
	private String makeInputLabel(InputData inputData)
	{
		Generator inputGenerator = inputData.getInputGenerator();
		
		String label;
		
		if (inputData.getInputValue() != null)
		{
			label = String.valueOf(inputData.getInputValue());
		}
		else
		{
			if (inputData.getInputStringValue() != null)
			{
				label = String.valueOf(inputData.getInputStringValue());
			}
			else
			{
				if (inputGenerator != null)
				{	
					label = inputGenerator.getName();

					GeneratorTypeData inputGeneratorTypeData = inputGenerator.getGeneratorTypeData();
					
					if (inputGeneratorTypeData != null)
					{
						label += " [" + inputGeneratorTypeData.getGeneratorTypeName() + "]";
					}
				}
				else
				{
					label = "--";
				}
			}
		}
		return label;
	}

	/**
	 * @param inputData
	 * 			is the Input Data.
	 */
	public void addInputData(InputData inputData)
	{
		this.inputs.add(inputData);
	}
}
