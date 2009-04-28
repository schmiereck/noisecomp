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
	/**
	 * Generator.
	 */
	private Generator	generator;
	
	/**
	 * Position of the Generator Track in the List of Tracks.
	 */
	private int trackPos;

	
	/**
	 * Constructor.
	 * 
	 * @param generator
	 * 			is the Generator.
	 */
	public TrackData(Generator generator)
	{
		super();
		this.generator = generator;
	}

	/**
	 * @return
	 * 			the name of the {@link #generator}.
	 */
	public String getName()
	{
		return this.generator.getName();
	}

	/**
	 * @return
	 * 			the {@link #generator}.
	 */
	public Generator getGenerator()
	{
		return this.generator;
	}

	/**
	 * @return
	 * 			the Position of the Generator Track in the List of Tracks.
	 */
	public int getTrackPos()
	{
		return this.trackPos;
	}

	/**
	 * @param trackPos
	 * 			is the Position of the Generator Track in the List of Tracks.
	 */
	public void setTrackPos(int trackPos)
	{
		this.trackPos = trackPos;
	}
}
