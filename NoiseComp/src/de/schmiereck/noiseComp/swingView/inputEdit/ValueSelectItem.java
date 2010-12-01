/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.swingView.inputEdit;

import de.schmiereck.noiseComp.frequences.ToneFrequences;
import de.schmiereck.noiseComp.frequences.ToneFrequences.Tone;
import de.schmiereck.noiseComp.swingView.CompareUtils;

/**
 * <p>
 * 	Value Select-Item for editable Combo-Box.
 * </p>
 * 
 * @author smk
 * @version <p>01.12.2010:	created, smk</p>
 */
public class ValueSelectItem
{
	//**********************************************************************************************
	// Fields:

	/**
	 * Value.
	 */
	private final String value;

	/**
	 * Octave.
	 */
	private final int octave;
	
	/**
	 * Tone.
	 */
	private final Tone tone;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 * 
	 * @param value
	 * 			is the Value.
	 * @param octave
	 * 			is the Octave.
	 * @param tone
	 * 			is the Tone.
	 */
	public ValueSelectItem(String value, int octave, Tone tone)
	{
		this.value = value;
		this.octave = octave;
		this.tone = tone;
	}

	/**
	 * Constructor.
	 * 
	 * @param value
	 * 			is the Value.
	 */
	public ValueSelectItem(String value)
	{
		this(value, 0, null);
	}

	/**
	 * @return 
	 * 			returns the {@link #value}.
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * @return 
	 * 			returns the {@link #octave}.
	 */
	public int getOctave()
	{
		return this.octave;
	}

	/**
	 * @return 
	 * 			returns the {@link #tone}.
	 */
	public Tone getTone()
	{
		return this.tone;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean ret;
		
		if (obj != null)
		{
			if (obj instanceof ValueSelectItem)
			{
				ValueSelectItem valueSelectItem = (ValueSelectItem)obj;
				
				ret = CompareUtils.compareWithNull(this.value, valueSelectItem.value);
			}
			else
			{
				ret = CompareUtils.compareWithNull(this.value, obj);
			}
		}
		else
		{
			ret = false;
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String ret;
		
		if (this.tone != null)
		{
			ret = ToneFrequences.makeLabel(this.octave, this.tone);
		}
		else
		{
			ret = this.value;
		}
		
		return ret;
	}
}
