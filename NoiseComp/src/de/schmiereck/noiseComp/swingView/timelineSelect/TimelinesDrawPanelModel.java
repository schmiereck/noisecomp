/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
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
	private Dimension dimension = new Dimension(2000, 400);;

	/**
	 * {@link #dimension} changed listeners.
	 */
	private final ModelPropertyChangedNotifier dimensionChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	private int maxUnitIncrementX = 1;
	private int maxUnitIncrementY = 16;

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
	/**
	 * Selected Timeline Generator Model.
	 */
	private TimelineSelectEntryModel selectedTimelineSelectEntryModel = null;
	
//	/**
//	 * Selected Timeline Changed Listeners.
//	 */
//	private List<SelectedTimelineChangedListenerInterface> selectedTimelineChangedListeners = new Vector<SelectedTimelineChangedListenerInterface>();
	/**
	 * {@link #selectedTimelineSelectEntryModel} changed listeners.
	 */
	private final ModelPropertyChangedNotifier selectedTimelineChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	
//	/**
//	 * Do Timeline Selected Listeners.
//	 */
//	private List<DoTimelineSelectedListenerInterface> doTimelineSelectedListeners = new Vector<DoTimelineSelectedListenerInterface>();
	
	/**
	 * {@link #timelineSelectEntryModels} positions changed.
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
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Something in the Timeline-Generator internals changed.
	 */
	private final ModelPropertyChangedListener timelineGeneratorModelChangedListener =
	 	new ModelPropertyChangedListener()
 	{
		@Override
		public void notifyModelPropertyChanged()
		{
//			timelinesDrawPanelView.repaint();
			timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		}
 	};
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntryModels}.
	 */
	public List<TimelineSelectEntryModel> getTimelineSelectEntryModels()
	{
		return this.timelineSelectEntryModels;
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		this.timelineSelectEntryModels.clear();
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * Do not use this directly, use {@link TimelinesDrawPanelController#addTimelineSelectEntryModel(TimelineSelectEntryModel)}
	 * because of registering change listeners for TimelineSelectEntryModel changes.
	 * 
	 * @param timelineSelectEntryModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineSelectEntryModel(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		this.timelineSelectEntryModels.add(timelineSelectEntryModel);
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is the Generator.
	 */
	public void removeTimelineSelectEntryModel(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		this.timelineSelectEntryModels.remove(timelineSelectEntryModel);
		
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
		this.selectedTimelineSelectEntryModel = selectedTimelineSelectEntryModel;
		
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
		TimelineSelectEntryModel firstTimelineSelectEntryModel = this.timelineSelectEntryModels.get(firstTimelinePos);
		TimelineSelectEntryModel secondTimelineSelectEntryModel = this.timelineSelectEntryModels.get(secondTimelinePos);
		
		this.timelineSelectEntryModels.set(firstTimelinePos, secondTimelineSelectEntryModel);
		this.timelineSelectEntryModels.set(secondTimelinePos, firstTimelineSelectEntryModel);
		
		//------------------------------------------------------------------------------------------
		// Update Timeline-Generator Models:
		
//		firstTimelineSelectEntryModel.setTimelinePos(secondTimelinePos);
//		secondTimelineSelectEntryModel.setTimelinePos(firstTimelinePos);
		
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

	/**
	 * @return 
	 * 			returns the {@link #dimension}.
	 */
	public Dimension getDimension()
	{
		return this.dimension;
	}

	/**
     * @param width  
     * 			the new width of the {@link #dimension}.
     * @param height 
     * 			the new height of the {@link #dimension}.
	 */
	public void setDimensionSize(double width, double height)
	{
		this.dimension.setSize(width, height);
		
		// Notify listeners.
		this.dimensionChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #dimensionChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getDimensionChangedNotifier()
	{
		return this.dimensionChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener()
	{
		return this.timelineGeneratorModelChangedListener;
	}

	/**
	 * @param searchTimelineSelectEntryModel
	 * 			is the searched timeline SelectEntryModel.
	 * @return
	 * 			the position of timeline.
	 */
	public int getTimelineSelectEntryPos(TimelineSelectEntryModel searchTimelineSelectEntryModel)
	{
		//==========================================================================================
		int retPos;
		
		retPos = 0;
		
		for (TimelineSelectEntryModel timelineSelectEntryModel : this.timelineSelectEntryModels)
		{
			if (timelineSelectEntryModel == searchTimelineSelectEntryModel)
			{
				break;
			}
			
			retPos++;
		}
		
		//==========================================================================================
		return retPos;
	}
}
