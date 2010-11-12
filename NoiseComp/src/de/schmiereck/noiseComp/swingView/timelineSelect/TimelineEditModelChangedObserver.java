/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.timelineEdit.TimelineEditModel;
import de.schmiereck.noiseComp.timeline.Timeline;


/**
 * <p>
 * 	Timeline-Edit-Model Changed Observer.
 * </p>
 * 
 * @author smk
 * @version <p>12.11.2010:	created, smk</p>
 */
public class TimelineEditModelChangedObserver 
//implements ModelPropertyChangedListener
{
	//**********************************************************************************************
	// Fields:

	private TimelineEditModel timelineEditModel;
	
	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelineEditModel 
	 * 			is the TimelineEditModel.
	 * @param timelinesDrawPanelModel
	 * 			is the TimelinesDrawPanelModel.
	 */
	public TimelineEditModelChangedObserver(TimelineEditModel timelineEditModel, 
	                                        TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		this.timelineEditModel = timelineEditModel;
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
	}

//	/* (non-Javadoc)
//	 * @see de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener#notifyModelPropertyChanged()
//	 */
//	@Override
//	public void notifyModelPropertyChanged()
//	{
//		TimelineSelectEntryModel timelineSelectEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
//		
//		if (timelineSelectEntryModel != null)
//		{
////			GeneratorTypeData generatorTypeData = this.timelineEditModel.getGeneratorTypeData();
//			Timeline timeline = this.timelineEditModel.getTimeline();
//			timelineSelectEntryModel.setTimeline(timeline);
////			timelineGeneratorModel.setGeneratorTypeData(generatorTypeData);
//
//			String generatorName = this.timelineEditModel.getGeneratorName();
//			timelineSelectEntryModel.setName(generatorName);
//			
//			Float generatorStartTimePos = this.timelineEditModel.getGeneratorStartTimePos();
//			timelineSelectEntryModel.setStartTimePos(generatorStartTimePos);
//
//			Float generatorEndTimePos = this.timelineEditModel.getGeneratorEndTimePos();
//			timelineSelectEntryModel.setEndTimePos(generatorEndTimePos);
//		}
//	}
}
