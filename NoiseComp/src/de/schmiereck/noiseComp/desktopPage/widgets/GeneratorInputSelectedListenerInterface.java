package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.generator.InputData;

/**
 * Informs the listener object, if a input is selected or deselected.
 *
 * @author smk
 * @version 08.02.2004
 */
public interface GeneratorInputSelectedListenerInterface
{

	/**
	 * @param selectedInputData
	 */
	void notifyGeneratorInputSelected(InputsData inputsData, InputData selectedInputData);

	/**
	 * @param deselectedInputData
	 */
	void notifyGeneratorInputDeselected(InputsData inputsData, InputData deselectedInputData);

}
