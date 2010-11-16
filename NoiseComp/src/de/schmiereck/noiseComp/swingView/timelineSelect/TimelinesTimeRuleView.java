/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;

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

//	public static final int	DPI		= 20;//Toolkit.getDefaultToolkit().getScreenResolution();
	public static final int	SIZE	= 35;

	/**
	 * Color - Background (dirty brown/orange).
	 */
	private static final Color	COLOR_BACKGROUND	= new Color(230, 163, 4);
	
	//**********************************************************************************************
	// Fields:

	/**
	 * Timelines-Time-Rule Model.
	 */
	@SuppressWarnings("unused")
	private final TimelinesTimeRuleModel timelinesTimeRuleModel;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelinesTimeRuleView(TimelinesTimeRuleModel timelinesTimeRuleModel)
	{
		this.timelinesTimeRuleModel = timelinesTimeRuleModel;
		
//		this.setIncrementAndUnits();
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
		Dimension dimension = new Dimension(pw, SIZE);
		
		//this.setSize(dimension);
		this.setPreferredSize(dimension);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		 
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area with dirty brown/orange.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);

//		int units = this.timelinesTimeRuleModel.getUnits();
//		int increment = this.timelinesTimeRuleModel.getIncrement();
		float zoomX = this.timelinesTimeRuleModel.getZoomX();
		float tickPerSecond;
		
		String unitLabel;
		
		TicksPer ticksPer = this.timelinesTimeRuleModel.getViewTicksPer();
		Float ticksCount = this.timelinesTimeRuleModel.getTicksCount();
		
		switch (ticksPer)
		{
			case Seconds:
			{
				tickPerSecond = 1.0F;
				unitLabel = " s";
				break;
			}
			case Milliseconds:
			{
				tickPerSecond = 1000.0F;
				unitLabel = " ms";
				break;
			}
			case BPM:
			{
				tickPerSecond = 1.0F / 60.0F;
				unitLabel = " bpm";
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected TicksPer \"" + ticksPer + "\".");
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
			
			tickLineLength = 10;
			text = Integer.toString((int)(tickPos));
			
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

//			{
//				tickLineLength = 5;
//				text = null;
//				textPosX = 0;
//			}
			
			g.drawLine((int)(tickPos * tickSize), SIZE - 1, 
			           (int)(tickPos * tickSize), SIZE - tickLineLength - 1);
			
			if (text != null)
			{
				g.drawString(text, 
				             (int)(tickPos * tickSize) + textPosX, 
				             21);
			}
		}
	}
}
