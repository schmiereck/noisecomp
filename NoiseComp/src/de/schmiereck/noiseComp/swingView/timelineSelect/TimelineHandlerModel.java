/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect;

import java.awt.Rectangle;

/**
 * <p>
 * 	Timeline Handler Model.
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
	 * Left Handler.
	 */
	private final Rectangle rect1;
	
	/**
	 * Right Handler.
	 */
	private final Rectangle rect2;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param rect1
	 * 			is the Left Handler.
	 * @param rect2
	 * 			is the Right Handler.
	 */
	public TimelineHandlerModel(Rectangle rect1, Rectangle rect2)
	{
		this.rect1 = rect1;
		this.rect2 = rect2;
	}

	/**
	 * @return 
	 * 			returns the {@link #rect1}.
	 */
	public Rectangle getRect1()
	{
		return this.rect1;
	}

	/**
	 * @return 
	 * 			returns the {@link #rect2}.
	 */
	public Rectangle getRect2()
	{
		return this.rect2;
	}
	
}
