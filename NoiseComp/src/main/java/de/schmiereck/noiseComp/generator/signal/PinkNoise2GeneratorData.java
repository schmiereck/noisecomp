/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator.signal;

import java.util.Random;

/**
 * <p> TODO docu type </p>
 * @author  smk
 * @version  <p>17.06.2011:	created, smk</p>
 */
public class PinkNoise2GeneratorData
{
	/**
	 * 
	 */
	public long			generatorSeed;
	/**
	 * 
	 */
	public Random		rnd;
	/**
	 * Pink noise source.
	 */
	public PinkNoise	pinkNoise;

	/**
	 * Constructor.
	 * 
	 */
	public PinkNoise2GeneratorData(long generatorSeed, Random rnd, PinkNoise pinkNoise)
	{
		this.generatorSeed = generatorSeed;
		this.rnd = rnd;
		this.pinkNoise = pinkNoise;
	}
}