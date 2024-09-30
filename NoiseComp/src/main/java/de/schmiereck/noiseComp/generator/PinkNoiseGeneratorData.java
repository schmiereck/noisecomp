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
public class PinkNoiseGeneratorData
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
	 * 
	 */
	public double[]	pinkBuf;

	/**
	 * Constructor.
	 * 
	 */
	public PinkNoiseGeneratorData(long generatorSeed, Random rnd, double[] pinkBuf)
	{
		this.generatorSeed = generatorSeed;
		this.rnd = rnd;
		this.pinkBuf = pinkBuf;
	}
}