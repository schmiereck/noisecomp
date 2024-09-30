/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * <p>
 * 	Timelines-Time-Rule Mouse-Listener.
 * </p>
 * 
 * @author smk
 * @version <p>02.03.2011:	created, smk</p>
 */
public class TimelinesTimeRuleMouseListener
implements MouseListener
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
	public TimelinesTimeRuleMouseListener(TimelinesTimeRuleModel timelinesTimeRuleModel, 
	                                      TimelinesTimeRuleView timelinesTimeRuleView)
	{
		//==========================================================================================
		this.timelinesTimeRuleModel = timelinesTimeRuleModel;
		this.timelinesTimeRuleView = timelinesTimeRuleView;
		
		//==========================================================================================
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		//==========================================================================================
		//==========================================================================================
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		//==========================================================================================
		//==========================================================================================
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		//==========================================================================================
//		this.timelinesTimeRuleModel.setHighlightedTimelineSelectEntryModel(null);
//		
//		this.timelinesTimeRuleView.repaint();
		
		//==========================================================================================
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//==========================================================================================
		Point point = e.getPoint();
		Point2D point2D = point;
		
		TimeMarkerSelectEntryModel timeMarkerSelectEntryModel = 
			this.timelinesTimeRuleView.searchTimeMarker(point2D);
		
		this.timelinesTimeRuleModel.setSelectedTimeMarkerSelectEntryModel(timeMarkerSelectEntryModel);
		
		//==========================================================================================
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//==========================================================================================
		this.timelinesTimeRuleModel.setSelectedTimeMarkerSelectEntryModel(null);
		
		//==========================================================================================
	}
}
