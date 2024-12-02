/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;

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
	
	//==============================================================================================
	private final List<InputEntryGroupModel> inputEntryGroups = new Vector<InputEntryGroupModel>();

	//----------------------------------------------------------------------------------------------
	
	/**
	 * Add entry to {@link #inputEntryGroups} notifier.
	 */
	private final InputEntriesAddNotifier inputEntriesAddNotifier = new InputEntriesAddNotifier();

	/**
	 * Remove entry from {@link #inputEntryGroups} notifier.
	 */
	private final InputEntriesRemoveNotifier inputEntriesRemoveNotifier = new InputEntriesRemoveNotifier();

	/**
	 * Update entry to {@link #inputEntryGroups} notifier.
	 */
	private final InputEntriesUpdateNotifier inputEntriesUpdateNotifier = new InputEntriesUpdateNotifier();
	
	/**
	 * Change-Positions of {@link #inputEntryGroups} notifier.
	 */
	private final InputEntriesChangePositionsNotifier inputEntriesChangePositionsNotifier = new InputEntriesChangePositionsNotifier();
	
	//**********************************************************************************************
	// Functions:

//	/**
//	 * @return 
//	 * 			returns the {@link #inputEntryModels}.
//	 */
//	public List<InputEntryModel> getInputEntryModels()
//	{
//		return this.inputEntryModels;
//	}

	/**
	 * @param rowNo
	 * 			is the row number.
	 * @return
	 * 			the entry.
	 */
	public InputEntryModel searchInputEntry(int rowNo)
	{
		//==========================================================================================
		InputEntryModel retInputEntryModel;
		
		retInputEntryModel = null;
		
		int entryPos = 0;
		
		for (InputEntryGroupModel inputEntryGroupModel : this.inputEntryGroups)
		{
			List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
			
			for (InputEntryModel inputEntryModel : inputEntries)
			{
				if (entryPos == rowNo)
				{
					retInputEntryModel = inputEntryModel;
					break;
				}
				entryPos++;
			}
			
			if (retInputEntryModel != null)
			{
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
		
		int entryPos = 0;
		
		for (InputEntryGroupModel inputEntryGroupModel : this.inputEntryGroups)
		{
			List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
			
			for (InputEntryModel inputEntryModel : inputEntries)
			{
				if (inputEntryModel == searchedInputEntryModel)
				{
					inputNo = entryPos;
					break;
				}
				entryPos++;
			}
			
			if (inputNo != null)
			{
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
	 * @param inputTypeData
	 * 			is the inputType.
	 * @param newInputEntryModel
	 * 			is the new input entry.
	 * @return
	 * 			is the inserted pos.
	 */
	public int addInputEntry(InputTypeData inputTypeData,
	                         InputEntryModel newInputEntryModel)
	{
		//==========================================================================================
		int entryPos = 0;
		
		for (InputEntryGroupModel inputEntryGroupModel : this.inputEntryGroups)
		{
			entryPos += inputEntryGroupModel.getInputEntries().size();
			
			if (inputEntryGroupModel.getInputTypeData() == inputTypeData)
			{
				List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
				
				inputEntries.add(newInputEntryModel);
				break;
			}
		}
		
		this.inputEntriesAddNotifier.notifyInputEntriesAddListeners(entryPos,
		                                                            newInputEntryModel);
		
		//==========================================================================================
		return entryPos;
	}

	/**
	 * @param inputNo
	 * 			is the number of the input entry.
	 * @return
	 * 			the removed InputEntryModel.
	 */
	public InputEntryModel removeInputEntry(final int inputNo) {
		//==========================================================================================
		InputEntryModel retInputEntryModel;
		
		retInputEntryModel = null;

		//InputEntryModel inputEntryModel = this.inputEntryModels.remove(inputNo);
		
		int entryPos = 0;
		
		for (final InputEntryGroupModel inputEntryGroupModel : this.inputEntryGroups) {
			final List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
			
			for (final InputEntryModel inputEntryModel : inputEntries) {
				if (entryPos == inputNo) {
					retInputEntryModel = inputEntryModel;
					inputEntries.remove(inputEntryModel);
					break;
				}
				entryPos++;
			}
			
			if (retInputEntryModel != null) {
				break;
			}
		}
		//------------------------------------------------------------------------------------------
		this.inputEntriesRemoveNotifier.notifyInputEntriesRemoveListeners(inputNo,
		                                                                  retInputEntryModel);
		
		//==========================================================================================
		return retInputEntryModel;
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
		//==========================================================================================
		int size = 0;
		
		for (InputEntryGroupModel inputEntryGroupModel : this.inputEntryGroups)
		{
			List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
			
			size += inputEntries.size();
		}			
		//==========================================================================================
		return size;
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

	/**
	 * 
	 */
	public void clear()
	{
		//==========================================================================================
		//this.inputEntryModels.clear();
		this.inputEntryGroups.clear();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntryGroups}.
	 */
	public List<InputEntryGroupModel> getInputEntryGroups()
	{
		//==========================================================================================
		return this.inputEntryGroups;
	}

	/**
	 * @param inputTypeData
	 * 			is the inputType.
	 * @return
	 * 			the InputEntryGroupModel.<br/>
	 * 			<code>null</code> if given inputType not found.
	 */
	public InputEntryGroupModel searchInputEntryGroup(InputTypeData inputTypeData)
	{
		//==========================================================================================
		InputEntryGroupModel retInputEntryGroupModel;
		
		retInputEntryGroupModel = null;
		
		List<InputEntryGroupModel> inputEntryGroups = this.getInputEntryGroups();
		
		for (InputEntryGroupModel inputEntryGroupModel : inputEntryGroups)
		{
			InputTypeData groupInputTypeData = inputEntryGroupModel.getInputTypeData();
			
			if (groupInputTypeData == inputTypeData)
			{
				retInputEntryGroupModel = inputEntryGroupModel;
				break;
			}
		}
		//==========================================================================================
		return retInputEntryGroupModel;
	}

	/**
	 * @param selectedInputEntryGroupModel
	 * 			is the selected Input-Entry-Group.
	 * @param selectedInputEntryModel
	 * 			is the selected Input-Entry.
	 * @param targetInputEntryModel
	 * 			is the target Input-Entry.
	 */
	public void changeInputPositions(final InputEntryGroupModel selectedInputEntryGroupModel,
	                                 final InputEntryModel selectedInputEntryModel, 
	                                 final InputEntryModel targetInputEntryModel)
	{
		//==========================================================================================
		List<InputEntryModel> inputEntries = selectedInputEntryGroupModel.getInputEntries();
		
		int selectedInputEntryPos = inputEntries.indexOf(selectedInputEntryModel);
		int targetInputEntryPos = inputEntries.indexOf(targetInputEntryModel);
		
		inputEntries.set(selectedInputEntryPos, targetInputEntryModel);
		inputEntries.set(targetInputEntryPos, selectedInputEntryModel);
		
		this.inputEntriesChangePositionsNotifier.notifyInputEntriesRemoveListeners(targetInputEntryModel,
		                                                                           selectedInputEntryModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntriesChangePositionsNotifier}.
	 */
	public InputEntriesChangePositionsNotifier getInputEntriesChangePositionsNotifier()
	{
		return this.inputEntriesChangePositionsNotifier;
	}
	
}
