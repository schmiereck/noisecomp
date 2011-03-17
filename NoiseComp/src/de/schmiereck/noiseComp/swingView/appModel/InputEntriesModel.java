/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.CompareUtils;

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

	/**
	 * Add entry to {@link #inputEntryModels} notifier.
	 */
	private final InputEntriesAddNotifier inputEntriesAddNotifier = new InputEntriesAddNotifier();

	/**
	 * Remove entry from {@link #inputEntryModels} notifier.
	 */
	private final InputEntriesRemoveNotifier inputEntriesRemoveNotifier = new InputEntriesRemoveNotifier();

	/**
	 * Update entry to {@link #inputEntryModels} notifier.
	 */
	private final InputEntriesUpdateNotifier inputEntriesUpdateNotifier = new InputEntriesUpdateNotifier();
	
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
			
			if (CompareUtils.compareWithNull((Integer)entryPos, rowNo) == true)
			{
				retInputEntryModel = inputEntryModel;
				break;
			}
		}
		
		//==========================================================================================
		return retInputEntryModel;
	}

	/**
	 * @param searchedInputEntryModel
	 * 			is the searched input entry.
	 * @return
	 * 			the number of the input entry.<br/>
	 * 			<code>null</code> if entry is not found.
	 */
	public Integer searchInputEntry(InputEntryModel searchedInputEntryModel)
	{
		//==========================================================================================
		Integer inputNo;
		
		inputNo = null;
		
		for (int entryPos = 0; entryPos < this.inputEntryModels.size(); entryPos++)
		{
			InputEntryModel inputEntryModel = this.inputEntryModels.get(entryPos);
			
			if (inputEntryModel == searchedInputEntryModel)
			{
				inputNo = entryPos;
				break;
			}
		}
		
		//==========================================================================================
		return inputNo;
	}

	/**
	 * @param inputEntryModel
	 * 			is the input entry.
	 * @param inputData
	 * 			is the inputData.
	 */
	public void updateInputEntry(InputEntryModel inputEntryModel,
	                             InputData inputData)
	{
		//==========================================================================================
		if (inputEntryModel != null)
		{
			inputEntryModel.setInputData(inputData);
		}
		
		this.inputEntriesUpdateNotifier.notifyInputEntriesUpdateListeners(inputEntryModel);
		
		//==========================================================================================
	}

	/**
	 * @param inputEntryModel
	 * 			is the input entry.
	 */
	public void addInputEntry(InputEntryModel inputEntryModel)
	{
		//==========================================================================================
		this.inputEntryModels.add(inputEntryModel);
		
		this.inputEntriesAddNotifier.notifyInputEntriesAddListeners(inputEntryModel);
		
		//==========================================================================================
	}

	/**
	 * @param inputNo
	 * 			is the number of the input entry.
	 */
	public void removeInputEntry(int inputNo)
	{
		//==========================================================================================
		InputEntryModel inputEntryModel = this.inputEntryModels.remove(inputNo);
		
		this.inputEntriesRemoveNotifier.notifyInputEntriesRemoveListeners(inputNo,
		                                                                  inputEntryModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntriesAddNotifier}.
	 */
	public InputEntriesAddNotifier getInputEntriesAddNotifier()
	{
		return this.inputEntriesAddNotifier;
	}

	/**
	 * @return
	 * 		the size of {@link #inputEntryModels}.
	 */
	public int getSize()
	{
		return this.inputEntryModels.size();
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntriesRemoveNotifier}.
	 */
	public InputEntriesRemoveNotifier getInputEntriesRemoveNotifier()
	{
		return this.inputEntriesRemoveNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntriesUpdateNotifier}.
	 */
	public InputEntriesUpdateNotifier getInputEntriesUpdateNotifier()
	{
		return this.inputEntriesUpdateNotifier;
	}
	
}
