package de.schmiereck.noiseComp.smkScreen.desktopController.actions.old;

import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.MainActionException;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;

/**
 * Load a generators definition from a XML file into memory.
 *
 * @author smk
 * @version 21.02.2004
 */
public class LoadFileButtonActionLogicListener 
implements ButtonActionLogicListenerInterface
{
	@SuppressWarnings("unused")
	private DesktopControllerLogic controllerLogic;
	
	@SuppressWarnings("unused")
	private DesktopControllerData controllerData;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public LoadFileButtonActionLogicListener(DesktopControllerLogic controllerLogic, DesktopControllerData controllerData)
	{
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
