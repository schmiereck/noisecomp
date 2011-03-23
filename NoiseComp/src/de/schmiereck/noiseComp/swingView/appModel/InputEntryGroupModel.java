/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.InputTypeData;

/**
 * <p>
 * 	Input-Entry Group Model.
 * </p>
 * 
 * @author smk
 * @version <p>18.03.2011:	created, smk</p>
 */
public class InputEntryGroupModel
{
	//**********************************************************************************************
	// Fields:

	private final InputTypeData inputTypeData;
	
	private final List<InputEntryModel> inputEntries = new Vector<InputEntryModel>();
	
	//**********************************************************************************************
	// Functions:

	public InputEntryGroupModel(final InputTypeData inputTypeData)
	{
		this.inputTypeData = inputTypeData;
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
	 * @return 
	 * 			returns the {@link #inputEntries}.
	 */
	public List<InputEntryModel> getInputEntries()
	{
		return this.inputEntries;
	}
}
