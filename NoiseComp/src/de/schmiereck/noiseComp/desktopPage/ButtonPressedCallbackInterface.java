package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;

/**
 * Callback-Interface über das gemeldet wird, wenn ein Button gedrückt wurde.
 *
 * @author smk
 * @version 01.02.2004
 */
public interface ButtonPressedCallbackInterface
{

	/**
	 * @param pressedButtonData
	 */
	void buttonPressed(ButtonData pressedButtonData);

}
