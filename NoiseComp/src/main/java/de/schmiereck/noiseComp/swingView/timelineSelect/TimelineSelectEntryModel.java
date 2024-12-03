/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;
import de.schmiereck.noiseComp.swingView.timelineSelect.timelinesDraw.TimelinesDrawPanelModel;
import de.schmiereck.noiseComp.timeline.Timeline;


/**
 * <p>
 * 	Timeline Select-Entry-Model.
 * </p>
 * 
 * @author smk
 * @version <p>05.09.2010:	created, smk</p>
 */
public class TimelineSelectEntryModel
{
	//**********************************************************************************************
	// Fields:
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Timeline.
	 * <code>null</code> if the timeline is new generated.
	 */
	private Timeline timeline = null;
	
//	//----------------------------------------------------------------------------------------------
//	/**
//	 * Position of tmeline in the select list.
//	 */
//	private int timelinePos;
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Name of Generator.
	 */
	private String name;

	/**
	 * {@link #name} changed listeners.
	 */
	private final ModelPropertyChangedNotifier nameChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Start time position in milli seconds.
	 */
	private Float startTimePos;

	/**
	 * {@link #startTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier startTimePosChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * End time position in milli seconds.
	 */
	private Float endTimePos;

	/**
	 * {@link #endTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier endTimePosChangedNotifier = new ModelPropertyChangedNotifier();

	//----------------------------------------------------------------------------------------------
	private boolean expanded;

	private int yPosGenerator;
	private int ySizeGenerator;// = TimelinesDrawPanelModel.Y_SIZE_TIMELINE;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param timeline
	 * 			is the Timeline.
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @param startTimePos
	 * 			is the Start time position in milli seconds.
	 * @param endTimePos
	 * 			is the End time position in milli seconds.
	 */
	public TimelineSelectEntryModel(final Timeline timeline,
//	                                final int timelinePos,
	                                final String generatorName,
	                                final Float startTimePos, final Float endTimePos,
									final int yPosGenerator, final int ySizeGenerator)
	{
		//==========================================================================================
		this.timeline = timeline;
//		this.timelinePos = timelinePos;
		this.name = generatorName;
		this.startTimePos = startTimePos;
		this.endTimePos = endTimePos;
		this.yPosGenerator = yPosGenerator;
		this.ySizeGenerator = ySizeGenerator;

		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #startTimePos}.
	 */
	public Float getStartTimePos() {
		return this.startTimePos;
	}

	/**
	 * @param startTimePos 
	 * 			to set {@link #startTimePos}.
	 */
	public void setStartTimePos(Float startTimePos) {
		this.startTimePos = startTimePos;
		
		// Notify listeners.
		this.startTimePosChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #startTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getStartTimePosChangedNotifier() {
		return this.startTimePosChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTimePos}.
	 */
	public Float getEndTimePos() {
		return this.endTimePos;
	}

	/**
	 * @param endTimePos 
	 * 			to set {@link #endTimePos}.
	 */
	public void setEndTimePos(Float endTimePos) {
		this.endTimePos = endTimePos;
		
		// Notify listeners.
		this.endTimePosChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #name}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param generatorName
	 * 			to set {@link #name}.
	 */
	public void setName(String generatorName) {
		this.name = generatorName;
		
		this.nameChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #nameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getNameChangedNotifier() {
		return this.nameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getEndTimePosChangedNotifier() {
		return this.endTimePosChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #timeline}.
	 */
	public Timeline getTimeline() {
		return this.timeline;
	}

	/**
	 * @param timeline 
	 * 			to set {@link #timeline}.
	 */
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public boolean getExpanded() {
		return this.expanded;
	}

	public void setExpanded(final boolean expanded) {
		this.expanded = expanded;
	}

	public int getYSizeGenerator() {
		return this.ySizeGenerator;
	}

	public void setYSizeGenerator(int ySizeGenerator) {
		this.ySizeGenerator = ySizeGenerator;
	}

	public void setYPosGenerator(int yPosGenerator) {
		this.yPosGenerator = yPosGenerator;
	}

	public int getYPosGenerator() {
		return this.yPosGenerator;
	}

}
