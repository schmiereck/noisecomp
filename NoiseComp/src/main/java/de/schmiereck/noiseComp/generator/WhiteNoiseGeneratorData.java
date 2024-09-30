/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator;

import java.util.Random;

/**
 * <p> TODO docu type </p>
 * @author  smk
 * @version  <p>17.06.2011:	created, smk</p>
 */
public class WhiteNoiseGeneratorData
{
	/**
	 * 
	 */
	public long		generatorSeed;
	/**
	 * 
	 */
	public Random	rnd;

	/**
	 * Constructor.
	 * 
	 */
	public WhiteNoiseGeneratorData(long generatorSeed, Random rnd)
	{
		this.generatorSeed = generatorSeed;
		this.rnd = rnd;
	}
}