/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel.HighlightedTimelineHandler;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler.TimelineHandlerModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timelines-Draw Mouse-Motion-Listerner.
 * </p>
 * 
 * @author smk
 * @version <p>22.02.2011:	created, smk</p>
 */
public class TimelinesDrawMouseMotionListener
implements MouseMotionListener
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline Draw-Panel Model. 
	 */
	private final TimelinesDrawPanelModel	timelinesDrawPanelModel;

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
	public TimelinesDrawMouseMotionListener(TimelinesDrawPanelModel timelinesDrawPanelModel, 
	                                         TimelinesDrawPanelView timelinesDrawPanelView)
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		this.timelinesDrawPanelView = timelinesDrawPanelView;
		
		//==========================================================================================
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		Point2D point2D = this.timelinesDrawPanelView.mousePos(e.getPoint());
		
		HighlightedTimelineHandler timelineHandler = this.timelinesDrawPanelModel.getHighlightedTimelineHandler();
		
		// Timeline Handler dragged?
		if (timelineHandler != HighlightedTimelineHandler.NONE)
		{
			//TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
			TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
				selectedTimelineModel.getSelectedTimelineSelectEntryModel();
			
			if (selectedTimelineSelectEntryModel != null)
			{
				double timePos = point2D.getX();
			
				double nearestSnapToTimpePos = 
					this.timelinesDrawPanelView.searchNearestSnapToTimpePos(this.timelinesDrawPanelModel, 
					                                                        selectedTimelineSelectEntryModel,
					                                                        timePos);
//				System.out.println(nearestSnapToTimpePos);
				
				boolean handlerSnaped;
				double pos;
				double snapDif = Math.abs(timePos - nearestSnapToTimpePos);
				double d = snapDif * at.getScaleX(); 
//				System.out.println(d + ", " + at.getScaleX());
				
				if (d < 6.0D)
				{
					handlerSnaped = true;
					pos = nearestSnapToTimpePos;
				}
				else
				{
					handlerSnaped = false;
					pos = timePos;
				}
				
				this.timelinesDrawPanelModel.setHandlerSnaped(handlerSnaped);
				this.timelinesDrawPanelModel.setNearestSnapToTimpePos(nearestSnapToTimpePos);
				
				switch (timelineHandler)
				{
					case LEFT:
					{
						Timeline timeline = selectedTimelineSelectEntryModel.getTimeline();
						
						float startTimePos = timeline.getGeneratorStartTimePos();
						float endTimePos = timeline.getGeneratorEndTimePos();
						
						float lengthTime = endTimePos - startTimePos;
						
						selectedTimelineSelectEntryModel.setStartTimePos((float)pos);
						selectedTimelineSelectEntryModel.setEndTimePos((float)pos + lengthTime);
						this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
						this.timelinesDrawPanelView.repaint();
						break;
					}
					case RIGHT:
					{
						selectedTimelineSelectEntryModel.setEndTimePos((float)pos);
						this.timelinesDrawPanelModel.setTimelineHandlerMoved(true);
						this.timelinesDrawPanelView.repaint();
						break;
					}
				}
				{
//					        JViewport vport = scrollPane.getViewport();
//					        Point viewPos = vport.getViewPosition();
//					        Dimension size = vport.getExtentSize();
//					        int vportx = viewPos.x;
//					        int vporty = viewPos.y;
//					        int dx = evt.getX() - startX;
//					        int dy = evt.getY() - startY;
//					 
//					        int newvportx = vportx - dx;
//					        int newvporty = vporty - dy;
//					        Rectangle rect = new Rectangle(newvportx, newvporty, size.width, size.height);
//					        mPanel.scrollRectToVisible(rect);
					Rectangle rect = new Rectangle((int)(point2D.getX() * at.getScaleX() - 32), 
					                               (int)(point2D.getY() - 32), 
					                               64, 64);
					this.timelinesDrawPanelView.scrollRectToVisible(rect);
			    }
			}
		}
		else
		{
			//--------------------------------------------------------------------------------------
			TimelineSelectEntryModel timelineSelectEntryModel = 
				this.timelinesDrawPanelView.searchTimeline(point2D);
			
			if (timelineSelectEntryModel != null)
			{
				TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
					selectedTimelineModel.getSelectedTimelineSelectEntryModel();
				
				if (timelineSelectEntryModel != selectedTimelineSelectEntryModel)
				{
					selectedTimelineModel.notifyDoChangeTimelinesPositionListeners(selectedTimelineSelectEntryModel, 
					                                                               timelineSelectEntryModel);
				}
				Rectangle rect = new Rectangle((int)(point2D.getX() * at.getScaleX() - 32), 
				                               (int)(point2D.getY() - 32), 
				                               64, 64);
				
				this.timelinesDrawPanelView.scrollRectToVisible(rect);
			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		//==========================================================================================
		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		Point point = e.getPoint();
		Point2D point2D = this.timelinesDrawPanelView.mousePos(point);
		
		//------------------------------------------------------------------------------------------
		boolean resetHighlightedTimelineHandler;
		
		TimelineSelectEntryModel timelineSelectEntryModel = 
			this.timelinesDrawPanelView.searchGenerator(point2D);
		
		//------------------------------------------------------------------------------------------
		if (this.timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel() != timelineSelectEntryModel)
		{
			this.timelinesDrawPanelModel.setHighlightedTimelineSelectEntryModel(timelineSelectEntryModel);
			
			this.timelinesDrawPanelView.repaint();
		}

		//------------------------------------------------------------------------------------------
		if (timelineSelectEntryModel != null)
		{
			float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
			
			int timelineGeneratorPos = (int)(point2D.getY() / maxUnitIncrementY);
			
			float startTimePos = timelineSelectEntryModel.getStartTimePos();
			float endTimePos = timelineSelectEntryModel.getEndTimePos();
			float generatorPosY = timelineGeneratorPos * maxUnitIncrementY;
			
			float timeLength = endTimePos - startTimePos;
			
			Rectangle2D rectangle = new Rectangle2D.Float(startTimePos,
			                                              generatorPosY,
			                                              timeLength,
			                                              maxUnitIncrementY);
			
			Shape shape = at.createTransformedShape(rectangle);
			
			Rectangle2D bounds2D = shape.getBounds2D();
			
			TimelineHandlerModel timelineHandlerModel = 
				this.timelinesDrawPanelView.makeTimelineHandlerModel(bounds2D);
			
			if (timelineHandlerModel.getRect1().contains(point))
			{
				if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.LEFT)
				{
					this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.LEFT);
					this.timelinesDrawPanelView.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				}
				resetHighlightedTimelineHandler = false;
			}
			else
			{
				if (timelineHandlerModel.getRect2().contains(point))
				{
					if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.RIGHT)
					{
						this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.RIGHT);
						this.timelinesDrawPanelView.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					}
					resetHighlightedTimelineHandler = false;
				}
				else
				{
					resetHighlightedTimelineHandler = true;
				}
			}
		}
		else
		{
			resetHighlightedTimelineHandler = true;
		}
		
		if (resetHighlightedTimelineHandler == true)
		{
			if (this.timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.NONE)
			{
				this.timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.NONE);
				this.timelinesDrawPanelView.setCursor(this.timelinesDrawPanelView.getDefaultCursor());
			}
		}
		//==========================================================================================
	}
}
