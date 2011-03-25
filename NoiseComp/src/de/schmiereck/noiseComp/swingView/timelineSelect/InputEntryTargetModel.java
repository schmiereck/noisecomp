/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.geom.Point2D;

/**
 * <p>
 * 	Input-Entry Target Model.
 * </p>
 * 
 * @author smk
 * @version <p>24.03.2011:	created, smk</p>
 */
public class InputEntryTargetModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Target Point.<br/>
	 * <code>null</code> if no target point selected.
	 */
	private Point2D targetPoint2D = null;
	
	/**
	 * Target Timeline.<br/>
	 * <code>null</code> if no target timeline found.
	 */
	private TimelineSelectEntryModel targetTimelineSelectEntryModel = null;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @return 
	 * 			returns the {@link #targetPoint2D}.
	 */
	public Point2D getTargetPoint2D()
	{
		return this.targetPoint2D;
	}

	/**
	 * @param point2d 
	 * 			to set {@link #targetPoint2D}.
	 */
	public void setTargetPoint2D(Point2D point2d)
	{
		this.targetPoint2D = point2d;
	}

	/**
	 * @return 
	 * 			returns the {@link #targetTimelineSelectEntryModel}.
	 */
	public TimelineSelectEntryModel getTargetTimelineSelectEntryModel()
	{
		return this.targetTimelineSelectEntryModel;
	}

	/**
	 * @param targetTimelineSelectEntryModel 
	 * 			to set {@link #targetTimelineSelectEntryModel}.
	 */
	public void setTargetTimelineSelectEntryModel(TimelineSelectEntryModel timelineSelectEntryModel)
	{
		this.targetTimelineSelectEntryModel = timelineSelectEntryModel;
	}
	
}
