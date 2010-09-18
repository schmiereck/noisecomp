/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;

/**
 * <p>
 * 	Input-Select-Entry Model.
 * </p>
 * 
 * @author smk
 * @version <p>18.09.2010:	created, smk</p>
 */
public class InputSelectEntryModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Input-Data.
	 */
	private InputData inputData;
	
	private String inputTypeName;
	
	private String inputLabel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputData
	 * 			is the Input-Data.
	 */
	public InputSelectEntryModel(InputData inputData)
	{
		this.inputData = inputData;
		
		this.updateInputData();
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
	 * @return 
	 * 			returns the {@link #inputTypeName}.
	 */
	public String getInputTypeName()
	{
		return this.inputTypeName;
	}

	/**
	 * @param inputTypeName 
	 * 			to set {@link #inputTypeName}.
	 */
	public void setInputTypeName(String inputTypeName)
	{
		this.inputTypeName = inputTypeName;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputLabel}.
	 */
	public String getInputLabel()
	{
		return this.inputLabel;
	}

	/**
	 * @param inputLabel 
	 * 			to set {@link #inputLabel}.
	 */
	public void setInputLabel(String inputLabel)
	{
		this.inputLabel = inputLabel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputData}.
	 */
	public InputData getInputData()
	{
		return this.inputData;
	}

	/**
	 * @param inputData 
	 * 			to set {@link #inputData}.
	 */
	public void setInputData(InputData inputData)
	{
		this.inputData = inputData;
	}
	
	public void updateInputData()
	{
		String inputTypeName;
		String inputLabel;
		
		if (inputData != null)
		{
			InputTypeData inputTypeData = inputData.getInputTypeData();
			inputTypeName = inputTypeData.getInputTypeName();
			
			inputLabel = this.makeInputLabel(inputData);
		}
		else
		{
			inputTypeName = null;
			inputLabel = null;
		}

		this.inputTypeName = inputTypeName;
		this.inputLabel = inputLabel;
	}

}
