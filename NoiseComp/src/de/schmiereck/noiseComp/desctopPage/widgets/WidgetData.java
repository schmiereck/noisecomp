package de.schmiereck.noiseComp.desctopPage.widgets;

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
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeX
	 * @param sizeY
	 */
	public WidgetData(int posX, int posY, int sizeX, int sizeY)
	{
		super();
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
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
}
