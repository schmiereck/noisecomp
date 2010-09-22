/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.about;

import javax.swing.JButton;

/**
 * <p>
 * 	About Controller.
 * </p>
 * 
 * @author smk
 * @version <p>21.09.2010:	created, smk</p>
 */
public class AboutController
{
	/**
	 * Is the View of the About-Dialog. 
	 */
	private AboutDialogView aboutDialogView;
	
	/**
	 * Constructor.
	 * 
	 * @param aboutDialogView 
	 * 			is the View of the About-Dialog. 
	 */
	public AboutController(AboutDialogView aboutDialogView)
	{
		this.aboutDialogView = aboutDialogView;
		
		{
			JButton okButton = this.aboutDialogView.getOkButton();
			
			OKAction okAction = new OKAction(this);
			
			okButton.setAction(okAction);
		}
	}

	/**
	 * Show Dialog.
	 */
	public void doShow()
	{
		this.aboutDialogView.setVisible(true);
	}

	/**
	 * Close Dialog.
	 */
	public void doClose()
	{
		this.aboutDialogView.setVisible(false);
	}
}
