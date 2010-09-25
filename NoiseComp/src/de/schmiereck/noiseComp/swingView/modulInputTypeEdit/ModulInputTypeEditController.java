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
	                     //final InputTypeData selectedInputTypeData,
	                     ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		ModulInputTypeEditModel inputEditModel = this.getModulInputTypeEditModel();
		ModulInputTypeEditView inputEditView = this.getModulInputTypeEditView();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		String inputTypeIDStr = inputEditView.getInputTypeIDTextField().getText();
		String inputTypeDefaultValueStr = inputEditView.getInputTypeDefaultValueTextField().getText();
		String inputTypeNameStr = inputEditView.getInputTypeNameTextField().getText();
		String inputTypeDescriptionStr = inputEditView.getInputTypeDescriptionTextField().getText();
			
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		Integer inputTypeID = InputUtils.makeIntegerValue(inputTypeIDStr);
		Float inputTypeDefaultValue = InputUtils.makeFloatValue(inputTypeDefaultValueStr);
		String inputTypeName = InputUtils.makeStringValue(inputTypeNameStr);
		String inputTypeDescription = InputUtils.makeStringValue(inputTypeDescriptionStr);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Input-Data:
		
//		Integer selectedRowNo = selectModel.getSelectedRowNo();
//		
//		// Input selected?
//		if (selectedRowNo != null)
		{
			InputTypeData inputTypeData = inputEditModel.getInputTypeData();
			
			// Existing Input-type selected?
			if (inputTypeData != null)
			{
				// Update selected Input:
				
//				inputTypeData.setInputCountMax(inputCountMax)
//				inputTypeData.setInputCountMin(inputCountMin)
				inputTypeData.setInputType(inputTypeID);
				inputTypeData.setDefaultValue(inputTypeDefaultValue);
				inputTypeData.setInputTypeName(inputTypeName);
				inputTypeData.setInputTypeDescription(inputTypeDescription);
			}
			else
			{
				// Insert new Input:
				
				inputTypeData = new InputTypeData(inputTypeID,
				                                  inputTypeName,
				                                  inputTypeDescription);

				inputTypeData.setDefaultValue(inputTypeDefaultValue);
				
				editedModulGeneratorTypeData.addInputTypeData(inputTypeData);
				
				ModulInputTypeSelectEntryModel selectEntryModel = selectModel.getSelectedRow();
				
				selectEntryModel.setInputTypeData(inputTypeData);
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		inputEditModel.setInputTypeID(inputTypeID);
		inputEditModel.setInputTypeName(inputTypeName);
		inputEditModel.setInputTypeDescription(inputTypeDescription);
	}

	/**
	 * @param editedInputTypeData
	 * 			is the input Type.
	 */
	public void updateEditedInputType(InputTypeData editedInputTypeData)
	{
		this.modulInputTypeEditModel.setInputTypeData(editedInputTypeData);
		
		Integer inputTypeID;
		Float inputTypeDefaultValue;
		String inputTypeName;
		String inputTypeDescription;

		if (editedInputTypeData != null)
		{
			inputTypeID = editedInputTypeData.getInputType();
			inputTypeDefaultValue = editedInputTypeData.getDefaultValue();
			inputTypeName = editedInputTypeData.getInputTypeName();
			inputTypeDescription = editedInputTypeData.getInputTypeDescription();
		}
		else
		{
			inputTypeID = null;
			inputTypeDefaultValue = null;
			inputTypeName = null;
			inputTypeDescription = null;
		}

		this.modulInputTypeEditModel.setInputTypeID(inputTypeID);
		this.modulInputTypeEditModel.setInputTypeDefaultValue(inputTypeDefaultValue);
		this.modulInputTypeEditModel.setInputTypeName(inputTypeName);
		this.modulInputTypeEditModel.setInputTypeDescription(inputTypeDescription);
	}

}
