package de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.MainPageLogic;

/**
 * Remove-Generator-Button Action-Logic Listener.
 *
 * @author smk
 * @version <p>22.02.2004: created, smk</p>
 */
public class RemoveGeneratorButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	@SuppressWarnings("unused")
	private DesktopControllerLogic controllerLogic;
	private MainPageLogic mainPageLogic;
	@SuppressWarnings("unused")
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public RemoveGeneratorButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			MainPageLogic mainPageLogic,
			DesktopControllerData controllerData)
	{
		this.controllerLogic = controllerLogic;
		this.mainPageLogic = mainPageLogic;
		this.controllerData = controllerData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.mainPageLogic.removeSelectedTrack();
	}
}
