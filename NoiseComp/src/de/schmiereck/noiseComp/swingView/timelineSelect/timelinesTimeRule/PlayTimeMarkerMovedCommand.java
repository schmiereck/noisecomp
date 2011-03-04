/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import de.schmiereck.noiseComp.swingView.appController.AppController;

/**
 * <p>
 * 	Play-Time-Marker Moved Command.
 * </p>
 * 
 * @author smk
 * @version <p>04.03.2011:	created, smk</p>
 */
public class PlayTimeMarkerMovedCommand
{
	//**********************************************************************************************
	// Fields:

	/**
	 * App Controller.
	 */
	private final AppController appController;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param appController
	 * 			is the App Controller.
	 */
	public PlayTimeMarkerMovedCommand(AppController appController)
	{
		//==========================================================================================
		this.appController = appController;
		
		//==========================================================================================
	}

	/**
	 * @param playTimePos
	 * 			is the play time pos.
	 */
	public void doSetPlayMarker(double playTimePos)
	{
		//==========================================================================================
		this.appController.doSubmitPlaybackPos((float)playTimePos);
		
		//==========================================================================================
	}
}
