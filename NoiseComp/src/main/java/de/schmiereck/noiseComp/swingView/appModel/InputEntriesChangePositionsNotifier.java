/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.appModel;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	InputEntries ChangePositions Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>29.03.2011:	created, smk</p>
 */
public class InputEntriesChangePositionsNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Entries Change Positions Listeners.
	 */
	private List<InputEntriesChangePositionsListenerInterface> inputEntriesChangePositionsListeners = new Vector<InputEntriesChangePositionsListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param selectedInputEntryModel
	 * 			is the selected Input-Entry.
	 * @param targetInputEntryModel
	 * 			is the target Input-Entry.
	 */
	public void notifyInputEntriesRemoveListeners(final InputEntryModel selectedInputEntryModel, 
	                                              final InputEntryModel targetInputEntryModel)
	{
		for (InputEntriesChangePositionsListenerInterface inputEntriesChangePositionsListener : this.inputEntriesChangePositionsListeners)
		{
			inputEntriesChangePositionsListener.notifyChangePositions(selectedInputEntryModel,
			                                                          targetInputEntryModel);
		}
		
	}

	/**
	 * @param inputEntriesChangePositionsListener 
	 * 			to add to {@link #inputEntriesChangePositionsListeners}.
	 */
	public void addInputEntriesChangePositionsListeners(InputEntriesChangePositionsListenerInterface inputEntriesChangePositionsListener)
	{
		this.inputEntriesChangePositionsListeners.add(inputEntriesChangePositionsListener);
	}
}
