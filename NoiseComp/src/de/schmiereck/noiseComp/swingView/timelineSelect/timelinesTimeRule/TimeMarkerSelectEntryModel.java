/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

import de.schmiereck.noiseComp.swingView.ModelPropertyChangedNotifier;

/**
 * <p>
 * 	Time-Marker Select-Entry-Model.
 * </p>
 * 
 * @author smk
 * @version <p>02.03.2011:	created, smk</p>
 */
public class TimeMarkerSelectEntryModel
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Time marker.
	 */
	private double timeMarker = 0.0D;

	/**
	 * {@link #timeMarker} changed listeners.
	 */
	private final ModelPropertyChangedNotifier timeMarkerChangedNotifier = new ModelPropertyChangedNotifier();
	
	//----------------------------------------------------------------------------------------------
	/**
	 * Types of Marker.
	 */
	public enum MarkerType
	{
		START,
		POS,
		END
	}
	
	/**
	 * Marker Type.
	 */
	final private MarkerType markerType;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param markerType
	 * 			is the Marker Type.
	 */
	public TimeMarkerSelectEntryModel(MarkerType markerType)
	{
		this.markerType = markerType;
	}

	/**
	 * @return 
	 * 			returns the {@link #timeMarker}.
	 */
	public double getTimeMarker()
	{
		return this.timeMarker;
	}

	/**
	 * @param timeMarker 
	 * 			to set {@link #timeMarker}.
	 */
	public void setTimeMarker(double timeMarker)
	{
		//==========================================================================================
		if (this.timeMarker != timeMarker)
		{
			this.timeMarker = timeMarker;
			
			this.timeMarkerChangedNotifier.notifyModelPropertyChangedListeners();
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #markerType}.
	 */
	public MarkerType getMarkerType()
	{
		return this.markerType;
	}

	/**
	 * @return 
	 * 			returns the {@link #timeMarkerChangedNotifier}.
	 */
	public ModelPropertyChangedNotifier getTimeMarkerChangedNotifier()
	{
		return this.timeMarkerChangedNotifier;
	}
}
