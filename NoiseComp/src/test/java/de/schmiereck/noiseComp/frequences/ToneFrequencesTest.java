/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.frequences;

import org.junit.Test;
import static org.junit.Assert.*;
import de.schmiereck.noiseComp.frequences.ToneFrequences.Tone;

/**
 * <p>
 * 	{@link ToneFrequences} TestCase.
 * </p>
 * 
 * @author smk
 * @version <p>01.12.2010:	created, smk</p>
 */
public class ToneFrequencesTest
{
	public void testMakeFrequence()
	{
		//==========================================================================================
		{
			float freq = ToneFrequences.makeFrequence(0, Tone.C);
			
			assertEquals(16.0F, freq);
		}
		//------------------------------------------------------------------------------------------
		{
			float freq = ToneFrequences.makeFrequence(1, Tone.C);
			
			assertEquals(33.0F, freq);
		}
		//------------------------------------------------------------------------------------------
		{
			float freq = ToneFrequences.makeFrequence(0, Tone.D);
			
			assertEquals(18.0F, freq);
		}
		//------------------------------------------------------------------------------------------
		{
			float freq = ToneFrequences.makeFrequence(1, Tone.D);
			
			assertEquals(37.0F, freq);
		}
		//------------------------------------------------------------------------------------------
		{
			try
			{
				ToneFrequences.makeFrequence(ToneFrequences.OCTAVE_MIN - 1, Tone.C);
				
				fail("Should throw exception: ToneFrequences.OCTAVE_MIN - 1");
			}
			catch (Exception ex)
			{
			}
		}
		//------------------------------------------------------------------------------------------
		{
			try
			{
				ToneFrequences.makeFrequence(ToneFrequences.OCTAVE_MAX + 1, Tone.C);
				
				fail("Should throw exception: ToneFrequences.OCTAVE_MAX + 1");
			}
			catch (Exception ex)
			{
			}
		}
		//==========================================================================================
	}
}
