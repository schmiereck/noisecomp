package de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions;

import java.util.Iterator;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.mainPage.MainPageLogic;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.SelectGeneratorPageData;
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
	private MainPageLogic mainPageLogic;
	private DesktopControllerData controllerData;
	
	private SelectGeneratorPageData selectGeneratorPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectEditButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			MainPageLogic mainPageLogic,
			DesktopControllerData controllerData,
			SelectGeneratorPageData selectGeneratorPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.mainPageLogic = mainPageLogic;
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
		
		ModulGeneratorTypeData modulTypeData = (ModulGeneratorTypeData)selectedGeneratorTypeData;
		
		Generators generators = modulTypeData.getGenerators();

		this.mainPageLogic.clearTracks();
		
		this.controllerData.getSoundData().setGenerators(generators);
		
		this.controllerData.setEditGenerators(modulTypeData, generators);
		this.mainPageLogic.triggerEditGeneratorChanged(modulTypeData, generators);
		
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
