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
	 * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.ModulGenerator)
	 */
	public void calculateSoundSample(long framePosition, float frameTime, SoundSample sample, ModulGenerator parentModulGenerator)
	{
		ModulGeneratorTypeData modulGeneratorTypeData = (ModulGeneratorTypeData)this.getGeneratorTypeData();
		
		Generators generators = modulGeneratorTypeData.getGenerators();
		
		OutputGenerator outputGenerator = generators.getOutputGenerator();
		
		// TODO let it crash, write better programs to prevent from this situation ;-) smk
		//if (outputGenerator != null)
		{	
			long outputStartPos = (long)(this.getStartTimePos() * this.getFrameRate());
			
			//outputGenerator.calculateSoundSample(framePosition, frameTime, sample);
			SoundSample outputSample = outputGenerator.generateFrameSample(framePosition - outputStartPos, this);
			
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
