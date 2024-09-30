/*
 * www.schmiereck.de (c) 2010
 */
package demo2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;

/**
 * <p>
 * 	Horizontal Rule.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class HorizontalRule
extends JComponent
{
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(java.awt.Graphics g)
	{
		Rectangle clip = g.getClipBounds();
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		g2.fill(clip);
		int start = clip.x / 100 * 100;
		int end = ((clip.x + clip.width) / 100 + 1) * 100;
		g2.setColor(Color.WHITE);
		for (int i = start; i < end; i += 100)
		{
			g2.drawLine(i, 20, i, 30);
			g2.drawString(Integer.toString(i), i + 2, 25);
		}
	}
}
