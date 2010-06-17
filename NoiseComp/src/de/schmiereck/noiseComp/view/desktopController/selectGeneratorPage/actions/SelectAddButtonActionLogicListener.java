package de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.view.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.view.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.view.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.view.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * Hinzuf�gen des ausgew�hlten Generators zu der Generator-Liste auf der ConsoleMain-Page.
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public class SelectAddButtonActionLogicListener
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
	public SelectAddButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
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

		SoundData soundData = this.controllerData.getSoundData();
		
		Generator generator = selectedGeneratorTypeData.createGeneratorInstance("generator" + this.controllerData.getTracksCount(), 
				soundData.getFrameRate());
		//sinusGenerator.addInputValue(1.0F, SinusGenerator.INPUT_TYPE_FREQ);
		
		this.controllerLogic.addDefaultInputs(generator);
		
		this.controllerLogic.addGenerator(generator);

		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}
}
