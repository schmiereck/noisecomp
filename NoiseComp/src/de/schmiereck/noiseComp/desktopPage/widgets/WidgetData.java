package de.schmiereck.noiseComp.desktopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 26.01.2004
 */
public class WidgetData
{
	private int posX;
	private int posY;
	private int sizeX;
	private int sizeY;
	private boolean acceptFocus;
	private boolean haveFocus = false;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public WidgetData(int posX, int posY, int sizeX, int sizeY, boolean acceptFocus)
	{
		super();
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.acceptFocus = acceptFocus;
	}
	/**
	 * @return the attribute {@link #sizeY}.
	 */
	public int getSizeY()
	{
		return this.sizeY;
	}
	/**
	 * @return the attribute {@link #posX}.
	 */
	public int getPosX()
	{
		return this.posX;
	}
	/**
	 * @return the attribute {@link #posY}.
	 */
	public int getPosY()
	{
		return this.posY;
	}
	/**
	 * @return the attribute {@link #sizeX}.
	 */
	public int getSizeX()
	{
		return this.sizeX;
	}
	/**
	 * @see #acceptFocus
	 */
	public boolean getAcceptFocus()
	{
		return this.acceptFocus;
	}
	/**
	 * @see #acceptFocus
	 */
	public void setAcceptFocus(boolean acceptFocus)
	{
		this.acceptFocus = acceptFocus;
	}
	/**
	 * @return the attribute {@link #haveFocus}.
	 */
	public boolean getHaveFocus()
	{
		return this.haveFocus;
	}
	/**
	 * @param haveFocus is the new value for attribute {@link #haveFocus} to set.
	 */
	public void setHaveFocus(boolean haveFocus)
	{
		this.haveFocus = haveFocus;
	}
}
