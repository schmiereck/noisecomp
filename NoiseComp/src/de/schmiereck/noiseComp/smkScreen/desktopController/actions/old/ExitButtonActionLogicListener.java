package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;

/**
 * Exit-Button Action Logic Listener.
 *
 * @author smk
 * @version 21.02.2004
 */
public class ExitButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic gameControllerLogic;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public ExitButtonActionLogicListener(DesktopControllerLogic gameControllerLogic)
	{
		super();
		
		this.gameControllerLogic = gameControllerLogic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.gameControllerLogic.doEndGame();
	}

}
