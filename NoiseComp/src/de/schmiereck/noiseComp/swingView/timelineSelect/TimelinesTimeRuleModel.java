/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.ModulGeneratorTypeData.TicksPer;
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
//	/**
//	 * Pixel increment between the unit markers.
//	 */
//	private int				units = 2;
//	
//	/**
//	 * Pixel increment between the markers.
//	 */
//	private int				increment = 1;

	private float zoomX = 1.0F;
	
	/**
	 * {@link #units} changed listeners.
	 */
	private final ModelPropertyChangedNotifier zoomXChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * {@link #ticksCount} per value.
	 */
	private TicksPer ticksPer = TicksPer.Seconds;
	
	/**
	 * Count of ticks per {@link #ticksPer}.
	 */
	private Float ticksCount = 1.0F;
	

	/**
	 * {@link #ticksPer} or {@link #ticksCount} changed listeners.
	 */
	private final ModelPropertyChangedNotifier ticksChangedNotifier = new ModelPropertyChangedNotifier();
	
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
	 * 			returns the {@link #zoomX}.
	 */
	public float getZoomX()
	{
		return this.zoomX;
	}

	/**
	 * @param units 
	 * 			to set {@link #zoomX}.
	 */
	public void setZoomX(float zoomX)
	{
//		this.units = units;
//		this.increment = this.units / 2;
//		
//		this.zoomXChangedNotifier.notifyModelPropertyChangedListeners();
		
		this.zoomX = zoomX;
		
		this.zoomXChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #zoomXChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getZoomXChangedNotifier()
	{
		return this.zoomXChangedNotifier;
	}

//	/**
//	 * @return 
//	 * 			returns the {@link #increment}.
//	 */
//	public int getIncrement()
//	{
//		return this.increment;
//	}

	/**
	 * @param ticksPer
	 * 			is the Tick Unit.
	 * @param ticksCount
	 * 			are the ticks.
	 */
	public void notifyTicksChangedNotifier(TicksPer ticksPer, Float ticksCount)
	{
		//==========================================================================================
		this.ticksPer = ticksPer;

		this.ticksCount = ticksCount;
		
		this.ticksChangedNotifier.notifyModelPropertyChangedListeners();
		
		//this.timelinesTimeRuleModel.setUnits((int)zoomX);
		
		//this.timelinesTimeRuleView.repaint();

		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTicksChangedNotifier()
	{
		return this.ticksChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksPer}.
	 */
	public TicksPer getTicksPer()
	{
		return this.ticksPer;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksCount}.
	 */
	public Float getTicksCount()
	{
		return this.ticksCount;
	}
}
