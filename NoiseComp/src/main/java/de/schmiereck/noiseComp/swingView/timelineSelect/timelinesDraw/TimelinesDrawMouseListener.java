/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Objects;

import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
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
	public TimelinesDrawMouseListener(final TimelinesDrawPanelModel timelinesDrawPanelModel,
									  final TimelinesDrawPanelView timelinesDrawPanelView)
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
	public void mousePressed(MouseEvent e) {
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		final TimelineSelectEntriesModel timelineSelectEntriesModel = this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();

		//==========================================================================================
		final Point2D point2D = this.timelinesDrawPanelView.mousePos(e.getPoint());
		boolean repaintView = false;

		//------------------------------------------------------------------------------------------
		final TimelineSelectEntryModel highlightedTimelineSelectEntryModel =
				this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();

		if (Objects.nonNull(highlightedTimelineSelectEntryModel)) {
			if (this.timelinesDrawPanelModel.getHighlightExpandTimelineHandler()) {
				highlightedTimelineSelectEntryModel.setExpanded(!highlightedTimelineSelectEntryModel.getExpanded());
				final int timelinePos = timelineSelectEntriesModel.calcTimelineSelectEntryPos(highlightedTimelineSelectEntryModel);
				TimelinesDrawPanelUtils.recalcYPosTimelineList(timelineSelectEntriesModel, timelinePos);
				repaintView = true;
			}
		}
		//------------------------------------------------------------------------------------------
		{
			final TimelineSelectEntryModel timelineSelectEntryModel = this.timelinesDrawPanelView.searchGenerator(point2D);
			
			if (selectedTimelineModel.getSelectedTimelineSelectEntryModel() != timelineSelectEntryModel) {
				selectedTimelineModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
			}
		}
		//------------------------------------------------------------------------------------------
		{
			final InputEntryModel highlightedInputEntry = selectedTimelineModel.getHighlightedInputEntry();
			
			if (selectedTimelineModel.getSelectedInputEntry() != highlightedInputEntry) {
				selectedTimelineModel.setSelectedInputEntry(highlightedInputEntry, false);
			}
		}
		//------------------------------------------------------------------------------------------
		if (repaintView) {
			this.timelinesDrawPanelView.repaint();
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
