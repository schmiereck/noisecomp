package de.schmiereck.noiseComp.desktopPage;

import de.schmiereck.noiseComp.desktopController.DesktopGraphic;

/*
 * Created on 20.03.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	Shows Message to User.
 * </p>
 * 
 * @author smk
 * @version <p>20.03.2005:	created, smk</p>
 */
public class ShowMessageListener
{
	private DesktopGraphic desktopGraphic;

	public ShowMessageListener(DesktopGraphic desktopGraphic)
	{
		this.desktopGraphic = desktopGraphic;
	}
	
	/**
	 * @param message
	 */
	public void showErrorMessage(String message)
	{
		this.desktopGraphic.showErrorMessage(message);
	}
}
