package de.schmiereck.noiseComp.generator;


/**
 * TODO docu
 *
 * @author smk
 * @version 22.01.2004
 */
public class FaderGenerator
extends Generator
{
	private float startFadeValue = 0.0F;
	private float endFadeValue = 0.0F;
	
	/**
	 * Constructor.
	 * 
	 */
	public FaderGenerator(String name, float frameRate)
	{
		super(name, frameRate);
	}

	/**
	 * @param endFadeValue is the new value for attribute {@link #endFadeValue} to set.
	 */
	public void setEndFadeValue(float endFadeValue)
	{
		this.endFadeValue = endFadeValue;
	}
	/**
	 * @param startFadeValue is the new value for attribute {@link #startFadeValue} to set.
	 */
	public void setStartFadeValue(float startFadeValue)
	{
		this.startFadeValue = startFadeValue;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, float, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		// Die Frameposition in Zeit umrechnen.
		//float frameTime = (framePosition / this.getFrameRate());
		
		// Relativer Zeitpunkt im Generator.
		float timePos = frameTime - (this.getStartTimePos());
		
		// Länge des Generators.
		float timeLen = this.getEndTimePos() - this.getStartTimePos();
		
		// Different zwischen End- und Startwert des Faders.
		float valueDif = this.endFadeValue - this.startFadeValue;
		
		// Wert zu dem angegebenen Zeitpunkt.
		float value = ((valueDif * timePos) / timeLen) + this.startFadeValue; 
		
		soundSample.setStereoValues(value, value);
	}
	/**
	 * @return the attribute {@link #endFadeValue}.
	 */
	public float getEndFadeValue()
	{
		return this.endFadeValue;
	}
	/**
	 * @return the attribute {@link #startFadeValue}.
	 */
	public float getStartFadeValue()
	{
		return this.startFadeValue;
	}
}
