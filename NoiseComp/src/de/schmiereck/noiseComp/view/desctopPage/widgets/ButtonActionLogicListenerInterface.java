package de.schmiereck.noiseComp.view.desctopPage.widgets;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public interface ButtonActionLogicListenerInterface
{
	void notifyButtonReleased(InputWidgetData buttonData) 
	throws MainActionException;
}
