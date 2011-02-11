/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.modulEdit;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
public class ModuleEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Modul-Edit Model.
	 */
	private final ModuleEditModel moduleEditModel;

	/**
	 * Modul-Name Text-Field
	 */
	private final JTextField modulNameTextField;
	
	/**
	 * modul-Is-Main Check-Box.
	 */
	private final JCheckBox modulIsMainCheckBox;
	
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
	 * @param moduleEditModel
	 * 			is the Modul-Edit Model.
	 */
	public ModuleEditView(final ModuleEditModel moduleEditModel)
	{
		//==========================================================================================
		this.setLayout(new FlowLayout());
		
		this.moduleEditModel = moduleEditModel;
		
		//------------------------------------------------------------------------------------------
		{
			this.modulNameTextField = this.addTextField(0, "Modul-Name:");
			
			this.moduleEditModel.getModulNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						modulNameTextField.setText(OutputUtils.makeStringText(moduleEditModel.getModulName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.modulIsMainCheckBox = this.addCheckBox(1, "Is Main:");
			
			this.moduleEditModel.getModulIsMainChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						boolean modulIsMain = OutputUtils.makeBoolean(moduleEditModel.getModulIsMain());
						
						modulIsMainCheckBox.setSelected(modulIsMain);
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.editInputTypesButton = new JButton("Edit Input-Types...");
			
			this.addField(2, this.editInputTypesButton);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(3, this.updateButton);
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

	/**
	 * @return 
	 * 			returns the {@link #modulIsMainCheckBox}.
	 */
	public JCheckBox getModulIsMainCheckBox()
	{
		return this.modulIsMainCheckBox;
	}
}
