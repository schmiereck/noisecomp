package de.schmiereck.noiseComp.smkScreen.desctopPage.widgets;

import de.schmiereck.noiseComp.generator.InputTypeData;

/**
* Informs the listener object, if a input is selected or deselected.
 *
 * @author smk
 * @version <p>21.03.2004: created, smk</p>
 */
public interface InputTypeSelectedListenerInterface
{

	void notifyInputTypeSelected(InputTypesWidgetData inputTypesWidgetData, InputTypeData inputGeneratorTypeData);

	void notifyInputTypeDeselected(InputTypesWidgetData inputTypesWidgetData, InputTypeData inputGeneratorTypeData);

}
