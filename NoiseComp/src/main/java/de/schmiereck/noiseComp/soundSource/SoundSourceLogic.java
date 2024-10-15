package de.schmiereck.noiseComp.soundSource;

import java.util.*;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.signal.OutputGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.soundScheduler.SoundDataLogic;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineChangedListernerInterface;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Manages a connection between the (Output) {@link Generator} of
 * 	the current selected Generators and the 
 * 	{@link SoundDataLogic}-Output.
 * </p>
 * <p>
 * 	Buffers the output.<br/>
 * 	The buffer is filled by a polling thread.<br/>
 * 	The affected parts of the buffer are cleard if changes in the input
 * 	are reported. 
 * </p>
 * <p>
 * Manages the actual Output-Generator and filles a buffer with his
 * pre calculated samples (by polling in a thread).
 * </p>
 * 
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceLogic
implements GeneratorChangeListenerInterface, TimelineChangedListernerInterface
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Timeline-Manager Logic.
	 */
	private TimelineManagerLogic timelineManagerLogic = null;
	
	/**
	 * Is a buffer to store the output samples generated by {@link SoundSourceData#getOutputTimeline()}.
	 */
	private final SoundSamplesBufferData	soundSamplesBufferData;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 */
	public SoundSourceLogic() {
		this.soundSamplesBufferData = new SoundSamplesBufferData();
	}

	/**
	 * @param mainModuleGeneratorTypeData
	 * 			is the mainModuleGeneratorTypeData.
	 */
	public synchronized void setMainModuleGeneratorTypeData(final SoundSourceData soundSourceData,
															final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData) {
		//==========================================================================================
		//------------------------------------------------------------------------------------------
		this.timelineManagerLogic = new TimelineManagerLogic(this, mainModuleGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
		soundSourceData.setOutputTimeline(null);
		
		//------------------------------------------------------------------------------------------
		// Walk rekursive through inputs because we need existing timelines to create input timelines:

		final OutputGenerator outputGenerator = mainModuleGeneratorTypeData.getOutputGenerator();
		
		if (outputGenerator != null) {
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			final List<Timeline> inputTimelines = new Vector<>();
			
			this.createTimeline(soundSourceData, inputTimelines, outputGenerator);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			// Sort timelines:

			final Iterator<Generator> generatorsIterator = mainModuleGeneratorTypeData.getGeneratorsIterator();
			
			while (generatorsIterator.hasNext()) {
				final Generator generator = generatorsIterator.next();

				final Timeline timeline;
				final Timeline inputTimeline = this.searchInputTimeline(inputTimelines, generator);
				
				if (Objects.nonNull(inputTimeline)) {
					timeline = inputTimeline;
				} else {
					// Not connected generator.
					timeline = this.timelineManagerLogic.createTimeline(soundSourceData, generator);
				}

				soundSourceData.addTimeline(timeline);
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
//		Iterator<Generator> generatorsIterator = mainModuleGeneratorTypeData.getGeneratorsIterator();
//		
//		while (generatorsIterator.hasNext())
//		{
//			Generator generator = generatorsIterator.next();
//			
//			Timeline timeline = 
//				this.timelineManagerLogic.createTimeline(generator);
//			
//			timelines.add(timeline);
//		}
		
		//------------------------------------------------------------------------------------------
		this.printDebug1(soundSourceData.getOutputTimeline(), 0);
		this.printDebug2(soundSourceData.getOutputTimeline(), 0);
		this.printDebug3(soundSourceData.getOutputTimeline(), 0, false);
		
		//==========================================================================================
	}

	private void printDebug1(Timeline parentTimeline, int level) {
		for (int pos = 0; pos < level * 2; pos++) {
			System.out.print(' ');
		}
		System.out.println("DBG 1: " + parentTimeline);
		
		if (parentTimeline != null) {
			Iterator<InputData> inputsIterator = parentTimeline.getInputsIterator();
			
			if (inputsIterator != null) {
				while (inputsIterator.hasNext()) {
					InputData inputData = inputsIterator.next();
	
					Timeline inputTimeline = (Timeline)parentTimeline.getInputGeneratorBuffer(inputData);
					
					if (inputTimeline != null) {
						this.printDebug1(inputTimeline, level + 1);
					}
				}
			}
		}
	}

	private void printDebug2(Timeline parentTimeline, int level) {
		for (int pos = 0; pos < level * 2; pos++) {
			System.out.print(' ');
		}
		System.out.println("DBG 2: " + parentTimeline);
		
		if (parentTimeline != null) {
			Collection<Timeline> timelines = parentTimeline.getInputTimelines().values();
			
			for (Timeline timeline : timelines) {
				this.printDebug2(timeline, level + 1);
			}
		}
	}


	private void printDebug3(Timeline parentTimeline, int level, boolean isSub) {
		for (int pos = 0; pos < level * 2; pos++) {
			System.out.print(' ');
		}
		if (isSub == true) {
			System.out.print("SUB ");
		}
		System.out.println("DBG 3: " + parentTimeline);
		
		if (parentTimeline != null) {
			Collection<Timeline> timelines = parentTimeline.getInputTimelines().values();
			
			for (Timeline timeline : timelines) {
				this.printDebug3(timeline, level + 1, false);
				
				for (Timeline subTimeline : timeline.getSubGeneratorTimelines()) {
					this.printDebug3(subTimeline, level + 2, true);
				}
			}
		}
	}

	/**
	 * @param inputTimelines
	 * 			are the timelines.
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			is the searched Timeline with given Generator.
	 */
	private Timeline searchInputTimeline(List<Timeline> inputTimelines, Generator generator)
	{
		//==========================================================================================
		Timeline retTimeline;
		
		retTimeline = null;
		
		for (Timeline timeline : inputTimelines)
		{
			if (timeline.getGenerator() == generator)
			{
				retTimeline = timeline;
				break;
			}
		}
		
		//==========================================================================================
		return retTimeline;
	}

	/**
	 * @param timelines
	 * 			are the timelines.
	 * @param generator
	 * 			is the generator.
	 */
	private void createTimeline(final SoundSourceData soundSourceData, List<Timeline> timelines, Generator generator)
	{
		//==========================================================================================
		Iterator<InputData> inputsIterator = generator.getInputsIterator();
		
		if (inputsIterator != null) {
			while (inputsIterator.hasNext()) {
				InputData inputData = inputsIterator.next();
				
				Generator inputGenerator = inputData.getInputGenerator();
				
				if (inputGenerator != null) {
					this.createTimeline(soundSourceData, timelines, inputGenerator);
				}
			}
		}
		
		//------------------------------------------------------------------------------------------
		Timeline timeline = this.timelineManagerLogic.createTimeline(soundSourceData, generator);
	
		timelines.add(timeline);
		
		//==========================================================================================
	}

	public void setOutputGeneratorTimeline(final SoundSourceData soundSourceData, final Timeline outputTimeline) {
		//==========================================================================================
		this.setOutputTimeline(soundSourceData, outputTimeline);

		final Generator outputGenerator = outputTimeline.getGenerator();

		if (Objects.nonNull(outputGenerator)) {
			final float timeLen = outputGenerator.getEndTimePos() - outputGenerator.getStartTimePos();

			this.soundSamplesBufferData.createBuffer(timeLen, outputGenerator.getSoundFrameRate());
		}
		//==========================================================================================
	}

	public void setOutputTimeline(final SoundSourceData soundSourceData, final Timeline outputTimeline) {
		//==========================================================================================
		// Remove last timeline change observer.

		if (Objects.nonNull(soundSourceData.getOutputTimeline())) {
			soundSourceData.getOutputTimeline().removeTimelineChangedListerner(this);
		}

		//------------------------------------------------------------------------------------------
		soundSourceData.setOutputTimeline(outputTimeline);

		//------------------------------------------------------------------------------------------
		// Register new timeline change observer.
		if (Objects.nonNull(soundSourceData.getOutputTimeline())) {
			soundSourceData.getOutputTimeline().addTimelineChangedListerner(this);
		}

		//==========================================================================================
	}

	/**
	 * @param frame
	 * 			ist the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample generateFrameSample(final SoundSourceData soundSourceData, final long frame)
	{
		SoundSample soundSample;
		
		//synchronized (this)
		{
			if (Objects.nonNull(soundSourceData.getOutputTimeline())) {
				soundSample = this.soundSamplesBufferData.generateSoundSample(frame, soundSourceData.getOutputTimeline());
			} else {
				soundSample = null;
			}
		}
		
		return soundSample;
	}

	/**
	 * Generates the next part of samples and store them in the buffer object {@link #soundSamplesBufferData}.
	 * 
	 * @param actualWaitPerFramesMillis
	 * 			are the Milliseconds to calculate.
	 */
	public void pollCalcFillBuffer(final SoundSourceData soundSourceData, final long actualWaitPerFramesMillis) {
		synchronized (this) {
			if (Objects.nonNull(soundSourceData.getOutputTimeline())) {
				long emptyBuffer1Start = this.soundSamplesBufferData.getEmptyBufferStart();
				long emptyBuffer1End = this.soundSamplesBufferData.getEmptyBufferEnd();
				
				// Time in seconds.
				float timeSchedulerIsWaiting = (actualWaitPerFramesMillis / 1000.0F);
				
				float timeBufferIsFilled = timeSchedulerIsWaiting * 2.0F;
				
				boolean bufferCompletelyFilled =
					this.soundSamplesBufferData.calcWaitingSamplesPart(timeBufferIsFilled,
							soundSourceData.getOutputTimeline());
				
				if (bufferCompletelyFilled) {
					this.timelineManagerLogic.notifyBufferCompletelyFilled();
				}
				
				long emptyBuffer2Start = this.soundSamplesBufferData.getEmptyBufferStart();
				long emptyBuffer2End = this.soundSamplesBufferData.getEmptyBufferEnd();
				
				//long calcBufferStart;
				//long calcBufferEnd;
				
				if ((emptyBuffer1Start != emptyBuffer2Start) ||
					(emptyBuffer1End != emptyBuffer2End)) {
					this.timelineManagerLogic.notifyTimelineContentChangedListeners(emptyBuffer1Start,
					                                                                emptyBuffer1End);
				}
			}
		}
	}

	public long getEmptyBufferStart()
	{
		long ret;
		
		if (this.soundSamplesBufferData != null)
		{
			ret = this.soundSamplesBufferData.getEmptyBufferStart();
		}
		else
		{
			ret = 0L;
		}
		
		return ret;
	}
	
	public long getEmptyBufferEnd()
	{
		long ret;
		
		if (this.soundSamplesBufferData != null)
		{
			ret = this.soundSamplesBufferData.getEmptyBufferEnd();
		}
		else
		{
			ret = 0L;
		}
		
		return ret;
	}

	public long getEmptyBufferSize()
	{
		long emptyBufferSize;
		
		emptyBufferSize = this.getEmptyBufferEnd() - this.getEmptyBufferStart();
		
		return emptyBufferSize;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	public void notifyGeneratorChanged(final SoundSourceData soundSourceData, Generator generator,
									   final float changedStartTimePos, final float changedEndTimePos) {
		final long bufferSamplesCount = this.soundSamplesBufferData.getBufferSamplesCount();

		final float timeLen = soundSourceData.getOutputTimeline().getGeneratorEndTimePos(); // - this.outputGenerator.getStartTimePos();

		final float frameRate = this.soundSamplesBufferData.getFrameRate();

		final long newCamplesCount = (long)(timeLen * frameRate);
		
		System.out.println("SoundSourceLogic.notifyChanged: frameRate: generator(" + generator.getSoundFrameRate() + "), buffer(" + frameRate + ")");
		System.out.println("SoundSourceLogic.notifyChanged: oldBuffer(" + bufferSamplesCount + "), new(" + newCamplesCount + "), " + changedStartTimePos + ", " + changedEndTimePos + ")");
		
		if (newCamplesCount > bufferSamplesCount) {
			this.soundSamplesBufferData.createBuffer(timeLen, frameRate);
		}
		
		this.soundSamplesBufferData.clearBuffer(changedStartTimePos, changedEndTimePos);
	}

//	/**
//	 * @param generator
//	 * 			is the generator.
//	 * @return
//	 * 			the timeline.
//	 */
//	public Timeline addGenerator(Generator generator)
//	{
//		Timeline timeline = this.timelineManagerLogic.addGenerator(generator);
//		
//		return timeline;
//	}

	/**
	 * @return 
	 * 			returns the {@link #timelineManagerLogic}.
	 */
	public TimelineManagerLogic getTimelineManagerLogic()
	{
		return this.timelineManagerLogic;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.timeline.TimelineChangedListernerInterface#notifyTimelineChanged(de.schmiereck.noiseComp.timeline.Timeline, float, float)
	 */
	@Override
	public void notifyTimelineChanged(final SoundSourceData soundSourceData, Timeline timeline, float changedStartTimePos, float changedEndTimePos) {
		//==========================================================================================
		Generator generator = timeline.getGenerator();
		
		if (generator != null)
		{
			this.notifyGeneratorChanged(soundSourceData, generator, changedStartTimePos, changedEndTimePos);
		}
		
		//==========================================================================================
	}
}
