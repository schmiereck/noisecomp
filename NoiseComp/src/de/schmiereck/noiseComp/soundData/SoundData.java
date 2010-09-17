package de.schmiereck.noiseComp.soundData;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;

/**
 * Verwaltet ein Output eines {@link Generator}-Objekt.
 * Stellt Funktionen zur Echtzeit Wiedergabe des Signals des Output-Generators zur Verfügung.
 * Verwaltet die für die Ausgabe nötigen Puffer-Objekte.
 *
 * TODO Knacksen bei pause und resume: http://code.google.com/p/pulpcore/source/browse/src/pulpcore/platform/applet/JavaSound.java?spec=svn904fbee4f0e91c96a9e983eb6dd98f8a99d11039&r=e4d270059719429e66f7fd1a53a445a1b2101f2d#472
 *
 * @author smk
 * @version 25.01.2004
 */
public class SoundData
{
	private static final int BUFFER_SIZE = 32000; //16000; 

	//nur noch die setOutput() aufrufen, wenn sich dieser ändert.
	//private Generators generators = null;

	//private OutputGenerator outputGenerator = null;
	private SoundSourceLogic	soundSourceLogic = null;
	
	private SourceDataLine line;
	
	private SoundBufferManager soundBufferManager;
	
	private byte lineBufferData[];
	
	private float frameRate;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundData(SourceDataLine line,
					 SoundSourceLogic soundSourceLogic)
	{
		super();
		
		this.line = line;
		
		this.frameRate = this.line.getFormat().getFrameRate();

		this.lineBufferData = new byte[BUFFER_SIZE];

		this.soundSourceLogic = soundSourceLogic;

		this.soundBufferManager = new SoundBufferManager(this.line.getFormat(), 
														 AudioSystem.NOT_SPECIFIED, 
														 SoundData.BUFFER_SIZE, 
														 soundSourceLogic);
	}
	
	/**
	 * @return the attribute {@link #outputGenerator}.
	public OutputGenerator getOutputGenerator()
	{
		return this.outputGenerator;
	}
	 */
	
	/**
	 * @param outputGenerator is the new value for attribute {@link #outputGenerator} to set.
	public void setOutputGenerator(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;

		this.soundBufferManager = new SoundBufferManager(this.line.getFormat(), this.frameRate,  
				AudioSystem.NOT_SPECIFIED, SoundData.BUFFER_SIZE, outputGenerator);
	}
	 */
	
	/**
	 * @return the attribute {@link #soundSourceLogic}.
	 */
	public SoundSourceLogic getSoundSourceLogic()
	{
		return this.soundSourceLogic;
	}
	/**
	 * @param soundSourceLogic 
	 * 			is the new value for attribute {@link #soundSourceLogic} to set.
	public void setSoundSourceLogic(SoundSourceLogic soundSourceLogic)
	{
		this.soundSourceLogic = soundSourceLogic;

		this.soundBufferManager = new SoundBufferManager(this.line.getFormat(), 
														 this.frameRate,  
														 AudioSystem.NOT_SPECIFIED, 
														 SoundData.BUFFER_SIZE, 
														 soundSourceLogic);
	}
	 */

	/**
	 * @return the attribute {@link #lineBufferData}.
	 */
	public byte[] getLineBufferData()
	{
		return this.lineBufferData;
	}
	/**
	 * @return the attribute {@link #soundBufferManager}.
	 */
	public SoundBufferManager getSoundBufferManager()
	{
		return this.soundBufferManager;
	}
	/**
	 * @return the attribute {@link #line}.
	 */
	public SourceDataLine getLine()
	{
		return this.line;
	}

	/**
	 * Start Playback of {@link #line}
	 */
	public void startPlayback()
	{
		SourceDataLine line = this.line;
		
		line.start();
	}

	/**
	 * Pause Playback of {@link #line}
	 */
	public void pausePlayback()
	{
		SourceDataLine line = this.line;

		line.flush();
		
		line.stop();
	}

	/**
	 * Resume Playback of {@link #line}
	 */
	public void resumePlayback()
	{
		SourceDataLine line = this.getLine();
		
		line.start();
	}
	
	/**
	 * Stop Playback of {@link #line}
	 */
	public void stopPlayback()
	{
		SoundBufferManager soundBufferManager = this.getSoundBufferManager();
		
		synchronized (soundBufferManager)
		{
			SourceDataLine line = this.getLine();
			
			line.flush();
			
			line.stop();
			
			soundBufferManager.stopGenerate();
		}
	}
	/**
	 * @return the attribute {@link #frameRate}.
	 */
	public float getFrameRate()
	{
		return this.frameRate;
	}
}
