/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p> TODO docu type </p>
 * @author  smk
 * @version  <p>17.06.2011:	created, smk</p>
 */
public class ResonanceFilterGeneratorData
{
	/**
	 * Last input value.
	 */
	public double	lp_input;
	/**
	 * 
	 */
	public double	lp_cutoff;
	/**
	 * 
	 */
	public double	lp_resonance;
	/**
	 * 
	 */
	public double	lp_amp;
	/**
	 * 
	 */
	public double	lp_low;
	/**
	 * 
	 */
	public double	lp_band;
	/**
	 * 
	 */
	public double	lp_high;
	/**
	 * 
	 */
	public double	lp_d1;
	/**
	 * 
	 */
	public double	lp_d2;

	/**
	 * Constructor.
	 * 
	 */
	public ResonanceFilterGeneratorData(double lp_input, double lp_cutoff, double lp_resonance, double lp_amp,
										double lp_low, double lp_band, double lp_high, double lp_d1, double lp_d2)
	{
		this.lp_input = lp_input;
		this.lp_cutoff = lp_cutoff;
		this.lp_resonance = lp_resonance;
		this.lp_amp = lp_amp;
		this.lp_low = lp_low;
		this.lp_band = lp_band;
		this.lp_high = lp_high;
		this.lp_d1 = lp_d1;
		this.lp_d2 = lp_d2;
	}
}