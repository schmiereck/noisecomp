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
}
