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
	 * Name of Generator.
	 */
	private String name;
	
	/**
	 * Start time position in milli seconds.
	 */
	private float startTimePos;
	
	/**
	 * End time position in milli seconds.
	 */
	private float endTimePos;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * 			is the Name of Generator.
	 * @param startTimePos
	 * 			is the Start time position in milli seconds.
	 * @param endTimePos
	 * 			is the End time position in milli seconds.
	 */
	public TimelineGeneratorModel(String generatorName,
	                              float startTimePos, float endTimePos)
	{
		this.name = generatorName;
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
	 * 			returns the {@link #name}.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @param name 
	 * 			to set {@link #name}.
	 */
	public void setName(String generatorName)
	{
		this.name = generatorName;
	}
	
}
