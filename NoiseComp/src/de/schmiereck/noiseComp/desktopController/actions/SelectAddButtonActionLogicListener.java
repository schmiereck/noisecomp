package de.schmiereck.noiseComp.desktopController.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.soundData.SoundData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>25.02.2004: created, smk</p>
 */
public class SelectAddButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectAddButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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

		if (selectedGeneratorTypeData== null)
		{
			throw new RuntimeException("no selected type");
		}
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());

		SoundData soundData = this.controllerData.getSoundData();
		TracksData tracksData = this.controllerData.getTracksData();
		
		Generator generator = selectedGeneratorTypeData.createGeneratorInstance("generator" + tracksData.getTracksCount(), 
				soundData.getFrameRate());
		//sinusGenerator.addInputValue(1.0F, SinusGenerator.INPUT_TYPE_FREQ);
		
		this.controllerLogic.addDefaultInputs(generator);
		
		this.controllerLogic.addGenerator(generator);
	}
}
