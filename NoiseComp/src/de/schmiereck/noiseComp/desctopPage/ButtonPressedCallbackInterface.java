package de.schmiereck.noiseComp.desctopPage;

import de.schmiereck.noiseComp.desctopPage.widgets.ButtonData;

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
