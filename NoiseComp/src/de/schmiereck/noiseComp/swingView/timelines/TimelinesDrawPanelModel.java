/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 * 	Timeline Draw Panel Model.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public class TimelinesDrawPanelModel
{
	//**********************************************************************************************
	// Fields:
	
	private List<TimelineGeneratorModel> timelineGeneratorModels = new Vector<TimelineGeneratorModel>();

	//**********************************************************************************************
	// Functions:
	
	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModels}.
	 */
	public List<TimelineGeneratorModel> getTimelineGeneratorModels()
	{
		return this.timelineGeneratorModels;
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		this.timelineGeneratorModels.clear();
		
		// TODO notify timelineGeneratorModels changed
	}

	/**
	 * @param timelineGeneratorModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelineGeneratorModels.add(timelineGeneratorModel);
		
		// TODO notify timelineGeneratorModels changed
	}
}
