/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Timeline Draw-Panel View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class TimelinesDrawPanelView
extends JPanel 
implements Scrollable//, MouseMotionListener
{
	//**********************************************************************************************
	// Constants:
	
	/**
	 * Zoom factor Y.
	 */
	private static final double	ZOOM_Y	= 1.0D;

	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline Draw Panel Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	private final AffineTransform at = AffineTransform.getScaleInstance(40.0D, ZOOM_Y);
	
	//----------------------------------------------------------------------------------------------
// 	private boolean isMousePressed = false;
 	
// 	private TimelineGeneratorModel selectedTimelineGeneratorModel;
	
//	/**
//	 * Do Timeline Selected Listeners.
//	 */
//	private List<DoTimelineSelectedListenerInterface> doTimelineSelectedListeners = new Vector<DoTimelineSelectedListenerInterface>();
	
	/**
	 * Do Timeline Selected Listeners.
	 */
	private List<DoChangeTimelinesPositionListenerInterface> doChangeTimelinesPositionListeners = new Vector<DoChangeTimelinesPositionListenerInterface>();
 	
	//**********************************************************************************************
	// Functions:

	/**
	* Constructor.
	* 
	* @param timelinesDrawPanelModel
	* 			is the Timeline Draw Panel Model.
	*/
	public TimelinesDrawPanelView(final TimelinesDrawPanelModel timelinesDrawPanelModel) 
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
	    this.setPreferredSize(this.timelinesDrawPanelModel.getDimension());
		
		this.setBackground(Color.LIGHT_GRAY);

		// Let the user scroll by dragging to outside the window.
		this.setAutoscrolls(true); //enable synthetic drag events
//		this.addMouseMotionListener(this); //handle mouse drags
		
		//------------------------------------------------------------------------------------------
		this.addMouseListener
		(
		 	new MouseListener()
		 	{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					Point2D point2D = mousePos(e.getPoint());
					
					TimelineGeneratorModel timelineGeneratorModel = searchGenerator(point2D);
					
					//if (timelineGeneratorModel != null)
					{
						timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
				}

				@Override
				public void mousePressed(MouseEvent e)
				{
					Point2D point2D = mousePos(e.getPoint());
					
					TimelineGeneratorModel timelineGeneratorModel = searchGenerator(point2D);
					
					timelinesDrawPanelModel.setSelectedTimelineGeneratorModel(timelineGeneratorModel);
//					selectedTimelineGeneratorModel = timelineGeneratorModel;
//					isMousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e)
				{
//					selectedTimelineGeneratorModel = null;
//					isMousePressed = false;
				}
		 	}
		);
		this.addMouseMotionListener
		(
		 	new MouseMotionListener()
		 	{
				@Override
				public void mouseDragged(MouseEvent e)
				{
					Point2D point2D = mousePos(e.getPoint());
					
					TimelineGeneratorModel timelineGeneratorModel = searchTimeline(point2D);
					
					if (timelineGeneratorModel != null)
					{
						TimelineGeneratorModel selectedTimelineGeneratorModel = 
							timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
						
						if (timelineGeneratorModel != selectedTimelineGeneratorModel)
						{
							notifyDoChangeTimelinesPositionListeners(selectedTimelineGeneratorModel, 
							                                         timelineGeneratorModel);
						}
					}
				}

				@Override
				public void mouseMoved(MouseEvent e)
				{
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					repaint();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					// Recalculate size of pane.
					recalculateDimension();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getChangeTimelinesPositionChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					repaint();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
					float zoomX = timelinesDrawPanelModel.getZoomX();
					
					at.setToScale(zoomX, ZOOM_Y);
					
					repaint();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getDimensionChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
				    setPreferredSize(timelinesDrawPanelModel.getDimension());
				    setSize(timelinesDrawPanelModel.getDimension());
				    
					repaint();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.timelinesDrawPanelModel.getZoomXChangedNotifier().addModelPropertyChangedListener
		(
		 	new ModelPropertyChangedListener()
		 	{
				@Override
				public void notifyModelPropertyChanged()
				{
				    recalculateDimension();
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		float zoomX = (float)this.at.getScaleX();
		
		this.timelinesDrawPanelModel.setZoomX(zoomX);
		
		//==========================================================================================
	}

	/**
	 * @param point
	 * @return
	 */
	private Point2D mousePos(Point point)
	{
		Point2D point2D;
		try
		{
			point2D = at.inverseTransform(point, null);
		}
		catch (NoninvertibleTransformException ex)
		{
			throw new RuntimeException(ex);
		}
		return point2D;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) 
	{
		//==========================================================================================
		super.paintComponent(g);  // I was missing this code which caused "bleeding"
	   
		//==========================================================================================
		Graphics2D g2 = (Graphics2D) g;
	   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
	   
		//------------------------------------------------------------------------------------------
		List<TimelineGeneratorModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels(); 
		
		//------------------------------------------------------------------------------------------
		// Paint timelines:
		{
			int timelineGeneratorPos = 0;
			
			for (TimelineGeneratorModel timelineGeneratorModel : timelineGeneratorModels)
			{
				this.paintTimeline(g2, timelineGeneratorPos, timelineGeneratorModel);
				
				timelineGeneratorPos++;
			}
		}
		
		//------------------------------------------------------------------------------------------
		// Paint timeline input connectors:
		{
			int entryHeight = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
			
			g2.setPaint(Color.red);
			
			TimelineGeneratorModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineGeneratorModel();
			
			if (selectedTimelineModel != null)
			{
			Timeline timeline = selectedTimelineModel.getTimeline();
			
			if (timeline != null)
			{
			int selectedPos = selectedTimelineModel.getTimelinePos();
			
			Iterator<InputData> inputsIterator = timeline.getInputsIterator();
			
			if (inputsIterator != null)
			{
				float selectedScreenPosX = selectedTimelineModel.getStartTimePos();
				float selectedScreenInputOffset = (((selectedTimelineModel.getEndTimePos() - 
													 selectedTimelineModel.getStartTimePos())) / 
												   (timeline.getInputsCount() + 1));
				
				int inputNo = 1;
				
				while (inputsIterator.hasNext())
				{
					InputData inputData = inputsIterator.next();

					Generator inputGenerator = inputData.getInputGenerator();
					
					if (inputGenerator != null)
					{
					TimelineGeneratorModel inputTimelineModel = this.searchTimelineModel(inputGenerator);

					// Generator found ?
					if (inputTimelineModel != null)
					{	
						int timelinePos = inputTimelineModel.getTimelinePos();
						
						float inputOffsetScreenX = inputNo * selectedScreenInputOffset;

						float inputScreenPosX = (int)inputGenerator.getEndTimePos(); //(int)((inputGenerator.getEndTimePos() - horizontalScrollStart) * scaleX);
						
						float inp1X = selectedScreenPosX + inputOffsetScreenX; //(int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
						float inp1Y = selectedPos * entryHeight; //(int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + selectedPos * entryHeight);
						
						float inp2X = inputScreenPosX; //(int)(tracksData.getGeneratorsLabelSizeX() + inputScreenPosX);
						float inp2Y = timelinePos * entryHeight + entryHeight / 2.0F; //(int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + timelinePos * entryHeight + entryHeight / 2);
						
						{
//							g2.drawLine(inp1X, inp1Y, 
//							            inp1X, inp2Y);
							Line2D line = new Line2D.Float(inp1X, inp1Y, 
							                                     inp1X, inp2Y);
							g2.draw(this.at.createTransformedShape(line));
						}
						{
//							g2.drawLine(inp1X, inp2Y, 
//							            inp2X, inp2Y);
							Line2D line = new Line2D.Float(inp1X, inp2Y, 
							                                     inp2X, inp2Y);
							g2.draw(this.at.createTransformedShape(line));
						}
						{
							Point2D inpPoint = new Point2D.Float(inp1X, inp1Y);
							Point2D point = this.at.transform(inpPoint, null);
							g2.fillRect((int)(point.getX() - 2), (int)(point.getY()), 5, 4);
//							Rectangle2D.Float rectangle = new Rectangle2D.Float(inp1X - 0.25F, inp1Y, 0.5F, 2.0F);
//							g2.fill(this.at.createTransformedShape(rectangle));
						}
					}
					}
					inputNo++;
				}
			}
			}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param generator
	 * 			is the generator.
	 * @return
	 * 			the timeline of given generator.
	 */
	private TimelineGeneratorModel searchTimelineModel(Generator generator)
	{
		//==========================================================================================
		TimelineGeneratorModel retTimelineGeneratorModel;
		
		//------------------------------------------------------------------------------------------
		List<TimelineGeneratorModel> timelineGeneratorModels = this.timelinesDrawPanelModel.getTimelineGeneratorModels(); 
		
		//------------------------------------------------------------------------------------------
		retTimelineGeneratorModel = null;
		
		for (TimelineGeneratorModel timelineGeneratorModel : timelineGeneratorModels)
		{
			Timeline timeline = timelineGeneratorModel.getTimeline();
			
			if (timeline.getGenerator() == generator)
			{
				retTimelineGeneratorModel = timelineGeneratorModel;
				break;
			}
		}
		
		//==========================================================================================
		return retTimelineGeneratorModel;
	}

	/**
	 * @param g2
	 * 			is the Graphics.
	 * @param timelineGeneratorPos
	 * 			is the timeline position.
	 * @param timelineGeneratorModel
	 * 			is the timeline model.
	 */
	private void paintTimeline(Graphics2D g2, int timelineGeneratorPos, TimelineGeneratorModel timelineGeneratorModel)
	{
		//==========================================================================================
		if (timelinesDrawPanelModel.getSelectedTimelineGeneratorModel() == timelineGeneratorModel)
		{
			g2.setPaint(Color.GREEN);
		}
		else
		{
			g2.setPaint(Color.WHITE);
		}
		
		float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		float startTimePos = timelineGeneratorModel.getStartTimePos();
		float endTimePos = timelineGeneratorModel.getEndTimePos();
		float generatorPosY = timelineGeneratorPos * maxUnitIncrementY;
		
		float timeLength = endTimePos - startTimePos;
		
		Rectangle2D rectangle = new Rectangle2D.Float(startTimePos,
		                                              generatorPosY,
		                                              timeLength,
		                                              maxUnitIncrementY);
		
		g2.fill(this.at.createTransformedShape(rectangle));
		
		//------------------------------------------------------------------------------------------
		// Display signal shapes:
		
		Timeline timeline = timelineGeneratorModel.getTimeline();

		if (timeline != null)
		{
			g2.setPaint(Color.BLACK);
			g2.setColor(Color.BLACK);
			
			ModulGenerator parentModulGenerator = null;	//TODO make it real, smk
			
			float frameRate = timeline.getSoundFrameRate();
			float frameStep = timeLength / (frameRate / 25.0F);
	
			float pointSizeX = (float)(1.0F / at.getScaleX());
			float pointSizeY = (float)(1.0F / at.getScaleY());
			
	//		Rectangle2D point = new Rectangle2D.Float();
			Point2D srcPoint = new Point2D.Float();
			Point2D dstPoint = new Point2D.Float();
			
			int lastX = 0;
			int lastY = 0;
			
			for (float timePos = 0.0F; timePos < timeLength; timePos += frameStep)
			{
				long sampleFrame = (long)((startTimePos + timePos) * frameRate);
				
				SoundSample soundSample = timeline.generateFrameSample(sampleFrame, parentModulGenerator);
				
				if (soundSample != null)
				{
					float posX = (startTimePos + timePos); 
					float posY = (generatorPosY + 
								  (soundSample.getMonoValue() * maxUnitIncrementY * -0.5F) + 
								  (maxUnitIncrementY / 2.0F)); 
					
	//				point.setRect(posX, posY,
	//				              pointSizeX, pointSizeY);
					srcPoint.setLocation(pointSizeX, pointSizeY);
					
	//				Shape shape = this.at.createTransformedShape(point);
					srcPoint.setLocation(posX, posY);
					this.at.transform(srcPoint, dstPoint);
					
					int x = (int)dstPoint.getX();
					int y = (int)dstPoint.getY();
					
					// x or y changed after last draw?
					if (((x != lastX) ||
						 (y != lastY)) &&
						(timePos > 0.0F))
					{
						// Draw line.
						
	//					g2.fill(shape);
						g2.drawLine(lastX, lastY, 
						            x, y);
					}
					
					lastX = x;
					lastY = y;
				}
			}
		}
		//==========================================================================================
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize() 
	{
		return this.getPreferredSize();
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect,
										  int orientation,
										  int direction) 
	{
		int nearestTick;
		
		// Get the current position.
		int currentPosition = 0;
		
		int maxUnitIncrementX = this.timelinesDrawPanelModel.getMaxUnitIncrementX();
		int maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		if (orientation == SwingConstants.HORIZONTAL) 
		{
			currentPosition = visibleRect.x;
			
			nearestTick = calcNearestTick(direction, currentPosition, maxUnitIncrementX);
		} 
		else 
		{
			currentPosition = visibleRect.y;
			
			nearestTick = calcNearestTick(direction, currentPosition, maxUnitIncrementY);
		}

		return nearestTick;
	}

	private int calcNearestTick(int direction, int currentPosition, int maxUnitIncrement)
	{
		int nearestTick;
		
		// Return the number of pixels between currentPosition
		// and the nearest tick mark in the indicated direction.
		if (direction < 0) 
		{
			int newPosition = 
				currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
			
			nearestTick = (newPosition == 0) ? maxUnitIncrement : newPosition;
		} 
		else 
		{
			nearestTick = ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
		}

		return nearestTick;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
										   int orientation,
										   int direction) 
	{
		int maxUnitIncrementX = this.timelinesDrawPanelModel.getMaxUnitIncrementX();
		int maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		if (orientation == SwingConstants.HORIZONTAL) 
		{
			return visibleRect.width - maxUnitIncrementX;
		} 
		else 
		{
			return visibleRect.height - maxUnitIncrementY;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth() 
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	public boolean getScrollableTracksViewportHeight() 
	{
		return false;
	}

	/**
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			is the Timeline at given point.
	 */
	private TimelineGeneratorModel searchTimeline(Point2D point2D)
	{
		TimelineGeneratorModel retTimelineGeneratorModel;
		
		retTimelineGeneratorModel = null;
		
		int maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		double generatorPosY = 0.0D;
		
		for (TimelineGeneratorModel timelineGeneratorModel : this.timelinesDrawPanelModel.getTimelineGeneratorModels())
		{
			if ((point2D.getY() >= generatorPosY) &&
				(point2D.getY() <= (generatorPosY + maxUnitIncrementY)))
			{
				retTimelineGeneratorModel = timelineGeneratorModel;
				break;
			}
			
			generatorPosY += maxUnitIncrementY;
		}
		
		return retTimelineGeneratorModel;
	}

	/**
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			is the generator at given point.
	 */
	private TimelineGeneratorModel searchGenerator(Point2D point2D)
	{
		TimelineGeneratorModel retTimelineGeneratorModel;
		
		TimelineGeneratorModel timelineGeneratorModel = this.searchTimeline(point2D);
		
		if (timelineGeneratorModel != null)
		{
			float startTimePos = timelineGeneratorModel.getStartTimePos();
			float endTimePos = timelineGeneratorModel.getEndTimePos();
			
			if ((point2D.getX() >= startTimePos) &&
				(point2D.getX() <= endTimePos))
			{
				retTimelineGeneratorModel = timelineGeneratorModel;
			}
			else
			{
				retTimelineGeneratorModel = null;
			}
		}
		else
		{
			retTimelineGeneratorModel = null;
		}
		
		return retTimelineGeneratorModel;
	}
//
//	/**
//	 * Notify the {@link #doTimelineSelectedListeners}.
//	 */
//	public void notifyDoTimelineSelectedListeners(TimelineGeneratorModel timelineGeneratorModel)
//	{
//		for (DoTimelineSelectedListenerInterface doTimelineSelectedListener : this.doTimelineSelectedListeners)
//		{
//			doTimelineSelectedListener.timelineSelected(timelineGeneratorModel);
//		};
//	}
//
//	/**
//	 * @param doTimelineSelectedListener 
//	 * 			to add to {@link #doTimelineSelectedListeners}.
//	 */
//	public void addDoTimelineSelectedListeners(DoTimelineSelectedListenerInterface doTimelineSelectedListener)
//	{
//		this.doTimelineSelectedListeners.add(doTimelineSelectedListener);
//	}

	/**
	 * Notify the {@link #doChangeTimelinesPositionListeners}.
	 */
	public void notifyDoChangeTimelinesPositionListeners(TimelineGeneratorModel selectedTimelineGeneratorModel,
	                                                     TimelineGeneratorModel newTimelineGeneratorModel)
	{
		for (DoChangeTimelinesPositionListenerInterface doTimelineSelectedListener : this.doChangeTimelinesPositionListeners)
		{
			doTimelineSelectedListener.changeTimelinesPosition(selectedTimelineGeneratorModel,
			                                                   newTimelineGeneratorModel);
		};
	}

	/**
	 * @param doChangeTimelinesPositionListener 
	 * 			to add to {@link #doChangeTimelinesPositionListeners}.
	 */
	public void addChangeTimelinesPositionListeners(DoChangeTimelinesPositionListenerInterface doChangeTimelinesPositionListener)
	{
		this.doChangeTimelinesPositionListeners.add(doChangeTimelinesPositionListener);
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesDrawPanelModel}.
	 */
	public TimelinesDrawPanelModel getTimelinesDrawPanelModel()
	{
		return this.timelinesDrawPanelModel;
	}

	/**
	 * Recalculate size of pane.
	 * 
	 */
	private void recalculateDimension()
	{
		double width = 0.0D;
		double height = 0.0D;
		
		for (TimelineGeneratorModel timelineGeneratorModel : this.timelinesDrawPanelModel.getTimelineGeneratorModels())
		{
			float endTimePos = timelineGeneratorModel.getEndTimePos();
			
			if (endTimePos > width)
			{
				width = endTimePos;
			}
			
			height += this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		}
		
		// Scale:
		
		this.timelinesDrawPanelModel.setDimensionSize(width * timelinesDrawPanelModel.getZoomX(), 
		                                              height * ZOOM_Y);
	}
	
}
