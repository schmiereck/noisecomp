/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Update Input-Select-Entry Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>23.03.2011:	created, smk</p>
 */
public interface UpdateInputSelectEntryListenerInterface
{
	/**
	 * @param selectedTimeline
	 * 			is the selectedTimeline.
	 * @param inputSelectModel
	 * 			is the InputSelectModel.
	 * @param inputSelectEntryModel
	 * 			is the updated InputSelectEntryModel.
	 */
	void notifyUpdateInputSelectEntry(Timeline selectedTimeline,
	                                  InputSelectModel inputSelectModel,
	                                  InputSelectEntryModel inputSelectEntryModel);
}
