package de.schmiereck.noiseComp.view.desctopPage.widgets;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;

/**
 * Informs the listener object, if a input is selected or deselected.
 *
 * @author smk
 * @version 08.02.2004
 */
public interface GeneratorTypeSelectedListenerInterface
{

	/**
	 * @param selectedGeneratorTypeData
	 */
	void notifyGeneratorTypeSelected(GeneratorTypesWidgetData generatorTypesWidgetData, GeneratorTypeData selectedGeneratorTypeData);

	/**
	 * @param deselectedGeneratorTypeData
	 */
	void notifyGeneratorTypeDeselected(GeneratorTypesWidgetData generatorTypesWidgetData, GeneratorTypeData deselectedGeneratorTypeData);

}
