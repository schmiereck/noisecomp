package de.schmiereck.noiseComp.desktopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 25.01.2004
 */
public class InputWidgetData
extends WidgetData
{
	private String name;
	private ButtonActionLogicListenerInterface buttonActionLogicListener;
	
	/**
	 * Constructor.
	 * 
	 */
	public InputWidgetData(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(posX, posY, sizeX, sizeY, true);
		this.name = name;
	}
	/**
	 * @return the attribute {@link #name}.
	 */
	public String getName()
	{
		return this.name;
	}
	/**
	 * @see #buttonActionLogicListener
	 */
	public void addActionLogicListener(ButtonActionLogicListenerInterface buttonActionLogicListener)
	{
		this.buttonActionLogicListener = buttonActionLogicListener;
	}
	/**
	 * @return the attribute {@link #buttonActionLogicListener}.
	 */
	public ButtonActionLogicListenerInterface getButtonActionLogicListener()
	{
		return this.buttonActionLogicListener;
	}
}
