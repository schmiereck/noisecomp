/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineEdit;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.swingView.timelines.SelectedTimelineChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelines.TimelineGeneratorModel;
import de.schmiereck.noiseComp.swingView.timelines.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Timeline-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>07.09.2010:	created, smk</p>
 */
public class TimelineEditController
{
	//**********************************************************************************************
	// Fields:

	private TimelineEditView	timelineEditView;

	private TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel 
	 * 			is the Timeline Draw-Panel Model.
	 */
	public TimelineEditController(final TimelinesDrawPanelModel timelinesDrawPanelModel)
	{
		//==========================================================================================
		this.timelineEditView = new TimelineEditView();

		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		this.timelinesDrawPanelModel.addSelectedTimelineChangedListener
		(
		 	new SelectedTimelineChangedListenerInterface()
		 	{
				@Override
				public void selectedTimelineChanged()
				{
					TimelineGeneratorModel timelineGeneratorModel = timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
					
					String generatorName = timelineGeneratorModel.getGeneratorName();
					
					Generator generator = xyzService.getGenerator(generatorName);
					
					this.timelineEditModel.setGeneratorName(generator.getName());
					this.timelineEditModel.setGeneratorStartTime(generator.getStartTimePos());
					this.timelineEditModel.setGeneratorEndTime(generator.getStartTimePos());
				}
		 		
		 	}
		);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEditView}.
	 */
	public TimelineEditView getTimelineEditView()
	{
		return this.timelineEditView;
	}
}
