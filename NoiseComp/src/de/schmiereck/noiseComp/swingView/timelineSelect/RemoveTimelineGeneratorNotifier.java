/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	RemoveTimelineGeneratorNotifier.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class RemoveTimelineGeneratorNotifier
{
	/**
	 * RemoveTimelineGeneratorListeners.
	 */
	private List<RemoveTimelineGeneratorListenerInterface> removeTimelineGeneratorListeners = new Vector<RemoveTimelineGeneratorListenerInterface>();
	
	/**
	 * @param timelineSelectEntryModel
	 * 			is the removed TimelineSelectEntryModel.
	 */
	public void notifyRemoveTimelineGeneratorListeners(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		for (RemoveTimelineGeneratorListenerInterface removeTimelineGeneratorListener : this.removeTimelineGeneratorListeners)
		{
			removeTimelineGeneratorListener.notifyRemoveTimelineGenerator(timelineSelectEntryModel);
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
