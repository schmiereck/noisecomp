package de.schmiereck.noiseComp.desktopController.editModulPage.actions;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.editModulPage.EditModulPageData;
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
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveGroupButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
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
		String modulName = this.editModulPageData.getGroupNameInputlineData().getInputText();
		
		modulName = modulName.trim();
		
		if (modulName.length() > 0)
		{	
			String modulDescription = "Generic Modul.";
			
			ModulGeneratorTypeData editModulTypeData = this.controllerData.getEditModulTypeData();
			Generators generators;
			
			// Actualy no modul edited ?
			if (editModulTypeData == null)
			{	
				editModulTypeData = new ModulGeneratorTypeData(ModulGenerator.class, modulName, modulDescription);

				generators = this.controllerData.getTracksData().getGenerators();
			
				editModulTypeData.setGenerators(generators);
			
				this.controllerData.getGeneratorTypesData().addGeneratorTypeData(editModulTypeData);
			
				this.controllerData.clearTracks();
			}
			else
			{
				// A modul is actualy edited:
				
				editModulTypeData.setGeneratorTypeName(modulName);
				editModulTypeData.setGeneratorTypeDescription(modulDescription);
				
				generators = this.controllerData.getEditGenerators();
			}
			
			this.controllerData.setEditGenerators(editModulTypeData, generators);
			
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			throw new RuntimeException("no modul name");
		}
	}
}
