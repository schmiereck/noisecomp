/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.listeners;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Remove Timeline-Generator Notifier.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class RemoveTimelineGeneratorNotifier
{
	//**********************************************************************************************
	// Fields:

	/**
	 * RemoveTimelineGeneratorListeners.
	 */
	private List<RemoveTimelineGeneratorListenerInterface> removeTimelineGeneratorListeners = new Vector<RemoveTimelineGeneratorListenerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param timelinesDrawPanelModel
	 * 			is the timelinesDrawPanelModel.
	 * @param timelineSelectEntryModel
	 * 			is the removed TimelineSelectEntryModel.
	 */
	public void notifyRemoveTimelineGeneratorListeners(TimelinesDrawPanelModel timelinesDrawPanelModel,
	                                                   TimelineSelectEntryModel timelineSelectEntryModel)
	{
		for (RemoveTimelineGeneratorListenerInterface removeTimelineGeneratorListener : this.removeTimelineGeneratorListeners)
		{
			removeTimelineGeneratorListener.notifyRemoveTimelineGenerator(timelinesDrawPanelModel,
			                                                              timelineSelectEntryModel);
		}
		
	}

	/**
	 * @param removeTimelineGeneratorListener 
	 * 			to add to {@link #removeTimelineGeneratorListeners}.
	 */
	public void addRemoveTimelineGeneratorListeners(RemoveTimelineGeneratorListenerInterface removeTimelineGeneratorListener)
	{
		this.removeTimelineGeneratorListeners.add(removeTimelineGeneratorListener);
	}
}
