package de.schmiereck.noiseComp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.desktopController.DesktopControllerData;
import de.schmiereck.noiseComp.desktopController.DesktopControllerLogic;
import de.schmiereck.noiseComp.desktopController.DesktopGraphic;
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
		
		DesktopControllerLogic gameControllerLogic = new DesktopControllerLogic(controllerData, inputListener, waiter, playerName);
		
		inputListener.setGraphic(multiBufferGraphic);
		inputListener.setGameControllerLogic(gameControllerLogic);
		
		//------------------------------------
		// run:
		
		Runner.run(controllerData, gameControllerLogic, 
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
