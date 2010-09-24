/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Timelines-Time-Rule Model.
 * </p>
 * 
 * @author smk
 * @version <p>24.09.2010:	created, smk</p>
 */
public class TimelinesTimeRuleModel
{
	//**********************************************************************************************
	// Fields:

	
	//----------------------------------------------------------------------------------------------
//	private int generatorSizeY = 0;;
	private TimelinesScrollPanelModel timelinesScrollPanelModel = null;

	//----------------------------------------------------------------------------------------------
	/**
	 * Timeline-Generator Models.
	 */
	private List<TimelineGeneratorModel> timelineGeneratorModels = new Vector<TimelineGeneratorModel>();

	/**
	 * {@link #timelineGeneratorModels} changed (insert or remove) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelinesTimeRuleModel()
	{
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

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModels}.
	 */
	public List<TimelineGeneratorModel> getTimelineGeneratorModels()
	{
		return this.timelineGeneratorModels;
	}

	/**
	 * @param timelineGeneratorModels 
	 * 			to set {@link #timelineGeneratorModels}.
	 */
	public void setTimelineGeneratorModels(List<TimelineGeneratorModel> timelineGeneratorModels)
	{
		this.timelineGeneratorModels = timelineGeneratorModels;
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}
}
