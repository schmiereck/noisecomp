/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * <p>
 * 	Timeline Scroll-Panel-View.
 * </p>
 * 
 * see: http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html
 * 
 * @author smk
 * @version <p>17.06.2010:	created, smk</p>
 */
public class TimelineDrawPanelView
extends JPanel 
implements Scrollable//, MouseMotionListener
{
	//**********************************************************************************************
	// Fields:
	
	private int maxUnitIncrementX = 1;
	private int maxUnitIncrementY = 16;

	private List<Rectangle> rects = new Vector<Rectangle>();
	 
	private Color[] colors = 
	{
		Color.RED, Color.ORANGE, Color.CYAN, Color.GREEN,
		Color.MAGENTA, Color.PINK,
		Color.GREEN, Color.RED, Color.ORANGE, Color.black
	};
	
	//**********************************************************************************************
	// Functions:

	/**
	* Constructor.
	* 
	*/
	public TimelineDrawPanelView() 
	{
		this.rects.add(new Rectangle(0, 0, 100, 100));
		this.rects.add(new Rectangle(100, 100, 100, 100));
		this.rects.add(new Rectangle(200, 200, 100, 100));
		this.rects.add(new Rectangle(300, 300, 100, 100));
		this.rects.add(new Rectangle(200, 0, 50, 50));
		this.rects.add(new Rectangle(250, 50, 50, 50));
		this.rects.add(new Rectangle(500, 200, 100, 100));
		this.rects.add(new Rectangle(600, 300, 100, 100));
		this.rects.add(new Rectangle(500, 0, 50, 50));
		this.rects.add(new Rectangle(650, 50, 50, 50));
		
		this.setBackground(Color.LIGHT_GRAY);

		//Let the user scroll by dragging to outside the window.
		this.setAutoscrolls(true); //enable synthetic drag events
//		this.addMouseMotionListener(this); //handle mouse drags
		
		//------------------------------------------------------------------------------------------
		TimelineGeneratorModel generatorModel = new TimelineGeneratorModel();
		
		//------------------------------------------------------------------------------------------
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);  // I was missing this code which caused "bleeding"
	   
		Graphics2D g2 = (Graphics2D) g;
	   
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
	   
		AffineTransform at = AffineTransform.getScaleInstance(1.0D, 1.0D);
	   
		for (Rectangle rectangle : this.rects)
		{
//			g2.setPaint(this.colors[j]);
			g2.setPaint(Color.BLACK);
			g2.fill(at.createTransformedShape(rectangle));
		}
		
		g2.setPaint(Color.red);
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
		
		if (orientation == SwingConstants.HORIZONTAL) 
		{
			currentPosition = visibleRect.x;
			
			nearestTick = calcNearestTick(direction, currentPosition, this.maxUnitIncrementX);
		} 
		else 
		{
			currentPosition = visibleRect.y;
			
			nearestTick = calcNearestTick(direction, currentPosition, this.maxUnitIncrementY);
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

}
