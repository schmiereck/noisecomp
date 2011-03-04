/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import java.awt.Dimension;
import java.util.List;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface;

/**
 * <p>
 * 	Timelines-Time-Rule Controller.
 * </p>
 * 
 * @author smk
 * @version <p>24.09.2010:	created, smk</p>
 */
public class TimelinesTimeRuleController 
implements RemoveTimelineGeneratorListenerInterface, 
		   TimelineContentChangedListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines-Time-Rule Model.
	 */
	private final TimelinesTimeRuleModel timelinesTimeRuleModel;

	/**
	 * Timelines-Time-Rule View.
	 */
	private final TimelinesTimeRuleView timelinesTimeRuleView;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App-Controller.
	 * @param timelineSelectEntriesModel
	 * 			are the Timeline-Select-Entries Model.
	 */
	public TimelinesTimeRuleController(final AppController appController,
	                                   final TimelineSelectEntriesModel timelineSelectEntriesModel)
	{
		//==========================================================================================
		this.timelinesTimeRuleModel = new TimelinesTimeRuleModel(timelineSelectEntriesModel);
		
		final PlayTimeMarkerMovedCommand timeMarkerMovedCommand = 
			new PlayTimeMarkerMovedCommand(appController);
		
		this.timelinesTimeRuleView = new TimelinesTimeRuleView(this.timelinesTimeRuleModel,
		                                                       timeMarkerMovedCommand);
		
		//------------------------------------------------------------------------------------------
		this.timelinesTimeRuleModel.getTicksChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					timelinesTimeRuleView.repaint();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		timelineSelectEntriesModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
			{
				@Override
				public void notifyModelPropertyChanged()
				{
					resetTime();
				}
			}
		);
		//------------------------------------------------------------------------------------------
		TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
		
		playTimeMarkerSelectEntryModel.getTimeMarkerChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
			{
				@Override
				public void notifyModelPropertyChanged()
				{
//					SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
//					
//					soundSourceLogic.set
					
					timelinesTimeRuleView.repaint();
				}
			}
		);
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesTimeRuleModel}.
	 */
	public TimelinesTimeRuleModel getTimelinesTimeRuleModel()
	{
		return this.timelinesTimeRuleModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesTimeRuleView}.
	 */
	public TimelinesTimeRuleView getTimelinesTimeRuleView()
	{
		return this.timelinesTimeRuleView;
	}

	/**
	 * Do Timeline Generator Models Changed.
	 */
	public void doTimelineGeneratorModelsChanged(double timelinesDrawPanelWidth)
	{
		//==========================================================================================
		this.timelinesTimeRuleView.setWidth((int)timelinesDrawPanelWidth);
		this.timelinesTimeRuleView.repaint();
		//==========================================================================================
	}

	/**
	 * 
	 */
	public void doChangeZoomX(float zoomX)
	{
		//==========================================================================================
//		this.timelinesTimeRuleModel.setUnits((int)zoomX);
		this.timelinesTimeRuleModel.setZoomX(zoomX);
		
		this.timelinesTimeRuleView.repaint();
		
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.swingView.timelineSelect.RemoveTimelineGeneratorListenerInterface#notifyRemoveTimelineGenerator(de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel, de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel)
	 */
	@Override
	public void notifyRemoveTimelineGenerator(TimelinesDrawPanelModel timelinesDrawPanelModel,
	                                          TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		Dimension timelinesDrawPanelDimension = timelinesDrawPanelModel.getDimension();
		
		// TimelinesTimeRule update.
		this.doTimelineGeneratorModelsChanged(timelinesDrawPanelDimension.getWidth());
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.timeline.TimelineContentChangedListenerInterface#notifyTimelineContentChanged(long, long)
	 */
	@Override
	public void notifyTimelineContentChanged(long bufferStart, long bufferEnd)
	{
		//==========================================================================================
		this.timelinesTimeRuleView.repaint();

		//==========================================================================================
	}

	/**
	 * Reset time markers and end time.
	 */
	public void resetTime()
	{
		//==========================================================================================
		double endTime = 0.0D;
		
		TimelineSelectEntriesModel timelineSelectEntriesModel = this.timelinesTimeRuleModel.getTimelineSelectEntriesModel();
	
		List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModels();
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			Timeline timeline = timelineSelectEntryModel.getTimeline();
			
			Generator generator = timeline.getGenerator();
			
			float endTimePos = generator.getEndTimePos();
			
			if (endTimePos > endTime)
			{
				endTime = endTimePos;
			}
		}
		
		timelineSelectEntriesModel.setEndTime(endTime);

		TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
		
		startTimeMarkerSelectEntryModel.setTimeMarker(0.0D);
		playTimeMarkerSelectEntryModel.setTimeMarker(0.0D);
		endTimeMarkerSelectEntryModel.setTimeMarker(endTime);

		//==========================================================================================
	}

}
