package de.schmiereck.noiseComp.soundBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import junit.framework.TestCase;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.soundData.SoundData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>31.05.2004: created, smk</p>
 */
public class SoundBufferManagerTest
	extends TestCase
{
	private SoundBufferManager soundBufferManager;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp()
		throws Exception
	{
		super.setUp();
		
		SourceDataLine line = SoundBufferManagerTest.createLine();

		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
		
		SoundData soundData = new SoundData(line, soundSourceLogic);
		
		//soundData.setSoundSourceLogic(soundSourceLogic);
		
		GeneratorTypeData generatorTypeData = OutputGenerator.createGeneratorTypeData();
		
		OutputGenerator outputGenerator = new OutputGenerator("out", 
															  new Float(line.getFormat().getFrameRate()), 
															  generatorTypeData);
		
		
		outputGenerator.setStartTimePos(0.0F);
		outputGenerator.setEndTimePos(3.0F);
		
		soundSourceLogic.setOutputGenerator(outputGenerator);
		
		this.soundBufferManager = soundData.getSoundBufferManager();
	}

	public void testPollGenerate()
	{
		assertNull(this.soundBufferManager.getWaitingGeneratorBuffer());
		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
		assertNull(this.soundBufferManager.getPlayingGeneratorBuffer());
		
		this.soundBufferManager.pollGenerate();

		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
		assertNull(this.soundBufferManager.getWaitingGeneratorBuffer());
		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());

		this.soundBufferManager.pollGenerate();

		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());

		this.soundBufferManager.pollGenerate();

		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());

		this.soundBufferManager.pollGenerate();

		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
	}

	private static SourceDataLine createLine()
	{
		//SourceDataLine sourceDataLine = new SourceDataLine();
		
		// Gew�nschtes Audioformat definieren:
		
		float sampleRate = 44100;
		
		// Because Java inherently creates big-endian data, 
		// you must do a lot of extra work to create little-endian audio data in Java.  
		// Therefore, I elected to create all of the synthetic sounds in this lesson in big-endian order.
		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
												  sampleRate, 16, 2, 4, 
												  sampleRate, true);
		
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
}