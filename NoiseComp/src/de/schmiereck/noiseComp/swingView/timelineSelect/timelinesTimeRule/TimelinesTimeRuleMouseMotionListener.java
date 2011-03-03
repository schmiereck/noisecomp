/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel.MarkerType;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleView;

/**
 * <p>
 * 	Timelines-Time-Rule Mouse-Motion-Listener.
 * </p>
 * 
 * @author smk
 * @version <p>02.03.2011:	created, smk</p>
 */
public class TimelinesTimeRuleMouseMotionListener
implements MouseMotionListener
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
	 * @param timelinesTimeRuleModel
	 * 			is the Timelines-Time-Rule Model.
	 * @param timelinesTimeRuleView 
	 * 			is the Timelines-Time-Rule View.
	 */
	public TimelinesTimeRuleMouseMotionListener(TimelinesTimeRuleModel timelinesTimeRuleModel, 
	                                            TimelinesTimeRuleView timelinesTimeRuleView)
	{
		//==========================================================================================
		this.timelinesTimeRuleModel = timelinesTimeRuleModel;
		this.timelinesTimeRuleView = timelinesTimeRuleView;
		
		//==========================================================================================
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		//==========================================================================================
		Point point = e.getPoint();
		Point2D point2D = point;
		
		//------------------------------------------------------------------------------------------
		TimeMarkerSelectEntryModel selectedTimeMarkerSelectEntryModel = 
			this.timelinesTimeRuleModel.getSelectedTimeMarkerSelectEntryModel();
		
		if (selectedTimeMarkerSelectEntryModel != null)
		{
			TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
			TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
			TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
			TimelineSelectEntriesModel timelineSelectEntriesModel = this.timelinesTimeRuleModel.getTimelineSelectEntriesModel();
			
			double startTimeMarker = startTimeMarkerSelectEntryModel.getTimeMarker();
			double playTimeMarker = playTimeMarkerSelectEntryModel.getTimeMarker();
			double endTimeMarker = endTimeMarkerSelectEntryModel.getTimeMarker();
			double endTime = timelineSelectEntriesModel.getEndTime();
			
			float tickSize = this.timelinesTimeRuleView.calcTickSize();
			
			double timeMarker = point2D.getX() / tickSize;
			
			if (timeMarker > endTime)
			{
				timeMarker = endTime;
			}
			else
			{
				if (timeMarker < 0.0D)
				{
					timeMarker = 0.0D;
				}
			}

			MarkerType markerType = selectedTimeMarkerSelectEntryModel.getMarkerType();
			
			switch (markerType)
			{
				case START:
				{
					if (timeMarker > endTimeMarker)
					{
						timeMarker = endTimeMarker;
					}
					
					if (playTimeMarker < timeMarker)
					{
						playTimeMarker = timeMarker;
						playTimeMarkerSelectEntryModel.setTimeMarker(timeMarker);
					}
					
					selectedTimeMarkerSelectEntryModel.setTimeMarker(timeMarker);
					break;
				}
				case POS:
				{
					if (timeMarker > endTimeMarker)
					{
						timeMarker = endTimeMarker;
					}
					else
					{
						if (timeMarker < startTimeMarker)
						{
							timeMarker = startTimeMarker;
						}
					}
					selectedTimeMarkerSelectEntryModel.setTimeMarker(timeMarker);
					break;
				}
				case END:
				{
					if (timeMarker < startTimeMarker)
					{
						timeMarker = startTimeMarker;
					}
					
					if (playTimeMarker > timeMarker)
					{
						playTimeMarker = timeMarker;
						playTimeMarkerSelectEntryModel.setTimeMarker(timeMarker);
					}
					
					selectedTimeMarkerSelectEntryModel.setTimeMarker(timeMarker);
					break;
				}
				default:
				{
					throw new RuntimeException("Unexpected marker type \"" + markerType + "\".");
				}
			}
			
			this.timelinesTimeRuleView.repaint();
		}
		
		//==========================================================================================
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		//==========================================================================================
	}	
}
