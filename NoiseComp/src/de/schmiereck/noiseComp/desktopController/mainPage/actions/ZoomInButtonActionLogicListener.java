package de.schmiereck.noiseComp.desktopController.mainPage.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.mainPage.MainPageLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.ScrollbarData;

/**
 * TODO docu
 *
 * @author smk
 * @version 22.02.2004
 */
public class ZoomInButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private MainPageLogic mainPageLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public ZoomInButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			MainPageLogic mainPageLogic, 
			DesktopControllerData controllerData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.mainPageLogic = mainPageLogic;
		this.controllerData = controllerData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.mainPageLogic.doChangeZoom(2.0F);
	}

}
