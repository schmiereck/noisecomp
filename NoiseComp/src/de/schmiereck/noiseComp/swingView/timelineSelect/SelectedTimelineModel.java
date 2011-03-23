/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.Generator;
import de.schmiereck.noiseComp.generator.GeneratorTypeData;
import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.generator.InputTypeData;
import de.schmiereck.noiseComp.generator.InputTypesData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryGroupModel;
import de.schmiereck.noiseComp.swingView.appModel.InputEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.DoChangeTimelinesPositionListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleModel;
import de.schmiereck.noiseComp.timeline.Timeline;

/**
 * <p>
 * 	Selected-Timeline Model.
 * </p>
 * <p>
 * 	Used from {@link TimelinesDrawPanelModel} and {@link TimelinesGeneratorsRuleModel}.
 * </p>
 * 
 * @author smk
 * @version <p>25.02.2011:	created, smk</p>
 */
public class SelectedTimelineModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Selected Timeline Generator Model.
	 */
	private TimelineSelectEntryModel selectedTimelineSelectEntryModel = null;
	
	/**
	 * {@link #selectedTimelineSelectEntryModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier selectedTimelineChangedNotifier = new ModelPropertyChangedNotifier();

	//==============================================================================================
	/**
	 * Do Timeline Selected Listeners.
	 */
	private final List<DoChangeTimelinesPositionListenerInterface> doChangeTimelinesPositionListeners = new Vector<DoChangeTimelinesPositionListenerInterface>();

	//==============================================================================================
	/**
	 * Input-Entries Model.
	 */
	private final InputEntriesModel inputEntriesModel = new InputEntriesModel();
	
	//==============================================================================================
	/**
	 * Highlighted Input-Entry Model.
	 */
	private InputEntryModel highlightedInputEntry = null;
	
	//==============================================================================================
	/**
	 * Selected Input-Entry Model.
	 */
	private InputEntryModel selectedInputEntry = null;
	
	/**
	 * {@link #selectedInputEntry} changed listeners.
	 */
	private final ModelPropertyChangedNotifier selectedInputEntryChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineSelectEntryModel}.
	 */
	public TimelineSelectEntryModel getSelectedTimelineSelectEntryModel()
	{
		return this.selectedTimelineSelectEntryModel;
	}

	/**
	 * @param selectedTimelineSelectEntryModel 
	 * 			to set {@link #selectedTimelineSelectEntryModel}.
	 */
	public void setSelectedTimelineSelectEntryModel(TimelineSelectEntryModel selectedTimelineSelectEntryModel)
	{
		//==========================================================================================
		this.selectedTimelineSelectEntryModel = selectedTimelineSelectEntryModel;
		
		//------------------------------------------------------------------------------------------
		if (this.selectedTimelineSelectEntryModel != null)
		{
			this.inputEntriesModel.clear();

			Timeline timeline = this.selectedTimelineSelectEntryModel.getTimeline();
			
			if (timeline != null)
			{
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				Generator generator = timeline.getGenerator();
				
				GeneratorTypeData generatorTypeData = generator.getGeneratorTypeData();
				
//				List<InputEntryModel> inputEntryModels = this.inputEntriesModel.getInputEntryModels();
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Add Input-Groups:
				
				InputTypesData inputTypesData = generatorTypeData.getInputTypesData();
				
				for (int inputTypePos = 0; inputTypePos < inputTypesData.getInputTypesSize(); inputTypePos++)
				{
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					InputTypeData inputTypeData = inputTypesData.getInputTypeDataByPos(inputTypePos);
					
					InputEntryGroupModel inputEntryGroupModel = new InputEntryGroupModel(inputTypeData);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					// Add Inputs:
					
					Iterator<InputData> inputsIterator = timeline.getInputsIterator();
					
					if (inputsIterator != null)
					{
						while (inputsIterator.hasNext())
						{
							final InputData inputData = inputsIterator.next();
							
							InputTypeData inputTypeData2 = inputData.getInputTypeData();
							
							if (inputTypeData == inputTypeData2)
							{
								InputEntryModel inputEntryModel = new InputEntryModel(inputData);
								
//								inputEntryModels.add(inputEntryModel);
								
								inputEntryGroupModel.getInputEntries().add(inputEntryModel);
							}
						}
					}
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
					this.inputEntriesModel.getInputEntryGroups().add(inputEntryGroupModel);
					
					// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				}
			}
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		//------------------------------------------------------------------------------------------
		this.setHighlightedInputEntry(null);
		
		//------------------------------------------------------------------------------------------
		this.selectedTimelineChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getSelectedTimelineChangedNotifier()
	{
		return this.selectedTimelineChangedNotifier;
	}

	/**
	 * @param doChangeTimelinesPositionListener 
	 * 			to add to {@link #doChangeTimelinesPositionListeners}.
	 */
	public void addChangeTimelinesPositionListeners(DoChangeTimelinesPositionListenerInterface doChangeTimelinesPositionListener)
	{
		this.doChangeTimelinesPositionListeners.add(doChangeTimelinesPositionListener);
	}

	/**
	 * Notify the {@link #doChangeTimelinesPositionListeners}.
	 */
	public void notifyDoChangeTimelinesPositionListeners(TimelineSelectEntryModel selectedTimelineSelectEntryModel,
	                                                     TimelineSelectEntryModel newTimelineSelectEntryModel)
	{
		//==========================================================================================
		for (DoChangeTimelinesPositionListenerInterface doTimelineSelectedListener : this.doChangeTimelinesPositionListeners)
		{
			doTimelineSelectedListener.changeTimelinesPosition(selectedTimelineSelectEntryModel,
			                                                   newTimelineSelectEntryModel);
		};
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntriesModel}.
	 */
	public InputEntriesModel getInputEntriesModel()
	{
		return this.inputEntriesModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #highlightedInputEntry}.
	 */
	public InputEntryModel getHighlightedInputEntry()
	{
		return this.highlightedInputEntry;
	}

	/**
	 * @param highlightedInputEntry 
	 * 			to set {@link #highlightedInputEntry}.
	 */
	public void setHighlightedInputEntry(InputEntryModel highlightedInputEntry)
	{
		this.highlightedInputEntry = highlightedInputEntry;
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedInputEntry}.
	 */
	public InputEntryModel getSelectedInputEntry()
	{
		return this.selectedInputEntry;
	}

	/**
	 * @param selectedInputEntry 
	 * 			to set {@link #selectedInputEntry}.
	 */
	public void setSelectedInputEntry(InputEntryModel selectedInputEntry)
	{
		this.selectedInputEntry = selectedInputEntry;
		
		this.selectedInputEntryChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedInputEntryChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getSelectedInputEntryChangedNotifier()
	{
		return this.selectedInputEntryChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #doChangeTimelinesPositionListeners}.
	 */
	public List<DoChangeTimelinesPositionListenerInterface> getDoChangeTimelinesPositionListeners()
	{
		return this.doChangeTimelinesPositionListeners;
	}

}
