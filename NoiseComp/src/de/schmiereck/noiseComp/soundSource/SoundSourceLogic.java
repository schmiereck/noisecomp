package de.schmiereck.noiseComp.soundSource;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;
import de.schmiereck.noiseComp.timeline.Timeline;
import de.schmiereck.noiseComp.timeline.TimelineManagerLogic;

/**
 * <p>
 * 	Manages a connection between the (Output) {@link Generator} of
 * 	the current selected Generators and the 
 * 	{@link de.schmiereck.noiseComp.soundData.SoundData}-Output.
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
 * TODO Change management einbauen, wenn sich "oben" was ändert, smk
 * 
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceLogic
implements GeneratorChangeListenerInterface
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Is the generator genarates the output samples.
	 * 
	 * TODO Remove this and only use {@link #outputTimeline}.
	 */
	private Generator outputGenerator = null;

	/**
	 * Output Timeline.
	 */
	private Timeline outputTimeline = null;

	/**
	 * Timeline-Manager Logic.
	 */
	private TimelineManagerLogic timelineManagerLogic = null;
	
	/**
	 * Is a buffer to store the output samples generated by {@link #outputGenerator}. 
	 */
	private SoundSamplesBufferData	soundSamplesBufferData;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 *
	 */
	public SoundSourceLogic() // OutputGenerator outputGenerator
	{
		//this.outputGenerator = outputGenerator;

		this.soundSamplesBufferData = new SoundSamplesBufferData();
	}

	/**
	 * @param mainModulGeneratorTypeData
	 * 			is the mainModulGeneratorTypeData.
	 * @return
	 * 			the timelines.
	 */
	public synchronized List<Timeline> setMainModulGeneratorTypeData(ModulGeneratorTypeData mainModulGeneratorTypeData)
	{
		//==========================================================================================
		List<Timeline> timelines = new Vector<Timeline>();
		
		this.timelineManagerLogic = new TimelineManagerLogic(mainModulGeneratorTypeData);
		
		//------------------------------------------------------------------------------------------
//		this.mainModulGeneratorTypeData = mainModulGeneratorTypeData;
		
		OutputGenerator outputGenerator = mainModulGeneratorTypeData.getOutputGenerator();
		
		this.setOutputGenerator(outputGenerator);
	
		//------------------------------------------------------------------------------------------
		Iterator<Generator> generatorsIterator = mainModulGeneratorTypeData.getGeneratorsIterator();
		
		while (generatorsIterator.hasNext())
		{
			Generator generator = generatorsIterator.next();
			
			Timeline timeline = 
				this.timelineManagerLogic.createTimeline(generator);

			// Output Generator?
			if (generator == outputGenerator)
			{
				this.outputTimeline = timeline;
			}
			
			timelines.add(timeline);
		}
		//==========================================================================================
		return timelines;
	}

	/**
	 * @param outputGenerator 
	 * 			to set {@link #outputGenerator}.
	 */
	public void setOutputGenerator(Generator outputGenerator)
	{
		//==========================================================================================
		if (this.outputGenerator != null)
		{
			this.outputGenerator.getGeneratorChangeObserver().removeGeneratorChangeListener(this);
		}
		
		this.outputGenerator = outputGenerator;
		
		if (this.outputGenerator != null)
		{
			float timeLen = this.outputGenerator.getEndTimePos() - this.outputGenerator.getStartTimePos();
			
			this.soundSamplesBufferData.createBuffer(timeLen, this.outputGenerator.getSoundFrameRate());
	
			this.outputGenerator.getGeneratorChangeObserver().registerGeneratorChangeListener(this);
		}		
		//==========================================================================================
	}

//	/**
//	 * @param generator
//	 * 			is the generator.
//	 * @return
//	 * 			the timeline.
//	 */
//	public Timeline getTimeline(Generator generator)
//	{
//		Timeline timeline = this.timelineManagerLogic.getTimeline(generator);
//		
//		return timeline;
//	}
	
	/**
	 * @return 
	 * 			the attribute {@link #outputGenerator}.
	 */
	public Generator getOutputGenerator()
	{
		return this.outputGenerator;
	}

	/**
	 * @return 
	 * 			returns the {@link #outputTimeline}.
	 */
	public Timeline getOutputTimeline()
	{
		return this.outputTimeline;
	}

	/**
	 * @param frame
	 * 			ist the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample generateFrameSample(long frame)
	{
		SoundSample soundSample;
		
		//synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				soundSample = this.soundSamplesBufferData.generateSoundSample(frame, this.outputTimeline);
			}
			else
			{
				soundSample = null;
			}
		}
		
		return soundSample;
	}
	
	/**
	 * @param frame
	 * 			ist the sound sample frame.
	 * @return
	 * 			the sound sample.
	 */
	public SoundSample getFrameSample(long frame)
	{
		SoundSample soundSample;
		
		//synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				soundSample = this.soundSamplesBufferData.getSoundSample(frame);
			}
			else
			{
				soundSample = null;
			}
		}
		
		return soundSample;
	}

	/**
	 * Generates the next part of samples and store them in the buffer object {@link #soundSamplesBufferData}.
	 * 
	 * @param actualWaitPerFramesMillis
	 * 			???
	 */
	public void pollCalcFillBuffer(long actualWaitPerFramesMillis)
	{
		synchronized (this)
		{
			if (this.outputGenerator != null)
			{
				this.soundSamplesBufferData.calcWaitingSamplesPart(actualWaitPerFramesMillis / 1000.0F, 
																   this.outputTimeline);
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

	/**
	 * @return
	 */
	public long getEmptyBufferSize()
	{
		long emptyBufferSize;
		
		emptyBufferSize = this.getEmptyBufferEnd() - this.getEmptyBufferStart();
		
		return emptyBufferSize;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.GeneratorChangeListenerInterface#notifyGeneratorChanged(de.schmiereck.noiseComp.generator.Generator, float, float)
	 */
	public void notifyGeneratorChanged(Generator generator, float changedStartTimePos, float changedEndTimePos)
	{
		long bufferSamplesCount = this.soundSamplesBufferData.getBufferSamplesCount();

		float timeLen = this.outputGenerator.getEndTimePos(); // - this.outputGenerator.getStartTimePos();

		float frameRate = this.soundSamplesBufferData.getFrameRate();

		long newCamplesCount = (long)(timeLen * frameRate);
		
		System.out.println("SoundSourceLogic.notifyChanged: frameRate: generator(" + generator.getSoundFrameRate() + "), buffer(" + frameRate + ")");
		System.out.println("SoundSourceLogic.notifyChanged: oldBuffer(" + bufferSamplesCount + "), new(" + newCamplesCount + "), " + changedStartTimePos + ", " + changedEndTimePos + ")");
		
		if (newCamplesCount > bufferSamplesCount)
		{
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
}
