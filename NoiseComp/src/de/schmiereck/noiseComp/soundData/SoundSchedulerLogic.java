package de.schmiereck.noiseComp.soundData;

import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic;
import de.schmiereck.screenTools.scheduler.PipelineSchedulerLogicTest;
import de.schmiereck.screenTools.scheduler.SchedulerLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version 26.01.2004
 */
public class SoundSchedulerLogic 
extends PipelineSchedulerLogic
{
	private SoundData soundData;
	private boolean playbackPaused = false;

	/**
	 * Constructor.
	 * 
	 * @param targetFramesPerSecond
	 */
	public SoundSchedulerLogic(int targetFramesPerSecond, SoundData soundData)
	{
		super(targetFramesPerSecond);
		
		this.soundData = soundData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic#notifyRunSchedulCalc(long)
	 */
	public void notifyRunSchedulCalc(long actualWaitPerFramesMillis)
	{
		if (this.playbackPaused == false)
		{	
			System.out.println("CALC: " + actualWaitPerFramesMillis);

			SoundBufferManager soundBufferManager = this.soundData.getSoundBufferManager();
			
			soundBufferManager.pollGenerate();
			//soundBufferManager.pollGenerate();
		}
	}
	
	/* (non-Javadoc)
	 * @see de.schmiereck.screenTools.scheduler.PipelineSchedulerLogic#notifyRunSchedulOut(long)
	 */
	public void notifyRunSchedulOut(long actualWaitPerFramesMillis)
	{
		if (this.playbackPaused == false)
		{	
			System.out.println("OUT: " + actualWaitPerFramesMillis);
			SourceDataLine line = this.soundData.getLine();
			
			SoundBufferManager soundBufferManager = this.soundData.getSoundBufferManager();
			
			//soundBufferManager.pollGenerate();
			
			byte abData[] = this.soundData.getLineBufferData();
			
			try
			{
				//int numBytesToRead = line.available();
				//if (numBytesToRead == -1) break;
				//int nRead = soundBufferManager.read(abData, 0, numBytesToRead);
				
				int nRead = soundBufferManager.read(abData);

				int	nWritten = line.write(abData, 0, nRead);
				
				//System.out.println("actualWaitPerFramesMillis: " + actualWaitPerFramesMillis + ", nWritten:" + nWritten + ", nRead: " + nRead);
				//System.out.println("nRead:" + nRead);
			}
			catch (IOException ex)
			{
				throw new RuntimeException("read sound data", ex);
			}
		}
	}

	/**
	 * 
	 */
	public void startPlayback()
	{
		this.soundData.startPlayback();
	}
	
	/**
	 * 
	 */
	public void pausePlayback()
	{
		this.soundData.pausePlayback();
		
		this.playbackPaused = true;
	}
	
	/**
	 * 
	 */
	public void resumePlayback()
	{
		this.playbackPaused = false;
		
		this.soundData.resumePlayback();
	}
	
	/**
	 * 
	 */
	public void stopPlayback()
	{
		this.soundData.stopPlayback();
	}

}
