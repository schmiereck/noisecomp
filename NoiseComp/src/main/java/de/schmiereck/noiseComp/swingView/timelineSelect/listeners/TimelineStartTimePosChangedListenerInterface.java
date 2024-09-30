/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.listeners;

import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timeline Start-Time-Pos Changed Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>18.11.2010:	created, smk</p>
 */
public interface TimelineStartTimePosChangedListenerInterface
{
	/**
	 * @param timeline
	 * 			is the Timeline.
	 * @param startTimePos
	 * 			is the new start time pos.
	 */
	void notifyTimelineStartTimePosChangedListener(Timeline timeline, Float startTimePos);
}
