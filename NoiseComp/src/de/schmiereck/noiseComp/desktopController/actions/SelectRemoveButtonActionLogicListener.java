package de.schmiereck.noiseComp.desktopController.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class SelectRemoveButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectRemoveButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		GeneratorTypeData selectedGeneratorTypeData = this.controllerData.getGeneratorTypesListData().getSelectedGeneratorTypeData();

		if (selectedGeneratorTypeData == null)
		{
			throw new RuntimeException("no selected type");
		}
		
		if (!(selectedGeneratorTypeData instanceof ModulGeneratorTypeData))
		{
			throw new RuntimeException("the selected type is not a modul");
		}
		
		this.controllerData.getGeneratorTypesListData().removeSelectedGeneratorType();
		
		//this.controllerData.getGeneratorTypesListData().removeSelectedGeneratorType();
		
		this.controllerLogic.selectMainModul();
		
	}
}
