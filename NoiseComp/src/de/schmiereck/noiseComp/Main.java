package de.schmiereck.noiseComp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
import de.schmiereck.noiseComp.desktopController.actions.AddGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.GroupGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PauseButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.PlayButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.StopButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.CancelGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.InputTypeEditSaveActionListener;
import de.schmiereck.noiseComp.desktopController.editModulPage.actions.SaveGroupButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.AddInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.NewInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.RemoveInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.mainPage.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectAddButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectInsertButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectMainEditButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.selectGeneratorPage.actions.SelectRemoveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
import de.schmiereck.noiseComp.desktopPage.ShowMessageListener;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.screenTools.Runner;
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
		//------------------------------------
		// Setup Sound:
		
		SourceDataLine line = Main.createLine();
		
		float frameRate = line.getFormat().getFrameRate();

		SoundData soundData = new SoundData(line, frameRate);
		
		Generators generators = new Generators();

		//------------------------------------
		///soundData.setGenerators(generators);
		
		//------------------------------------
		// Setup Desktop:
		
		boolean useFullScreen = false;
		//boolean useFullScreen = true;
		String playerName = null;
		
		SchedulerWaiter waiter = new SchedulerWaiter();

		DesktopGraphic multiBufferGraphic;
		
		multiBufferGraphic = new DesktopGraphic();
		
		DesktopControllerData controllerData = new DesktopControllerData(soundData, generators);

		multiBufferGraphic.initGrafic(controllerData);

		DesktopInputListener inputListener = new DesktopInputListener();
		
		ShowMessageListener showMessageListener = new ShowMessageListener(multiBufferGraphic);
		
		DesktopControllerLogic controllerLogic = new DesktopControllerLogic(controllerData, 
																			inputListener, 
																			showMessageListener,
																			waiter, 
																			playerName);
		
		inputListener.setGraphic(multiBufferGraphic);
		inputListener.setGameControllerLogic(controllerLogic);
		
		AddGeneratorButtonActionLogicListener addGeneratorButtonActionLogicListener	= new AddGeneratorButtonActionLogicListener(controllerLogic, controllerData);
		RemoveGeneratorButtonActionLogicListener removeGeneratorButtonActionLogicListener = new RemoveGeneratorButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		GroupGeneratorButtonActionLogicListener groupGeneratorButtonActionLogicListener = new GroupGeneratorButtonActionLogicListener(controllerLogic, controllerData, controllerData.getEditModulPageData());

		PlayButtonActionLogicListener playButtonActionLogicListener = new PlayButtonActionLogicListener(controllerLogic, controllerData);
		PauseButtonActionLogicListener pauseButtonActionLogicListener = new PauseButtonActionLogicListener(controllerLogic, controllerData);
		StopButtonActionLogicListener stopButtonActionLogicListener = new StopButtonActionLogicListener(controllerLogic, controllerData);
		
		ExitButtonActionLogicListener exitButtonActionLogicListener = new ExitButtonActionLogicListener(controllerLogic);
		NewButtonActionLogicListener newButtonActionLogicListener = new NewButtonActionLogicListener(controllerLogic, controllerData);

		ZoomInButtonActionLogicListener zoomInButtonActionLogicListener = new ZoomInButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener = new ZoomOutButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		
		SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener = new SetGeneratorButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		SetInputButtonActionLogicListener setInputButtonActionLogicListener = new SetInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		RemoveInputButtonActionLogicListener removeInputButtonActionLogicListener = new RemoveInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		NewInputButtonActionLogicListener newInputButtonActionLogicListener = new NewInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		AddInputButtonActionLogicListener addInputButtonActionLogicListener = new AddInputButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData);
		
		SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener = new SelectCancelButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectAddButtonActionLogicListener selectAddButtonActionLogicListener = new SelectAddButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectInsertButtonActionLogicListener selectInsertButtonActionLogicListener = new SelectInsertButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectEditButtonActionLogicListener selectEditButtonActionLogicListener = new SelectEditButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData, controllerData.getSelectGeneratorPageData());
		SelectMainEditButtonActionLogicListener selectMainEditButtonActionLogicListener = new SelectMainEditButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		SelectRemoveButtonActionLogicListener selectRemoveButtonActionLogicListener = new SelectRemoveButtonActionLogicListener(controllerLogic, controllerData, controllerData.getSelectGeneratorPageData());
		
		SaveButtonActionLogicListener saveButtonActionLogicListener = new SaveButtonActionLogicListener(controllerLogic, controllerData);
		SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener = new SaveCancelButtonActionLogicListener(controllerLogic, controllerData);
		SaveFileButtonActionLogicListener saveFileButtonActionLogicListener = new SaveFileButtonActionLogicListener(controllerLogic, controllerData);
		
		LoadButtonActionLogicListener loadButtonActionLogicListener = new LoadButtonActionLogicListener(controllerLogic, controllerData);
		LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener = new LoadCancelButtonActionLogicListener(controllerLogic, controllerData);
		LoadFileButtonActionLogicListener loadFileButtonActionLogicListener = new LoadFileButtonActionLogicListener(controllerLogic, controllerData);
		
		CancelGroupButtonActionLogicListener cancelGroupButtonActionLogicListener = new CancelGroupButtonActionLogicListener(controllerLogic, controllerData);
		SaveGroupButtonActionLogicListener saveGroupButtonActionLogicListener = new SaveGroupButtonActionLogicListener(controllerLogic, controllerLogic.getMainPageLogic(), controllerData, controllerData.getEditModulPageData());
		
		InputTypeEditSaveActionListener inputTypeEditSaveActionListener = new InputTypeEditSaveActionListener(controllerLogic, controllerData, controllerData.getEditModulPageData());
		
		controllerData.setActionListeners(
				addGeneratorButtonActionLogicListener,
				removeGeneratorButtonActionLogicListener,
				groupGeneratorButtonActionLogicListener,
				
				playButtonActionLogicListener,
				pauseButtonActionLogicListener,
				stopButtonActionLogicListener,
				
				exitButtonActionLogicListener,
				newButtonActionLogicListener,
				
				zoomInButtonActionLogicListener,
				zoomOutButtonActionLogicListener,
				
				setGeneratorButtonActionLogicListener,
				setInputButtonActionLogicListener,
				removeInputButtonActionLogicListener,
				newInputButtonActionLogicListener,
				addInputButtonActionLogicListener,
				
				selectCancelButtonActionLogicListener,
				selectAddButtonActionLogicListener,
				selectInsertButtonActionLogicListener,
				selectEditButtonActionLogicListener,
				selectMainEditButtonActionLogicListener,
				selectRemoveButtonActionLogicListener,
										  
				saveButtonActionLogicListener,
				saveCancelButtonActionLogicListener,
				saveFileButtonActionLogicListener,
										  
				loadButtonActionLogicListener,
				loadCancelButtonActionLogicListener,
				loadFileButtonActionLogicListener,
				
				cancelGroupButtonActionLogicListener,
				saveGroupButtonActionLogicListener,
				
				inputTypeEditSaveActionListener);
		
		//------------------------------------
		// run:
		
		Runner.run(controllerData, controllerLogic, 
				   multiBufferGraphic, inputListener, 
				   waiter, 24, 16,
				   false, useFullScreen,
				   -1, -1);
		
		// TODO das exit loswerden, alle Threads selber beenden.
		System.exit(0);
	}
	
	private static SourceDataLine createLine()
	{
		//SourceDataLine sourceDataLine = new SourceDataLine();
		
		// Gewünschtes Audioformat definieren:
		
		float sampleRate = 44100;
		
		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
												  sampleRate, 16, 2, 4, 
												  sampleRate, false);
		
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat);

		// Ausgabekanal mit den gewünschten Eigenschaften holen und öffnen:
		
		SourceDataLine line;
		
		try
		{
			line = (SourceDataLine)AudioSystem.getLine(info);
		}
		catch (LineUnavailableException ex)
		{
			throw new RuntimeException("getLine info", ex);
		}
		
		try
		{
			line.open(audioFormat);
		}
		catch (LineUnavailableException ex)
		{
			throw new RuntimeException("open line", ex);
		}
		
		//line.start();
		return line;
	}

}
