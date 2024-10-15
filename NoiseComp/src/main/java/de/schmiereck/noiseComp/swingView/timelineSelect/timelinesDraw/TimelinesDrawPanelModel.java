/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.generator.module.ModuleGeneratorTypeInfoData.TicksPer;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedListener;
import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.timelineSelect.SelectedTimelineModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntriesModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.TimelineSelectEntryModel;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineEndTimePosChangedListenerInterface;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.TimelineStartTimePosChangedListenerInterface;
import de.schmiereck.noiseComp.timeline.Timeline;

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
	// Constants:
	
	public static final int SIZE_TIMELINE_Y = 16;
	
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
	private int maxUnitIncrementY = SIZE_TIMELINE_Y;

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
//
//	/**
//	 * {@link #timelineSelectEntryModels} removed listeners.
//	 */
//	private RemoveTimelineGeneratorNotifier removeTimelineGeneratorNotifier = new RemoveTimelineGeneratorNotifier();
	
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
	
	/**
	 * Highlighted Timeline Handler.
	 */
	public enum HighlightedTimelineHandler
	{
		NONE,
		LEFT,
		RIGHT
	}
	
	/**
	 * Highlighted Handler.
	 */
	private HighlightedTimelineHandler highlightedTimelineHandler = HighlightedTimelineHandler.NONE;
	
	/**
	 * <code>true</code> if the {@link #highlightedTimelineHandler} is moved.
	 */
	private boolean timelineHandlerMoved = false;
	
	/**
	 * Timeline Start-Time-Pos Changed Listeners.
	 */
	private final List<TimelineStartTimePosChangedListenerInterface> timelineStartTimePosChangedListeners = new Vector<TimelineStartTimePosChangedListenerInterface>();
	
	/**
	 * Timeline End-Time-Pos Changed Listeners.
	 */
	private final List<TimelineEndTimePosChangedListenerInterface> timelineEndTimePosChangedListeners = new Vector<TimelineEndTimePosChangedListenerInterface>();
	
	private double nearestSnapToTimpePos = Double.NaN;
	
	/**
	 * <code>true</code> if a handler snaped to a line.
	 */
	private boolean handlerSnaped = false;
	
	//----------------------------------------------------------------------------------------------
	
//	/**
//	 * Do Timeline Selected Listeners.
//	 */
//	private List<DoTimelineSelectedListenerInterface> doTimelineSelectedListeners = new Vector<DoTimelineSelectedListenerInterface>();
//	
//	/**
//	 * {@link #timelineSelectEntryModels} positions changed.
//	 * 
//	 * @see #changeTimelinesPosition(int, int)
//	 */
//	private final ModelPropertyChangedNotifier changeTimelinesPositionChangedNotifier = new ModelPropertyChangedNotifier();
	
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
//			timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
			timelineSelectEntriesModel.getTimelineGeneratorModelsChangedNotifier().notifyModelPropertyChangedListeners();
		}
 	};
	
	//----------------------------------------------------------------------------------------------
	private final AppModelChangedObserver appModelChangedObserver;
 	
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
	 * @param timelineSelectEntriesModel
	 * 			are the Timeline-Select-Entries Model.
	 * @param selectedTimelineModel
	 * 			is the Selected-Timeline Model.
	 */
	public TimelinesDrawPanelModel(final AppModelChangedObserver appModelChangedObserver,
	                               final TimelineSelectEntriesModel timelineSelectEntriesModel,
                                   final SelectedTimelineModel selectedTimelineModel)
	{
		//==========================================================================================
		this.timelineSelectEntriesModel = timelineSelectEntriesModel;
		
		this.appModelChangedObserver = appModelChangedObserver;
		
		this.selectedTimelineModel = selectedTimelineModel;

		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #selectedTimelineModel}.
	 */
	public SelectedTimelineModel getSelectedTimelineModel()
	{
		return this.selectedTimelineModel;
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
//	 * Clear Timeline-Generators.
//	 */
//	public void clearTimelineGenerators()
//	{
//		//==========================================================================================
//		this.timelineSelectEntryModels.clear();
//		
//		// Notify listeners.
//		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
//		
//		//==========================================================================================
//	}
//
//	/**
//	 * Do not use this directly, use {@link TimelinesDrawPanelController#addTimelineSelectEntryModel(TimelineSelectEntryModel)}
//	 * because of registering change listeners for TimelineSelectEntryModel changes.
//	 * 
//	 * @param timelinePos
//	 * 			is the timeline Pos to insert the Timeline.
//	 * @param timelineSelectEntryModel
//	 * 			is a Timeline-Generator Model.
//	 */
//	public void addTimelineSelectEntryModel(int timelinePos,
//	                                        TimelineSelectEntryModel timelineSelectEntryModel)
//	{
//		//==========================================================================================
//		this.timelineSelectEntryModels.add(timelinePos,
//		                                   timelineSelectEntryModel);
//		
//		// Notify listeners.
//		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
//		//==========================================================================================
//	}
//
//	/**
//	 * @param timelineSelectEntryModel
//	 * 			is the Generator.
//	 */
//	public void removeTimelineSelectEntryModel(TimelineSelectEntryModel timelineSelectEntryModel)
//	{
//		//==========================================================================================
//		this.timelineSelectEntryModels.remove(timelineSelectEntryModel);
//		
//		// Notify listeners.
//		this.removeTimelineGeneratorNotifier.notifyRemoveTimelineGeneratorListeners(this,
//		                                                                            timelineSelectEntryModel);
//		
//		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
//		
//		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		this.appModelChangedObserver.notifyAppModelChanged();
//		
//		//==========================================================================================
//	}
//
//	/**
//	 * @return 
//	 * 			returns the {@link #timelineGeneratorModelsChangedNotifier}.
//	 */
//	public ModelPropertyChangedNotifier getTimelineGeneratorModelsChangedNotifier()
//	{
//		return this.timelineGeneratorModelsChangedNotifier;
//	}
//
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
//		
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
//
//	/**
//	 * @param firstTimelinePos
//	 * 			is the first Timeline-Generator Poisition.
//	 * @param secondTimelinePos
//	 * 			is the second Timeline-Generator Poisition.
//	 */
//	public void changeTimelinesPosition(int firstTimelinePos, int secondTimelinePos)
//	{
//		//==========================================================================================
//		TimelineSelectEntryModel firstTimelineSelectEntryModel = this.timelineSelectEntryModels.get(firstTimelinePos);
//		TimelineSelectEntryModel secondTimelineSelectEntryModel = this.timelineSelectEntryModels.get(secondTimelinePos);
//		
//		this.timelineSelectEntryModels.set(firstTimelinePos, secondTimelineSelectEntryModel);
//		this.timelineSelectEntryModels.set(secondTimelinePos, firstTimelineSelectEntryModel);
//		
//		//------------------------------------------------------------------------------------------
//		// Update Timeline-Generator Models:
//		
////		firstTimelineSelectEntryModel.setTimelinePos(secondTimelinePos);
////		secondTimelineSelectEntryModel.setTimelinePos(firstTimelinePos);
//		
//		//------------------------------------------------------------------------------------------
//		// Notify listeners.
//		this.changeTimelinesPositionChangedNotifier.notifyModelPropertyChangedListeners();
//		
//		//==========================================================================================
//	}
//
//	/**
//	 * @return 
//	 * 			returns the {@link #changeTimelinesPositionChangedNotifier}.
//	 */
//	public ModelPropertyChangedNotifier getChangeTimelinesPositionChangedNotifier()
//	{
//		return this.changeTimelinesPositionChangedNotifier;
//	}

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
		//==========================================================================================
		this.zoomX = zoomX;
		
		// Notify listeners.
		this.zoomXChangedNotifier.notifyModelPropertyChangedListeners();
		//==========================================================================================
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
		//==========================================================================================
		this.dimension.setSize(width, height);
		
		// Notify listeners.
		this.dimensionChangedNotifier.notifyModelPropertyChangedListeners();
		//==========================================================================================
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
//
//	/**
//	 * @param searchTimelineSelectEntryModel
//	 * 			is the searched timeline SelectEntryModel.
//	 * @return
//	 * 			the position of timeline.
//	 */
//	public int getTimelineSelectEntryPos(TimelineSelectEntryModel searchTimelineSelectEntryModel)
//	{
//		//==========================================================================================
//		int retPos;
//		
//		retPos = 0;
//		
//		for (TimelineSelectEntryModel timelineSelectEntryModel : this.timelineSelectEntryModels)
//		{
//			if (timelineSelectEntryModel == searchTimelineSelectEntryModel)
//			{
//				break;
//			}
//			
//			retPos++;
//		}
//		
//		//==========================================================================================
//		return retPos;
//	}
//
//	/**
//	 * @return 
//	 * 			returns the {@link #removeTimelineGeneratorNotifier}.
//	 */
//	public RemoveTimelineGeneratorNotifier getRemoveTimelineGeneratorNotifier()
//	{
//		return this.removeTimelineGeneratorNotifier;
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
	 * 			returns the {@link #highlightedTimelineHandler}.
	 */
	public HighlightedTimelineHandler getHighlightedTimelineHandler()
	{
		return this.highlightedTimelineHandler;
	}

	/**
	 * @param highlightedTimelineHandler 
	 * 			to set {@link #highlightedTimelineHandler}.
	 */
	public void setHighlightedTimelineHandler(HighlightedTimelineHandler highlightedTimelineHandler)
	{
		this.highlightedTimelineHandler = highlightedTimelineHandler;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineHandlerMoved}.
	 */
	public boolean getTimelineHandlerMoved()
	{
		return this.timelineHandlerMoved;
	}

	/**
	 * @param timelineHandlerMoved 
	 * 			to set {@link #timelineHandlerMoved}.
	 */
	public void setTimelineHandlerMoved(boolean timelineHandlerMoved)
	{
		this.timelineHandlerMoved = timelineHandlerMoved;
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is the TimelineSelectEntryModel.
	 */
	public void notifyTimelineStartTimePosChangedListeners(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		Timeline timeline = timelineSelectEntryModel.getTimeline();
		Float startTimePos = timelineSelectEntryModel.getStartTimePos();
		
		if (timeline != null)
		{
			for (TimelineStartTimePosChangedListenerInterface changedListener : this.timelineStartTimePosChangedListeners)
			{
				changedListener.notifyTimelineStartTimePosChangedListener(timeline,
				                                                          startTimePos);
			}
		}
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is the TimelineSelectEntryModel.
	 */
	public void notifyTimelineEndTimePosChangedListeners(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		Timeline timeline = timelineSelectEntryModel.getTimeline();
		Float endTimePos = timelineSelectEntryModel.getEndTimePos();
		
		for (TimelineEndTimePosChangedListenerInterface changedListener : this.timelineEndTimePosChangedListeners)
		{
			changedListener.notifyTimelineEndTimePosChangedListener(timeline,
			                                                        endTimePos);
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineStartTimePosChangedListeners}.
	 */
	public List<TimelineStartTimePosChangedListenerInterface> getTimelineStartTimePosChangedListeners()
	{
		return this.timelineStartTimePosChangedListeners;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEndTimePosChangedListeners}.
	 */
	public List<TimelineEndTimePosChangedListenerInterface> getTimelineEndTimePosChangedListeners()
	{
		return this.timelineEndTimePosChangedListeners;
	}

	/**
	 * @return 
	 * 			returns the {@link #nearestSnapToTimpePos}.
	 */
	public double getNearestSnapToTimpePos()
	{
		return this.nearestSnapToTimpePos;
	}

	/**
	 * @param nearestSnapToTimpePos 
	 * 			to set {@link #nearestSnapToTimpePos}.
	 */
	public void setNearestSnapToTimpePos(double nearestSnapToTimpePos)
	{
		this.nearestSnapToTimpePos = nearestSnapToTimpePos;
	}

	/**
	 * @return 
	 * 			returns the {@link #handlerSnaped}.
	 */
	public boolean getHandlerSnaped()
	{
		return this.handlerSnaped;
	}

	/**
	 * @param handlerSnaped 
	 * 			to set {@link #handlerSnaped}.
	 */
	public void setHandlerSnaped(boolean handlerSnaped)
	{
		this.handlerSnaped = handlerSnaped;
	}

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

	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntriesModel}.
	 */
	public TimelineSelectEntriesModel getTimelineSelectEntriesModel()
	{
		return this.timelineSelectEntriesModel;
	}

	/**
	 * @param timelineEntryModel
	 * 			is the Timeline-Entry Model.
	 * @return
	 * 			the Position of timeline.
	 */
	public int calcTimelinePos(TimelineSelectEntryModel timelineEntryModel)
	{
		//==========================================================================================
		int timelinePos;
		
		//------------------------------------------------------------------------------------------
		timelinePos = 0;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelineSelectEntriesModel.getTimelineSelectEntryModels(); 
		
		{
			int timelineGeneratorPos = 0;
			
			for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels)
			{
				if (timelineEntryModel == timelineSelectEntryModel)
				{
					timelinePos = timelineGeneratorPos;
					break;
				}
				
				timelineGeneratorPos++;
			}
		}
		//==========================================================================================
		return timelinePos;
	}
}
