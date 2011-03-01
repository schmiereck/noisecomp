/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.DoChangeTimelinesPositionListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule.TimelinesGeneratorsRuleModel;

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
}
