/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.generator.InputData;


/**
 * <p>
 * 	Input-Select Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final InputsTabelModel inputsTabelModel = new InputsTabelModel();

	/**
	 * @return 
	 * 			returns the {@link #inputsTabelModel}.
	 */
	public InputsTabelModel getInputsTabelModel()
	{
		return this.inputsTabelModel;
	}
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Clear Inputs.
	 */
	public void clearInputs()
	{
		this.inputsTabelModel.clearInputs();
	}

	/**
	 * @param inputData
	 * 			is the Input Data.
	 */
	public void addInputData(InputData inputData)
	{
		this.inputsTabelModel.addInputData(inputData);
	}

}
