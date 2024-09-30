/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.listeners;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;

/**
 * <p>
 * 	Do Change-Timelines-Position Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>25.09.2010:	created, smk</p>
 */
public interface DoChangeTimelinesPositionListenerInterface
{

	/**
	 * @param selectedTimelineSelectEntryModel
	 * @param newTimelineSelectEntryModel
	 */
	void changeTimelinesPosition(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
	                             TimelineSelectEntryModel newTimelineSelectEntryModel);
}
