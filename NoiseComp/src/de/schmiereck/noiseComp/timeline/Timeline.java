/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorBufferInterface;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;

/**
 * <p>
 * 	Timeline.
 * </p>
 * <p>
 * 	Manages a generator, his inputs, outputs and a buffer with his generated samples.
 * </p>
 * <p>
 * 	Provides a change distribution system depending on changing inputs.
 * </p>
 * 
 * @author smk
 * @version <p>05.10.2010:	created, smk</p>
 */
public class Timeline
implements GeneratorBufferInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Generator.
	 */
	private Generator generator = null;
	
	/**
	 * Input Timelines.
	 * 
	 * Key is the {@link InputData} of the source input timeline generator.
	 */
	private Map<InputData, Timeline> inputTimelines = new HashMap<InputData, Timeline>();
	
	/**
	 * Output Timelines.
	 * 
	 * Key is the {@link InputData} of the target output timeline generator.
	 */
	private Map<InputData, Timeline> outputTimelines = new HashMap<InputData, Timeline>();
	
	/**
	 * Buffered Sound Samples between start and end output of {@link #generator}.
	 */
	private SoundSample[] bufSoundSamples = new SoundSample[0];
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #generator}.
	 */
	public Generator getGenerator()
	{
		return this.generator;
	}

	/**
	 * @param generator 
	 * 			to set {@link #generator}.
	 */
	public synchronized void setGenerator(Generator generator)
	{
		//==========================================================================================
		this.generator = generator;
		
		//------------------------------------------------------------------------------------------
		float startTimePos = this.generator.getStartTimePos();
		float endTimePos = this.generator.getEndTimePos();
		
		float timeLength = endTimePos - startTimePos;
		
		int bufSize = (int)(this.generator.getSoundFrameRate() * timeLength);
		
		this.bufSoundSamples = new SoundSample[bufSize];

		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTimelines}.
	 */
	public Map<InputData, Timeline> getInputTimelines()
	{
		return this.inputTimelines;
	}

	/**
	 * @param inputData
	 * 			to add to {@link #inputTimelines} as key.
	 * @param inputTimeline
	 * 			to add to {@link #inputTimelines} as value.
	 */
	public void addInputTimeline(InputData inputData, Timeline inputTimeline)
	{
		this.inputTimelines.put(inputData, inputTimeline);
	}

	/**
	 * @return 
	 * 			returns the {@link #outputTimelines}.
	 */
	public Map<InputData, Timeline> getOutputTimelines()
	{
		return this.outputTimelines;
	}

	/**
	 * @param inputData
	 * 			to add to {@link #outputTimelines} as key.
	 * @param outputTimeline
	 * 			to add to {@link #outputTimelines} as value.
	 */
	public void addOutputTimeline(InputData inputData, Timeline outputTimeline)
	{
		this.outputTimelines.put(inputData, outputTimeline);
	}
	
	/**
	 * Wird aufgerufen, um den Ausgangswert eines Generators für die angegebene 
	 * Frame-Position zu ermitteln.
	 * 
	 * Liefert <code>null</code>, wenn der Generator für den Zeitpunkt keinen Wert 
	 * generieren kann (Frame-Position nicht zwischen Start und Ende).
	 * 
	 * @see Generator#generateFrameSample(long, ModulGenerator, GeneratorBufferInterface)
	 * 
	 * @param framePosition
	 * 			ist the position of the sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample generateFrameSample(long framePosition, ModulGenerator parentModulGenerator)
	{
		//==========================================================================================
		SoundSample retSoundSample;
		
		int bufFramePos = this.makeBufferFramePos(framePosition);
		
		if (bufFramePos >= 0)
		{
			retSoundSample = this.bufSoundSamples[bufFramePos];
			
			if (retSoundSample == null)
			{
				retSoundSample = this.generator.generateFrameSample(framePosition,
				                                                    parentModulGenerator, 
				                                                    this);
				
				this.bufSoundSamples[bufFramePos] = retSoundSample;
			}
		}
		else
		{
			retSoundSample = null;
		}
		
		//==========================================================================================
		return retSoundSample;
	}

	/**
	 * @see Generator#getSoundFrameRate()
	 * 
	 * @return
	 * 			the Frame rate of the outgoing sound.
	 */
	public float getSoundFrameRate()
	{
		return this.generator.getSoundFrameRate();
	}

	/**
	 * @return
	 * 			the iterator of {@link Generator#getInputsIterator()}.
	 */
	public Iterator<InputData> getInputsIterator()
	{
		return this.generator.getInputsIterator();
	}

	/**
	 * @return
	 * 			the vaue of {@link Generator#getInputsCount()}.
	 */
	public int getInputsCount()
	{
		return this.generator.getInputsCount();
	}

	/**
	 * @param sampleFramePos
	 * 			is the sampleFrame position.
	 * @return
	 * 			is the buffered sample.
	 */
	public SoundSample getBufSoundSample(long sampleFramePos)
	{
		//==========================================================================================
		SoundSample soundSample;
		
		int bufFramePos = this.makeBufferFramePos(sampleFramePos);
		
		if (bufFramePos >= 0)
		{
			soundSample = this.bufSoundSamples[bufFramePos];
		}
		else
		{
			soundSample = null;
		}
		//==========================================================================================
		return soundSample;
	}

	/**
	 * @param sampleFrame
	 * 			is the sample Frame.
	 * @return
	 * 			is the buffer Frame or <code>-1</code> if not in buffer range.
	 */
	private int makeBufferFramePos(long sampleFrame)
	{
		//==========================================================================================
		float startTimePos = this.generator.getStartTimePos();
		
		long startFrame = (long)(this.generator.getSoundFrameRate() * startTimePos);
		
		int bufFramePos = (int)(sampleFrame - startFrame);
		
		// Frame is not in buffer range?
		if (((bufFramePos >= 0) && (bufFramePos < this.bufSoundSamples.length)) == false)
		{
			bufFramePos = -1;
		}
		
		//==========================================================================================
		return bufFramePos;
	}
	
	/**
	 * Fill buffer.
	 * Clear buffer (on changes).
	 */
	
}
