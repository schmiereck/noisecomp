/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	Input-Entries Add Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public class InputEntriesAddNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Entries Add Listeners.
	 */
	private List<InputEntriesAddListenerInterface> inputEntriesAddListeners = new Vector<InputEntriesAddListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param entryPos
	 * 			is the entry pos.
	 * @param inputEntryModel
	 * 			is the added InputEntryModel.
	 */
	public void notifyInputEntriesAddListeners(int entryPos,
	                                           InputEntryModel inputEntryModel)
	{
		for (InputEntriesAddListenerInterface inputEntriesAddListener : this.inputEntriesAddListeners)
		{
			inputEntriesAddListener.notifyAddInputEntry(entryPos,
			                                            inputEntryModel);
		}
		
	}

	/**
	 * @param addToInputEntriesListener 
	 * 			to add to {@link #inputEntriesAddListeners}.
	 */
	public void addInputEntriesAddListeners(InputEntriesAddListenerInterface addToInputEntriesListener)
	{
		this.inputEntriesAddListeners.add(addToInputEntriesListener);
	}
}
