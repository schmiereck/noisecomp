package de.schmiereck.noiseComp.desktopController.actions;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.desktopPage.widgets.TrackData;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>06.03.2004: created, smk</p>
 */
public class SelectEditButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectEditButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		
		ModulGeneratorTypeData modulTypeData = (ModulGeneratorTypeData)selectedGeneratorTypeData;
		
		Generators generators = modulTypeData.getGenerators();

		this.controllerData.getTracksData().clearTracks();
		
		this.controllerData.getSoundData().setGenerators(generators);
		
		this.controllerData.setEditGenerators(modulTypeData, generators);
		
		//-----------------------------------------------------
		// Generators updating in actual View:
		
		Iterator generatorsIterator = generators.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			this.controllerLogic.addTrackData(new TrackData(generator));
		}
		
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}
}
