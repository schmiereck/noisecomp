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
	 */
	public ButtonData(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(posX, posY, sizeX, sizeY);
		this.name = name;
	}
	/**
	 * @return the attribute {@link #name}.
	 */
	public String getName()
	{
		return this.name;
	}
}
