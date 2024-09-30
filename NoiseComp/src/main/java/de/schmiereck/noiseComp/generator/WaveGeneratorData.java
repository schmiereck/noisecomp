/*
 * www.schmiereck.de (c) 2011
 */
package de.schmiereck.noiseComp.generator;

/**
 * <p> TODO docu type </p>
 * @author  smk
 * @version  <p>17.06.2011:	created, smk</p>
 */
public class WaveGeneratorData
{
	/**
	 * 
	 */
	public SoundSample[]	soundSamples;
	/**
	 * 
	 */
	public float			wafeFrameRate;

	/**
	 * Constructor.
	 * 
	 */
	public WaveGeneratorData(SoundSample[] soundSamples, float wafeFrameRate)
	{
		this.soundSamples = soundSamples;
		this.wafeFrameRate = wafeFrameRate;
	}
}