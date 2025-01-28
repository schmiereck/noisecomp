/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.*;
import java.util.Map.Entry;

import de.schmiereck.dataTools.VectorHash;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorBufferInterface;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModuleArguments;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.generator.ModuleGeneratorRemoveListenerInterface;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;

/**
 * <p>
 * 	Timeline.<br/>
 * 	Visible, editable and playable Instances of a Generator.
 * </p>
 * <p>
 * 	Implements the Logic of a Generator is managing Samples in an Buffer at Framepositions:
 *  So he is calulating Samples only one time.
 * <p>
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
		   ModuleGeneratorRemoveListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Module Timeline.<br/>
	 * <code>null</code> if there is no 'parent' module.
	 */
	final private Timeline moduleTimeline;
	
	/**
	 * Generator.
	 */
	private Generator generator = null;

	/**
	 * <code>true</code> (default) if {@link #bufSoundSamples} is not completely calculated.
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
	private final VectorHash<InputData, Timeline> inputTimelines = new VectorHash<>();
	
	/**
	 * Output Timelines.
	 * 
	 * Key is the {@link InputData} of the target output timeline generator.
	 */
	private final Map<InputData, Timeline> outputTimelines = new HashMap<>();
	
	/**
	 * Sub-Module Generator Timelines.
	 */
	private final Map<Generator, Timeline> subGeneratorTimelines = new HashMap<>();
	
	/**
	 * Timeline Changed Listerners.
	 */
	private final List<TimelineChangedListernerInterface> timelineChangedListerners = new Vector<>();

	/**
	 * Generator Data.
	 */
	private Object generatorData = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public Timeline(Timeline moduleimeline)
	{
		//==========================================================================================
		this.moduleTimeline = moduleimeline;
		
		//==========================================================================================
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
	public synchronized void setGenerator(final Generator generator) {
		//==========================================================================================
		this.generator = generator;
		
		this.generatorData = this.generator.createGeneratorData();
			
		//------------------------------------------------------------------------------------------
		final float startTimePos = this.generator.getStartTimePos();
		final float endTimePos = this.generator.getEndTimePos();

		this.recalcSoundBuffer();

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
		this.generator.addModuleGeneratorRemoveListener(this);
		
		//==========================================================================================
	}

	/**
	 * Reinitialize Sound-Buffer.
	 */
	private synchronized 
	void recalcSoundBuffer() {
		//==========================================================================================
		final float startTimePos = this.generator.getStartTimePos();
		final float endTimePos = this.generator.getEndTimePos();

		float timeLength = endTimePos - startTimePos;
		
		if (timeLength < 0)
		{
			timeLength = 0.F;
		}

		final int bufSize = (int)(this.generator.getSoundFrameRate() * timeLength);
		
		this.bufSoundSamples = new SoundSample[bufSize];
		
		//==========================================================================================
	}

	/**
	 * Change start end end time of timeline and generator.
	 * Reinitialize buffer.
	 * 
	 * @see #recalcSoundBuffer()
	 * 
	 * @param newStartTimePos
	 * 			is the newStartTimePos.
	 * @param newEndTimePos
	 * 			is the newEndTimePos.
	 */
	public void setTimePos(final SoundSourceData soundSourceData, final float newStartTimePos, final float newEndTimePos) {
		//==========================================================================================
		final float oldStartTimePos = this.generator.getStartTimePos();
		final float oldEndTimePos = this.generator.getEndTimePos();

		//------------------------------------------------------------------------------------------
		this.generator.setTimePos(soundSourceData, newStartTimePos, newEndTimePos);

		this.recalcSoundBuffer();

		//------------------------------------------------------------------------------------------
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputTimelines}.
	 */
	public VectorHash<InputData, Timeline> getInputTimelines() {
		return this.inputTimelines;
	}

	/**
	 * @param inputData
	 * 			to add to {@link #inputTimelines} as key.
	 * @param inputTimeline
	 * 			to add to {@link #inputTimelines} as value.
	 */
	public void addInputTimeline(final InputData inputData, final Timeline inputTimeline) {
		this.inputTimelines.add(inputData, inputTimeline);
	}

	/**
	 * @param inputData
	 * 			to set {@link #inputTimelines} with key.
	 * @param inputTimeline
	 * 			to set to {@link #inputTimelines} as value with given key.
	 * @return
	 * 			the old Timeline.
	 */
	public Timeline updateInput(final SoundSourceData soundSourceData,
								final InputData inputData, final Timeline inputTimeline) {
		//==========================================================================================
		final Timeline oldInputTimeline = this.inputTimelines.add(inputData, inputTimeline);

		//------------------------------------------------------------------------------------------
		final boolean generatorChanged;
		final float oldStartTimePos;
		final float oldEndTimePos;
		final float newStartTimePos;
		final float newEndTimePos;

		if (oldInputTimeline != null) {
			oldStartTimePos = oldInputTimeline.getGeneratorStartTimePos();
			oldEndTimePos = oldInputTimeline.getGeneratorEndTimePos();
			if (inputTimeline != null) {
				newStartTimePos = Math.min(oldInputTimeline.getGeneratorStartTimePos(), inputTimeline.getGeneratorStartTimePos());
				newEndTimePos = Math.max(oldInputTimeline.getGeneratorEndTimePos(), inputTimeline.getGeneratorEndTimePos());
				generatorChanged = true;
			} else {
				newStartTimePos = oldInputTimeline.getGeneratorStartTimePos();
				newEndTimePos = oldInputTimeline.getGeneratorEndTimePos();
				generatorChanged = true;
			}
		} else {
			if (inputTimeline != null) {
				oldStartTimePos = inputTimeline.getGeneratorStartTimePos();
				oldEndTimePos = inputTimeline.getGeneratorEndTimePos();
				newStartTimePos = inputTimeline.getGeneratorStartTimePos();
				newEndTimePos = inputTimeline.getGeneratorEndTimePos();
				generatorChanged = true;
			} else {
				oldStartTimePos = 0.0F;
				oldEndTimePos = 0.0F;
				newStartTimePos = 0.0F;
				newEndTimePos = 0.0F;
				generatorChanged = false;
			}
		}

		if (generatorChanged) {
			this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
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
	public void addOutputTimeline(final SoundSourceData soundSourceData,
								  final InputData inputData, final Timeline outputTimeline) {
		//==========================================================================================
		final Timeline oldOutputTimeline = this.outputTimelines.put(inputData, outputTimeline);
		
		//------------------------------------------------------------------------------------------
		final float oldStartTimePos;
		final float oldEndTimePos;
		final float newStartTimePos;
		final float newEndTimePos;
		
		if (Objects.nonNull(oldOutputTimeline)) {
			oldStartTimePos = oldOutputTimeline.getGeneratorStartTimePos();
			oldEndTimePos = oldOutputTimeline.getGeneratorEndTimePos();
			newStartTimePos = Math.min(oldOutputTimeline.getGeneratorStartTimePos(), outputTimeline.getGeneratorStartTimePos());
			newEndTimePos = Math.max(oldOutputTimeline.getGeneratorEndTimePos(), outputTimeline.getGeneratorEndTimePos());
		} else {
			oldStartTimePos = outputTimeline.getGeneratorStartTimePos();
			oldEndTimePos = outputTimeline.getGeneratorEndTimePos();
			newStartTimePos = outputTimeline.getGeneratorStartTimePos();
			newEndTimePos = outputTimeline.getGeneratorEndTimePos();
		}
		
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);

		//==========================================================================================
	}
	
	/**
	 * Wird aufgerufen, um den Ausgangswert eines Generators für die angegebene 
	 * Frame-Position zu ermitteln.
	 * 
	 * Liefert <code>null</code>, wenn der Generator für den Zeitpunkt keinen Wert 
	 * generieren kann (Frame-Position nicht zwischen Start und Ende).
	 * 
	 * @see Generator#generateFrameSample(long, ModuleGenerator, GeneratorBufferInterface, ModuleArguments)
	 * 
	 * @param framePosition
	 * 			ist the position of the sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public synchronized 
	SoundSample generateFrameSample(final long framePosition,
									final ModuleGenerator parentModuleGenerator,
									final ModuleArguments moduleArguments){
		//==========================================================================================
		final SoundSample retSoundSample;

		final int bufFramePos = this.makeBufferFramePos(framePosition);
		
		if (bufFramePos >= 0) {
			final SoundSample soundSample = this.bufSoundSamples[bufFramePos];
			
			if (Objects.isNull(soundSample)) {
				retSoundSample = this.generator.generateFrameSample(framePosition,
				                                                    parentModuleGenerator, 
				                                                    this,
				                                                    moduleArguments);
				
				this.bufSoundSamples[bufFramePos] = retSoundSample;
			} else {
				retSoundSample = soundSample;
			}
		} else {
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
	public synchronized 
	SoundSample getBufSoundSample(long sampleFramePos)
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
	private synchronized 
	void setBufSoundSample(long sampleFramePos, SoundSample soundSample)
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
	 * 			is the absulte sample Frame in module.
	 * @return
	 * 			is the buffer Frame or <code>-1</code> if not in buffer range.
	 */
	private synchronized 
	int makeBufferFramePos(long sampleFrame)
	{
		//==========================================================================================
		int bufFramePos;
		
		if (this.bufSoundSamples != null)
		{
			float startTimePos = this.generator.getStartTimePos();
			
			long startFrame = (long)(this.generator.getSoundFrameRate() * startTimePos);
			
			bufFramePos = (int)(sampleFrame - startFrame);
			
			// Frame is not in buffer range?
			if (((bufFramePos >= 0) && (bufFramePos < this.bufSoundSamples.length)) == false)
			{
				bufFramePos = -1;
			}
		}
		else
		{
			bufFramePos = -1;
		}
		//==========================================================================================
		return bufFramePos;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBufferInterface#calcFrameSample(long, float, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
	 */
	@Override
	public SoundSample calcFrameSample(final long framePosition,
									   final float frameTime,
									   final ModuleGenerator parentModuleGenerator,
									   final ModuleArguments moduleArguments)
	{
		//==========================================================================================
		SoundSample bufInputSoundSample;
		
		if (this.generator.checkIsInTime(frameTime) == true) {
			bufInputSoundSample = this.getBufSoundSample(framePosition);
			
			if (Objects.isNull(bufInputSoundSample)) {
				//SoundSample bufInputSoundSample = this.generator.generateFrameSample(framePosition, parentModuleGenerator, generatorBuffer);
				//bufInputSoundSample = new SoundSample();
				bufInputSoundSample = this.generator.createSoundSample();

				this.generator.calculateSoundSample(framePosition, 
				                                    frameTime, 
				                                    bufInputSoundSample, 
				                                    parentModuleGenerator, 
				                                    this,
				                                    moduleArguments);
				
				this.setBufSoundSample(framePosition,
				                       bufInputSoundSample);
			}
		} else {
			bufInputSoundSample = null;
		}
		//==========================================================================================
		return bufInputSoundSample;
	}

	@Override
	public SoundSample retrieveFrameSample(final long framePosition,
									       final float frameTime,
									       final ModuleGenerator parentModuleGenerator,
									       final ModuleArguments moduleArguments)
	{
		//==========================================================================================
		SoundSample bufInputSoundSample;

		if (this.generator.checkIsInTime(frameTime) == true) {
			bufInputSoundSample = this.getBufSoundSample(framePosition);
		} else {
			bufInputSoundSample = null;
		}
		//==========================================================================================
		return bufInputSoundSample;
	}

	/**
	 * Generator changed.
	 * 
	 * @param newStartTimePos
	 * 			is the start time pos the data in generator changed.
	 * @param newEndTimePos
	 * 			is the end time pos the data in generator changed.
	 */
	protected void generatorChanged(final SoundSourceData soundSourceData,
									final float oldStartTimePos, final float oldEndTimePos,
									final float newStartTimePos, final float newEndTimePos) {
		//==========================================================================================
		final float changedStartTimePos 	= Math.min(oldStartTimePos, newStartTimePos);
		final float changedEndTimePos		= Math.max(oldEndTimePos, newEndTimePos);

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
			final float startTimePos;
			
			if (changedStartTimePos < this.getGeneratorStartTimePos()) {
				startTimePos = this.getGeneratorStartTimePos();
			} else {
				startTimePos = changedStartTimePos;
			}

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final float endTimePos;

			if (changedEndTimePos > this.getGeneratorEndTimePos()) {
				endTimePos = this.getGeneratorEndTimePos();
			} else {
				endTimePos = changedEndTimePos;
			}
			
			System.out.println("Timeline generator \"" + this.generator.getName() + "\" changed(" + startTimePos + ", " + endTimePos + ")");
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final int changedStartBufPos = (int)(this.generator.getSoundFrameRate() * (startTimePos - this.getGeneratorStartTimePos()));
			final int changedEndBufSize = (int)(this.generator.getSoundFrameRate() * (endTimePos - this.getGeneratorStartTimePos()));
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			this.clearBuffer(changedStartBufPos, changedEndBufSize);
		}
		//------------------------------------------------------------------------------------------
//		// Notify Module
//		
//		if (this.generator != null)
//		{
//			if (this.generator instanceof ModuleGenerator)
//			{
//				// No chance to notify module timeline
//			}
//		}
		
		// Notify Output-Timelines:
		
		for (final Timeline outputTimeline : this.outputTimelines.values()) {
			outputTimeline.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, changedStartTimePos, changedEndTimePos);
		}
		
		//------------------------------------------------------------------------------------------
		// Notify Changed-Listeners:
		
		this.notifyTimelineChangedListerners(soundSourceData, changedStartTimePos, changedEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * Generator changed.
	 *
	 * #see {@link #generatorChanged(SoundSourceData, float, float, float, float)} for generator time.
	 */
	public void generatorChanged(final SoundSourceData soundSourceData, final float oldStartTimePos, final float oldEndTimePos) {
		final float newStartTimePos = this.generator.getStartTimePos();
		final float newEndTimePos = this.generator.getStartTimePos();
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
	}

	/**
	 * @param changedStartBufPos
	 * 			is the start position of changed buffer area.
	 * @param changedEndBufSize
	 * 			is the start position of changed buffer area.
	 */
	private synchronized 
	void clearBuffer(final int changedStartBufPos, final int changedEndBufSize) {
		//==========================================================================================
		this.bufferIsDirty = true;
		
		try {
			for (int bufPos = changedStartBufPos; bufPos < changedEndBufSize; bufPos++) {
				this.bufSoundSamples[bufPos] = null;
			}
		} catch (final Exception ex) {
			throw new RuntimeException("gen:" + this.generator.getName() + ", bufSize:" + this.bufSoundSamples.length + ", changedStartBufPos:" + changedStartBufPos + ", changedEndBufSize:" + changedEndBufSize + ", startTimePos:" + this.moduleTimeline.getGeneratorStartTimePos() + ", endTimePos:" + this.moduleTimeline.getGeneratorEndTimePos(), ex);
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
		GeneratorBufferInterface inputTimeline;
		
		if (inputData != null)
		{
			inputTimeline = this.inputTimelines.get(inputData);

			if (inputTimeline == null)
			{
				Generator inputGenerator = inputData.getInputGenerator();
				
				if (inputGenerator instanceof ModuleGenerator)
				{
					inputTimeline = this.subGeneratorTimelines.get(inputGenerator);
				}
			}
		}
		else
		{
			inputTimeline = null;
		}
		//==========================================================================================
		return inputTimeline;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.ModuleGeneratorRemoveListenerInterface#notifyModuleGeneratorRemoved(de.schmiereck.noiseComp.generator.Generator)
	 */
	@Override
	public void notifyModuleGeneratorRemoved(final SoundSourceData soundSourceData, final Generator removedGenerator)
	{
		//==========================================================================================
		for (final InputData inputData : this.inputTimelines.keySet()) {
			if (inputData.getInputGenerator() == removedGenerator) {
				//this.inputTimelines.remove(removedGenerator);
				
				this.inputTimelines.remove(inputData);
			}
		}
		//------------------------------------------------------------------------------------------
		for (final InputData inputData : this.outputTimelines.keySet()) {
			if (inputData.getInputGenerator() == removedGenerator) {
				this.outputTimelines.remove(removedGenerator);
			}
		}
		//==========================================================================================
	}

	/**
	 * @param inputData
	 * 			is the InputData.
	 */
	public void removeInput(final SoundSourceData soundSourceData, final InputData inputData) {
		//==========================================================================================
//		Generator inputGenerator = inputData.getInputGenerator();
//		Generator ownerGenerator = inputData.getOwnerGenerator();

		//------------------------------------------------------------------------------------------
		// Remove from Input-Timelines:

		final Timeline removedTimeline = this.inputTimelines.remove(inputData);

		final float oldStartTimePos;
		final float oldEndTimePos;
		final float newStartTimePos;
		final float newEndTimePos;
		
		if (Objects.nonNull(removedTimeline)) {
			oldStartTimePos = removedTimeline.generator.getStartTimePos();
			oldEndTimePos = removedTimeline.generator.getEndTimePos();
			newStartTimePos = removedTimeline.generator.getStartTimePos();
			newEndTimePos = removedTimeline.generator.getEndTimePos();
		} else {
			oldStartTimePos = this.generator.getStartTimePos();
			oldEndTimePos = this.generator.getEndTimePos();
			newStartTimePos = this.generator.getStartTimePos();
			newEndTimePos = this.generator.getEndTimePos();
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
		this.generator.removeInput(soundSourceData, inputData);
		
		//------------------------------------------------------------------------------------------
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * @param inputData
	 * 			is the InputData.
	 */
	public void updateInput(final SoundSourceData soundSourceData, final InputData inputData) {
		//==========================================================================================
		// Update from Input-Timelines:

		final Timeline updatedTimeline = this.inputTimelines.get(inputData);

		final float oldStartTimePos;
		final float oldEndTimePos;
		final float newStartTimePos;
		final float newEndTimePos;
		
		if (updatedTimeline != null) {
			oldStartTimePos = updatedTimeline.generator.getStartTimePos();
			oldEndTimePos = updatedTimeline.generator.getEndTimePos();
			newStartTimePos = updatedTimeline.generator.getStartTimePos();
			newEndTimePos = updatedTimeline.generator.getEndTimePos();
		} else {
			oldStartTimePos = this.generator.getStartTimePos();
			oldEndTimePos = this.generator.getEndTimePos();
			newStartTimePos = this.generator.getStartTimePos();
			newEndTimePos = this.generator.getEndTimePos();
		}
		
		//------------------------------------------------------------------------------------------
		this.generator.updateInput(soundSourceData, inputData);
		
		//------------------------------------------------------------------------------------------
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * @param removedTimeline
	 * 			is the removed Timeline.
	 */
	public void removeInputTimeline(final SoundSourceData soundSourceData, final Timeline removedTimeline) {
		//==========================================================================================
		{
			final float oldStartTimePos = removedTimeline.getGeneratorStartTimePos();
			final float oldEndTimePos = removedTimeline.getGeneratorEndTimePos();
			final float newStartTimePos = removedTimeline.getGeneratorStartTimePos();
			final float newEndTimePos = removedTimeline.getGeneratorEndTimePos();

			final Set<Entry<InputData, Timeline>> entrySet = this.inputTimelines.entrySet();

			final Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();
			
			while (entrySetIterator.hasNext()) {
				final Map.Entry<InputData, Timeline> entry = entrySetIterator.next();
				
				if (entry.getValue() == removedTimeline) {
					InputData inputData = entry.getKey();
					
					this.generator.removeInput(soundSourceData, inputData);
					
					entrySetIterator.remove();
					
					this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		{
			final Set<Entry<InputData, Timeline>> entrySet = this.outputTimelines.entrySet();

			final Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();

			while (entrySetIterator.hasNext()) {
				final Map.Entry<InputData, Timeline> entry = entrySetIterator.next();

				if (entry.getValue() == removedTimeline) {
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
	public void removeOutputTimeline(final SoundSourceData soundSourceData, final InputData inputData) {
		//==========================================================================================
		final Timeline removedTimeline = this.outputTimelines.remove(inputData);
		
		//------------------------------------------------------------------------------------------
		final float oldStartTimePos = removedTimeline.generator.getStartTimePos();
		final float oldEndTimePos = removedTimeline.generator.getEndTimePos();
		final float newStartTimePos = removedTimeline.getGeneratorStartTimePos();
		final float newEndTimePos = removedTimeline.getGeneratorEndTimePos();
		
		this.generatorChanged(soundSourceData, oldStartTimePos, oldEndTimePos, newStartTimePos, newEndTimePos);

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
	public synchronized 
	void calcMinMaxValues()
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
	public void notifyTimelineChangedListerners(final SoundSourceData soundSourceData, float changedStartTimePos, float changedEndTimePos)
	{
		//==========================================================================================
		for (TimelineChangedListernerInterface timelineChangedListerner : this.timelineChangedListerners)
		{
			timelineChangedListerner.notifyTimelineChanged(soundSourceData, this,
			                                               changedStartTimePos, changedEndTimePos);
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #moduleTimeline}.
	 */
	public Timeline getModuleTimeline()
	{
		return this.moduleTimeline;
	}

	/**
	 * Check if timeline is already output generators of given timeline (or sub timelines).
	 * 
	 * @param timeline
	 * 			is the timeline.
	 * @return
	 * 			<code>true</code> if timeline is already output timeline.
	 */
	public boolean checkIsOutputTimeline(Timeline timeline)
	{
		//==========================================================================================
		boolean ret;
		
		Set<Entry<InputData, Timeline>> entrySet = this.outputTimelines.entrySet();
		
		Iterator<Entry<InputData, Timeline>> entrySetIterator = entrySet.iterator();
		
		ret = false;
		
		while (entrySetIterator.hasNext())
		{
			Map.Entry<InputData, Timeline> entry = entrySetIterator.next();
			
			Timeline outputTimeline = entry.getValue();
			
			if (outputTimeline == timeline)
			{
				ret = true;
				break;
			}
			else
			{
				if (outputTimeline.checkIsOutputTimeline(timeline) == true)
				{
					ret = true;
					break;
				}
			}
		}
		
		//==========================================================================================
		return ret;
	}

	/**
	 * XXX Because of a memory leake clear timelines explicitely.
	 */
	public synchronized 
	void clearTimeline()
	{
		this.bufSoundSamples = null;
		
		this.inputTimelines.clear();
		
		this.outputTimelines.clear();
		
		this.subGeneratorTimelines.clear();
		
		this.timelineChangedListerners.clear();
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorBufferInterface#getGeneratorData()
	 */
	@Override
	public //synchronized
	Object getGeneratorData()
	{
		return this.generatorData;
	}
	
}
