/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.MultiValue;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.InputEntryTargetModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler.TimelineHandlerModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimelinesTimeRuleModel;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;
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
	
	//==============================================================================================
	// Input-Marker:
	
	/**
	 * 
	 */
	private static final int	INPUT_MARKER_SIZE_X	= 7;

	/**
	 * 
	 */
	private static final int	INPUT_MARKER_SIZE_Y	= 8;

	//==============================================================================================
	// Zoom:
	
	/**
	 * Initial Zoom factor X.
	 */
	private static final double	INIT_ZOOM_X	= 40.0D;
	
	/**
	 * Initial Zoom factor Y.
	 */
	public static final double	INIT_ZOOM_Y	= 1.0D;

	//==============================================================================================
	// Colors:
	
	//----------------------------------------------------------------------------------------------
	private static final Color CBackground = Color.WHITE;
	private static final Color CBackgroundLines = new Color(0xFFF7E1);
	
	//----------------------------------------------------------------------------------------------
	// Timeline:
	
	private static final Color CTimelineNormalBackground = new Color(0xFFF0C9);//Color.LIGHT_GRAY;
	private static final Color CTimelineSelectedBackground = new Color(0xBFB7A1);//128, 128, 192);
	private static final Color CTimelineHighlightedBackground = new Color(0xFFE6C9);//128, 198, 192);
	private static final Color CTimelineSignal = new Color(0x1A, 0x23, 0x74, 160);//0x42, 0x82, 0xA4, 160);//0, 0, 0, 160);
	
	//----------------------------------------------------------------------------------------------
	// Timeline-Buffer:
	
	private static final Color CTimelineBuffer = new Color(0xDFD0A9);

	//----------------------------------------------------------------------------------------------
	// Input-Connector:
	
	private static final Color CTimelineInputConnector = new Color(0xA67841);//255, 0, 0, 192);
	private static final Color CTimelineInputHighlightedConnector = new Color(0xC69861);
	private static final Color CTimelineInputDisabledConnector = new Color(0xFF9751);
	private static final Color CTimelineInputReadyConnector = new Color(0xC6C891);
	private static final Color CTimelineInputConnectorBackground = new Color(0xB67841);
	private static final Color CTimelineInputHighlightedConnectorBackground = new Color(0xE6A871);
	private static final Color CTimelineInputSelectedConnectorBackground = new Color(0xDFA881);
	private static final Color CTimelineInputText = new Color(0xF6B881);
	private static final Color CTimelineInputHighlightedText = new Color(0x566501);
	private static final Color CTimelineInputSelectedText = new Color(0x566501);
	
	// Strokes:
	
	private static final BasicStroke BSTimelineInputConnector	= new BasicStroke();
	private static final BasicStroke BSTimelineInputSelectedConnector	= new BasicStroke(3.0F);
	
	// Input-Group:
	
	private static final BasicStroke BSimelineInputConnectorGroup	= new BasicStroke();
	private static final Color CTimelineInputConnectorGroup = new Color(0xF6C891);
	private static final Color CTimelineInputConnectorGroupBackground = new Color(0xC6, 0x98, 0x61, 0xC0);

	//----------------------------------------------------------------------------------------------
	// Timeline-Handler:
	
	private static final Color CTimelineHandlerBorder = new Color(0xA1B4BE);//200, 200, 255);
	private static final Color CTimelineHandlerBackground = new Color(0x4282A4);//100, 100, 100);
	private static final Color CTimelineHandlerNearestSnapLine = new Color(0x16, 0x76, 0x43, 64);//0, 0, 200, 200);
	private static final Color CTimelineHandlerSnapLine = new Color(0x90CB4F);//60, 60, 255);
	
	//----------------------------------------------------------------------------------------------
	// Playback-Line:
	
	private static final Color CPlaybackTimeLine = new Color(0x561360);
	private static final Color CPlaybackTimeLineGlow = new Color(0xBE, 0x6F, 0xC9, 128);
	
	//==============================================================================================
	private final float DRAW_EVERY_SAMPLE = 4.0F;//1.0F;//25.0F;
	
	private static final int TimelineHandlerSizeX = 8;
	private static final int TimelineHandlerSizeY = 8;
	
	private final Cursor defaultCursor;
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * @return 
	 * 			returns the {@link #defaultCursor}.
	 */
	public Cursor getDefaultCursor()
	{
		return this.defaultCursor;
	}

	/**
	 * Timeline Draw Panel Model.
	 */
	private final TimelinesDrawPanelModel timelinesDrawPanelModel;
	
	/**
	 * Timelines-Time-Rule Model.
	 */
	private final TimelinesTimeRuleModel timelinesTimeRuleModel;
	
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
	 * @return 
	 * 			returns the {@link #at}.
	 */
	public AffineTransform getAt()
	{
		return this.at;
	}
 	
	//**********************************************************************************************
	// Functions:
	
	/**
	* Constructor.
	* 
	* @param timelinesDrawPanelModel
	* 			is the Timeline Draw Panel Model.
	 * @param timelinesTimeRuleModel
	 * 			is the Timelines-Time-Rule Model.
	*/
	public TimelinesDrawPanelView(final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                              final TimelinesTimeRuleModel timelinesTimeRuleModel) 
	{
		//==========================================================================================
		this.timelinesDrawPanelModel = timelinesDrawPanelModel;
		
		this.timelinesTimeRuleModel = timelinesTimeRuleModel;
		
		//------------------------------------------------------------------------------------------
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		SelectedTimelineModel selectedTimelineModel = 
			this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//------------------------------------------------------------------------------------------
	    this.setPreferredSize(this.timelinesDrawPanelModel.getDimension());
		
		this.setBackground(CBackground);

		// Let the user scroll by dragging to outside the window.
		this.setAutoscrolls(true); //enable synthetic drag events
//		this.addMouseMotionListener(this); //handle mouse drags
		
		this.defaultCursor = getCursor();
		
		ToolTipManager.sharedInstance().registerComponent(this);
		
		//------------------------------------------------------------------------------------------
		this.addMouseListener
		(
		 	new TimelinesDrawMouseListener(timelinesDrawPanelModel,
		 	                                this)
		);
		this.addMouseMotionListener
		(
		 	new TimelinesDrawMouseMotionListener(timelinesDrawPanelModel,
		 	                                      this)
		);
		//------------------------------------------------------------------------------------------
		selectedTimelineModel.getSelectedTimelineChangedNotifier().addModelPropertyChangedListener
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
		timelineSelectEntriesModel.getTimelineGeneratorModelsChangedNotifier().addModelPropertyChangedListener
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
		timelineSelectEntriesModel.getChangeTimelinesPositionChangedNotifier().addModelPropertyChangedListener
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
	public Point2D mousePos(Point point)
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
		final SelectedTimelineModel selectedTimelineModel = 
			this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		final TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		final Graphics2D g2 = (Graphics2D)g;
	   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
	   
		Rectangle clipRect = g.getClipBounds();

		//------------------------------------------------------------------------------------------
		float maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		//------------------------------------------------------------------------------------------
		List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModels(); 
		
		TimelineSelectEntryModel selectedTimelineEntryModel = selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
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
		if (selectedTimelineEntryModel != null)
		{
			this.paintTimelineInputConnectors(g2, 
			                                  timelineSelectEntriesModel, 
			                                  selectedTimelineEntryModel, 
			                                  selectedPos);
		}
		//------------------------------------------------------------------------------------------
		// Playback Time:
		{
			TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
			
			double playbackTime = playTimeMarkerSelectEntryModel.getTimeMarker();
			
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
		//------------------------------------------------------------------------------------------
		// Input-Target:
		
		final InputEntryTargetModel inputEntryTargetModel = selectedTimelineModel.getInputEntryTargetModel();
		
		if (inputEntryTargetModel != null)
		{
			InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();
			
			if (selectedInputEntry != null)
			{
				final InputPosEntriesModel inputPosEntriesModel = 
					selectedTimelineModel.getInputPosEntriesModel();

				final TimelineSelectEntryModel targetTimelineSelectEntryModel = 
					inputEntryTargetModel.getTargetTimelineSelectEntryModel();
				
				float selectedScreenPosX = selectedTimelineEntryModel.getStartTimePos();
				float selectedScreenInputOffset = 
					this.calcSelectedScreenInputOffset(selectedTimelineModel,
					                                   selectedTimelineEntryModel);
				
				int entryHeight = this.timelinesDrawPanelModel.getMaxUnitIncrementY();

				int inputNo = inputPosEntriesModel.searchInputEntryPos(selectedInputEntry);
				
				float inputOffsetScreenX = inputNo * selectedScreenInputOffset;
				
				float inp1X = selectedScreenPosX + inputOffsetScreenX;
				float inp1Y = selectedPos * entryHeight;

				Point2D point2D = inputEntryTargetModel.getTargetPoint2D();
				
				g2.setStroke(BSTimelineInputSelectedConnector);
				
				if (targetTimelineSelectEntryModel != null)
				{
					if (inputEntryTargetModel.getTargetEnabled() == true)
					{
						g2.setPaint(CTimelineInputReadyConnector);
					}
					else
					{
						g2.setPaint(CTimelineInputDisabledConnector);
					}
				}
				else
				{
					g2.setPaint(CTimelineInputHighlightedConnector);
				}
				
				Line2D line = new Line2D.Float(inp1X, inp1Y, 
				                               (float)point2D.getX(), (float)point2D.getY());
				g2.draw(this.at.createTransformedShape(line));
			}
		}
		//==========================================================================================
	}

	/**
	 * @param g2
	 * 			is the graphic.
	 * @param timelineSelectEntriesModel
	 * 			are the timelines.
	 * @param selectedTimelineEntryModel
	 * 			is the selected timeline.
	 * @param selectedPos
	 * 			is the position of the selected timeline.
	 */
	private void paintTimelineInputConnectors(Graphics2D g2, 
	                                          TimelineSelectEntriesModel timelineSelectEntriesModel,
	                                          TimelineSelectEntryModel selectedTimelineEntryModel, 
	                                          int selectedPos)
	{
		//==========================================================================================
		final SelectedTimelineModel selectedTimelineModel = this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		int entryHeight = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		Timeline timeline = selectedTimelineEntryModel.getTimeline();
		
		if (timeline != null)
		{
			//--------------------------------------------------------------------------------------
			// Input-Types:
			
//			Generator generator = timeline.getGenerator();
//			
//			GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
//			
//			InputTypesData inputTypesData = generatorTypeData.getInputTypesData();
//			
//			for (int inputTypePos = 0; inputTypePos < inputTypesData.getInputTypesSize(); inputTypePos++)
//			{
//				InputTypeData inputTypeData = inputTypesData.getInputTypeDataByPos(inputTypePos);
//				
//			}
			
			//--------------------------------------------------------------------------------------
//			Iterator<InputData> inputsIterator = timeline.getInputsIterator();
			
			// Calculates the Positions of Inputs.
			final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
			
			final int retEntryCnt = inputPosEntriesModel.getSumEntryCnt();
			
//			if (inputsIterator != null)
//			if (inputEntriesModel.getSize() > 0)
			if (retEntryCnt > 0)
			{
				//----------------------------------------------------------------------------------
				float selectedScreenPosX = selectedTimelineEntryModel.getStartTimePos();
				float selectedScreenInputOffset = 
					this.calcSelectedScreenInputOffset(selectedTimelineModel,
					                                   selectedTimelineEntryModel);

				//----------------------------------------------------------------------------------
				// Inputs:
				
//				Iterator<InputEntryModel> inputEntryModelsIterator = inputEntryModels.iterator();
				final List<InputPosEntriesModel> groupInputPosEntries = inputPosEntriesModel.getInputPosEntries();
				
				int inputNo = 1;
				
//				while (inputsIterator.hasNext())
//				while (inputEntryModelsIterator.hasNext())
				for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
				{
					final List<InputPosEntriesModel> inputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
					
					{
						Rectangle2D groupRect = this.calcInputPosGroupRect(selectedPos, 
						                                                   entryHeight, 
						                                                   selectedScreenPosX,
						                                                   selectedScreenInputOffset, 
						                                                   inputNo,
						                                                   groupInputPosEntriesModel);
						
						Shape tGroupRect = this.at.createTransformedShape(groupRect);
						
						g2.setStroke(BSimelineInputConnectorGroup);
						g2.setPaint(CTimelineInputConnectorGroupBackground);
						g2.fill(tGroupRect);
						g2.setPaint(CTimelineInputConnectorGroup);
						g2.draw(tGroupRect);
					}
					
//					InputData inputData = inputsIterator.next();
//					InputEntryModel inputEntryModel = inputEntryModelsIterator.next();
					
					if (inputPosEntries.size() > 0)
					{
						for (InputPosEntriesModel inputPosEntryModel : inputPosEntries)
						{
	//						InputData inputData = inputEntryModel.getInputData();
							InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
							
							InputData inputData;
							
							if (inputEntryModel != null)
							{
								inputData = inputEntryModel.getInputData();
							}
							else
							{
								inputData = null;
							}
							
							float inputOffsetScreenX = inputNo * selectedScreenInputOffset;
							
							float inp1X = selectedScreenPosX + inputOffsetScreenX; //(int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
							float inp1Y = selectedPos * entryHeight; //(int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + selectedPos * entryHeight);
								
							boolean highlighted = this.checkInputHighlighted(selectedTimelineModel, inputEntryModel);
							boolean selected = this.checkInputSelected(selectedTimelineModel, inputEntryModel);
							boolean addInput;
							
							if (inputData != null)
							{
								Generator inputGenerator = inputData.getInputGenerator();
								
								// Have Input-Generator?
								if (inputGenerator != null)
								{
									this.paintTimelineInputConnectorLine(g2, 
									                                     timelineSelectEntriesModel, 
									                                     inputGenerator,
									                                     highlighted, selected, 
									                                     entryHeight, 
									                                     inp1X, inp1Y);
								}
								
								addInput = false;
							}
							else
							{
								addInput = true;
							}

							this.paintTimelineInputConnectorHandler(g2, 
							                                        highlighted, selected, 
							                                        addInput,
							                                        inp1X, inp1Y);
							inputNo++;
						}
					}
					else
					{
						inputNo++;
					}
				}
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}

	/**
	 * @param selectedTimelineModel
	 * @param selectedTimelineEntryModel
	 * @return
	 * 			is the offset between the input pos entries.
	 */
	public float calcSelectedScreenInputOffset(final SelectedTimelineModel selectedTimelineModel,
	                                           final TimelineSelectEntryModel selectedTimelineEntryModel)
	{
		//==========================================================================================
		final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
		
		final int retEntryCnt = inputPosEntriesModel.getSumEntryCnt();
		
		float selectedScreenInputOffset = (((selectedTimelineEntryModel.getEndTimePos() - 
											 selectedTimelineEntryModel.getStartTimePos())) / 
										   (retEntryCnt + 1));
		
		//==========================================================================================
		return selectedScreenInputOffset;
	}

	/**
	 * @param selectedPos
	 * @param entryHeight
	 * @param selectedScreenPosX
	 * @param selectedScreenInputOffset
	 * @param inputNo
	 * @param groupInputPosEntriesModel
	 * @return
	 */
	private Rectangle2D calcInputPosGroupRect(int selectedPos, int entryHeight, float selectedScreenPosX,
												float selectedScreenInputOffset, int inputNo,
												InputPosEntriesModel groupInputPosEntriesModel)
	{
		int groupSumEntryCnt = groupInputPosEntriesModel.getSumEntryCnt();

		float groupX = selectedScreenPosX + 
						(inputNo * selectedScreenInputOffset) - 
						(selectedScreenInputOffset * 0.25F);
		float groupY = selectedPos * entryHeight - 1.0F;
		float groupW = groupSumEntryCnt * selectedScreenInputOffset -
						(selectedScreenInputOffset * 0.5F);
		float groupH = INPUT_MARKER_SIZE_Y + 2.0F;

		Rectangle2D groupRect = new Rectangle2D.Float(groupX, groupY, groupW, groupH);
		return groupRect;
	}

	/**
	 * @param g2
	 * 			is the graphic.
	 * @param timelineSelectEntriesModel
	 * 			are the timelines.
	 * @param inputGenerator
	 * @param highlighted
	 * 			<code>true</code> if the input is highlighted.
	 * @param selected
	 * 			<code>true</code> if the input is selected.
	 * @param entryHeight
	 * @param inp1X
	 * 			is the input position X.
	 * @param inp1Y
	 * 			is the input position Y.
	 */
	private void paintTimelineInputConnectorLine(Graphics2D g2, 
	                                             TimelineSelectEntriesModel timelineSelectEntriesModel,
	                                             Generator inputGenerator, 
	                                             boolean highlighted, boolean selected,
	                                             int entryHeight, 
	                                             float inp1X, float inp1Y)
	{
		//==========================================================================================
		TimelineSelectEntryModel inputTimelineEntryModel = this.searchTimelineModel(inputGenerator);

		// Timeline of Input-Generator found ?
		if (inputTimelineEntryModel != null)
		{	
//							int timelinePos = inputTimelineModel.getTimelinePos();
			int timelinePos = timelineSelectEntriesModel.getTimelineSelectEntryPos(inputTimelineEntryModel);
			
			float inputScreenPosX = inputGenerator.getEndTimePos(); //(int)((inputGenerator.getEndTimePos() - horizontalScrollStart) * scaleX);
			
			float inp2X = inputScreenPosX; //(int)(tracksData.getGeneratorsLabelSizeX() + inputScreenPosX);
			float inp2Y = timelinePos * entryHeight + entryHeight / 2.0F; //(int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + timelinePos * entryHeight + entryHeight / 2);
			
			if (highlighted == true)
			{
				g2.setPaint(CTimelineInputHighlightedConnector);
			}
			else
			{
				g2.setPaint(CTimelineInputConnector);
			}
			
			if (selected == true)
			{
				g2.setStroke(BSTimelineInputSelectedConnector);
			}
			else
			{
				g2.setStroke(BSTimelineInputConnector);
			}
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
		}
		//==========================================================================================
	}

	/**
	 * @param g2
	 * 			is the graphic.
	 * @param highlighted
	 * 			<code>true</code> if the input is highlighted.
	 * @param selected
	 * 			<code>true</code> if the input is selected.
	 * @param addInput
	 * 			<code>true</code> if it is the the Add-Input Entry.
	 * @param inp1X
	 * 			is the input position X.
	 * @param inp1Y
	 * 			is the input position Y.
	 */
	private void paintTimelineInputConnectorHandler(Graphics2D g2, 
	                                                boolean highlighted, boolean selected, boolean addInput,
	                                                float inp1X, float inp1Y)
	{
		//==========================================================================================
		if (selected == true)
		{
			g2.setPaint(CTimelineInputSelectedConnectorBackground);
		}
		else
		{
			if (highlighted == true)
			{
				g2.setPaint(CTimelineInputHighlightedConnectorBackground);
			}
			else
			{
				g2.setPaint(CTimelineInputConnectorBackground);
			}
		}
		
		Point2D inpPoint = new Point2D.Float(inp1X, inp1Y);
		Point2D tInpPoint = this.at.transform(inpPoint, null);
		
		int imX = (int)(tInpPoint.getX() - INPUT_MARKER_SIZE_X / 2);
		int imY = (int)(tInpPoint.getY());
		
		g2.fillRect(imX, 
		            imY, 
		            INPUT_MARKER_SIZE_X, 
		            INPUT_MARKER_SIZE_Y);
		
		boolean raised;
		
		if (selected == true)
		{
			raised = false;
		}
		else
		{
			raised = true;
		}
		
		g2.draw3DRect(imX, 
		              imY, 
		              INPUT_MARKER_SIZE_X, 
		              INPUT_MARKER_SIZE_Y, 
		              raised);
		
		if (addInput == true)
		{
			if (selected == true)
			{
				g2.setPaint(CTimelineInputSelectedText);
			}
			else
			{
				if (highlighted == true)
				{
					g2.setPaint(CTimelineInputHighlightedText);
				}
				else
				{
					g2.setPaint(CTimelineInputText);
				}
			}
			
			g2.drawString("+", 
			              imX + 1, 
			              imY + INPUT_MARKER_SIZE_Y);
		}
		
		//==========================================================================================
	}

	/**
	 * @param selectedTimelineModel
	 * 			is the selected timeline.
	 * @param inputEntryModel
	 * 			is the inputEntryModel.
	 * @return
	 * 			<code>true</code> if the input is selected.
	 */
	private boolean checkInputSelected(final SelectedTimelineModel selectedTimelineModel, 
	                                   final InputEntryModel inputEntryModel)
	{
		//==========================================================================================
		boolean selected;
		
		if (selectedTimelineModel != null)
		{
			InputEntryModel selectedInputEntry = selectedTimelineModel.getSelectedInputEntry();
			
			if (selectedInputEntry != null)
			{
//				InputData selectedInputData = selectedInputEntry.getInputData();
//				
//				if (inputData == selectedInputData)
				if (inputEntryModel == selectedInputEntry)
				{
					selected = true;
				}
				else
				{
					selected = false;
				}
			}
			else
			{
				selected = false;
			}
		}
		else
		{
			selected = false;
		}
		//==========================================================================================
		return selected;
	}

	/**
	 * @param selectedTimelineModel
	 * 			is the selected timeline.
	 * @param inputEntryModel
	 * 			is the inputEntryModel.
	 * @return
	 * 			<code>true</code> if the input is highlighted.
	 */
	private boolean checkInputHighlighted(final SelectedTimelineModel selectedTimelineModel, 
	                                      final InputEntryModel inputEntryModel)
	{
		//==========================================================================================
		boolean highlighted;
		
		if (selectedTimelineModel != null)
		{
			InputEntryModel highlightedInputEntry = selectedTimelineModel.getHighlightedInputEntry();

			if (highlightedInputEntry != null)
			{
//				InputData highlightedInputData = highlightedInputEntry.getInputData();
//				
//				if (inputData == highlightedInputData)
				if (inputEntryModel == highlightedInputEntry)
				{
					highlighted = true;
				}
				else
				{
					highlighted = false;
				}
			}
			else
			{
				highlighted = false;
			}
		}
		else
		{
			highlighted = false;
		}
		//==========================================================================================
		return highlighted;
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
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		//------------------------------------------------------------------------------------------
		List<TimelineSelectEntryModel> timelineSelectEntryModels = timelineSelectEntriesModel.getTimelineSelectEntryModels(); 
		
		//------------------------------------------------------------------------------------------
		retTimelineSelectEntryModel = null;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
		{
			Timeline timeline = timelineSelectEntryModel.getTimeline();
			
			if (timeline != null)
			{
				if (timeline.getGenerator() == generator)
				{
					retTimelineSelectEntryModel = timelineSelectEntryModel;
					break;
				}
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
		SelectedTimelineModel selectedTimelineModel = timelinesDrawPanelModel.getSelectedTimelineModel();
		
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
		
		if (selectedTimelineModel.getSelectedTimelineSelectEntryModel() == timelineSelectEntryModel)
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
	
//			float pointSizeX = (float)(1.0F / this.at.getScaleX());
//			float pointSizeY = (float)(1.0F / this.at.getScaleY());
			
//			Rectangle2D point = new Rectangle2D.Float();
			Point2D srcPoint = new Point2D.Float();
			Point2D dstPoint = new Point2D.Float();
			
			boolean haveLastSample = false;
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
					soundSample = null;//timeline.generateFrameSample(sampleFrame, parentModuleGenerator);
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
								(value * maxUnitIncrementY * -0.45F) + 
								(maxUnitIncrementY / 2.0F)) +
								1.0F; 
					}
					else
					{
						if (haveLastSample == true)
						{
							break;
						}
						posY = 0.0F;
					}
					
//					Shape shape = this.at.createTransformedShape(point);
					srcPoint.setLocation(posX, posY);
					this.at.transform(srcPoint, dstPoint);
					
					int x = (int)dstPoint.getX();
					int y = (int)dstPoint.getY();
					
					if (haveLastSample == true)
					{
						// x or y changed after last draw?
						if ((x != lastX) || (y != lastY))
						{
							// Draw line.
							
							g2.setColor(CTimelineSignal);
							
		//					g2.fill(shape);
							g2.drawLine(lastX, lastY, 
							            x, y);
						}
					}
					else
					{
						haveLastSample = true;
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
	public TimelineHandlerModel makeTimelineHandlerModel(Rectangle2D bounds2D)
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
	 * 			is the Timeline at given point.<br/>
	 * 			<code>null</code> if no timeline is found.
	 */
	public TimelineSelectEntryModel searchTimeline(Point2D point2D)
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		TimelineSelectEntryModel retTimelineSelectEntryModel;
		
		retTimelineSelectEntryModel = null;
		
		int maxUnitIncrementY = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
		
		double generatorPosY = 0.0D;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntriesModel.getTimelineSelectEntryModels())
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
	 * 			is the generator at given point.<br/>
	 * 			<code>null</code> if no generator is selected.
	 */
	public TimelineSelectEntryModel searchGenerator(Point2D point2D)
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
//
//	/**
//	 * Notify the {@link #doChangeTimelinesPositionListeners}.
//	 */
//	public void notifyDoChangeTimelinesPositionListeners(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
//	                                                     TimelineSelectEntryModel newTimelineSelectEntryModel)
//	{
//		//==========================================================================================
//		for (DoChangeTimelinesPositionListenerInterface doTimelineSelectedListener : this.doChangeTimelinesPositionListeners)
//		{
//			doTimelineSelectedListener.changeTimelinesPosition(selectedTimelineSelectEntryModel,
//			                                                   newTimelineSelectEntryModel);
//		};
//		//==========================================================================================
//	}

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
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		double width = 0.0D;
		double height = 0.0D;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntriesModel.getTimelineSelectEntryModels())
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

	public double searchNearestSnapToTimpePos(TimelinesDrawPanelModel timelinesDrawPanelModel, 
	                                          TimelineSelectEntryModel highlightedTimelineSelectEntryModel,
	                                          double timePos)
	{
		//==========================================================================================
		TimelineSelectEntriesModel timelineSelectEntriesModel = 
			this.timelinesDrawPanelModel.getTimelineSelectEntriesModel();
		
		//==========================================================================================
		double snapToTimpePos = Double.MAX_VALUE;//Double.NaN;
		
//		double nearestTimePos = Double.MAX_VALUE;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntriesModel.getTimelineSelectEntryModels())
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

	/**
	 * @param selectedTimelineModel
	 * 			is the selected Timeline Model.
	 * @param point
	 * 			is the mouse position.
	 * @param point2D
	 * 			is the mouse position.
	 * @return
	 * 			the Input-Pos-Entry Model at given position.<br/>
	 * 			<code>null</code> if no input found.
	 */
	public InputPosEntriesModel searchInputEntry(final SelectedTimelineModel selectedTimelineModel, 
	                                             final Point point,
	                                             final Point2D point2D)
	{
		//==========================================================================================
		final TimelineSelectEntryModel selectedTimelineEntryModel = 
			selectedTimelineModel.getSelectedTimelineSelectEntryModel();
		
		//==========================================================================================
		InputPosEntriesModel retInputPosEntryModel;

		//------------------------------------------------------------------------------------------
		if (selectedTimelineEntryModel != null)
		{
			int entryHeight = this.timelinesDrawPanelModel.getMaxUnitIncrementY();
			
			Timeline timeline = selectedTimelineEntryModel.getTimeline();
			
			if (timeline != null)
			{
				// Position of selected timeline.
				int selectedTimelinePos = this.timelinesDrawPanelModel.calcTimelinePos(selectedTimelineEntryModel);
				
				// Calculates the Positions of Inputs.
				final InputPosEntriesModel inputPosEntriesModel = selectedTimelineModel.getInputPosEntriesModel();
				
				final int retEntryCnt = inputPosEntriesModel.getSumEntryCnt();
				
				if (retEntryCnt > 0)
				{
					//----------------------------------------------------------------------------------
					retInputPosEntryModel = null;
					
					//----------------------------------------------------------------------------------
					float selectedScreenPosX = selectedTimelineEntryModel.getStartTimePos();
					float selectedScreenInputOffset = 
						this.calcSelectedScreenInputOffset(selectedTimelineModel,
						                                   selectedTimelineEntryModel);

					//----------------------------------------------------------------------------------
					// Inputs:
					
//					Iterator<InputEntryModel> inputEntryModelsIterator = inputEntryModels.iterator();
					List<InputPosEntriesModel> groupInputPosEntries = inputPosEntriesModel.getInputPosEntries();
					
					int inputNo = 1;
					
					outerloop:
					{
	//					while (inputsIterator.hasNext())
	//					while (inputEntryModelsIterator.hasNext())
						for (InputPosEntriesModel groupInputPosEntriesModel : groupInputPosEntries)
						{
	//						InputData inputData = inputsIterator.next();
	//						InputEntryModel inputEntryModel = inputEntryModelsIterator.next();
							List<InputPosEntriesModel> inputPosEntries = groupInputPosEntriesModel.getInputPosEntries();
							
							for (InputPosEntriesModel inputPosEntryModel : inputPosEntries)
							{
	//							InputData inputData = inputEntryModel.getInputData();
//								InputEntryModel inputEntryModel = inputPosEntryModel.getInputEntryModel();
	//							InputData inputData = inputEntryModel.getInputData();
							
								float inputOffsetScreenX = inputNo * selectedScreenInputOffset;
								
								float inp1X = selectedScreenPosX + inputOffsetScreenX; //(int)(tracksData.getGeneratorsLabelSizeX() + selectedScreenPosX + inputOffsetScreenX);
								float inp1Y = selectedTimelinePos * entryHeight; //(int)(posY - ((int)(verticalScrollerStart + 1) * entryHeight) + selectedPos * entryHeight);
								
		//						Generator inputGenerator = inputData.getInputGenerator();
								
								{
									Point2D inpPoint = new Point2D.Float(inp1X, inp1Y);
									Point2D tInpPoint = this.at.transform(inpPoint, null);
									
									int imX = (int)(tInpPoint.getX() - INPUT_MARKER_SIZE_X / 2);
									int imY = (int)(tInpPoint.getY());
									
									Rectangle2D rectangle = new Rectangle2D.Float(imX,
									                                              imY,
									                                              INPUT_MARKER_SIZE_X,
									                                              INPUT_MARKER_SIZE_Y);
									
									if (rectangle.contains(point) == true)
									{
										retInputPosEntryModel = inputPosEntryModel;
										break outerloop;
									}
								}
								inputNo++;
							}
						}
					}
				}
				else
				{
					retInputPosEntryModel = null;
				}
			}
			else
			{
				retInputPosEntryModel = null;
			}
		}
		else
		{
			retInputPosEntryModel = null;
		}
		//==========================================================================================
		return retInputPosEntryModel;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event)
	{
		//==========================================================================================
//		final AffineTransform at = this.timelinesDrawPanelView.getAt();
		
		final SelectedTimelineModel selectedTimelineModel = 
			this.timelinesDrawPanelModel.getSelectedTimelineModel();
		
		//==========================================================================================
		String tooltipText;
		
		if (selectedTimelineModel != null)
		{
			final InputEntryModel highlightedInputEntry = selectedTimelineModel.getHighlightedInputEntry();
			
			if (highlightedInputEntry != null)
			{
				final InputEntryGroupModel inputEntryGroupModel = highlightedInputEntry.getInputEntryGroupModel();
				
				tooltipText = inputEntryGroupModel.getInputTypeData().getInputTypeName();;
				
				final InputData inputData = highlightedInputEntry.getInputData();
				
				if (inputData != null)
				{
					final Generator inputGenerator = inputData.getInputGenerator();
					
					if (inputGenerator != null)
					{
						tooltipText += ": " + inputGenerator.getName();
					}
					else
					{
						MultiValue multiValue = new MultiValue();

						multiValue.floatValue = inputData.getInputValue();
						multiValue.stringValue = inputData.getInputStringValue();
						
						tooltipText += ": " + OutputUtils.makeMultiValueEditText(multiValue);
					}
				}
				else
				{
					tooltipText += ": [+]";
				}
			}
			else
			{
				tooltipText = null;
			}
		}
		else
		{
			tooltipText = null;
		}
		
		//==========================================================================================
		return tooltipText;
	}
}
