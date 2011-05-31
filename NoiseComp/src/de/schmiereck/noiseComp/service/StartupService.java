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
import de.schmiereck.noiseComp.generator.AddGenerator;
import de.schmiereck.noiseComp.generator.CutGenerator;
import de.schmiereck.noiseComp.generator.EchoGenerator;
import de.schmiereck.noiseComp.generator.ExponentGenerator;
import de.schmiereck.noiseComp.generator.FaderGenerator;
import de.schmiereck.noiseComp.generator.FilterGenerator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.IntegratorGenerator;
import de.schmiereck.noiseComp.generator.LowpassFilterGenerator;
import de.schmiereck.noiseComp.generator.MixerGenerator;
import de.schmiereck.noiseComp.generator.ModuleGenerator;
import de.schmiereck.noiseComp.generator.ModuleGeneratorTypeData;
import de.schmiereck.noiseComp.generator.MultGenerator;
import de.schmiereck.noiseComp.generator.MultiplierGenerator;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.PinkNoise2Generator;
import de.schmiereck.noiseComp.generator.PinkNoiseGenerator;
import de.schmiereck.noiseComp.generator.PowGenerator;
import de.schmiereck.noiseComp.generator.RectangleGenerator;
import de.schmiereck.noiseComp.generator.ResonanceFilterGenerator;
import de.schmiereck.noiseComp.generator.SawtoothGenerator;
import de.schmiereck.noiseComp.generator.SinusGenerator;
import de.schmiereck.noiseComp.generator.TanhGenerator;
import de.schmiereck.noiseComp.generator.TriangelGenerator;
import de.schmiereck.noiseComp.generator.WaveGenerator;
import de.schmiereck.noiseComp.generator.WhiteNoiseGenerator;

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
	//**********************************************************************************************
	// Constants:
	
	/**
	 * Generator Folder-Path.
	 */
	public static final String GENERATOR_FOLDER_PATH = "/";
	
	/**
	 * Module Folder-Path.
	 */
	public static final String	MODULE_FOLDER_PATH	= "/File/Modules/";

	//**********************************************************************************************
	// Functions:
	
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
		soundService.addGeneratorType(AddGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(MultGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(PowGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(OutputGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(SinusGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(RectangleGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(TriangelGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(SawtoothGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(CutGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(WaveGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(ASRPulseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(WhiteNoiseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(PinkNoiseGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(PinkNoise2Generator.createGeneratorTypeData());
		soundService.addGeneratorType(TanhGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(ExponentGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(IntegratorGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(EchoGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(FilterGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(LowpassFilterGenerator.createGeneratorTypeData());
		soundService.addGeneratorType(ResonanceFilterGenerator.createGeneratorTypeData());
		
		//==========================================================================================
	}
	
	public static SourceDataLine createLine()
	{
		//==========================================================================================
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
		//==========================================================================================
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
	ModuleGeneratorTypeData createDemoGenerators(float frameRate)
	{
		//==========================================================================================
		SoundService soundService = SoundService.getInstance();
		
		//==========================================================================================
		// Sound-Generatoren für das Sound-Format des Ausgabekanals erzeugen:
		
		Float frameRateFloat = Float.valueOf(frameRate);
		
		//Generators generators = mainModuleypeData.getGenerators();

		final ModuleGeneratorTypeData mainModuleypeData = ModuleGenerator.createModuleGeneratorTypeData(MODULE_FOLDER_PATH);

		mainModuleypeData.setIsMainModuleGeneratorType(true);
		
		mainModuleypeData.setGeneratorTypeName("Main-Module");

		soundService.addGeneratorType(mainModuleypeData);
		
		//---------------------------------
		FaderGenerator faderInGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           FaderGenerator.class.getName());
			// or generatorTypeData.createGeneratorInstance("faderIn", Float.valueOf(frameRate))
			faderInGenerator = new FaderGenerator("faderIn", frameRateFloat, generatorTypeData);
			
			faderInGenerator.setTimePos(0.0F, 2.0F);
			
			faderInGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderInGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderInGenerator.setStartFadeValue(0.0F);
			//faderInGenerator.setEndFadeValue(1.0F);
			
			mainModuleypeData.addGenerator(faderInGenerator);
		}
		//---------------------------------
		FaderGenerator faderOutGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           FaderGenerator.class.getName());
			faderOutGenerator = new FaderGenerator("faderOut", frameRateFloat, generatorTypeData);
			
			faderOutGenerator.setTimePos(2.0F, 5.0F);
			
			faderOutGenerator.addInputValue(0.5F, FaderGenerator.INPUT_TYPE_START_VALUE);
			faderOutGenerator.addInputValue(0.0F, FaderGenerator.INPUT_TYPE_END_VALUE);
			//faderOutGenerator.setStartFadeValue(1.0F);
			//faderOutGenerator.setEndFadeValue(0.0F);
			
			mainModuleypeData.addGenerator(faderOutGenerator);
		}
		//---------------------------------
		SinusGenerator sinus0Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           SinusGenerator.class.getName());
			sinus0Generator = new SinusGenerator("sinus0", frameRateFloat, generatorTypeData);
			sinus0Generator.addInputValue(110F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinus0Generator.setTimePos(0.0F, 5.0F);
			
			mainModuleypeData.addGenerator(sinus0Generator);
		}
		//---------------------------------
		SinusGenerator sinus1Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           SinusGenerator.class.getName());
			sinus1Generator = new SinusGenerator("sinus1", frameRateFloat, generatorTypeData);
			sinus1Generator.addInputValue(220F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinusGenerator.setSignalFrequency(262F);
			
			sinus1Generator.setTimePos(0.0F, 5.0F);
			
			mainModuleypeData.addGenerator(sinus1Generator);
		}
		//---------------------------------
		SinusGenerator sinus2Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           SinusGenerator.class.getName());
			sinus2Generator = new SinusGenerator("sinus2", frameRateFloat, generatorTypeData);
			sinus2Generator.addInputValue(240F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus2Generator.setSignalFrequency(131F);
			
			sinus2Generator.setTimePos(0.0F, 5.0F);
			
			mainModuleypeData.addGenerator(sinus2Generator);
		}
		//---------------------------------
		SinusGenerator sinus3Generator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           SinusGenerator.class.getName());
			sinus3Generator = new SinusGenerator("sinus3", frameRateFloat, generatorTypeData);
			sinus3Generator.addInputValue(248F, SinusGenerator.INPUT_TYPE_FREQ);
			//sinus3Generator.setSignalFrequency(70F);
			
			sinus3Generator.setTimePos(0.0F, 5.0F);
			
			mainModuleypeData.addGenerator(sinus3Generator);
		}
		//---------------------------------
		MixerGenerator mixerGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           MixerGenerator.class.getName());
			mixerGenerator = new MixerGenerator("mixer", frameRateFloat, generatorTypeData);
			mixerGenerator.addInputValue(0.5F, MixerGenerator.INPUT_TYPE_VOLUME);
		
			mixerGenerator.setTimePos(0.0F, 5.0F);
			
			mixerGenerator.addVolumeInput(faderInGenerator);
			mixerGenerator.addVolumeInput(faderOutGenerator);
			
			mixerGenerator.addSignalInput(sinus0Generator);
			mixerGenerator.addSignalInput(sinus1Generator);
			mixerGenerator.addSignalInput(sinus2Generator);
			mixerGenerator.addSignalInput(sinus3Generator);
			
			mainModuleypeData.addGenerator(mixerGenerator);
		}
		//---------------------------------
		//---------------------------------
		OutputGenerator outputGenerator;
		{
			GeneratorTypeData generatorTypeData = soundService.searchGeneratorTypeData(GENERATOR_FOLDER_PATH,
			                                                                           OutputGenerator.class.getName());
			outputGenerator = new OutputGenerator("output", frameRateFloat, generatorTypeData);
	
			outputGenerator.setTimePos(0.0F, 5.0F);
			
			outputGenerator.setSignalInput(mixerGenerator);
			
			mainModuleypeData.addGenerator(outputGenerator);
		}		
		//==========================================================================================
		return mainModuleypeData;
	}
}
