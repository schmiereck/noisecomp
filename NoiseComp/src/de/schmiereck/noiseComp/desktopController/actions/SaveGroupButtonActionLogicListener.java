package de.schmiereck.noiseComp.desktopController.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TracksData;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>22.02.2004: created, smk</p>
 */
public class SaveGroupButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveGroupButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		String modulName = this.controllerData.getGroupNameInputlineData().getInputText();
		
		modulName = modulName.trim();
		
		if (modulName.length() > 0)
		{	
			String modulDescription = "XXX";
			
			ModulGeneratorTypeData modulTypeData = new ModulGeneratorTypeData(ModulGenerator.class, modulName, modulDescription);

			Generators generators = this.controllerData.getTracksData().getGenerators();
			
			modulTypeData.setGenerators(generators);
			
			this.controllerData.getGeneratorTypesData().addGeneratorTypeData(modulTypeData);
			
			this.controllerData.getTracksData().clearTracks();
			
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			throw new RuntimeException("no modul name");
		}
	}
}
