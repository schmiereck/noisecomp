package de.schmiereck.noiseComp.desktopPage.widgets;

import de.schmiereck.noiseComp.generator.InputTypeData;

/**
 * A object with this interface is used to manages the selected 
 * 	entry in an {@link de.schmiereck.noiseComp.desktopPage.widgets.ListWidgetData}-Object.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public interface SelectedListEntryInterface
{

	/**
	 * @param selectedInputData
	 */
	void setSelectedInputTypeData(InputTypeData selectedInputData);

	/**
	 * @return
	 */
	InputTypeData getSelectedInputTypeData();
}
