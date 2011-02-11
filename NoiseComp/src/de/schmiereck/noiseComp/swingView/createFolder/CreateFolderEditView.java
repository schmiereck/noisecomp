/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.createFolder;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.basicEditView.BasicEditView;
import de.schmiereck.noiseComp.swingView.utils.OutputUtils;

/**
 * <p>
 * 	Create-Folder Edit-View.
 * </p>
 * 
 * @author smk
 * @version <p>31.01.2011:	created, smk</p>
 */
public class CreateFolderEditView
extends BasicEditView
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final CreateFolderModel createFolderModel;
	
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
	 * @param createFolderModel
	 * 			is the Create-Module Model.
	 */
	public CreateFolderEditView(final CreateFolderModel createFolderModel)
	{
		//==========================================================================================
		this.createFolderModel = createFolderModel;
		
		//------------------------------------------------------------------------------------------
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder("Folder:"));
		
		//------------------------------------------------------------------------------------------
		{
			this.folderNameTextField = this.addTextField(0, "Name:");
			
			this.createFolderModel.getFolderNameChangedNotifier().addModelPropertyChangedListener
			(
			 	new ModelPropertyChangedListener()
			 	{
					@Override
					public void notifyModelPropertyChanged()
					{
						folderNameTextField.setText(OutputUtils.makeStringText(createFolderModel.getFolderName()));
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
