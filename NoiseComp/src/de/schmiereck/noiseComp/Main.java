package de.schmiereck.noiseComp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
import de.schmiereck.noiseComp.desktopController.actions.ExitButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.LoadFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.NewButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SaveFileButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SelectCancelButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SetGeneratorButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.SetInputButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ZoomInButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopController.actions.ZoomOutButtonActionLogicListener;
import de.schmiereck.noiseComp.desktopInput.DesktopInputListener;
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
		
		SourceDataLine line = createLine();
		
		float frameRate = line.getFormat().getFrameRate();

		SoundData soundData = new SoundData(line, frameRate);
		
		//------------------------------------
		// Setup Desktop:
		
		boolean useFullScreen = true;
		String playerName = null;
		
		SchedulerWaiter waiter = new SchedulerWaiter();

		DesktopGraphic multiBufferGraphic;
		
		multiBufferGraphic = new DesktopGraphic();
		
		DesktopControllerData controllerData = new DesktopControllerData(soundData);//graphicBridge);

		multiBufferGraphic.initGrafic(controllerData);

		DesktopInputListener inputListener = new DesktopInputListener();
		
		DesktopControllerLogic controllerLogic = new DesktopControllerLogic(controllerData, inputListener, waiter, playerName);
		
		inputListener.setGraphic(multiBufferGraphic);
		inputListener.setGameControllerLogic(controllerLogic);
		
		ExitButtonActionLogicListener exitButtonActionLogicListener = new ExitButtonActionLogicListener(controllerLogic);
		NewButtonActionLogicListener newButtonActionLogicListener = new NewButtonActionLogicListener(controllerLogic, controllerData);

		ZoomInButtonActionLogicListener zoomInButtonActionLogicListener = new ZoomInButtonActionLogicListener(controllerLogic, controllerData);
		ZoomOutButtonActionLogicListener zoomOutButtonActionLogicListener = new ZoomOutButtonActionLogicListener(controllerLogic, controllerData);
		
		SetGeneratorButtonActionLogicListener setGeneratorButtonActionLogicListener = new SetGeneratorButtonActionLogicListener(controllerLogic, controllerData);
		SetInputButtonActionLogicListener setInputButtonActionLogicListener = new SetInputButtonActionLogicListener(controllerLogic, controllerData);
		
		SelectCancelButtonActionLogicListener selectCancelButtonActionLogicListener = new SelectCancelButtonActionLogicListener(controllerLogic, controllerData);
		
		SaveButtonActionLogicListener saveButtonActionLogicListener = new SaveButtonActionLogicListener(controllerLogic, controllerData);
		SaveCancelButtonActionLogicListener saveCancelButtonActionLogicListener = new SaveCancelButtonActionLogicListener(controllerLogic, controllerData);
		SaveFileButtonActionLogicListener saveFileButtonActionLogicListener = new SaveFileButtonActionLogicListener(controllerLogic, controllerData);
		
		LoadButtonActionLogicListener loadButtonActionLogicListener = new LoadButtonActionLogicListener(controllerLogic, controllerData);
		LoadCancelButtonActionLogicListener loadCancelButtonActionLogicListener = new LoadCancelButtonActionLogicListener(controllerLogic, controllerData);
		LoadFileButtonActionLogicListener loadFileButtonActionLogicListener = new LoadFileButtonActionLogicListener(controllerLogic, controllerData);
		
		controllerData.setActionListeners(exitButtonActionLogicListener,
										  newButtonActionLogicListener,
				
				zoomInButtonActionLogicListener,
				zoomOutButtonActionLogicListener,
				
				setGeneratorButtonActionLogicListener,
				setInputButtonActionLogicListener,
				
				selectCancelButtonActionLogicListener,
										  
				saveButtonActionLogicListener,
				saveCancelButtonActionLogicListener,
				saveFileButtonActionLogicListener,
										  
				loadButtonActionLogicListener,
				loadCancelButtonActionLogicListener,
				loadFileButtonActionLogicListener);
		
		//------------------------------------
		// run:
		
		Runner.run(controllerData, controllerLogic, 
				multiBufferGraphic, inputListener, 
				waiter, 24, 8,
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
