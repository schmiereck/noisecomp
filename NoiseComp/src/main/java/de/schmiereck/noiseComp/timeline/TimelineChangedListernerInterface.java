/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;

/**
 * <p>
 * 	Timeline Changed Listerner Interface.
 * </p>
 * <p>
 * 	Called if the Timeline changed.
 * </p>
 * 
 * @author smk
 * @version <p>08.12.2010:	created, smk</p>
 */
public interface TimelineChangedListernerInterface
{

	/**
	 * Timeline changed.
	 *
	 * @param soundSourceData
	 * @param timeline            is the timeline.
	 * @param changedStartTimePos is the start time pos the data in generator changed.
	 * @param changedEndTimePos   is the end time pos the data in generator changed.
	 */
	void notifyTimelineChanged(SoundSourceData soundSourceData, Timeline timeline, float changedStartTimePos, float changedEndTimePos);
}
