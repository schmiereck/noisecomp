package de.schmiereck.noiseComp.view.desctopPage;

import de.schmiereck.noiseComp.view.desctopPage.widgets.MainActionException;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public interface SubmitWidgetListenerInterface
{

	/**
	 * Is called if the widget have focus and the page is submited (normaly by pressing the ENTER-Key).
	 */
	void notifySubmit()
	throws MainActionException;

}
