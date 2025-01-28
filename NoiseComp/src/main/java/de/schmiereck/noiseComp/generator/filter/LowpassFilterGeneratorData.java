/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator.filter;

import java.util.Arrays;
import java.util.Objects;

/**
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

	public double ax[];
	public double by[];

	public float cutoff = Float.NaN;

	/**
	 * Constructor.
	 * 
	 */
	public LowpassFilterGeneratorData(double[] xv, double[] yv) {
		this.xv = xv;
		this.yv = yv;

		this.ax = new double[3];
		this.by = new double[3];

		this.cutoff = Float.NaN;
	}

	/**
	 * Constructor.
	 *
	 */
	public LowpassFilterGeneratorData() {
		this.xv = new double[3];
		this.yv = new double[3];

		this.ax = new double[3];
		this.by = new double[3];
	}

	public LowpassFilterGeneratorData(final LowpassFilterGeneratorData previousLowpassFilterData) {
		this.xv = Arrays.copyOf(previousLowpassFilterData.xv, previousLowpassFilterData.xv.length);
		this.yv = Arrays.copyOf(previousLowpassFilterData.yv, previousLowpassFilterData.yv.length);

		this.ax = Arrays.copyOf(previousLowpassFilterData.ax, previousLowpassFilterData.ax.length);
		this.by = Arrays.copyOf(previousLowpassFilterData.by, previousLowpassFilterData.by.length);

		this.cutoff = previousLowpassFilterData.cutoff;
	}
}