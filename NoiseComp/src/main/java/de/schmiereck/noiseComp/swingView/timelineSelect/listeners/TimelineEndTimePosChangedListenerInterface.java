/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.listeners;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timeline End-Time-Pos Changed Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>18.11.2010:	created, smk</p>
 */
public interface TimelineEndTimePosChangedListenerInterface
{
	/**
	 * @param timeline
	 * 			is the Timeline.
	 * @param startTimePos
	 * 			is the new end time pos.
	 */
	void notifyTimelineEndTimePosChangedListener(Timeline timeline, Float startTimePos);
}
