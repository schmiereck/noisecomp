package de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * Select-Remove-Button Action-Logic Listener.
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class SelectRemoveButtonActionLogicListener
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
	public SelectRemoveButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
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
		GeneratorTypeData selectedGeneratorTypeData = this.selectGeneratorPageData.getGeneratorTypesListData().getSelectedGeneratorTypeData();

		if (selectedGeneratorTypeData == null)
		{
			throw new RuntimeException("no selected type");
		}
		
		if (!(selectedGeneratorTypeData instanceof ModulGeneratorTypeData))
		{
			throw new RuntimeException("the selected type is not a modul");
		}
		
		this.selectGeneratorPageData.getGeneratorTypesListData().removeSelectedGeneratorType();
		
		//this.controllerData.getGeneratorTypesListData().removeSelectedGeneratorType();
		
		this.controllerLogic.selectMainModul();
		
	}
}
