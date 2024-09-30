package de.schmiereck.noiseComp.console;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundData.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;


/*
 * Created on 31.10.2006, Copyright (c) schmiereck, 2006
 */
/**
 * <p>
 * 	ConsoleMain Function of Sound generator loaded from command line.
 * </p>
 * 
 * @author smk
 * @version <p>31.10.2006:	created, smk</p>
 */
public class ConsoleMain
{
	//**********************************************************************************************
	// Fields:

	private static SoundSchedulerLogic soundSchedulerLogic = null;

	private static SoundSourceSchedulerLogic soundSourceSchedulerLogic = null;
	
	//**********************************************************************************************
	// Functions:

	public static void main(String[] args)
	{
		//==========================================================================================
//		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		StartupService.createBaseGeneratorTypes();
		
//		// new ModuleGeneratorTypeData(null, null, null);
//		ModuleGeneratorTypeData mainModuleGeneratorTypeData = ModuleGenerator.createModuleGeneratorTypeData();
//
//		mainModuleGeneratorTypeData.setIsMainModuleGeneratorType(true);
//		
//		mainModuleGeneratorTypeData.setGeneratorTypeName("Console-Main-Module");
//
//		soundService.addGeneratorType(mainModuleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		SoundData soundData = new SoundData(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		ModuleGeneratorTypeData mainModuleGeneratorTypeData = 
			StartupService.createDemoGenerators(soundData.getFrameRate());

		soundSourceLogic.setMainModuleGeneratorTypeData(mainModuleGeneratorTypeData);;

		//------------------------------------------------------------------------------------------
	
		soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(32);
		
		// Start scheduled polling with the new SoundSource.
		soundSourceSchedulerLogic.setSoundSourceLogic(soundSourceLogic);

		soundSourceSchedulerLogic.startThread();
		
		//------------------------------------------------------------------------------------------
		
//		String waitStr = "";
//		for (int i = 0; i < 10000; i++)
//		{
//			waitStr += "WAIT ";
//		}
//		waitStr = null;
		
//		try
//		{
//			ConsoleMain.class.wait(1000);
//		}
//		catch (InterruptedException ex)
//		{
//			throw new RuntimeException(ex);
//
//		}
		//------------------------------------------------------------------------------------------
		soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);
		
		soundSchedulerLogic.startThread();

		soundSchedulerLogic.startPlayback();
		
		//------------------------------------------------------------------------------------------
		SoundBufferManager soundBufferManager = soundData.getSoundBufferManager();
		
		OutputGenerator outputGenerator = mainModuleGeneratorTypeData.getOutputGenerator();

		float endTimePos = outputGenerator.getEndTimePos();
		
		do
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace(System.err);
			}
		}
		while (soundBufferManager.getActualTime() <= endTimePos);

		//------------------------------------------------------------------------------------------
		//System.exit(0);

		soundSchedulerLogic.stopPlayback();
		soundSchedulerLogic.stopThread();
		soundSourceSchedulerLogic.stopThread();

		//==========================================================================================
	}
}
