/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
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
public class TimelinesDrawPanelModel {
	//**********************************************************************************************
	// Constants:
	
	public static final int X_SIZE_TIMELINE = 1;
	public static final int Y_SIZE_TIMELINE =
			TimelinesDrawPanelView.INPUT_MARKER_SIZE_Y + 4 +
			TimelinesDrawPanelView.TimelineHandlerSizeY + 4 +
			TimelinesDrawPanelView.ExpandHandlerSizeY + 4;

	//**********************************************************************************************
	// Fields:
	
	//----------------------------------------------------------------------------------------------
	private Dimension dimension = new Dimension(2000, 400);;

	/**
	 * {@link #dimension} changed listeners.
	 */
	private final ModelPropertyChangedNotifier dimensionChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	//private int xMinUnitIncrement = 1;
	//private int yMinUnitIncrement = Y_SIZE_TIMELINE;

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

	private final List<PropertyChangeListener> yPosTimelineListChangedListenerList = new ArrayList<>();

	/**
	 * Highlighted Timeline Handler.
	 */
	public enum HighlightedTimelineHandler {
		NONE,
		START,
		END
	}
	
	/**
	 * Highlighted Handler.
	 */
	private HighlightedTimelineHandler highlightedTimelineHandler = HighlightedTimelineHandler.NONE;

	/**
	 * Highlight Expand-Handler.
	 */
	private boolean highlightExpandTimelineHandler = false;

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

	private boolean timelineMoved;
	private double dragOffsetX;

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
	 	new ModelPropertyChangedListener() {
		@Override
		public void notifyModelPropertyChanged() {
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
                                   final SelectedTimelineModel selectedTimelineModel) {
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
	public SelectedTimelineModel getSelectedTimelineModel() {
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

//	/**
//	 * @return
//	 * 			returns the {@link #xMinUnitIncrement}.
//	 */
//	public int getXMinUnitIncrement() {
//		return this.xMinUnitIncrement;
//	}
//
//	/**
//	 * @param xMinUnitIncrement
//	 * 			to set {@link #xMinUnitIncrement}.
//	 */
//	public void setXMinUnitIncrement(int xMinUnitIncrement) {
//		this.xMinUnitIncrement = xMinUnitIncrement;
//	}
//
//	/**
//	 * @return
//	 * 			returns the {@link #yMinUnitIncrement}.
//	 */
//	public int getYMinUnitIncrement() {
//		return this.yMinUnitIncrement;
//	}
//
//	/**
//	 * @param yMinUnitIncrement
//	 * 			to set {@link #yMinUnitIncrement}.
//	 */
//	public void setYMinUnitIncrement(int yMinUnitIncrement) {
//		this.yMinUnitIncrement = yMinUnitIncrement;
//	}
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
	public float getZoomX() {
		return this.zoomX;
	}

	/**
	 * @param zoomX 
	 * 			to set {@link #zoomX}.
	 */
	public void setZoomX(float zoomX) {
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
	public ModelPropertyChangedNotifier getZoomXChangedNotifier() {
		return this.zoomXChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #dimension}.
	 */
	public Dimension getDimension() {
		return this.dimension;
	}

	/**
     * @param width  
     * 			the new width of the {@link #dimension}.
     * @param height 
     * 			the new height of the {@link #dimension}.
	 */
	public void setDimensionSize(double width, double height) {
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
	public ModelPropertyChangedNotifier getDimensionChangedNotifier() {
		return this.dimensionChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineGeneratorModelChangedListener}.
	 */
	public ModelPropertyChangedListener getTimelineGeneratorModelChangedListener() {
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

	public TimelineSelectEntryModel getHighlightedTimelineSelectEntryModel() {
		return this.highlightedTimelineSelectEntryModel;
	}

	public void setHighlightedTimelineSelectEntryModel(TimelineSelectEntryModel highlightedTimelineSelectEntryModel) {
		this.highlightedTimelineSelectEntryModel = highlightedTimelineSelectEntryModel;
	}

	public HighlightedTimelineHandler getHighlightedTimelineHandler() {
		return this.highlightedTimelineHandler;
	}

	public void setHighlightedTimelineHandler(final HighlightedTimelineHandler highlightedTimelineHandler) {
		this.highlightedTimelineHandler = highlightedTimelineHandler;
	}

	public boolean getHighlightExpandTimelineHandler() {
		return this.highlightExpandTimelineHandler;
	}

	public void setHighlightExpandTimelineHandler(final boolean highlightExpandTimelineHandler) {
		this.highlightExpandTimelineHandler = highlightExpandTimelineHandler;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineHandlerMoved}.
	 */
	public boolean getTimelineHandlerMoved() {
		return this.timelineHandlerMoved;
	}

	/**
	 * @param timelineHandlerMoved 
	 * 			to set {@link #timelineHandlerMoved}.
	 */
	public void setTimelineHandlerMoved(boolean timelineHandlerMoved) {
		this.timelineHandlerMoved = timelineHandlerMoved;
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is the TimelineSelectEntryModel.
	 */
	public void notifyTimelineStartTimePosChangedListeners(TimelineSelectEntryModel timelineSelectEntryModel) {
		Timeline timeline = timelineSelectEntryModel.getTimeline();
		Float startTimePos = timelineSelectEntryModel.getStartTimePos();
		
		if (timeline != null) {
			for (TimelineStartTimePosChangedListenerInterface changedListener : this.timelineStartTimePosChangedListeners) {
				changedListener.notifyTimelineStartTimePosChangedListener(timeline,
				                                                          startTimePos);
			}
		}
	}

	/**
	 * @param timelineSelectEntryModel
	 * 			is the TimelineSelectEntryModel.
	 */
	public void notifyTimelineEndTimePosChangedListeners(TimelineSelectEntryModel timelineSelectEntryModel) {
		Timeline timeline = timelineSelectEntryModel.getTimeline();
		Float endTimePos = timelineSelectEntryModel.getEndTimePos();
		
		for (TimelineEndTimePosChangedListenerInterface changedListener : this.timelineEndTimePosChangedListeners) {
			changedListener.notifyTimelineEndTimePosChangedListener(timeline,
			                                                        endTimePos);
		}
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineStartTimePosChangedListeners}.
	 */
	public List<TimelineStartTimePosChangedListenerInterface> getTimelineStartTimePosChangedListeners() {
		return this.timelineStartTimePosChangedListeners;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineEndTimePosChangedListeners}.
	 */
	public List<TimelineEndTimePosChangedListenerInterface> getTimelineEndTimePosChangedListeners() {
		return this.timelineEndTimePosChangedListeners;
	}

	/**
	 * @return 
	 * 			returns the {@link #nearestSnapToTimpePos}.
	 */
	public double getNearestSnapToTimpePos() {
		return this.nearestSnapToTimpePos;
	}

	/**
	 * @param nearestSnapToTimpePos 
	 * 			to set {@link #nearestSnapToTimpePos}.
	 */
	public void setNearestSnapToTimpePos(double nearestSnapToTimpePos) {
		this.nearestSnapToTimpePos = nearestSnapToTimpePos;
	}

	/**
	 * @return 
	 * 			returns the {@link #handlerSnaped}.
	 */
	public boolean getHandlerSnaped() {
		return this.handlerSnaped;
	}

	/**
	 * @param handlerSnaped 
	 * 			to set {@link #handlerSnaped}.
	 */
	public void setHandlerSnaped(boolean handlerSnaped) {
		this.handlerSnaped = handlerSnaped;
	}

	/**
	 * @param ticksPer
	 * 			is the Tick Unit.
	 * @param ticksCount
	 * 			are the ticks.
	 */
	public void notifyTicksChangedNotifier(TicksPer ticksPer, Float ticksCount) {
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
	public ModelPropertyChangedNotifier getTicksChangedNotifier() {
		return this.ticksChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksPer}.
	 */
	public TicksPer getTicksPer() {
		return this.ticksPer;
	}

	/**
	 * @return 
	 * 			returns the {@link #ticksCount}.
	 */
	public Float getTicksCount() {
		return this.ticksCount;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntriesModel}.
	 */
	public TimelineSelectEntriesModel getTimelineSelectEntriesModel() {
		return this.timelineSelectEntriesModel;
	}

	/**
	 * @param timelineEntryModel
	 * 			is the Timeline-Entry Model.
	 * @return
	 * 			the Position of timeline.
	 */
	public int calcTimelinePos(TimelineSelectEntryModel timelineEntryModel) {
		//==========================================================================================
		int timelinePos;
		
		//------------------------------------------------------------------------------------------
		timelinePos = 0;
		
		List<TimelineSelectEntryModel> timelineSelectEntryModels = this.timelineSelectEntriesModel.getTimelineSelectEntryModelList();
		
		{
			int timelineGeneratorPos = 0;
			
			for (TimelineSelectEntryModel timelineSelectEntryModel : timelineSelectEntryModels) {
				if (timelineEntryModel == timelineSelectEntryModel) {
					timelinePos = timelineGeneratorPos;
					break;
				}
				timelineGeneratorPos++;
			}
		}
		//==========================================================================================
		return timelinePos;
	}

	public boolean getTimelineMoved() {
		return this.timelineMoved;
	}

	public void setTimelineMoved(final boolean timelineMoved) {
		this.timelineMoved = timelineMoved;
	}

	public double getDragOffsetX() {
		return this.dragOffsetX;
	}

	public void setDragOffsetX(final double dragOffsetX) {
		this.dragOffsetX = dragOffsetX;
	}

	public void addYPosTimelineListChangedListener(final PropertyChangeListener listener) {
		this.yPosTimelineListChangedListenerList.add(listener);
	}

	public void fireYPosTimelineListChanged() {
		final PropertyChangeEvent event = new PropertyChangeEvent(this, "yPosTimelineList", null, null);
		for (final PropertyChangeListener listener : this.yPosTimelineListChangedListenerList) {
			listener.propertyChange(event);
		}
	}
}
