/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.listeners;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Remove Timeline-Generator Listener Interface.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public interface RemoveTimelineGeneratorListenerInterface
{
	/**
	 * @param timelinesDrawPanelModel  is the removed timelinesDrawPanelModel.
	 * @param timelineSelectEntryModel is the removed TimelineSelectEntryModel.
	 */
	void notifyRemoveTimelineGenerator(SoundSourceData soundSourceData, TimelinesDrawPanelModel timelinesDrawPanelModel,
									   TimelineSelectEntryModel timelineSelectEntryModel);
}
