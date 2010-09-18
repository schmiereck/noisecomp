package de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.actions;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.MainPageLogic;

/**
 * Set-Input-Button Action-Logic Listener.
 *
 * @author smk
 * @version 22.02.2004
 */
public class SetInputButtonActionLogicListener 
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
	public SetInputButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
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
		//InputsWidgetData generatorInputsData = this.controllerData.getGeneratorInputsData();
		
		this.mainPageLogic.setInput(/*generatorInputsData, */false);
	}

}
