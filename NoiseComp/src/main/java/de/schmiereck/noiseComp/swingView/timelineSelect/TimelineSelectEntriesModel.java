/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.util.List;
import java.util.Vector;

import de.schmiereck.noiseComp.soundSource.SoundSourceData;
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
public class TimelineSelectEntriesModel {
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
	private List<TimelineSelectEntryModel> timelineSelectEntryModelList = new Vector<TimelineSelectEntryModel>();

	/**
	 * {@link #timelineSelectEntryModelList} changed (set new, insert or remove) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineGeneratorModelsChangedNotifier = new ModelPropertyChangedNotifier();

	/**
	 * {@link #timelineSelectEntryModelList} changed (set new) listeners.
	 */
	private final ModelPropertyChangedNotifier timelineSelectEntryModelsChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * {@link #timelineSelectEntryModelList} removed listeners.
	 */
	private RemoveTimelineGeneratorNotifier removeTimelineGeneratorNotifier = new RemoveTimelineGeneratorNotifier();
	
	/**
	 * {@link #timelineSelectEntryModelList} positions changed.
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
	public TimelineSelectEntriesModel(final AppModelChangedObserver appModelChangedObserver) {
		//==========================================================================================
		this.appModelChangedObserver = appModelChangedObserver;
		
		//==========================================================================================
	}
	
	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntryModelList}.
	 */
	public List<TimelineSelectEntryModel> getTimelineSelectEntryModelList() {
		return this.timelineSelectEntryModelList;
	}

	/**
	 * @param timelineSelectEntryModelList
	 * 			to set {@link #timelineSelectEntryModelList}.
	 */
	public void setTimelineSelectEntryModelList(final List<TimelineSelectEntryModel> timelineSelectEntryModelList) {
		//==========================================================================================
		this.timelineSelectEntryModelList = timelineSelectEntryModelList;
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		this.timelineSelectEntryModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * Clear Timeline-Generators.
	 */
	public void clearTimelineGenerators() {
		//==========================================================================================
		this.timelineSelectEntryModelList.clear();
		
		// Notify listeners.
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		//==========================================================================================
	}

	/**
	 * Do not use this directly, use {@link TimelinesDrawPanelController#addTimelineSelectEntryModel(int, TimelineSelectEntryModel)}
	 * because of registering change listeners for TimelineSelectEntryModel changes.
	 * 
	 * @param timelinePos
	 * 			is the timeline Pos to insert the Timeline.
	 * @param timelineSelectEntryModel
	 * 			is a Timeline-Generator Model.
	 */
	public void addTimelineSelectEntryModel(final int timelinePos,
											final TimelineSelectEntryModel timelineSelectEntryModel) {
		//==========================================================================================
		this.timelineSelectEntryModelList.add(timelinePos,
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
	public int removeTimelineSelectEntryModel(final SoundSourceData soundSourceData,
											  final TimelinesDrawPanelModel timelinesDrawPanelModel,
											  final TimelineSelectEntryModel timelineSelectEntryModel) {
		//==========================================================================================
		final int timelinePos = this.calcTimelineSelectEntryPos(timelineSelectEntryModel);

		this.timelineSelectEntryModelList.remove(timelinePos);
		
		// Notify listeners.
		this.removeTimelineGeneratorNotifier.notifyRemoveTimelineGeneratorListeners(soundSourceData,
				timelinesDrawPanelModel, timelineSelectEntryModel);
		
		this.timelineGeneratorModelsChangedNotifier.notifyModelPropertyChangedListeners();
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		this.appModelChangedObserver.notifyAppModelChanged();
		
		//==========================================================================================
		return timelinePos;
	}

	/**
	 * @param firstTimelinePos
	 * 			is the first Timeline-Generator Poisition.
	 * @param secondTimelinePos
	 * 			is the second Timeline-Generator Poisition.
	 */
	public void changeTimelinesPosition(final int firstTimelinePos, final int secondTimelinePos) {
		//==========================================================================================
		final TimelineSelectEntryModel firstTimelineSelectEntryModel = this.timelineSelectEntryModelList.get(firstTimelinePos);
		final TimelineSelectEntryModel secondTimelineSelectEntryModel = this.timelineSelectEntryModelList.get(secondTimelinePos);
		
		this.timelineSelectEntryModelList.set(firstTimelinePos, secondTimelineSelectEntryModel);
		this.timelineSelectEntryModelList.set(secondTimelinePos, firstTimelineSelectEntryModel);
		
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
	public int calcTimelineSelectEntryPos(final TimelineSelectEntryModel searchTimelineSelectEntryModel) {
		//==========================================================================================
		int retPos = 0;
		
		for (final TimelineSelectEntryModel timelineSelectEntryModel : this.timelineSelectEntryModelList) {
			if (timelineSelectEntryModel == searchTimelineSelectEntryModel) {
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
	public ModelPropertyChangedNotifier getTimelineGeneratorModelsChangedNotifier() {
		return this.timelineGeneratorModelsChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #removeTimelineGeneratorNotifier}.
	 */
	public RemoveTimelineGeneratorNotifier getRemoveTimelineGeneratorNotifier() {
		return this.removeTimelineGeneratorNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #changeTimelinesPositionChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getChangeTimelinesPositionChangedNotifier() {
		return this.changeTimelinesPositionChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTime}.
	 */
	public double getEndTime() {
		return this.endTime;
	}

	/**
	 * @param endTime 
	 * 			to set {@link #endTime}.
	 */
	public void setEndTime(final double endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 
	 * 			returns the {@link #timelineSelectEntryModelsChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTimelineSelectEntryModelsChangedNotifier() {
		return this.timelineSelectEntryModelsChangedNotifier;
	}

}
