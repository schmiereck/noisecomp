package de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.MainPageLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * Edit generators of the selected ModulGenerator.
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
			throw new RuntimeException("The selected type \"" + selectedGeneratorTypeData.getGeneratorTypeName() + "\" is not a modul.");
		}
		
		ModulGeneratorTypeData modulTypeData = (ModulGeneratorTypeData)selectedGeneratorTypeData;
		
		//Generators generators = modulTypeData.getGenerators();

		this.controllerLogic.selectModulGeneratorToEdit(modulTypeData);
		/*
		// Clear the list with the prviouse selected generators.
		this.mainPageLogic.clearTracks();

		this.controllerData.getEditData().setEditModulGenerator(modulTypeData);
		this.mainPageLogic.triggerEditGeneratorChanged(this.controllerData.getEditData());
		
		//-----------------------------------------------------
		// Generators updating in actual View:
		
		Iterator generatorsIterator = generators.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = (Generator)generatorsIterator.next();
			
			this.controllerLogic.addTrackData(new TrackData(generator));
		}
		*/
		this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
	}
}
