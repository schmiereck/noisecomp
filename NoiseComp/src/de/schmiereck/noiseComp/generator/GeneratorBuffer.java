package de.schmiereck.noiseComp.generator;

/**
 * Basic class and interface for a buffer of a generator.<br/>
 * A implementation of a buffer has to implement these functions.
 *
 * @author smk
 * @version <p>14.04.2004: created, smk</p>
 */
public abstract class GeneratorBuffer
{
	private long	startPosition	= 0;
	private long	endPosition		= 0;
	
	/**
	 * @param framePosition
	 * @return true, if the framePosition is in the buffer time
	 */
	public boolean checkIsInTime(long framePosition)
	{
		boolean ret;
		
		if ((framePosition >= this.startPosition) && (framePosition < this.endPosition))
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	/**
	 * Call this function if the generator length or pos is changed.
	 * 
	 * @param endPosition is the new value for attribute {@link #endPosition} to set.
	 */
	public void setEndPosition(long endPosition)
	{
		this.endPosition = endPosition;
	}
	/**
	 * Call this function if the generator length or pos is changed.
	 * 
	 * @param startPosition is the new value for attribute {@link #startPosition} to set.
	 */
	public void setStartPosition(long startPosition)
	{
		this.startPosition = startPosition;
	}
	/**
	 * @return the attribute {@link #endPosition}.
	 */
	protected long getEndPosition()
	{
		return this.endPosition;
	}
	/**
	 * @return the attribute {@link #startPosition}.
	 */
	protected long getStartPosition()
	{
		return this.startPosition;
	}
	
	/**
	 * @param framePosition
	 * @return 
	 * 			<code>null</code> wenn im Buffer für diese Position kein Wert vorliegt.
	 */
	public abstract SoundSample readBuffer(long framePosition);

	/**
	 * @param framePosition
	 */
	public abstract void writeBuffer(long framePosition, SoundSample soundSample);
}
