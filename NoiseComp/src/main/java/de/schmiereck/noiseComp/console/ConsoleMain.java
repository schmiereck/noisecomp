package de.schmiereck.noiseComp.console;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.signal.OutputGenerator;
import de.schmiereck.noiseComp.service.SoundService;
import de.schmiereck.noiseComp.service.StartupService;
import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.soundScheduler.SoundSchedulerLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.soundSource.SoundSourceSchedulerLogic;
import de.schmiereck.noiseComp.soundScheduler.SoundSchedulerData;


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

	public static void main(final String[] args) {
		//==========================================================================================
		final SoundService soundService = new SoundService();
		
		//==========================================================================================
		StartupService.createBaseGeneratorTypes(soundService);
		
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

		final SourceDataLine line = StartupService.createLine();
		
		//------------------------------------------------------------------------------------------
		final SoundSourceLogic soundSourceLogic = new SoundSourceLogic();

		final SoundDataLogic soundDataLogic = new SoundDataLogic(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		final ModuleGeneratorTypeData mainModuleGeneratorTypeData =
			StartupService.createDemoGenerators(soundService, soundDataLogic.getFrameRate());

		soundSourceLogic.setMainModuleGeneratorTypeData(mainModuleGeneratorTypeData);;

		//------------------------------------------------------------------------------------------
	
		soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(32);
		
		// Start scheduled polling with the new SoundSource.
		soundSourceSchedulerLogic.setSoundSourceLogic(soundSourceLogic);

		soundSourceSchedulerLogic.startThread();
		
		//------------------------------------------------------------------------------------------
		final SoundSchedulerData soundSchedulerData = new SoundSchedulerData(25);
		soundSchedulerLogic = new SoundSchedulerLogic(soundSchedulerData, soundDataLogic);
		
		soundSchedulerLogic.startThread();

		soundSchedulerLogic.startPlayback();
		
		//------------------------------------------------------------------------------------------
		final SoundBufferManager soundBufferManager = soundDataLogic.getSoundBufferManager();

		final OutputGenerator outputGenerator = mainModuleGeneratorTypeData.getOutputGenerator();

		float endTimePos = outputGenerator.getEndTimePos();
		
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		} while (soundBufferManager.getActualTime() <= endTimePos);

		//------------------------------------------------------------------------------------------
		//System.exit(0);

		soundSchedulerLogic.stopPlayback();
		soundSchedulerLogic.stopThread();
		soundSourceSchedulerLogic.stopThread();

		//==========================================================================================
	}
}
