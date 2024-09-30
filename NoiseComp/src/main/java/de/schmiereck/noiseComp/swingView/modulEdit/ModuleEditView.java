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
 * 	ModuleEdit View.
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
	 * ModuleEdit Model.
	 */
	private final ModuleEditModel moduleEditModel;

	/**
	 * ModuleName Text-Field
	 */
	private final JTextField moduleameTextField;
	
	/**
	 * moduleIs-Main Check-Box.
	 */
	private final JCheckBox modulesMainCheckBox;
	
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
	 * 			is the ModuleEdit Model.
	 */
	public ModuleEditView(final ModuleEditModel moduleEditModel)
	{
		//==========================================================================================
		this.moduleEditModel = moduleEditModel;
		
		this.setLayout(new FlowLayout());
		
		//------------------------------------------------------------------------------------------
		{
			this.moduleameTextField = this.addTextField(0, "ModuleName:");
			
			this.moduleEditModel.getModuleNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						moduleameTextField.setText(OutputUtils.makeStringText(moduleEditModel.getModuleName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.modulesMainCheckBox = this.addCheckBox(1, "Is Main:");
			
			this.moduleEditModel.getModuleIsMainChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						boolean modulesMain = OutputUtils.makeBoolean(moduleEditModel.getModuleIsMain());
						
						modulesMainCheckBox.setSelected(modulesMain);
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

//			this.getRootPane().setDefaultButton(this.updateButton);
			this.setDefaultButton(this.updateButton);
			this.updateButton.setDefaultCapable(true);
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
	 * 			returns the {@link #moduleameTextField}.
	 */
	public JTextField getModuleameTextField()
	{
		return this.moduleameTextField;
	}

	/**
	 * @return 
	 * 			returns the {@link #modulesMainCheckBox}.
	 */
	public JCheckBox getModulesMainCheckBox()
	{
		return this.modulesMainCheckBox;
	}
	
}
