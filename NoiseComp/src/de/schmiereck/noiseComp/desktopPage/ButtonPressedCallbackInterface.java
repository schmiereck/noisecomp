package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopPage.widgets.ButtonData;

/**
 * Callback-Interface �ber das gemeldet wird, wenn ein Button gedr�ckt wurde.
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
