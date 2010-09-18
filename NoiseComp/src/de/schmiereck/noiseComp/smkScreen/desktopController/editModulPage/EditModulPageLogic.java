package de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.InputTypesData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputTypeSelectedListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputTypesWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.EditGeneratorChangedListener;
import de.schmiereck.screenTools.controller.ControllerLogic;

/**
 * Edit-Modul Page Logic.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public class EditModulPageLogic
implements InputTypeSelectedListenerInterface,
EditGeneratorChangedListener
{
	@SuppressWarnings("unused")
	private ControllerLogic controllerLogic;
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 */
	public EditModulPageLogic(ControllerLogic controllerLogic, 
							  EditModulPageData editModulPageData)
	{
		this.controllerLogic = controllerLogic;
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
	 * @see de.schmiereck.noiseComp.desktopController.EditGeneratorChangedListener#notifyEditGeneratorChanged(de.schmiereck.noiseComp.generator.ModulGeneratorTypeData)
	 */
	public void notifyEditGeneratorChanged(ModulGeneratorTypeData editModulTypeData)
	{
		InputTypesData inputTypesData;
		
		if (editModulTypeData != null)
		{	
			inputTypesData = editModulTypeData.getInputTypesData();
		}
		else
		{
			inputTypesData = null;
		}
		
		this.editModulPageData.getInputTypesListWidgetData().setInputTypesData(inputTypesData);
	}
}
