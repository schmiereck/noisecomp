/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.createFolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.utils.InputUtils;

/**
 * <p>
 * 	Create-Folder Controller.
 * </p>
 * 
 * @author smk
 * @version <p>31.01.2011:	created, smk</p>
 */
public class CreateFolderController
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Create-Module View.
	 */
	private final CreateFolderView createFolderView;

	/**
	 * Create-Module Model.
	 */
	private final CreateFolderModel createFolderModel;
	
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
	public CreateFolderController(final AppController appController, 
	                              final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.createFolderModel = new CreateFolderModel();
		
		//------------------------------------------------------------------------------------------
		this.createFolderView = new CreateFolderView(appController.getAppView(),
		                                            this.createFolderModel);
		
		//------------------------------------------------------------------------------------------
		// ModuleInput-Type Edit Update-Button: Update ModuleInput-Type Data and ModuleInput-Type Edit-Model:
		// TODO Move to controller.
		this.createFolderView.getUpdateButton().addActionListener
		(
		 	new ActionListener()
		 	{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Das Model updaten und der App-Controller reagiert auf den change event.
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					createFolderView.setVisible(false);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					String folderName = InputUtils.makeStringValue(createFolderView.getFolderNameTextField().getText());
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					appController.doCreateFolder(folderName);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
		 	}
		);
		//==========================================================================================
	}

	/**
	 * Do Create Module.
	 * 
	 * @param folderName
	 * 			is the folder name.
	 */
	public void doCreateFolder(String folderName)
	{
		//==========================================================================================
		this.createFolderView.getFolderNameTextField().setText(InputUtils.makeStringValue(folderName));
		
		this.createFolderView.setVisible(true);	
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #createFolderModel}.
	 */
	public CreateFolderModel getCreateFolderModel()
	{
		return this.createFolderModel;
	}
}
