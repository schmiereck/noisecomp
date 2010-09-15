/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.OutputUtils;

/**
 * <p>
 * 	Input-Edit Controller.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditController
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Input-Edit Model.
	 */
	private final InputEditModel inputEditModel;
	
	/**
	 * Input-Edit View.
	 */
	private final InputEditView inputEditView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public InputEditController()
	{
		//==========================================================================================
		this.inputEditModel = new InputEditModel();
		
		this.inputEditView = new InputEditView(this.inputEditModel);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditModel}.
	 */
	public InputEditModel getInputEditModel()
	{
		return this.inputEditModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEditView}.
	 */
	public InputEditView getInputEditView()
	{
		return this.inputEditView;
	}

	/**
	 * @param inputData
	 * 			is the edited input data.
	 */
	public void updateEditedInput(InputData inputData)
	{
		//==========================================================================================
		String value;

		if (inputData != null)
		{
			value = OutputUtils.makeFloatText(inputData.getInputValue());
		}
		else
		{
			value = null;
		}

		inputEditModel.setValue(value);
		
		//==========================================================================================
	}

}
