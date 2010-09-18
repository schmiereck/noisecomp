package de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.actions;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.EditData;
import de.schmiereck.noiseComp.smkScreen.desktopController.editModulPage.EditModulPageData;
import de.schmiereck.noiseComp.smkScreen.desktopController.mainPage.MainPageLogic;

/**
 * Save-Group-Button Action-Logic Listener.
 *
 * @author smk
 * @version <p>22.02.2004: created, smk</p>
 */
public class SaveGroupButtonActionLogicListener
	implements ButtonActionLogicListenerInterface
{
	@SuppressWarnings("unused")
	private DesktopControllerLogic controllerLogic;
	private MainPageLogic mainPageLogic;
	private DesktopControllerData controllerData;
	private EditModulPageData editModulPageData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveGroupButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			MainPageLogic mainPageLogic,
			DesktopControllerData controllerData,
			EditModulPageData editModulPageData)
	{
		this.controllerLogic = controllerLogic;
		this.mainPageLogic = mainPageLogic;
		this.controllerData = controllerData;
		this.editModulPageData = editModulPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		String modulName = this.editModulPageData.getGroupNameInputlineData().getInputText();
		
		if (modulName != null)
		{
			modulName = modulName.trim();
			
			if (modulName.length() > 0)
			{	
				String modulDescription = "Generic Modul.";
				
				EditData editData = this.controllerData.getEditData();
				
				ModulGeneratorTypeData editModulTypeData = editData.getEditModulTypeData();
				//Generators generators;
				
				// Actualy no modul edited ?
				/*
				if (editModulTypeData == null)
				{	
					editModulTypeData = new ModulGeneratorTypeData(ModulGenerator.class, modulName, modulDescription);
	
					//generators = this.controllerData.getTracksListWidgetData().getGenerators();
					generators = editData.getEditGenerators();
					
					editModulTypeData.setGenerators(generators);
				
					this.controllerData.getGeneratorTypesData().addGeneratorTypeData(editModulTypeData);
				
					this.controllerData.clearTracks();
				}
				else
				*/
				{
					// A modul is actualy edited:
					
					editModulTypeData.setGeneratorTypeName(modulName);
					editModulTypeData.setGeneratorTypeDescription(modulDescription);
					
					//generators = editData.getEditGenerators();
				}
				
				editData.setEditModulGenerator(editModulTypeData);
				this.mainPageLogic.triggerEditGeneratorChanged(editData);
				
				this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
			}
			else
			{
				throw new RuntimeException("no modul name");
			}
		}
		else
		{
			throw new RuntimeException("no modul name");
		}
	}
}
