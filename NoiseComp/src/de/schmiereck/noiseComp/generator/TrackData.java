package de.schmiereck.noiseComp.generator;


/**
 * Ein Track ist ein Eintrag in der Liste.
 * Verwaltet einen Generator.
 *
 * @author smk
 * @version 25.01.2004
 */
public class TrackData
{
	private Generator	generator;
	
	/**
	 * Position des Tracks in der Liste der Tracks.
	 */
	private int trackPos;

	
	/**
	 * Constructor.
	 * 
	 * @param generator
	 */
	public TrackData(Generator generator)
	{
		super();
		this.generator = generator;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return this.generator.getName();
	}

	/**
	 * @return
	 */
	public Generator getGenerator()
	{
		return this.generator;
	}

	/**
	 * @see #trackPos
	 */
	public int getTrackPos()
	{
		return this.trackPos;
	}

	/**
	 * @see #trackPos
	 */
	public void setTrackPos(int trackPos)
	{
		this.trackPos = trackPos;
	}
}
