/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelines;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;


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
	 * {@link #name} changed listeners.
	 */
	private final ModelPropertyChangedNotifier nameChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * Start time position in milli seconds.
	 */
	private float startTimePos;

	/**
	 * {@link #startTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier startTimePosChangedNotifier = new ModelPropertyChangedNotifier();
	
	/**
	 * End time position in milli seconds.
	 */
	private float endTimePos;

	/**
	 * {@link #endTimePos} changed listeners.
	 */
	private final ModelPropertyChangedNotifier endTimePosChangedNotifier = new ModelPropertyChangedNotifier();
	
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
		
		this.startTimePosChangedNotifier.notifyModelPropertyChangedListeners();
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
		
		this.endTimePosChangedNotifier.notifyModelPropertyChangedListeners();
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
		
		this.nameChangedNotifier.notifyModelPropertyChangedListeners();
	}

	/**
	 * @return 
	 * 			returns the {@link #nameChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getNameChangedNotifier()
	{
		return this.nameChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #startTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getStartTimePosChangedNotifier()
	{
		return this.startTimePosChangedNotifier;
	}

	/**
	 * @return 
	 * 			returns the {@link #endTimePosChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getEndTimePosChangedNotifier()
	{
		return this.endTimePosChangedNotifier;
	}
	
}
