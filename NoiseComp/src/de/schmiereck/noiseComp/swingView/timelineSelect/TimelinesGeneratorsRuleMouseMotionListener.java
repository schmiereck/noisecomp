/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 * <p>
 * 	Timelines-Generators-Rule Mouse-Motion-Listener.
 * </p>
 * 
 * @author smk
 * @version <p>24.02.2011:	created, smk</p>
 */
public class TimelinesGeneratorsRuleMouseMotionListener
implements MouseMotionListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines-Generators-Rule Model. 
	 */
	private final TimelinesGeneratorsRuleModel	timelinesGeneratorsRuleModel;

	/**
	 * Timelines-Generators-Rule View. 
	 */
	private final TimelinesGeneratorsRuleView timelinesGeneratorsRuleView;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesGeneratorsRuleModel
	 * 			is the Timelines-Generators-Rule Model.
	 * @param timelinesGeneratorsRuleView 
	 * 			is the Timelines-Generators-Rule View.
	 */
	public TimelinesGeneratorsRuleMouseMotionListener(TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel, 
	                                                  TimelinesGeneratorsRuleView timelinesGeneratorsRuleView)
	{
		//==========================================================================================
		this.timelinesGeneratorsRuleModel = timelinesGeneratorsRuleModel;
		this.timelinesGeneratorsRuleView = timelinesGeneratorsRuleView;
		
		//==========================================================================================
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesGeneratorsRuleModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------

		Point point = e.getPoint();
		Point2D point2D = point;
		
		//------------------------------------------------------------------------------------------
		TimelineSelectEntryModel timelineSelectEntryModel = 
			this.timelinesGeneratorsRuleView.searchGenerator(point2D);
		
		if (timelineSelectEntryModel != null)
		{
			TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
				selectedTimelineModel.getSelectedTimelineSelectEntryModel();
			
			if (timelineSelectEntryModel != selectedTimelineSelectEntryModel)
			{
				selectedTimelineModel.notifyDoChangeTimelinesPositionListeners(selectedTimelineSelectEntryModel, 
				                                                                     timelineSelectEntryModel);
			}
			Rectangle rect = new Rectangle((int)(point2D.getX() - 32), 
			                               (int)(point2D.getY() - 32), 
			                               64, 64);
			
			this.timelinesGeneratorsRuleView.scrollRectToVisible(rect);
		}
		//==========================================================================================
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		//==========================================================================================
		Point point = e.getPoint();
		Point2D point2D = point;
		
		//------------------------------------------------------------------------------------------
		TimelineSelectEntryModel timelineSelectEntryModel = 
			this.timelinesGeneratorsRuleView.searchGenerator(point2D);
		
		//------------------------------------------------------------------------------------------
		if (this.timelinesGeneratorsRuleModel.getHighlightedTimelineSelectEntryModel() != timelineSelectEntryModel)
		{
			this.timelinesGeneratorsRuleModel.setHighlightedTimelineSelectEntryModel(timelineSelectEntryModel);
			
			this.timelinesGeneratorsRuleView.repaint();
		}
//		if (timelineSelectEntryModel != null)
//		{
//			System.out.println(timelineSelectEntryModel.getName());
//		}
		
		//==========================================================================================
	}
}
