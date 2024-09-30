/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;


/**
 * <p>
 * 	Rename-Folder Controller.
 * </p>
 * 
 * @author smk
 * @version <p>25.01.2011:	created, smk</p>
 */
public class RenameFolderController
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Rename-Module View.
	 */
	private final RenameFolderView renameFolderView;

	/**
	 * Rename-Module Model.
	 */
	private final RenameFolderModel renameFolderModel;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 * @param appModelChangedObserver 
	 * 			is the AppModelChangedObserver.
	 */
	public RenameFolderController(final AppController appController, 
	                              final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.renameFolderModel = new RenameFolderModel();
		
		//------------------------------------------------------------------------------------------
		this.renameFolderView = new RenameFolderView(appController.getAppView(),
		                                            this.renameFolderModel);
		
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Update-Button: Update ModuleInput-Type Data and ModuleInput-Type Edit-Model:
		// TODO Move to controller.
		this.renameFolderView.getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Das Model updaten und der App-Controller reagiert auf den change event.
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					renameFolderView.setVisible(false);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					String folderName = InputUtils.makeStringValue(renameFolderView.getFolderNameTextField().getText());
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					renameFolderModel.setFolderName(folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * Do Rename Module.
	 * 
	 * @param folderName
	 * 			is the folder name.
	 */
	public void doRenameFolder(String folderName)
	{
		//==========================================================================================
		this.renameFolderView.getFolderNameTextField().setText(InputUtils.makeStringValue(folderName));
		
		this.renameFolderView.setVisible(true);	
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #renameFolderModel}.
	 */
	public RenameFolderModel getRenameFolderModel()
	{
		return this.renameFolderModel;
	}
	
}
