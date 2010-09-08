/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;


/**
 * <p>
 * 	Timeline Generator Model.
 * </p>
 * 
 * @author smk
 * @version <p>05.09.2010:	created, smk</p>
 */
public class TimelineGeneratorModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Start time position in milli seconds.
	 */
	private float startTimePos;
	
	/**
	 * End time position in milli seconds.
	 */
	private float endTimePos;
	
	private boolean selected = false;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param startTimePos
	 * 			is the Start time position in milli seconds.
	 * @param endTimePos
	 * 			is the End time position in milli seconds.
	 */
	public TimelineGeneratorModel(float startTimePos, float endTimePos)
	{
		this.startTimePos = startTimePos;
		this.endTimePos = endTimePos;
	}

	/**
	 * @return 
	 * 			returns the {@link #startTimePos}.
	 */
	public float getStartTimePos()
	{
		return this.startTimePos;
	}

	/**
	 * @param startTimePos 
	 * 			to set {@link #startTimePos}.
	 */
	public void setStartTimePos(float startTimePos)
	{
		this.startTimePos = startTimePos;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTimePos}.
	 */
	public float getEndTimePos()
	{
		return this.endTimePos;
	}

	/**
	 * @param endTimePos 
	 * 			to set {@link #endTimePos}.
	 */
	public void setEndTimePos(float endTimePos)
	{
		this.endTimePos = endTimePos;
	}

	/**
	 * @return 
	 * 			returns the {@link #selected}.
	 */
	public boolean getSelected()
	{
		return this.selected;
	}

	/**
	 * @param selected 
	 * 			to set {@link #selected}.
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
}
