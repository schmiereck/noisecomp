/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;

/**
 * <p>
 * 	Timelines-Generators-Rule Mouse-Listener.
 * </p>
 * 
 * @author smk
 * @version <p>25.02.2011:	created, smk</p>
 */
public class TimelinesGeneratorsRuleMouseListener
implements MouseListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timelines-Generators-Rule Model. 
	 */
	private final TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel;

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
	public TimelinesGeneratorsRuleMouseListener(TimelinesGeneratorsRuleModel timelinesGeneratorsRuleModel, 
	                                            TimelinesGeneratorsRuleView timelinesGeneratorsRuleView)
	{
		//==========================================================================================
		this.timelinesGeneratorsRuleModel = timelinesGeneratorsRuleModel;
		this.timelinesGeneratorsRuleView = timelinesGeneratorsRuleView;
		
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
		this.timelinesGeneratorsRuleModel.setHighlightedTimelineSelectEntryModel(null);
		
		this.timelinesGeneratorsRuleView.repaint();
		
		//==========================================================================================
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//==========================================================================================
		SelectedTimelineModel selectedTimelineModel = this.timelinesGeneratorsRuleModel.getSelectedTimelineModel();
		
		Point point = e.getPoint();
		Point2D point2D = point;
		
		TimelineSelectEntryModel timelineSelectEntryModel = 
			this.timelinesGeneratorsRuleView.searchGenerator(point2D);
		
		selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
//				selectedTimelineGeneratorModel = timelineGeneratorModel;
//				isMousePressed = true;
		//==========================================================================================
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//==========================================================================================
//				selectedTimelineGeneratorModel = null;
//				isMousePressed = false;
		//==========================================================================================
	}

}
