package de.schmiereck.noiseComp.generator;

import java.util.Iterator;

import de.schmiereck.dataTools.VectorHash;

/**
 * TODO docu
 *
 * @author smk
 * @version 21.02.2004
 */
public class GeneratorTypesData
{
	/**
	 * List of the available {@link GeneratorTypeData}-Objects.
	 */
	private VectorHash generatorTypes = new VectorHash();
	
	public void addGeneratorTypeData(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypes.add(generatorTypeData.getGeneratorClass(), generatorTypeData);
	}

	/**
	 * @param generator
	 * @return
	 */
	public GeneratorTypeData searchGeneratorTypeData(Generator generator)
	{
		GeneratorTypeData generatorTypeData;
		
		if (generator != null)
		{	
			generatorTypeData = (GeneratorTypeData)this.generatorTypes.get(generator.getClass());
		}
		else
		{	
			generatorTypeData = null;
		}

		return generatorTypeData;
	}
	
	public GeneratorTypeData get(int pos)
	{
		GeneratorTypeData generatorTypeData;
		
		generatorTypeData = (GeneratorTypeData)this.generatorTypes.get(pos);

		return generatorTypeData;
	}
	
	public void removeGeneratorType(GeneratorTypeData generatorTypeData)
	{
		this.generatorTypes.remove(generatorTypeData);
	}
	
	public int getSize()
	{
		return this.generatorTypes.size();
	}

	public Iterator getGeneratorTypesIterator()
	{
		return this.generatorTypes.iterator();
	}
}
