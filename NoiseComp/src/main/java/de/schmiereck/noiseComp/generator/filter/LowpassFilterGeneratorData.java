/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator.filter;

/**
 * <p> TODO docu type </p>
 * @author  smk
 * @version  <p>17.06.2011:	created, smk</p>
 */
public class LowpassFilterGeneratorData
{
	/**
	 * 
	 */
	public double[]	xv;
	/**
	 * 
	 */
	public double[]	yv;

	/**
	 * Constructor.
	 * 
	 */
	public LowpassFilterGeneratorData(double[] xv, double[] yv)
	{
		this.xv = xv;
		this.yv = yv;
	}
}