/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.ModulGenerator;

/**
 * <p>
 * 	Timeline-Manager Logic.
 * </p>
 * <p>
 * 	Manages a list of all Modul-Generator Timelines without inputs.
 * </p>
 * 
 * @author smk
 * @version <p>05.10.2010:	created, smk</p>
 */
public class TimelineManagerLogic
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Modul-Generator Timelines without inputs.
	 */
	private Map<ModulGenerator, Timeline> moduleGenerators = new HashMap<ModulGenerator, Timeline>();
	
//	/**
//	 * Generator Timelines.
//	 */
//	private Map<Generator, Timeline> generators = new HashMap<Generator, Timeline>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Create all timelines for gnerators an input-generators if they have inputs 
	 * that maybe changed in the module.
	 * 
	 * @param generator
	 * 			is the generator
	 * @return
	 * 			the timeline.
	 */
	public Timeline createTimeline(Generator generator)
	{
		//==========================================================================================
		Timeline timeline = this.makeTimeline(generator);
		
		//------------------------------------------------------------------------------------------
		Iterator<InputData> inputsIterator = generator.getInputsIterator();
		
		if (inputsIterator != null)
		{
			while (inputsIterator.hasNext())
			{
				InputData inputData = (InputData)inputsIterator.next();
				
				Generator inputGenerator = inputData.getInputGenerator();
				
				if (inputGenerator != null)
				{
					Timeline inputTimeline;
					
					// Modul-Generator without inputs? TODO Only the count of inputs with other generators are interesting, const. value inputs not.
					if ((inputGenerator instanceof ModulGenerator) &&
						(inputGenerator.getInputsCount() == 0))
					{
						ModulGenerator modulGenerator = (ModulGenerator)inputGenerator;
						
						inputTimeline = this.moduleGenerators.get(modulGenerator);
						
						if (inputTimeline == null)
						{
							inputTimeline = this.makeTimeline(inputGenerator);
							
							this.moduleGenerators.put(modulGenerator, inputTimeline);
						}
					}
					else
					{
						inputTimeline = this.createTimeline(inputGenerator);
					}
					
					timeline.addInputTimeline(inputData, inputTimeline);
					
					inputTimeline.addOutputTimeline(inputData, timeline);
						
				}
			}
		}
		//==========================================================================================
		return timeline;
	}

	/**
	 * @param generator
	 * 			is the genrator.
	 * @return
	 * 			the timeline.
	 */
	private Timeline makeTimeline(Generator generator)
	{
		Timeline timeline;
		
		timeline = this.moduleGenerators.get(generator);
		
		if (timeline == null)
		{
			timeline = new Timeline();
		
			timeline.setGenerator(generator);
		}
		
//		this.generators.put(generator, timeline);
		
		return timeline;
	}

//	/**
//	 * @param generator
//	 * 			is the generator.
//	 * @return
//	 * 			the timeline or <code>null</code> if not found.
//	 */
//	public Timeline getTimeline(Generator generator)
//	{
//		return this.generators.get(generator);
//	}

	/**
	 * @param generator
	 * 			is the generator.
	 * @return
	 * 			the timeline.
	 */
	public Timeline addGenerator(Generator generator)
	{
		Timeline timeline = this.makeTimeline(generator);
		
		// TODO Notify outputs.
		
		return timeline;
	}

	/**
	 * @param timeline
	 * 			is the timeline.
	 * @param generatorStartTimePos
	 * 			is the generator StartTimePos.
	 * @param generatorEndTimePos
	 * 			is the generator EndTimePos.
	 */
	public void updateTimePos(Timeline timeline, Float generatorStartTimePos, Float generatorEndTimePos)
	{
		Generator generator = timeline.getGenerator();
		
		// TODO Notify outputs.
		
		generator.setStartTimePos(generatorStartTimePos);
		generator.setEndTimePos(generatorEndTimePos);
	}

	/**
	 * @param timeline
	 * 			is the timeline.
	 * @param generatorName
	 * 			is the generator name.
	 */
	public void updateName(Timeline timeline, String generatorName)
	{
		Generator generator = timeline.getGenerator();
		
		// TODO Notify change listeners.
		
		generator.setName(generatorName);
	}
	
}
