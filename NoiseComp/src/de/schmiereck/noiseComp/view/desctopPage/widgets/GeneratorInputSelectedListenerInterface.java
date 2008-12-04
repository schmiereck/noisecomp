package de.schmiereck.noiseComp.view.desctopPage.widgets;

import de.schmiereck.noiseComp.generator.InputData;

/**
 * TODO docu
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
