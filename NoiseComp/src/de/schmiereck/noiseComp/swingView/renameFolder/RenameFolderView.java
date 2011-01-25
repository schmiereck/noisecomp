/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

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
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------------------------
		RenameFolderEditView renameFolderEditView = new RenameFolderEditView(renameFolderModel);
		
		this.add(renameFolderEditView);
		
		//==========================================================================================
	}
	
}
