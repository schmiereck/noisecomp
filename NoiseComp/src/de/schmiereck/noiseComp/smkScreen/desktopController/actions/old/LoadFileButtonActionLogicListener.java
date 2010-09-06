package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import de.schmiereck.noiseComp.PopupRuntimeException;
import de.schmiereck.noiseComp.file.FileOperationInterface;
import de.schmiereck.noiseComp.file.LoadFileOperationLogic;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.TrackData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.MainActionException;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.xmlTools.XMLData;
import de.schmiereck.xmlTools.XMLException;
import de.schmiereck.xmlTools.XMLPort;

/**
 * Load a generators definition from a XML file into memory.
 *
 * @author smk
 * @version 21.02.2004
 */
public class LoadFileButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public LoadFileButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
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
		String fileName = this.controllerData.getLoadFileNameInputlineData().getInputText();
		
		fileName = fileName.trim();
		
		if (fileName.length() > 0)
		{	
			if (fileName.endsWith("." + FileOperationInterface.FILE_EXTENSION) == false)
			{
				fileName = fileName.concat("." + FileOperationInterface.FILE_EXTENSION);
			}
			
			//-----------------------------------------------------
			this.controllerData.clearTracks();
			
			//-----------------------------------------------------
			GeneratorTypesData generatorTypesData = this.controllerData.getGeneratorTypesData();
			
//			Generators mainGenerators = this.controllerData.getMainGenerators();
			
			// is the empty sound data object the generators are inserted.
			SoundData soundData = this.controllerData.getSoundData();

			//-----------------------------------------------------
			// Loading NoiseComp XML file:
			
			try
			{
				LoadFileOperationLogic.loadNoiseCompFile(generatorTypesData,
//														 mainGenerators,
														 fileName,
														 soundData.getFrameRate());
			}
			catch (Exception ex)
			{
				throw new MainActionException("while open xml file: \"" + fileName + "\"", ex);
			}

			//this.controllerLogic.updateEditModul(mainGenerators);
			this.controllerLogic.selectGeneratorsToEdit();

			//-----------------------------------------------------
			this.controllerData.setActiveDesktopPageData(this.controllerData.getMainDesktopPageData());
		}
		else
		{
			throw new MainActionException("file name is empty");
		}
		*/
	}
}
