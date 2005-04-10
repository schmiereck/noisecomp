package de.schmiereck.noiseComp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
//import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.PauseButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.PlayButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.actions.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomInButtonActionLogicListener;
//import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.generator.ASRPulseGenerator;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypesData;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.screenTools.Runner;
import de.schmiereck.screenTools.graphic.MultiBufferFullScreenGraphicException;
import de.schmiereck.screenTools.scheduler.SchedulerWaiter;

/**
 * <p>
 * 	Hauptprogram zum generieren eines Sounds.
 * </p>
 *
 * @author smk
 * @version 21.01.2004
 */
public class Main
{
	public static void main(String[] args)
	{
		//----------------------------------------------------------------------
		// Build:
		
		boolean useFullScreen = false;
		//boolean useFullScreen = true;

		//======================================================================
		GeneratorTypesData generatorTypesData  = new GeneratorTypesData();

		Main.createBaseGeneratorTypes(generatorTypesData);
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("Main-Modul");

		generatorTypesData.addGeneratorTypeData(mainModulGeneratorTypeData);
		
		//======================================================================
		MainModel mainModel = new MainModel(generatorTypesData, mainModulGeneratorTypeData);
		
		MainView mainView;
		try
		{
			mainView = new MainView(useFullScreen, mainModel);

			Runner.run(mainModel.getControllerData(), 
					   mainModel.getControllerLogic(), 
					   mainView.getMultiBufferGraphic(), 
					   mainView.getInputListener(), 
					   mainModel.getWaiter(), 24, 16,
					   false, 
					   useFullScreen,
					   -1, -1);
		}
		catch (MultiBufferFullScreenGraphicException ex)
		{
			ex.printStackTrace(System.err);
		}
		
		// TODO das exit loswerden, alle Threads selber beenden.
		System.exit(0);
	}

	private static void createBaseGeneratorTypes(GeneratorTypesData generatorTypesData)
	{
		generatorTypesData.clear();
		
		generatorTypesData.addGeneratorTypeData(FaderGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(MixerGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(OutputGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(SinusGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(RectangleGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(CutGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(WaveGenerator.createGeneratorTypeData());
		generatorTypesData.addGeneratorTypeData(ASRPulseGenerator.createGeneratorTypeData());
	}

}
