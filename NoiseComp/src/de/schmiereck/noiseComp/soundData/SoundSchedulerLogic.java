package de.schmiereck.noiseComp.soundData;

import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.screenTools.scheduler.SchedulerLogic;

/**
 * TODO docu
 *
 * @author smk
 * @version 26.01.2004
 */
public class SoundSchedulerLogic 
extends SchedulerLogic
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
	 * @see de.schmiereck.screenTools.scheduler.SchedulerLogic#notifyRunSchedul(long)
	 */
	public void notifyRunSchedul(long actualWaitPerFramesMillis)
	{
		if (this.playbackPaused == false)
		{	
			SourceDataLine line = this.soundData.getLine();
			
			SoundBufferManager soundBufferManager = soundData.getSoundBufferManager();
			
			soundBufferManager.pollGenerate();
			//soundBufferManager.pollGenerate();
			
			byte abData[] = soundData.getLineBufferData();
			
			int	nRead;
			try
			{
				nRead = soundBufferManager.read(abData);
				
				//System.out.println("nRead:" + nRead);
			}
			catch (IOException ex)
			{
				throw new RuntimeException("read sound data", ex);
			}
			int	nWritten = line.write(abData, 0, nRead);
			
			//System.out.println("nWritten:" + nWritten);
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
