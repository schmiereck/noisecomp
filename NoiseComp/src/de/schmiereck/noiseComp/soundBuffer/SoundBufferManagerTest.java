package de.schmiereck.noiseComp.soundBuffer;

import junit.framework.TestCase;

/**
 * Sound-Buffer-Manager Test.
 *
 * @author smk
 * @version <p>31.05.2004: created, smk</p>
 */
public class SoundBufferManagerTest
	extends TestCase
{
//	private SoundBufferManager soundBufferManager;
//	/*
//	 * @see TestCase#setUp()
//	 */
//	protected void setUp()
//		throws Exception
//	{
//		super.setUp();
//		
//		SourceDataLine line = StartupService.createLine();
//
//		SoundSourceLogic soundSourceLogic = new SoundSourceLogic();
//		
//		SoundData soundData = new SoundData(line, soundSourceLogic);
//		
//		//soundData.setSoundSourceLogic(soundSourceLogic);
//		
//		GeneratorTypeData generatorTypeData = OutputGenerator.createGeneratorTypeData();
//		
//		OutputGenerator outputGenerator = new OutputGenerator("out", 
//															  new Float(line.getFormat().getFrameRate()), 
//															  generatorTypeData);
//		
//		
//		outputGenerator.setStartTimePos(0.0F);
//		outputGenerator.setEndTimePos(3.0F);
//		
//		//soundSourceLogic.setOutputGenerator(outputGenerator);
//		soundSourceLogic.setMainModuleGeneratorTypeData(mainModuleGeneratorTypeData);
//		
//		this.soundBufferManager = soundData.getSoundBufferManager();
//	}
//
//	public void testPollGenerate()
//	{
//		assertNull(this.soundBufferManager.getWaitingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getPlayingGeneratorBuffer());
//		
//		this.soundBufferManager.pollGenerate();
//
//		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getWaitingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
//
//		this.soundBufferManager.pollGenerate();
//
//		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
//		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
//
//		this.soundBufferManager.pollGenerate();
//
//		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
//		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
//
//		this.soundBufferManager.pollGenerate();
//
//		assertNotNull(this.soundBufferManager.getPlayingGeneratorBuffer());
//		assertNotNull(this.soundBufferManager.getWaitingGeneratorBuffer());
//		assertNull(this.soundBufferManager.getGeneratingGeneratorBuffer());
//	}
}
