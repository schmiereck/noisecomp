/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.timeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.ModulGenerator;
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
	 * Main-Modul Generator Timelines.
	 */
	private Map<Generator, Timeline> mainGeneratorTimelines = new HashMap<Generator, Timeline>();
	
	/**
	 * Timeline Content Changed Listeners.
	 */
	private List<TimelineContentChangedListenerInterface> timelineContentChangedListeners = new Vector<TimelineContentChangedListenerInterface>();
	
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
	 * @param isSubModuleGenerator
	 * 			<code>true</code> if the generator is a input of a sub module.
	 * @return
	 * 			the timeline.
	 */
	public Timeline createTimeline(Generator generator)
	{
		//==========================================================================================
		SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		//==========================================================================================
		Timeline timeline;
		
		//------------------------------------------------------------------------------------------
		// Search or create Main-Modul Timeline:
		
		timeline = this.makeMainTimeline(generator);
		
		//------------------------------------------------------------------------------------------
//		if (OutputGenerator.class.getName().equals(generator.getGeneratorTypeData().getGeneratorTypeClassName())
		if (generator instanceof OutputGenerator)
		{
			OutputGenerator outputGenerator = (OutputGenerator)generator;
			
			// Update mainModulGeneratorTypeData.
			this.mainModulGeneratorTypeData.setOutputGenerator(outputGenerator);
			
			soundSourceLogic.setOutputTimeline(timeline);
			
			// Update SoundSourceLogic.
			soundSourceLogic.setOutputGenerator(generator);
		}
		//------------------------------------------------------------------------------------------
		this.createTimelineInputs(generator, timeline);
		
		//------------------------------------------------------------------------------------------
		// Notify Modul:
		{
			this.updateModuleTimelines(timeline);
		}
		
		//==========================================================================================
		return timeline;
	}

	/**
	 * @param generator
	 * 			is the generator
	 * @param timeline
	 * 			is the timeline.
	 */
	private void createTimelineInputs(Generator generator, 
	                                  Timeline timeline)
	{
		//==========================================================================================
		Iterator<InputData> inputsIterator = generator.getInputsIterator();
		
		if (inputsIterator != null)
		{
			while (inputsIterator.hasNext())
			{
				InputData inputData = (InputData)inputsIterator.next();
				
				Generator inputGenerator = inputData.getInputGenerator();
				
				if (inputGenerator != null)
				{
					//Timeline inputTimeline = 
						this.addInputTimeline(timeline, inputData, inputGenerator);
				}
			}
		}
		
		// Modul-Generator?
		if ((generator instanceof ModulGenerator))
		{
			ModulGenerator modulGenerator = (ModulGenerator)generator;
			
			ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)modulGenerator.getGeneratorTypeData();
			
			OutputGenerator outputGenerator = modulGeneratorTypeData.getOutputGenerator();
			
			this.createSubTimeline(timeline, outputGenerator);
			//this.createSubTimelineInputs(inputGenerator, timeline);
		}
		//==========================================================================================
	}

	/**
	 * @param modulTimeline
	 * 			is the Modul-Timeline.
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.
	 */
	private Timeline createSubTimeline(Timeline modulTimeline, Generator generator)
	{
		//==========================================================================================
		// Search or create Sub-Modul Timeline for given Modul-Timeline:
		
		Timeline subTimeline = this.makeSubTimeline(modulTimeline, generator);
		
		//------------------------------------------------------------------------------------------
		this.createSubTimelineInputs(modulTimeline, generator);
		
		//==========================================================================================
		return subTimeline;
	}

	/**
	 * @param modulTimeline
	 * 			is the Modul-Timeline.
	 * @param generator
	 * 			is the generator
	 */
	private void createSubTimelineInputs(Timeline modulTimeline,
	                                     Generator generator)
	{
		//==========================================================================================
		Iterator<InputData> inputsIterator = generator.getInputsIterator();
		
		if (inputsIterator != null)
		{
			while (inputsIterator.hasNext())
			{
				InputData inputData = (InputData)inputsIterator.next();
				
				Generator inputGenerator = inputData.getInputGenerator();
				
				if (inputGenerator != null)
				{
					//------------------------------------------------------------------------------
					Timeline inputTimeline = this.createSubTimeline(modulTimeline, inputGenerator);
					
					// Modul-Generator?
					if ((inputGenerator instanceof ModulGenerator))
					{
						ModulGenerator modulGenerator = (ModulGenerator)inputGenerator;
						
						ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)modulGenerator.getGeneratorTypeData();
						
						OutputGenerator outputGenerator = modulGeneratorTypeData.getOutputGenerator();
						
						this.createSubTimeline(inputTimeline, outputGenerator);
					}
					//------------------------------------------------------------------------------
					this.addInputSubTimeline(modulTimeline, inputTimeline, inputData, generator);
					
					//------------------------------------------------------------------------------
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * Add the given Input Data for the given input generator
	 * to the given timeline
	 * if the coresponding inputTimeline for the given input generator is managed in {@link #mainGeneratorTimelines}.
	 * 
	 * @param timeline
	 * 			is the timeline.
	 * @param inputData
	 * 			is the Input Data.
	 * @param inputGenerator
	 * 			is the input generator.
	 * @return
	 * 			the inputTimeline.
	 */
	private Timeline addInputTimeline(Timeline timeline, InputData inputData, Generator inputGenerator)
	{
		Timeline inputTimeline = this.mainGeneratorTimelines.get(inputGenerator);
		
		if (inputTimeline != null)
		{
			timeline.addInputTimeline(inputData, inputTimeline);
			
			inputTimeline.addOutputTimeline(inputData, timeline);
		}
		
		return inputTimeline;
	}
	
	/**
	 * Add the given Input Data for the given input generator
	 * to the given timeline
	 * if the coresponding inputTimeline for the given input generator is managed in {@link #mainGeneratorTimelines}.
	 * 
	 * @param modulTimeline
	 * 			is the Modul-Timeline.
	 * @param timeline
	 * 			is the timeline.
	 * @param inputData
	 * 			is the Input Data.
	 * @param inputGenerator
	 * 			is the input generator.
	 */
	private void addInputSubTimeline(Timeline modulTimeline, Timeline timeline, InputData inputData, Generator inputGenerator)
	{
		Timeline inputTimeline = modulTimeline.getSubGeneratorTimeline(inputGenerator);
		
		if (inputTimeline != null)
		{
//			timeline.addInputTimeline(inputData, inputTimeline);
//			
//			inputTimeline.addOutputTimeline(inputData, timeline);

			inputTimeline.addInputTimeline(inputData, timeline);
			
			timeline.addOutputTimeline(inputData, inputTimeline);
		}
	}

	/**
	 * Search or create Main-Modul Timeline.
	 * 
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.
	 */
	private Timeline makeMainTimeline(Generator generator)
	{
		//==========================================================================================
		Timeline timeline;
		
		timeline = this.mainGeneratorTimelines.get(generator);
		
		if (timeline == null)
		{
			timeline = new Timeline(null);
		
			timeline.setGenerator(generator);
			
			this.mainGeneratorTimelines.put(generator, timeline);
		}
		
		//==========================================================================================
		return timeline;
	}

	/**
	 * Search or create Sub-Modul Timeline.
	 * 
	 * @param modulTimeline
	 * 			is the Modul-Timeline.
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.
	 */
	private Timeline makeSubTimeline(Timeline modulTimeline, Generator generator)
	{
		//==========================================================================================
		Timeline timeline;
		
		timeline = modulTimeline.getSubGeneratorTimeline(generator);
		
		if (timeline == null)
		{
			timeline = new Timeline(modulTimeline);
		
			timeline.setGenerator(generator);
			
			modulTimeline.addSubGeneratorTimeline(generator, timeline);
		}
		
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
	 * @param updatedTimeline
	 * 			is the timeline.
	 * @param generatorStartTimePos
	 * 			is the generator StartTimePos.
	 */
	public void updateStartTimePos(Timeline updatedTimeline, float generatorStartTimePos)
	{
		//==========================================================================================
		float endTimePos = updatedTimeline.getGeneratorEndTimePos();
		
		this.updateTimePos(updatedTimeline, generatorStartTimePos, endTimePos);

		//==========================================================================================
	}

	/**
	 * @param updatedTimeline
	 * 			is the timeline.
	 * @param generatorStartTimePos
	 * 			is the generator EndTimePos.
	 */
	public void updateEndTimePos(Timeline updatedTimeline, float generatorEndTimePos)
	{
		//==========================================================================================
		float startTimePos = updatedTimeline.getGeneratorStartTimePos();
		
		this.updateTimePos(updatedTimeline, startTimePos, generatorEndTimePos);
		
		//==========================================================================================
	}

	/**
	 * @param updatedTimeline
	 * 			is the timeline.
	 * @param generatorStartTimePos
	 * 			is the generator StartTimePos.
	 * @param generatorEndTimePos
	 * 			is the generator EndTimePos.
	 */
	public void updateTimePos(Timeline updatedTimeline, float generatorStartTimePos, float generatorEndTimePos)
	{
		//==========================================================================================
		updatedTimeline.setTimePos(generatorStartTimePos, generatorEndTimePos);

		//------------------------------------------------------------------------------------------
		// Notify Modul:
		{
			this.updateModuleTimelines(updatedTimeline);
		}
		//------------------------------------------------------------------------------------------
		// Update all output Module-Timelines:
		{
			Map<InputData, Timeline> outputInputTimelines = updatedTimeline.getOutputTimelines();
			Collection<Timeline> outputTimelines = outputInputTimelines.values();
			
			for (Timeline outputTimeline : outputTimelines)
			{
				this.updateModuleTimelines(outputTimeline);
			}
		}
		//==========================================================================================
	}
	
	/**
	 * @param updatedTimeline
	 * 			is the timeline.
	 * @param generatorName
	 * 			is the generator name.
	 */
	public void updateName(Timeline updatedTimeline, String generatorName)
	{
		//==========================================================================================
		Generator generator = updatedTimeline.getGenerator();
		
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
	 * @param generatorPos
	 * 			is the position of the generator in the list of generators.
	 * @return
	 * 			the generator.
	 */
	public Timeline createTimeline(GeneratorTypeData generatorTypeData,
	                               Float soundFrameRate,
	                               String generatorName,
                                   int generatorPos)
	{
		//==========================================================================================
		Generator generator = generatorTypeData.createGeneratorInstance(generatorName, 
		                                                                soundFrameRate);

		this.mainModulGeneratorTypeData.addGenerator(generatorPos,
		                                             generator);

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
		// remove all inputs from all other timelines:
		
		for (Timeline timeline : this.mainGeneratorTimelines.values())
		{
			timeline.removeInputTimeline(removedTimeline);
		}
		//------------------------------------------------------------------------------------------
		Generator timelineGenerator = removedTimeline.getGenerator();
		
		this.mainModulGeneratorTypeData.removeGenerator(timelineGenerator);
		
		this.mainGeneratorTimelines.remove(timelineGenerator);
		
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
		
		final Generator generator = newTimeline.getGenerator();
		
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
			generator.addInputGenerator(inputGenerator, 
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
	 * 			to set {@link Timeline#getInputTimelines()} of updatedTimeline with key.
	 * @param inputTimeline
	 * 			to set to {@link Timeline#getInputTimelines()} of updatedTimeline as value with given key.
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
		{
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
		}
		//------------------------------------------------------------------------------------------
		// Update Modul:
		{
			this.updateModuleTimelines(updatedTimeline);
		}
		//------------------------------------------------------------------------------------------
		boolean generatorChanged = false;
		
		if ((CompareUtils.compareWithNull(inputData.getInputValue(), floatValue) == false) ||
			(CompareUtils.compareWithNull(inputData.getInputStringValue(), stringValue) == false))
		{
			inputData.setInputValue(floatValue, stringValue);
			
			generatorChanged = true;
		}
		
		if (inputData.getInputModulInputTypeData() != modulInputTypeData)
		{
			inputData.setInputModulInputTypeData(modulInputTypeData);
			
			generatorChanged = true;
		}
		
		if (generatorChanged == true)
		{
			updatedTimeline.generatorChanged();
		}
		//==========================================================================================
	}

	/**
	 * @param updatedTimeline
	 * 			is the updated timeline.
	 */
	private void updateModuleTimelines(Timeline updatedTimeline)
	{
		//==========================================================================================
		Generator generator = updatedTimeline.getGenerator();
		
		if (generator != null)
		{
			if (generator instanceof ModulGenerator)
			{
				// Notify module timelines.

				Collection<Timeline> subGeneratorTimelines = updatedTimeline.getSubGeneratorTimelines();
				
				for (Timeline subGeneratorTimeline : subGeneratorTimelines)
				{
					this.updateModuleTimelines(subGeneratorTimeline);
					
					subGeneratorTimeline.generatorChanged();
				}
			}
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns Iterator of {@link #mainGeneratorTimelines} values.
	 */
	public Iterator<Timeline> getTimelinesIterator()
	{
		return this.mainGeneratorTimelines.values().iterator();
	}

	/**
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.<br/>
	 * 			<code>null</code> if no timeline found.
	 */
	public Timeline searchGeneratorTimeline(Generator generator)
	{
		return mainGeneratorTimelines.get(generator);
	}

	/**
	 * @param timelineContentChangedListener
	 * 			to remove from {@link #timelineContentChangedListeners}.
	 */
	public void removeTimelineContentChangedListeners(TimelineContentChangedListenerInterface timelineContentChangedListener)
	{
		this.timelineContentChangedListeners.remove(timelineContentChangedListener);
	}

	/**
	 * @param timelineContentChangedListener 
	 * 			to add to {@link #timelineContentChangedListeners}.
	 */
	public void addTimelineContentChangedListener(TimelineContentChangedListenerInterface timelineContentChangedListener)
	{
		this.timelineContentChangedListeners.add(timelineContentChangedListener);
	}

	/**
	 * @param bufferStart
	 * 			is the changed Buffer start.
	 * @param bufferEnd
	 * 			is the changed Buffer end.
	 */
	public void notifyTimelineContentChangedListeners(long bufferStart, long bufferEnd)
	{
		//==========================================================================================
		for (TimelineContentChangedListenerInterface timelineContentChangedListener : this.timelineContentChangedListeners)
		{
			timelineContentChangedListener.notifyTimelineContentChanged(bufferStart, bufferEnd);
		}
		
		//==========================================================================================
	}

	/**
	 * Remove the given Input from given Timeline.
	 * 
	 * @param timeline
	 * 			is the Timeline.
	 * @param inputData
	 * 			is the InputData.
	 */
	public void removeInput(Timeline timeline, InputData inputData)
	{
		//==========================================================================================
		timeline.removeInput(inputData);
		
		//==========================================================================================
	}

	/**
	 * Update the given Input from given Timeline.
	 * 
	 * @param timeline
	 * 			is the Timeline.
	 * @param inputData
	 * 			is the InputData.
	 */
	public void updateInput(Timeline timeline, InputData inputData)
	{
		//==========================================================================================
		timeline.updateInput(inputData);
		
		//==========================================================================================
	}
	
	/**
	 * Notify Sound Buffer is now completely filled.
	 */
	public void notifyBufferCompletelyFilled()
	{
		//==========================================================================================
		for (Timeline timeline : this.mainGeneratorTimelines.values())
		{
			if (timeline.getBufferIsDirty() == true)
			{
				// Calculate min and max values of timeline.
				timeline.calcMinMaxValues();
				
				// Reset dirty flag of timeline.
				timeline.setBufferIsDirty(false);
			}
		}
		
		//==========================================================================================
	}

	/**
	 * @param firstTimelinePos
	 * 			is the first Position of timelines.
	 * @param secondTimelinePos
	 * 			is the second Position of timelines.
	 */
	public void switchTracksByPos(int firstTimelinePos, int secondTimelinePos)
	{
		//==========================================================================================
		this.mainModulGeneratorTypeData.switchTracksByPos(firstTimelinePos,
		                                                  secondTimelinePos);
		
		//==========================================================================================
	}

	/**
	 * XXX Because of a memory leake clear timelines explicitely.
	 */
	public void clearTimelines()
	{
		//==========================================================================================
		Iterator<Timeline> timelinesIterator = this.getTimelinesIterator();
		
		while (timelinesIterator.hasNext())
		{
			Timeline timeline = timelinesIterator.next();
			
			timeline.clearTimeline();
		}
		
		this.mainGeneratorTimelines.clear();
		
		this.timelineContentChangedListeners.clear();
		
		//==========================================================================================
	}
	
}
