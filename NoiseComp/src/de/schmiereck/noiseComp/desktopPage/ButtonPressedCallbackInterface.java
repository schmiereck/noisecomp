package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;

/**
 * Callback-Interface über das gemeldet wird, wenn ein Button gedrückt wurde.
 *
 *	You can alternatively (better) use the 
 *	{@link de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface}
 *	to catch the button actions.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ButtonPressedCallbackInterface
{

	/**
	 * @param pressedButtonData
	 */
	void buttonPressed(InputWidgetData pressedButtonData);

}
