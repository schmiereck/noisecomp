package de.schmiereck.noiseComp.desktopController.editModulPage.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * Called if the Save Button of the InputType-Edit is pressed.
 * Saves the edited data in the selected input type or
 * cretes a new input type, if no one is selected.
 *
 * @author smk
 * @version <p>11.04.2004: created, smk</p>
 */
public class InputTypeEditSaveActionListener
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public InputTypeEditSaveActionListener(DesktopControllerLogic controllerLogic, 
			DesktopControllerData controllerData,
			EditModulPageData editModulPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
		this.editModulPageData = editModulPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		ModulGeneratorTypeData editModulTypeData = this.controllerData.getEditModulTypeData();
		
		// Actualy no modul edited ?
		if (editModulTypeData == null)
		{	
			throw new RuntimeException("Actualy no modul edited.");
		}
		
		//----------------------------------------------------------------------
		// read data from inputs:
		
		Integer inputTypeEditType = this.editModulPageData.getInputTypeEditTypeInputlineData().getInputInteger();
		String inputTypeEditName = this.editModulPageData.getInputTypeEditNameInputlineData().getInputText();
		Integer inputTypeEditCountMin = this.editModulPageData.getInputTypeEditCountMinInputlineData().getInputInteger();
		Integer inputTypeEditCountMax = this.editModulPageData.getInputTypeEditCountMaxInputlineData().getInputInteger();
		Float inputTypeEditDefaultValue = this.editModulPageData.getInputTypeEditDefaultValueInputlineData().getInputFloat();
		String inputTypeEditDescription = this.editModulPageData.getInputTypeEditDescriptionInputlineData().getInputText();
		
		InputTypeData selectedInputTypeData = this.editModulPageData.getSelectedInputTypeData();
		
		if (selectedInputTypeData == null)
		{
			selectedInputTypeData = new InputTypeData(inputTypeEditType.intValue(), 
					inputTypeEditName, 
					inputTypeEditCountMax, 
					inputTypeEditCountMin, 
					inputTypeEditDefaultValue,
					inputTypeEditDescription);
			
			editModulTypeData.addInputTypeData(selectedInputTypeData);
			this.editModulPageData.getInputTypesListWidgetData().notifyInputTypeAdded();
			
			// Clear the inputs.
			this.editModulPageData.setSelectedInputTypeData(null);
		}
		else
		{
			selectedInputTypeData.setInputType(inputTypeEditType.intValue()); 
			selectedInputTypeData.setInputTypeName(inputTypeEditName);
			selectedInputTypeData.setInputCountMax(inputTypeEditCountMax); 
			selectedInputTypeData.setInputCountMin(inputTypeEditCountMin);
			selectedInputTypeData.setDefaultValue(inputTypeEditDefaultValue);
			selectedInputTypeData.setInputTypeDescription(inputTypeEditDescription);
		}
	}
}
