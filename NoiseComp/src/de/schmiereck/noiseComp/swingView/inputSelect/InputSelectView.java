/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputSelect;

import javax.swing.JTable;

/**
 * <p>
 * 	Input-Select View.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputSelectView
extends JTable
{
	//**********************************************************************************************
	// Fields:

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputSelectModel
	 * 			is the Input-Select Model.
	 */
	public InputSelectView(InputSelectModel inputSelectModel)
	{
		super(inputSelectModel.getInputsTabelModel());
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

}
