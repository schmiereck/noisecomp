/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

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
	private final ModulEditModel modulEditModel;

	/**
	 * Modul-Name Text-Field
	 */
	private final JTextField modulNameTextField;
	
	/**
	 * Update Button.
	 */
	private final JButton updateButton;
	
	/**
	 * Input-Types Button.
	 */
	private final JButton editInputTypesButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulEditModel
	 * 			is the Modul-Edit Model.
	 */
	public ModulEditView(final ModulEditModel modulEditModel)
	{
		//==========================================================================================
		this.setLayout(new FlowLayout());
		
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
			this.editInputTypesButton = new JButton("Edit Input-Types...");
			
			this.addField(1, this.editInputTypesButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(2, this.updateButton);
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #editInputTypesButton}.
	 */
	public JButton getEditInputTypesButton()
	{
		return this.editInputTypesButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #updateButton}.
	 */
	public JButton getUpdateButton()
	{
		return this.updateButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulNameTextField}.
	 */
	public JTextField getModulNameTextField()
	{
		return this.modulNameTextField;
	}
}
