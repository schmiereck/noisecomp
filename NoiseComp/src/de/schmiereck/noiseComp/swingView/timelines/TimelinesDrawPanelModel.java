/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Timeline Draw-Panel Model.
 * </p>
 * 
 * @author smk
 * @version <p>06.09.2010:	created, smk</p>
 */
public class TimelinesDrawPanelModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Timeline-Generator Models.
	 */
	private List<TimelineGeneratorModel> timelineGeneratorModels = new Vector<TimelineGeneratorModel>();

	/**
	 * {@link #timelineGeneratorModels} changed (insert or remove) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Selected Timeline Generator Model.
	 */
	private TimelineGeneratorModel selectedTimelineGeneratorModel = null;
	
	/**
	 * Selected Timeline Changed Listeners.
	 */
	private List<SelectedTimelineChangedListenerInterface> selectedTimelineChangedListeners = new Vector<SelectedTimelineChangedListenerInterface>();
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModels}.
	 */
	public List<TimelineGeneratorModel> getTimelineGeneratorModels()
	{
		return this.timelineGeneratorModels;
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		this.timelineGeneratorModels.clear();
		
		// TODO notify timelineGeneratorModels changed
	}

	/**
	 * @param timelineGeneratorModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelineGeneratorModels.add(timelineGeneratorModel);
		
		// TODO notify timelineGeneratorModels changed
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineGeneratorModel}.
	 */
	public TimelineGeneratorModel getSelectedTimelineGeneratorModel()
	{
		return this.selectedTimelineGeneratorModel;
	}

	/**
	 * @param selectedTimelineGeneratorModel 
	 * 			to set {@link #selectedTimelineGeneratorModel}.
	 */
	public void setSelectedTimelineGeneratorModel(TimelineGeneratorModel selectedTimelineGeneratorModel)
	{
		this.selectedTimelineGeneratorModel = selectedTimelineGeneratorModel;
		
		this.notifySelectedTimelineChangedListeners();
	}

	/**
	 * Notify the {@link #selectedTimelineChangedListeners}.
	 */
	public void notifySelectedTimelineChangedListeners()
	{
		for (SelectedTimelineChangedListenerInterface selectedTimelineChangedListener : this.selectedTimelineChangedListeners)
		{
			selectedTimelineChangedListener.selectedTimelineChanged();
		}
	}

	/**
	 * @param selectedTimelineChangedListener 
	 * 			to add to {@link #selectedTimelineChangedListeners}.
	 */
	public void addSelectedTimelineChangedListener(SelectedTimelineChangedListenerInterface selectedTimelineChangedListener)
	{
		this.selectedTimelineChangedListeners.add(selectedTimelineChangedListener);
	}

	/**
	 * @param timelineGeneratorModel
	 * 			is the Generator.
	 */
	public void removeTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelineGeneratorModels.remove(timelineGeneratorModel);
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTimelineGeneratorModelsChangedNotifier()
	{
		return this.timelineGeneratorModelsChangedNotifier;
	}
}
