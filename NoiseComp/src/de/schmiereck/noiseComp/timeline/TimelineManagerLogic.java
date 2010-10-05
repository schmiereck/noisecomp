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
		
		while (inputsIterator.hasNext())
		{
			InputData inputData = (InputData)inputsIterator.next();
			
			Generator inputGenerator = inputData.getInputGenerator();
			
			if (inputGenerator != null)
			{
				Timeline inputTimeline;
				
				// Modul-Generator without inputs?
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
		Timeline timeline = new Timeline();
		
		timeline.setGenerator(generator);
		return timeline;
	}
	
}
