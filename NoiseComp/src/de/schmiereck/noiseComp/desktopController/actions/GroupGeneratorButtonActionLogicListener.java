package de.schmiereck.noiseComp.desktopController.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>22.02.2004: created, smk</p>
 */
public class GroupGeneratorButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public GroupGeneratorButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		ModulGeneratorTypeData editModulTypeData = this.controllerData.getEditModulTypeData();
		
		String generatorTypeName;
		
		if (editModulTypeData != null)
		{	
			generatorTypeName = editModulTypeData.getGeneratorTypeName();
		}
		else
		{
			generatorTypeName = "";
		}
		this.controllerData.getGroupNameInputlineData().setInputText(generatorTypeName);
		
		this.controllerData.setActiveDesktopPageData(this.controllerData.getGroupGeneratorPageData());
	}
}
