/*
 * www.schmiereck.de (c) 2010
 */
package demo2;

import java.awt.Rectangle;

/**
 * <p>
 * 	Draw Panel Model.
 * </p>
 * 
 * @author smk
 * @version <p>03.11.2010:	created, smk</p>
 */
public class DrawPanelModel
{
	private Rectangle	rect;

	/**
	 * @return 
	 * 			returns the {@link #rect}.
	 */
	public Rectangle getRect()
	{
		return this.rect;
	}

	/**
	 * @param rect 
	 * 			to set {@link #rect}.
	 */
	public void setRect(Rectangle rect)
	{
		this.rect = rect;
	}
}
