/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	Input-Entries Update Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>17.03.2011:	created, smk</p>
 */
public class InputEntriesUpdateNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Entries Update Listeners.
	 */
	private List<InputEntriesUpdateListenerInterface> inputEntriesUpdateListeners = new Vector<InputEntriesUpdateListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param inputEntryModel
	 * 			is the updated InputEntryModel.
	 */
	public void notifyInputEntriesUpdateListeners(InputEntryModel inputEntryModel)
	{
		for (InputEntriesUpdateListenerInterface inputEntriesUpdateListener : this.inputEntriesUpdateListeners)
		{
			inputEntriesUpdateListener.notifyUpdateInputEntry(inputEntryModel);
		}
		
	}

	/**
	 * @param inputEntriesUpdateListener 
	 * 			to add to {@link #inputEntriesUpdateListeners}.
	 */
	public void addInputEntriesUpdateListeners(InputEntriesUpdateListenerInterface inputEntriesUpdateListener)
	{
		this.inputEntriesUpdateListeners.add(inputEntriesUpdateListener);
	}
}
