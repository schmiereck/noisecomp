package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.screenTools.controller.ControllerData;
import de.schmiereck.screenTools.controller.DataChangedObserver;

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
	public InputWidgetData(ControllerData controllerData,
							  DataChangedObserver dataChangedObserver,
						   String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(controllerData, dataChangedObserver, 
			  posX, posY, sizeX, sizeY, true);
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
