package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;

/**
 * Save-Cancel-Button Action-Logic Listener.
 *
 * @author smk
 * @version 21.02.2004
 */
public class SaveCancelButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	@SuppressWarnings("unused")
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveCancelButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}
	
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}

}
