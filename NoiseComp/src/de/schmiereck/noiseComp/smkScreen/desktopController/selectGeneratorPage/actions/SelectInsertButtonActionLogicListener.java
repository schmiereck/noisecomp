package de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.actions;

import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.ButtonActionLogicListenerInterface;
import de.schmiereck.noiseComp.smkScreen.desctopPage.widgets.InputWidgetData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.smkScreen.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.smkScreen.desktopController.selectGeneratorPage.SelectGeneratorPageData;

/**
 * Hinzuf�gen eines neuen Generator-Moduls zu der Auswahl-Liste.
 *
 * @author smk
 * @version <p>22.06.2004: created, smk</p>
 */
public class SelectInsertButtonActionLogicListener
implements ButtonActionLogicListenerInterface
{
	private DesktopControllerLogic controllerLogic;
	private DesktopControllerData controllerData;
	
	private SelectGeneratorPageData selectGeneratorPageData;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SelectInsertButtonActionLogicListener(DesktopControllerLogic controllerLogic, 
			DesktopControllerData controllerData,
			SelectGeneratorPageData selectGeneratorPageData)
	{
		super();
		
		this.controllerLogic = controllerLogic;
		this.controllerData = controllerData;
		this.selectGeneratorPageData = selectGeneratorPageData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.desktopPage.widgets.ButtonActionLogicListenerInterface#notifyButtonReleased(de.schmiereck.noiseComp.desktopPage.widgets.InputWidgetData)
	 */
	public void notifyButtonReleased(InputWidgetData buttonData)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		GeneratorTypeData selectedGeneratorTypeData = this.selectGeneratorPageData.getGeneratorTypesListData().getSelectedGeneratorTypeData();
		/*
		if (selectedGeneratorTypeData == null)
		{
			throw new RuntimeException("no selected type");
		}

		SoundData soundData = this.controllerData.getSoundData();
		
		Generator generator = selectedGeneratorTypeData.createGeneratorInstance("generator" + this.controllerData.getTracksCount(), 
				soundData.getFrameRate());
		//sinusGenerator.addInputValue(1.0F, SinusGenerator.INPUT_TYPE_FREQ);
		
		this.controllerLogic.addDefaultInputs(generator);
		
		this.controllerLogic.addGenerator(generator);

		*/
		//this.controllerData.setActiveDesktopPageData(this.controllerData.getSelectNewGeneratorPageData());
		
		//----------------------------------------------------------------------
		ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)ModulGenerator.createGeneratorTypeData(); 

		modulGeneratorTypeData.setGeneratorTypeName("unnamed");
		modulGeneratorTypeData.setGeneratorTypeDescription("New created modul.");
		
		soundService.addGeneratorType(modulGeneratorTypeData);

		//----------------------------------------------------------------------
		float frameRate = this.controllerData.getSoundData().getFrameRate();
		
		//---------------------------------
		{
			OutputGenerator outputGenerator;
			
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", Float.valueOf(frameRate), generatorTypeData);
	
			outputGenerator.setStartTimePos(0.0F);
			outputGenerator.setEndTimePos(5.0F);
			
			//outputGenerator.setSignalInput(mixerGenerator);
			
			modulGeneratorTypeData.addGenerator(outputGenerator);
		}		
		
		//----------------------------------------------------------------------
		this.controllerData.getEditData().setEditModulGenerator(modulGeneratorTypeData);
		
		this.controllerData.setActiveDesktopPageData(this.controllerData.getEditModulPageData());
		
		//==========================================================================================
	}
}
