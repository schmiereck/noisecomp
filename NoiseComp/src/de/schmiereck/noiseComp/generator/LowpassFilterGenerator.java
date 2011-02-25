/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	TODO docu type
 * </p>
 * 
 * see: http://baumdevblog.blogspot.com/2010/11/butterworth-lowpass-filter-coefficients.html
 * 
 * @author smk
 * @version <p>23.02.2011:	created, smk</p>
 */
public class LowpassFilterGenerator
{
    private final static double sqrt2 = 1.4142135623730950488;
    
	void calcLPCoefficientsButterworth2Pole(final int samplerate, 
	                                       final double cutoff, 
	                                       final double ax[], 
	                                       final double by[])
	{
	    // Find cutoff frequency in [0..PI]
	    double qcRaw  = (2 * Math.PI * cutoff) / samplerate;
	    // Warp cutoff frequency
	    double qcWarp = Math.tan(qcRaw); 

	    double gain = 1 / ( 1 + sqrt2 / qcWarp + 2 / (qcWarp * qcWarp));
	    
	    by[2] = (1 - sqrt2 / qcWarp + 2 / (qcWarp * qcWarp)) * gain;
	    by[1] = (2 - 2 * 2 / (qcWarp * qcWarp)) * gain;
	    by[0] = 1;
	    ax[0] = 1 * gain;
	    ax[1] = 2 * gain;
	    ax[2] = 1 * gain;
	}

	void filter(final double samples[], int count)
	{
	   double ax[] = new double[3];
	   double by[] = new double[3];

	   this.calcLPCoefficientsButterworth2Pole(44100, 5000, ax, by);

	   double xv[] = new double[3];
	   double yv[] = new double[3];

	   for (int i = 0; i < count; i++)
	   {
	       xv[2] = xv[1]; 
	       xv[1] = xv[0];
	       xv[0] = samples[i];
	       yv[2] = yv[1]; 
	       yv[1] = yv[0];

	       yv[0] = (ax[0] * xv[0] + 
	    		    ax[1] * xv[1] + 
	    		    ax[2] * xv[2] - 
	    		    by[1] * yv[0] - 
	    		    by[2] * yv[1]);

	       samples[i] = yv[0];
	   }
	}
}
