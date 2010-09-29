/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

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
	
	//----------------------------------------------------------------------------------------------
	private int maxUnitIncrementX = 1;
	private int maxUnitIncrementY = 16;

	//----------------------------------------------------------------------------------------------
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
	
//	/**
//	 * Selected Timeline Changed Listeners.
//	 */
//	private List<SelectedTimelineChangedListenerInterface> selectedTimelineChangedListeners = new Vector<SelectedTimelineChangedListenerInterface>();
	/**
	 * {@link #selectedTimelineGeneratorModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier selectedTimelineChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	
//	/**
//	 * Do Timeline Selected Listeners.
//	 */
//	private List<DoTimelineSelectedListenerInterface> doTimelineSelectedListeners = new Vector<DoTimelineSelectedListenerInterface>();
	
	/**
	 * {@link #timelineGeneratorModels} positions changed.
	 * 
	 * @see #changeTimelinesPosition(int, int)
	 */
	private final ModelPropertyChangedNotifier changeTimelinesPositionChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Zoom Size X.
	 */
	private float zoomX;

	/**
	 * {@link #zoomX} changed listeners.
	 */
	private final ModelPropertyChangedNotifier zoomXChangedNotifier = new ModelPropertyChangedNotifier();
	
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
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * Do not use this directly, use {@link TimelinesDrawPanelController#addTimelineGeneratorModel(TimelineGeneratorModel)}
	 * because of registering change listeners for TimelineGeneratorModel changes.
	 * 
	 * @param timelineGeneratorModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineGeneratorModel(TimelineGeneratorModel timelineGeneratorModel)
	{
		this.timelineGeneratorModels.add(timelineGeneratorModel);
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
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
		
		this.selectedTimelineChangedNotifier.notifyModelPropertyChangedListeners();
	}
//
//	/**
//	 * Notify the {@link #selectedTimelineChangedListeners}.
//	 */
//	public void notifySelectedTimelineChangedListeners()
//	{
//		for (SelectedTimelineChangedListenerInterface selectedTimelineChangedListener : this.selectedTimelineChangedListeners)
//		{
//			selectedTimelineChangedListener.selectedTimelineChanged();
//		}
//	}
//	
//	/**
//	 * @param selectedTimelineChangedListener 
//	 * 			to add to {@link #selectedTimelineChangedListeners}.
//	 */
//	public void addSelectedTimelineChangedListener(SelectedTimelineChangedListenerInterface selectedTimelineChangedListener)
//	{
//		this.selectedTimelineChangedNotifier.add(selectedTimelineChangedListener);
//	}

	/**
	 * @return 
	 * 			returns the {@link #maxUnitIncrementX}.
	 */
	public int getMaxUnitIncrementX()
	{
		return this.maxUnitIncrementX;
	}

	/**
	 * @param maxUnitIncrementX 
	 * 			to set {@link #maxUnitIncrementX}.
	 */
	public void setMaxUnitIncrementX(int maxUnitIncrementX)
	{
		this.maxUnitIncrementX = maxUnitIncrementX;
	}

	/**
	 * @return 
	 * 			returns the {@link #maxUnitIncrementY}.
	 */
	public int getMaxUnitIncrementY()
	{
		return this.maxUnitIncrementY;
	}

	/**
	 * @param maxUnitIncrementY 
	 * 			to set {@link #maxUnitIncrementY}.
	 */
	public void setMaxUnitIncrementY(int maxUnitIncrementY)
	{
		this.maxUnitIncrementY = maxUnitIncrementY;
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
	 * @param firstTimelinePos
	 * 			is the first Timeline-Generator Poisition.
	 * @param secondTimelinePos
	 * 			is the second Timeline-Generator Poisition.
	 */
	public void changeTimelinesPosition(int firstTimelinePos, int secondTimelinePos)
	{
		TimelineGeneratorModel firstTimelineGeneratorModel = this.timelineGeneratorModels.get(firstTimelinePos);
		TimelineGeneratorModel secondTimelineGeneratorModel = this.timelineGeneratorModels.get(secondTimelinePos);
		
		this.timelineGeneratorModels.set(firstTimelinePos, secondTimelineGeneratorModel);
		this.timelineGeneratorModels.set(secondTimelinePos, firstTimelineGeneratorModel);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Generator Models:
		
		firstTimelineGeneratorModel.setTimelinePos(secondTimelinePos);
		secondTimelineGeneratorModel.setTimelinePos(firstTimelinePos);
		
		//------------------------------------------------------------------------------------------
		// Notify listeners.
		this.changeTimelinesPositionChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #changeTimelinesPositionChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getChangeTimelinesPositionChangedNotifier()
	{
		return this.changeTimelinesPositionChangedNotifier;
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
	 * @param zoomX 
	 * 			to set {@link #zoomX}.
	 */
	public void setZoomX(float zoomX)
	{
		this.zoomX = zoomX;
		
		// Notify listeners.
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
}
