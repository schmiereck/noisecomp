/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.service;

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
import de.schmiereck.noiseComp.generator.MultiplierGenerator;
import de.schmiereck.noiseComp.generator.PinkNoise2Generator;
import de.schmiereck.noiseComp.generator.PinkNoiseGenerator;
import de.schmiereck.noiseComp.generator.WhiteNoiseGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;

/**
 * <p>
 * 	Startup Service.
 * </p>
 * 
 * @author smk
 * @version <p>02.09.2010:	created, smk</p>
 */
public class StartupService
{
	/**
	 * Create build in generator types.
	 */
	public static void createBaseGeneratorTypes()
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		soundService.removeAllGeneratorTypes();
		
		soundService.addGeneratorType(FaderGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(MixerGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(MultiplierGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(OutputGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(SinusGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(RectangleGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(CutGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(WaveGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(ASRPulseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(WhiteNoiseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(PinkNoiseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(PinkNoise2Generator.createGeneratorTypeData());
		//==========================================================================================
	}
	
	public static SourceDataLine createLine()
	{
		//SourceDataLine sourceDataLine = new SourceDataLine();
		
		// Gewünschtes Audioformat definieren:
		
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

	/**
	 * Creates a demo list of generators with different types.
	 * It's only for developing.
	 * 
	 * @param frameRate
	 * 			is the frame rate.
	 * 
	 */
	public static 
	ModulGeneratorTypeData createDemoGenerators(float frameRate)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:
		
		Float frameRateFloat = Float.valueOf(frameRate);
		
		//Generators generators = mainModulTypeData.getGenerators();

		final ModulGeneratorTypeData mainModulTypeData = ModulGenerator.createModulGeneratorTypeData();

		mainModulTypeData.setIsMainModulGeneratorType(true);
		
		mainModulTypeData.setGeneratorTypeName("Main-Modul");

		soundService.addGeneratorType(mainModulTypeData);
		
		//---------------------------------
		FaderGenerator faderInGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(FaderGenerator.class.getName());
			// or generatorTypeData.createGeneratorInstance("faderIn", Float.valueOf(frameRate))
			faderInGenerator = new FaderGenerator("faderIn", frameRateFloat, generatorTypeData);
			
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
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(FaderGenerator.class.getName());
			faderOutGenerator = new FaderGenerator("faderOut", frameRateFloat, generatorTypeData);
			
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
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinusGenerator = new SinusGenerator("sinus", frameRateFloat, generatorTypeData);
			sinusGenerator.addInputValue(262F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinusGenerator.setStartTimePos(0.0F);
			sinusGenerator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinusGenerator);
		}
		//---------------------------------
		SinusGenerator sinus2Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus2Generator = new SinusGenerator("sinus2", frameRateFloat, generatorTypeData);
			sinus2Generator.addInputValue(131F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus2Generator.setSignalFrequency(131F);
			
			sinus2Generator.setStartTimePos(0.0F);
			sinus2Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus2Generator);
		}
		//---------------------------------
		SinusGenerator sinus3Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(SinusGenerator.class.getName());
			sinus3Generator = new SinusGenerator("sinus3", frameRateFloat, generatorTypeData);
			sinus3Generator.addInputValue(70F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus3Generator.setSignalFrequency(70F);
			
			sinus3Generator.setStartTimePos(0.0F);
			sinus3Generator.setEndTimePos(5.0F);
			
			mainModulTypeData.addGenerator(sinus3Generator);
		}
		//---------------------------------
		MixerGenerator mixerGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(MixerGenerator.class.getName());
			mixerGenerator = new MixerGenerator("mixer", frameRateFloat, generatorTypeData);
		
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
		//---------------------------------
		OutputGenerator outputGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", frameRateFloat, generatorTypeData);
	
			outputGenerator.setStartTimePos(0.0F);
			outputGenerator.setEndTimePos(5.0F);
			
			outputGenerator.setSignalInput(mixerGenerator);
			
			mainModulTypeData.addGenerator(outputGenerator);
		}		
		return mainModulTypeData;
	}
}
