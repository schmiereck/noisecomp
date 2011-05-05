/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeEdit;

import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModuleInputTypeSelectController;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModuleInputTypeSelectEntryModel;
import de.schmiereck.noiseComp.swingView.modulInputTypeSelect.ModuleInputTypeSelectModel;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeController;
import de.schmiereck.noiseComp.swingView.modulsTree.ModulesTreeModel;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;


/**
 * <p>
 * 	Modul-Input-Type Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Modul-Input-Type Edit Model.
	 */
	private final ModuleInputTypeEditModel	moduleInputTypeEditModel;

	/**
	 * Modul-Input-Type Edit View.
	 */
	private final ModuleInputTypeEditView	moduleInputTypeEditView;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 */
	public ModuleInputTypeEditController()
	{
		//==========================================================================================
		this.moduleInputTypeEditModel = new ModuleInputTypeEditModel();
		this.moduleInputTypeEditView = new ModuleInputTypeEditView(this.moduleInputTypeEditModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeEditModel}.
	 */
	public ModuleInputTypeEditModel getModuleInputTypeEditModel()
	{
		return this.moduleInputTypeEditModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeEditView}.
	 */
	public ModuleInputTypeEditView getModuleInputTypeEditView()
	{
		return this.moduleInputTypeEditView;
	}

	/**
	 * @param selectModel
	 * 			is the Select Model.
	 * @param selectedInputTypeData
	 * 			is the selected Input-Type Data.
	 * @param editedModulGeneratorTypeData
	 * 			is the edited Modul-Generator-Type Data.
	 */
	public void doUpdate(final ModuleInputTypeSelectModel selectModel, 
	                     //final InputTypeData selectedInputTypeData,
	                     ModulGeneratorTypeData editedModulGeneratorTypeData)
	{
		//==========================================================================================
		ModuleInputTypeEditModel inputEditModel = this.getModuleInputTypeEditModel();
		ModuleInputTypeEditView inputEditView = this.getModuleInputTypeEditView();
		
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
				
				ModuleInputTypeSelectEntryModel selectEntryModel = selectModel.getSelectedRow();
				
				if (selectEntryModel != null)
				{
					selectEntryModel.setInputTypeData(inputTypeData);
				}
				else
				{
					// TODO Show Message "No input type selected.".
				}
			}
		}
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// Update Input-Edit-Model:
		
		inputEditModel.setInputTypeID(inputTypeID);
		inputEditModel.setInputTypeName(inputTypeName);
		inputEditModel.setInputTypeDescription(inputTypeDescription);

		//==========================================================================================
	}

	/**
	 * @param editedInputTypeData
	 * 			is the input Type.
	 */
	public void updateEditedInputType(InputTypeData editedInputTypeData)
	{
		//==========================================================================================
		this.moduleInputTypeEditModel.setInputTypeData(editedInputTypeData);
		
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

		this.moduleInputTypeEditModel.setInputTypeID(inputTypeID);
		this.moduleInputTypeEditModel.setInputTypeDefaultValue(inputTypeDefaultValue);
		this.moduleInputTypeEditModel.setInputTypeName(inputTypeName);
		this.moduleInputTypeEditModel.setInputTypeDescription(inputTypeDescription);

		//==========================================================================================
	}

	/**
	 * @param appModelChangedObserver
	 */
	public void doUpdateModulInputType(final ModulesTreeController modulesTreeController,
	                                   final ModuleInputTypeSelectController moduleInputTypeSelectController,
	                                   final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		ModulesTreeModel modulesTreeModel = modulesTreeController.getModulesTreeModel();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		ModulGeneratorTypeData editedModulGeneratorTypeData = modulesTreeModel.getEditedModulGeneratorTypeData();
		ModuleInputTypeSelectModel selectModel = moduleInputTypeSelectController.getInputTypeSelectModel();
		
//		InputTypeData inputTypeData = modulInputTypeSelectController.getSelectedModulInputType();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.doUpdate(selectModel, 
		              //inputTypeData,
		              editedModulGeneratorTypeData);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}
}
