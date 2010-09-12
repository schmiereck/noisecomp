/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;

/**
 * <p>
 * 	Modul-Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>09.09.2010:	created, smk</p>
 */
public class ModulEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Modul-Edit Model.
	 */
	private ModulEditModel	modulEditModel;

	private final JTextField modulNameTextField;
	
	/**
	 * Update Button.
	 */
	private JButton updateButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulEditModel
	 */
	public ModulEditView(final ModulEditModel modulEditModel)
	{
		//==========================================================================================
		this.modulEditModel = modulEditModel;
		
		//------------------------------------------------------------------------------------------
		{
			this.modulNameTextField = this.addTextField(0, "Modul-Name:");
			
			this.modulEditModel.getModulNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						modulNameTextField.setText(OutputUtils.makeStringText(modulEditModel.getModulName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(1, this.updateButton);
		}
		
		//==========================================================================================
	}
}
