package de.schmiereck.noiseComp.desktopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 25.01.2004
 */
public class ButtonData
extends WidgetData
{
	private String name;
	
	/**
	 * Constructor.
	 * 
	 * @param posX
	 * @param posY
	 * @param sizeY
	 * @param sizeX
	 * @param labelText
	 */
	public ButtonData(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(posX, posY, sizeX, sizeY);
		this.name = name;
	}
	/**
	 * @return the attribute {@link #labelText}.
	 */
	public String getName()
	{
		return this.name;
	}
}
