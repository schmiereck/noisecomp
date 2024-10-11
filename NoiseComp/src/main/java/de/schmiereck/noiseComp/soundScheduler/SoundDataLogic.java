package de.schmiereck.noiseComp.soundScheduler;

import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;

/**
 * Verwaltet ein Output eines {@link de.schmiereck.noiseComp.generator.Generator}-Objekt.
 * Stellt Funktionen zur Echtzeit Wiedergabe des Signals des Output-Generators zur Verfügung.
 * Verwaltet die für die Ausgabe nötigen Puffer-Objekte.
 *
 * TODO Knacksen bei pause und resume: http://code.google.com/p/pulpcore/source/browse/src/pulpcore/platform/applet/JavaSound.java?spec=svn904fbee4f0e91c96a9e983eb6dd98f8a99d11039&r=e4d270059719429e66f7fd1a53a445a1b2101f2d#472
 *
 * @author smk
 * @version 25.01.2004
 */
public class SoundDataLogic {
	public static final int BUFFER_SIZE = 32000; //16000;

	private SourceDataLine line;
	
	private SoundBufferManager soundBufferManager;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundDataLogic(final SourceDataLine line,
						  final SoundSourceLogic soundSourceLogic) {
		this.line = line;

		this.soundBufferManager = new SoundBufferManager(this.line.getFormat(), SoundDataLogic.BUFFER_SIZE, soundSourceLogic);
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
	public SourceDataLine getLine() {
		return this.line;
	}

	/**
	 * Start Playback of {@link #line}
	 */
	public void startPlayback() {
		this.line.start();
	}

	/**
	 * Pause Playback of {@link #line}
	 */
	public void pausePlayback() {
		this.line.flush();

		this.line.stop();
	}

	/**
	 * Resume Playback of {@link #line}
	 */
	public void resumePlayback() {
		this.line.start();
	}
	
	/**
	 * Stop Playback of {@link #line}
	 */
	public void stopPlayback() {
		final SoundBufferManager soundBufferManager = this.getSoundBufferManager();
		
		synchronized (soundBufferManager) {
			this.line.flush();

			this.line.stop();
			
			soundBufferManager.stopGenerate();
		}
	}
	/**
	 * @return the attribute frameRate of the {@link #line}.
	 */
	public float getFrameRate() {
		return this.line.getFormat().getFrameRate();
	}
}
