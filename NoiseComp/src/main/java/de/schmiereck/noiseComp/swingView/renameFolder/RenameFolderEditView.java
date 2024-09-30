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

	private final RenameFolderModel renameFolderModel;
	
	/**
	 * Folder-Name Text-Field.
	 */
	private final JTextField folderNameTextField;
	
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
			this.folderNameTextField = this.addTextField(0, "Name:");
			
			this.renameFolderModel.getFolderNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						folderNameTextField.setText(OutputUtils.makeStringText(renameFolderModel.getFolderName()));
					}
			 	}
			);
		}
		//------------------------------------------------------------------------------------------
		{
			this.updateButton = new JButton("Update");
			
			this.addField(1, this.updateButton);
		}
		
		//------------------------------------------------------------------------------------------
//		this.getRootPane().setDefaultButton(this.updateButton);
		
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
	 * 			returns the {@link #folderNameTextField}.
	 */
	public JTextField getFolderNameTextField()
	{
		return this.folderNameTextField;
	}
}
