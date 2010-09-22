/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeEdit;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectEntryModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModulInputTypeSelectModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;


/**
 * <p>
 * 	Modul-Input-Type Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Input-Type Edit Model.
	 */
	private final ModulInputTypeEditModel	modulInputTypeEditModel;

	/**
	 * Modul-Input-Type Edit View.
	 */
	private final ModulInputTypeEditView	modulInputTypeEditView;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public ModulInputTypeEditController()
	{
		//==========================================================================================
		this.modulInputTypeEditModel = new ModulInputTypeEditModel();
		this.modulInputTypeEditView = new ModulInputTypeEditView(this.modulInputTypeEditModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeEditModel}.
	 */
	public ModulInputTypeEditModel getModulInputTypeEditModel()
	{
		return this.modulInputTypeEditModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeEditView}.
	 */
	public ModulInputTypeEditView getModulInputTypeEditView()
	{
		return this.modulInputTypeEditView;
	}

	/**
	 * @param selectModel
	 * 			is the Select Model.
	 * @param selectedInputTypeData
	 * 			is the selected Input-Type Data.
	 * @param editedModulGeneratorTypeData
	 * 			is the edited Modul-Generator-Type Data.
	 */
	public void doUpdate(final ModulInputTypeSelectModel selectModel, 
	                     final InputTypeData selectedInputTypeData,
	                     ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		ModulInputTypeEditModel inputEditModel = this.getModulInputTypeEditModel();
		ModulInputTypeEditView inputEditView = this.getModulInputTypeEditView();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		String inputTypeIDStr = inputEditView.getInputTypeIDTextField().getText();
		String inputTypeNameStr = inputEditView.getInputTypeNameTextField().getText();
		String inputTypeDescriptionStr = inputEditView.getInputTypeDescriptionTextField().getText();
			
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Integer inputTypeID = InputUtils.makeIntegerValue(inputTypeIDStr);
		String inputTypeName = InputUtils.makeStringValue(inputTypeNameStr);
		String inputTypeDescription = InputUtils.makeStringValue(inputTypeDescriptionStr);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Input-Data:
		
		ModulInputTypeSelectEntryModel selectEntryModel = selectModel.getSelectedRow();
		
		Integer selectedRowNo = selectModel.getSelectedRowNo();
		
		// Input selected?
		if (selectedRowNo != null)
		{
			InputTypeData inputTypeData = selectEntryModel.getInputTypeData();
			
			// Existing Input-type selected?
			if (inputTypeData != null)
			{
				// Update selected Input:
				
//				inputTypeData.setDefaultValue(defaultValue)
//				inputTypeData.setInputCountMax(inputCountMax)
//				inputTypeData.setInputCountMin(inputCountMin)
				inputTypeData.setInputType(inputTypeID);
				inputTypeData.setInputTypeDescription(inputTypeDescription);
				inputTypeData.setInputTypeName(inputTypeName);
			}
			else
			{
				// Insert new Input:
				
				inputTypeData = new InputTypeData(inputTypeID,
				                                  inputTypeName,
				                                  inputTypeDescription);

				editedModulGeneratorTypeData.addInputTypeData(inputTypeData);
				
				selectEntryModel.setInputTypeData(inputTypeData);
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		inputEditModel.setInputTypeID(inputTypeID);
		inputEditModel.setInputTypeName(inputTypeName);
		inputEditModel.setInputTypeDescription(inputTypeDescription);
	}

}
