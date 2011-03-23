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
	 * Group InputPosEntries Model.
	 */
	private final InputPosEntriesModel groupInputPosEntriesModel;
	
//	private int sumEntryCnt = 0;
	
	private final List<InputPosEntriesModel> inputPosEntries = new Vector<InputPosEntriesModel>();

	/**
	 * Input-Entry Group Model.
	 */
	private final InputEntryGroupModel inputEntryGroupModel;
	
	/**
	 * Input-Entry Model. 
	 */	
	private final InputEntryModel inputEntryModel;
	
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
		this.groupInputPosEntriesModel = groupInputPosEntriesModel;
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
		
		int inputEntriesCnt = inputPosEntries.getSumEntryCnt();

		if (inputEntriesCnt == 0)
		{
			inputEntriesCnt = 1;
		}

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
	 * 			returns the {@link #groupInputPosEntriesModel}.
	 */
	public InputPosEntriesModel getGroupInputPosEntriesModel()
	{
		return this.groupInputPosEntriesModel;
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

}
