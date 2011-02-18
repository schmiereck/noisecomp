/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.createFolder;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.schmiereck.noiseComp.DialogUtils;
import de.schmiereck.noiseComp.swingView.appView.AppView;

/**
 * <p>
 * 	Create-Folder View.
 * </p>
 * 
 * @author smk
 * @version <p>31.01.2011:	created, smk</p>
 */
public class CreateFolderView
extends JDialog
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final CreateFolderModel createFolderModel;
	
	/**
	 * Create-Folder Edit-View.
	 */
	private final CreateFolderEditView createFolderEditView;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * Contructs a invisble Dialog.
	 * 
	 * @param appView 
	 * 			is the App View.
	 * @param createFolderModel
	 * 			is the Create-Module Model.
	 */
	public CreateFolderView(final AppView appView, final CreateFolderModel createFolderModel)
	{
		super(appView, false);
		//==========================================================================================
		this.createFolderModel = createFolderModel;
		
		//------------------------------------------------------------------------------------------
		this.setVisible(false);
		
		this.setTitle("Create Folder");
		this.setModal(true);
		
		this.setSize(400, 100);
		//this.setLocationByPlatform(true);
		DialogUtils.setLocationCenter(appView, this);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------------------------
		this.createFolderEditView = new CreateFolderEditView(createFolderModel);
		
		this.add(this.createFolderEditView);

		this.getRootPane().setDefaultButton(this.createFolderEditView.getUpdateButton());
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the Update-Button.
	 */
	public JButton getUpdateButton()
	{
		//==========================================================================================
		JButton updateButton = this.createFolderEditView.getUpdateButton();
		
		//==========================================================================================
		return updateButton;
	}

	/**
	 * @return 
	 * 			returns the {@link CreateFolderEditView#getFolderNameTextField()}.
	 */
	public JTextField getFolderNameTextField()
	{
		return this.createFolderEditView.getFolderNameTextField();
	}
}
