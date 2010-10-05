/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
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
	 * Sound Samples between start and end output of {@link #generator}.
	 */
	private List<SoundSample> soundSamples = new Vector<SoundSample>();
	
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
	public void setGenerator(Generator generator)
	{
		this.generator = generator;
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
	
}
