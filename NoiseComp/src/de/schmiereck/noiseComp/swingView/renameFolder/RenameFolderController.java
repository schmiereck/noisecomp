/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.renameFolder;

import de.schmiereck.noiseComp.swingView.appController.AppController;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;


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
		
		//==========================================================================================
	}

	/**
	 * Do Rename Module.
	 */
	public void doRenameModule()
	{
		//==========================================================================================
		this.renameFolderView.setVisible(true);	
		
		//==========================================================================================
	}
	
}
