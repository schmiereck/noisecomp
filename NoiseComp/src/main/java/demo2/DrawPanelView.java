/*
 * www.schmiereck.de (c) 2010
 */
package demo2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * <p>
 * 	Draw Panel View.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class DrawPanelView
extends JPanel
{
	private DrawPanelModel drawPanelModel = new DrawPanelModel();
	
	Dimension		SIZE	= new Dimension(1500, 1000);
	GradientPaint	paint	= new GradientPaint(0, 0, 
	             	     	                    Color.WHITE, 
	             	     	                    SIZE.width, SIZE.height, 
	             	     	                    Color.BLACK);

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(java.awt.Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(paint);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		Rectangle rect = this.drawPanelModel.getRect();
		
		if (rect != null)
		{
			g2.setPaint(new GradientPaint(rect.x, rect.y, 
			                              Color.BLACK, 
			                              rect.x + rect.width, rect.y + rect.height, 
			                              Color.WHITE));
			g2.fill(rect);
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(2));
			g2.draw(rect);
			g2.drawString("You wanted this rectangle to be visible",
			              rect.x + 5, rect.y + 20);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1, 
			                             BasicStroke.CAP_BUTT,
			                             BasicStroke.JOIN_BEVEL, 3, 
			                             new float[]
			                             {
											3
										}, 
										1.0f));
			g2.drawLine(0, rect.y, rect.x, rect.y);
			g2.drawLine(0, rect.y + rect.height, rect.x, rect.y + rect.height);
			g2.drawLine(rect.x, 0, rect.x, rect.y);
			g2.drawLine(rect.x + rect.width, 0, rect.x + rect.width, rect.y);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize()
	{
		return SIZE;
	}

	/**
	 * @return 
	 * 			returns the {@link #drawPanelModel}.
	 */
	public DrawPanelModel getDrawPanelModel()
	{
		return this.drawPanelModel;
	}
}
