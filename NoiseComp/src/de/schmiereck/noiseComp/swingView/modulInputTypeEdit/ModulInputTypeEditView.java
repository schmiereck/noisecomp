/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulInputTypeEdit;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.OutputUtils;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;

/**
 * <p>
 * 	Modul-Input-Type Edit View.
 * </p>
 * 
 * @author smk
 * @version <p>20.09.2010:	created, smk</p>
 */
public class ModulInputTypeEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final ModulInputTypeEditModel modulInputTypeEditModel;
	
	private final JTextField inputTypeIDTextField;
	private final JTextField inputTypeNameTextField;
	private final JTextField inputTypeDescriptionTextField;
	
	/**
	 * Create-New Button.
	 */
	private final JButton	createNewButton;

	/**
	 * Remove Button.
	 */
	private final JButton	removeButton;
	
	/**
	 * Update Button.
	 */
	private final JButton updateButton;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulInputTypeEditModel
	 * 			is the Modul-Input-Type Edit Model.
	 */
	public ModulInputTypeEditView(final ModulInputTypeEditModel modulInputTypeEditModel)
	{
		//==========================================================================================
		this.modulInputTypeEditModel = modulInputTypeEditModel;
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Input-Type:"));
		
		//------------------------------------------------------------------------------------------
		{
			this.createNewButton = new JButton("Create new input type");
			
			this.addField(0, this.createNewButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.removeButton = new JButton("Remove input type");
			
			this.addField(1, this.removeButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeIDTextField = this.addTextField(2, "ID:");
			
			modulInputTypeEditModel.getInputTypeNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeIDTextField.setText(OutputUtils.makeIntegerText(modulInputTypeEditModel.getInputTypeID()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeNameTextField = this.addTextField(3, "Name:");
			
			modulInputTypeEditModel.getInputTypeNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeNameTextField.setText(OutputUtils.makeStringText(modulInputTypeEditModel.getInputTypeName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.inputTypeDescriptionTextField = this.addTextField(4, "Description:");
			
			modulInputTypeEditModel.getInputTypeNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						inputTypeDescriptionTextField.setText(OutputUtils.makeStringText(modulInputTypeEditModel.getInputTypeDescription()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(5, this.updateButton);
		}
		//==========================================================================================
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
	 * 			returns the {@link #createNewButton}.
	 */
	public JButton getCreateNewButton()
	{
		return this.createNewButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #removeButton}.
	 */
	public JButton getRemoveButton()
	{
		return this.removeButton;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeNameTextField}.
	 */
	public JTextField getInputTypeNameTextField()
	{
		return this.inputTypeNameTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeIDTextField}.
	 */
	public JTextField getInputTypeIDTextField()
	{
		return this.inputTypeIDTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTypeDescriptionTextField}.
	 */
	public JTextField getInputTypeDescriptionTextField()
	{
		return this.inputTypeDescriptionTextField;
	}
}