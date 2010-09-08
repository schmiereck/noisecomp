package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

/**
 * Button Action Logic Listener Interface.
 *
 * @author smk
 * @version 21.02.2004
 */
public interface ButtonActionLogicListenerInterface
{
	void notifyButtonReleased(InputWidgetData buttonData) 
	throws MainActionException;
}
