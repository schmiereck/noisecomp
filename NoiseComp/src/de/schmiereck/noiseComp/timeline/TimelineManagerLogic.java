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
import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.CompareUtils;
import de.schmiereck.noiseComp.swingView.SwingMain;

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
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
//		//==========================================================================================
//		Timeline timeline = this.makeTimeline(generator);
//		
////		if (OutputGenerator.class.getName().equals(generator.getGeneratorTypeData().getGeneratorTypeClassName())
//		if (generator instanceof OutputGenerator)
//		{
//			// TODO Update mainModulGeneratorTypeData.
//			//mainModulGeneratorTypeData.se
//			
//			// TODO Update SoundSourceLogic.
//			soundSourceLogic.setOutputGenerator(generator);
//		}
		//==========================================================================================
//		if (OutputGenerator.class.getName().equals(generator.getGeneratorTypeData().getGeneratorTypeClassName())
		if (generator instanceof OutputGenerator)
		{
			// TODO Update mainModulGeneratorTypeData.
			//mainModulGeneratorTypeData.set
			
			// TODO Update SoundSourceLogic.
			soundSourceLogic.setOutputGenerator(generator);
		}
		//------------------------------------------------------------------------------------------
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

//	/**
//	 * @param generator
//	 * 			is the generator.
//	 * @return
//	 * 			the timeline.
//	 */
//	public Timeline addGenerator(Generator generator)
//	{
//		//==========================================================================================
//		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
//		
//		//==========================================================================================
//		Timeline timeline = this.makeTimeline(generator);
//		
////		if (OutputGenerator.class.getName().equals(generator.getGeneratorTypeData().getGeneratorTypeClassName())
//		if (generator instanceof OutputGenerator)
//		{
//			// TODO Update mainModulGeneratorTypeData.
//			//mainModulGeneratorTypeData.se
//			
//			// TODO Update SoundSourceLogic.
//			soundSourceLogic.setOutputGenerator(generator);
//		}
//		
//		return timeline;
//		//==========================================================================================
//	}

	/**
	 * @param timeline
	 * 			is the timeline.
	 * @param generatorStartTimePos
	 * 			is the generator StartTimePos.
	 * @param generatorEndTimePos
	 * 			is the generator EndTimePos.
	 */
	public void updateTimePos(Timeline timeline, float generatorStartTimePos, float generatorEndTimePos)
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
	 * @param newTimeline
	 * 			is the timeline.
	 * @param inputTimeline
	 * 			is the Input-Timeline.
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
	public InputData addInputGenerator(Timeline newTimeline, 
	                                   Timeline inputTimeline,
	                                   InputTypeData inputTypeData, 
	                                   Float floatValue, String stringValue,
	                                   InputTypeData modulInputTypeData)
	{
		//==========================================================================================
		InputData inputData;
		
		final Generator selectedGenerator = newTimeline.getGenerator();
		
		final Generator inputGenerator;
		
		if (inputTimeline != null)
		{
			inputGenerator = inputTimeline.getGenerator();
		}
		else
		{
			inputGenerator = null;
		}
		
		inputData = 
			selectedGenerator.addInputGenerator(inputGenerator, 
			                                    inputTypeData, 
			                                    floatValue, stringValue,
			                                    modulInputTypeData);
		
		//------------------------------------------------------------------------------------------
		this.addInputTimeline(newTimeline, inputData, inputGenerator);
		
		//==========================================================================================
		return inputData;
	}
	
	/**
	 * @param updatedTimeline
	 * @param inputData
	 * @param inputTimeline
	 * @param inputTypeData
	 * @param floatValue
	 * @param stringValue
	 * @param modulInputTypeData
	 */
	public void updateInput(Timeline updatedTimeline, 
	                        InputData inputData, 
	                        Timeline inputTimeline,
							InputTypeData inputTypeData, 
							Float floatValue, String stringValue,
							InputTypeData modulInputTypeData)
	{
		//==========================================================================================
		// Update Timeline-Input for given Input-Data and Input-Generator:
		
		Timeline oldInputTimeline = updatedTimeline.updateInput(inputData, inputTimeline);
		
		if (oldInputTimeline != inputTimeline)
		{
			//------------------------------------------------------------------------------------------
			// Update Timeline-Outputs for given Input-Data and Input-Generator:
		
//			Iterator<Timeline> timelinesIterator = this.getTimelinesIterator();
//			
//			while (timelinesIterator.hasNext())
//			{
//				Timeline timeline = timelinesIterator.next();
//				
//				Map<InputData, Timeline> outputTimelines = timeline.getOutputTimelines();
//				
//				for (Map.Entry<InputData, Timeline> outputTimelineEntry : outputTimelines.entrySet())
//				{
//					if (outputTimelineEntry.getValue() == oldInputTimeline)
//					{ }
//				}
//			}
			if (oldInputTimeline != null)
			{
				oldInputTimeline.removeOutputTimeline(inputData);
			}
			
			if (inputTimeline != null)
			{
				inputTimeline.addOutputTimeline(inputData, updatedTimeline);
			}
			//--------------------------------------------------------------------------------------
			// Update Input-Data:
			
			Generator inputGenerator;
			
			if (inputTimeline != null)
			{
				inputGenerator = inputTimeline.getGenerator();
			}
			else
			{
				inputGenerator = null;
			}
			
			inputData.setInputGenerator(inputGenerator);
		}
		
		if ((CompareUtils.compareWithNull(inputData.getInputValue(), floatValue) == false) ||
			(CompareUtils.compareWithNull(inputData.getInputStringValue(), stringValue) == false))
		{
			inputData.setInputValue(floatValue, stringValue);
			
			updatedTimeline.generatorChanged();
		}
		
		if (inputData.getInputModulInputTypeData() != modulInputTypeData)
		{
			inputData.setInputModulInputTypeData(modulInputTypeData);
			
			updatedTimeline.generatorChanged();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns Iterator of {@link #generatorTimelines} values.
	 */
	public Iterator<Timeline> getTimelinesIterator()
	{
		return this.generatorTimelines.values().iterator();
	}

	
}
