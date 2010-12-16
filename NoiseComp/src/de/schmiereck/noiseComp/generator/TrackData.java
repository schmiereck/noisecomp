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
	//**********************************************************************************************
	// Fields:

	/**
	 * Generator.
	 */
	private Generator	generator;

	//**********************************************************************************************
	// Functions:

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
}
