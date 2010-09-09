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
	private String generatorName;
	
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
	 * @param generatorName
	 * 			is the Name of Generator.
	 * @param startTimePos
	 * 			is the Start time position in milli seconds.
	 * @param endTimePos
	 * 			is the End time position in milli seconds.
	 */
	public TimelineGeneratorModel(String generatorName,
	                              float startTimePos, float endTimePos)
	{
		this.generatorName = generatorName;
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
	 * 			returns the {@link #generatorName}.
	 */
	public String getGeneratorName()
	{
		return this.generatorName;
	}

	/**
	 * @param generatorName 
	 * 			to set {@link #generatorName}.
	 */
	public void setGeneratorName(String generatorName)
	{
		this.generatorName = generatorName;
	}
	
}
