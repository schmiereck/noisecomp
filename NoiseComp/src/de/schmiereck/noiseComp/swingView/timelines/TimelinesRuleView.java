/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JComponent;

/**
 * <p>
 * 	Rule View.
 * </p>
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class TimelinesRuleView
extends JComponent
{
	public static final int	INCH		= Toolkit.getDefaultToolkit().getScreenResolution();
	public static final int	HORIZONTAL	= 0;
	public static final int	VERTICAL	= 1;
	public static final int	SIZE		= 35;
	public int				orientation;
	public boolean			isMetric;
	private int				increment;
	private int				units;

	/**
	 * Constructor.
	 * 
	 * @param orientation
	 * 			is the orientation.
	 * @param isMetric
	 * 			<code>true</code> if is Metric.
	 */
	public TimelinesRuleView(int orientation, boolean isMetric)
	{
		this.orientation = orientation;
		this.isMetric = isMetric;
		this.setIncrementAndUnits();
	}

	public void setIsMetric(boolean isMetric)
	{
		this.isMetric = isMetric;
		this.setIncrementAndUnits();
		this.repaint();
	}

	private void setIncrementAndUnits()
	{
		if (this.isMetric)
		{
			this.units = (int)((double)INCH / (double)2.54); // dots per centimeter
			this.increment = units;
		}
		else
		{
			this.units = INCH;
			this.increment = units / 2;
		}
	}

	public boolean isMetric()
	{
		return this.isMetric;
	}

	public int getIncrement()
	{
		return this.increment;
	}

	public void setPreferredHeight(int ph)
	{
		setPreferredSize(new Dimension(SIZE, ph));
	}

	public void setPreferredWidth(int pw)
	{
		setPreferredSize(new Dimension(pw, SIZE));
	}

	protected void paintComponent(Graphics g)
	{
		Rectangle drawHere = g.getClipBounds();
		
		// Fill clipping area with dirty brown/orange.
		g.setColor(new Color(230, 163, 4));
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
		
		// Do the ruler labels in a small font that's black.
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.setColor(Color.black);
		
		// Some vars we need.
		int end = 0;
		int start = 0;
		int tickLength = 0;
		String text = null;
		// Use clipping bounds to calculate first and last tick locations.
		if (this.orientation == HORIZONTAL)
		{
			start = (drawHere.x / this.increment) * this.increment;
			end = (((drawHere.x + drawHere.width) / this.increment) + 1) * this.increment;
		}
		else
		{
			start = (drawHere.y / this.increment) * this.increment;
			end = (((drawHere.y + drawHere.height) / this.increment) + 1) * this.increment;
		}
		// Make a special case of 0 to display the number
		// within the rule and draw a units label.
		if (start == 0)
		{
			text = Integer.toString(0) + (this.isMetric ? " cm" : " in");
			tickLength = 10;
			if (this.orientation == HORIZONTAL)
			{
				g.drawLine(0, SIZE - 1, 0, SIZE - tickLength - 1);
				g.drawString(text, 2, 21);
			}
			else
			{
				g.drawLine(SIZE - 1, 0, SIZE - tickLength - 1, 0);
				g.drawString(text, 9, 10);
			}
			text = null;
			start = this.increment;
		}
		// ticks and labels
		for (int i = start; i < end; i += this.increment)
		{
			if (i % this.units == 0)
			{
				tickLength = 10;
				text = Integer.toString(i / units);
			}
			else
			{
				tickLength = 7;
				text = null;
			}
			if (tickLength != 0)
			{
				if (this.orientation == HORIZONTAL)
				{
					g.drawLine(i, SIZE - 1, i, SIZE - tickLength - 1);
					if (text != null)
						g.drawString(text, i - 3, 21);
				}
				else
				{
					g.drawLine(SIZE - 1, i, SIZE - tickLength - 1, i);
					if (text != null)
						g.drawString(text, 9, i + 3);
				}
			}
		}
	}
}
