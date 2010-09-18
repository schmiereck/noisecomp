package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.EditModulPageData;

/**
 * Bearbeiten des gerade zum bearbeiten ausgew�hlten Moduls.
 *
 * @author smk
 * @version <p>22.02.2004: created, smk</p>
 */
public class GroupGeneratorButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	@SuppressWarnings("unused")
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public GroupGeneratorButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			DesktopControllerData controllerData,
			EditModulPageData editModulPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
		this.editModulPageData = editModulPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		ModulGeneratorTypeData editModulTypeData = this.controllerData.getEditData().getEditModulTypeData();
		
		String generatorTypeName;
		
		if (editModulTypeData != null)
		{	
			generatorTypeName = editModulTypeData.getGeneratorTypeName();
		}
		else
		{
			generatorTypeName = "";
		}
		this.editModulPageData.getGroupNameInputlineData().setInputText(generatorTypeName);
		
		this.controllerData.setActiveDesktopPageData(this.controllerData.getEditModulPageData());
	}
}
