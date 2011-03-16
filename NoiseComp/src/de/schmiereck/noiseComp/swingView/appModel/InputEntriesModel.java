/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	Input-Entries Model manage {@link InputEntryModel}.
 * </p>
 * 
 * @author smk
 * @version <p>11.03.2011:	created, smk</p>
 */
public class InputEntriesModel
{
	//**********************************************************************************************
	// Fields:
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Input-Entry Models.
	 */
	private final List<InputEntryModel> inputEntryModels = new Vector<InputEntryModel>();

	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #inputEntryModels}.
	 */
	public List<InputEntryModel> getInputEntryModels()
	{
		return this.inputEntryModels;
	}

	/**
	 * @param rowNo
	 * 			is the row number.
	 * @return
	 * 			the entry.
	 */
	public InputEntryModel searchInputEntry(Integer rowNo)
	{
		//==========================================================================================
		InputEntryModel retInputEntryModel;
		
		retInputEntryModel = null;
		
		for (int entryPos = 0; entryPos < this.inputEntryModels.size(); entryPos++)
		{
			InputEntryModel inputEntryModel = this.inputEntryModels.get(entryPos);
			
			if (entryPos == rowNo)
			{
				retInputEntryModel = inputEntryModel;
				break;
			}
		}
		
		//==========================================================================================
		return retInputEntryModel;
	}

}
