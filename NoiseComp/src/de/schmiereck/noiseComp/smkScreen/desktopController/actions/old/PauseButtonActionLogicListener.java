package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;

/**
 * Pause-Button Action-Logic Listener.
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class PauseButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	@SuppressWarnings("unused")
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public PauseButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.controllerLogic.pauseSound();
	}
}
