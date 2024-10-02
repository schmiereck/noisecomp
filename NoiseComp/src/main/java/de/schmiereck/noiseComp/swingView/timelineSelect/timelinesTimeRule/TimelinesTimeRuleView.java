/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData.TicksPer;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule.TimeMarkerSelectEntryModel.MarkerType;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

/**
 * <p>
 * 	Timelines-Time Rule View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class TimelinesTimeRuleView
extends JComponent
{
	//**********************************************************************************************
	// Constats:

	/**
	 * Marker box size Y.
	 */
	private static final int MARKER_BOX_SIZE_Y	= 12;

	/**
	 * Marker box offset Y.
	 */
	private static final int MARKER_BOX_OFFSET_Y	= 0;

	/**
	 * Marker size X.
	 */
	private static final int MARKER_SIZE_X	= 8;
	
	/**
	 * Marker size Y.
	 */
	private static final int MARKER_SIZE_Y	= 11;

	/**
	 * Marker Play offset Y.
	 */
	private static final int MARKER_OFFSET_Y = 1;
	
	/**
	 * Marker Play size X.
	 */
	private static final int MARKER_PLAY_SIZE_X = 13;

	/**
	 * Marker Play offset Y.
	 */
	private static final int MARKER_PLAY_OFFSET_Y = 25;
	
	/**
	 * Marker Play size Y.
	 */
	private static final int MARKER_PLAY_SIZE_Y = 8;
	
	//	public static final int	DPI		= 20;//Toolkit.getDefaultToolkit().getScreenResolution();
	public static final int	SIZE	= 35;

	/**
	 * Color - Background (dirty brown/orange).
	 */
	private static final Color COLOR_BACKGROUND	= new Color(0xFFF1E1); //230, 163, 4);
	
	/**
	 * Color - Time Border.
	 */
	private static final Color COLOR_TIME_BORDER	= new Color(0x808080);
	
	/**
	 * Color - Time Background.
	 */
	private static final Color COLOR_TIME_BACKGROUND	= new Color(0xFFFFFF);
	
	/**
	 * Color - Marker light border.
	 */
	private static final Color COLOR_MARKER_LIGHT_BORDER	= new Color(0xA0A0A0);
	
	/**
	 * Color - Marker bright border.
	 */
	private static final Color COLOR_MARKER_BRIGHT_BORDER	= new Color(0x808080);
	
	/**
	 * Color - Marker dark border.
	 */
	private static final Color COLOR_MARKER_DARK_BORDER	= new Color(0x202020);
	
	/**
	 * Color - Marker Background.
	 */
	private static final Color COLOR_MARKER_BACKGROUND	= new Color(0x606060);
	
	/**
	 * Color - Marker Background.
	 */
	private static final Color COLOR_MARKER_TIME_BACKGROUND	= new Color(0xD0D0D0);
	
	//**********************************************************************************************
	// Fields:

	/**
	 * Timelines-Time-Rule Model.
	 */
	private final TimelinesTimeRuleModel timelinesTimeRuleModel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param playTimeMarkerMovedCommand
	 * 			is the Play-Time-Marker Moved Command.
	 */
	public TimelinesTimeRuleView(final SoundService soundService,
								 final TimelinesTimeRuleModel timelinesTimeRuleModel,
	                             final PlayTimeMarkerMovedCommand playTimeMarkerMovedCommand)
	{
		//==========================================================================================
		this.timelinesTimeRuleModel = timelinesTimeRuleModel;
		
		//------------------------------------------------------------------------------------------
		this.addMouseListener
		(
		 	new TimelinesTimeRuleMouseListener(timelinesTimeRuleModel,
		 	                                   this)
		);
		this.addMouseMotionListener
		(
		 	new TimelinesTimeRuleMouseMotionListener(soundService,
												     timelinesTimeRuleModel,
		 	                                         this,
		 	                                         playTimeMarkerMovedCommand)
		);
		//==========================================================================================
	}

//	public void setIsMetric(boolean isMetric)
//	{
//		this.isMetric = isMetric;
//		this.setIncrementAndUnits();
//		this.repaint();
//	}

//	private void setIncrementAndUnits()
//	{
//		this.units = DPI;
//		this.increment = DPI / 2;
//	}
//
//	public int getIncrement()
//	{
//		return this.increment;
//	}

	public void setWidth(int pw)
	{
		//==========================================================================================
		Dimension dimension = new Dimension(pw, SIZE);
		
		//this.setSize(dimension);
		this.setPreferredSize(dimension);
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		//==========================================================================================
		//super.paintComponent(g);
		 
		Graphics2D g2 = (Graphics2D)g;
		   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
	   
		//------------------------------------------------------------------------------------------
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area with dirty brown/orange.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		//System.out.println(drawHere.x + ", " + drawHere.width);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);

//		int units = this.timelinesTimeRuleModel.getUnits();
//		int increment = this.timelinesTimeRuleModel.getIncrement();
		float zoomX = this.timelinesTimeRuleModel.getZoomX();

		float tickPerSecond = this.calcTickPerSecond();
		
		float ticksCount = this.timelinesTimeRuleModel.getTicksCount();
		
		String unitLabel;
		{
			TicksPer ticksPer = this.timelinesTimeRuleModel.getTicksPer();
			
			switch (ticksPer)
			{
				case Seconds:
				{
					unitLabel = " s";
					break;
				}
				case Milliseconds:
				{
					unitLabel = " ms";
					break;
				}
				case BPM:
				{
					unitLabel = " bpm";
					break;
				}
				default:
				{
					throw new RuntimeException("Unexpected TicksPer \"" + ticksPer + "\".");
				}
			}
		}
		
		float tickSize = (1.0F / (tickPerSecond * ticksCount)) * zoomX;
		 
		// Use clipping bounds to calculate first and last tick locations.
		int startTickNo	= (int)((drawHere.x / tickSize));
		int endTickNo 	= (int)((drawHere.x + drawHere.width) / tickSize) + 1;

		// ticks and labels
		for (int tickPos = startTickNo; tickPos < endTickNo; tickPos += 1)
		{
			int tickLineLength;
			String text;
			int textPosX;
			
			boolean showText;
			
			if (tickSize < 30)
			{
				int tickMod = ((int)(30 / tickSize) + 1);
				
				if ((tickPos % tickMod) == 0)
				{
					showText = true;
				}
				else
				{
					showText = false;
				}
			}
			else
			{
				showText = true;
			}
			
			if (showText == true)
			{
				tickLineLength = 10;
			}
			else
			{
				tickLineLength = 5;
			}
			
			//text = Integer.toString((int)(tickPos));
			//text = Integer.toString((int)(tickPos / ticksCount));
			text = OutputUtils.makeFloatText((tickPos / ticksCount), 2);
			
			if (tickPos == 0)
			{
				// Make a special case of 0 to display the number
				// within the rule and draw a units label.
				
				text += unitLabel;
				textPosX = 0;
			}
			else
			{
				textPosX = -3;
			}
			
			g.drawLine((int)(tickPos * tickSize), SIZE - 1, 
			           (int)(tickPos * tickSize), SIZE - tickLineLength - 1);
			
			if (text != null)
			{
				if (showText == true)
				{
					g.drawString(text, 
					             (int)(tickPos * tickSize) + textPosX, 
					             21);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		TimelineSelectEntriesModel timelineSelectEntriesModel = this.timelinesTimeRuleModel.getTimelineSelectEntriesModel();
		
		TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();
		
		//------------------------------------------------------------------------------------------
		double startTimeMarker = startTimeMarkerSelectEntryModel.getTimeMarker();
		double playTimeMarker = playTimeMarkerSelectEntryModel.getTimeMarker();
		double endTimeMarker = endTimeMarkerSelectEntryModel.getTimeMarker();
		
		double endTime = timelineSelectEntriesModel.getEndTime();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Marker box:
		{
			g.setColor(COLOR_TIME_BORDER);
			
			g.draw3DRect((int)(0.0D * zoomX), MARKER_BOX_OFFSET_Y, 
			             (int)(endTime * zoomX), MARKER_BOX_SIZE_Y, 
			             false);
			
			g.setColor(COLOR_TIME_BACKGROUND);
			
			g.fillRect((int)(0.0D * zoomX) + 1, MARKER_BOX_OFFSET_Y + 1, 
			           (int)(endTime * zoomX) - 2, MARKER_BOX_SIZE_Y - 2);

			g.setColor(COLOR_MARKER_TIME_BACKGROUND);
			
			int startX = (int)(startTimeMarker * zoomX) + 1;
			int endX = (int)(endTimeMarker * zoomX) - 1 - startX;
			
			g.fillRect(startX, 4, 
			           endX, MARKER_SIZE_Y - 8);
		}

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Start-Marker:
		{
			int ltx = (int)(startTimeMarker * zoomX);
			int lty = MARKER_OFFSET_Y;
			int lbx = ltx;
			int lby = MARKER_OFFSET_Y + MARKER_SIZE_Y - 1;
			int lhx = ltx + MARKER_SIZE_X;
			int lhy = lty + ((MARKER_SIZE_Y - 2) / 2);
			
			g.setColor(COLOR_MARKER_BACKGROUND);
			
			int xPoints[] = { ltx, lbx, lhx };
			int yPoints[] = { lty, lby, lhy };
			
			g.fillPolygon(xPoints, yPoints, 3);
			
			g.setColor(COLOR_MARKER_LIGHT_BORDER);
			
			g.drawLine(ltx, lty, 
			           lbx, lby);
			
			g.setColor(COLOR_MARKER_BRIGHT_BORDER);
			
			g.drawLine(ltx, lty, 
			           lhx, lhy);
			
			g.setColor(COLOR_MARKER_DARK_BORDER);
			
			g.drawLine(lbx, lby, 
			           lhx, lhy);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// End-Marker:
		{
			int ltx = (int)(endTimeMarker * zoomX);
			int lty = 1;
			int lbx = ltx;
			int lby = MARKER_OFFSET_Y + MARKER_SIZE_Y - 1;
			int lhx = ltx - MARKER_SIZE_X;
			int lhy = lty + ((MARKER_SIZE_Y - 2) / 2);
			
			g.setColor(COLOR_MARKER_BACKGROUND);
			
			int xPoints[] = { ltx, lbx, lhx };
			int yPoints[] = { lty, lby, lhy };
			
			g.fillPolygon(xPoints, yPoints, 3);
			
			g.setColor(COLOR_MARKER_DARK_BORDER);
			
			g.drawLine(ltx, lty, 
			           lbx, lby);
			
			g.setColor(COLOR_MARKER_LIGHT_BORDER);
			
			g.drawLine(ltx, lty, 
			           lhx, lhy);
			
			g.setColor(COLOR_MARKER_BRIGHT_BORDER);
			
			g.drawLine(lbx, lby, 
			           lhx, lhy);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Play-Marker:
		{
			int bx = (int)(playTimeMarker * zoomX);
			int by = MARKER_PLAY_OFFSET_Y + MARKER_PLAY_SIZE_Y;
			int ltx = bx - (MARKER_PLAY_SIZE_X / 2);
			int lty = MARKER_PLAY_OFFSET_Y;
			int rtx = ltx + MARKER_PLAY_SIZE_X;
			int rty = MARKER_PLAY_OFFSET_Y;
			
			g.setColor(COLOR_MARKER_BACKGROUND);
			
			int xPoints[] = { ltx, rtx, bx };
			int yPoints[] = { lty, rty, by };
			
			g.fillPolygon(xPoints, yPoints, 3);
			
			g.setColor(COLOR_MARKER_LIGHT_BORDER);
			
			g.drawLine(ltx, lty, 
			           rtx, rty);
			
			g.setColor(COLOR_MARKER_BRIGHT_BORDER);
			
			g.drawLine(ltx, lty, 
			           bx, by);
			
			g.setColor(COLOR_MARKER_DARK_BORDER);
			
			g.drawLine(rtx, rty, 
			           bx, by);
		}
		//==========================================================================================
	}

	/**
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			is the Time-Marker at given point.<br/>
	 * 			<code>null</code> if no marker is at the given point.
	 */
	public TimeMarkerSelectEntryModel searchTimeMarker(Point2D point2D)
	{
		//==========================================================================================
		TimeMarkerSelectEntryModel timeMarkerSelectEntryModel;
		
		// Search left or right Time-Marker.
		TimeMarkerSelectEntryModel startTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getStartTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel playTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getPlayTimeMarkerSelectEntryModel();
		TimeMarkerSelectEntryModel endTimeMarkerSelectEntryModel = this.timelinesTimeRuleModel.getEndTimeMarkerSelectEntryModel();

		// Start Marker?
		if (this.checkHitMarker(startTimeMarkerSelectEntryModel, point2D) == true)
		{
			timeMarkerSelectEntryModel = startTimeMarkerSelectEntryModel;
		}
		else
		{
			if (this.checkHitMarker(playTimeMarkerSelectEntryModel, point2D) == true)
			{
				timeMarkerSelectEntryModel = playTimeMarkerSelectEntryModel;
			}
			else
			{
				if (this.checkHitMarker(endTimeMarkerSelectEntryModel, point2D) == true)
				{
					timeMarkerSelectEntryModel = endTimeMarkerSelectEntryModel;
				}
				else
				{
					timeMarkerSelectEntryModel = null;
				}
			}
		}
		//==========================================================================================
		return timeMarkerSelectEntryModel;
	}

	/**
	 * @return
	 * 		the tiks per second.
	 */
	public float calcTickPerSecond()
	{
		float tickPerSecond;
		
		TicksPer ticksPer = this.timelinesTimeRuleModel.getTicksPer();
		
		switch (ticksPer)
		{
			case Seconds:
			{
				tickPerSecond = 1.0F;
				break;
			}
			case Milliseconds:
			{
				tickPerSecond = 1000.0F;
				break;
			}
			case BPM:
			{
				tickPerSecond = 1.0F / 60.0F;
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected TicksPer \"" + ticksPer + "\".");
			}
		}
		return tickPerSecond;
	}

	/**
	 * @return
	 * 			the tick size in pixel.
	 */
	public float calcTickSize()
	{
		//==========================================================================================
		float zoomX = this.timelinesTimeRuleModel.getZoomX();
		float ticksCount = this.timelinesTimeRuleModel.getTicksCount();
		
		float tickPerSecond = this.calcTickPerSecond();
		
		float tickSize = (1.0F / (tickPerSecond * ticksCount)) * zoomX;
		
		//==========================================================================================
		return tickSize;
	}

	/**
	 * @param timeMarkerSelectEntryModel
	 * 			is the marker model.
	 * @param point2D
	 * 			is the point.
	 * @return
	 * 			<code>true</code> if the given marker is at the given point.
	 */
	private boolean checkHitMarker(TimeMarkerSelectEntryModel timeMarkerSelectEntryModel, 
	                               Point2D point2D)
	{
		//==========================================================================================
		boolean hit;
		
		//------------------------------------------------------------------------------------------
		double timeMarker = timeMarkerSelectEntryModel.getTimeMarker();
		MarkerType markerType = timeMarkerSelectEntryModel.getMarkerType();

		double markerOffsetX;
		double markerOffsetY;
		double markerSizeX;
		double markerSizeY;
		
		switch (markerType)
		{
			case START:
			{
				markerOffsetX = 0.0D;
				markerOffsetY = 0.0D;
				markerSizeX = MARKER_SIZE_X;
				markerSizeY = MARKER_SIZE_Y;
				break;
			}
			case POS:
			{
				markerOffsetX = -(MARKER_PLAY_SIZE_X / 2);
				markerOffsetY = MARKER_PLAY_OFFSET_Y;
				markerSizeX = MARKER_PLAY_SIZE_X;
				markerSizeY = MARKER_PLAY_SIZE_Y;
				break;
			}
			case END:
			{
				markerOffsetX = -MARKER_SIZE_X;
				markerOffsetY = 0.0D;
				markerSizeX = MARKER_SIZE_X;
				markerSizeY = MARKER_SIZE_Y;
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected marker type \"" + markerType + "\".");
			}
		}
		
		//------------------------------------------------------------------------------------------
//		float tickSize = this.calcTickSize();
		float zoomX = this.timelinesTimeRuleModel.getZoomX();
		 
		//------------------------------------------------------------------------------------------
		double x = point2D.getX();
		double y = point2D.getY();
		
		if (x >= ((timeMarker * zoomX) + markerOffsetX))
		{
			if (x <= ((timeMarker * zoomX) + markerOffsetX + markerSizeX))
			{
				if (y >= (markerOffsetY))
				{
					if (y <= (markerOffsetY + markerSizeY))
					{
						hit = true;
					}
					else
					{
						hit = false;
					}
				}
				else
				{
					hit = false;
				}
			}
			else
			{
				hit = false;
			}
		}
		else
		{
			hit = false;
		}
		
		//==========================================================================================
		return hit;
	}
}
