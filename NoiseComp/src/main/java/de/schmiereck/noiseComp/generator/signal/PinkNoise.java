package de.schmiereck.noiseComp.generator.signal;
/*
 * PinkNoise.java  -  a pink noise generator
 *
 * Copyright (c) 2008, Sampo Niskanen <sampo.niskanen@iki.fi>
 * All rights reserved.
 * Source:  http://www.iki.fi/sampo.niskanen/PinkNoise/
 *
 * Distrubuted under the BSD license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 *  - Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 *  - Neither the name of the copyright owners nor contributors may be
 * used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Random;


/**
 * A class that provides a source of pink noise with a power spectrum
 * density (PSD) proportional to 1/f^alpha.  "Regular" pink noise has a
 * PSD proportional to 1/f, i.e. alpha=1.  However, many natural
 * systems may require a different PSD proportionality.  The value of
 * alpha may be from 0 to 2, inclusive.  The special case alpha=0
 * results in white noise (directly generated random numbers) and
 * alpha=2 results in brown noise (integrated white noise).
 * <p>
 * The values are computed by applying an IIR filter to generated
 * Gaussian random numbers.  The number of poles used in the filter
 * may be specified.  For each number of poles there is a limiting
 * frequency below which the PSD becomes constant.  Values as low as
 * 1-3 poles produce relatively good results, however these values
 * will be concentrated near zero.  Using a larger number of poles
 * will allow more low frequency components to be included, leading to
 * more variation from zero.  However, the sequence is stationary,
 * that is, it will always return to zero even with a large number of
 * poles.
 * <p>
 * The distribution of values is very close to Gaussian with mean
 * zero, but the variance depends on the number of poles used.  The
 * algorithm can be made faster by changing the method call
 *	 <code> rnd.nextGaussian() </code>	to
 *	 <code> rnd.nextDouble()-0.5 </code>
 * in the method {@link #nextValue()}.  The resulting distribution is
 * almost Gaussian, but has a relatively larger amount of large
 * values.
 * <p>
 * The IIR filter used by this class is presented by N. Jeremy Kasdin,
 * Proceedings of the IEEE, Vol. 83, No. 5, May 1995, p. 822.
 * 
 * @author Sampo Niskanen <sampo.niskanen@iki.fi>
 */
public class PinkNoise 
{
	//**********************************************************************************************
	// Fields:

	private final Random rnd;
	private int poles;
	private double[] multipliers;
	
	private double[] values;

	/**
	 * Exponent of the pink noise, 1/f^alpha.
	 */
	private double alpha;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * Generate pink noise with alpha=1.0 using a five-pole IIR.
	 */
	public PinkNoise() 
	{
		this(1.0D, 5, new Random());
	}
	
	/**
	 * Constructor.
	 * 
	 * Generate a specific pink noise using a five-pole IIR.
	 * 
	 * @param alpha  the exponent of the pink noise, 1/f^alpha.
	 * @throws IllegalArgumentException  if <code>alpha < 0</code> or
	 *									  <code>alpha > 2</code>.
	 */
	public PinkNoise(double alpha) 
	{
		this(alpha, 5, new Random());
	}
	
	/**
	 * Constructor.
	 * 
	 * Generate pink noise specifying alpha and the number of poles.
	 * The larger the number of poles, the lower are the lowest
	 * frequency components that are amplified. 
	 * 
	 * @param alpha   the exponent of the pink noise, 1/f^alpha.
	 * @param poles   the number of poles to use.
	 * @throws IllegalArgumentException  if <code>alpha < 0</code> or
	 *									  <code>alpha > 2</code>.
	 */
	public PinkNoise(double alpha, int poles) 
	{
		this(alpha, poles, new Random());
	}

	/**
	 * Constructor.
	 * 
	 * Generate pink noise from a specific randomness source
	 * specifying alpha and the number of poles.  The larger the
	 * number of poles, the lower are the lowest frequency components
	 * that are amplified.
	 * 
	 * @param alpha   the exponent of the pink noise, 1/f^alpha.
	 * @param poles   the number of poles to use.
	 * @param random  the randomness source.
	 * @throws IllegalArgumentException  if <code>alpha < 0</code> or
	 *									  <code>alpha > 2</code>.
	 */
	public PinkNoise(double alpha, int poles, Random random) 
	{
		if (alpha < 0.0D || alpha > 2.0D) 
		{
			throw new IllegalArgumentException("Invalid pink noise alpha \"" + alpha + "\".");
		}

		this.rnd = random;
		this.alpha = alpha;
		
		this.setPoles(poles);
	}
	
	/**
	 * @param poles
	 * 			to set {@link #poles} and recalc {@link #multipliers}, {@link #values} and random-history.
	 */
	public void setPoles(int poles)
	{
		//==========================================================================================
		this.poles = poles;
		this.multipliers = new double[poles];
		this.values = new double[poles];
		
		//------------------------------------------------------------------------------------------
		this.recalcMultipliers();
		
		//------------------------------------------------------------------------------------------
		// Fill the history with random values
		for (int i = 0; i < 5 * poles; i++)
		{
			this.nextValue();
		}
		
		//==========================================================================================
	}
	
	/**
	 * Return the next pink noise sample.
	 *
	 * @return  the next pink noise sample.
	 */
	public double nextValue() 
	{
		//==========================================================================================
		// The following may be changed to  rnd.nextDouble()-0.5
		// if strict Gaussian distribution of resulting values is not
		// required.
		double x = this.rnd.nextGaussian();
		
		for (int i=0; i < this.poles; i++) 
		{
			x -= this.multipliers[i] * this.values[i];
		}
		System.arraycopy(this.values, 0, this.values, 1, this.values.length-1);
		this.values[0] = x;
		
		//==========================================================================================
		return x;
	}

	/**
	 * @return 
	 * 			returns the {@link #alpha}.
	 */
	public double getAlpha()
	{
		return this.alpha;
	}

	/**
	 * @param alpha 
	 * 			to set {@link #alpha} and recalc {@link #multipliers}.
	 */
	public void setAlpha(double alpha)
	{
		//==========================================================================================
		this.alpha = alpha;

		//------------------------------------------------------------------------------------------
		this.recalcMultipliers();
		
		//==========================================================================================
	}

	/**
	 * Recalc {@link #multipliers} after setting {@link #alpha} or {@link #poles}.
	 */
	private void recalcMultipliers()
	{
		//==========================================================================================
		double a = 1.0D;
		for (int i = 0; i < this.poles; i++) 
		{
			a = (i - this.alpha/2.0D) * a / (i+1.0D);
			this.multipliers[i] = a;
		}
		
		//==========================================================================================
	}

	/**
	 * A main method to demonstrate the functionality.  The program is used as:
	 * <pre>
	 *	 java PinkNoise samples [alpha [poles]]
	 *
	 *	 samples  =  number of samples to output
	 *	 alpha	=  PSD distribution exponent, 1/f^alpha (default 1.0)
	 *	 poles	=  number of IIR poles to use (default 5)
	 * </pre>
	 */
	public static void main(String[] arg) 
	{
		//==========================================================================================
		PinkNoise source;

		int n;
		double alpha = 1.0;
		int poles = 5;
		
		if (arg.length < 1 || arg.length > 3) 
		{
			System.out.println("\nUsage:\n" +
							   "	java PinkNoise <samples> [<alpha> [<poles>]]\n\n" +
							   " <samples> = number of samples to output\n" +
							   " <alpha>   = PSD distribution exponent, 1/f^alpha (default 1.0)\n" +
				   			   " <poles>   = number of IIR poles to use (default 5)\n");
		}
		else
		{
			// Parse arguments:
			n = Integer.parseInt(arg[0]);
			if (arg.length >= 2) 
			{
				alpha = Double.parseDouble(arg[1]);
			}
			if (arg.length >= 3) 
			{
				poles = Integer.parseInt(arg[2]);
			}
	
			// Generate values:
			
			source = new PinkNoise(alpha, poles);
			
			for (int i=0; i < n; i++) 
			{
				System.out.println(source.nextValue());
			}
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 * 			returns the {@link #poles}.
	 */
	public int getPoles()
	{
		return this.poles;
	}
}
