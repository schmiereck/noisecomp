/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.appModel.AppModelChangedObserver;
import de.schmiereck.noiseComp.swingView.timelineSelect.listeners.RemoveTimelineGeneratorNotifier;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelController;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;

/**
 * <p>
 * 	Timeline-Select-Entries Model.
 * </p>
 * 
 * @author smk
 * @version <p>01.03.2011:	created, smk</p>
 */
public class TimelineSelectEntriesModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * End time of timelines.
	 */
	private double endTime = 0.0D;
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Timeline-Generator Models.
	 */
	private List<TimelineSelectEntryModel> timelineSelectEntryModels = new Vector<TimelineSelectEntryModel>();

	/**
	 * {@link #timelineSelectEntryModels} changed (set new, insert or remove) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();

	/**
	 * {@link #timelineSelectEntryModels} changed (set new) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineSelectEntryModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * {@link #timelineSelectEntryModels} removed listeners.
	 */
	private RemoveTimelineGeneratorNotifier removeTimelineGeneratorNotifier = new RemoveTimelineGeneratorNotifier();
	
	/**
	 * {@link #timelineSelectEntryModels} positions changed.
	 * 
	 * @see #changeTimelinesPosition(int, int)
	 */
	private final ModelPropertyChangedNotifier changeTimelinesPositionChangedNotifier = new ModelPropertyChangedNotifier();
	
//	//----------------------------------------------------------------------------------------------
//	private float playbackTime = 0.0F;
//
//	/**
//	 * {@link #playbackTime} changed listeners.
//	 */
//	private final ModelPropertyChangedNotifier playbackTimeChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	private final AppModelChangedObserver appModelChangedObserver;
 	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 */
	public TimelineSelectEntriesModel(final AppModelChangedObserver appModelChangedObserver)
	{
		//==========================================================================================
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
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
		//==========================================================================================
		this.timelineSelectEntryModels = timelineSelectEntryModels;
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		this.timelineSelectEntryModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators()
	{
		//==========================================================================================
		this.timelineSelectEntryModels.clear();
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * Do not use this directly, use {@link TimelinesDrawPanelController#addTimelineSelectEntryModel(TimelineSelectEntryModel)}
	 * because of registering change listeners for TimelineSelectEntryModel changes.
	 * 
	 * @param timelinePos
	 * 			is the timeline Pos to insert the Timeline.
	 * @param timelineSelectEntryModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineSelectEntryModel(int timelinePos,
	                                        TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		this.timelineSelectEntryModels.add(timelinePos,
		                                   timelineSelectEntryModel);
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		//==========================================================================================
	}

	/**
	 * @param timelinesDrawPanelModel
	 * 			is the Timeline Draw-Panel Model. 
	 * @param timelineSelectEntryModel
	 * 			is the Generator.
	 */
	public void removeTimelineSelectEntryModel(final TimelinesDrawPanelModel timelinesDrawPanelModel,
	                                           final TimelineSelectEntryModel timelineSelectEntryModel)
	{
		//==========================================================================================
		this.timelineSelectEntryModels.remove(timelineSelectEntryModel);
		
		// Notify listeners.
		this.removeTimelineGeneratorNotifier.notifyRemoveTimelineGeneratorListeners(timelinesDrawPanelModel,
		                                                                            timelineSelectEntryModel);
		
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
	}

	/**
	 * @param firstTimelinePos
	 * 			is the first Timeline-Generator Poisition.
	 * @param secondTimelinePos
	 * 			is the second Timeline-Generator Poisition.
	 */
	public void changeTimelinesPosition(int firstTimelinePos, int secondTimelinePos)
	{
		//==========================================================================================
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
		
		//==========================================================================================
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
	 * 			returns the {@link #removeTimelineGeneratorNotifier}.
	 */
	public RemoveTimelineGeneratorNotifier getRemoveTimelineGeneratorNotifier()
	{
		return this.removeTimelineGeneratorNotifier;
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
	 * 			returns the {@link #endTime}.
	 */
	public double getEndTime()
	{
		return this.endTime;
	}

	/**
	 * @param endTime 
	 * 			to set {@link #endTime}.
	 */
	public void setEndTime(double endTime)
	{
		this.endTime = endTime;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntryModelsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTimelineSelectEntryModelsChangedNotifier()
	{
		return this.timelineSelectEntryModelsChangedNotifier;
	}

//	/**
//	 * @return 
//	 * 			returns the {@link #playbackTime}.
//	 */
//	public float getPlaybackTime()
//	{
//		return this.playbackTime;
//	}
//
//	/**
//	 * @param playbackTime 
//	 * 			to set {@link #playbackTime}.
//	 */
//	public void setPlaybackTime(float playbackTime)
//	{
//		//==========================================================================================
//		this.playbackTime = playbackTime;
//		
//		this.playbackTimeChangedNotifier.notifyModelPropertyChangedListeners();
//		
//		//==========================================================================================
//	}
//
//	/**
//	 * @return 
//	 * 			returns the {@link #playbackTimeChangedNotifier}.
//	 */
//	public ModelPropertyChangedNotifier getPlaybackTimeChangedNotifier()
//	{
//		//==========================================================================================
//		return this.playbackTimeChangedNotifier;
//	}
	
}
