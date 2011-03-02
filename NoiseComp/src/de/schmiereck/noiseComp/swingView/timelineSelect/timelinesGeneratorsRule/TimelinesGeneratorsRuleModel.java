/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesGeneratorsRule;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesScrollPanel.TimelinesScrollPanelModel;

/**
 * <p>
 * 	Timeline-Generators-Rule Model.
 * </p>
 * 
 * @author smk
 * @version <p>23.09.2010:	created, smk</p>
 */
public class TimelinesGeneratorsRuleModel
{
	//**********************************************************************************************
	// Fields:
	
	//----------------------------------------------------------------------------------------------
//	private int generatorSizeY = 0;;
	private TimelinesScrollPanelModel timelinesScrollPanelModel = null;

	//----------------------------------------------------------------------------------------------
//	/**
//	 * Timeline-Generator Models.
//	 */
//	private List<TimelineSelectEntryModel> timelineSelectEntryModels = new Vector<TimelineSelectEntryModel>();
//
//	/**
//	 * {@link #timelineSelectEntryModels} changed (insert or remove) listeners.
//	 */
//	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * Timeline-Select-Entries Model.
	 */
	private final TimelineSelectEntriesModel timelineSelectEntriesModel;
	
	//----------------------------------------------------------------------------------------------
//	/**
//	 * Selected Timeline Generator Model.
//	 */
//	private TimelineSelectEntryModel selectedTimelineSelectEntryModel = null;
//	
//	/**
//	 * {@link #selectedTimelineSelectEntryModel} changed listeners.
//	 */
//	private final ModelPropertyChangedNotifier selectedTimelineChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * Selected-Timeline Model.
	 */
	private final SelectedTimelineModel selectedTimelineModel;
	
	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineModel}.
	 */
	public SelectedTimelineModel getSelectedTimelineModel()
	{
		return this.selectedTimelineModel;
	}

	//----------------------------------------------------------------------------------------------
	/**
	 * Highlighted (mouse over) Timeline Generator Model.
	 */
	private TimelineSelectEntryModel highlightedTimelineSelectEntryModel = null;
	
	/**
	 * {@link #highlightedTimelineSelectEntryModel} changed listeners.
	 */
	@SuppressWarnings("unused")
	private final ModelPropertyChangedNotifier highlightedTimelineChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param timelineSelectEntriesModel
	 * 			are the Timeline-Select-Entries Model.
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 */
	public TimelinesGeneratorsRuleModel(final TimelineSelectEntriesModel timelineSelectEntriesModel,
	                                    final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
		this.timelineSelectEntriesModel = timelineSelectEntriesModel;
		this.selectedTimelineModel = selectedTimelineModel;
		
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #timelinesScrollPanelModel}.
	 */
	public TimelinesScrollPanelModel getTimelinesScrollPanelModel()
	{
		return this.timelinesScrollPanelModel;
	}

	/**
	 * @param timelinesScrollPanelModel 
	 * 			to set {@link #timelinesScrollPanelModel}.
	 */
	public void setTimelinesScrollPanelModel(TimelinesScrollPanelModel timelinesScrollPanelModel)
	{
		this.timelinesScrollPanelModel = timelinesScrollPanelModel;
	}

//	/**
//	 * @return 
//	 * 			returns the {@link #timelineSelectEntryModels}.
//	 */
//	public List<TimelineSelectEntryModel> getTimelineSelectEntryModels()
//	{
//		return this.timelineSelectEntryModels;
//	}
//
//	/**
//	 * @param timelineSelectEntryModels 
//	 * 			to set {@link #timelineSelectEntryModels}.
//	 */
//	public void setTimelineSelectEntryModels(List<TimelineSelectEntryModel> timelineSelectEntryModels)
//	{
//		this.timelineSelectEntryModels = timelineSelectEntryModels;
//		
//		// Notify listeners.
//		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
//	}

//	/**
//	 * @return 
//	 * 			returns the {@link #selectedTimelineSelectEntryModel}.
//	 */
//	public TimelineSelectEntryModel getSelectedTimelineSelectEntryModel()
//	{
//		return this.selectedTimelineSelectEntryModel;
//	}
//
//	/**
//	 * @param selectedTimelineSelectEntryModel 
//	 * 			to set {@link #selectedTimelineSelectEntryModel}.
//	 */
//	public void setSelectedTimelineSelectEntryModel(TimelineSelectEntryModel selectedTimelineSelectEntryModel)
//	{
//		//==========================================================================================
//		this.selectedTimelineSelectEntryModel = selectedTimelineSelectEntryModel;
//		
//		this.selectedTimelineChangedNotifier.notifyModelPropertyChangedListeners();
//		//==========================================================================================
//	}
//
//	/**
//	 * @return 
//	 * 			returns the {@link #selectedTimelineChangedNotifier}.
//	 */
//	public ModelPropertyChangedNotifier getSelectedTimelineChangedNotifier()
//	{
//		return this.selectedTimelineChangedNotifier;
//	}

	/**
	 * @return 
	 * 			returns the {@link #highlightedTimelineSelectEntryModel}.
	 */
	public TimelineSelectEntryModel getHighlightedTimelineSelectEntryModel()
	{
		return this.highlightedTimelineSelectEntryModel;
	}

	/**
	 * @param highlightedTimelineSelectEntryModel 
	 * 			to set {@link #highlightedTimelineSelectEntryModel}.
	 */
	public void setHighlightedTimelineSelectEntryModel(TimelineSelectEntryModel highlightedTimelineSelectEntryModel)
	{
		this.highlightedTimelineSelectEntryModel = highlightedTimelineSelectEntryModel;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntriesModel}.
	 */
	public TimelineSelectEntriesModel getTimelineSelectEntriesModel()
	{
		return this.timelineSelectEntriesModel;
	}
}
