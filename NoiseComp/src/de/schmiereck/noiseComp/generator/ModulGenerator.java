package de.schmiereck.noiseComp.generator;

/**
 * Manages a List of Generators and use them
 * to generate his output signal.
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModulGenerator
extends Generator
{
	/**
	 * Constructor.
	 * 
	 */
	public ModulGenerator(String name, Float frameRate, GeneratorTypeData generatorTypeData)
	{
		super(name, frameRate, generatorTypeData);
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample sample)
	{
		ModulGeneratorTypeData generatorTypeData = (ModulGeneratorTypeData)this.getGeneratorTypeData();
		
		Generators generators = generatorTypeData.getGenerators();
		
		OutputGenerator outputGenerator = generators.getOutputGenerator();
		
		//if (outputGenerator != null)
		{	
			long outputStartPos = (long)(this.getStartTimePos() * this.getFrameRate());
			
			//outputGenerator.calculateSoundSample(framePosition, frameTime, sample);
			SoundSample outputSample = outputGenerator.generateFrameSample(framePosition - outputStartPos);
			
			sample.setValues(outputSample);
		}
	}

	/* (non-Javadoc)
	 * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
	 */
	public static GeneratorTypeData createGeneratorTypeData()
	{
		ModulGeneratorTypeData generatorTypeData = new ModulGeneratorTypeData(ModulGenerator.class, "Modul", "Manages a Group of other generators.");
		
		return generatorTypeData;
	}
}
