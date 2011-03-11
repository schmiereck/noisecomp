/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import de.schmiereck.noiseComp.generator.InputData;


/**
 * <p>
 * 	Input-Entry Model.
 * </p>
 * 
 * @author smk
 * @version <p>11.03.2011:	created, smk</p>
 */
public class InputEntryModel
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input.
	 */
	private final InputData inputData;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param inputData
	 * 			is the Input.
	 */
	public InputEntryModel(InputData inputData)
	{
		this.inputData = inputData;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputData}.
	 */
	public InputData getInputData()
	{
		return this.inputData;
	}
	
}
