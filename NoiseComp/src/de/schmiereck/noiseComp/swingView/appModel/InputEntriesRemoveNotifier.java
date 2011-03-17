/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	Input-Entries Remove Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public class InputEntriesRemoveNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Entries Remove Listeners.
	 */
	private List<InputEntriesRemoveListenerInterface> inputEntriesRemoveListeners = new Vector<InputEntriesRemoveListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param inputNo
	 * 			is the number of the input entry.
	 * @param inputEntryModel
	 * 			is the removed InputEntryModel.
	 */
	public void notifyInputEntriesRemoveListeners(int inputNo,
	                                             InputEntryModel inputEntryModel)
	{
		for (InputEntriesRemoveListenerInterface inputEntriesRemoveListener : this.inputEntriesRemoveListeners)
		{
			inputEntriesRemoveListener.notifyRemoveInputEntry(inputNo,
			                                                  inputEntryModel);
		}
		
	}

	/**
	 * @param inputEntriesRemoveListener 
	 * 			to add to {@link #inputEntriesRemoveListeners}.
	 */
	public void addAddToInputEntriesListeners(InputEntriesRemoveListenerInterface inputEntriesRemoveListener)
	{
		this.inputEntriesRemoveListeners.add(inputEntriesRemoveListener);
	}
}
