package de.schmiereck.noiseComp.desctopPage;

import de.schmiereck.noiseComp.desctopPage.widgets.ButtonData;

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
