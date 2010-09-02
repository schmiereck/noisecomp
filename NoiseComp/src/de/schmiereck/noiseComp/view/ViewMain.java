package de.schmiereck.noiseComp.view;

import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.screenTools.Runner;
import de.schmiereck.screenTools.graphic.GraphicMediator;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;

/**
 * <p>
 * 	Hauptprogram zum generieren eines Sounds.
 * </p>
 *
 * @author smk
 * @version 21.01.2004
 */
public class ViewMain
{
	/**
	 * @param args
	 * 			are the command line arguments.
	 */
	public static void main(String[] args)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Build:
		
		boolean useFullScreen = false;
		//boolean useFullScreen = true;

		//==========================================================================================
		StartupService.createBaseGeneratorTypes();
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("Main-Modul");

		soundService.addGeneratorType(mainModulGeneratorTypeData);
		
		//==========================================================================================
		GraphicMediator graphicMediator = new GraphicMediator();
		
		MainController mainController = new MainController(mainModulGeneratorTypeData,
		                                                   graphicMediator);
		
		MainView mainView;
		try
		{
			mainView = new MainView(useFullScreen, mainController);

			graphicMediator.setScreenGraphic(mainView.getMultiBufferGraphic());

			Runner.run(mainController.getControllerData(), 
					   mainController.getControllerLogic(), 
					   mainView.getMultiBufferGraphic(), 
					   mainView.getInputListener(), 
					   mainController.getWaiter(), 
					   24, 16,
					   false, 
					   useFullScreen,
					   -1, -1);
		}
		catch (MultiBufferFullScreenGraphicException ex)
		{
			ex.printStackTrace(System.err);
		}
		
		//==========================================================================================
		// TODO das exit loswerden, alle Threads selber beenden.
		System.exit(0);
		//==========================================================================================
	}

}
