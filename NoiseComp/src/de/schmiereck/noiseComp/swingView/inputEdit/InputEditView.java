/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JTable;

import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.inputSelect.InputSelectModel;

/**
 * <p>
 * 	Input-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>15.09.2010:	created, smk</p>
 */
public class InputEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	private final InputEditModel inputEditModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param inputEditModel
	 * 			is the Input-Edit Model.
	 */
	public InputEditView(final InputEditModel inputEditModel)
	{
		//==========================================================================================
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createTitledBorder("Input:"));
		
		this.inputEditModel = inputEditModel;
		
		//------------------------------------------------------------------------------------------
		
		//==========================================================================================
	}
}
