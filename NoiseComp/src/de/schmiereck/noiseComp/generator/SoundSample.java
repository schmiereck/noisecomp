package de.schmiereck.noiseComp.generator;

/**
 * TODO docu
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
		this.leftValue = soundSample.leftValue;
		this.rightValue = soundSample.rightValue;
	}

	/**
	 * @return (left + right) / 2
	 */
	public float getMonoValue()
	{
		return (this.leftValue + this.rightValue) / 2;
	}
}
