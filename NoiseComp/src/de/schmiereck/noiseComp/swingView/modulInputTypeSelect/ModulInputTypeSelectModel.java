/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeSelect;

import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Modul-Input-Type Select Model.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeSelectModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final ModulInputTypeTabelModel modulInputTypeTabelModel = new ModulInputTypeTabelModel();
	
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
	public ModulInputTypeSelectModel()
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
		this.modulInputTypeTabelModel.clearInputs();
	}

	/**
	 * @param modulInputTypeSelectEntryModel
	 * 			is the Modul-Input-Type Select-Entry Model.
	 */
	public void addInputData(ModulInputTypeSelectEntryModel modulInputTypeSelectEntryModel)
	{
		this.modulInputTypeTabelModel.addInputData(modulInputTypeSelectEntryModel);
	}

	/**
	 * @return 
	 * 			returns the {@link #modulInputTypeTabelModel}.
	 */
	public ModulInputTypeTabelModel getModulInputTypeTabelModel()
	{
		return this.modulInputTypeTabelModel;
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
	 * 			the selected Modul-Input-Type Select-Entry Model.
	 */
	public ModulInputTypeSelectEntryModel getSelectedRow()
	{
		ModulInputTypeSelectEntryModel selectEntryModel;
		
		Integer selectedRowNo = this.getSelectedRowNo();
		
		if (selectedRowNo != null)
		{
			ModulInputTypeTabelModel tabelModel = this.getModulInputTypeTabelModel();
			
			selectEntryModel = tabelModel.getRow(selectedRowNo);
		}
		else
		{
			selectEntryModel = null;
		}
		
		return selectEntryModel;
	}

}
