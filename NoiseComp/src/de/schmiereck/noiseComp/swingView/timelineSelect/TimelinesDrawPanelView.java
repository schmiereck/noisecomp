/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
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
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelinesDrawPanelModel.HighlightedTimelineHandler;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.DoChangeTimelinesPositionListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler.TimelineHandlerModel;
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
	 * Initial Zoom factor X.
	 */
	private static final double	INIT_ZOOM_X	= 40.0D;
	
	/**
	 * Initial Zoom factor Y.
	 */
	public static final double	INIT_ZOOM_Y	= 1.0D;

	private static final Color CBackground = Color.WHITE;
	private static final Color CBackgroundLines = new Color(0xFFF7E1);
	
	private static final Color CTimelineNormalBackground = new Color(0xFFF0C9);//Color.LIGHT_GRAY;
	private static final Color CTimelineSelectedBackground = new Color(0xBFB7A1);//128, 128, 192);
	private static final Color CTimelineHighlightedBackground = new Color(0xFFE6C9);//128, 198, 192);
	private static final Color CTimelineSignal = new Color(0x1A, 0x23, 0x74, 160);//0x42, 0x82, 0xA4, 160);//0, 0, 0, 160);
	private static final Color CTimelineInputConnector = new Color(0xA67841);//255, 0, 0, 192);
	private static final Color CTimelineBuffer = new Color(0x434DA5);//0, 200, 0, 127);

	private static final Color CTimelineHandlerBorder = new Color(0xA1B4BE);//200, 200, 255);
	private static final Color CTimelineHandlerBackground = new Color(0x4282A4);//100, 100, 100);
	private static final Color CTimelineHandlerNearestSnapLine = new Color(0x16, 0x76, 0x43, 64);//0, 0, 200, 200);
	private static final Color CTimelineHandlerSnapLine = new Color(0x90CB4F);//60, 60, 255);
	
	private static final Color CPlaybackTimeLine = new Color(0x561360);
	private static final Color CPlaybackTimeLineGlow = new Color(0xBE, 0x6F, 0xC9, 128);
	
	private final float DRAW_EVERY_SAMPLE = 4.0F;//1.0F;//25.0F;
	
	private static final int TimelineHandlerSizeX = 8;
	private static final int TimelineHandlerSizeY = 8;
	
	private final Cursor defaultCursor;
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline Draw Panel Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	/**
	 * Tranforma positions to view.
	 */
	private final AffineTransform at = AffineTransform.getScaleInstance(INIT_ZOOM_X, INIT_ZOOM_Y);
	
	//----------------------------------------------------------------------------------------------
// 	private boolean isMousePressed = false;
 	
// 	private TimelineSelectEntryModel selectedTimelineGeneratorModel;
	
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
		
		this.setBackground(CBackground);

		// Let the user scroll by dragging to outside the window.
		this.setAutoscrolls(true); //enable synthetic drag events
//		this.addMouseMotionListener(this); //handle mouse drags
		
		this.defaultCursor = getCursor();
		
		//------------------------------------------------------------------------------------------
		this.addMouseListener
		(
		 	new MouseListener()
		 	{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					timelinesDrawPanelModel.setHighlightedTimelineSelectEntryModel(null);
					
					repaint();
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}

				@Override
				public void mousePressed(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Point2D point2D = mousePos(e.getPoint());
					
					TimelineSelectEntryModel timelineSelectEntryModel = searchGenerator(point2D);
					
					timelinesDrawPanelModel.setSelectedTimelineSelectEntryModel(timelineSelectEntryModel);
//					selectedTimelineGeneratorModel = timelineGeneratorModel;
//					isMousePressed = true;
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}

				@Override
				public void mouseReleased(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//					selectedTimelineGeneratorModel = null;
//					isMousePressed = false;
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Point2D point2D = mousePos(e.getPoint());
					
					HighlightedTimelineHandler timelineHandler = timelinesDrawPanelModel.getHighlightedTimelineHandler();
					
					// Timeline Handler dragged?
					if (timelineHandler != HighlightedTimelineHandler.NONE)
					{
						//TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
						TimelineSelectEntryModel selectedTimelineSelectEntryModel = timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
						
						if (selectedTimelineSelectEntryModel != null)
						{
							double timePos = point2D.getX();
						
							double nearestSnapToTimpePos = searchNearestSnapToTimpePos(timelinesDrawPanelModel, 
							                                                           selectedTimelineSelectEntryModel,
							                                                           timePos);
//							System.out.println(nearestSnapToTimpePos);
							
							boolean handlerSnaped;
							double pos;
							double snapDif = Math.abs(timePos - nearestSnapToTimpePos);
							double d = snapDif * at.getScaleX(); 
//							System.out.println(d + ", " + at.getScaleX());
							
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
							
							timelinesDrawPanelModel.setHandlerSnaped(handlerSnaped);
							timelinesDrawPanelModel.setNearestSnapToTimpePos(nearestSnapToTimpePos);
							
							switch (timelineHandler)
							{
								case LEFT:
								{
									selectedTimelineSelectEntryModel.setStartTimePos((float)pos);
									timelinesDrawPanelModel.setTimelineHandlerMoved(true);
									repaint();
									break;
								}
								case RIGHT:
								{
									selectedTimelineSelectEntryModel.setEndTimePos((float)pos);
									timelinesDrawPanelModel.setTimelineHandlerMoved(true);
									repaint();
									break;
								}
							}
							{
//						        JViewport vport = scrollPane.getViewport();
//						        Point viewPos = vport.getViewPosition();
//						        Dimension size = vport.getExtentSize();
//						        int vportx = viewPos.x;
//						        int vporty = viewPos.y;
//						        int dx = evt.getX() - startX;
//						        int dy = evt.getY() - startY;
//						 
//						        int newvportx = vportx - dx;
//						        int newvporty = vporty - dy;
//						        Rectangle rect = new Rectangle(newvportx, newvporty, size.width, size.height);
//						        mPanel.scrollRectToVisible(rect);
								Rectangle rect = new Rectangle((int)(point2D.getX() * at.getScaleX() - 32), 
								                               (int)(point2D.getY() - 32), 
								                               64, 64);
						        scrollRectToVisible(rect);
						    }
						}
					}
					else
					{
						TimelineSelectEntryModel timelineSelectEntryModel = searchTimeline(point2D);
						
						if (timelineSelectEntryModel != null)
						{
							TimelineSelectEntryModel selectedTimelineSelectEntryModel = 
								timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
							
							if (timelineSelectEntryModel != selectedTimelineSelectEntryModel)
							{
								notifyDoChangeTimelinesPositionListeners(selectedTimelineSelectEntryModel, 
								                                         timelineSelectEntryModel);
							}
							Rectangle rect = new Rectangle((int)(point2D.getX() * at.getScaleX() - 32), 
							                               (int)(point2D.getY() - 32), 
							                               64, 64);
					        scrollRectToVisible(rect);
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}

				@Override
				public void mouseMoved(MouseEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					Point point = e.getPoint();
					Point2D point2D = mousePos(point);
					
					boolean resetHighlightedTimelineHandler;
					
					TimelineSelectEntryModel timelineSelectEntryModel = searchGenerator(point2D);
					
					if (timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel() != timelineSelectEntryModel)
					{
						timelinesDrawPanelModel.setHighlightedTimelineSelectEntryModel(timelineSelectEntryModel);
						
						repaint();
					}

					if (timelineSelectEntryModel != null)
					{
						float maxUnitIncrementY = timelinesDrawPanelModel.getMaxUnitIncrementY();
						
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
						
						TimelineHandlerModel timelineHandlerModel = makeTimelineHandlerModel(bounds2D);
						
						if (timelineHandlerModel.getRect1().contains(point))
						{
							if (timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.LEFT)
							{
								timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.LEFT);
								setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
							}
							resetHighlightedTimelineHandler = false;
						}
						else
						{
							if (timelineHandlerModel.getRect2().contains(point))
							{
								if (timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.RIGHT)
								{
									timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.RIGHT);
									setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
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
						if (timelinesDrawPanelModel.getHighlightedTimelineHandler() != HighlightedTimelineHandler.NONE)
						{
							timelinesDrawPanelModel.setHighlightedTimelineHandler(HighlightedTimelineHandler.NONE);
							setCursor(defaultCursor);
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//------------------------------------------------------------------------------------------
		this.addMouseListener
		(
		 	new MouseAdapter() 
		 	{
		 		@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e);
					//setActive(true);
		 			//Toolkit.getDefaultToolkit().

					HighlightedTimelineHandler timelineHandler = timelinesDrawPanelModel.getHighlightedTimelineHandler();
					
					if (timelineHandler != HighlightedTimelineHandler.NONE)
					{
						timelinesDrawPanelModel.setTimelineHandlerMoved(true);
					}
				}

				/* (non-Javadoc)
				 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
				 */
				@Override
				public void mouseReleased(MouseEvent e)
				{
					super.mouseReleased(e);

					if (timelinesDrawPanelModel.getTimelineHandlerMoved() == true)
					{
						timelinesDrawPanelModel.setTimelineHandlerMoved(false);
						
						TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
						
						if (highlightedTimelineSelectEntryModel != null)
						{
							HighlightedTimelineHandler timelineHandler = timelinesDrawPanelModel.getHighlightedTimelineHandler();
							
							if (timelineHandler != HighlightedTimelineHandler.NONE)
							{
								switch (timelineHandler)
								{
									case LEFT:
									{
										timelinesDrawPanelModel.notifyTimelineStartTimePosChangedListeners(highlightedTimelineSelectEntryModel);
										timelinesDrawPanelModel.setTimelineHandlerMoved(false);
										timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
										break;
									}
									case RIGHT:
									{
										timelinesDrawPanelModel.notifyTimelineEndTimePosChangedListeners(highlightedTimelineSelectEntryModel);
										timelinesDrawPanelModel.setTimelineHandlerMoved(false);
										timelinesDrawPanelModel.setNearestSnapToTimpePos(Double.NaN);
										break;
									}
								}
							}
						}
					}
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
					
					at.setToScale(zoomX, INIT_ZOOM_Y);
					
//				    // Update client's preferred size because
//				    // the area taken up by the graphics has
//				    // gotten larger or smaller (if cleared).
//					setPreferredSize(timelinesDrawPanelModel.getDimension());
//
//				    // Let the scroll pane know to update itself
//				    // and its scroll bars.
//				    revalidate();
				    
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
				    setSize(timelinesDrawPanelModel.getDimension());
				    
				    // Update client's preferred size because
				    // the area taken up by the graphics has
				    // gotten larger or smaller (if cleared).
					setPreferredSize(timelinesDrawPanelModel.getDimension());

				    // Let the scroll pane know to update itself
				    // and its scroll bars.
				    revalidate();
				    
					//repaint();
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
	 * 			is the mouse Point poition of coordinates relative to the source component.
	 * @return
	 * 			the Point of tranformated to view.
	 */
	private Point2D mousePos(Point point)
	{
		Point2D point2D;
		try
		{
			point2D = this.at.inverseTransform(point, null);
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
		Graphics2D g2 = (Graphics2D)g;
	   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
	   
		Rectangle clipRect = g.getClipBounds();

		//------------------------------------------------------------------------------------------
		float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		//------------------------------------------------------------------------------------------
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesDrawPanelModel.getTimelineSelectEntryModels(); 
		
		TimelineSelectEntryModel selectedTimelineEntryModel = this.timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel();
		
		int selectedPos = 0;
		
		//------------------------------------------------------------------------------------------
		// Paint timelines:
		{
			int timelineGeneratorPos = 0;
			
			for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
			{
				g2.setColor(CBackgroundLines);
				
				int y = (int)((timelineGeneratorPos + 1) * maxUnitIncrementY);
				
				g2.drawLine(clipRect.x, y, 
				            clipRect.x + clipRect.width, y);
				
				this.paintTimeline(g2, timelineGeneratorPos, timelineSelectEntryModel);
				
				if (selectedTimelineEntryModel == timelineSelectEntryModel)
				{
					selectedPos = timelineGeneratorPos;
				}
				
				timelineGeneratorPos++;
			}
		}
		
		//------------------------------------------------------------------------------------------
		// Paint timeline input connectors:
		{
			int entryHeight = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
			
			g2.setPaint(CTimelineInputConnector);
			
			if (selectedTimelineEntryModel != null)
			{
			Timeline timeline = selectedTimelineEntryModel.getTimeline();
			
			if (timeline != null)
			{
//			int selectedPos = selectedTimelineEntryModel.getTimelinePos();
			
			Iterator<InputData> inputsIterator = timeline.getInputsIterator();
			
			if (inputsIterator != null)
			{
				float selectedScreenPosX = selectedTimelineEntryModel.getStartTimePos();
				float selectedScreenInputOffset = (((selectedTimelineEntryModel.getEndTimePos() - 
													 selectedTimelineEntryModel.getStartTimePos())) / 
												   (timeline.getInputsCount() + 1));
				
				int inputNo = 1;
				
				while (inputsIterator.hasNext())
				{
					InputData inputData = inputsIterator.next();

					Generator inputGenerator = inputData.getInputGenerator();
					
					if (inputGenerator != null)
					{
					TimelineSelectEntryModel inputTimelineEntryModel = this.searchTimelineModel(inputGenerator);

					// Generator found ?
					if (inputTimelineEntryModel != null)
					{	
//						int timelinePos = inputTimelineModel.getTimelinePos();
						int timelinePos = this.timelinesDrawPanelModel.getTimelineSelectEntryPos(inputTimelineEntryModel);
						
						float inputOffsetScreenX = inputNo * selectedScreenInputOffset;

						float inputScreenPosX = inputGenerator.getEndTimePos(); //(int)((inputGenerator.getEndTimePos() - horizontalScrollStart) * scaleX);
						
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
		//------------------------------------------------------------------------------------------
		// Playback Time:
		{
			float playbackTime = this.timelinesDrawPanelModel.getPlaybackTime();
			
			g2.setPaint(CPlaybackTimeLine);
			
			int x = (int)(this.at.getScaleX() * playbackTime);
			int y1 = clipRect.y;
			int y2 = clipRect.y + clipRect.height;
			
			g2.drawLine(x, y1, 
			            x, y2);
			
			g2.setPaint(CPlaybackTimeLineGlow);
			
			g2.drawLine(x - 1, y1, 
			            x - 1, y2);
			g2.drawLine(x + 1, y1, 
			            x + 1, y2);
		}
		//------------------------------------------------------------------------------------------
		// Snap Line:
		{
			double nearestSnapToTimpePos = this.timelinesDrawPanelModel.getNearestSnapToTimpePos();
			
			if (Double.isNaN(nearestSnapToTimpePos) == false)
			{
				if (this.timelinesDrawPanelModel.getHandlerSnaped() == true)
				{
					g2.setPaint(CTimelineHandlerSnapLine);
				}
				else
				{
					g2.setPaint(CTimelineHandlerNearestSnapLine);
				}
				
				int x = (int)(this.at.getScaleX() * nearestSnapToTimpePos);
				int y1 = clipRect.y;
				int y2 = clipRect.y + clipRect.height;
				
				g2.drawLine(x, y1, 
				            x, y2);
	
				if (this.timelinesDrawPanelModel.getHandlerSnaped() == true)
				{
					g2.setPaint(CTimelineHandlerNearestSnapLine);
					
					g2.drawLine(x - 1, y1, 
					            x - 1, y2);
					g2.drawLine(x + 1, y1, 
					            x + 1, y2);
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
	private TimelineSelectEntryModel searchTimelineModel(Generator generator)
	{
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		//------------------------------------------------------------------------------------------
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelinesDrawPanelModel.getTimelineSelectEntryModels(); 
		
		//------------------------------------------------------------------------------------------
		retTimelineSelectEntryModel = null;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			Timeline timeline = timelineSelectEntryModel.getTimeline();
			
			if (timeline.getGenerator() == generator)
			{
				retTimelineSelectEntryModel = timelineSelectEntryModel;
				break;
			}
		}
		
		//==========================================================================================
		return retTimelineSelectEntryModel;
	}

	/**
	 * @param g2
	 * 			is the Graphics.
	 * @param timelineGeneratorPos
	 * 			is the timeline position.
	 * @param timelineSelectEntryModel
	 * 			is the timeline model.
	 */
	private void paintTimeline(Graphics2D g2, int timelineGeneratorPos, TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		float startTimePos = timelineSelectEntryModel.getStartTimePos();
		float endTimePos = timelineSelectEntryModel.getEndTimePos();
		float generatorPosY = timelineGeneratorPos * maxUnitIncrementY;
		
		float timeLength = endTimePos - startTimePos;
		
		boolean highlighted;
		
		TimelineSelectEntryModel highlightedTimelineSelectEntryModel = timelinesDrawPanelModel.getHighlightedTimelineSelectEntryModel();
		
		if (highlightedTimelineSelectEntryModel == timelineSelectEntryModel)
		{
			highlighted = true;
		}
		else
		{
			highlighted = false;
		}
		
		//------------------------------------------------------------------------------------------
		// Background:
		
		if (timelinesDrawPanelModel.getSelectedTimelineSelectEntryModel() == timelineSelectEntryModel)
		{
			g2.setPaint(CTimelineSelectedBackground);
		}
		else
		{
			if (highlighted == true)
			{
				g2.setPaint(CTimelineHighlightedBackground);
			}
			else
			{
				g2.setPaint(CTimelineNormalBackground);
			}
		}
		
		Rectangle2D rectangle = new Rectangle2D.Float(startTimePos,
		                                              generatorPosY,
		                                              timeLength,
		                                              maxUnitIncrementY);
		
		Shape shape = this.at.createTransformedShape(rectangle);
		
		g2.fill(shape);
		
		//------------------------------------------------------------------------------------------
		// Display signal shapes:
		
		Timeline timeline = timelineSelectEntryModel.getTimeline();

		if (timeline != null)
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			float valueMax = timeline.getValueMax();
			float valueMin = timeline.getValueMin();
			
			float frameRate = timeline.getSoundFrameRate();
			float frameStep = timeLength / (frameRate / DRAW_EVERY_SAMPLE);
	
			float pointSizeX = (float)(1.0F / this.at.getScaleX());
			float pointSizeY = (float)(1.0F / this.at.getScaleY());
			
//			Rectangle2D point = new Rectangle2D.Float();
			Point2D srcPoint = new Point2D.Float();
			Point2D dstPoint = new Point2D.Float();
			
			int lastX = 0;
			int lastY = 0;
			
			for (float timePos = 0.0F; timePos < timeLength; timePos += frameStep)
			{
				long sampleFrame = (long)((startTimePos + timePos) * frameRate);
				
				SoundSample bufSoundSample = timeline.getBufSoundSample(sampleFrame);
				SoundSample soundSample;
				
				if (bufSoundSample != null)
				{
					soundSample = bufSoundSample;
				}
				else
				{
					soundSample = null;//timeline.generateFrameSample(sampleFrame, parentModulGenerator);
					bufSoundSample = null;
				}
				
				if (soundSample != null)
				{
					float posX = (startTimePos + timePos); 

					float value = soundSample.getMonoValue();
					float posY;
					
					if (Float.isNaN(value) == false)
					{
						if (value > 0.0F)
						{
							if (Float.isNaN(valueMax) == false)
							{
								value /= valueMax;
							}
						}
						else
						{
							if (value < 0.0F)
							{
								if (Float.isNaN(valueMin) == false)
								{
									value /= -valueMin;
								}
							}
						}
						
						posY = (generatorPosY + 
								(value * maxUnitIncrementY * -0.5F) + 
								(maxUnitIncrementY / 2.0F)); 
					}
					else
					{
						posY = 0.0F;
					}
					
					srcPoint.setLocation(pointSizeX, pointSizeY);
					
//					Shape shape = this.at.createTransformedShape(point);
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
						
						g2.setColor(CTimelineSignal);
						
	//					g2.fill(shape);
						g2.drawLine(lastX, lastY, 
						            x, y);
					}
					
					lastX = x;
					lastY = y;
					
					if (bufSoundSample != null)
					{
						g2.setColor(CTimelineBuffer);
						
						int gy = (int)(generatorPosY * this.at.getScaleY());
						
						g2.drawLine(x, gy, 
						            x, gy + 1);
					}
				}
			}
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (highlighted == true)
			{
				Rectangle2D bounds2D = shape.getBounds2D();
				
				TimelineHandlerModel timelineHandlerModel = this.makeTimelineHandlerModel(bounds2D);
				
				g2.setColor(CTimelineHandlerBackground);
				
				g2.fill(timelineHandlerModel.getRect1());
				g2.fill(timelineHandlerModel.getRect2());
				
				g2.setColor(CTimelineHandlerBorder);
				
				g2.draw(timelineHandlerModel.getRect1());
				g2.draw(timelineHandlerModel.getRect2());
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		//==========================================================================================
	}

	/**
	 * @param bounds2D
	 * 			are the bounds of the timeline.
	 * @return
	 * 			the TimelineHandlerModel.
	 */
	private TimelineHandlerModel makeTimelineHandlerModel(Rectangle2D bounds2D)
	{
		//==========================================================================================
		double posX = bounds2D.getX();
		double posY = bounds2D.getY();
		
		double width = bounds2D.getWidth();
		double height = bounds2D.getHeight();
		
		int h = (int)height;
		int w = (int)width;

		int x1 = (int)posX;
		int y1 = (int)(posY + h / 2 - TimelineHandlerSizeX / 2);

		int x2 = (int)(posX + w - TimelineHandlerSizeX);
		int y2 = (int)(posY + h / 2 - TimelineHandlerSizeX / 2);
		
		Rectangle rect1 = new Rectangle(x1, y1, 
		    				            TimelineHandlerSizeX, TimelineHandlerSizeY);
		
		Rectangle rect2 = new Rectangle(x2, y2, 
		    				            TimelineHandlerSizeX, TimelineHandlerSizeY);
		
		TimelineHandlerModel timelineHandlerModel = new TimelineHandlerModel(rect1,
		                                                                     rect2);
		//==========================================================================================
		return timelineHandlerModel;
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
		//==========================================================================================
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

		//==========================================================================================
		return nearestTick;
	}

	private int calcNearestTick(int direction, int currentPosition, int maxUnitIncrement)
	{
		//==========================================================================================
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

		//==========================================================================================
		return nearestTick;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
										   int orientation,
										   int direction) 
	{
		//==========================================================================================
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
		//==========================================================================================
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
	private TimelineSelectEntryModel searchTimeline(Point2D point2D)
	{
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		retTimelineSelectEntryModel = null;
		
		int maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		double generatorPosY = 0.0D;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : this.timelinesDrawPanelModel.getTimelineSelectEntryModels())
		{
			if ((point2D.getY() >= generatorPosY) &&
				(point2D.getY() <= (generatorPosY + maxUnitIncrementY)))
			{
				retTimelineSelectEntryModel = timelineSelectEntryModel;
				break;
			}
			
			generatorPosY += maxUnitIncrementY;
		}
		
		//==========================================================================================
		return retTimelineSelectEntryModel;
	}

	/**
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			is the generator at given point.
	 */
	private TimelineSelectEntryModel searchGenerator(Point2D point2D)
	{
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		TimelineSelectEntryModel timelineSelectEntryModel = this.searchTimeline(point2D);
		
		if (timelineSelectEntryModel != null)
		{
			float startTimePos = timelineSelectEntryModel.getStartTimePos();
			float endTimePos = timelineSelectEntryModel.getEndTimePos();
			
			if ((point2D.getX() >= startTimePos) &&
				(point2D.getX() <= endTimePos))
			{
				retTimelineSelectEntryModel = timelineSelectEntryModel;
			}
			else
			{
				retTimelineSelectEntryModel = null;
			}
		}
		else
		{
			retTimelineSelectEntryModel = null;
		}
		
		//==========================================================================================
		return retTimelineSelectEntryModel;
	}
//
//	/**
//	 * Notify the {@link #doTimelineSelectedListeners}.
//	 */
//	public void notifyDoTimelineSelectedListeners(TimelineSelectEntryModel timelineGeneratorModel)
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
	public void notifyDoChangeTimelinesPositionListeners(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
	                                                     TimelineSelectEntryModel newTimelineSelectEntryModel)
	{
		//==========================================================================================
		for (DoChangeTimelinesPositionListenerInterface doTimelineSelectedListener : this.doChangeTimelinesPositionListeners)
		{
			doTimelineSelectedListener.changeTimelinesPosition(selectedTimelineSelectEntryModel,
			                                                   newTimelineSelectEntryModel);
		};
		//==========================================================================================
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
		//==========================================================================================
		double width = 0.0D;
		double height = 0.0D;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : this.timelinesDrawPanelModel.getTimelineSelectEntryModels())
		{
			float endTimePos = timelineSelectEntryModel.getEndTimePos();
			
			if (endTimePos > width)
			{
				width = endTimePos;
			}
			
			height += this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		}
		
		// Scale:
		
		this.timelinesDrawPanelModel.setDimensionSize(width * timelinesDrawPanelModel.getZoomX(), 
		                                              height * INIT_ZOOM_Y);
		//==========================================================================================
	}
	
//	public Dimension getPreferredSize()
//	{
//		return new Dimension(1500, 1000);
//	}

	private double searchNearestSnapToTimpePos(TimelinesDrawPanelModel timelinesDrawPanelModel, 
	                                           TimelineSelectEntryModel highlightedTimelineSelectEntryModel,
	                                           double timePos)
	{
		//==========================================================================================
		double snapToTimpePos = Double.MAX_VALUE;//Double.NaN;
		
//		double nearestTimePos = Double.MAX_VALUE;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : this.timelinesDrawPanelModel.getTimelineSelectEntryModels())
		{
			Timeline timeline = timelineSelectEntryModel.getTimeline();
			
			if (timeline != null)
			{
				float startTimePos = timeline.getGeneratorStartTimePos();
				float endTimePos = timeline.getGeneratorEndTimePos();
				
	//			if (highlightedTimelineSelectEntryModel != timelineSelectEntryModel)
				{
					snapToTimpePos = this.calcNearestPos(timePos, snapToTimpePos, startTimePos);
					snapToTimpePos = this.calcNearestPos(timePos, snapToTimpePos, endTimePos);
				}
			}
		}

		//------------------------------------------------------------------------------------------
		TicksPer ticksPer = timelinesDrawPanelModel.getTicksPer();
		Float ticksCount = timelinesDrawPanelModel.getTicksCount();
		
		float tickPos;
		
		switch (ticksPer)
		{
			case Seconds:
			{
				tickPos = (float)(Math.round(timePos * ticksCount) / ticksCount);
				break;
			}
			case Milliseconds:
			{
				tickPos = (float)(Math.round(timePos * ticksCount * 1000.0D) / (ticksCount * 1000.0D));
				break;
			}
			case BPM:
			{
				tickPos = (float)(Math.round(timePos * ticksCount * (1.0D / 60.0D)) / (ticksCount * (1.0D / 60.0D)));
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected ticksPer \"" + ticksPer + "\".");
			}
		}
		
		snapToTimpePos = this.calcNearestPos(timePos, snapToTimpePos, tickPos);
		
//		if (nearestTimePos < 6.0D)
//		{
//			snapToTimpePos = nearestTimePos;
//		}
//		else
//		{
//			snapToTimpePos = timePos;
//		}
		
		//==========================================================================================
		return snapToTimpePos;
	}
	
	/**
	 * @param pos
	 * 			is the actual pos of mouse.
	 * @param lastNearestPos
	 * 			is the nearest pos found bevor.
	 * @param newPos
	 * 			is the new pos looking for.
	 * @return
	 * 			the nearest position of lastNearestPos and newPos to pos.
	 */
	private double calcNearestPos(double pos, double lastNearestPos, double newPos)
	{
		//==========================================================================================
		double ret;
		
		double lastDif = Math.abs(lastNearestPos - pos);
		
		double newDif = Math.abs(newPos - pos);
		
		if (lastDif <= newDif)
		{
			ret = lastNearestPos;
		}
		else
		{
			ret = newPos;
		}
		//==========================================================================================
//		System.out.println(pos + ", " + lastNearestPos + ", " + newPos + ", " + ret);
		return ret;
	}
}
