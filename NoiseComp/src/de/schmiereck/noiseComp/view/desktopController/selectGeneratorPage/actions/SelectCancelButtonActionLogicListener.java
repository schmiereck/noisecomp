package de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.view.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.view.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class SelectCancelButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	private SelectGeneratorPageData selectGeneratorPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectCancelButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			DesktopControllerData controllerData,
			SelectGeneratorPageData selectGeneratorPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
		this.selectGeneratorPageData = selectGeneratorPageData;
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}

}
