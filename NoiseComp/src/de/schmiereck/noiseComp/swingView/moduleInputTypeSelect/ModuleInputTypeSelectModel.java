/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.moduleInputTypeSelect;

import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	ModuleInput-Type Select Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModuleInputTypeSelectModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final ModuleInputTypeTabelModel moduleInputTypeTabelModel = new ModuleInputTypeTabelModel();
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * Selected Row.
	 */
	private Integer selectedRowNo = null;
	
	/**
	 * {@link #selectedRowNo} changed notifier.
	 */
	private final ModelPropertyChangedNotifier selectedRowNoChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ModuleInputTypeSelectModel()
	{
		//==========================================================================================
		//------------------------------------------------------------------------------------------
		//==========================================================================================
	}

	/**
	 * Clear Inputs.
	 */
	public void clearInputs()
	{
		this.moduleInputTypeTabelModel.clearInputs();
	}

	/**
	 * @param moduleInputTypeSelectEntryModel
	 * 			is the ModuleInput-Type Select-Entry Model.
	 */
	public void addInputData(ModuleInputTypeSelectEntryModel moduleInputTypeSelectEntryModel)
	{
		this.moduleInputTypeTabelModel.addInputData(moduleInputTypeSelectEntryModel);
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleInputTypeTabelModel}.
	 */
	public ModuleInputTypeTabelModel getModuleInputTypeTabelModel()
	{
		return this.moduleInputTypeTabelModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedRowNo}.
	 */
	public Integer getSelectedRowNo()
	{
		return this.selectedRowNo;
	}

	/**
	 * @param selectedRowNo 
	 * 			to set {@link #selectedRowNo}.
	 */
	public void setSelectedRowNo(Integer selectedRowNo)
	{
		if (CompareUtils.compareWithNull(this.selectedRowNo, selectedRowNo) == false)
		{
			this.selectedRowNo = selectedRowNo;
			
			// Notify listeners.
			this.selectedRowNoChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedRowNoChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getSelectedRowNoChangedNotifier()
	{
		return this.selectedRowNoChangedNotifier;
	}

	/**
	 * @return
	 * 			the selected ModuleInput-Type Select-Entry Model.
	 */
	public ModuleInputTypeSelectEntryModel getSelectedRow()
	{
		ModuleInputTypeSelectEntryModel selectEntryModel;
		
		Integer selectedRowNo = this.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			ModuleInputTypeTabelModel tabelModel = this.getModuleInputTypeTabelModel();
			
			selectEntryModel = tabelModel.getRow(selectedRowNo);
		}
		else
		{
			selectEntryModel = null;
		}
		
		return selectEntryModel;
	}

}
