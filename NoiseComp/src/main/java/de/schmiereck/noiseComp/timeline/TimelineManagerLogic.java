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

import de.schmiereck.dataTools.VectorHash;
import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;
import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData;
import de.schmiereck.noiseComp.generator.signal.OutputGenerator;
import de.schmiereck.noiseComp.soundSource.SoundSourceData;
import de.schmiereck.noiseComp.soundSource.SoundSourceLogic;
import de.schmiereck.noiseComp.swingView.CompareUtils;

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

	private final SoundSourceLogic soundSourceLogic;

	/**
	 * Main ModuleGenerator-Type Data.
	 */
	private final ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData;
	
	/**
	 * Main-Module Generator Timelines.
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
	 * @param mainModuleGeneratorTypeData
	 * 			is the mainModuleGeneratorTypeData.
	 */
	public TimelineManagerLogic(SoundSourceLogic soundSourceLogic, ModuleGeneratorTypeInfoData mainModuleGeneratorTypeData)
	{
		this.soundSourceLogic = soundSourceLogic;
		this.mainModuleGeneratorTypeData = mainModuleGeneratorTypeData;
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
	public Timeline createTimeline(final SoundSourceData soundSourceData, final Generator generator) {
		//==========================================================================================
		//SoundSourceLogic soundSourceLogic = SwingMain.getSoundSourceLogic();
		
		//==========================================================================================
		// Search or create Main-Module Timeline:
		final Timeline timeline = this.makeMainTimeline(generator);
		
		//------------------------------------------------------------------------------------------
//		if (OutputGenerator.class.getName().equals(generator.getGeneratorTypeData().getGeneratorTypeClassName())
		if (generator instanceof OutputGenerator outputGenerator) {
            // Update mainModuleGeneratorTypeData.
			this.mainModuleGeneratorTypeData.setOutputGenerator(outputGenerator);

			// Update SoundSourceLogic.
			soundSourceLogic.setOutputGeneratorTimeline(soundSourceData, timeline);
		}
		//------------------------------------------------------------------------------------------
		this.createTimelineInputs(soundSourceData, generator, timeline);
		
		//------------------------------------------------------------------------------------------
		// Notify Module
		this.updateModuleTimelines(soundSourceData, timeline);

		//==========================================================================================
		return timeline;
	}

	/**
	 * @param generator
	 * 			is the generator
	 * @param timeline
	 * 			is the timeline.
	 */
	private void createTimelineInputs(final SoundSourceData soundSourceData, Generator generator,
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
						this.addInputTimeline(soundSourceData, timeline, inputData, inputGenerator);
				}
			}
		}
		
		// ModuleGenerator?
		if ((generator instanceof ModuleGenerator))
		{
			ModuleGenerator moduleGenerator = (ModuleGenerator)generator;
			
			ModuleGeneratorTypeInfoData moduleGeneratorTypeData = (ModuleGeneratorTypeInfoData)moduleGenerator.getGeneratorTypeData();
			
			OutputGenerator outputGenerator = moduleGeneratorTypeData.getOutputGenerator();
			
			this.createSubTimeline(soundSourceData, timeline, outputGenerator);
			//this.createSubTimelineInputs(inputGenerator, timeline);
		}
		//==========================================================================================
	}

	/**
	 * @param moduleimeline
	 * 			is the ModuleTimeline.
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.
	 */
	private Timeline createSubTimeline(final SoundSourceData soundSourceData, Timeline moduleimeline, Generator generator)
	{
		//==========================================================================================
		// Search or create Sub-Module Timeline for given ModuleTimeline:
		
		Timeline subTimeline = this.makeSubTimeline(moduleimeline, generator);
		
		//------------------------------------------------------------------------------------------
		this.createSubTimelineInputs(soundSourceData, moduleimeline, generator);
		
		//==========================================================================================
		return subTimeline;
	}

	/**
	 * @param moduleimeline
	 * 			is the ModuleTimeline.
	 * @param generator
	 * 			is the generator
	 */
	private void createSubTimelineInputs(final SoundSourceData soundSourceData, Timeline moduleimeline,
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
					Timeline inputTimeline = this.createSubTimeline(soundSourceData, moduleimeline, inputGenerator);
					
					// ModuleGenerator?
					if ((inputGenerator instanceof ModuleGenerator))
					{
						ModuleGenerator moduleGenerator = (ModuleGenerator)inputGenerator;
						
						ModuleGeneratorTypeInfoData moduleGeneratorTypeData = (ModuleGeneratorTypeInfoData)moduleGenerator.getGeneratorTypeData();
						
						OutputGenerator outputGenerator = moduleGeneratorTypeData.getOutputGenerator();
						
						this.createSubTimeline(soundSourceData, inputTimeline, outputGenerator);
					}
					//------------------------------------------------------------------------------
					this.addInputSubTimeline(soundSourceData, moduleimeline, inputTimeline, inputData, generator);
					
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
	private Timeline addInputTimeline(final SoundSourceData soundSourceData, Timeline timeline, InputData inputData, Generator inputGenerator)
	{
		Timeline inputTimeline = this.mainGeneratorTimelines.get(inputGenerator);
		
		if (inputTimeline != null)
		{
			timeline.addInputTimeline(inputData, inputTimeline);
			
			inputTimeline.addOutputTimeline(soundSourceData, inputData, timeline);
		}
		
		return inputTimeline;
	}
	
	/**
	 * Add the given Input Data for the given input generator
	 * to the given timeline
	 * if the coresponding inputTimeline for the given input generator is managed in {@link #mainGeneratorTimelines}.
	 * 
	 * @param moduleimeline
	 * 			is the ModuleTimeline.
	 * @param timeline
	 * 			is the timeline.
	 * @param inputData
	 * 			is the Input Data.
	 * @param inputGenerator
	 * 			is the input generator.
	 */
	private void addInputSubTimeline(final SoundSourceData soundSourceData, Timeline moduleimeline, Timeline timeline, InputData inputData, Generator inputGenerator)
	{
		Timeline inputTimeline = moduleimeline.getSubGeneratorTimeline(inputGenerator);
		
		if (inputTimeline != null)
		{
//			timeline.addInputTimeline(inputData, inputTimeline);
//			
//			inputTimeline.addOutputTimeline(inputData, timeline);

			inputTimeline.addInputTimeline(inputData, timeline);
			
			timeline.addOutputTimeline(soundSourceData, inputData, inputTimeline);
		}
	}

	/**
	 * Search or create Main-Module Timeline.
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
	 * Search or create Sub-Module Timeline.
	 * 
	 * @param moduleimeline
	 * 			is the ModuleTimeline.
	 * @param generator
	 * 			is the Generator.
	 * @return
	 * 			the Timeline.
	 */
	private Timeline makeSubTimeline(Timeline moduleimeline, Generator generator)
	{
		//==========================================================================================
		Timeline timeline;
		
		timeline = moduleimeline.getSubGeneratorTimeline(generator);
		
		if (timeline == null)
		{
			timeline = new Timeline(moduleimeline);
		
			timeline.setGenerator(generator);
			
			moduleimeline.addSubGeneratorTimeline(generator, timeline);
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
//			// TODO Update mainModuleGeneratorTypeData.
//			//mainModuleGeneratorTypeData.se
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
	public void updateStartTimePos(final SoundSourceData soundSourceData, Timeline updatedTimeline, float generatorStartTimePos)
	{
		//==========================================================================================
		float endTimePos = updatedTimeline.getGeneratorEndTimePos();
		
		this.updateTimePos(soundSourceData, updatedTimeline, generatorStartTimePos, endTimePos);

		//==========================================================================================
	}

	/**
	 * @param updatedTimeline
	 * 			is the timeline.
	 * @param generatorEndTimePos
	 * 			is the generator EndTimePos.
	 */
	public void updateEndTimePos(final SoundSourceData soundSourceData, Timeline updatedTimeline, float generatorEndTimePos)
	{
		//==========================================================================================
		float startTimePos = updatedTimeline.getGeneratorStartTimePos();
		
		this.updateTimePos(soundSourceData, updatedTimeline, startTimePos, generatorEndTimePos);
		
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
	public void updateTimePos(final SoundSourceData soundSourceData, Timeline updatedTimeline, float generatorStartTimePos, float generatorEndTimePos)
	{
		//==========================================================================================
		updatedTimeline.setTimePos(soundSourceData, generatorStartTimePos, generatorEndTimePos);

		//------------------------------------------------------------------------------------------
		// Notify Module
		{
			this.updateModuleTimelines(soundSourceData, updatedTimeline);
		}
		//------------------------------------------------------------------------------------------
		// Update all output Module-Timelines:
		{
			Map<InputData, Timeline> outputInputTimelines = updatedTimeline.getOutputTimelines();
			Collection<Timeline> outputTimelines = outputInputTimelines.values();
			
			for (Timeline outputTimeline : outputTimelines)
			{
				this.updateModuleTimelines(soundSourceData, outputTimeline);
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
	 * @param generatorTypeInfoData
	 * 			is the generatorType Data.
	 * @param soundFrameRate
	 * 			is the frame rate.
	 * @param generatorName
	 * 			is the genrator name.
	 * @param generatorPos
	 * 			is the position of the generator in the list of generators.
	 * @return
	 * 			the generator.
	 */
	public Timeline createTimeline(final SoundSourceData soundSourceData, GeneratorTypeInfoData generatorTypeInfoData,
	                               Float soundFrameRate,
	                               String generatorName,
                                   int generatorPos)
	{
		//==========================================================================================
		Generator generator = generatorTypeInfoData.createGeneratorInstance(generatorName,
		                                                                soundFrameRate);

		this.mainModuleGeneratorTypeData.addGenerator(generatorPos,
		                                             generator);

		Timeline timeline = 
			this.createTimeline(soundSourceData, generator);

		//==========================================================================================
		return timeline;
	}

	/**
	 * @param removedTimeline
	 * 			is the timeline.
	 */
	public void removeTimeline(final SoundSourceData soundSourceData, Timeline removedTimeline)
	{
		//==========================================================================================
		// remove all inputs from all other timelines:
		
		for (Timeline timeline : this.mainGeneratorTimelines.values())
		{
			timeline.removeInputTimeline(soundSourceData, removedTimeline);
		}
		//------------------------------------------------------------------------------------------
		Generator timelineGenerator = removedTimeline.getGenerator();
		
		this.mainModuleGeneratorTypeData.removeGenerator(soundSourceData, timelineGenerator);
		
		this.mainGeneratorTimelines.remove(timelineGenerator);
		
		//==========================================================================================
	}

	/**
	 * Add input to the given New-Timeline connected to the given Input-Timeline and 
	 * the generator of this timeline.
	 * 
	 * @param newTimeline
	 * 			is the New-Timeline.
	 * @param inputTimeline
	 * 			is the Input-Timeline.<br/>
	 * 			<code>null</code> if not used.
	 * @param inputTypeData
	 * 			is the Input-Type Data.
	 * @param floatValue
	 * 			is the Input-Value.<br/>
	 * 			<code>null</code> if not used.
	 * @param stringValue
	 * 			is the Input-String-Value.<br/>
	 * 			<code>null</code> if not used.
	 * @param inputModuleInputTypeData
	 * 			is the Input-ModuleInput-Type Data.<br/>
	 * 			<code>null</code> if not used.
	 * @return 
	 * 			the new created and added {@link InputData}-Object.
	 */
	public InputData addGeneratorInput(final SoundSourceData soundSourceData, final Timeline newTimeline,
									   final Timeline inputTimeline,
									   final InputTypeData inputTypeData,
									   final Float floatValue, final String stringValue,
									   final InputTypeData inputModuleInputTypeData) {
		//==========================================================================================
		InputData inputData;
		
		final Generator newGenerator = newTimeline.getGenerator();
		
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
			newGenerator.addGeneratorInput(soundSourceData, inputGenerator,
			                               inputTypeData, 
			                               floatValue, stringValue,
			                               inputModuleInputTypeData);
		
		//------------------------------------------------------------------------------------------
		this.addInputTimeline(soundSourceData, newTimeline, inputData, inputGenerator);
		
		//------------------------------------------------------------------------------------------
		newTimeline.generatorChanged(soundSourceData);
		
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
	 * @param moduleInputTypeData
	 */
	public void updateInput(final SoundSourceData soundSourceData,
							Timeline updatedTimeline,
	                        InputData inputData, 
	                        Timeline inputTimeline,
							InputTypeData inputTypeData, 
							Float floatValue, String stringValue,
							InputTypeData moduleInputTypeData)
	{
		//==========================================================================================
		// Update Timeline-Input for given Input-Data and Input-Generator:
		{
			Timeline oldInputTimeline = updatedTimeline.updateInput(soundSourceData, inputData, inputTimeline);
			
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
					oldInputTimeline.removeOutputTimeline(soundSourceData, inputData);
				}
				
				if (inputTimeline != null)
				{
					inputTimeline.addOutputTimeline(soundSourceData, inputData, updatedTimeline);
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
				
				inputData.setInputGenerator(soundSourceData, inputGenerator);
			}
		}
		//------------------------------------------------------------------------------------------
		// Update Module
		{
			this.updateModuleTimelines(soundSourceData, updatedTimeline);
		}
		//------------------------------------------------------------------------------------------
		boolean generatorChanged;
		
		if ((CompareUtils.compareWithNull(inputData.getInputValue(), floatValue) == false) ||
			(CompareUtils.compareWithNull(inputData.getInputStringValue(), stringValue) == false))
		{
			inputData.setInputValue(soundSourceData, floatValue, stringValue);
			
			generatorChanged = true;
		}
		else
		{
			generatorChanged = false;
		}
		
		if (inputData.getInputModuleInputTypeData() != moduleInputTypeData)
		{
			inputData.setInputModuleInputTypeData(soundSourceData, moduleInputTypeData);
			
			generatorChanged = true;
		}
		
		if (generatorChanged == true)
		{
			updatedTimeline.generatorChanged(soundSourceData);
		}
		//==========================================================================================
	}

	/**
	 * @param updatedTimeline
	 * 			is the updated timeline.
	 */
	private void updateModuleTimelines(final SoundSourceData soundSourceData, final Timeline updatedTimeline) {
		//==========================================================================================
		Generator generator = updatedTimeline.getGenerator();
		
		if (generator != null) {
			if (generator instanceof ModuleGenerator) {
				// Notify module timelines.

				Collection<Timeline> subGeneratorTimelines = updatedTimeline.getSubGeneratorTimelines();
				
				for (Timeline subGeneratorTimeline : subGeneratorTimelines) {
					this.updateModuleTimelines(soundSourceData, subGeneratorTimeline);
					
					subGeneratorTimeline.generatorChanged(soundSourceData);
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
	public void removeInput(final SoundSourceData soundSourceData, Timeline timeline, InputData inputData)
	{
		//==========================================================================================
		timeline.removeInput(soundSourceData, inputData);
		
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
	public void updateInput(final SoundSourceData soundSourceData, Timeline timeline, InputData inputData) {
		//==========================================================================================
		timeline.updateInput(soundSourceData, inputData);
		
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
		this.mainModuleGeneratorTypeData.switchTracksByPos(firstTimelinePos,
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

	/**
	 * @param selectedTimeline
	 * 			is the Timeline.
	 * @param selectedInputData
	 * 			is the selected InputData.
	 * @param targetInputData
	 * 			is the target InputData.
	 */
	public void changeInputPositions(Timeline selectedTimeline, 
	                                 InputData selectedInputData, 
	                                 InputData targetInputData)
	{
		//==========================================================================================
		VectorHash<InputData, Timeline> inputTimelines = selectedTimeline.getInputTimelines();
		
		inputTimelines.changePositions(selectedInputData, 
		                               targetInputData);
		
		//------------------------------------------------------------------------------------------
		Generator generator = selectedTimeline.getGenerator();
		
		Vector<InputData> inputs = generator.getInputs();
		
		int selectedInputPos = inputs.indexOf(selectedInputData);
		int targetInputPos = inputs.indexOf(targetInputData);
		
		inputs.set(targetInputPos, selectedInputData);
		inputs.set(selectedInputPos, targetInputData);
		
		//==========================================================================================
	}
	
}
