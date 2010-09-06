package de.schmiereck.noiseComp.smkScreen.desktop;

import de.schmiereck.screenTools.graphic.GraphicMediator;

/**
 * Manages the date of the desktop.<br/>
 * Specialy:<br/>
 * 	The main mouse position.
 *
 * @author smk
 * @version 21.02.2004
 */
public class DesktopData
{
	
	/**
	 * X-Position des Mauszeigers.
	 * @see #pointerPosY
	 */
	private int pointerPosX = 0;

	/**
	 * Y-Position des Mauszeigers.
	 * @see #pointerPosX
	 */
	private int pointerPosY = 0;

	/**
	 * Breite 1 der Scrollbars.
	 */
	private int scrollbarWidth			= 20;

	/**
	 * Breite 2 der Scrollbars.
	 */
	private int scrollbarWidth2			= 15;

	/**
	 * Daten der Screen Grafik.
	 */
	private GraphicMediator graphicMediator;
	
	/**
	 * Constructor.
	 * 
	 */
	public DesktopData(GraphicMediator graphicMediator)
	{
		this.graphicMediator = graphicMediator;
	}

	/**
	 * @param posX
	 * 			to set {@link #pointerPosX}. 
	 * @param posY 
	 * 			to set {@link #pointerPosY}. 
	 * @return
	 *  		<code>true</code> if position is changed.
	 */
	public boolean setPointerPos(int posX, int posY)
	{
		boolean ret;

		if ((this.pointerPosX != posX) || (this.pointerPosY != posY))
		{
			ret = true;
		}
		else
		{
			ret = false;
		}

		// hier den offset aus MultiBufferFullScreenGraphic#initScreen der bounds abziehen
		
		this.pointerPosX = posX - this.graphicMediator.getPointerOffsetX();
		this.pointerPosY = posY - this.graphicMediator.getPointerOffsetY();
		
		return ret;
	}

	/**
	 * @return the attribute {@link #pointerPosX}.
	 */
	public int getPointerPosX()
	{
		return this.pointerPosX;
	}
	/**
	 * @return the attribute {@link #pointerPosY}.
	 */
	public int getPointerPosY()
	{
		return this.pointerPosY;
	}
	/**
	 * @return the attribute {@link #scrollbarWidth}.
	 */
	public int getScrollbarWidth()
	{
		return this.scrollbarWidth;
	}
	/**
	 * @return the attribute {@link #scrollbarWidth2}.
	 */
	public int getScrollbarWidth2()
	{
		return this.scrollbarWidth2;
	}
}
