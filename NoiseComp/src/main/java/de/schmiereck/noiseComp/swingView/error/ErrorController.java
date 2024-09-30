/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.error;

import java.awt.Frame;

import javax.swing.JButton;

/**
 * <p>
 * 	Error Controller.
 * </p>
 * 
 * @author smk
 * @version <p>16.03.2011:	created, smk</p>
 */
public class ErrorController
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * View of the Error-Dialog. 
	 */
	private final ErrorDialogView errorDialogView;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public ErrorController()
	{
		//------------------------------------------------------------------------------------------
		Frame parent = null;
		
		this.errorDialogView = new ErrorDialogView(parent, true);
		
		{
			JButton okButton = this.errorDialogView.getOkButton();
			
			ErrorOKAction errorOkAction = new ErrorOKAction(this);
			
			okButton.setAction(errorOkAction);
		}
	}

	/**
	 * Show Dialog.
	 */
	public void doShow()
	{
		this.errorDialogView.setVisible(true);
	}

	/**
	 * Close Dialog.
	 */
	public void doClose()
	{
		this.errorDialogView.setVisible(false);
	}

	/**
	 * @param activeFrame 
	 * @param stacktrace
	 * 			is the stacktrace of error.
	 */
	public void showError(Frame activeFrame, String stacktrace)
	{
		//activeFrame.add(this.errorDialogView);
		this.errorDialogView.setStacktrace(stacktrace);
		this.doShow();
	}
}
