package de.schmiereck.noiseComp.console;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.ASRPulseGenerator;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;
import de.schmiereck.noiseComp.service.SoundService;
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
	private static SoundSchedulerLogic soundSchedulerLogic = null;

	private static SoundSourceSchedulerLogic soundSourceSchedulerLogic = null;
	
	public static void main(String[] args)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		ConsoleMain.createBaseGeneratorTypes();
		
		// new ModulGeneratorTypeData(null, null, null);
		ModulGeneratorTypeData mainModulGeneratorTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulGeneratorTypeData.setIsMainModulGeneratorType(true);
		
		mainModulGeneratorTypeData.setGeneratorTypeName("ConsoleMain-Modul");

		soundService.addGeneratorType(mainModulGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		// Setup Sound:
		
		SourceDataLine line = ConsoleMain.createLine();
		
		//------------------------------------------------------------------------------------------
		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		SoundData soundData = new SoundData(line, soundSourceLogic);
		
		//------------------------------------------------------------------------------------------
		OutputGenerator outputGenerator;
		
		outputGenerator = ConsoleMain.createDemoGenerators(soundData.getFrameRate(), 
		                                                   mainModulGeneratorTypeData);

		soundSourceLogic.setOutputGenerator(outputGenerator);
		
		//------------------------------------------------------------------------------------------
	
		soundSourceSchedulerLogic = new SoundSourceSchedulerLogic(16);
		
		// Start scheduled polling with the new SoundSource.
		soundSourceSchedulerLogic.setSoundSourceLogic(soundSourceLogic);

		soundSourceSchedulerLogic.startThread();
		
		//------------------------------------------------------------------------------------------
		String waitStr = "";
		for (int i = 0; i < 10000; i++)
		{
			waitStr += "WAIT ";
		}
		waitStr = null;
		
		//------------------------------------------------------------------------------------------
		soundSchedulerLogic = new SoundSchedulerLogic(25, soundData);
		
		soundSchedulerLogic.startThread();

		soundSchedulerLogic.startPlayback();
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

	private static SourceDataLine createLine()
	{
		//SourceDataLine sourceDataLine = new SourceDataLine();
		
		// Gew�nschtes Audioformat definieren:
		
		float sampleRate 		= 44100;		// the number of samples per second
		int sampleSizeInBits 	= 16;			// the number of bits in each sample
		int channels 			= 2;			// the number of channels (1 for mono, 2 for stereo, and so on)
		int frameSize 			= 4;			// the number of bytes in each frame
		float frameRate 		= sampleRate;	// the number of frames per second
		boolean bigEndian 		= true; 		// indicates whether the data for a single sample is stored in big-endian byte order 
												// (false means little-endian)
												// Because Java inherently creates big-endian data, 
												// you must do a lot of extra work to create little-endian audio data in Java.  
												// Therefore, I elected to create all of the synthetic sounds in this lesson in big-endian order.

		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,	// encoding the audio encoding technique
												  sampleRate, 
												  sampleSizeInBits, 
												  channels, 
												  frameSize, 
												  frameRate, 
												  bigEndian);
		
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat);

		// Ausgabekanal mit den gew�nschten Eigenschaften holen und �ffnen:
		
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


	/**
	 * Creates a demo list of generators with different types.
	 * It's only for developing.
	 * 
	 * @return
	 * 			the output generator.
	 */
	public static OutputGenerator createDemoGenerators(float frameRate, 
	                                                   ModulGeneratorTypeData mainModulTypeData)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:
		
		//Generators generators = mainModulTypeData.getGenerators();

		//---------------------------------
		FaderGenerator faderInGenerator;
		{
			GeneratorTypeData generatorTypeData =
				soundService.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderInGenerator = new FaderGenerator("faderIn", Float.valueOf(frameRate), generatorTypeData);
			
			faderInGenerator.setStartTimePos(0.0F);
			faderInGenerator.setEndTimePos(2.0F);
			
			faderInGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderInGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderInGenerator.setStartFadeValue(0.0F);
			//faderInGenerator.setEndFadeValue(1.0F);
			
			mainModulTypeData.addGenerator(faderInGenerator);
		}
		//---------------------------------
		FaderGenerator faderOutGenerator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderOutGenerator = new FaderGenerator("faderOut", Float.valueOf(frameRate), generatorTypeData);
			
			faderOutGenerator.setStartTimePos(2.0F);
			faderOutGenerator.setEndTimePos(5.0F);
			
			faderOutGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderOutGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderOutGenerator.setStartFadeValue(1.0F);
			//faderOutGenerator.setEndFadeValue(0.0F);
			
			mainModulTypeData.addGenerator(faderOutGenerator);
		}
		//---------------------------------
		SinusGenerator sinusGenerator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinusGenerator = new SinusGenerator("sinus", Float.valueOf(frameRate), generatorTypeData);
			sinusGenerator.addInputValue(262F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinusGenerator.setStartTimePos(0.0F);
			sinusGenerator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinusGenerator);
		}
		//---------------------------------
		SinusGenerator sinus2Generator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus2Generator = new SinusGenerator("sinus2", Float.valueOf(frameRate), generatorTypeData);
			sinus2Generator.addInputValue(131F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus2Generator.setSignalFrequency(131F);
			
			sinus2Generator.setStartTimePos(0.0F);
			sinus2Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus2Generator);
		}
		//---------------------------------
		SinusGenerator sinus3Generator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus3Generator = new SinusGenerator("sinus3", Float.valueOf(frameRate), generatorTypeData);
			sinus3Generator.addInputValue(70F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus3Generator.setSignalFrequency(70F);
			
			sinus3Generator.setStartTimePos(0.0F);
			sinus3Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus3Generator);
		}
		//---------------------------------
		MixerGenerator mixerGenerator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(MixerGenerator.class.getName());
			mixerGenerator = new MixerGenerator("mixer", Float.valueOf(frameRate), generatorTypeData);
		
			mixerGenerator.setStartTimePos(0.0F);
			mixerGenerator.setEndTimePos(5.0F);
			
			mixerGenerator.addVolumeInput(faderInGenerator);
			mixerGenerator.addVolumeInput(faderOutGenerator);
			
			mixerGenerator.addSignalInput(sinusGenerator);
			mixerGenerator.addSignalInput(sinus2Generator);
			mixerGenerator.addSignalInput(sinus3Generator);
			
			mainModulTypeData.addGenerator(mixerGenerator);
		}
		//---------------------------------
		OutputGenerator outputGenerator;
		{
			GeneratorTypeData generatorTypeData = 
				soundService.searchGeneratorTypeData(OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", Float.valueOf(frameRate), generatorTypeData);
	
			outputGenerator.setStartTimePos(0.0F);
			outputGenerator.setEndTimePos(5.0F);
			
			outputGenerator.setSignalInput(mixerGenerator);
			
			mainModulTypeData.addGenerator(outputGenerator);
		}		
		return outputGenerator;
	}
}
