/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelineHandler;

import java.awt.Rectangle;

/**
 * <p>
 * 	Timeline-Handler Model.
 * </p>
 * 
 * @author smk
 * @version <p>17.11.2010:	created, smk</p>
 */
public class TimelineHandlerModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Start-Time Handler.
	 */
	private final Rectangle startTimelineHandlerRect;
	
	/**
	 * End-Time Handler.
	 */
	private final Rectangle endTimelineHandlerRect;

	/**
	 * Expand Timeline Handler.
	 */
	private final Rectangle expandTimelineHandlerRect;

	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param startTimelineHandlerRect
	 * 			is the Left Handler.
	 * @param endTimelineHandlerRect
	 * 			is the Right Handler.
	 */
	public TimelineHandlerModel(final Rectangle startTimelineHandlerRect,
								final Rectangle endTimelineHandlerRect,
								final Rectangle expandTimelineHandlerRect)
	{
		this.startTimelineHandlerRect = startTimelineHandlerRect;
		this.endTimelineHandlerRect = endTimelineHandlerRect;
		this.expandTimelineHandlerRect = expandTimelineHandlerRect;
	}

	public Rectangle getStartTimelineHandlerRect() {
		return this.startTimelineHandlerRect;
	}

	public Rectangle getEndTimelineHandlerRect() {
		return this.endTimelineHandlerRect;
	}

	public Rectangle getExpandTimelineHandlerRect() {
		return this.expandTimelineHandlerRect;
	}

}
