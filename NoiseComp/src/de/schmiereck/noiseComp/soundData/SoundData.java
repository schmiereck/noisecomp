package de.schmiereck.noiseComp.soundData;

import java.util.Iterator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.Generators;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.soundBuffer.SoundBufferManager;

/**
 * Verwaltet eine Liste aus {@link Generator}-Objekten in einem {@link Generators}-Objekt.
 * Verwaltet ein Output-Objekt.
 * Stellt Funktionen zur Echtzeit Wiedergabe des Signals des Output-Generators zur Verfügung.
 * Verwaltet die für die Ausgabe nötigen Puffer-Objekte.
 *
 * @author smk
 * @version 25.01.2004
 */
public class SoundData
{
	private static final int BUFFER_SIZE = 16000; 

	private Generators generators = null;

	private SourceDataLine line;
	
	private SoundBufferManager soundBufferManager;
	
	private byte lineBufferData[];
	
	private float frameRate;
	
	/**
	 * Constructor.
	 * 
	 */
	public SoundData(SourceDataLine line, float frameRate)
	{
		super();
		
		this.line = line;
		
		this.frameRate = frameRate;

		this.lineBufferData = new byte[BUFFER_SIZE];
	}
	
	/**
	 * @return the attribute {@link #outputGenerator}.
	 */
	public OutputGenerator getOutputGenerator()
	{
		return this.generators.getOutputGenerator();
	}
	/**
	 * @param outputGenerator is the new value for attribute {@link #outputGenerator} to set.
	 */
	public void setOutputGenerator(OutputGenerator outputGenerator)
	{
		this.generators.setOutputGenerator(outputGenerator);

		this.soundBufferManager = new SoundBufferManager(this.line.getFormat(), this.frameRate,  
				AudioSystem.NOT_SPECIFIED, BUFFER_SIZE, outputGenerator);
	}
	/**
	 * @param generator is the Generator to add.
	public void addGenerator(Generator generator)
	{
		this.generators.addGenerator(generator);
	}
	 */

	/**
	 * @param trackPos
	 */
	public void removeGenerator(int trackPos)
	{
		this.generators.removeGenerator(trackPos);
	}
	
	/**
	 * @return a Iterator over the {@link Generator}-Objects.
	 */
	public Iterator getGeneratorsIterator()
	{
		return this.generators.getGeneratorsIterator();
	}

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
	 * 
	 */
	public void startPlayback()
	{
		SourceDataLine line = this.getLine();
		
		line.start();
	}

	/**
	 * 
	 */
	public void pausePlayback()
	{
		SourceDataLine line = this.getLine();
		
		line.stop();
	}

	/**
	 * 
	 */
	public void resumePlayback()
	{
		SourceDataLine line = this.getLine();
		
		line.start();
	}
	
	/**
	 * 
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

	/**
	 * 
	 */
	public void clear()
	{
		this.generators.clear();
	}

	/**
	 * @see #generators
	 */
	public void setGenerators(Generators generators)
	{
		this.generators = generators;
	}

	/**
	 * @return the attribute {@link #generators}.
	 */
	public Generators getGenerators()
	{
		return this.generators;
	}
	
}
