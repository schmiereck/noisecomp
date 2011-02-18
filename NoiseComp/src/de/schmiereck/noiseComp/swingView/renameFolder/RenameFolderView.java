/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.schmiereck.noiseComp.DialogUtils;
import de.schmiereck.noiseComp.swingView.appView.AppView;

/**
 * <p>
 * 	Rename-Folder View.
 * </p>
 * 
 * @author smk
 * @version <p>25.01.2011:	created, smk</p>
 */
public class RenameFolderView
extends JDialog
{
	//**********************************************************************************************
	// Fields:

	@SuppressWarnings("unused")
	private final RenameFolderModel renameFolderModel;
	
	/**
	 * Rename-Folder Edit-View.
	 */
	private final RenameFolderEditView renameFolderEditView;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * Contructs a invisble Dialog.
	 * 
	 * @param appView 
	 * 			is the App View.
	 * @param renameFolderModel
	 * 			is the Rename-Module Model.
	 */
	public RenameFolderView(final AppView appView, final RenameFolderModel renameFolderModel)
	{
		super(appView, false);
		//==========================================================================================
		this.renameFolderModel = renameFolderModel;
		
		//------------------------------------------------------------------------------------------
		this.setVisible(false);
		
		this.setTitle("Rename Folder");
		this.setModal(true);
		
		this.setSize(400, 100);
		//this.setLocationByPlatform(true);
		DialogUtils.setLocationCenter(appView, this);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------------------------
		this.renameFolderEditView = new RenameFolderEditView(renameFolderModel);
		
		this.add(this.renameFolderEditView);

		this.getRootPane().setDefaultButton(this.renameFolderEditView.getUpdateButton());
		
		//==========================================================================================
	}

	/**
	 * @return
	 * 			the Update-Button.
	 */
	public JButton getUpdateButton()
	{
		//==========================================================================================
		JButton updateButton = this.renameFolderEditView.getUpdateButton();
		
		//==========================================================================================
		return updateButton;
	}

	/**
	 * @return 
	 * 			returns the {@link RenameFolderEditView#getFolderNameTextField()}.
	 */
	public JTextField getFolderNameTextField()
	{
		return this.renameFolderEditView.getFolderNameTextField();
	}
}
