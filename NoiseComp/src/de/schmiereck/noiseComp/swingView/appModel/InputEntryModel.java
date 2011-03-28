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

	private final InputEntryGroupModel inputEntryGroupModel;
	
	/**
	 * Input.<br/>
	 * <code>null</code> if no input is created yet.
	 */
	private InputData inputData;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param inputData
	 * 			is the Input.
	 */
	public InputEntryModel(final InputEntryGroupModel inputEntryGroupModel,
	                       final InputData inputData)
	{
		//==========================================================================================
		this.inputEntryGroupModel = inputEntryGroupModel;
		this.inputData = inputData;
		
		//==========================================================================================
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

	/**
	 * @return 
	 * 			returns the {@link #inputEntryGroupModel}.
	 */
	public InputEntryGroupModel getInputEntryGroupModel()
	{
		return this.inputEntryGroupModel;
	}
	
}
