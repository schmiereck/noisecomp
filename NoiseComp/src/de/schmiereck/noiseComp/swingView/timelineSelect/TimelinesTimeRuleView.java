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

/**
 * <p>
 * 	Timelines-Time Rule View.
 * </p>
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

	public void setPreferredHeight(int ph)
	{
		setPreferredSize(new Dimension(SIZE, ph));
	}

	public void setPreferredWidth(int pw)
	{
		setPreferredSize(new Dimension(pw, SIZE));
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		int units = this.timelinesTimeRuleModel.getUnits();
		int increment = this.timelinesTimeRuleModel.getIncrement();
		
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area with dirty brown/orange.
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);

		// Use clipping bounds to calculate first and last tick locations.
		int start = ((drawHere.x / increment) * increment);
		int end = ((((drawHere.x + drawHere.width) / increment) + 1) * increment);

		// ticks and labels
		for (int tickPos = start; tickPos < end; tickPos += increment)
		{
			int tickLength;
			String text;
			int textPosX;
			
			if (tickPos % units == 0)
			{
				tickLength = 10;
				text = Integer.toString((int)(tickPos / units));
				
				if (tickPos == 0)
				{
					// Make a special case of 0 to display the number
					// within the rule and draw a units label.
					text += " s";
					textPosX = 0;
				}
				else
				{
					textPosX = tickPos - 3;
				}

			}
			else
			{
				tickLength = 5;
				text = null;
				textPosX = 0;
			}
			
			g.drawLine(tickPos, SIZE - 1, tickPos, SIZE - tickLength - 1);
			
			if (text != null)
			{
				g.drawString(text, textPosX, 21);
			}
		}
	}
}
