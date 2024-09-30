/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.timeline.Timeline;


/**
 * <p>
 * 	Remove Input-Select-Entry Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>04.11.2010:	created, smk</p>
 */
public interface RemoveInputSelectEntryListenerInterface
{
	/**
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 * @param inputSelectModel
	 * 			is the InputSelectModel.
	 * @param inputSelectEntryModel
	 * 			is the removed InputSelectEntryModel.
	 */
	void notifyRemoveInputSelectEntry(Timeline selectedTimeline,
	                                  InputSelectModel inputSelectModel,
	                                  InputSelectEntryModel inputSelectEntryModel);
}
