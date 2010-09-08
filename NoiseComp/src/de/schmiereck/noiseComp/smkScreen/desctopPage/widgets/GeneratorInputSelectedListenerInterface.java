package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import de.schmiereck.noiseComp.generator.InputData;

/**
 * Generator-Input Selected Listener Interface.
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public interface GeneratorInputSelectedListenerInterface
{
	/**
	 * @param selectedInputData
	 */
	void notifyGeneratorInputSelected(InputsWidgetData inputsData, InputData selectedInputData);

	/**
	 * @param deselectedInputData
	 */
	void notifyGeneratorInputDeselected(InputsWidgetData inputsData, InputData deselectedInputData);
}
