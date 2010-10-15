/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData;

/**
 * <p>
 * 	Timeline-Manager Logic.
 * </p>
 * <p>
 * 	Manages a list of all Generator Timelines of a Module.
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
	 * Main Modul-Generator-Type Data.
	 */
	private final ModulGeneratorTypeData mainModulGeneratorTypeData;
	
	/**
	 * Generator Timelines.
	 */
	private Map<Generator, Timeline> generatorTimelines = new HashMap<Generator, Timeline>();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param mainModulGeneratorTypeData
	 * 			is the mainModulGeneratorTypeData.
	 */
	public TimelineManagerLogic(ModulGeneratorTypeData mainModulGeneratorTypeData)
	{
		this.mainModulGeneratorTypeData = mainModulGeneratorTypeData;
	}

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
//					Timeline inputTimeline;
//					
//					// Modul-Generator without inputs? TODO Only the count of inputs with other generators are interesting, const. value inputs not.
//					if ((inputGenerator instanceof ModulGenerator) &&
//						(inputGenerator.getInputsCount() == 0))
//					{
//						ModulGenerator modulGenerator = (ModulGenerator)inputGenerator;
//						
//						inputTimeline = this.moduleGenerators.get(modulGenerator);
//						
//						if (inputTimeline == null)
//						{
//							inputTimeline = this.makeTimeline(inputGenerator);
//							
//							this.moduleGenerators.put(modulGenerator, inputTimeline);
//						}
//					}
//					else
//					{
//						inputTimeline = this.createTimeline(inputGenerator);
//					}
					
					this.addInputTimeline(timeline, inputData, inputGenerator);
				}
			}
		}
		//==========================================================================================
		return timeline;
	}

	/**
	 * Add the given Input Data for the given input generator
	 * to the given timeline
	 * if the coresponding inputTimeline for the given input generator is managed in {@link #generatorTimelines}.
	 * 
	 * @param timeline
	 * 			is the timeline.
	 * @param inputData
	 * 			is the Input Data.
	 * @param inputGenerator
	 * 			is the input generator.
	 */
	private void addInputTimeline(Timeline timeline, InputData inputData, Generator inputGenerator)
	{
		Timeline inputTimeline = this.generatorTimelines.get(inputGenerator);
		
		if (inputTimeline != null)
		{
			timeline.addInputTimeline(inputData, inputTimeline);
			
			inputTimeline.addOutputTimeline(inputData, timeline);
		}
	}

	/**
	 * @param generator
	 * 			is the genrator.
	 * @return
	 * 			the timeline.
	 */
	private Timeline makeTimeline(Generator generator)
	{
		//==========================================================================================
		Timeline timeline;
		
		timeline = this.generatorTimelines.get(generator);
		
		if (timeline == null)
		{
			timeline = new Timeline();
		
			timeline.setGenerator(generator);
			
			this.generatorTimelines.put(generator, timeline);
		}
		
//		this.generators.put(generator, timeline);
		
		//==========================================================================================
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
		//==========================================================================================
		Timeline timeline = this.makeTimeline(generator);
		
		// TODO Notify outputs.
		
		return timeline;
		//==========================================================================================
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
		//==========================================================================================
//		Generator generator = timeline.getGenerator();
		
		timeline.setTimePos(generatorStartTimePos, generatorEndTimePos);

		//==========================================================================================
	}

	/**
	 * @param timeline
	 * 			is the timeline.
	 * @param generatorName
	 * 			is the generator name.
	 */
	public void updateName(Timeline timeline, String generatorName)
	{
		//==========================================================================================
		Generator generator = timeline.getGenerator();
		
		generator.setName(generatorName);
		
		//==========================================================================================
	}

	/**
	 * @param generatorTypeData
	 * 			is the generatorType Data.
	 * @param frameRate
	 * 			is the frame rate.
	 * @param generatorName
	 * 			is the genrator name.
	 * @return
	 * 			the generator.
	 */
	public Timeline createTimeline(GeneratorTypeData generatorTypeData,
	                               Float soundFrameRate,
	                               String generatorName)
	{
		//==========================================================================================
		Generator generator = generatorTypeData.createGeneratorInstance(generatorName, 
		                                                                soundFrameRate);

		this.mainModulGeneratorTypeData.addGenerator(generator);

		Timeline timeline = 
			this.createTimeline(generator);

		//==========================================================================================
		return timeline;
	}

	/**
	 * @param removedTimeline
	 * 			is the timeline.
	 */
	public void removeTimeline(Timeline removedTimeline)
	{
		//==========================================================================================
		Generator timelineGenerator = removedTimeline.getGenerator();
		
		this.mainModulGeneratorTypeData.removeGenerator(timelineGenerator);
		
		this.generatorTimelines.remove(timelineGenerator);
		
		//------------------------------------------------------------------------------------------
		// remove all inputs from all other timelines:
		
		for (Timeline timeline : this.generatorTimelines.values())
		{
			timeline.removeInputTimeline(removedTimeline);
		}
		//==========================================================================================
	}

	/**
	 * Add input to the given timeline and 
	 * the generator of this timeline.
	 * 
	 * @param timeline
	 * 			is the timeline.
	 * @param inputGenerator
	 * 			is the Input-Generator.
	 * @param inputTypeData
	 * 			is the Input-Type Data.
	 * @param inputValue
	 * 			is the Input-Value.
	 * @param inputStringValue
	 * 			is the Input-String-Value.
	 * @param inputModulInputTypeData
	 * 			is the Input-Modul-Input-Type Data.
	 * @return 
	 * 			the new created and added {@link InputData}-Object.
	 */
	public InputData addInputGenerator(Timeline timeline, 
	                                   Generator inputGenerator,
	                                   InputTypeData inputTypeData, 
	                                   Float floatValue, String stringValue,
	                                   InputTypeData modulInputTypeData)
	{
		//==========================================================================================
		InputData inputData;
		
		final Generator selectedGenerator = timeline.getGenerator();
		
		inputData = 
			selectedGenerator.addInputGenerator(inputGenerator, 
			                                    inputTypeData, 
			                                    floatValue, stringValue,
			                                    modulInputTypeData);
		
		//------------------------------------------------------------------------------------------
		this.addInputTimeline(timeline, inputData, inputGenerator);
		
		//==========================================================================================
		return inputData;
	}
	
}
