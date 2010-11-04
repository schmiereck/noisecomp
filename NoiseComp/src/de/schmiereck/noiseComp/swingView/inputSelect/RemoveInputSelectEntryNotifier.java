/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Remove Input-Select-Entry Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>04.11.2010:	created, smk</p>
 */
public class RemoveInputSelectEntryNotifier
{
	/**
	 * RemoveInputSelectEntryListeners.
	 */
	private List<RemoveInputSelectEntryListenerInterface> removeInputSelectEntryListeners = new Vector<RemoveInputSelectEntryListenerInterface>();
	
	/**
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 * @param inputSelectModel
	 * 			is the InputSelectModel.
	 * @param inputSelectEntryModel
	 * 			is the removed InputSelectEntryModel.
	 */
	public void notifyRemoveInputSelectEntryListeners(Timeline selectedTimeline,
	           	                                   	  InputSelectModel inputSelectModel,
	                                                  InputSelectEntryModel inputSelectEntryModel)
	{
		for (RemoveInputSelectEntryListenerInterface removeInputSelectEntryListener : this.removeInputSelectEntryListeners)
		{
			removeInputSelectEntryListener.notifyRemoveInputSelectEntry(selectedTimeline,
			                                                            inputSelectModel,
			                                                            inputSelectEntryModel);
		}
		
	}

	/**
	 * @param removeInputSelectEntryListener 
	 * 			to add to {@link #removeInputSelectEntryListeners}.
	 */
	public void addRemoveInputSelectEntryListeners(RemoveInputSelectEntryListenerInterface removeInputSelectEntryListener)
	{
		this.removeInputSelectEntryListeners.add(removeInputSelectEntryListener);
	}
}
