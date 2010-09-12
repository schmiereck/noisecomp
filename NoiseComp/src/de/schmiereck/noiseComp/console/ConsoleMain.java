package de.schmiereck.noiseComp.console;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.service.StartupService;
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
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		StartupService.createBaseGeneratorTypes();
		
//		// new ModulGeneratorTypeData(null, null, null);
//		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();
//
//		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
//		
//		mainModulGeneratorTypeData.setGeneratorTypeName("Console-Main-Modul");
//
//		soundService.addGeneratorType(mainModulGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		SoundData soundData = new SoundData(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		ModulGeneratorTypeData mainModulGeneratorTypeData = 
			StartupService.createDemoGenerators(soundData.getFrameRate());

		OutputGenerator outputGenerator = mainModulGeneratorTypeData.getOutputGenerator();
		
		soundSourceLogic.setOutputGenerator(outputGenerator);

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
		//==========================================================================================
	}
}
