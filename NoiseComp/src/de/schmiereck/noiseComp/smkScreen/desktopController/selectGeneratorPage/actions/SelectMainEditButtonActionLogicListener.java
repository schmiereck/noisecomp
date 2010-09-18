package de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * Select-Main-Edit-Button Action-Logic Listener.
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class SelectMainEditButtonActionLogicListener
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	@SuppressWarnings("unused")
	private SelectGeneratorPageData selectGeneratorPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectMainEditButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			DesktopControllerData controllerData,
			SelectGeneratorPageData selectGeneratorPageData)
	{
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
		this.selectGeneratorPageData = selectGeneratorPageData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.controllerLogic.selectMainModul();
		
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}

}
