/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

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
	 * @param selectedTimelineGeneratorModel
	 * @param newTimelineGeneratorModel
	 */
	void changeTimelinesPosition(TimelineGeneratorModel selectedTimelineGeneratorModel,
	                             TimelineGeneratorModel newTimelineGeneratorModel);
}
