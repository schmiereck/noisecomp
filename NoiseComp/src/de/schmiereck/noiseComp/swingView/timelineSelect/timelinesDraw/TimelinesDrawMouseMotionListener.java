/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.InputEntryTargetModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler.TimelineHandlerModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel.HighlightedTimelineHandler;
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
		
		//==========================================================================================
		final TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
			selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		final Point point = e.getPoint();
		final Point2D point2D = this.timelinesDrawPanelView.mousePos(point);
		
		final HighlightedTimelineHandler timelineHandler = this.timelinesDrawPanelModel.getHighlightedTimelineHandler();
		
		// Timeline Handler dragged?
		if (timelineHandler != HighlightedTimelineHandler.NONE)
		{
			//TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
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
				
				// Position is negativ?
				if (pos < 0.0D)
				{
					// limit to 0.0D.
					pos = 0.0D;
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
			InputEntryModel selectedInputEntry = 
				selectedTimelineModel.getSelectedInputEntry();
			
			if (selectedInputEntry != null)
			{
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				InputEntryTargetModel inputEntryTargetModel = selectedTimelineModel.getInputEntryTargetModel();
				
				if (inputEntryTargetModel == null)
				{
					inputEntryTargetModel = new InputEntryTargetModel();
					
					selectedTimelineModel.setInputEntryTargetModel(inputEntryTargetModel);
				}
				
				final TimelineSelectEntryModel targetTimelineSelectEntryModel = 
					this.timelinesDrawPanelView.searchTimeline(point2D);
				
				inputEntryTargetModel.setTargetPoint2D(point2D);
				inputEntryTargetModel.setTargetTimelineSelectEntryModel(targetTimelineSelectEntryModel);
				
				Timeline selectedTimeline = selectedTimelineSelectEntryModel.getTimeline();
				
				if (targetTimelineSelectEntryModel != null)
				{
					Timeline targetTimeline = targetTimelineSelectEntryModel.getTimeline();

					// Selected and target input timeline are not the same?
					if (selectedTimeline != targetTimeline)
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Check if is input is disabled.
						// Output generators of edited generator is a already input generator?
						if ((selectedTimeline.checkIsOutputTimeline(targetTimeline) == true))
						{
							inputEntryTargetModel.setTargetEnabled(false);
						}
						else
						{
							inputEntryTargetModel.setTargetEnabled(true);
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
					else
					{
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						// Selected and target input timeline are the same:
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						inputEntryTargetModel.setTargetEnabled(false);
						
						this.changeInputPositions(selectedTimelineModel, 
						                          point, point2D, 
						                          selectedInputEntry);
						
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					}
				}
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				this.timelinesDrawPanelView.repaint();
			}
			else
			{
				TimelineSelectEntryModel timelineSelectEntryModel = 
					this.timelinesDrawPanelView.searchTimeline(point2D);
				
				if (timelineSelectEntryModel != null)
				{
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
			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	/**
	 * Change Input positions of inputs of selected timeline in same group by drag and drop.
	 * 
	 * @param selectedTimelineModel
	 * @param point
	 * @param point2D
	 * @param selectedInputEntry
	 */
	private void changeInputPositions(final SelectedTimelineModel selectedTimelineModel, 
	                                  final Point point,
	                                  final Point2D point2D, 
	                                  final InputEntryModel selectedInputEntry)
	{
		//==========================================================================================
		final InputPosEntriesModel targetInputPosEntryModel = 
			this.timelinesDrawPanelView.searchInputEntry(selectedTimelineModel,
			                                             point,
			                                             point2D);

		// Found Target Input?
		if (targetInputPosEntryModel != null)
		{
			final InputEntryGroupModel selectedInputEntryGroupModel = selectedInputEntry.getInputEntryGroupModel();
			final InputEntryGroupModel targetInputEntryGroupModel = targetInputPosEntryModel.getInputEntryGroupModel();
			
			// Same group?
			if (selectedInputEntryGroupModel == targetInputEntryGroupModel)
			{
				final InputPosEntriesModel timelineInputPosEntriesModel = 
					selectedTimelineModel.getInputPosEntriesModel();

				final InputPosEntriesModel selectedInputPosEntryModel = 
					timelineInputPosEntriesModel.searchInputPosEntry(selectedInputEntry);
				
				// Not the same input entry?
				if (selectedInputPosEntryModel != targetInputPosEntryModel)
				{
					final InputEntryModel selectedInputEntryModel = selectedInputPosEntryModel.getInputEntryModel();
					final InputEntryModel targetInputEntryModel = targetInputPosEntryModel.getInputEntryModel();
					
					if ((selectedInputEntryModel.getInputData() != null) &&
						(targetInputEntryModel.getInputData() != null))
					{
						// Change input positions.
						
						selectedTimelineModel.changeInputPositions(selectedInputEntryGroupModel,
						                                           selectedInputEntryModel,
						                                           targetInputEntryModel);
					}
				}
			}
		}
		//==========================================================================================
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		//==========================================================================================
		Point point = e.getPoint();
		Point2D point2D = this.timelinesDrawPanelView.mousePos(point);
		
		//------------------------------------------------------------------------------------------
		this.updateTimelineHandlers(point, 
		                            point2D);
		
		//------------------------------------------------------------------------------------------
		this.updateInputHandler(point, 
	                            point2D);
		
		//==========================================================================================
	}

	/**
	 * @param point
	 * 			is the mouse position.
	 * @param point2D
	 * 			is the scaled mouse position.
	 */
	private void updateTimelineHandlers(final Point point,
	                                    final Point2D point2D)
	{
		//==========================================================================================
		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		//==========================================================================================
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

	/**
	 * @param point
	 * 			is the mouse position.
	 * @param point2D
	 * 			is the mouse position.
	 */
	private void updateInputHandler(final Point point,
                                    final Point2D point2D)
	{
		//==========================================================================================
//		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		final SelectedTimelineModel selectedTimelineModel = 
			this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		InputPosEntriesModel inputPosEntryModel = 
			this.timelinesDrawPanelView.searchInputEntry(selectedTimelineModel,
			                                             point,
			                                             point2D);

		if (inputPosEntryModel != null)
		{
			InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
			
			if (selectedTimelineModel.getHighlightedInputEntry() != inputEntryModel)
			{
				selectedTimelineModel.setHighlightedInputEntry(inputEntryModel);
			}
		}
		else
		{
			selectedTimelineModel.setHighlightedInputEntry(null);
		}
		//==========================================================================================
	}
}
