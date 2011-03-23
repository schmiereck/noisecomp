/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Update Input-Select-Entry Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>23.03.2011:	created, smk</p>
 */
public class UpdateInputSelectEntryNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * UpdateInputSelectEntryListeners.
	 */
	private List<UpdateInputSelectEntryListenerInterface> updateInputSelectEntryListeners = new Vector<UpdateInputSelectEntryListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 * @param inputSelectModel
	 * 			is the InputSelectModel.
	 * @param inputSelectEntryModel
	 * 			is the updated InputSelectEntryModel.
	 */
	public void notifyUpdateInputSelectEntryListeners(Timeline selectedTimeline,
	           	                                   	  InputSelectModel inputSelectModel,
	                                                  InputSelectEntryModel inputSelectEntryModel)
	{
		for (UpdateInputSelectEntryListenerInterface updateInputSelectEntryListener : this.updateInputSelectEntryListeners)
		{
			updateInputSelectEntryListener.notifyUpdateInputSelectEntry(selectedTimeline,
			                                                            inputSelectModel,
			                                                            inputSelectEntryModel);
		}
		
	}

	/**
	 * @param updateInputSelectEntryListener 
	 * 			to add to {@link #updateInputSelectEntryListeners}.
	 */
	public void addUpdateInputSelectEntryListeners(UpdateInputSelectEntryListenerInterface updateInputSelectEntryListener)
	{
		this.updateInputSelectEntryListeners.add(updateInputSelectEntryListener);
	}
}
