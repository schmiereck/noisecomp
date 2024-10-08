/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;

/**
 * <p>
 * 	Timelines-Draw Mouse-Listerner.
 * </p>
 * 
 * @author  smk
 * @version  <p>22.02.2011:	created, smk</p>
 */
public class TimelinesDrawMouseListener
implements MouseListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline Draw-Panel Model. 
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;

	/**
	 * Timeline Draw-Panel View. 
	 */
	private final TimelinesDrawPanelView timelinesDrawPanelView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelinesDrawPanelModel
	 * 			is the Timeline Draw-Panel Model.
	 * @param timelinesDrawPanelView 
	 * 			is the Timeline Draw-Panel View.
	 */
	public TimelinesDrawMouseListener(TimelinesDrawPanelModel timelinesDrawPanelModel, 
	                                   TimelinesDrawPanelView timelinesDrawPanelView)
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		this.timelinesDrawPanelView = timelinesDrawPanelView;
		
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
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
			selectedTimelineModel.getSelectedTimelineSelectEntryModel();

		// TimelineHandler not moved?
//		if (this.timelinesDrawPanelModel.getTimelineHandlerMoved() == false)
		if (selectedTimelineSelectEntryModel == null)
		{
			// Reset highlighted timeline.
			this.timelinesDrawPanelModel.setHighlightedTimelineSelectEntryModel(null);
		}
		
		this.timelinesDrawPanelView.repaint();
		
		//==========================================================================================
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = 
			this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		Point2D point2D = this.timelinesDrawPanelView.mousePos(e.getPoint());
		
		//------------------------------------------------------------------------------------------
		{
			TimelineSelectEntryModel timelineSelectEntryModel = 
				this.timelinesDrawPanelView.searchGenerator(point2D);
			
			if (selectedTimelineModel.getSelectedTimelineSelectEntryModel() != timelineSelectEntryModel)
			{
				selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
			}
		}
		//------------------------------------------------------------------------------------------
		{
			final InputEntryModel highlightedInputEntry = selectedTimelineModel.getHighlightedInputEntry();
			
			if (selectedTimelineModel.getSelectedInputEntry() != highlightedInputEntry)
			{
				selectedTimelineModel.setSelectedInputEntry(highlightedInputEntry);
			}
		}
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
