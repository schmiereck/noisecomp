package de.schmiereck.noiseComp.view.desktopController.actions.old;

import de.schmiereck.noiseComp.view.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.view.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class NewButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public NewButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
//		this.controllerData.clearTracks();

		//this.controllerData.createBaseGeneratorTypes();
	}

}
