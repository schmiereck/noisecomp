/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
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
	 * Modul Timeline.<br/>
	 * <code>null</code> if there is no 'parent' module.
	 */
	final private Timeline modulTimeline;
	
	/**
	 * Generator.
	 */
	private Generator generator = null;
	
	/**
	 * Start time Position in seconds.
	 */
	private float timelineStartTimePos = 0.0F;
	
	/**
	 * End time Position in seconds.
	 */
	private float timelineEndTimePos = 0.0F;
	
	/**
	 * <code>true</code> (default) if {@link #bufSoundSamples} is not compleately calculated. 
	 */
	private boolean bufferIsDirty = true;
	
	/**
	 * Maximum value of timeline {@link #bufSoundSamples}.<br/>
	 * {@link Float#NaN} (default) if no value is calculated.<br/>
	 * Only correct, if {@link #bufferIsDirty} if <code>false</code>.
	 */
	private float valueMax = Float.NaN;
	
	/**
	 * Minimum value of timeline {@link #bufSoundSamples}.<br/>
	 * {@link Float#NaN} (default) if no value is calculated.<br/>
	 * Only correct, if {@link #bufferIsDirty} if <code>false</code>.
	 */
	private float valueMin = Float.NaN;
	
	/**
	 * Buffered Sound Samples between start and end output of {@link #generator}.
	 * @see #bufferIsDirty is <code>true</code> if not all samples are completely calculated.
	 * @see #valueMax
	 * @see #valueMin
	 */
	private SoundSample[] bufSoundSamples = new SoundSample[0];
	
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
	 * Sub-Modul Generator Timelines.
	 */
	private Map<Generator, Timeline> subGeneratorTimelines = new HashMap<Generator, Timeline>();
	
	/**
	 * Timeline Changed Listerners.
	 */
	private List<TimelineChangedListernerInterface> timelineChangedListerners = new Vector<TimelineChangedListernerInterface>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param modulTimeline
	 */
	public Timeline(Timeline modulTimeline)
	{
		this.modulTimeline = modulTimeline;
	}

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
		this.timelineStartTimePos = startTimePos;
		this.timelineEndTimePos = endTimePos;
		
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
		float changedStartTimePos 	= Math.min(this.timelineStartTimePos, startTimePos);
		float changedEndTimePos		= Math.max(this.timelineEndTimePos, endTimePos);

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
				changedStartTimePos = Math.min(oldInputTimeline.getGeneratorStartTimePos(), inputTimeline.getGeneratorStartTimePos());
				changedEndTimePos = Math.max(oldInputTimeline.getGeneratorEndTimePos(), inputTimeline.getGeneratorEndTimePos());
			}
			else
			{
				changedStartTimePos = inputTimeline.getGeneratorStartTimePos();
				changedEndTimePos = inputTimeline.getGeneratorEndTimePos();
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
	public float getGeneratorStartTimePos()
	{
		return this.generator.getStartTimePos();
	}

	/**
	 * @return
	 * 			the {@link Generator#getEndTimePos()} of {@link #generator}.
	 */
	public float getGeneratorEndTimePos()
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
			changedStartTimePos = Math.min(oldOutputTimeline.getGeneratorStartTimePos(), outputTimeline.getGeneratorStartTimePos());
			changedEndTimePos = Math.max(oldOutputTimeline.getGeneratorEndTimePos(), outputTimeline.getGeneratorEndTimePos());
		}
		else
		{
			changedStartTimePos = outputTimeline.getGeneratorStartTimePos();
			changedEndTimePos = outputTimeline.getGeneratorEndTimePos();
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
			//--------------------------------------------------------------------------------------
			// Only a part of generator changed:
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			float startTimePos;
			
			if (changedStartTimePos < this.timelineStartTimePos)
			{
				startTimePos = this.timelineStartTimePos;
			}
			else
			{
				startTimePos = changedStartTimePos;
			}

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			float endTimePos;

			if (changedEndTimePos > this.timelineEndTimePos)
			{
				endTimePos = this.timelineEndTimePos;
			}
			else
			{
				endTimePos = changedEndTimePos;
			}
			
//			float timeLength = startTimePos - endTimePos;
			
			System.out.println("Timeline generator \"" + this.generator.getName() + "\" changed(" + startTimePos + ", " + endTimePos + ")");
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			int changedStartBufPos = (int)(this.generator.getSoundFrameRate() * (startTimePos - this.timelineStartTimePos));
	
			int changedEndBufSize = (int)(this.generator.getSoundFrameRate() * (endTimePos - this.timelineStartTimePos));
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.clearBuffer(changedStartBufPos, changedEndBufSize);
		}
		//------------------------------------------------------------------------------------------
//		// Notify Modul:
//		
//		if (this.generator != null)
//		{
//			if (this.generator instanceof ModulGenerator)
//			{
//				// No chance to notify module timeline
//			}
//		}
		
		// Notify Output-Timelines:
		
		for (Timeline outputTimeline : this.outputTimelines.values())
		{
			outputTimeline.generatorChanged(changedStartTimePos, changedEndTimePos);
		}
		
		//------------------------------------------------------------------------------------------
		// Notify Changed-Listeners:
		
		this.notifyTimelineChangedListerners(changedStartTimePos, changedEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * Generator changed.
	 * 
	 * #see {@link #generatorChanged(float, float)} for generator time.
	 */
	public void generatorChanged()
	{
		this.generatorChanged(this.timelineStartTimePos, this.timelineEndTimePos);
	}

	/**
	 * @param changedStartBufPos
	 * 			is the start position of changed buffer area.
	 * @param changedEndBufSize
	 * 			is the start position of changed buffer area.
	 */
	private void clearBuffer(int changedStartBufPos, int changedEndBufSize)
	{
		//==========================================================================================
		this.bufferIsDirty = true;
		
		try
		{
			for (int bufPos = changedStartBufPos; bufPos < changedEndBufSize; bufPos++)
			{
				this.bufSoundSamples[bufPos] = null;
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("gen:" + this.generator.getName() + ", bufSize:" + this.bufSoundSamples.length + ", changedStartBufPos:" + changedStartBufPos + ", changedEndBufSize:" + changedEndBufSize + ", startTimePos:" + this.timelineStartTimePos + ", endTimePos:" + this.timelineEndTimePos, ex);
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
	 * @param inputData
	 * 			is the InputData.
	 */
	public void removeInput(InputData inputData)
	{
		//==========================================================================================
//		Generator inputGenerator = inputData.getInputGenerator();
//		Generator ownerGenerator = inputData.getOwnerGenerator();

		//------------------------------------------------------------------------------------------
		// Remove from Input-Timelines:
		
		Timeline removedTimeline = this.inputTimelines.remove(inputData);
		
		float changedStartTimePos;
		float changedEndTimePos;
		
		if (removedTimeline != null)
		{
			changedStartTimePos = removedTimeline.timelineStartTimePos;
			changedEndTimePos = removedTimeline.timelineEndTimePos;
		}
		else
		{
			changedStartTimePos = this.timelineStartTimePos;
			changedEndTimePos = this.timelineEndTimePos;
		}
		
//		//------------------------------------------------------------------------------------------
//		// Remove also from Output-Timelines:
//		
//		for (Timeline outputTimeline : this.outputTimelines.values())
//		{
//			outputTimeline.removeInput(inputData);
//		}
		
		//------------------------------------------------------------------------------------------
		//inputGenerator.removeOutput();
		this.generator.removeInput(inputData);
		
		//------------------------------------------------------------------------------------------
		this.generatorChanged(changedStartTimePos, changedEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * @param removedTimeline
	 * 			is the removed Timeline.
	 */
	public void removeInputTimeline(Timeline removedTimeline)
	{
		//==========================================================================================
		float changedStartTimePos = removedTimeline.timelineStartTimePos;
		float changedEndTimePos = removedTimeline.timelineEndTimePos;
		
		//------------------------------------------------------------------------------------------
		{
			Set<Entry<InputData, Timeline>> entrySet = this.inputTimelines.entrySet();
			
			Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();
			
			while (entrySetIterator.hasNext())
			{
				Map.Entry<InputData, Timeline> entry = entrySetIterator.next();
				
				if (entry.getValue() == removedTimeline)
				{
					InputData inputData = entry.getKey();
					
					this.generator.removeInput(inputData);
					
					entrySetIterator.remove();
					
					this.generatorChanged(changedStartTimePos, changedEndTimePos);
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
		float changedStartTimePos = removedTimeline.getGeneratorStartTimePos();
		float changedEndTimePos = removedTimeline.getGeneratorEndTimePos();
		
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

	/**
	 * @return 
	 * 			returns the {@link #valueMax}.
	 */
	public float getValueMax()
	{
		return this.valueMax;
	}

	/**
	 * @param valueMax 
	 * 			to set {@link #valueMax}.
	 */
	public void setValueMax(float valueMax)
	{
		this.valueMax = valueMax;
	}

	/**
	 * @return 
	 * 			returns the {@link #valueMin}.
	 */
	public float getValueMin()
	{
		return this.valueMin;
	}

	/**
	 * @param valueMin 
	 * 			to set {@link #valueMin}.
	 */
	public void setValueMin(float valueMin)
	{
		this.valueMin = valueMin;
	}

	/**
	 * @return 
	 * 			returns the {@link #bufferIsDirty}.
	 */
	public boolean getBufferIsDirty()
	{
		return this.bufferIsDirty;
	}

	/**
	 * @param bufferIsDirty 
	 * 			to set {@link #bufferIsDirty}.
	 */
	public void setBufferIsDirty(boolean bufferIsDirty)
	{
		this.bufferIsDirty = bufferIsDirty;
	}

	/**
	 * Calculate min and max values of timeline buffer.
	 */
	public void calcMinMaxValues()
	{
		//==========================================================================================
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		
		//------------------------------------------------------------------------------------------
		for (int bufPos = 0; bufPos < this.bufSoundSamples.length; bufPos++)
		{
			SoundSample soundSample = this.bufSoundSamples[bufPos];
			
			if (soundSample != null)
			{
				float value = soundSample.getMonoValue();
				
				if (value > max)
				{
					max = value;
				}
				
				if (value < min)
				{
					min = value;
				}
			}
		}

		//------------------------------------------------------------------------------------------
		if (max != Float.MIN_VALUE)
		{
			this.valueMax = max;
		}
		else
		{
			this.valueMax = Float.NaN;
		}
		
		if (min != Float.MAX_VALUE)
		{
			this.valueMin = min;
		}
		else
		{
			this.valueMin = Float.NaN;
		}
		//==========================================================================================
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
	 * @param timelineChangedListerner 
	 * 			to add to {@link #timelineChangedListerners}.
	 */
	public void addTimelineChangedListerner(TimelineChangedListernerInterface timelineChangedListerner)
	{
		this.timelineChangedListerners.add(timelineChangedListerner);
	}

	/**
	 * @param timelineChangedListerner 
	 * 			to remove form {@link #timelineChangedListerners}.
	 */
	public void removeTimelineChangedListerner(TimelineChangedListernerInterface timelineChangedListerner)
	{
		this.timelineChangedListerners.remove(timelineChangedListerner);
	}

	/**
	 * Notify the {@link #timelineChangedListerners}.
	 * 
	 * @param changedStartTimePos
	 * 			is the start time pos the data in generator changed.
	 * @param changedEndTimePos
	 * 			is the end time pos the data in generator changed.
	 */
	public void notifyTimelineChangedListerners(float changedStartTimePos, float changedEndTimePos)
	{
		for (TimelineChangedListernerInterface timelineChangedListerner : this.timelineChangedListerners)
		{
			timelineChangedListerner.notifyTimelineChanged(this,
			                                               changedStartTimePos, changedEndTimePos);
		}
	}

	/**
	 * Notify the {@link #timelineChangedListerners}.
	 */
	public void notifyTimelineChangedListerners()
	{
		this.notifyTimelineChangedListerners(this.timelineStartTimePos,
		                                     this.timelineEndTimePos);
	}

	/**
	 * @return 
	 * 			returns the {@link #modulTimeline}.
	 */
	public Timeline getModulTimeline()
	{
		return this.modulTimeline;
	}
	
}
