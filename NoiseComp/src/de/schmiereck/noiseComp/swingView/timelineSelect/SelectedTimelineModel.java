/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.InputData;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.appModel.InputEntriesModel;
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
	private List<DoChangeTimelinesPositionListenerInterface> doChangeTimelinesPositionListeners = new Vector<DoChangeTimelinesPositionListenerInterface>();

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
			List<InputEntryModel> inputEntryModels = this.inputEntriesModel.getInputEntryModels();
			
			inputEntryModels.clear();
			
			Timeline timeline = this.selectedTimelineSelectEntryModel.getTimeline();
			
			Iterator<InputData> inputsIterator = timeline.getInputsIterator();
			
			while (inputsIterator.hasNext())
			{
				final InputData inputData = inputsIterator.next();
				
				InputEntryModel inputEntryModel = new InputEntryModel(inputData);
				
				inputEntryModels.add(inputEntryModel);
			}
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
}
