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
	public static final int	INPUT_TYPE_START_VALUE	= 1;
	public static final int	INPUT_TYPE_END_VALUE	= 2;
	
	//private float startFadeValue = 0.0F;
	//private float endFadeValue = 0.0F;
	
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
	public void setEndFadeValue(float endFadeValue)
	{
		this.endFadeValue = endFadeValue;
	}
	 */
	/**
	 * @param startFadeValue is the new value for attribute {@link #startFadeValue} to set.
	public void setStartFadeValue(float startFadeValue)
	{
		this.startFadeValue = startFadeValue;
	}
	 */

	/* (non-Javadoc)
	 * @see de.schmiereck.soundGenerator.Generator#calculateSoundSample(long, float, de.schmiereck.soundGenerator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample soundSample)
	{
		float startFadeValue = this.calcStartFadeValue(framePosition);
		
		float endFadeValue = this.calcEndFadeValue(framePosition);
		
		// Relativer Zeitpunkt im Generator.
		float timePos = frameTime - (this.getStartTimePos());
		
		// Länge des Generators.
		float timeLen = this.getEndTimePos() - this.getStartTimePos();
		
		// Different zwischen End- und Startwert des Faders.
		float valueDif = endFadeValue - startFadeValue;
		
		// Wert zu dem angegebenen Zeitpunkt.
		float value = ((valueDif * timePos) / timeLen) + startFadeValue; 
		
		soundSample.setStereoValues(value, value);
	}
	
	private float calcEndFadeValue(long framePosition)
	{
		float endFadeValue;
		{
			InputData endFadeValueInputData = this.searchInputByType(INPUT_TYPE_END_VALUE);
			
			if (endFadeValueInputData != null)
			{	
				Float endFadeValueInputValue = endFadeValueInputData.getInputValue();
				
				// Constant input value ?
				if (endFadeValueInputValue != null)
				{
					endFadeValue = endFadeValueInputValue.floatValue();
				}
				else
				{	
					Generator inputSoundGenerator = endFadeValueInputData.getInputGenerator();
					
					if (inputSoundGenerator != null)
					{	
						SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
						
						if (inputSoundSample != null)
						{	
							endFadeValue = inputSoundSample.getMonoValue();
						}
						else
						{
							endFadeValue = 0.0F;
						}
					}
					else
					{
						endFadeValue = 0.0F;
					}
				}
			}
			else
			{
				endFadeValue = 0.0F;
			}
		}
		// Die Frameposition in Zeit umrechnen.
		//float frameTime = (framePosition / this.getFrameRate());
		return endFadeValue;
	}

	private float calcStartFadeValue(long framePosition)
	{
		float startFadeValue;
		{
			InputData startFadeValueInputData = this.searchInputByType(INPUT_TYPE_START_VALUE);
			
			if (startFadeValueInputData != null)
			{	
				Float startFadeValueInputValue = startFadeValueInputData.getInputValue();
				
				// Constant input value ?
				if (startFadeValueInputValue != null)
				{
					startFadeValue = startFadeValueInputValue.floatValue();
				}
				else
				{	
					Generator inputSoundGenerator = startFadeValueInputData.getInputGenerator();
					
					if (inputSoundGenerator != null)
					{	
						SoundSample inputSoundSample = inputSoundGenerator.generateFrameSample(framePosition);
						
						if (inputSoundSample != null)
						{	
							startFadeValue = inputSoundSample.getMonoValue();
						}
						else
						{
							startFadeValue = 0.0F;
						}
					}
					else
					{
						startFadeValue = 0.0F;
					}
				}
			}
			else
			{
				startFadeValue = 0.0F;
			}
		}
		return startFadeValue;
	}

	public static GeneratorTypeData createGeneratorTypeData()
	{
		GeneratorTypeData generatorTypeData = new GeneratorTypeData(FaderGenerator.class, "Fader");
		
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_START_VALUE, "startFadeValue", 1, 1);
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		{
			InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_END_VALUE, "endFadeValue", 1, 1);
			generatorTypeData.addInputTypeData(inputTypeData);
		}
		return generatorTypeData;
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#getGeneratorSampleDrawScale()
	 */
	public float getGeneratorSampleDrawScale()
	{
		// Works only, if the values are constant inputs:
		
		float startFadeValue = this.calcStartFadeValue(0);
		float endFadeValue = this.calcEndFadeValue(0);
		
		float max = Math.max(Math.abs(startFadeValue), Math.abs(endFadeValue));
		
		float ret;
		
		if (max > 1.0F)
		{
			ret = 1.0F / max;
		}
		else
		{	
			ret = 1.0F;
		}
		
		return ret;
	}
}
