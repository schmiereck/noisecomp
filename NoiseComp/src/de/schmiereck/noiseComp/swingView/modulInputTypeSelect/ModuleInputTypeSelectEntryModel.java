/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeSelect;

import de.schmiereck.noiseComp.generator.InputTypeData;

/**
 * <p>
 * 	Modul-Input-Type Select-Entry Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeSelectEntryModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Input-Type Data.
	 */
	private InputTypeData inputTypeData;
	
	private String inputTypeName;
	
	private String inputTypeLabel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputTypeData
	 * 			is the Input-Type Data.
	 */
	public ModuleInputTypeSelectEntryModel(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
		
		this.updateInputData();
	}

	/**
	 * @param inputTypeData
	 * 			is the input.
	 * @return
	 * 			the label.
	 */
	private String makeInputLabel(InputTypeData inputTypeData)
	{
		String label;
		
		label = inputTypeData.getInputTypeName();

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
	 * 			returns the {@link #inputTypeLabel}.
	 */
	public String getInputTypeLabel()
	{
		return this.inputTypeLabel;
	}

	/**
	 * @param inputTypeLabel 
	 * 			to set {@link #inputTypeLabel}.
	 */
	public void setInputTypeLabel(String inputLabel)
	{
		this.inputTypeLabel = inputLabel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeData}.
	 */
	public InputTypeData getInputTypeData()
	{
		return this.inputTypeData;
	}

	/**
	 * @param inputTypeData 
	 * 			to set {@link #inputTypeData}.
	 */
	public void setInputTypeData(InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
	}
	
	public void updateInputData()
	{
		String inputTypeName;
		String inputLabel;
		
		if (this.inputTypeData != null)
		{
			inputTypeName = this.inputTypeData.getInputTypeName();
			inputLabel = this.makeInputLabel(this.inputTypeData);
		}
		else
		{
			inputTypeName = null;
			inputLabel = null;
		}

		this.inputTypeName = inputTypeName;
		this.inputTypeLabel = inputLabel;
	}
}
