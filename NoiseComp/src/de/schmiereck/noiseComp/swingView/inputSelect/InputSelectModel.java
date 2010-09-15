/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;


/**
 * <p>
 * 	Input-Select Model.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectModel
{
	//**********************************************************************************************
	// Fields:

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private final InputsTabelModel inputsTabelModel = new InputsTabelModel();

	/**
	 * @return 
	 * 			returns the {@link #inputsTabelModel}.
	 */
	public InputsTabelModel getInputsTabelModel()
	{
		return this.inputsTabelModel;
	}
	
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
	public InputSelectModel()
	{
		//==========================================================================================
//		this.inputsTabelModel.addTableModelListener
//		(
//		 	new TableModelListener()
//		 	{
//				@Override
//				public void tableChanged(TableModelEvent e)
//				{
//				}
//		 	}
//		);
		//------------------------------------------------------------------------------------------

		//==========================================================================================
	}

	/**
	 * Clear Inputs.
	 */
	public void clearInputs()
	{
		this.inputsTabelModel.clearInputs();
	}

	/**
	 * @param inputData
	 * 			is the Input Data.
	 */
	public void addInputData(InputData inputData)
	{
		this.inputsTabelModel.addInputData(inputData);
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
		this.selectedRowNo = selectedRowNo;
		
		// Notify listeners.
		this.selectedRowNoChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedRowNoChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getSelectedRowNoChangedNotifier()
	{
		return this.selectedRowNoChangedNotifier;
	}

}
