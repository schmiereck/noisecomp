package de.schmiereck.noiseComp.soundSource;

import de.schmiereck.noiseComp.generator.OutputGenerator;
import de.schmiereck.noiseComp.generator.SoundSample;

/**
 * <p>
 * 	Manages a connection between the OutputGenerator of
 * 	the current selected Generators and the 
 * 	{@link de.schmiereck.noiseComp.soundData.SoundData}-Output.
 * </p>
 * <p>
 * 	Buffers the output.<br/>
 * 	The buffer is filled by a polling thread.<br/>
 * 	The affected parts of the buffer are cleard if changes in the input
 * 	are reported. 
 * </p>
 * 
 * TODO Change management einbauen, wenn sich "oben" was ändert, smk
 * 
 * @author smk
 * @version <p>06.06.2004: created, smk</p>
 */
public class SoundSourceLogic
{
	private OutputGenerator outputGenerator = null;

	private SoundSamplesBufferData	soundSamplesBufferData;
	
	public SoundSourceLogic(OutputGenerator outputGenerator)
	{
		this.outputGenerator = outputGenerator;

		this.soundSamplesBufferData = new SoundSamplesBufferData();
		
		float timeLen = this.outputGenerator.getEndTimePos() - this.outputGenerator.getStartTimePos();
		
		this.soundSamplesBufferData.createBuffer(timeLen, this.outputGenerator.getFrameRate());
	}

	/**
	 * @return the attribute {@link #outputGenerator}.
	 */
	public OutputGenerator getOutputGenerator()
	{
		return this.outputGenerator;
	}

	/**
	 * @param frame
	 * @return
	 */
	public SoundSample generateFrameSample(long frame)
	{
		SoundSample soundSample;
		
		soundSample = this.soundSamplesBufferData.get(frame, this.outputGenerator);
		
		return soundSample;
	}

	/**
	 * @param actualWaitPerFramesMillis
	 */
	public void pollCalcFillBuffer(long actualWaitPerFramesMillis)
	{
		this.soundSamplesBufferData.calcWaitingSamplesPart(actualWaitPerFramesMillis / 1000.0F, this.outputGenerator);
	}
}
