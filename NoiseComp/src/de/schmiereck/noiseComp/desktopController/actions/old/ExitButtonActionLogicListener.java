package de.schmiereck.noiseComp.desktopController.actions.old;

import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;

/**
 * TODO docu
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
