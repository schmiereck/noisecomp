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
	private List<TimelineSelectEntryModel> timelineSelectEntryModels = new Vector<TimelineSelectEntryModel>();

	/**
	 * {@link #timelineSelectEntryModels} changed (insert or remove) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	private int				units = 1;
	private int				increment = 1;

	/**
	 * {@link #units} changed listeners.
	 */
	private final ModelPropertyChangedNotifier unitsChangedNotifier = new ModelPropertyChangedNotifier();
	
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
	 * 			returns the {@link #timelineSelectEntryModels}.
	 */
	public List<TimelineSelectEntryModel> getTimelineSelectEntryModels()
	{
		return this.timelineSelectEntryModels;
	}

	/**
	 * @param timelineSelectEntryModels 
	 * 			to set {@link #timelineSelectEntryModels}.
	 */
	public void setTimelineSelectEntryModels(List<TimelineSelectEntryModel> timelineSelectEntryModels)
	{
		this.timelineSelectEntryModels = timelineSelectEntryModels;
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #units}.
	 */
	public int getUnits()
	{
		return this.units;
	}

	/**
	 * @param units 
	 * 			to set {@link #units}.
	 */
	public void setUnits(int units)
	{
		this.units = units;
		this.increment = this.units / 2;
		
		this.unitsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #unitsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getUnitsChangedNotifier()
	{
		return this.unitsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #increment}.
	 */
	public int getIncrement()
	{
		return this.increment;
	}
}
