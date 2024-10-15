/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

import java.util.Objects;

/**
 * <p>
 * 	Input Select-Entry Model.
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
	 * Input Data.
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
	 * 			is the Input Data.
	 */
	public InputSelectEntryModel(final InputData inputData) {
		//==========================================================================================
		this.inputData = inputData;
		
		this.updateInputData();
		//==========================================================================================
	}

	/**
	 * @param inputData
	 * 			is the input.
	 * @return
	 * 			the label.
	 */
	private String makeInputLabel(final InputData inputData) {
		//==========================================================================================
		final Generator inputGenerator = inputData.getInputGenerator();

		final String valueLabel;

		if (inputData.getInputValue() != null) {
			valueLabel = OutputUtils.makeFloatEditText(inputData.getInputValue());
		} else {
			if (inputData.getInputStringValue() != null) {
				valueLabel = String.valueOf(inputData.getInputStringValue());
			} else {
				valueLabel = null;
			}
		}

		final String generatorLabel;

		if (inputGenerator != null) {
			final GeneratorTypeInfoData inputGeneratorTypeInfoData = inputGenerator.getGeneratorTypeData();

			if (inputGeneratorTypeInfoData != null) {
				generatorLabel = inputGenerator.getName() + " [" + inputGeneratorTypeInfoData.getGeneratorTypeName() + "]";
			} else {
				generatorLabel = inputGenerator.getName();
			}
		} else {
			generatorLabel = null;
		}

		final String label;

		if (Objects.nonNull(valueLabel)) {
			if (Objects.nonNull(generatorLabel)) {
				label = generatorLabel + " (" + valueLabel + ")";
			} else {
				label = valueLabel;
			}
		} else {
			if (Objects.nonNull(generatorLabel)) {
				label = generatorLabel;
			} else {
				label = "--";
			}
		}

		//==========================================================================================
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
		//==========================================================================================
		String inputTypeName;
		String inputLabel;
		
		if (this.inputData != null)
		{
			InputTypeData inputTypeData = this.inputData.getInputTypeData();
			inputTypeName = inputTypeData.getInputTypeName();
			
			inputLabel = this.makeInputLabel(this.inputData);
		}
		else
		{
			inputTypeName = null;
			inputLabel = null;
		}

		this.inputTypeName = inputTypeName;
		this.inputLabel = inputLabel;
		//==========================================================================================
	}

}
