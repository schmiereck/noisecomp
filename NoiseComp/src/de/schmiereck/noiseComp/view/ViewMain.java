package de.schmiereck.noiseComp.view;

import de.schmiereck.noiseComp.generator.ASRPulseGenerator;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;
import de.schmiereck.noiseComp.service.SoundService;
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
		ViewMain.createBaseGeneratorTypes();
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("ConsoleMain-Modul");

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

	private static void createBaseGeneratorTypes()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		soundService.removeAllGeneratorTypes();
		
		soundService.addGeneratorType(FaderGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(MixerGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(OutputGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(SinusGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(RectangleGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(CutGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(WaveGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(ASRPulseGenerator.createGeneratorTypeData());
		//==========================================================================================
	}

}
