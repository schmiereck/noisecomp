package de.schmiereck.noiseComp.desktop;

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

	private int scrollbarWidth			= 20;
	private int scrollbarWidth2			= 15;

	/**
	 * @see #pointerPosX
	 * @see #pointerPosY
	 */
	public void setPointerPos(int posX, int posY)
	{
		this.pointerPosX = posX;
		this.pointerPosY = posY;
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
