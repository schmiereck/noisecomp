/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.timelineSelect.timelinesTimeRule;

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
		this.timeMarker = timeMarker;
	}

	/**
	 * @return 
	 * 			returns the {@link #markerType}.
	 */
	public MarkerType getMarkerType()
	{
		return this.markerType;
	}
}
