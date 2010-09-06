package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.schmiereck.noiseComp.file.FileOperationInterface;
import de.schmiereck.noiseComp.file.SaveFileOperationLogic;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.MainActionException;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLPort;
import de.schmiereck.xmlTools.XMLPortException;

/**
 * Load the actual generators definitions in the memory in a XML file.
 *
 * @author smk
 * @version 21.02.2004
 */
public class SaveFileButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SaveFileButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData) 
	throws MainActionException
	{
		/*
		String fileName = this.controllerData.getSaveFileNameInputlineData().getInputText();
		
		fileName = fileName.trim();
		
		if (fileName.length() > 0)
		{	
			if (fileName.endsWith("." + FileOperationInterface.FILE_EXTENSION) == false)
			{
				fileName = fileName.concat("." + FileOperationInterface.FILE_EXTENSION);
			}
			
			GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
			
			///Generators generators = soundData.getGenerators();
			Generators mainGenerators = this.controllerData.getMainGenerators();
			
			try
			{
				SaveFileOperationLogic.saveFile(generatorTypesData,
												mainGenerators,
												fileName);
			}
			catch (XMLPortException ex)
			{
				throw new MainActionException("Wile saveing file \"" + fileName + "\".", ex);
			}
		
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			// TODO show ERROR message
		}
		*/
	}

}
