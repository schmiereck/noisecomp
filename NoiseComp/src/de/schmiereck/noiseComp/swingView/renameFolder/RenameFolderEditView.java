/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

/**
 * <p>
 * 	Rename-Folder Edit-View.
 * </p>
 * 
 * @author smk
 * @version <p>25.01.2011:	created, smk</p>
 */
public class RenameFolderEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final RenameFolderModel renameFolderModel;
	
	private final JTextField moduleNameTextField;
	
	/**
	 * Update Button.
	 */
	private final JButton updateButton;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param renameFolderModel
	 * 			is the Rename-Module Model.
	 */
	public RenameFolderEditView(final RenameFolderModel renameFolderModel)
	{
		//==========================================================================================
		this.renameFolderModel = renameFolderModel;
		
		//------------------------------------------------------------------------------------------
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Folder:"));
		
		//------------------------------------------------------------------------------------------
		{
			this.moduleNameTextField = this.addTextField(0, "Name:");
			
			this.renameFolderModel.getModuleNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						moduleNameTextField.setText(OutputUtils.makeStringText(renameFolderModel.getModuleName()));
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
