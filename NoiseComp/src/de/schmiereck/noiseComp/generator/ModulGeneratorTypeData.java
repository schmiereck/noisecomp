package de.schmiereck.noiseComp.generator;

import de.schmiereck.noiseComp.desktopPage.widgets.TracksListWidgetData;

/**
 * TODO docu
 *
 * @author smk
 * @version <p>28.02.2004: created, smk</p>
 */
public class ModulGeneratorTypeData
	extends GeneratorTypeData
{
	private Generators generators = null;
	
	/**
	 * Constructor.
	 * 
	 * @param generatorClass
	 * @param generatorTypeName
	 * @param generatorTypeDescription
	 */
	public ModulGeneratorTypeData(Class generatorClass, String generatorTypeName, String generatorTypeDescription)
	{
		super(generatorClass, generatorTypeName, generatorTypeDescription);
	}

	/**
	 * @see #generators
	 */
	public void setGenerators(Generators generators)
	{
		this.generators = generators;
	}
	/**
	 * @see #generators
	 */
	public Generators getGenerators()
	{
		return this.generators;
	}
}
