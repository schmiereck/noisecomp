package de.schmiereck.noiseComp.desktopController.editModulPage;

import de.schmiereck.noiseComp.desktopController.EditGeneratorChangedListener;
import de.schmiereck.noiseComp.desktopPage.widgets.InputTypeSelectedListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputTypesWidgetData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public class EditModulPageLogic
implements InputTypeSelectedListenerInterface,
EditGeneratorChangedListener
{
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 * @param data
	 */
	public EditModulPageLogic(EditModulPageData editModulPageData)
	{
		this.editModulPageData = editModulPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.InputTypeSelectedListenerInterface#notifyInputTypeSelected(de.schmiereck.noiseComp.desktopPage.widgets.InputTypesWidgetData, de.schmiereck.noiseComp.generator.InputTypeData)
	 */
	public void notifyInputTypeSelected(InputTypesWidgetData inputTypesWidgetData, InputTypeData inputGeneratorTypeData)
	{
		this.editModulPageData.setSelectedInputTypeData(inputGeneratorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.InputTypeSelectedListenerInterface#notifyInputTypeDeselected(de.schmiereck.noiseComp.desktopPage.widgets.InputTypesWidgetData, de.schmiereck.noiseComp.generator.InputTypeData)
	 */
	public void notifyInputTypeDeselected(InputTypesWidgetData inputTypesWidgetData, InputTypeData inputGeneratorTypeData)
	{
		// Clear the inputs.
		this.editModulPageData.setSelectedInputTypeData(null);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopController.EditGeneratorChangedListener#notifyEditGeneratorChanged(de.schmiereck.noiseComp.generator.ModulGeneratorTypeData, de.schmiereck.noiseComp.generator.Generators)
	 */
	public void notifyEditGeneratorChanged(ModulGeneratorTypeData editModulTypeData, Generators editGenerators)
	{
		if (editModulTypeData != null)
		{	
			this.editModulPageData.getInputTypesListWidgetData().setInputTypesData(editModulTypeData.getInputTypesData());
		}
	}
}
