/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorBufferInterface;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulArguments;
import de.schmiereck.noiseComp.generator.ModulGenerator;
import de.schmiereck.noiseComp.generator.ModulGeneratorRemoveListenerInterface;
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
implements GeneratorBufferInterface, 
		   ModulGeneratorRemoveListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Generator.
	 */
	private Generator generator = null;
	
	private float startTimePos = 0.0F;
	
	private float endTimePos = 0.0F;
	
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
	
	/**
	 * Sub-Modul Generator Timelines.
	 */
	private Map<Generator, Timeline> subGeneratorTimelines = new HashMap<Generator, Timeline>();
	
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
		
		this.changeTimePos(startTimePos, endTimePos);

		//------------------------------------------------------------------------------------------
//		this.generator.getGeneratorChangeObserver().registerGeneratorChangeListener
//		(
//		 	new GeneratorChangeListenerInterface()
//		 	{
//				@Override
//				public void notifyGeneratorChanged(Generator generator, 
//				                                   float changedStartTimePos, float changedEndTimePos)
//				{
//					generatorChanged(changedStartTimePos, changedEndTimePos);
//				}
//		 	}
//		);
		
		//------------------------------------------------------------------------------------------
		this.generator.addModulGeneratorRemoveListener(this);
		
		//==========================================================================================
	}

	/**
	 * Change start- and end-time of timeline.
	 * Reinitialize buffer.
	 * 
	 * @param startTimePos
	 * 			is the startTimePos.
	 * @param endTimePos
	 * 			is the endTimePos.
	 */
	private void changeTimePos(float startTimePos, float endTimePos)
	{
		//==========================================================================================
		this.startTimePos = startTimePos;
		this.endTimePos = endTimePos;
		
		float timeLength = endTimePos - startTimePos;
		
		int bufSize = (int)(this.generator.getSoundFrameRate() * timeLength);
		
		this.bufSoundSamples = new SoundSample[bufSize];
		
		//==========================================================================================
	}

	/**
	 * Change start end end time of timeline and generator.
	 * Reinitialize buffer.
	 * 
	 * @see #changeTimePos(float, float)
	 * 
	 * @param startTimePos
	 * 			is the startTimePos.
	 * @param endTimePos
	 * 			is the endTimePos.
	 */
	public void setTimePos(float startTimePos, float endTimePos)
	{
		//==========================================================================================
		float changedStartTimePos 	= Math.min(this.startTimePos, startTimePos);
		float changedEndTimePos		= Math.max(this.endTimePos, endTimePos);

		//------------------------------------------------------------------------------------------
		this.changeTimePos(startTimePos, endTimePos);
		
		this.generator.setTimePos(startTimePos, endTimePos);
		
		//------------------------------------------------------------------------------------------
		this.generatorChanged(changedStartTimePos, changedEndTimePos);
		
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
	 * @param inputData
	 * 			to set {@link #inputTimelines} with key.
	 * @param inputTimeline
	 * 			to set to {@link #inputTimelines} as value with given key.
	 * @return
	 * 			the old Timeline.
	 */
	public Timeline updateInput(InputData inputData, Timeline inputTimeline)
	{
		//==========================================================================================
		Timeline oldInputTimeline;
		
		if (inputTimeline != null)
		{
			oldInputTimeline = this.inputTimelines.put(inputData, inputTimeline);
			
			//------------------------------------------------------------------------------------------
			float changedStartTimePos;
			float changedEndTimePos;
			
			if (oldInputTimeline != null)
			{
				changedStartTimePos = Math.min(oldInputTimeline.getStartTimePos(), inputTimeline.getStartTimePos());
				changedEndTimePos = Math.max(oldInputTimeline.getEndTimePos(), inputTimeline.getEndTimePos());
			}
			else
			{
				changedStartTimePos = inputTimeline.getStartTimePos();
				changedEndTimePos = inputTimeline.getEndTimePos();
			}
			
			this.generatorChanged(changedStartTimePos, changedEndTimePos);
		}
		else
		{
			oldInputTimeline = null;
		}
		
		//==========================================================================================
		return oldInputTimeline;
	}

	/**
	 * @return
	 * 			the {@link Generator#getStartTimePos()} of {@link #generator}.
	 */
	private float getStartTimePos()
	{
		return this.generator.getStartTimePos();
	}

	/**
	 * @return
	 * 			the {@link Generator#getEndTimePos()} of {@link #generator}.
	 */
	private float getEndTimePos()
	{
		return this.generator.getEndTimePos();
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
		//==========================================================================================
		Timeline oldOutputTimeline = this.outputTimelines.put(inputData, outputTimeline);
		
		//------------------------------------------------------------------------------------------
		float changedStartTimePos;
		float changedEndTimePos;
		
		if (oldOutputTimeline != null)
		{
			changedStartTimePos = Math.min(oldOutputTimeline.getStartTimePos(), outputTimeline.getStartTimePos());
			changedEndTimePos = Math.max(oldOutputTimeline.getEndTimePos(), outputTimeline.getEndTimePos());
		}
		else
		{
			changedStartTimePos = outputTimeline.getStartTimePos();
			changedEndTimePos = outputTimeline.getEndTimePos();
		}
		
		this.generatorChanged(changedStartTimePos, changedEndTimePos);

		//==========================================================================================
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
	public SoundSample generateFrameSample(long framePosition, 
	                                       ModulGenerator parentModulGenerator,
	                                       ModulArguments modulArguments)
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
				                                                    this,
				                                                    modulArguments);
				
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
	 * @param sampleFramePos
	 * 			is the sampleFrame position.
	 * @param soundSample
	 * 			is sample.
	 */
	private void setBufSoundSample(long sampleFramePos, SoundSample soundSample)
	{
		//==========================================================================================
		int bufFramePos = this.makeBufferFramePos(sampleFramePos);
		
		if (bufFramePos >= 0)
		{
			this.bufSoundSamples[bufFramePos] = soundSample;
		}

		//==========================================================================================
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

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBufferInterface#calcFrameSample(long, float, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	@Override
	public SoundSample calcFrameSample(long framePosition, 
	                                   float frameTime,
	                                   ModulGenerator parentModulGenerator,
	                                   ModulArguments modulArguments)
	{
		//==========================================================================================
		SoundSample bufInputSoundSample;
		
		if (this.generator.checkIsInTime(frameTime) == true)
		{
			bufInputSoundSample = this.getBufSoundSample(framePosition);
			
			if (bufInputSoundSample == null)
			{
				//SoundSample bufInputSoundSample = this.generator.generateFrameSample(framePosition, parentModulGenerator, generatorBuffer);
				bufInputSoundSample = new SoundSample();
				
				this.generator.calculateSoundSample(framePosition, 
				                                    frameTime, 
				                                    bufInputSoundSample, 
				                                    parentModulGenerator, 
				                                    this,
				                                    modulArguments);
				
				this.setBufSoundSample(framePosition,
				                       bufInputSoundSample);
			}
		}
		else
		{
			bufInputSoundSample = null;
		}
		//==========================================================================================
		return bufInputSoundSample;
	}

	/**
	 * Generator changed.
	 * 
	 * @param changedStartTimePos
	 * 			is the start time pos the data in generator changed.
	 * @param changedEndTimePos
	 * 			is the end time pos the data in generator changed.
	 */
	protected void generatorChanged(float changedStartTimePos, float changedEndTimePos)
	{
		//==========================================================================================
//		float generatorStartTimePos = this.generator.getStartTimePos();
//		float generatorEndTimePos = this.generator.getEndTimePos();
//		
//		// Generator length or position changed?
//		if ((this.startTimePos != generatorStartTimePos) || (this.endTimePos != generatorEndTimePos))
//		{
//			this.changeTimePos(generatorStartTimePos, generatorEndTimePos);
//		}
//		else
		{
			// Only a part of generator changed:
			
			//--------------------------------------------------------------------------------------
			float startTimePos;
			
			if (changedStartTimePos < this.startTimePos)
			{
				startTimePos = this.startTimePos;
			}
			else
			{
				startTimePos = changedStartTimePos;
			}

			float endTimePos;

			if (changedEndTimePos > this.endTimePos)
			{
				endTimePos = this.endTimePos;
			}
			else
			{
				endTimePos = changedEndTimePos;
			}
			
//			float timeLength = startTimePos - endTimePos;
			
			//--------------------------------------------------------------------------------------
			int changedStartBufPos = (int)(this.generator.getSoundFrameRate() * (startTimePos - this.startTimePos));
	
			int changedEndBufSize = (int)(this.generator.getSoundFrameRate() * (endTimePos - this.startTimePos));
			
			//--------------------------------------------------------------------------------------
			this.clearBuffer(changedStartBufPos, changedEndBufSize);
		}
		//==========================================================================================
		// Notify also Output-Timelines:
		
		for (Timeline outputTimeline : this.outputTimelines.values())
		{
			outputTimeline.generatorChanged(changedStartTimePos, changedEndTimePos);
		}
		
		//==========================================================================================
	}

	/**
	 * Generator changed.
	 * 
	 * #see {@link #generatorChanged(float, float)} for generator time.
	 */
	public void generatorChanged()
	{
		this.generatorChanged(this.startTimePos, this.endTimePos);
	}

	/**
	 * @param changedStartBufPos
	 * @param changedEndBufSize
	 */
	private void clearBuffer(int changedStartBufPos, int changedEndBufSize)
	{
		//==========================================================================================
		try
		{
			for (int bufPos = changedStartBufPos; bufPos < changedEndBufSize; bufPos++)
			{
				this.bufSoundSamples[bufPos] = null;
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("gen:" + this.generator.getName() + ", bufSize:" + this.bufSoundSamples.length + ", changedStartBufPos:" + changedStartBufPos + ", changedEndBufSize:" + changedEndBufSize + ", startTimePos:" + this.startTimePos + ", endTimePos:" + this.endTimePos, ex);
		}
		//==========================================================================================
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBufferInterface#getInputGeneratorBuffer(de.schmiereck.noiseComp.generator.InputData)
	 */
	@Override
	public GeneratorBufferInterface getInputGeneratorBuffer(InputData inputData)
	{
		//==========================================================================================
		GeneratorBufferInterface inputTimeline = this.inputTimelines.get(inputData);
		
		if (inputTimeline == null)
		{
			Generator inputGenerator = inputData.getInputGenerator();
			
			if (inputGenerator instanceof ModulGenerator)
			{
				inputTimeline = this.subGeneratorTimelines.get(inputGenerator);
			}
		}
		
		//==========================================================================================
		return inputTimeline;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.ModulGeneratorRemoveListenerInterface#notifyModulGeneratorRemoved(de.schmiereck.noiseComp.generator.Generator)
	 */
	@Override
	public void notifyModulGeneratorRemoved(Generator removedGenerator)
	{
		//==========================================================================================
		for (InputData inputData : this.inputTimelines.keySet())
		{
			if (inputData.getInputGenerator() == removedGenerator)
			{
				this.inputTimelines.remove(removedGenerator);
			}
		}
		//------------------------------------------------------------------------------------------
		for (InputData inputData : this.outputTimelines.keySet())
		{
			if (inputData.getInputGenerator() == removedGenerator)
			{
				this.outputTimelines.remove(removedGenerator);
			}
		}
		//==========================================================================================
	}

	/**
	 * @param removedTimeline
	 */
	public void removeInputTimeline(Timeline removedTimeline)
	{
		//==========================================================================================
		float changedStartTimePos = removedTimeline.startTimePos;
		float changedEndTimePos = removedTimeline.endTimePos;
		
		//------------------------------------------------------------------------------------------
		{
			Set<Entry<InputData, Timeline>> entrySet = this.inputTimelines.entrySet();
			
			Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();
			
			while (entrySetIterator.hasNext())
			{
				Map.Entry<InputData, Timeline> entry = entrySetIterator.next();
				
				if (entry.getValue() == removedTimeline)
				{
					this.generatorChanged(changedStartTimePos, changedEndTimePos);
					
					entrySetIterator.remove();
				}
			}
		}
		//------------------------------------------------------------------------------------------
		{
			Set<Entry<InputData, Timeline>> entrySet = this.outputTimelines.entrySet();
			
			Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();
			
			while (entrySetIterator.hasNext())
			{
				Map.Entry<InputData, Timeline> entry = entrySetIterator.next();
				
				if (entry.getValue() == removedTimeline)
				{
					entrySetIterator.remove();
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @param inputData
	 * 			is the inputData to remove from {@link #outputTimelines}.
	 */
	public void removeOutputTimeline(InputData inputData)
	{
		//==========================================================================================
		Timeline removedTimeline = this.outputTimelines.remove(inputData);
		
		//------------------------------------------------------------------------------------------
		float changedStartTimePos = removedTimeline.getStartTimePos();
		float changedEndTimePos = removedTimeline.getEndTimePos();
		
		this.generatorChanged(changedStartTimePos, changedEndTimePos);

		//==========================================================================================
	}

	/**
	 * @param inputData
	 * 			is the Input-Data.
	 * @return
	 * 			is the Input-Timeline for given Input-Data.
	 */
	public Timeline searchInputTimeline(InputData inputData)
	{
		return this.inputTimelines.get(inputData);
	}

	/**
	 * @param generator
	 * 			is the Sub-Generator.
	 * @return 
	 * 			returns Timeline of the {@link #subGeneratorTimelines} for given Sub-Generator.
	 */
	public Timeline getSubGeneratorTimeline(Generator generator)
	{
		return this.subGeneratorTimelines.get(generator);
	}

	/**
	 * @param subGenerator
	 * 			to set {@link #subGeneratorTimelines}.
	 * @param subTimeline
	 * 			to set {@link #subGeneratorTimelines}.
	 */
	public void addSubGeneratorTimeline(Generator subGenerator, Timeline subTimeline)
	{
		this.subGeneratorTimelines.put(subGenerator, subTimeline);
	}

	/**
	 * @return 
	 * 			returns the {@link #subGeneratorTimelines} values.
	 */
	public Collection<Timeline> getSubGeneratorTimelines()
	{
		return this.subGeneratorTimelines.values();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		//==========================================================================================
		String generatorStr;
		
		if (this.generator != null)
		{
			generatorStr = "generator.name:" + this.generator.getName();
		}
		else
		{
			generatorStr = "generator:null";
		}
		//==========================================================================================
		return super.toString() + "{" + generatorStr + "}";
	}

	/**
	 * @param inputData
	 * 			is the InputData.
	 */
	public void removeInput(InputData inputData)
	{
		//==========================================================================================
		Generator inputGenerator = inputData.getInputGenerator();
		Generator ownerGenerator = inputData.getOwnerGenerator();

		Den entsprechenden output aus den soutput timeline löschen?
		
		this.inputTimelines.remove(inputData);
		
		//------------------------------------------------------------------------------------------
		//inputGenerator.removeOutput();
		this.generator.removeInput(inputData);
		
		//------------------------------------------------------------------------------------------
		this.generatorChanged();
		
		//==========================================================================================
	}
	
}
