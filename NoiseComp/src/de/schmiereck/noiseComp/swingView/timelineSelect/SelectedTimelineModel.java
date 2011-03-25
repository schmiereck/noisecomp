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
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.InputPosEntriesModel;
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
	
	/**
	 * {@link #highlightedInputEntry} changed listeners.
	 */
	private final ModelPropertyChangedNotifier highlightedInputEntryChangedNotifier = new ModelPropertyChangedNotifier();
	
	//==============================================================================================
	/**
	 * Selected Input-Entry Model.
	 */
	private InputEntryModel selectedInputEntry = null;
	
	/**
	 * {@link #selectedInputEntry} changed listeners.
	 */
	private final ModelPropertyChangedNotifier selectedInputEntryChangedNotifier = new ModelPropertyChangedNotifier();
	
	//==============================================================================================
	/**
	 * Positions of Input-Entries and Input-Groups.<br/>
	 * <code>null</code> if no timeline is selected.
	 */
	private InputPosEntriesModel inputPosEntriesModel = null;
	
	//==============================================================================================
	/**
	 * Input-Entry Target Model.<br/>
	 * <code>null</code> if no input target selected.
	 */
	private InputEntryTargetModel inputEntryTargetModel = null;
	
	/**
	 * {@link #inputEntryTargetModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier inputEntryTargetModelChangedNotifier = new ModelPropertyChangedNotifier();
	
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
	 * Called if selected timeline is changed.
	 * 
	 * @param selectedTimelineSelectEntryModel 
	 * 			to set {@link #selectedTimelineSelectEntryModel}.
	 */
	public void setSelectedTimelineSelectEntryModel(TimelineSelectEntryModel selectedTimelineSelectEntryModel)
	{
		//==========================================================================================
		this.selectedTimelineSelectEntryModel = selectedTimelineSelectEntryModel;
		
		//------------------------------------------------------------------------------------------
		InputPosEntriesModel inputPosEntriesModel;
		
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
			inputPosEntriesModel =
				this.calcInputPosEntries(this);
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		else
		{
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			inputPosEntriesModel = null;
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		}
		
		this.inputPosEntriesModel = inputPosEntriesModel;
		
		//------------------------------------------------------------------------------------------
		this.setHighlightedInputEntry(null);
		this.setSelectedInputEntry(null);
		
		//------------------------------------------------------------------------------------------
		this.selectedTimelineChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model. 
	 * @return
	 * 			the root input pos entries model.
	 */
	private InputPosEntriesModel calcInputPosEntries(final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
		InputPosEntriesModel retInputPosEntriesModel = new InputPosEntriesModel(null,
		                                                                        null,
		                                                                        null);
		
		final InputEntriesModel inputEntriesModel = selectedTimelineModel.getInputEntriesModel();
		
//		List<InputEntryModel> inputEntryModels = inputEntriesModel.getInputEntryModels();
		final List<InputEntryGroupModel> inputEntryGroups = inputEntriesModel.getInputEntryGroups();
		
		{
			for (InputEntryGroupModel inputEntryGroupModel : inputEntryGroups)
			{
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				InputPosEntriesModel groupInputPosEntriesModel = new InputPosEntriesModel(retInputPosEntriesModel,
				                                                                          inputEntryGroupModel,
				                                                                          null);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Add Input-Entries:
				
				List<InputEntryModel> inputEntries = inputEntryGroupModel.getInputEntries();
				
				for (InputEntryModel inputEntryModel : inputEntries)
				{
					InputPosEntriesModel inputPosEntry = new InputPosEntriesModel(groupInputPosEntriesModel,
					                                                              inputEntryGroupModel,
					                                                              inputEntryModel);
					
					groupInputPosEntriesModel.addInputPosEntry(inputPosEntry);
				}

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Add Add-Input Entry to Group:
				{
					final InputEntryModel inputEntryModel = new InputEntryModel(null);

					InputPosEntriesModel inputPosEntry = new InputPosEntriesModel(groupInputPosEntriesModel,
					                                                              inputEntryGroupModel,
					                                                              inputEntryModel);
					
					groupInputPosEntriesModel.addInputPosEntry(inputPosEntry);
				}
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				retInputPosEntriesModel.addInputPosEntry(groupInputPosEntriesModel);
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			}
		}
		//==========================================================================================
		return retInputPosEntriesModel;
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
		if (this.highlightedInputEntry != highlightedInputEntry)
		{
			this.highlightedInputEntry = highlightedInputEntry;
			
			this.highlightedInputEntryChangedNotifier.notifyModelPropertyChangedListeners();
		}
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
		if (this.selectedInputEntry != selectedInputEntry)
		{
			this.selectedInputEntry = selectedInputEntry;
			
			this.selectedInputEntryChangedNotifier.notifyModelPropertyChangedListeners();
		}
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

	/**
	 * @return 
	 * 			returns the {@link #inputPosEntriesModel}.
	 */
	public InputPosEntriesModel getInputPosEntriesModel()
	{
		return this.inputPosEntriesModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntryTargetModel}.
	 */
	public InputEntryTargetModel getInputEntryTargetModel()
	{
		return this.inputEntryTargetModel;
	}

	/**
	 * @param inputEntryTargetModel 
	 * 			to set {@link #inputEntryTargetModel}.
	 */
	public void setInputEntryTargetModel(InputEntryTargetModel inputEntryTargetModel)
	{
		if (this.inputEntryTargetModel != inputEntryTargetModel)
		{
			this.inputEntryTargetModel = inputEntryTargetModel;
			
			this.inputEntryTargetModelChangedNotifier.notifyModelPropertyChangedListeners();
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #inputEntryTargetModelChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getInputEntryTargetModelChangedNotifier()
	{
		return this.inputEntryTargetModelChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #highlightedInputEntryChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getHighlightedInputEntryChangedNotifier()
	{
		return this.highlightedInputEntryChangedNotifier;
	}

}
