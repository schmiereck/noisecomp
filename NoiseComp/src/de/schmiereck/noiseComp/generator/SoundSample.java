package de.schmiereck.noiseComp.generator;

/**
 * <p>
 * 	Manges a sound sample.<br/>
 * 	This is a floating point stereo signal.
 * </p>
 * <p>
 * 	The folowing names are used:<br/>
 * 	value: is a free floating point stereo value<br/>
 * 	signal: is a floating point stereo signal in the range between -1 and +1<br/>
 * 	mono: is a floating point mono value or signal<br/>
 * </p>
 * 
 * @author smk
 * @version 21.01.2004
 */
public class SoundSample
{
	private float leftValue = 0.0F;
	private float rightValue = 0.0F;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public SoundSample()
	{
		super();
	}

	/**
	 * @return the attribute {@link #leftValue}.
	 */
	public float getLeftValue()
	{
		return this.leftValue;
	}
	/**
	 * @param leftValue is the new value for attribute {@link #leftValue} to set.
	 * @param rightValue is the new value for attribute {@link #rightValue} to set.
	 */
	public void setStereoValues(float leftValue, float rightValue)
	{
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}

	/**
	 * Limites the Range to -1.0 and +1.0.
	 * 
	 * @param leftValue is the new value for attribute {@link #leftValue} to set.
	 * @param rightValue is the new value for attribute {@link #rightValue} to set.
	 */
	public void setStereoSignals(float leftValue, float rightValue)
	{
		if (leftValue > 1.0F)
		{
			this.leftValue = 1.0F;
		}
		else
		{
			if (leftValue < -1.0F)
			{
				this.leftValue = -1.0F;
			}
			else
			{
				this.leftValue = leftValue;
			}
		}
		if (rightValue > 1.0F)
		{
			this.rightValue = 1.0F;
		}
		else
		{
			if (rightValue < -1.0F)
			{
				this.rightValue = -1.0F;
			}
			else
			{
				this.rightValue = rightValue;
			}
		}
	}

	/**
	 * @return the attribute {@link #rightValue}.
	 */
	public float getRightValue()
	{
		return this.rightValue;
	}

	/**
	 * @param inputSoundSample
	 */
	public void setValues(SoundSample soundSample)
	{
		if (soundSample != null)
		{	
			this.leftValue = soundSample.leftValue;
			this.rightValue = soundSample.rightValue;
		}
	}

	/**
	 * @return (left + right) / 2
	 */
	public float getMonoValue()
	{
		return (this.leftValue + this.rightValue) / 2;
	}

	/**
	 * @param value
	 */
	public void setMonoValue(float value)
	{
		this.leftValue = value;
		this.rightValue = value;
	}

	/**
	 * Limites the Range to -1.0 and +1.0.
	 * 
	 * @param value
	 */
	public void setMonoSignal(float value)
	{
		if (leftValue > 1.0F)
		{
			this.leftValue = 1.0F;
			this.rightValue = 1.0F;
		}
		else
		{
			if (leftValue < -1.0F)
			{
				this.leftValue = -1.0F;
				this.rightValue = -1.0F;
			}
			else
			{
				this.leftValue = value;
				this.rightValue = value;
			}
		}
	}

	/**
	 * @param inputSoundSample
	 */
	public void setSignals(SoundSample soundSample)
	{
		if (soundSample != null)
		{	
			this.setStereoSignals(soundSample.leftValue, soundSample.rightValue);
		}
	}
}
