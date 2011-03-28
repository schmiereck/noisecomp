/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;

/**
 * <p>
 * 	Positions of Input-Entries and Input-Groups.
 * </p>
 * 
 * @author smk
 * @version <p>21.03.2011:	created, smk</p>
 */
public class InputPosEntriesModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Parent InputPosEntries Model.
	 */
	private final InputPosEntriesModel parentInputPosEntriesModel;
	
//	private int sumEntryCnt = 0;
	
	private final List<InputPosEntriesModel> inputPosEntries = new Vector<InputPosEntriesModel>();

	/**
	 * Input-Entry Group Model.
	 */
	private final InputEntryGroupModel inputEntryGroupModel;
	
	/**
	 * Input-Entry Model. 
	 */	
	private InputEntryModel inputEntryModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param groupInputPosEntriesModel
	 * 			is the Group InputPosEntries Model.
	 * @param inputEntryGroupModel
	 * 			is the Input-Entry Group Model.
	 * @param inputEntryModel
	 * 			is the Input-Entry Model.
	 */
	public InputPosEntriesModel(final InputPosEntriesModel groupInputPosEntriesModel,
	                            final InputEntryGroupModel inputEntryGroupModel,
	                            final InputEntryModel inputEntryModel)
	{
		//==========================================================================================
		this.parentInputPosEntriesModel = groupInputPosEntriesModel;
		this.inputEntryGroupModel = inputEntryGroupModel;
		this.inputEntryModel = inputEntryModel;

		//==========================================================================================
	}
	
	/**
	 * @return 
	 * 			returns sum of entries of {@link #inputPosEntries}.
	 */
	public int getSumEntryCnt()
	{
		//==========================================================================================
		int sumEntryCnt = 0;
		
		if (this.inputPosEntries.size() == 0)
		{
			sumEntryCnt = 1;
		}
		else
		{
			for (InputPosEntriesModel inputPosEntry : this.inputPosEntries)
			{
				int entrySumEntryCnt = inputPosEntry.getSumEntryCnt();
				
//				if (entrySumEntryCnt > 0)
//				{
					sumEntryCnt += entrySumEntryCnt;
//				}
//				else
//				{
//					sumEntryCnt += 1;
//				}
			}
		}
		
		//==========================================================================================
		return sumEntryCnt;
	}

//	/**
//	 * @param sumEntryCnt 
//	 * 			to add to {@link #sumEntryCnt}.
//	 */
//	public void addEntryCnt(int entryCnt)
//	{
//		this.sumEntryCnt += entryCnt;
//	}

	/**
	 * @return 
	 * 			returns the {@link #inputPosEntries}.
	 */
	public List<InputPosEntriesModel> getInputPosEntries()
	{
		return this.inputPosEntries;
	}

	/**
	 * @param inputPosEntries 
	 * 			to add to {@link #inputPosEntries}.
	 */
	public void addInputPosEntry(InputPosEntriesModel inputPosEntries)
	{
		//==========================================================================================
		this.inputPosEntries.add(inputPosEntries);
		
//		int inputEntriesCnt = inputPosEntries.getSumEntryCnt();
//
//		if (inputEntriesCnt == 0)
//		{
//			inputEntriesCnt = 1;
//		}
//
//		this.entryCnt += inputEntriesCnt;
//		
//		if (this.groupInputPosEntriesModel != null)
//		{
//			int groupEntryCnt = this.groupInputPosEntriesModel.getEntryCnt();
//			
//			this.groupInputPosEntriesModel.setEntryCnt(groupEntryCnt + inputEntriesCnt);
//		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #parentInputPosEntriesModel}.
	 */
	public InputPosEntriesModel getGroupInputPosEntriesModel()
	{
		return this.parentInputPosEntriesModel;
	}

//	/**
//	 * @param sumEntryCnt 
//	 * 			to set {@link #sumEntryCnt}.
//	 */
//	public void setSumEntryCnt(int entryCnt)
//	{
//		this.sumEntryCnt = entryCnt;
//	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntryModel}.
	 */
	public InputEntryModel getInputEntryModel()
	{
		return this.inputEntryModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntryGroupModel}.
	 */
	public InputEntryGroupModel getInputEntryGroupModel()
	{
		return this.inputEntryGroupModel;
	}

	/**
	 * @param selectedInputEntry
	 * 			is the input entry.
	 * @return
	 * 			the pos of the given input entry in inputPosEntries.
	 */
	public int searchInputEntryPos(//final InputPosEntriesModel inputPosEntriesModel, 
	                               final InputEntryModel selectedInputEntry)
	{
		//==========================================================================================
//				boolean foundEntry = false;
		int inputNo = 1;
		
		outerloop:
		{
			final List<InputPosEntriesModel> groupInputPosEntries = this.getInputPosEntries();
			
			for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
			{
				final List<InputPosEntriesModel> inputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
				
				if (inputPosEntries.size() > 0)
				{
					for (InputPosEntriesModel inputPosEntryModel : inputPosEntries)
					{
						InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
						
						if (selectedInputEntry == inputEntryModel)
						{
//									foundEntry = true;
							break outerloop;
						}
						inputNo++;
					}
				}
				else
				{
					inputNo++;
				}
//						
//						if (foundEntry == true)
//						{
//							break;
//						}
			}
		}
		//==========================================================================================
		return inputNo;
	}

	/**
	 * @param selectedInputEntry
	 * 			is the input entry.
	 * @return
	 * 			the InputPosEntry of the given input entry in inputPosEntries.
	 */
	public InputPosEntriesModel searchInputPosEntry(final InputEntryModel selectedInputEntry)
	{
		//==========================================================================================
		InputPosEntriesModel retInputPosEntryModel;
		
		retInputPosEntryModel = null;
		
		outerloop:
		{
			final List<InputPosEntriesModel> groupInputPosEntries = this.getInputPosEntries();
			
			for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
			{
				final List<InputPosEntriesModel> inputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
				
				if (inputPosEntries.size() > 0)
				{
					for (InputPosEntriesModel inputPosEntryModel : inputPosEntries)
					{
						InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
						
						if (selectedInputEntry == inputEntryModel)
						{
							retInputPosEntryModel = inputPosEntryModel;
							break outerloop;
						}
					}
				}
			}
		}
		//==========================================================================================
		return retInputPosEntryModel;
	}

	/**
	 * @param newInputEntryModel
	 */
	public void addInputPosEntryInGroup(InputEntryModel newInputEntryModel)
	{
		//==========================================================================================
//		final InputData newInputData = newInputEntryModel.getInputData();
		final InputEntryGroupModel newInputEntryGroupModel = newInputEntryModel.getInputEntryGroupModel();
		
//		final InputTypeData newInputTypeData = newInputData.getInputTypeData();
		
		final List<InputPosEntriesModel> groupInputPosEntries = this.getInputPosEntries();
		
		for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
		{
			//--------------------------------------------------------------------------------------
			InputEntryGroupModel inputEntryGroupModel = groupInputPosEntriesModel.getInputEntryGroupModel();
			
			if (inputEntryGroupModel == newInputEntryGroupModel)
			{
//				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//				// Search and update Add-Entry of group.
//				
//				List<InputPosEntriesModel> group2InputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
//				
//				for (InputPosEntriesModel inputPos2EntriesModel : group2InputPosEntries)
//				{
//					InputEntryModel input2EntryModel = inputPos2EntriesModel.getInputEntryModel();
//					InputData input2Data = input2EntryModel.getInputData();
//					
////					if (input2EntryModel == newInputEntryModel)
//					if (input2Data == null)
//					{
////						inputPos2EntriesModel.setInputEntryModel(newInputEntryModel);
//						
//						TimelineManagerLogic timelineManagerLogic;
//						
//						InputData inputData = 
//							timelineManagerLogic.addInputGenerator(newTimeline, 
//							                                       inputTimeline, 
//							                                       inputTypeData, 
//							                                       floatValue, 
//							                                       stringValue, 
//							                                       modulInputTypeData);
//						
//						input2EntryModel.setInputData(inputData);
//						break;
//					}
//				}
//				
//				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Create new Add-Entry.
				
				InputEntryModel addInputEntryModel = new InputEntryModel(inputEntryGroupModel, 
				                                                         null);
				
				final InputPosEntriesModel addInputPosEntriesModel = 
					new InputPosEntriesModel(groupInputPosEntriesModel, 
					                         inputEntryGroupModel, 
					                         addInputEntryModel);
				
				groupInputPosEntriesModel.addInputPosEntry(addInputPosEntriesModel);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				break;
			}
//			InputTypeData groupInputTypeData = inputEntryGroupModel.getInputTypeData();
//			
//			if (groupInputTypeData == newInputTypeData)
//			{
////				final List<InputPosEntriesModel> inputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
//				
//				final InputPosEntriesModel newInputPosEntriesModel = 
//					new InputPosEntriesModel(groupInputPosEntriesModel, 
//					                         inputEntryGroupModel, 
//					                         newInputEntryModel);
//				
//				groupInputPosEntriesModel.addInputPosEntry(newInputPosEntriesModel);
//				
//				break;
//			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	/**
	 * @param inputEntryModel 
	 * 			to set {@link #inputEntryModel}.
	 */
	public void setInputEntryModel(InputEntryModel inputEntryModel)
	{
		this.inputEntryModel = inputEntryModel;
	}

	/**
	 * @param inputPos2EntriesModel
	 */
	public void removeGroupInputPosEntry(InputPosEntriesModel inputPos2EntriesModel)
	{
		//==========================================================================================
		inputPosEntries.remove(inputPos2EntriesModel);
		
		//==========================================================================================
	}

}
